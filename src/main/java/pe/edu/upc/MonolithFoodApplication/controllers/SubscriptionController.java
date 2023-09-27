package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.subscriptions.SubscriptionPlanDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.subscriptions.UserSubscriptionDTO;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/subscriptions")
public class SubscriptionController {
    // * Atributos
    // Inyección de dependencias
    private final SubscriptionService subscriptionService;
    private final JwtService jwtService;
    
    // * Metodos
    // * Subscription
    // Get: Obtiene plan de suscripción del usuario
    @GetMapping("/mySubscriptions")
    public ResponseEntity<?> getSubscriptionPlan(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = subscriptionService.getSubscriptionPlan(username);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Post: Compra un plan de suscripción
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSubscriptionPlan(@RequestHeader("Authorization") String bearerToken, @RequestBody SubscriptionPlanDTO subscriptionPlanDTO) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = subscriptionService.purchaseSubscriptionPlan(username, subscriptionPlanDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }    
    // Delete: Cancela un plan de suscripción
    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancelSubscriptionPlan(@RequestHeader("Authorization") String bearerToken, @RequestBody UserSubscriptionDTO plan) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = subscriptionService.cancelSubscriptionPlan(username, plan);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

}
