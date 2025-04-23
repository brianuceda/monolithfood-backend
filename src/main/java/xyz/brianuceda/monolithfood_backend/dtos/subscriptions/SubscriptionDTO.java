package xyz.brianuceda.monolithfood_backend.dtos.subscriptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;
import xyz.brianuceda.monolithfood_backend.enums.RoleEnum;

@Getter
@Setter
public class SubscriptionDTO extends ResponseDTO {
    private RoleEnum subscriptionPlan;
    private String information;
    private Double price;
    private Boolean selected = false;

    @Builder
    public SubscriptionDTO(String message, Integer status, ResponseType type, RoleEnum subscriptionPlan, String information, Double price, Boolean selected) {
        super(message, status, type);
        this.subscriptionPlan = subscriptionPlan;
        this.information = information;
        this.price = price;
        this.selected = selected;
    }
}
