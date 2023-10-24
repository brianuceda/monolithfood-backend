package pe.edu.upc.MonolithFoodApplication.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;

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
    List<FoodEntity> findByNutrientName(@Param("nutrientName") String nutrientName);

    @Query("SELECT " + 
            "f.id, " +
            "f.name, " + 
            "f.information, " + 
            "f.imgUrl, " + 
            "cf.name, " + 
            "cf.information, " + 
            "cf.benefits, " + 
            "cf.disadvantages " + 
        "FROM FoodEntity f " +
            "JOIN f.categoryFood cf " +
        "WHERE f.id = :id"
    )
    List<Object[]> findDetailedFood(
        @Param("id") Long id
    );
}
