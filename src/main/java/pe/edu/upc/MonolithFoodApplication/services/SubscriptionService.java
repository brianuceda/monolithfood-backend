package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.MonolithFoodApplication.dto.UserSubscriptionDTO;
import pe.edu.upc.MonolithFoodApplication.entities.SubscriptionPlanEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserSubscriptionEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.SubscriptionPlanRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserSubscriptionRepository;

import java.time.LocalDateTime;

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

        
        UserEntity user = userRepository.findById(userSubscriptionDTO.getUserId()).orElse(null);
        SubscriptionPlanEntity subscriptionPlan = subscriptionPlanRepository.findById(userSubscriptionDTO.getSubscriptionPlanId()).orElse(null);

        
        userSubscriptionEntity.setUser(user);
        userSubscriptionEntity.setSubscriptionPlan(subscriptionPlan);

        
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusMonths(1);  
        userSubscriptionEntity.setStartDate(startDate);
        userSubscriptionEntity.setEndDate(endDate);

        
        userSubscriptionRepository.save(userSubscriptionEntity);
    }

    
}
