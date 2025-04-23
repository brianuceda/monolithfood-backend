package xyz.brianuceda.monolithfood_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.brianuceda.monolithfood_backend.entities.ActivityLevelEntity;

public interface ActivityLevelRepository extends JpaRepository<ActivityLevelEntity, Long> {
    Optional<ActivityLevelEntity> findByName(String name);
}
