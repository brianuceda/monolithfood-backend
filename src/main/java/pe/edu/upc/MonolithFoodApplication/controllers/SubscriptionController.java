package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.MonolithFoodApplication.dto.UserSubscriptionDTO;
import pe.edu.upc.MonolithFoodApplication.services.SubscriptionService;

@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/purchase")
    public void purchaseSubscription(@RequestBody UserSubscriptionDTO userSubscriptionDTO) {
        subscriptionService.purchaseSubscription(userSubscriptionDTO);
        
    }
    
}
