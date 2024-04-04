package com.world.rentcar.integrador.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Puntuaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Puntuacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id") // Asegúrate de ajustar el nombre de la columna según tu esquema de base de datos
    private Vehiculo vehiculo;

    private int puntaje;

}
