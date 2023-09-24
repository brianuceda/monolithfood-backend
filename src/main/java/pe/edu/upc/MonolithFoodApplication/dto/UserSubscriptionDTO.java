package pe.edu.upc.MonolithFoodApplication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSubscriptionDTO {
    private String username;
    private Long subscriptionPlanId;
    private boolean isActive;
    
}
