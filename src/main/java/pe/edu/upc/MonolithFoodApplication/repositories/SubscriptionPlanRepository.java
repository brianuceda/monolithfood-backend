package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.MonolithFoodApplication.entities.SubscriptionPlanEntity;

@Repository
public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlanEntity, Long> {

}
