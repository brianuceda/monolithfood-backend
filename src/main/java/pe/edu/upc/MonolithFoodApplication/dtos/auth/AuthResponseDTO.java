package pe.edu.upc.MonolithFoodApplication.dtos.auth;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthResponseDTO extends ResponseDTO {
    private String token;
    private Boolean darkMode;

    @Builder
    public AuthResponseDTO(String message, Integer statusCode, ResponseType type, String token, Boolean darkMode) {
        super(message, statusCode, type);
        this.token = token;
        this.darkMode = darkMode;
    }
}
