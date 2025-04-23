package xyz.brianuceda.monolithfood_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import xyz.brianuceda.monolithfood_backend.entities.ObjectiveEntity;

public interface ObjectiveRepository extends JpaRepository<ObjectiveEntity, Long> {

}
