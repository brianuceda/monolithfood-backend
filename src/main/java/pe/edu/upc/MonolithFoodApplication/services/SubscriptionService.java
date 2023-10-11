package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.subscriptions.SubscriptionsResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.subscriptions.SubscriptionRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.subscriptions.SubscriptionDTO;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEntity;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.RoleRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(SubscriptionService.class);

    // ? Metodos
    // * Gabriela: Obtener los planes de suscripcion de un usuario (roles)
    public ResponseDTO getSubscriptions(String username) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        List<RoleEntity> allRoles = roleRepository.findAll();
        if (allRoles.isEmpty()) {
            logger.error("No se encontraron planes de suscripción en el servidor.");
            return new ResponseDTO("No se encontraron planes de suscripción en el servidor.", 404);
        }
        Set<RoleEntity> myRoles = optUser.get().getRoles();
        if (optUser.get().getRoles().isEmpty()) {
            logger.error("El usuario " + username + " no cuenta con planes de suscripción activos.");
            return new ResponseDTO("No cuentas con planes de suscripción activos.", 404);
        }
        // Obtiene los roles del usuario en formato RoleEnum
        Set<RoleEnum> myRoleNames = myRoles.stream().map(RoleEntity::getName).collect(Collectors.toSet());
        // Se crea una lista de SubscriptionDTO con los roles del usuario y los roles del servidor
        // Si el usuario tiene el rol, selected = true, si no, selected = false
        List<SubscriptionDTO> subscriptionDTOs = allRoles.stream()
            .map(role -> new SubscriptionDTO(
                null, 
                null, 
                role.getName(),
                role.getInformation(), 
                role.getPrice(), 
                myRoleNames.contains(role.getName()))) // selected
            .collect(Collectors.toList());
        return new SubscriptionsResponseDTO(null, 200, subscriptionDTOs);
    }
    // * Gabriela: Comprar plan de suscripcion
    @Transactional
    public ResponseDTO purchaseSubscription(String username, SubscriptionRequestDTO subscriptionPlanDTO) {
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
                return new ResponseDTO("Ya cuentas con el plan de suscripción.", 404);
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
            return new ResponseDTO("No se ha confirmado la compra.", 404);
        }
    }
    // * Gabriela: Cancelar plan de suscripcion
    @Transactional
    public ResponseDTO cancelSubscription(String username, SubscriptionRequestDTO subscriptionPlanDTO) {
        if(subscriptionPlanDTO.getConfirmed() == true) {
            // Verifica que el usuario exista
            Optional<UserEntity> optUser = userRepository.findByUsername(username);
            if (!optUser.isPresent()) {
                logger.error("Usuario no encontrado.");
                return new ResponseDTO("Usuario no encontrado.", 404);
            }
            // Verifica que el plan de suscripción exista
            Optional<RoleEntity> optRole = roleRepository.findByName(subscriptionPlanDTO.getSubscriptionPlan());
            if(!optRole.isPresent()) {
                logger.error("Plan de suscripción no encontrado para el usuario: " + username);
                return new ResponseDTO("No se encontró ese plan de suscripción.", 404);
            }
            UserEntity user = optUser.get();
            RoleEntity role = optRole.get();
            // Verifica que el usuario tenga el rol que ha elegido actualmente
            if(!user.getRoles().contains(role)) {
                logger.error("El usuario " + username + " no cuenta con el plan de suscripción elegido.");
                return new ResponseDTO("No cuentas con ese plan de suscripción.", 404);
            }
            // Verifica que el rol que está intentando suspender el usuario, no sea su único rol (siempre debe tener al menos uno)
            if(user.getRoles().size() == 1) {
                logger.error("El usuario " + username + " no puede suspender su único plan de suscripción.");
                return new ResponseDTO("No puedes suspender tu único plan de suscripción.", 404);
            }
            // Elimina el rol del usuario
            user.getRoles().remove(role);
            // Guarda el usuario en la base de datos
            userRepository.save(user);
            // Retorna el plan de suscripción eliminado
            logger.info("El usuario " + username + " suspendió su plan de suscripción " + role.getName() + " correctamente.");
            return new ResponseDTO("Has suspendido tu suscripción al plan " + role.getName() + " correctamente.", 200);
        } else {
            return new ResponseDTO("No se ha confirmado la cancelación.", 404);
        }
    }

}
