package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.MonolithFoodApplication.dtos.UserPersonalInfoDTO;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;

@Repository
public interface UserPersonalInfoRepository extends JpaRepository<UserPersonalInfoEntity, Long> {

    @Query(value = "SELECT upi.gender AS gender, upi.birthdate AS birthdate, height_cm AS heightCm, weight_kg AS weightKg FROM user_personal_info upi INNER JOIN users u ON upi.user_id = u.id WHERE u.username = :username", nativeQuery = true)
    UserPersonalInfoDTO findByUsername(@Param("username") String username);
}
