package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;

import java.util.List;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {

    List<FoodEntity> findByNameContaining(String name);
    List<FoodEntity> findByCategoryName(String category);
    List<FoodEntity> findByNutrient(String nutrient);
    

}
