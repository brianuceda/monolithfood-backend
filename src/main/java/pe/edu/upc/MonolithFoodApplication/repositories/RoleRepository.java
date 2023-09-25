package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.edu.upc.MonolithFoodApplication.entities.RoleEntity;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    RoleEntity findByName(RoleEnum name);
}
