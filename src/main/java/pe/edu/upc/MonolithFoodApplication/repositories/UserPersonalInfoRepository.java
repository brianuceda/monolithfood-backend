package pe.edu.upc.MonolithFoodApplication.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;

@Repository
public interface UserPersonalInfoRepository extends JpaRepository<UserPersonalInfoEntity, Long> {
    // @Query("SELECT u FROM UserPersonalInfoEntity u WHERE u.user.username = ?1")
    // Optional<UserPersonalInfoEntity> findByUsername(String username);
    //opcional es para manejarel objeto  si la bd no retorna valores(null).
}
