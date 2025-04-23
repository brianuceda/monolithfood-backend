package pe.edu.upc.MonolithFoodApplication.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.upc.MonolithFoodApplication.entities.ActivityLevelEntity;

public interface ActivityLevelRepository extends JpaRepository<ActivityLevelEntity, Long> {
    Optional<ActivityLevelEntity> findByName(String name);
}
