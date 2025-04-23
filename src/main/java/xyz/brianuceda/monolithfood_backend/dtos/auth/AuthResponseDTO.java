package xyz.brianuceda.monolithfood_backend.dtos.auth;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

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
