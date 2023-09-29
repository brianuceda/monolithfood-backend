package pe.edu.upc.MonolithFoodApplication.dtos.subscriptions;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class AllSubscriptionsDTO extends ResponseDTO {
    
    List<UserSubscriptionDTO> subscriptions;

    public AllSubscriptionsDTO(List<UserSubscriptionDTO> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Builder
    public AllSubscriptionsDTO(String message, Integer statusCode, List<UserSubscriptionDTO> subscriptions) {
        super(message, statusCode);
        this.subscriptions = subscriptions;
    }

}
