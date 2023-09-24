package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import pe.edu.upc.MonolithFoodApplication.dto.UserSubscriptionDTO;
import pe.edu.upc.MonolithFoodApplication.entities.SubscriptionPlanEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserSubscriptionEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.SubscriptionPlanRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserSubscriptionRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;


@Service
public class SubscriptionService {

    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Autowired
    private UserRepository userRepository;

    public void purchaseSubscription(UserSubscriptionDTO userSubscriptionDTO) {
        UserSubscriptionEntity userSubscriptionEntity = new UserSubscriptionEntity();
    
        Optional<UserEntity> optionalUser = userRepository.findByUsername(userSubscriptionDTO.getUsername());
        UserEntity user = optionalUser.orElse(null);
    
        SubscriptionPlanEntity subscriptionPlan = subscriptionPlanRepository.findById(userSubscriptionDTO.getSubscriptionPlanId()).orElse(null);
    
        userSubscriptionEntity.setUser(user);
        userSubscriptionEntity.setSubscriptionPlan(subscriptionPlan);
    
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMonths(1);
        userSubscriptionEntity.setStartDate(startDate);
        userSubscriptionEntity.setEndDate(endDate);
    
        userSubscriptionRepository.save(userSubscriptionEntity);
    }

    public void deleteSubscription(Long subscriptionId) {
       
        Optional<UserSubscriptionEntity> subscriptionOptional = userSubscriptionRepository.findById(subscriptionId);
        if (subscriptionOptional.isPresent()) {
            
            userSubscriptionRepository.deleteById(subscriptionId);
        } else {
            throw new EntityNotFoundException("Subscription not found with id: " + subscriptionId);
        }
    }

    public class SubscriptionNotFoundException extends RuntimeException {
        public SubscriptionNotFoundException(String message) {
            super(message);
        }
    }

    public void cancelSubscriptionByUsername(String username) {
        List<UserSubscriptionEntity> subscriptions = userSubscriptionRepository.findByUserUsername(username);
        
        if (subscriptions.isEmpty()) {
            throw new SubscriptionNotFoundException("No se encontraron suscripciones para el usuario: " + username);
        }

        
        UserSubscriptionEntity subscriptionEntityToDelete = subscriptions.get(0);
        userSubscriptionRepository.deleteById(subscriptionEntityToDelete.getId());
    }

    public String getSubscriptionPlanByUsername(String username) {
        Optional<String> subscriptionPlanName = userSubscriptionRepository.findSubscriptionPlanNameByUsername(username);
        return subscriptionPlanName.orElse("No subscription plan found");
    }
    
}
