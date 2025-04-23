package xyz.brianuceda.monolithfood_backend.dtos.subscriptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.brianuceda.monolithfood_backend.enums.RoleEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionRequestDTO {
    private RoleEnum subscriptionPlan;
    private Boolean confirmed;
    // private int durationMonths;
   
}
