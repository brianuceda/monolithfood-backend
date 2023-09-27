package pe.edu.upc.MonolithFoodApplication.dtos.userconfig;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class DarkModeDTO extends ResponseDTO {
    private Boolean darkMode;

    public DarkModeDTO(Boolean darkMode) {
        this.darkMode = darkMode;
    }

    @Builder
    public DarkModeDTO(String message, Integer statusCode, Boolean darkMode) {
        super(message, statusCode);
        this.darkMode = darkMode;
    }
}
