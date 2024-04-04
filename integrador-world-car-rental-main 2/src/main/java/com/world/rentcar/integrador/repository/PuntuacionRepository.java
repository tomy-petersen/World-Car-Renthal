package com.world.rentcar.integrador.repository;

import com.world.rentcar.integrador.model.Puntuacion;
import com.world.rentcar.integrador.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PuntuacionRepository extends JpaRepository<Puntuacion,Long> {
    @Query("SELECT p FROM Puntuacion p WHERE p.vehiculo.id = :vehiculoId")
    List<Puntuacion> findByVehiculoId(@Param("vehiculoId") Long vehiculoId);
}
