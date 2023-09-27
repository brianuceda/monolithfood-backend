package pe.edu.upc.MonolithFoodApplication.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
    Optional<FoodEntity> findByName(String name);
    List<FoodEntity> findByNameContaining(String name);

    @Query("SELECT f FROM FoodEntity f " +
            "JOIN f.compositions c " +
            "JOIN c.nutrient n " +
            "WHERE n.name LIKE :nutrientName")
    List<FoodEntity> findByNutrientName(@Param("nutrientName") String nutrientName);

}
