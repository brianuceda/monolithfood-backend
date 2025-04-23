package xyz.brianuceda.monolithfood_backend.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import xyz.brianuceda.monolithfood_backend.dtos.foodintake.DetailedIntakeDTO;
import xyz.brianuceda.monolithfood_backend.dtos.foodintake.MacrosDetailedDTO;
import xyz.brianuceda.monolithfood_backend.dtos.foodintake.MacrosPerCategoryDTO;
import xyz.brianuceda.monolithfood_backend.entities.EatEntity;
import xyz.brianuceda.monolithfood_backend.enums.CategoryIntakeEnum;

@Repository
public interface EatRepository extends JpaRepository<EatEntity, Long> {

    boolean existsByIdAndUserUsername(Long id, String username);

    // * Heather (JPQL): Elimina un registro de la tabla eat
    @Modifying
    @Query("DELETE FROM EatEntity e WHERE e.id = :id")
    void deleteId(@Param("id") Long id);

    // * Willy (JPQL): Retorna el promedio de calorias consumidas en la ultima semana
    @Query(
        "SELECT " +
            "SUM(cf.nutrientQuantity) " +
        "FROM EatEntity e " +
        "JOIN e.user u " +
        "JOIN e.food f " +
        "JOIN f.compositions cf " +
        "JOIN cf.nutrient n " +
        "WHERE n.name = 'Grasa' AND u.username = :username " +
        "AND (u.username, FUNCTION('DATE_TRUNC', 'WEEK', e.date)) IN " +
        "(SELECT u2.username, MAX(FUNCTION('DATE_TRUNC', 'WEEK', e2.date)) " +
        "FROM EatEntity e2 " +
        "JOIN e2.user u2 " +
        "WHERE u2.username = :username " +
        "GROUP BY u2.username) " +
        "GROUP BY username"
    )
    List<Object[]> getAveragecaloriesLastWeek(@Param("username") String username);

    // * (JPQL) Willy: Retorna el promedio de calorias consumidas en el ultimo dia
    @Query(
        "SELECT " +  
            "e.date As date, "+
            "AVG(cf.nutrientQuantity) as averageCaloriesDay " +
        "FROM EatEntity e " +
        "JOIN e.user u " +
        "JOIN e.food f " +
        "JOIN f.compositions cf " +
        "JOIN cf.nutrient n " +
        "WHERE n.name = 'Grasa' " +
        "AND u.username = :username " +
        "AND e.date >= CURRENT_DATE - 7 " +  
        "GROUP BY username, date " +
        "ORDER BY date"
    )
    List<Object[]> getAverageCalorieConsumptioDay(@Param("username") String username);

    // asd
    @Query(
        "SELECT new pe.edu.upc.MonolithFoodApplication.dtos.foodintake.MacrosDetailedDTO(" +
            "SUM(CASE WHEN n.name = 'Calorias' THEN cf.nutrientQuantity * e.eatQuantity ELSE 0 END) as consumedCalories, " +
            "u.userFitnessInfo.dailyCaloricIntake as dailyCaloricIntake, " +
            "SUM(CASE WHEN n.name = 'Proteina' THEN cf.nutrientQuantity * e.eatQuantity ELSE 0 END) as consumedProteins, " +
            "u.userFitnessInfo.dailyProteinIntake as dailyProteinIntake, " +
            "SUM(CASE WHEN n.name = 'Carbohidratos' THEN cf.nutrientQuantity * e.eatQuantity ELSE 0 END) as consumedCarbohydrates, " +
            "u.userFitnessInfo.dailyCarbohydrateIntake as dailyCarbohydrateIntake, " +
            "SUM(CASE WHEN n.name = 'Grasa' THEN cf.nutrientQuantity * e.eatQuantity ELSE 0 END) as consumedFats, " +
            "u.userFitnessInfo.dailyFatIntake as dailyFatIntake) " +
        "FROM EatEntity e " +
        "JOIN e.user u " +
        "JOIN e.food f " +
        "JOIN f.compositions cf " +
        "JOIN cf.nutrient n " +
        "WHERE u.username = :username AND e.date BETWEEN :startDate AND :endDate " +
        "GROUP BY username, dailyCaloricIntake, dailyProteinIntake, dailyCarbohydrateIntake, dailyFatIntake"
    )
    MacrosDetailedDTO getMacrosDetailed(
        @Param("username") String username,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query(
        "SELECT new pe.edu.upc.MonolithFoodApplication.dtos.foodintake.MacrosPerCategoryDTO(" +
            "SUM(CASE WHEN n.name = 'Calorias' THEN cf.nutrientQuantity * e.eatQuantity ELSE 0 END), " +
            "SUM(CASE WHEN n.name = 'Proteina' THEN cf.nutrientQuantity * e.eatQuantity ELSE 0 END), " +
            "SUM(CASE WHEN n.name = 'Carbohidratos' THEN cf.nutrientQuantity * e.eatQuantity ELSE 0 END), " +
            "SUM(CASE WHEN n.name = 'Grasa' THEN cf.nutrientQuantity * e.eatQuantity ELSE 0 END)) " +
        "FROM EatEntity e " +
        "JOIN e.user u " +
        "JOIN e.food f " +
        "JOIN f.compositions cf " +
        "JOIN cf.nutrient n " +
        "WHERE u.username = :username AND e.categoryIntake = :category AND e.date BETWEEN :startDate AND :endDate " +
        "GROUP BY u.username"
    )
    MacrosPerCategoryDTO findMacrosPerCategory(
        @Param("username") String username,
        @Param("category") CategoryIntakeEnum category,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query(
        "SELECT " + 
            "e.id as id, " +
            "f.name as name, " + 
            "f.imgUrl as imgUrl, " +  
            "e.eatQuantity as quantity, " + 
            "e.unitOfMeasurement as unitOfMeasurement, " + 
            "e.date as createdAt " +
        "FROM EatEntity e " +
        "JOIN e.user u " +
        "JOIN e.food f " +
        "JOIN f.categoryFood c " +
        "WHERE u.username = :username AND e.categoryIntake = :category AND e.date BETWEEN :startDate AND :endDate"
    )
    List<Object[]> findIntakesPerCategory(
        @Param("username") String username,
        @Param("category") CategoryIntakeEnum category,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate
    );

    @Query(
        "SELECT new pe.edu.upc.MonolithFoodApplication.dtos.foodintake.DetailedIntakeDTO(" + 
            "e.id as id, " +
            "f.name as name, " + 
            "e.categoryIntake as categoryIntake, " + 
            "c.name as categoryFood, " + 
            "e.unitOfMeasurement as unitOfMeasurement, " + 
            "e.eatQuantity as quantity, " + 
            "e.date as date, " +
            "f.id as foodId) " +
        "FROM EatEntity e " +
            "JOIN e.user u " +
            "JOIN e.food f " +
            "JOIN f.categoryFood c " +
        "WHERE u.username = :username " +
            "AND e.id = :id " +
        "GROUP BY "+
            "e.id, f.name, c.name, e.categoryIntake, e.unitOfMeasurement, e.eatQuantity, e.date"
    )
    DetailedIntakeDTO findDetailedIntake(
        @Param("username") String username,
        @Param("id") Long id
    );

}
 