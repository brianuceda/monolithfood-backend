package pe.edu.upc.MonolithFoodApplication.dtos.userconfig;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

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
