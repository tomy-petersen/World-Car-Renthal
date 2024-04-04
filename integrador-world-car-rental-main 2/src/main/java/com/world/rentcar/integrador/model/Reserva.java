package com.world.rentcar.integrador.model;

import com.world.rentcar.integrador.enums.MetodoDePago;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;

@Entity
@Table(name = "Reservas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @SequenceGenerator(name = "reserva_sequence", sequenceName = "reserva_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserva_sequence")
    private Long id;

    private Date diaInicio;
    private Date diaFinalizacion;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST )
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST )
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    private Double precioFinal;
    private MetodoDePago metodoDePago;

    public void calcularPrecioFinal() {
        LocalDate fechaInicio = diaInicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaFinalizacion = diaFinalizacion.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        long diferenciaEnDias = ChronoUnit.DAYS.between(fechaInicio, fechaFinalizacion);
        precioFinal = vehiculo.getPrecio() * diferenciaEnDias;
    }
    private boolean politicas = false;

}
