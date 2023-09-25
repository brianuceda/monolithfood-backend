package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.MonolithFoodApplication.entities.CompositionFoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.CompositionFoodKey;

@Repository
public interface CompositionFoodRepository extends JpaRepository<CompositionFoodEntity, CompositionFoodKey> {
    
}
