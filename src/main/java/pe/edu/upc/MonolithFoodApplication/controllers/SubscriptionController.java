package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.subscriptions.SubscriptionRequestDTO;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/subscriptions")
public class SubscriptionController {
    private final SubscriptionService subscriptionService;
    private final JwtService jwtService;
    
    // * Gabriela (Suscripciones)
    // Get: Obtener los planes de suscripción de un usuario
    @GetMapping
    public ResponseEntity<?> getMySubscriptions(@RequestHeader("Authorization") String bearerToken) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = subscriptionService.getSubscriptions(username);
        return validateResponse(response);
    }
    // Post: Comprar un plan de suscripción
    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseSubscription(@RequestHeader("Authorization") String bearerToken,
        @RequestBody SubscriptionRequestDTO subscriptionPlanDTO) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = subscriptionService.purchaseSubscription(username, subscriptionPlanDTO);
        return validateResponse(response);
    }
    // Delete: Cancelar un plan de suscripción
    @DeleteMapping("/cancel")
    public ResponseEntity<?> cancelSubscription(@RequestHeader("Authorization") String bearerToken,
        @RequestBody SubscriptionRequestDTO subscriptionPlanDTO) {
        String username = jwtService.getUsernameFromBearerToken(bearerToken);
        ResponseDTO response = subscriptionService.cancelSubscription(username, subscriptionPlanDTO);
        return validateResponse(response);
    }

    // * Responder a la petición con el código de estado y el mensaje correspondiente
    private ResponseEntity<?> validateResponse(ResponseDTO response) {
        try {  
            if (response.getStatusCode() == 200 && response.getMessage() == null) {
                response.setStatusCode(null);
                response.setMessage(null);
                return new ResponseEntity<>(response, HttpStatus.valueOf(200));
            } else {
                return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
            }
        } catch (Exception e) {
                return new ResponseEntity<>("Ocurrió un error.", HttpStatus.valueOf(500));
        }
    }

}
