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
    private Double price;
    // private boolean isActive;

    public UserSubscriptionDTO(RoleEnum subscriptionPlan) {
        this.subscriptionPlan = subscriptionPlan;
    }

    @Builder
    public UserSubscriptionDTO(RoleEnum subscriptionPlan, Double price) {
        this.subscriptionPlan = subscriptionPlan;
        this.price = price;
    }

    @Builder
    public UserSubscriptionDTO(String message, Integer statusCode, RoleEnum subscriptionPlan, Double price) {
        super(message, statusCode);
        this.subscriptionPlan = subscriptionPlan;
        this.price = price;
    }
}
