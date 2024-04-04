package com.world.rentcar.integrador.security;


import com.world.rentcar.integrador.security.filters.JwtAuthenticationFilter;
import com.world.rentcar.integrador.security.filters.JwtAuthorizationFilter;
import com.world.rentcar.integrador.security.jwt.JwtUtils;
import com.world.rentcar.integrador.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import java.beans.Encoder;
import java.util.Base64;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration

public class SecurityConfig {
    @Autowired
    UserDetailsServiceImpl userDetailsServiceImpl;
    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    JwtAuthorizationFilter authorizationFilter;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {

        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);
        jwtAuthenticationFilter.setFilterProcessesUrl("/login");


        return httpSecurity
                .csrf(config -> config.disable())
                .authorizeHttpRequests(auth -> {
                    //VEHICULOS
                    auth.requestMatchers("vehiculo/vehiculos","/demo/**","imagen/upload").permitAll();
                    auth.requestMatchers("vehiculo/vehiculos","/demo/**").permitAll();
                    auth.requestMatchers("vehiculo/buscar/{id}").permitAll();
                    auth.requestMatchers("vehiculo/buscarPorMarca/{marca}").permitAll();
                    auth.requestMatchers("vehiculo/buscarPorTipo/{tipo}").permitAll();
                    auth.requestMatchers("vehiculo/disponibles").permitAll();
                    auth.requestMatchers("vehiculo/modificar").hasRole("ADMIN");
                    auth.requestMatchers("vehiculo/agregarCaracteristica").hasRole("ADMIN");
                    auth.requestMatchers("vehiculo/registrar").hasRole("ADMIN");
                    auth.requestMatchers("vehiculo/eliminar/{id}").hasRole("ADMIN");

                    //USUARIO
                    auth.requestMatchers("usuario/registrar").permitAll();
                    auth.requestMatchers("usuario/{userId}/modificar-rol").hasRole("ADMIN");
                    //SUCURSAL
                    auth.requestMatchers("sucursal/sucursales").permitAll();
                    auth.requestMatchers("sucursal/registrar").hasRole("ADMIN");
                    //RESERVAS
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return  httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder)
                .and().build();

    }


}




