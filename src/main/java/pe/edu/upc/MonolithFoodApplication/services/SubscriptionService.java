package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.subscriptions.AllSubscriptionsDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.subscriptions.SubscriptionPlanDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.subscriptions.UserSubscriptionDTO;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEntity;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.RoleRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    // Log de errores y eventos
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    public ResponseDTO getSubscriptionPlan(String username) {
        // Verifica que el usuario exista
        Optional<UserEntity> getUser = userRepository.findByUsername(username);
        if(!getUser.isPresent()) return new ResponseDTO("Usuario no encontrado.", 404);
        // Obtiene los roles del usuario de la BD
        Set<RoleEnum> roles = getUser.get().getRoles().stream().map(role -> role.getName()).collect(Collectors.toSet());
        // Retorna los roles del usuario
        return new AllSubscriptionsDTO("Suscripciones recuperadas correctamente.", 200, roles);
    }

    public ResponseDTO purchaseSubscriptionPlan(String username, SubscriptionPlanDTO subscriptionPlanDTO) {
        if(subscriptionPlanDTO.getConfirmed() == true) {
            // Verifica que el usuario exista
            Optional<UserEntity> getUser = userRepository.findByUsername(username);
            if(!getUser.isPresent()) return new ResponseDTO("Usuario no encontrado.", 404);
            // Verifica que el plan de suscripción exista
            Optional<RoleEntity> roles = roleRepository.findByName(subscriptionPlanDTO.getSubscriptionPlan());
            if(!roles.isPresent()) {
                logger.error("Plan de suscripción no encontrado para el usuario: " + username);
            return new ResponseDTO("No se encontró ese plan de suscripción.", 404);
            }
            // Verifica que el usuario no tenga el rol que ha elegido actualmente
            if(getUser.get().getRoles().contains(roles.get())) {
                logger.error("El usuario " + username + " ya cuenta con el plan de suscripción elegido.");
                return new ResponseDTO("Ya cuentas con el plan de suscripción.", 400);
            }
            // Asigna el rol al usuario
            getUser.get().getRoles().add(roles.get());
            // Guarda el usuario en la base de datos
            userRepository.save(getUser.get());
            // Retorna el plan de suscripción comprado
            return new UserSubscriptionDTO("Compra realizada correctamente.", 200, subscriptionPlanDTO.getSubscriptionPlan());
        } else {
            return new ResponseDTO("No se ha confirmado la compra.", 400);
        }
    }

    public ResponseDTO cancelSubscriptionPlan(String username, UserSubscriptionDTO userSubscriptionDTO) {
        // Verifica que el usuario exista
        Optional<UserEntity> getUser = userRepository.findByUsername(username);
        if(!getUser.isPresent()) return new ResponseDTO("Usuario no encontrado.", 404);
        // Verifica que el plan de suscripción exista
        Optional<RoleEntity> roles = roleRepository.findByName(userSubscriptionDTO.getSubscriptionPlan());
        if(!roles.isPresent()) {
            logger.error("Plan de suscripción no encontrado para el usuario: " + username);
            return new ResponseDTO("No se encontró ese plan de suscripción.", 404);
        }
        // Verifica que el usuario tenga el rol que ha elegido actualmente
        if(!getUser.get().getRoles().contains(roles.get())) {
            logger.error("El usuario " + username + " no cuenta con el plan de suscripción elegido.");
            return new ResponseDTO("No cuentas con ese plan de suscripción.", 400);
        }
        // Elimina el rol del usuario
        getUser.get().getRoles().remove(roles.get());
        // Guarda el usuario en la base de datos
        userRepository.save(getUser.get());
        // Retorna el plan de suscripción eliminado
        return new UserSubscriptionDTO("Suscripción eliminada correctamente.", 200, userSubscriptionDTO.getSubscriptionPlan());
    }

}
