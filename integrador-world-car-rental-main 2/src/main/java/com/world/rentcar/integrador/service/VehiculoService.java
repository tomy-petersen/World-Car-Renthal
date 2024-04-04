package com.world.rentcar.integrador.service;

import com.world.rentcar.integrador.exeptions.BadRequest;
import com.world.rentcar.integrador.model.ProductoCaracteristicas;
import com.world.rentcar.integrador.model.Puntuacion;
import com.world.rentcar.integrador.model.Reserva;
import com.world.rentcar.integrador.model.Vehiculo;
import com.world.rentcar.integrador.repository.PuntuacionRepository;
import com.world.rentcar.integrador.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private PuntuacionRepository puntuacionRepository;

    public Optional<Vehiculo> buscarxID(Long id) throws BadRequest
    {
        if(vehiculoRepository.findById(id).isEmpty()){
            throw  new BadRequest("No existe un vehiculo con id: "+id);
        }
        return vehiculoRepository.findById(id);
    }


    public Vehiculo guardar(Vehiculo vehiculo) throws BadRequest {
        // Obtener todos los vehículos de la base de datos
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();


        boolean patenteRepetida = vehiculos.stream()
                .anyMatch(vehiculomap -> vehiculomap.getPatente().equals(vehiculo.getPatente()));

        if (patenteRepetida) {

            throw new BadRequest("Ya existe un vehículo con la patente: " + vehiculo.getPatente());
        }


        return vehiculoRepository.save(vehiculo);
    }

    @Transactional
    public Vehiculo modificar(Vehiculo vehiculo)throws BadRequest{
        Vehiculo v = buscarxID(vehiculo.getId()).get();
        v.setModelo(vehiculo.getModelo());
        v.setMarca(vehiculo.getMarca());
        v.setPrecio(vehiculo.getPrecio());
        v.setDisponibilidad(vehiculo.isDisponibilidad());
        v.setTipo(vehiculo.getTipo());
        v.setPasajeros(vehiculo.getPasajeros());
        v.setDescripcion(vehiculo.getDescripcion());
        v.setMotor(vehiculo.getMotor());
        v.setCilindrada(vehiculo.getCilindrada());
        v.setCaja(vehiculo.getCaja());
        v.setPatente(vehiculo.getPatente());
        return vehiculoRepository.save(v);

    }
    public void borrar(Long id) {
        //logger.info("vehiculo id. "+ id +" borrado");
        vehiculoRepository.deleteById(id);
    }
    public List<Vehiculo> listarVehiculos(){
        return vehiculoRepository.findAll();
    };
    public List<Vehiculo> buscarPorMarca(String marca){
        List<Vehiculo> vehiculos = vehiculoRepository.buscarPorMarca(marca);

        return vehiculos;
    };
    public List<Vehiculo> buscarPorTipo(String tipo){
        return vehiculoRepository.findByTipoIgnoreCaseContaining(tipo);

    }
    public List<Vehiculo> buscarVehiculosDisponibles(LocalDate fechaInicio, LocalDate fechaFin, Long idSucursal) {
        // Obtener todos los vehículos
        List<Vehiculo> todosLosVehiculos = vehiculoRepository.findAll();

        // Filtrar vehículos que no tienen fechas no disponibles en el rango de fechas
        return todosLosVehiculos.stream()
                .filter(vehiculo -> !existenFechasNoDisponiblesEnRango(fechaInicio, fechaFin, vehiculo.getFechasNoDisponibles()))
                .filter(vehiculo -> vehiculo.getSucursal().getId() == idSucursal) // Agregar filtro por ID de sucursal
                .collect(Collectors.toList());
    }

    private boolean existenFechasNoDisponiblesEnRango(LocalDate fechaInicio, LocalDate fechaFin, List<Date> fechasNoDisponibles) {
        List<LocalDate> fechasNoDisponiblesLocales = fechasNoDisponibles.stream()
                .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .collect(Collectors.toList());

        return fechasNoDisponiblesLocales.stream()
                .anyMatch(fechaNoDisponible -> fechaNoDisponible.isAfter(fechaInicio.minusDays(1))
                        && fechaNoDisponible.isBefore(fechaFin.plusDays(1)));
    }


    public List<Puntuacion> obtenerPuntuacionPorVehiculo(Long vehiucloId) {

        return puntuacionRepository.findByVehiculoId(vehiucloId);
    }


    public double calcularPromedioPuntuacionPorVehiculo(Long vehiculoId) {
        List<Puntuacion> puntuaciones = obtenerPuntuacionPorVehiculo(vehiculoId);
        if (puntuaciones.isEmpty()) {
            return 0.0; // o algún valor predeterminado si la lista está vacía
        }

        double totalPuntuaciones = 0.0;

        for (Puntuacion puntuacion : puntuaciones) {
            totalPuntuaciones += puntuacion.getPuntaje();
        }

        int numeroPuntuaciones = puntuaciones.size();

        return totalPuntuaciones / numeroPuntuaciones;
    }
    @Transactional
    public void guardarImagen(Vehiculo vehiculo){
        vehiculoRepository.save(vehiculo);
    }
}



