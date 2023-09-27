package pe.edu.upc.MonolithFoodApplication.dtos.subscriptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEnum;

@Getter
@Setter
public class UserSubscriptionDTO extends ResponseDTO {
    private RoleEnum subscriptionPlan;
    // private boolean isActive;

    @Builder
    public UserSubscriptionDTO(String message, Integer statusCode, RoleEnum subscriptionPlan) {
        super(message, statusCode);
        this.subscriptionPlan = subscriptionPlan;
    }
}
