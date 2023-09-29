package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.subscriptions.SubscriptionPlanDTO;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEnum;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/subscriptions")
public class SubscriptionController {
    // ? Atributos
    // Inyección de dependencias
    private final SubscriptionService subscriptionService;
    private final JwtService jwtService;
    
    // ? Metodos
    // * Gabriela (Suscripciones)
    // Get: Obtener todos los planes de suscripción disponibles
    @GetMapping("/all")
    public ResponseEntity<?> getSubscription() {
        ResponseDTO response = subscriptionService.getSubscription();
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Get: Obtener los planes de suscripción de un usuario
    @GetMapping
    public ResponseEntity<?> getMySubscriptions(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = subscriptionService.getMySubscriptions(username);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Post: Comprar un plan de suscripción
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSubscription(@RequestHeader("Authorization") String bearerToken,
            @RequestBody SubscriptionPlanDTO subscriptionPlanDTO) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = subscriptionService.purchaseSubscription(username, subscriptionPlanDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }
    // Delete: Cancelar un plan de suscripción
    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancelSubscription(@RequestHeader("Authorization") String bearerToken,
            @RequestParam RoleEnum plan) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = subscriptionService.cancelSubscription(username, plan);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

}
