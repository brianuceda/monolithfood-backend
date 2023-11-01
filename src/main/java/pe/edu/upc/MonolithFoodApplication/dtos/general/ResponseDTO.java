package pe.edu.upc.MonolithFoodApplication.dtos.general;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDTO {
    private String message;
    private Integer statusCode;
    private ResponseType type;

    public ResponseDTO() {
        this.message = "Datos recuperados exitosamente";
        this.statusCode = 200;
        this.type = ResponseType.SUCCESS;
    }

    public ResponseDTO(String message, Integer statusCode, ResponseType type) {
        this.message = message;
        this.statusCode = statusCode;
        this.type = type;
    }
}
