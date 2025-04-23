package xyz.brianuceda.monolithfood_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.brianuceda.monolithfood_backend.entities.RoleEntity;
import xyz.brianuceda.monolithfood_backend.enums.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(RoleEnum name);

}
