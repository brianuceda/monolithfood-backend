package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.MonolithFoodApplication.entities.CategoryFoodEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryFoodEntity, Long> {
    CategoryFoodEntity findByName(String name);
}
