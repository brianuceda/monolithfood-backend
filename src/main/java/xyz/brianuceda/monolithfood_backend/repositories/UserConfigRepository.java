package xyz.brianuceda.monolithfood_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.brianuceda.monolithfood_backend.entities.UserConfigEntity;

@Repository
public interface UserConfigRepository extends JpaRepository<UserConfigEntity, Long> {

}
