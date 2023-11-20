package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.subscriptions.SubscriptionRequestDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;
import pe.edu.upc.MonolithFoodApplication.services.JwtService;
import pe.edu.upc.MonolithFoodApplication.services.SubscriptionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user/subscriptions")
@PreAuthorize("hasAnyRole('ADMIN', 'USER', 'VIP')")
@CrossOrigin(origins = "https://monolithfood.site", allowedHeaders = {"Content-Type", "Authorization", "Cache-Control"})
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
            return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseDTO("Ocurrio un error", 500, ResponseType.ERROR), HttpStatus.valueOf(500));
        }
    }

}
