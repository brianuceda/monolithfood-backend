package pe.edu.upc.MonolithFoodApplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.upc.MonolithFoodApplication.entities.EatEntity;

@Repository
public interface EatRepository extends JpaRepository<EatEntity, Long> {

    // JPQL
    @Query(
        "SELECT " + 
            "f.name as name, " + 
            "c.name as category, " + 
            "e.unitOfMeasurement as unitOfMeasurement, " + 
            "e.eatQuantity as quantity, " + 
            "(e.eatQuantity * cf.nutrientQuantity) as calories, " + 
            "e.date as date " +
        "FROM EatEntity e " +
        "JOIN e.user u " +
        "JOIN e.food f " +
        "JOIN f.category c " +
        "LEFT JOIN f.compositions cf " +
        "JOIN cf.nutrient n " +
        "WHERE u.username = :username AND n.name = 'Calorias'"
    )
    List<Object[]> findAllIntakesByUsername(@Param("username") String username);

    

}
