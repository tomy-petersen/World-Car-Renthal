package com.world.rentcar.integrador.service;

import com.world.rentcar.integrador.exeptions.BadRequest;
import com.world.rentcar.integrador.model.Sucursal;
import com.world.rentcar.integrador.model.Vehiculo;
import com.world.rentcar.integrador.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SucursalService {
    @Autowired
    private SucursalRepository sucursalRepository;

    public List<Sucursal> sucursales(){
        return sucursalRepository.findAll();
    }
    public Sucursal buscarxID(Long id) throws BadRequest {
        if (sucursalRepository.findById(id).isEmpty()){
            throw  new BadRequest("No existe un sucursal con id: "+id);
        } return sucursalRepository.getReferenceById(id);
    }
    public Sucursal agregarVehiculoSucursal(Long id, Vehiculo vehiculo){
        sucursalRepository.getReferenceById(id).getVehiculos().add(vehiculo);
        return sucursalRepository.getReferenceById(id);
    }
    public Sucursal guardar(Sucursal sucursal) {
        //logger.info("sucursal registrada");
        return sucursalRepository.save(sucursal);
    }

    public List<Sucursal> listarSucursales(){
        return sucursalRepository.findAll();
    };

}
