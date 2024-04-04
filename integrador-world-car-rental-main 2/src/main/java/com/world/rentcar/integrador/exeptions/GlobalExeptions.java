package com.world.rentcar.integrador.exeptions;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;

@ControllerAdvice
public class GlobalExeptions {
    @ExceptionHandler({BadRequest.class})
    public ResponseEntity<String> procesarErrorBadRequest(HttpClientErrorException.BadRequest ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
