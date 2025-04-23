package xyz.brianuceda.monolithfood_backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import xyz.brianuceda.monolithfood_backend.dtos.searches.DetailedFoodDTO;
import xyz.brianuceda.monolithfood_backend.entities.FoodEntity;

public interface FoodRepository extends JpaRepository<FoodEntity, Long> {
    List<FoodEntity> findAllByOrderByIdAsc();
    Optional<FoodEntity> findByName(String name);
    List<FoodEntity> findByNameContaining(String name);

    // * (JPQL) Gabriela: Buscar alimentos por nutriente
    @Query(
        "SELECT f FROM FoodEntity f " +
            "JOIN f.compositions c " +
            "JOIN c.nutrient n " +
        "WHERE n.name LIKE :nutrientName " +
        "ORDER BY f.id ASC")
    List<FoodEntity> findByNutrientName(
        @Param("nutrientName") String nutrientName
    );

    @Query(
        "SELECT " +
            "n.id, " +
            "n.name, " +
            "(cf.nutrientQuantity * 1) as nutrientQuantity, " +
            "cf.unitOfMeasurement, " +
            "n.color " +
        "FROM FoodEntity f " +
            "JOIN f.compositions cf " +
            "JOIN cf.nutrient n " +
        "WHERE f.id = :id " +
        "GROUP BY n.id, n.name, cf.nutrientQuantity, cf.unitOfMeasurement, n.color"
    )
    List<Object[]> findNutrientsOfFood(
        @Param("id") Long id);

    @Query(
        "SELECT new pe.edu.upc.MonolithFoodApplication.dtos.searches.DetailedFoodDTO(" + 
            "f.id as id, " +
            "f.name as name, " + 
            "c.name as categoryFood) " +
        "FROM FoodEntity f " +
            "JOIN f.categoryFood c " +
        "WHERE f.id = :id " +
        "GROUP BY f.id, f.name, c.name"
    )
    DetailedFoodDTO findDetailedFood(
        @Param("id") Long id
    );
}
