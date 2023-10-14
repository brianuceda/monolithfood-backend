package pe.edu.upc.MonolithFoodApplication.dtos.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class AuthResponseDTO extends ResponseDTO {
    private String token;
    private Boolean darkMode;

    @Builder
    public AuthResponseDTO(String message, Integer statusCode, String token, Boolean darkMode) {
        super(message, statusCode);
        this.token = token;
        this.darkMode = darkMode;
    }
}
