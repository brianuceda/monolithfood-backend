package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.MonolithFoodApplication.entities.CompositionEntity;
import pe.edu.upc.MonolithFoodApplication.entities.CompositionKey;

@Repository
public interface CompositionRepository extends JpaRepository<CompositionEntity, CompositionKey> {
    
}
