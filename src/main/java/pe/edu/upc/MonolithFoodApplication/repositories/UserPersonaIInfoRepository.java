package pe.edu.upc.MonolithFoodApplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;

@Repository
public interface UserPersonaIInfoRepository extends JpaRepository<UserPersonalInfoEntity,Long> {

}
