package xyz.brianuceda.monolithfood_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import xyz.brianuceda.monolithfood_backend.entities.UserPersonalInfoEntity;

@Repository
public interface UserPersonalInfoRepository extends JpaRepository<UserPersonalInfoEntity, Long> {

}
