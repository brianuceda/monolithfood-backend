package xyz.brianuceda.monolithfood_backend.dtos.subscriptions;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

@Getter
@Setter
public class SubscriptionsResponseDTO extends ResponseDTO {
    List<SubscriptionDTO> subscriptions;

    public SubscriptionsResponseDTO(List<SubscriptionDTO> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Builder
    public SubscriptionsResponseDTO(String message, Integer statusCode, ResponseType type, List<SubscriptionDTO> subscriptions) {
        super(message, statusCode, type);
        this.subscriptions = subscriptions;
    }

}
