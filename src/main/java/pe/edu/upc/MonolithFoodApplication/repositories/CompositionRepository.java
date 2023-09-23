package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.MonolithFoodApplication.entities.CompositionEntity;
import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;

import java.util.List;

@Repository
public interface CompositionRepository extends JpaRepository<CompositionEntity, Long> {
    // Método para obtener todas las composiciones asociadas a un alimento específico

    List<CompositionEntity> findByFood(FoodEntity food);
}
