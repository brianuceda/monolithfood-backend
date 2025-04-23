package pe.edu.upc.MonolithFoodApplication.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.MonolithFoodApplication.entities.UserFitnessInfoEntity;

@Repository
public interface UserFitnessInfoRepository extends JpaRepository<UserFitnessInfoEntity, Long> {

    @Query(value = "SELECT " +
       "SUM(CASE WHEN EXTRACT(DOW FROM e.date) = 0 THEN cf.nutrient_quantity * e.eat_quantity ELSE 0 END) as domingo, " +
       "SUM(CASE WHEN EXTRACT(DOW FROM e.date) = 1 THEN cf.nutrient_quantity * e.eat_quantity ELSE 0 END) as lunes, " +
       "SUM(CASE WHEN EXTRACT(DOW FROM e.date) = 2 THEN cf.nutrient_quantity * e.eat_quantity ELSE 0 END) as martes, " +
       "SUM(CASE WHEN EXTRACT(DOW FROM e.date) = 3 THEN cf.nutrient_quantity * e.eat_quantity ELSE 0 END) as miercoles, " +
       "SUM(CASE WHEN EXTRACT(DOW FROM e.date) = 4 THEN cf.nutrient_quantity * e.eat_quantity ELSE 0 END) as jueves, " +
       "SUM(CASE WHEN EXTRACT(DOW FROM e.date) = 5 THEN cf.nutrient_quantity * e.eat_quantity ELSE 0 END) as viernes, " +
       "SUM(CASE WHEN EXTRACT(DOW FROM e.date) = 6 THEN cf.nutrient_quantity * e.eat_quantity ELSE 0 END) as sabado " +
       "FROM eat e " +
       "INNER JOIN users u ON e.user_id = u.id " +
       "INNER JOIN food f ON e.food_id = f.id " +
       "INNER JOIN composition_food cf ON f.id = cf.food_id " +
       "INNER JOIN nutrient n ON cf.nutrient_id = n.id " +
       "WHERE u.username = :username AND n.name = :nutrientSearch AND " +
       "e.date BETWEEN :startWeekDate AND :endWeekDate", 
       nativeQuery = true)
    List<Object[]> getMacrosReport(
        @Param("username") String username,
        @Param("nutrientSearch") String nutrientSearch,
        @Param("startWeekDate") LocalDateTime startWeekDate,
        @Param("endWeekDate") LocalDateTime endWeekDate);
}