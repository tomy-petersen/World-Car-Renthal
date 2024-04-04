package com.world.rentcar.integrador.modelDTO;

import com.world.rentcar.integrador.enums.UserRol;
import com.world.rentcar.integrador.model.Direccion;

public record UsuarioDTO(Long id, String nombre, String apellido, String dni, UserRol rol, Direccion direccion) {
}
