package pe.edu.upc.MonolithFoodApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Se ejecuta cuando se envía un JSON con campos vacíos o nulos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ResponseDTO response = new ResponseDTO("Error en el formato de los datos enviados.", 400);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Se ejecuta cuando se envía un JSON con un formato incorrecto
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ResponseDTO response = new ResponseDTO("Los datos enviados no son legibles.", 400);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    // Se ejecuta cuando se intenta acceder a un recurso al que no se tiene permiso
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ResponseDTO> handleAccessDeniedException(AccessDeniedException e) {
        ResponseDTO response = new ResponseDTO("No tienes permiso para acceder a este recurso.", 400);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

}
