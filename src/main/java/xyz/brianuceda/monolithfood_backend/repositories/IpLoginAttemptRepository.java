package xyz.brianuceda.monolithfood_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import xyz.brianuceda.monolithfood_backend.entities.IpLoginAttemptEntity;

@Repository
public interface IpLoginAttemptRepository extends JpaRepository<IpLoginAttemptEntity, Long> {
    // * (SQL Nativo) Brian: Buscar intentos hacia la cuenta de un usuario por IP
    @Query(value = 
        "SELECT * FROM ip_login_attempt " +
        "WHERE ip_address = ?1 " +
        "AND user_id = (SELECT id FROM users WHERE username = ?2)",
        nativeQuery = true
    )
    Optional<IpLoginAttemptEntity> findByIpAddressAndUsername(String ipAddress, String username);

}
