package com.world.rentcar.integrador.controller;

import com.world.rentcar.integrador.exeptions.BadRequest;
import com.world.rentcar.integrador.model.Reserva;
import com.world.rentcar.integrador.model.Sucursal;
import com.world.rentcar.integrador.model.Usuario;
import com.world.rentcar.integrador.model.Vehiculo;
import com.world.rentcar.integrador.service.ReservaService;
import com.world.rentcar.integrador.service.SucursalService;
import com.world.rentcar.integrador.service.UsuarioService;
import com.world.rentcar.integrador.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/reserva")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;
    @Autowired
    private VehiculoService vehiculoService;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    JavaMailSender javaMailSender;

    @PostMapping("/reservar")
    public ResponseEntity<?> realizarReserva(@RequestBody Reserva reserva) {
        try {
            // Validaciones previas
            Long vehiculoId = reserva.getVehiculo().getId();
            Vehiculo vehiculo = vehiculoService.buscarxID(vehiculoId).orElseThrow(() -> new BadRequest("No se encontró el vehículo con ID: " + vehiculoId));

            Long usuarioId = reserva.getUsuario().getId();
            Usuario usuario = usuarioService.buscarxID(usuarioId).orElseThrow(() -> new BadRequest("No se encontró el usuario con ID: " + usuarioId));

            reserva.setVehiculo(vehiculo);
            reserva.setUsuario(usuario);

            if (reserva.getVehiculo().getPrecio() == null) {
                throw new BadRequest("El precio del vehículo es nulo.");
            }

            if (reservaService.existenReservasEnFechas(reserva.getDiaInicio(), reserva.getDiaFinalizacion(), vehiculoId)) {
                throw new BadRequest("El vehículo ya está reservado para algunas de las fechas seleccionadas.");
            }
            if (reserva.getDiaInicio().after(reserva.getDiaFinalizacion())) {
                throw new BadRequest("La fecha de inicio debe ser anterior a la fecha de finalización.");
            }
            if (!reserva.isPoliticas()) {
                throw new BadRequest("El usario no aceptó la politícas.");
            }


            // Llamada al servicio para realizar la reserva
            reservaService.calcularPrecioFinal(reserva);
            Reserva reservaGuardada = reservaService.realizarReserva(reserva);



            // Actualización de fechas no disponibles del vehículo
            List<Date> diasNoDisponible = vehiculo.getFechasNoDisponibles();
            diasNoDisponible.addAll(reservaService.obtenerFechasEntre(reserva.getDiaInicio(), reserva.getDiaFinalizacion()));
            vehiculo.setFechasNoDisponibles(diasNoDisponible);
            vehiculoService.modificar(vehiculo);

            String recipientEmail = usuario.getEmail();
            //ENVIO DE EMAIL
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(recipientEmail);
            mailMessage.setSubject("Confirmación de reserva");
            mailMessage.setText("¡Gracias por elegir nuestros servicios de alquiler de vehículos!\n\n"
                    + "Su reserva ha sido confirmada con éxito. A continuación, encontrará los detalles de su reserva:\n\n"

                    + "Detalles de la reserva:\n"
                    + "Producto reservado: " + reserva.getVehiculo().getModelo() + "\n"
                    + "Fecha de inicio de la reserva: " + reserva.getDiaInicio() + "\n"
                    + "Fecha de finalización de la reserva: " + reserva.getDiaFinalizacion() + "\n"
                    + "Precio final: " + reserva.getPrecioFinal() + "\n"
                    + "Método de pago: " + reserva.getMetodoDePago() + "\n"
                    + "Para cualquier consulta o modificación, no dude en ponerse en contacto con nosotros.\n\n"
                    + "Atentamente,\n[WorldRentCar]\n\n"
                    + "Información de contacto del proveedor:\n"
                    + "Nombre del proveedor: [Juan Rodriguez]\n"
                    + "Correo electrónico del proveedor: [worldcartrental@gmail.com]\n"
                    + "Número de teléfono del proveedor: [+543755957416]");
            javaMailSender.send(mailMessage);

            return new ResponseEntity<>(reservaGuardada, HttpStatus.CREATED);
        } catch (BadRequest e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Función para truncar la hora y dejar solo la fecha
    private Date truncateToStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }


    @GetMapping("/reservasPorUsuario/{usuarioId}")
    public ResponseEntity<?> obtenerReservasPorUsuario(@PathVariable Long usuarioId) {
        try {
            List<Reserva> reservas = reservaService.obtenerReservasPorUsuario(usuarioId);
            return new ResponseEntity<>(reservas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/darPuntaje")
    public ResponseEntity<String> darPuntajeAVehiculo(@RequestBody Map<String, String> datos) {




        try {
            String usuarioIdStr = datos.get("usuarioId");
            String vehiculoIdStr = datos.get("vehiculoId");
            String puntajeStr = datos.get("puntaje");



            Long usuarioId = Long.parseLong(usuarioIdStr);
            Long vehiculoId = Long.parseLong(vehiculoIdStr);
            int puntaje = Integer.parseInt(puntajeStr);


            reservaService.darPuntajeAVehiculo(usuarioId, vehiculoId, puntaje);
            return new ResponseEntity<>("Puntaje asignado correctamente", HttpStatus.OK);

        } catch (BadRequest e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
