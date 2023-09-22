package pe.edu.upc.MonolithFoodApplication.dtos.general;

import lombok.Data;

@Data
public class ResponseDTO {
    private String message;
    private Integer statusCode;
    
    public ResponseDTO() {
        this.message = "OK";
        this.statusCode = 200;
    }

    public ResponseDTO(String message, Integer statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
