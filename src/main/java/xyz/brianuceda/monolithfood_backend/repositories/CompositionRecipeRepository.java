package xyz.brianuceda.monolithfood_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.brianuceda.monolithfood_backend.entities.CompositionRecipeEntity;
import xyz.brianuceda.monolithfood_backend.entities.CompositionRecipeKey;

@Repository
public interface CompositionRecipeRepository extends JpaRepository<CompositionRecipeEntity, CompositionRecipeKey> {

}
