package com.world.rentcar.integrador.repository;

import com.world.rentcar.integrador.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo,Long> {
    @Query("SELECT o FROM Vehiculo o WHERE UPPER(o.marca) LIKE UPPER(concat('%', :marca, '%'))")
    List<Vehiculo> buscarPorMarca(@Param("marca") String marca);

    List<Vehiculo> findByTipoIgnoreCaseContaining(String tipo);

}
