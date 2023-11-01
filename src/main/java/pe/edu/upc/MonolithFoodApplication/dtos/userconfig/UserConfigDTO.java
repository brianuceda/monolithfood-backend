package pe.edu.upc.MonolithFoodApplication.dtos.userconfig;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
public class UserConfigDTO extends ResponseDTO {
    private Boolean notifications;
    private Timestamp lastFoodEntry;
    private Timestamp lastWeightUpdate;
    private Boolean darkMode;

    @Builder
    public UserConfigDTO(String message, Integer statusCode, ResponseType type, Boolean notifications, Timestamp lastFoodEntry, Timestamp lastWeightUpdate, Boolean darkMode) {
        super(message, statusCode, type);
        this.notifications = notifications;
        this.lastFoodEntry = lastFoodEntry;
        this.lastWeightUpdate = lastWeightUpdate;
        this.darkMode = darkMode;
    }

}
