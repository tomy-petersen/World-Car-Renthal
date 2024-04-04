package com.world.rentcar.integrador.security.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.world.rentcar.integrador.model.Usuario;
import com.world.rentcar.integrador.security.jwt.JwtUtils;
import com.world.rentcar.integrador.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtUtils jwtUtils;


    public JwtAuthenticationFilter(JwtUtils jwtUtils){
        this.jwtUtils =jwtUtils;
    }





    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        Usuario user;
        String usuario;
        String contraseña;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            usuario = user.getUsuario();
            contraseña = user.getContraseña();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al leer la solicitud JSON", e);
        } catch (IOException e) {
            throw new RuntimeException("Error de E/S al leer la solicitud", e);
        }

        if (usuario == null || contraseña == null) {
            throw new RuntimeException("Usuario o contraseña nulos en la solicitud");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(usuario, contraseña);

        return getAuthenticationManager().authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String token = jwtUtils.generateAccesToken(user.getUsername());

        // Agregar el token al encabezado de autorización
        response.addHeader("Authorization", "Bearer " + token);

        // Crear una respuesta JSON con el token y otros detalles
        Map<String, Object> jsonResponse = new HashMap<>();
        jsonResponse.put("token", token);
        jsonResponse.put("message", "Autenticación Correcta");
        jsonResponse.put("usuario", user.getUsername());


        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.OK.value());

        // Escribir la respuesta JSON en el cuerpo de la respuesta
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(jsonResponse));
    }
}
