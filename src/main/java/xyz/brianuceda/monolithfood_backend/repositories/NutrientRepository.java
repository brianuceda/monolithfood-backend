package xyz.brianuceda.monolithfood_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.brianuceda.monolithfood_backend.entities.NutrientEntity;

@Repository
public interface NutrientRepository extends JpaRepository<NutrientEntity, Long> {
    NutrientEntity findByName(String name);

}
