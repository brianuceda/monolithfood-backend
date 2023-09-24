package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pe.edu.upc.MonolithFoodApplication.dto.UserSubscriptionDTO;
import pe.edu.upc.MonolithFoodApplication.services.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;



@RestController
@RequestMapping("/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/purchase")
    public void purchaseSubscription(@RequestBody UserSubscriptionDTO userSubscriptionDTO) {
        subscriptionService.purchaseSubscription(userSubscriptionDTO);
        
    }
    
    @DeleteMapping("/cancel/{username}")
    public ResponseEntity<String> cancelSubscription(@RequestParam("username") String username) {
        
        subscriptionService.cancelSubscriptionByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body("Subscription canceled successfully");
    }
    
    @GetMapping("/getSubscriptionPlan/{username}")
    public ResponseEntity<String> getSubscriptionPlanByUsername(@RequestParam("username") String username) {
        String subscriptionPlanName = subscriptionService.getSubscriptionPlanByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(subscriptionPlanName);
    }
    
}
