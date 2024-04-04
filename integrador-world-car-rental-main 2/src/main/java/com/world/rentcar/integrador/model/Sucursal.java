package com.world.rentcar.integrador.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Sucursales")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sucursal {
    @Id
    @SequenceGenerator(name = "sucursal_sequence", sequenceName = "sucursal_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sucursal_sequence")
    private Long id;
    private String nombre;
    @OneToMany(mappedBy = "sucursal", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Vehiculo> vehiculos = new HashSet<>();



    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id")
    private Direccion direccion;

}
