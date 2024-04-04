package com.world.rentcar.integrador.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name="Direcciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {
    @Id
    @SequenceGenerator(name="direccion_sequence", sequenceName = "direccion_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "direccion_sequence")
    private Long id;
    @Column(nullable = false)
    private String pais;
    @Column(nullable = false)
    private String calle;
    @Column(nullable = false)
    private int numero;
    @Column(nullable = false)
    private String localidad;
    @Column(nullable = false)
    private String provincia;


    public Direccion(String pais,String calle, int numero, String localidad, String provincia) {
        this.pais = pais;
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

}
