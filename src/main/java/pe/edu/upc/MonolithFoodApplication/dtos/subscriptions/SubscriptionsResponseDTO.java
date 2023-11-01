package pe.edu.upc.MonolithFoodApplication.dtos.subscriptions;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

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
