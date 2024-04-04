package com.world.rentcar.integrador.service;

import com.world.rentcar.integrador.exeptions.BadRequest;
import com.world.rentcar.integrador.model.Puntuacion;
import com.world.rentcar.integrador.model.Reserva;
import com.world.rentcar.integrador.model.Vehiculo;
import com.world.rentcar.integrador.repository.PuntuacionRepository;
import com.world.rentcar.integrador.repository.ReservaRepository;
import com.world.rentcar.integrador.repository.UsuarioRepository;
import com.world.rentcar.integrador.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PuntuacionRepository puntuacionRepository;

    public void calcularPrecioFinal(Reserva reserva) {
        LocalDate fechaInicio = reserva.getDiaInicio().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaFinalizacion = reserva.getDiaFinalizacion().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long diferenciaEnDias = ChronoUnit.DAYS.between(fechaInicio, fechaFinalizacion);
        Double dias = (double) diferenciaEnDias;
        Vehiculo vehiculo = vehiculoRepository.getReferenceById(reserva.getVehiculo().getId());
        reserva.setPrecioFinal(vehiculo.getPrecio() * dias);
    }

    public Optional<Reserva> buscarPorId(Long id) throws BadRequest {
        if (reservaRepository.findById(id).isEmpty()) {
            throw new BadRequest("No existe una reserva con id: " + id);
        }
        return reservaRepository.findById(id);
    }

    public Reserva realizarReserva(Reserva reserva) throws BadRequest {
        calcularPrecioFinal(reserva);  // Llamar al método antes de guardar
        reservaRepository.save(reserva);
        return reserva;
    }

    public void borrar(Long id) throws BadRequest {
        reservaRepository.deleteById(id);
    }

    public List<Reserva> listarTodos() throws BadRequest {
        return reservaRepository.findAll();
    }

    public List<Date> obtenerFechasEntre(Date fechaInicio, Date fechaFin) {
        List<Date> fechasIntermedias = new ArrayList<>();

        Date fechaActual = fechaInicio;
        while (!fechaActual.after(fechaFin)) {
            fechasIntermedias.add(fechaActual);
            fechaActual = sumarDias(fechaActual, 1);
        }

        return fechasIntermedias;
    }

    private Date sumarDias(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

    public boolean existenReservasEnFechas(Date fechaInicio, Date fechaFin, Long vehiculoId) throws BadRequest {
        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId).orElse(null);
        if (vehiculo == null) {
            throw new BadRequest("No se encontró el vehículo con ID: " + vehiculoId);
        }

        List<Date> fechasReservadas = vehiculo.getFechasNoDisponibles();
        List<Date> fechasNuevas = obtenerFechasEntre(fechaInicio, fechaFin);

        // Verificar si alguna de las nuevas fechas coincide con las ya reservadas
        return fechasNuevas.stream().anyMatch(fechaReserva -> fechasReservadas.contains(fechaReserva));
    }

    public List<Reserva> obtenerReservasPorUsuario(Long usuarioId) {
        System.out.println("god1");
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    public void darPuntajeAVehiculo(Long usuarioId, Long vehiculoId, int puntaje) throws BadRequest {
        List<Reserva> reservas = obtenerReservasPorUsuario(usuarioId);

        // Verificar si hay alguna reserva con el vehículoId específico
        boolean vehiculoReservadoPorUsuario = reservas.stream()
                .anyMatch(reserva -> reserva.getVehiculo() != null && reserva.getVehiculo().getId().equals(vehiculoId));

        if (vehiculoReservadoPorUsuario) {

            // Validar el puntaje
            if (puntaje < 1 || puntaje > 5) {
                throw new BadRequest("El puntaje debe estar entre 1 y 5");
            }

            // Aquí puedes continuar con la lógica para dar puntaje al vehículo
            Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                    .orElseThrow(() -> new BadRequest("No existe un vehículo con id: " + vehiculoId));

            // Verificar si el usuario ya puntuó este vehículo
            Optional<Puntuacion> puntuacionExistente = vehiculo.getPuntuaciones().stream()
                    .filter(puntuacion -> puntuacion.getUsuario().getId().equals(usuarioId))
                    .findFirst();
            if (puntuacionExistente.isPresent()) {

                Puntuacion nuevaPuntuacion = puntuacionExistente.get();
                nuevaPuntuacion.setPuntaje(puntaje);
                try {
                    puntuacionRepository.save(nuevaPuntuacion);
                    vehiculo.getPuntuaciones().add(nuevaPuntuacion);
                    vehiculo.setCalifiacion(vehiculoService.calcularPromedioPuntuacionPorVehiculo(vehiculoId));
                    vehiculoRepository.save(vehiculo);

                } catch (Exception e) {
                    // Manejar la excepción de base de datos, podrías lanzar una excepción personalizada si es necesario
                    throw new RuntimeException("Error al guardar el vehículo", e);
                }
            } else{



            // Crear la nueva puntuación y agregarla a la lista de puntuaciones del vehículo
            Puntuacion nuevaPuntuacion = new Puntuacion();
            nuevaPuntuacion.setUsuario(usuarioRepository.findById(usuarioId)
                    .orElseThrow(() -> new BadRequest("No existe un usuario con id: " + usuarioId)));
            nuevaPuntuacion.setVehiculo(vehiculoRepository.findById(vehiculoId)
                    .orElseThrow(() -> new BadRequest("No existe un usuario con id: " + usuarioId)));
            nuevaPuntuacion.setPuntaje(puntaje);


            // Guardar el vehículo dentro de una transacción
            try {
                puntuacionRepository.save(nuevaPuntuacion);
                vehiculo.getPuntuaciones().add(nuevaPuntuacion);
                vehiculo.setCalifiacion(vehiculoService.calcularPromedioPuntuacionPorVehiculo(vehiculoId));
                vehiculoRepository.save(vehiculo);
            } catch (Exception e) {
                // Manejar la excepción de base de datos, podrías lanzar una excepción personalizada si es necesario
                throw new RuntimeException("Error al guardar el vehículo", e);
            }

        }} else {
            throw new BadRequest("El usuario con id " + usuarioId + " no ha reservado el vehículo con id " + vehiculoId);
        }
    }

    public Integer cantidadDeReservasPorVehiculo(Long id){
        return reservaRepository.CantidadDeReservasPorVehiculos(id);
    }
}

