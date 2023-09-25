package pe.edu.upc.MonolithFoodApplication.dtos.userconfig;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.ResponseDTO;

@Getter
@Setter
public class NotificationsDTO extends ResponseDTO {
    private Boolean notifications;

    public NotificationsDTO(Boolean notifications) {
        this.notifications = notifications;
    }

    @Builder
    public NotificationsDTO(String message, Integer statusCode, Boolean notifications) {
        super(message, statusCode);
        this.notifications = notifications;
    }
}
