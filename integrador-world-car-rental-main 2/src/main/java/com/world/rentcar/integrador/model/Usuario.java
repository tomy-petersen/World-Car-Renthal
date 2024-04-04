package com.world.rentcar.integrador.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.world.rentcar.integrador.enums.UserRol;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
    @Id
    @SequenceGenerator(name = "user_sequence",sequenceName = "user_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private String dni;
    @Column(nullable = false)
    private String telefono;
    @Column(nullable = false)
    private String usuario;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String contraseña;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRol rol;


    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "direccion_id", referencedColumnName = "id")
    private Direccion direccion;

    @Column(nullable = true)
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Reserva> reservas= new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "usuarios_vehiculos_favoritos",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "vehiculo_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "vehiculo_id"})
    )
    private List<Vehiculo> vehiculosFavoritos;
}
