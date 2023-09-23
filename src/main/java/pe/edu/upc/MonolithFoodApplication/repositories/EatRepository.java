package pe.edu.upc.MonolithFoodApplication.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pe.edu.upc.MonolithFoodApplication.entities.EatEntity;

@Repository
public interface EatRepository extends JpaRepository<EatEntity, Long> {

    // Método para obtener la ingesta de un alimento específico de un usuario en un día específico
    @Query("FROM EatEntity e WHERE e.user.username = :username AND e.food.id = :foodId AND e.date = :date")
    Optional<EatEntity> findByUsernameAndFoodIdAndDate(String username, Long foodId, LocalDate date);

    //Obtener todos los alimentos consumidos por un usuario en un día específico
    @Query("FROM EatEntity e WHERE e.user.username = :username AND e.date = :date")
    List<EatEntity> findByUsernameAndDate(String username, LocalDate date);

    //Eliminar todos los registros de alimentos consumidos por un usuario en un día específico
    @Query("DELETE FROM EatEntity e WHERE e.user.username = :username AND e.date = :date")
    void deleteByUsernameAndDate(String username, LocalDate date);

    //Obtener el total de calorías consumidas por un usuario en un día específico
    @Query("SELECT SUM(f.calories * e.quantity) FROM EatEntity e JOIN e.food f WHERE e.user.username = :username AND e.date = :date")
    Double findTotalCaloriesByUsernameAndDate(String username, LocalDate date);

    //Obtener los días en los que un usuario ha excedido su límite calórico
    @Query("SELECT e.date, SUM(f.calories * e.quantity) FROM EatEntity e JOIN e.food f WHERE e.user.username = :username GROUP BY e.date HAVING SUM(f.calories * e.quantity) > :caloricLimit")
    List<Object[]> findDaysExceedingCaloricLimitByUsername(String username, Double caloricLimit);

    // Aquí se puede agregar métodos personalizados si es necesario.
}
