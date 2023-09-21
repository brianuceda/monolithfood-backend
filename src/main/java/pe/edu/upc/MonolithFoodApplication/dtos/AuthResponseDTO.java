package pe.edu.upc.MonolithFoodApplication.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponseDTO extends ResponseDTO {
    private String token;
    
    @Builder
    public AuthResponseDTO(String message, Integer statusCode, String token) {
        super(message, statusCode);
        this.token = token;
    }
}
