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
    Optional<EatEntity> findByUserIdAndFoodId(Long userId, Long foodId);

    //Obtener todos los alimentos consumidos por un usuario en un día específico
    List<EatEntity> findByUserIdAndDate(Long userId, LocalDate date);

    //Eliminar todos los registros de alimentos consumidos por un usuario en un día específico
    void deleteByUserIdAndDate(Long userId, LocalDate date);

    //Obtener el total de calorías consumidas por un usuario en un día específico
    @Query("SELECT SUM(f.calories * e.quantity) FROM EatEntity e JOIN e.food f WHERE e.userId = :userId AND e.date = :date")
    Double findTotalCaloriesByUserIdAndDate(String userId, LocalDate date);

    //Obtener los días en los que un usuario ha excedido su límite calórico
    @Query("SELECT e.date, SUM(f.calories * e.quantity) FROM EatEntity e JOIN e.food f WHERE e.userId = :userId GROUP BY e.date HAVING SUM(f.calories * e.quantity) > :caloricLimit")
    List<Object[]> findDaysExceedingCaloricLimit(Long userId, Double caloricLimit);



    // Aquí se puede agregar métodos personalizados si es necesario.
}
