package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;

import java.util.List;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {

    FoodEntity findByName(String name);

    List<FoodEntity> findByNameContaining(String name);
    
    @Query("SELECT f FROM FoodEntity f " +
           "JOIN f.compositions c " +
           "JOIN c.nutrient n " +
           "WHERE n.name LIKE :nutrientName")
    List<FoodEntity> findByNutrientName(@Param("nutrientName") String nutrientName);

}
