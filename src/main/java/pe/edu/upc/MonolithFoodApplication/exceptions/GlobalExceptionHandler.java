package pe.edu.upc.MonolithFoodApplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Se ejecuta cuando se envía un JSON con campos vacíos o nulos
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        ResponseDTO response = new ResponseDTO("Los datos son ilegibles", 400, ResponseType.WARN);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Se ejecuta cuando se envía un JSON con un formato incorrecto
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseDTO> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        ResponseDTO response = new ResponseDTO("Formato de datos ilegibles", 400, ResponseType.WARN);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
    
    // Se ejecuta cuando se intenta acceder a un recurso al que no se tiene permiso
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ResponseDTO> handleAccessDeniedException(AccessDeniedException e) {
        ResponseDTO response = new ResponseDTO("Recurso protegido", 400, ResponseType.ERROR);
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    // Se ejecuta cuando se intenta convertir un valor no válido en un valor de un enum
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseDTO> handleIllegalArgumentException(IllegalArgumentException ex) {
        if (ex.getMessage().contains("No enum constant")) {
            ResponseDTO response = new ResponseDTO("Valor no permitido", 400, ResponseType.ERROR);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } else {
            ResponseDTO response = new ResponseDTO("Formato de datos ilegibles", 400, ResponseType.WARN);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex) {
        // Crear un objeto de respuesta que contenga el mensaje y el tipo de respuesta
        ResponseDTO response = new ResponseDTO("Usuario no encontrado", 401, ResponseType.ERROR);
        // Devolver una entidad de respuesta con el estado HTTP y el cuerpo del mensaje
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}
