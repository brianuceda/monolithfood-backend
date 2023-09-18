package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.MonolithFoodApplication.entities.UserConfigEntity;

@Repository
public interface UserConfigRepository extends JpaRepository<UserConfigEntity, Long> {
    
}
