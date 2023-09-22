package pe.edu.upc.MonolithFoodApplication.dtos.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

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
