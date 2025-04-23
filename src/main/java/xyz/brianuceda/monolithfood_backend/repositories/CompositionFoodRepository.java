package xyz.brianuceda.monolithfood_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.brianuceda.monolithfood_backend.entities.CompositionFoodEntity;
import xyz.brianuceda.monolithfood_backend.entities.CompositionFoodKey;

@Repository
public interface CompositionFoodRepository extends JpaRepository<CompositionFoodEntity, CompositionFoodKey> {
}
