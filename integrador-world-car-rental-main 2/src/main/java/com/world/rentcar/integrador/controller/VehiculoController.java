package com.world.rentcar.integrador.controller;

import com.world.rentcar.integrador.exeptions.BadRequest;
import com.world.rentcar.integrador.model.*;
import com.world.rentcar.integrador.service.ReservaService;
import com.world.rentcar.integrador.service.SucursalService;
import com.world.rentcar.integrador.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@Controller
@RequestMapping("/vehiculo")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;
    @Autowired
    private ReservaService reservaService;

    @Autowired
    private SucursalService sucursalService;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarVehiculo(@RequestBody Vehiculo vehiculo) {
        try {
            Long sucursalId = vehiculo.getSucursal().getId();
            Sucursal sucursal = sucursalService.buscarxID(sucursalId);
            vehiculo.setSucursal(sucursal);

            // Llama al método guardar del servicio de vehículos y maneja BadRequest si es necesario
            Vehiculo vehiculoGuardado = vehiculoService.guardar(vehiculo);

            return ResponseEntity.ok(vehiculoGuardado);
        } catch (BadRequest e) {
            // Devuelve una respuesta de error en formato JSON
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<Vehiculo>> buscarVehiculo(@PathVariable Long id)throws BadRequest {
        Optional<Vehiculo> vehiculo = vehiculoService.buscarxID(id);

        return ResponseEntity.ok(vehiculo);
    }


    @PutMapping("/modificar")
    public ResponseEntity<?> actualizarVehiculo(@RequestBody Vehiculo vehiculo)throws BadRequest {
        ResponseEntity<?> response = null;

        if (vehiculo.getId() != null && vehiculoService.buscarxID(vehiculo.getId()).isPresent()) {
            Vehiculo v = vehiculoService.modificar(vehiculo);
           return  new ResponseEntity<>(v, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/agregarCaracteristica")
    public ResponseEntity<Vehiculo> agregarCaracteristica(@RequestBody Vehiculo vehiculo)throws BadRequest {
        ResponseEntity<Vehiculo> response = null;
        Optional<Vehiculo> vehiculoExistenteOptional = vehiculoService.buscarxID(vehiculo.getId());
        Vehiculo vehiculoExistente = vehiculoExistenteOptional.get();

        List<ProductoCaracteristicas> nuevaLista = vehiculo.getCaracteristicasExtras();
        ProductoCaracteristicas primerElemento = nuevaLista.get(0);

        vehiculoExistente.getCaracteristicasExtras().add(primerElemento);



        if (vehiculo.getId() != null && vehiculoService.buscarxID(vehiculo.getId()).isPresent())
            response = ResponseEntity.ok(vehiculoService.modificar(vehiculoExistente));
        else
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        return response;
    }





    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarVehiculo(@PathVariable Long id)throws BadRequest {
        ResponseEntity<String> response = null;

        if (vehiculoService.buscarxID(id).isPresent()) {
            Vehiculo v = vehiculoService.buscarxID(id).get();
            int cantidadReservas = reservaService.cantidadDeReservasPorVehiculo(id);
            if(cantidadReservas > 0){
                return new ResponseEntity<>("No se puede eliminar vehiculo por que tiene reservas disponibles.", HttpStatus.BAD_REQUEST);
            }
            vehiculoService.borrar(id);
            String errorMessage = "Vehiculo con id: " + id+" eliminado";
            return new ResponseEntity<>(errorMessage, HttpStatus.OK);
        } else {
            String errorMessage = "No existe vehiculo con id:"  + id;
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        }

    }




    @GetMapping("/vehiculos")
    public ResponseEntity<ArrayList<Vehiculo>> listarVehiculos(){
        return ResponseEntity.ok((ArrayList<Vehiculo>) vehiculoService.listarVehiculos());
    };

    @GetMapping("/buscarPorMarca/{marca}")
    public ResponseEntity<?> buscarPorMarca(@PathVariable String marca) throws BadRequest {
        List<Vehiculo> vehiculos = vehiculoService.buscarPorMarca(marca);

        if (vehiculos.isEmpty()) {
            String errorMessage = "No se encontraron vehículos de la marca: " + marca;
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(vehiculos);
        }
    }
    @GetMapping("/buscarPorTipo/{tipo}")
    public ResponseEntity<?> buscarPorTipo(@PathVariable String tipo) throws BadRequest {
        List<Vehiculo> vehiculos = vehiculoService.buscarPorTipo (tipo);

        if (vehiculos.isEmpty()) {
            String errorMessage = "No se encontraron " + tipo+"s";
            return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(vehiculos);
        }
    }
    @GetMapping("/disponibles")
    public ResponseEntity<?> buscarVehiculosDisponibles(@RequestBody Map<String, String> datos) {
        try {
            String fechaInicioStr = datos.get("fechaInicio");
            String fechaFinStr = datos.get("fechaFin");
            String idSucursalStr = datos.get("idSucursal");

            if (fechaInicioStr == null || fechaFinStr == null) {
                throw new BadRequest("Las fechas de inicio y fin son obligatorias.");
            }

            LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
            LocalDate fechaFin = LocalDate.parse(fechaFinStr);
            Long idSucursal = Long.parseLong(idSucursalStr);

            // Lógica para buscar vehículos disponibles en el rango de fechas
            List<Vehiculo> vehiculosDisponibles = vehiculoService.buscarVehiculosDisponibles(fechaInicio, fechaFin,idSucursal);
            return new ResponseEntity<>(vehiculosDisponibles, HttpStatus.OK);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>("Formato de fecha incorrecto. Usa el formato ISO_DATE.", HttpStatus.BAD_REQUEST);
        } catch (BadRequest e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/PuntuacionPorVehiuclo/{vehiculoId}")
    public ResponseEntity<?> obtenerPuntuacionPorVehiuclo(@PathVariable Long vehiculoId) {
        try {
            List<Puntuacion> puntuacion = vehiculoService.obtenerPuntuacionPorVehiculo(vehiculoId);
            return new ResponseEntity<>(puntuacion, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}



