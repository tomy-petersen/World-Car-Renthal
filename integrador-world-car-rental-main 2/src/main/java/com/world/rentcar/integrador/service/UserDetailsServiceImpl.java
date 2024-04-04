package com.world.rentcar.integrador.service;

import com.world.rentcar.integrador.model.Usuario;
import com.world.rentcar.integrador.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    // Crear una colección para roles
    Collection<GrantedAuthority> authorities = new ArrayList<>();



    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario user = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario con email:"+email+" no existe."));

        // Agregar roles "ADMIN" y "USER" a la colección
        // authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        authorities.clear();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRol()));

        return new User(user.getEmail(),user.getContraseña(),true,true,true,true,authorities);

    }
}
