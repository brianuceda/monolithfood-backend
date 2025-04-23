package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.edu.upc.MonolithFoodApplication.entities.ObjectiveEntity;

public interface ObjectiveRepository extends JpaRepository<ObjectiveEntity, Long> {

}
