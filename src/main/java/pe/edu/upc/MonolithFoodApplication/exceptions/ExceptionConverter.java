package pe.edu.upc.MonolithFoodApplication.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

public class ExceptionConverter {
    
    private static final Logger logger = LoggerFactory.getLogger(ExceptionConverter.class);

    // Guarda el error en el log, muestra el mensaje
    public static void saveAndShowInfoError(Exception e, HttpServletResponse response, ResponseDTO responseDTO)
            throws IOException, java.io.IOException {
        logger.error("Entrando al bloque Exception: " + e.getClass().getName());
        logger.error("Causa exacta: " + e.getCause());
        logger.error("Error detallado: ", e);
        sendErrorResponseInJSON(response, responseDTO);
    }

    // Reemplazar por el Entry Point cuando se haga
    public static void sendErrorResponseInJSON(HttpServletResponse response, ResponseDTO responseDTO)
            throws IOException, java.io.IOException {
        response.setStatus(responseDTO.getStatusCode());
        response.setContentType("application/json");
        response.getWriter().write(
            "{\"message\":\"" + responseDTO.getMessage() +
            "\",\"statusCode\":" + responseDTO.getStatusCode() +"}");
        response.getWriter().flush();
    }
}
