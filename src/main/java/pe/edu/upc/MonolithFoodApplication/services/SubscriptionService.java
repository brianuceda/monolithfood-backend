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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    // ? Atributos
    // Inyección de dependencias
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    // Log de errores y eventos
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    // ? Metodos
    // * Gabriela: Obtener todos los planes de suscripcion
    public ResponseDTO getSubscription() {
        List<RoleEntity> roles = roleRepository.findAll();
        if (roles.isEmpty()) {
            logger.error("No se encontraron planes de suscripción.");
            return new ResponseDTO("No se encontraron planes de suscripción.", 404);
        }
        // Obtiene los roles de la base de datos
        List<UserSubscriptionDTO> subscriptionDTOs = roles.stream()
                .map(role -> new UserSubscriptionDTO("", 200, role.getName(), role.getPrice()))
                .collect(Collectors.toList());
        return new AllSubscriptionsDTO("Todos los planes de suscripción recuperados correctamente.", 200, subscriptionDTOs);
    }
    // * Gabriela: Obtener los planes de suscripcion de un usuario
    public ResponseDTO getMySubscriptions(String username) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Obtiene los roles del usuario
        List<UserSubscriptionDTO> subscriptionDTOs = optUser.get().getRoles().stream()
                .map(role -> new UserSubscriptionDTO("", 200, role.getName(), role.getPrice()))
                .collect(Collectors.toList());
        // Retorna los roles del usuario
        return new AllSubscriptionsDTO("Planes de suscripción recuperados correctamente.", 200, subscriptionDTOs);
    }
    // * Gabriela: Comprar plan de suscripcion
    public ResponseDTO purchaseSubscription(String username, SubscriptionPlanDTO subscriptionPlanDTO) {
        if(subscriptionPlanDTO.getConfirmed() == true) {
            // Verifica que el usuario exista
            Optional<UserEntity> optUser = userRepository.findByUsername(username);
            if (!optUser.isPresent()) {
                logger.error("Usuario no encontrado.");
                return new ResponseDTO("Usuario no encontrado.", 404);
            }
            // Verifica que el plan de suscripción exista
            Optional<RoleEntity> role = roleRepository.findByName(subscriptionPlanDTO.getSubscriptionPlan());
            if(!role.isPresent()) {
                logger.error("Plan de suscripción no encontrado para el usuario: " + username);
                return new ResponseDTO("No se encontró ese plan de suscripción.", 404);
            }
            UserEntity user = optUser.get();
            // Verifica que el usuario no tenga el rol que ha elegido actualmente
            if(user.getRoles().contains(role.get())) {
                logger.error("El usuario " + username + " ya cuenta con el plan de suscripción elegido.");
                return new ResponseDTO("Ya cuentas con el plan de suscripción.", 400);
            }
            // Asigna el rol al usuario
            user.getRoles().add(role.get());
            // Guarda el usuario en la base de datos
            userRepository.save(user);
            logger.info("El usuario " + username + " ha comprado el plan de suscripción " + subscriptionPlanDTO.getSubscriptionPlan());
            // Retorna el plan de suscripción comprado
            return new ResponseDTO("Plan de suscripción " + subscriptionPlanDTO.getSubscriptionPlan() + " adquirido correctamente.", 200
            );
        } else {
            return new ResponseDTO("No se ha confirmado la compra.", 400);
        }
    }
    // * Gabriela: Cancelar plan de suscripcion
    public ResponseDTO cancelSubscription(String username, RoleEnum userSubscriptionDTO) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Verifica que el plan de suscripción exista
        Optional<RoleEntity> optRole = roleRepository.findByName(userSubscriptionDTO);
        if(!optRole.isPresent()) {
            logger.error("Plan de suscripción no encontrado para el usuario: " + username);
            return new ResponseDTO("No se encontró ese plan de suscripción.", 404);
        }
        UserEntity user = optUser.get();
        RoleEntity role = optRole.get();
        // Verifica que el usuario tenga el rol que ha elegido actualmente
        if(!user.getRoles().contains(role)) {
            logger.error("El usuario " + username + " no cuenta con el plan de suscripción elegido.");
            return new ResponseDTO("No cuentas con ese plan de suscripción.", 400);
        }
        // Elimina el rol del usuario
        user.getRoles().remove(role);
        // Guarda el usuario en la base de datos
        userRepository.save(user);
        // Retorna el plan de suscripción eliminado
        logger.info("El usuario " + username + " suspendió su plan de suscripción " + role.getName() + " correctamente.");
        return new ResponseDTO("Has suspendido tu suscripción al plan " + role.getName() + " correctamente.", 200);
    }

}
