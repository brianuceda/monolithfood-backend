package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.MonolithFoodApplication.entities.UserSubscriptionEntity;
import java.util.List;
import java.util.Optional;


@Repository
public interface UserSubscriptionRepository extends JpaRepository<UserSubscriptionEntity, Long> {
    List<UserSubscriptionEntity> findByUserUsername(String username);
    @Query("SELECT us.subscriptionPlan.name FROM UserSubscriptionEntity us WHERE us.user.username = :username")
    Optional<String> findSubscriptionPlanNameByUsername(@Param("username") String username);
}
