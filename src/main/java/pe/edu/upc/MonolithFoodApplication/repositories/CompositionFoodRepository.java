package pe.edu.upc.MonolithFoodApplication.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import pe.edu.upc.MonolithFoodApplication.entities.CompositionFoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.CompositionFoodKey;

@Repository
public interface CompositionFoodRepository extends JpaRepository<CompositionFoodEntity, CompositionFoodKey> {

    @Query(value =
        "SELECT cf.* FROM composition_food cf " +
        "JOIN food f ON cf.food_id = f.id " +
        "JOIN nutrients n ON cf.nutrient_id = n.id " +
        "WHERE f.name = ?1 AND n.name = ?2",
        nativeQuery = true                
    )
    Optional<CompositionFoodEntity> findByFoodAndNutrient(String foodName, String nutrientName);
}
