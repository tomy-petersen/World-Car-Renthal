package com.world.rentcar.integrador.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.*;


@Entity
@Table(name = "Autos")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {
    @Id
    @SequenceGenerator(name = "vehiculo_sequence", sequenceName = "vehiculo_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehiculo_sequence")
    private Long id;

    @Column(nullable = false)
    private String modelo;

    @Column(nullable = false)
    private String marca;

    @Column(nullable = false)
    private Double precio;

    @Column(nullable = false)
    private boolean disponibilidad;

    @Column(nullable = false)
    private String tipo;

    @Column(nullable = false)
    private int pasajeros;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String motor;

    @Column(nullable = false)
    private String cilindrada;

    @Column(nullable = false)
    private String caja;

    @Column(nullable = false, unique = true)
    private String patente;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Puntuacion> puntuaciones;

    private Double califiacion=0.0;

    @ElementCollection
    @CollectionTable(name = "vehiculo_imagenes")
    @Column(name = "imagen")
    private List<String> imagen;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal;  // La sucursal a la que está asociado el vehículo.

    @OneToMany(mappedBy = "vehiculo", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Reserva> reservas = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "vehiculo_id")
    private List<ProductoCaracteristicas> caracteristicasExtras;

    @ElementCollection
    @CollectionTable(name = "vehiculo_fechas_no_disponibles")
    @Column(name = "fecha_no_disponible")
    private List<Date> fechasNoDisponibles = new ArrayList<>();
    public void actualizarFechasNoDisponibles(Reserva reserva) {
        List<Date> rangoReserva = obtenerRangoDeFechas(reserva.getDiaInicio(), reserva.getDiaFinalizacion());
        fechasNoDisponibles.addAll(rangoReserva);
    }

    private List<Date> obtenerRangoDeFechas(Date inicio, Date fin) {
        List<Date> fechas = new ArrayList<>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(inicio);

        while (!calendar.getTime().after(fin)) {
            fechas.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }

        return fechas;
    }


}






