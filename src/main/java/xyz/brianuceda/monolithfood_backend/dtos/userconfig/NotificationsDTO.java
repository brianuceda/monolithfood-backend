package xyz.brianuceda.monolithfood_backend.dtos.userconfig;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

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
