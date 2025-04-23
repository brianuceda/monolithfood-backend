package xyz.brianuceda.monolithfood_backend.dtos.userconfig;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

@Getter
@Setter
public class DarkModeDTO extends ResponseDTO {
    private Boolean darkMode;

    public DarkModeDTO(Boolean darkMode) {
        this.darkMode = darkMode;
    }

    @Builder
    public DarkModeDTO(String message, Integer statusCode, ResponseType type, Boolean darkMode) {
        super(message, statusCode, type);
        this.darkMode = darkMode;
    }
}
