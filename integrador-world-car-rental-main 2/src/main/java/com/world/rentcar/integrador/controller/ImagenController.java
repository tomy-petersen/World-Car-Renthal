package com.world.rentcar.integrador.controller;

import com.world.rentcar.integrador.service.ImagenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/imagen")
public class ImagenController {

    @Autowired
    private ImagenService imagenService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("imagenes") List<MultipartFile> multipartFiles, @RequestParam("id") Long id
                                        ) throws IOException {
        try{
            imagenService.guardarImagen(multipartFiles, id);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok("OK");
    }
}
