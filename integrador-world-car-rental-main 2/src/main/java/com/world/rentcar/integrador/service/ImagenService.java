package com.world.rentcar.integrador.service;

import com.cloudinary.Cloudinary;
import com.world.rentcar.integrador.exeptions.BadRequest;
import com.world.rentcar.integrador.model.Vehiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ImagenService {
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private VehiculoService vehiculoService;

    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return cloudinary.uploader()
                .upload(multipartFile.getBytes(),
                        Map.of("public_id", UUID.randomUUID().toString()))
                .get("url")
                .toString();
    }

    public void guardarImagen(List<MultipartFile> imagenes, Long id) throws BadRequest, IOException {
        Vehiculo vehiculo = vehiculoService.buscarxID(id).get();
        for(MultipartFile m :
             imagenes) {
            vehiculo.getImagen().add(uploadFile(m));

        }

        vehiculoService.guardarImagen(vehiculo);
    }
}
