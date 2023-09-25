package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.MonolithFoodApplication.entities.IngredientEntity;
import pe.edu.upc.MonolithFoodApplication.entities.IngredientKey;

@Repository
public interface IngredientRepository extends JpaRepository<IngredientEntity, IngredientKey> {
    
}
