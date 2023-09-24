package pe.edu.upc.MonolithFoodApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Se ejecuta cuando se envía un JSON con campos vacíos o nulos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ResponseDTO response = new ResponseDTO("Error en el formato de los datos enviados (1).", 400);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    // Se ejecuta cuando se envía un JSON con un formato incorrecto
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ResponseDTO response = new ResponseDTO("Error en el formato de los datos enviados (2).", 400);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
