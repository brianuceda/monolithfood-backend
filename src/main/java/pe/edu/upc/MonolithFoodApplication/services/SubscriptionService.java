package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.http.HttpStatus;
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
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;
import pe.edu.upc.MonolithFoodApplication.enums.RoleEnum;
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
            logger.error("Usuario no encontrado");
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        List<RoleEntity> allRoles = roleRepository.findAll();
        if (allRoles.isEmpty()) {
            return new ResponseDTO("No se encontraron planes", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        Set<RoleEntity> myRoles = optUser.get().getRoles();
        if (optUser.get().getRoles().isEmpty()) {
            return new ResponseDTO("No tienes ninguna suscripción", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        Set<RoleEnum> myRoleNames = myRoles.stream().map(RoleEntity::getName).collect(Collectors.toSet());
        List<SubscriptionDTO> subscriptionDTOs = allRoles.stream()
            .map(role -> new SubscriptionDTO(
                null, 
                null, 
                null,
                role.getName(),
                role.getInformation(), 
                role.getPrice(), 
                myRoleNames.contains(role.getName()))) // selected
            .collect(Collectors.toList());
        return new SubscriptionsResponseDTO(null, 200, null, subscriptionDTOs);
    }
    // * Gabriela: Comprar plan de suscripcion
    @Transactional
    public ResponseDTO purchaseSubscription(String username, SubscriptionRequestDTO subscriptionPlanDTO) {
        subscriptionPlanDTO.setConfirmed(true);
        if(subscriptionPlanDTO.getConfirmed() == true) {
            Optional<UserEntity> optUser = userRepository.findByUsername(username);
            if (!optUser.isPresent()) {
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
            }
            Optional<RoleEntity> role = roleRepository.findByName(subscriptionPlanDTO.getSubscriptionPlan());
            if(!role.isPresent()) {
                logger.error("Plan de suscripción no encontrado para el usuario: " + username);
                return new ResponseDTO("El plan de suscripción no existe", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
            }
            UserEntity user = optUser.get();
            if(user.getRoles().contains(role.get())) {
                return new ResponseDTO("Ya cuentas con ese plan de suscripción", HttpStatus.NOT_FOUND.value(), ResponseType.INFO);
            }
            if(user.getWallet().getBalance() < role.get().getPrice()) {
                return new ResponseDTO("Saldo insuficiente", HttpStatus.NOT_FOUND.value(), ResponseType.INFO);
            }
            user.getRoles().add(role.get());
            user.getWallet().setBalance(user.getWallet().getBalance() - role.get().getPrice());
            userRepository.save(user);
            logger.info("El usuario " + username + " ha comprado el plan de suscripción " + subscriptionPlanDTO.getSubscriptionPlan());
            return new ResponseDTO("Plan de suscripción adquirido exitosamente", HttpStatus.OK.value(), ResponseType.SUCCESS);
        } else {
            return new ResponseDTO("No se ha confirmado la compra", HttpStatus.NOT_FOUND.value(), ResponseType.WARN);
        }
    }
    // * Gabriela: Cancelar plan de suscripcion
    @Transactional
    public ResponseDTO cancelSubscription(String username, SubscriptionRequestDTO subscriptionPlanDTO) {
        if(subscriptionPlanDTO.getConfirmed() == true) {
            Optional<UserEntity> optUser = userRepository.findByUsername(username);
            if (!optUser.isPresent()) {
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
            }
            Optional<RoleEntity> optRole = roleRepository.findByName(subscriptionPlanDTO.getSubscriptionPlan());
            if(!optRole.isPresent()) {
                return new ResponseDTO("El plan de suscripción no existe", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
            }
            UserEntity user = optUser.get();
            RoleEntity role = optRole.get();
            if(!user.getRoles().contains(role)) {
                return new ResponseDTO("No cuentas con ese plan de suscripción", HttpStatus.NOT_FOUND.value(), ResponseType.INFO);
            }
            if(user.getRoles().size() == 1) {
                return new ResponseDTO("No puedes suspender tu único plan de suscripción", HttpStatus.NOT_FOUND.value(), ResponseType.INFO);
            }
            user.getRoles().remove(role);
            userRepository.save(user);
            logger.info("El usuario " + username + " suspendió su plan de suscripción " + role.getName() + " correctamente.");
            return new ResponseDTO("Plan de suscripción suspendido exitosamente", HttpStatus.OK.value(), ResponseType.SUCCESS);
        } else {
            return new ResponseDTO("No se ha confirmado la suspensión", HttpStatus.NOT_FOUND.value(), ResponseType.WARN);
        }
    }

}
