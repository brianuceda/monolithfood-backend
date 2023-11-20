package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.MonolithFoodApplication.entities.NutrientEntity;

@Repository
public interface NutrientRepository extends JpaRepository<NutrientEntity, Long> {
    NutrientEntity findByName(String name);

}
