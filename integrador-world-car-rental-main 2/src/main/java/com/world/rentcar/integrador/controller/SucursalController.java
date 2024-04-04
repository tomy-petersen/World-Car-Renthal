package com.world.rentcar.integrador.controller;

import com.world.rentcar.integrador.model.Sucursal;
import com.world.rentcar.integrador.model.Vehiculo;
import com.world.rentcar.integrador.service.SucursalService;
import com.world.rentcar.integrador.service.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/sucursal")
public class SucursalController {
    @Autowired
    private SucursalService sucursalService;
    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping("/sucursales")
    public ResponseEntity<ArrayList<Sucursal>> listarSucursal(){
        return ResponseEntity.ok((ArrayList<Sucursal>) sucursalService.listarSucursales());
    };


    @PostMapping("/registrar")
    public ResponseEntity<Sucursal> registrarSucursal(@RequestBody Sucursal sucursal) {

        return ResponseEntity.ok(sucursalService.guardar(sucursal));

    }

}
