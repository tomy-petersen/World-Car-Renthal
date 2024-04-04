package com.world.rentcar.integrador.repository;

import com.world.rentcar.integrador.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva,Long> {
    List<Reserva> findByUsuarioId(Long usuarioId);

    @Query( value = "SELECT COUNT(id) FROM reservas where vehiculo_id =:id", nativeQuery = true)
    public  Integer CantidadDeReservasPorVehiculos(Long id);
}
