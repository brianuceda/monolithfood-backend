package pe.edu.upc.MonolithFoodApplication.dtos.subscriptions;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEnum;

@Getter
@Setter
public class AllSubscriptionsDTO extends ResponseDTO {
    
    Set<RoleEnum> subscriptions;

    public AllSubscriptionsDTO(Set<RoleEnum> subscriptions) {
        this.subscriptions = subscriptions;
    }

    @Builder
    public AllSubscriptionsDTO(String message, Integer statusCode, Set<RoleEnum> subscriptions) {
        super(message, statusCode);
        this.subscriptions = subscriptions;
    }

}
