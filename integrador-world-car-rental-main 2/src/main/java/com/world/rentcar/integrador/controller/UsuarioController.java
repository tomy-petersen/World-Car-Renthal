package com.world.rentcar.integrador.controller;

import com.world.rentcar.integrador.enums.UserRol;
import com.world.rentcar.integrador.exeptions.BadRequest;
import com.world.rentcar.integrador.model.Reserva;
import com.world.rentcar.integrador.model.Sucursal;
import com.world.rentcar.integrador.model.Usuario;
import com.world.rentcar.integrador.model.Vehiculo;
import com.world.rentcar.integrador.modelDTO.UsuarioDTO;
import com.world.rentcar.integrador.repository.UsuarioRepository;
import com.world.rentcar.integrador.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    JavaMailSender javaMailSender;

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) throws BadRequest {try {


        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
        usuario.setRol(UserRol.USER);
        ResponseEntity.ok(usuarioService.guardar(usuario));
        String recipientEmail = usuario.getEmail();
        //ENVIO DE EMAIL
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientEmail);
        mailMessage.setSubject("Confirmación de registro");
        mailMessage.setText("¡Bienvenido a nuestra plataforma!\n\n"
                + "Su cuenta ha sido creada exitosamente. Ahora puede iniciar sesión en su cuenta recién creada.\n\n"
                + "Nombre de Usuario: " + usuario.getUsuario() + "\n"
                + "Dirección de Correo Electrónico: " + usuario.getEmail() + "\n\n"
                + "Para loguearse, haga clic en el siguiente enlace o cópielo y péguelo en la barra de direcciones de su navegador:\n"
                + "[www.worldrentcar.com.ar]\n\n"
                + "Gracias por unirse a nosotros y esperamos que disfrute de nuestros servicios.\n\n"
                + "Atentamente,\n[WorldRentCar]");
        javaMailSender.send(mailMessage);


        return ResponseEntity.status(HttpStatus.OK).body("Usuario creado correctamente");
    } catch (BadRequest e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/usuarios")
    public ResponseEntity<ArrayList<Usuario>> listarUsuarios(){
        return ResponseEntity.ok((ArrayList<Usuario>) usuarioService.listarUsuarios());
    }
    @PutMapping("/modificar")
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) throws BadRequest {
        ResponseEntity<Usuario> response = null;

        if (usuario.getId() != null && usuarioService.buscarxID(usuario.getId()).isPresent()) {
            // Obtén el usuario existente de la base de datos
            Optional<Usuario> usuarioExistenteOptional = usuarioService.buscarxID(usuario.getId());

            if (usuarioExistenteOptional.isPresent()) {
                Usuario usuarioExistente = usuarioExistenteOptional.get();

                if (usuario.getNombre() != null) {
                    usuarioExistente.setNombre(usuario.getNombre());
                }
                if (usuario.getApellido() != null) {
                    usuarioExistente.setApellido(usuario.getApellido());
                }
                if (usuario.getDni() != null) {
                    usuarioExistente.setDni(usuario.getDni());
                }
                if (usuario.getTelefono() != null) {
                    usuarioExistente.setTelefono(usuario.getTelefono());
                }
                if (usuario.getUsuario() != null) {
                    usuarioExistente.setUsuario(usuario.getUsuario());
                }
                if (usuario.getEmail() != null) {
                    usuarioExistente.setEmail(usuario.getEmail());
                }
                if (usuario.getContraseña() != null) {

                    usuarioExistente.setContraseña(passwordEncoder.encode(usuario.getContraseña()));
                }

                    usuarioExistente.setRol(UserRol.USER);
                    usuarioExistente.setReservas(usuario.getReservas());

                response = ResponseEntity.ok(usuarioService.modificar(usuarioExistente));
            }
        } else {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return response;
    }


    @PutMapping("/{userId}/modificar-rol")
    public ResponseEntity<String> modificarRol(
            @PathVariable Long userId,
            @RequestBody Map<String, String> requestBody) {
        try {
            String nuevoRol = requestBody.get("rol");
            usuarioService.modificarRol(userId, nuevoRol);
            return new ResponseEntity<>("Rol modificado correctamente", HttpStatus.OK);
        } catch (BadRequest e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Optional<UsuarioDTO>> buscarUsuarioPorId(@PathVariable Long id)throws BadRequest {
        Optional<UsuarioDTO> usuario = Optional.ofNullable(usuarioService.usuarioDTOxId(id));


        return ResponseEntity.ok(usuario);
    }
    @GetMapping("/buscarEmail")
    public ResponseEntity<Optional<UsuarioDTO>> buscarUsuarioPorEmail(@RequestParam("email") String email) throws BadRequest {
        Optional<UsuarioDTO> usuario = usuarioService.buscarxEmail(email);
        return ResponseEntity.ok(usuario);
    }

    @GetMapping("/usuariosDTO")
    public ResponseEntity<ArrayList<UsuarioDTO>> listarUsuariosDTO(){
        return ResponseEntity.ok((ArrayList<UsuarioDTO>) usuarioService.listarUsuariosDTO());
    }




    @GetMapping("/{usuarioId}/reservas")
    public ResponseEntity<?> obtenerReservasPorUsuario(@PathVariable Long usuarioId) {
        try {
            // Obtener el usuario por ID
            Usuario usuario = usuarioService.buscarxID(usuarioId)
                    .orElseThrow(() -> new BadRequest("No se encontró el usuario con ID: " + usuarioId));

            // Obtener las reservas asociadas al usuario
            List<Reserva> reservas = new ArrayList<>(usuario.getReservas());

            return new ResponseEntity<>(reservas, HttpStatus.OK);
        } catch (BadRequest e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /*@PostMapping("/{userId}/vehiculoAddFav")
    public ResponseEntity<String> agregarAFavoritos(
            @PathVariable Long userId,
            @RequestBody Map<String, Long> requestBody) {
        try {
            Long vehiculoId = requestBody.get("vehiculoId");


            // Agregar vehículo a favoritos
            usuarioService.agregarAFavoritos(userId, vehiculoId);

            return ResponseEntity.ok("Vehículo agregado a favoritos exitosamente.");
        } catch (BadRequest e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado.", e);
        }
    }

    @DeleteMapping("/{userId}/vehiculoDeleteFav")
    public ResponseEntity<String> eliminarDeFavoritos(
            @PathVariable Long userId,
            @RequestBody Map<String, Long> requestBody) {
        try {
            Long vehiculoId = requestBody.get("vehiculoId");
            usuarioService.eliminarDeFavoritos(userId, vehiculoId);
            return ResponseEntity.ok("Vehículo eliminado de favoritos exitosamente.");
        } catch (BadRequest e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado.", e);
        }
    }*/
    @PostMapping("/{userId}/vehiculoAddFav")
    public ResponseEntity<String> gestionarFavoritos(
            @PathVariable Long userId,
            @RequestBody Map<String, Long> requestBody) {
        try {
            Long vehiculoId = requestBody.get("vehiculoId");
            usuarioService.gestionarFavoritos(userId, vehiculoId);
            return ResponseEntity.ok("Operación de favoritos realizada con éxito.");
        } catch (BadRequest e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error inesperado. Detalles: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}/vehiculosFavoritos")
    public ResponseEntity<List<Vehiculo>> listarVehiculosFavoritos(
            @PathVariable Long userId) {
        try {
            List<Vehiculo> vehiculosFavoritos = usuarioService.listarVehiculosFavoritos(userId);
            return ResponseEntity.ok(vehiculosFavoritos);
        } catch (BadRequest e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}


