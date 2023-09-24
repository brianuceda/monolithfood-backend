package pe.edu.upc.MonolithFoodApplication.dtos.userconfig;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.ResponseDTO;

@Getter
@Setter
public class UserConfigDTO extends ResponseDTO {
    private Boolean darkMode;
    private Boolean notifications;

    @Builder
    public UserConfigDTO(String message, int statusCode, Boolean darkMode, Boolean notifications) {
        super(message, statusCode);
        this.darkMode = darkMode;
        this.notifications = notifications;
    }

}
