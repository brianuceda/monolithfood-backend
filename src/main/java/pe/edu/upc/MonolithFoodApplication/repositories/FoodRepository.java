package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;

import java.util.Optional;

@Repository
public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
    
    @Query("SELECT c.nutrientQuantity FROM CompositionEntity c WHERE c.food.id = :foodId AND c.nutrient.name = 'CALORIA'")
    Optional<Double> findCaloriesByFoodId(@Param("foodId") Long foodId);
}
