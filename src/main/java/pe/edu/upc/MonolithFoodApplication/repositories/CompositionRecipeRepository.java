package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.MonolithFoodApplication.entities.CompositionRecipeEntity;
import pe.edu.upc.MonolithFoodApplication.entities.CompositionRecipeKey;

@Repository
public interface CompositionRecipeRepository extends JpaRepository<CompositionRecipeEntity, CompositionRecipeKey> {
    
}
