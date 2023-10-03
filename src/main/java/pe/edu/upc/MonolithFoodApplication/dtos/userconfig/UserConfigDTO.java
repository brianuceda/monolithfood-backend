package pe.edu.upc.MonolithFoodApplication.dtos.userconfig;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class UserConfigDTO extends ResponseDTO {
    private Boolean notifications;
    private Timestamp lastFoodEntry;
    private Timestamp lastWeightUpdate;
    private Boolean darkMode;

    @Builder
    public UserConfigDTO(String message, int statusCode, Boolean notifications, Timestamp lastFoodEntry, Timestamp lastWeightUpdate, Boolean darkMode) {
        super(message, statusCode);
        this.notifications = notifications;
        this.lastFoodEntry = lastFoodEntry;
        this.lastWeightUpdate = lastWeightUpdate;
        this.darkMode = darkMode;
    }

}
