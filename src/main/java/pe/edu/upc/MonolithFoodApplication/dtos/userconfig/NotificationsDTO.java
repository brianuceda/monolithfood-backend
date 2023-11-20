package pe.edu.upc.MonolithFoodApplication.dtos.userconfig;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
public class NotificationsDTO extends ResponseDTO {
    private Boolean notifications;

    public NotificationsDTO(Boolean notifications) {
        this.notifications = notifications;
    }

    @Builder
    public NotificationsDTO(String message, Integer statusCode, ResponseType type, Boolean notifications) {
        super(message, statusCode, type);
        this.notifications = notifications;
    }
}
