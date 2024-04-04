package com.world.rentcar.integrador.service;


import com.world.rentcar.integrador.enums.UserRol;
import com.world.rentcar.integrador.exeptions.BadRequest;
import com.world.rentcar.integrador.model.Usuario;
import com.world.rentcar.integrador.model.Vehiculo;
import com.world.rentcar.integrador.modelDTO.UsuarioDTO;
import com.world.rentcar.integrador.repository.UsuarioRepository;
import com.world.rentcar.integrador.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public UsuarioDTO usuarioDTOxId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No existe un usuario con id: " + id));

        return new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getDni(), usuario.getRol(), usuario.getDireccion());
    }

    public Optional<Usuario> buscarxID(Long id) throws BadRequest {
        if (usuarioRepository.findById(id).isEmpty()) {
            throw new BadRequest("No existe un usuario con id: " + id);
        }
        return usuarioRepository.findById(id);
    }

    public List<UsuarioDTO> listarUsuariosDTO() {
        List<Usuario> usuarios = (List<Usuario>) usuarioRepository.findAll();

        List<UsuarioDTO> usuariosDTO = usuarios.stream()
                .map(usuario -> {
                    UsuarioDTO usuarioDTO = new UsuarioDTO(usuario.getId(), usuario.getNombre(), usuario.getApellido(), usuario.getDni(), usuario.getRol(), usuario.getDireccion());
                    return usuarioDTO;
                })
                .collect(Collectors.toList());
        return usuariosDTO;
    }

    public Usuario guardar(Usuario usuario) throws BadRequest {
        List<String> errores = new ArrayList<>();
        Optional<Usuario> usuarioExistentePorUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

        if (usuarioExistentePorUsuario.isPresent() && !usuarioExistentePorUsuario.get().getId().equals(usuario.getId())) {
            errores.add("Nombre de usuario ya en uso");
        }


        Optional<Usuario> usuarioExistentePorEmail = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistentePorEmail.isPresent() && !usuarioExistentePorEmail.get().getId().equals(usuario.getId())) {
            errores.add("Correo electrónico ya en uso");
        }
        if (!errores.isEmpty()) {
            throw new BadRequest("" + errores);
        }

        return usuarioRepository.save(usuario);
    }


    public void borrar(Long id) {
        //logger.info("paciente id. "+ id +" borrado");
        usuarioRepository.deleteById(id);
    }

    public Usuario modificar(Usuario usuario) throws BadRequest {
        //logger.info("paciente id. "+ paciente.getId() +" modificado");
        List<String> errores = new ArrayList<>();
        Optional<Usuario> usuarioExistentePorUsuario = usuarioRepository.findByUsuario(usuario.getUsuario());

        if (usuarioExistentePorUsuario.isPresent() && !usuarioExistentePorUsuario.get().getId().equals(usuario.getId())) {
            errores.add("Nombre de usuario ya en uso");
        }


        Optional<Usuario> usuarioExistentePorEmail = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioExistentePorEmail.isPresent() && !usuarioExistentePorEmail.get().getId().equals(usuario.getId())) {
            errores.add("Correo electrónico ya en uso");
        }
        if (!errores.isEmpty()) {
            throw new BadRequest("" + errores);
        }
        usuarioRepository.save(usuario);
        return usuario;
    }

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    ;

    public Usuario buscarPorEmail(String email) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByEmail(email);
        return optionalUsuario.orElse(null); // Devuelve el Usuario si está presente o null si no lo está
    }


    public void modificarRol(Long userId, String nuevoRol) throws BadRequest {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(userId);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setRol(UserRol.valueOf(nuevoRol)); // Actualiza el rol del usuario
            usuarioRepository.save(usuario); // Guarda el usuario modificado en la base de datos
        } else {
            // Manejo de error si el usuario no se encuentra
            throw new BadRequest("Usuario no encontrado con ID: " + userId);
        }


    }

    public Optional<UsuarioDTO> buscarxEmail(String email) throws BadRequest {
        if (usuarioRepository.findByEmail(email).isEmpty()) {
            throw new BadRequest("No existe un usuario con email: " + email);
        }
         Usuario usuario = usuarioRepository.findByEmail(email).get();
        Optional<UsuarioDTO> usuarioDTO = Optional.of(new UsuarioDTO(usuario.getId(), usuario.getUsuario(), usuario.getApellido(), usuario.getDni(), usuario.getRol(), usuario.getDireccion()));
            return usuarioDTO;

    }




    public void gestionarFavoritos(Long userId, Long vehiculoId) throws BadRequest {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new BadRequest("No existe un usuario con id: " + userId));

        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new BadRequest("No existe un vehículo con id: " + vehiculoId));

        List<Vehiculo> vehiculosFavoritos = usuario.getVehiculosFavoritos();

        if (vehiculosFavoritos == null) {
            throw new BadRequest("La lista de vehículos favoritos es nula para el usuario con id: " + userId);
        }

        if (vehiculosFavoritos.contains(vehiculo)) {
            // Si el vehículo ya está en la lista, eliminarlo
            vehiculosFavoritos.removeIf(v -> v.getId().equals(vehiculoId));
        } else {
            // Si el vehículo no está en la lista, agregarlo
            vehiculosFavoritos.add(vehiculo);
        }

        usuarioRepository.save(usuario);
    }

    public List<Vehiculo> listarVehiculosFavoritos(Long userId) throws BadRequest {
        Usuario usuario = usuarioRepository.findById(userId)
                .orElseThrow(() -> new BadRequest("No existe un usuario con id: " + userId));

        List<Vehiculo> vehiculosFavoritos = usuario.getVehiculosFavoritos();
        if (vehiculosFavoritos == null) {
            throw new BadRequest("La lista de vehículos favoritos es nula para el usuario con id: " + userId);
        }

        return vehiculosFavoritos;
    }


}
