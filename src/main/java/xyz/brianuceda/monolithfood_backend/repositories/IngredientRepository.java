package xyz.brianuceda.monolithfood_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.brianuceda.monolithfood_backend.entities.IngredientEntity;
import xyz.brianuceda.monolithfood_backend.entities.IngredientKey;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, IngredientKey> {

}
