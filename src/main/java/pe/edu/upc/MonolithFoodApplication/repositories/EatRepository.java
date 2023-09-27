package pe.edu.upc.MonolithFoodApplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.MonolithFoodApplication.entities.EatEntity;

@Repository
public interface EatRepository extends JpaRepository<EatEntity, Long> {
    //JPQL para retornar el promedio de calorias consumidas en la ultima semana 
    @Query("SELECT username, SUM(cf.nutrientQuantity) " +
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
    "GROUP BY username")
List<Object[]> AveragecaloriesLastWeek(@Param("username") String username);

//JPQL para retornar el promedio de calorias consumidas en la ultima semana
@Query("SELECT u.username as username, " +  
    //"FUNCTION('to_char', e.date as Date, 'Dy DD/MM/YYYY') as diaFecha, " +
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
    "ORDER BY date")
List<Object[]> AverageCalorieConsumptioDay(@Param("username") String username);

}
