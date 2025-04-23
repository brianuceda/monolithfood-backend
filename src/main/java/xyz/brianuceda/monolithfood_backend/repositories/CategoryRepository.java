package xyz.brianuceda.monolithfood_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.brianuceda.monolithfood_backend.entities.CategoryFoodEntity;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryFoodEntity, Long> {
    CategoryFoodEntity findByName(String name);

}
