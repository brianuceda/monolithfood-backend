package xyz.brianuceda.monolithfood_backend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import xyz.brianuceda.monolithfood_backend.entities.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByOauthProviderId(String oauthProviderId);

    // JPA PARA ESTUDIAR
    // // Verifica si existe un usuario con un nombre de usuario específico.
    // Boolean existsByUsername(String username);
    // // Encuentra todos los usuarios con un apellido específico y los ordena por nombre de forma ascendente.
    // List<UserEntity> findByLastnameOrderByFirstnameAsc(String lastname);
    // // Encuentra todos los usuarios con una edad específica y los ordena por apellido de forma descendente.
    // List<UserEntity> findByAgeOrderByLastnameDesc(Integer age);
    // // Encuentra todos los usuarios que coinciden con un nombre y apellido específicos.
    // List<UserEntity> findByFirstnameAndLastname(String firstname, String lastname);
    // // Encuentra todos los usuarios que coinciden con un nombre o apellido específicos.
    // List<UserEntity> findByFirstnameOrLastname(String firstname, String lastname);
    // // Encuentra todos los usuarios mayores que una edad específica.
    // List<UserEntity> findByAgeGreaterThan(Integer age);
    // // Encuentra todos los usuarios menores que una edad específica.
    // List<UserEntity> findByAgeLessThan(Integer age);
    // // Encuentra todos los usuarios cuyo nombre contiene una cadena específica.
    // List<UserEntity> findByFirstnameLike(String firstname);
    // // Encuentra todos los usuarios cuyo nombre no contiene una cadena específica.
    // List<UserEntity> findByFirstnameNotLike(String firstname);
    // // Encuentra todos los usuarios cuyo nombre comienza con una cadena específica.
    // List<UserEntity> findByFirstnameStartingWith(String prefix);
    // // Encuentra todos los usuarios cuyo nombre termina con una cadena específica.
    // List<UserEntity> findByFirstnameEndingWith(String suffix);
    // // Encuentra todos los usuarios cuyo nombre contiene una cadena específica.
    // List<UserEntity> findByFirstnameContaining(String content);
    // // Encuentra todos los usuarios cuyo nombre, al ignorar mayúsculas y minúsculas, coincide con una cadena específica.
    // List<UserEntity> findByFirstnameIgnoreCase(String firstname);
    // // Encuentra todos los usuarios que están marcados como activos.
    // List<UserEntity> findByActiveTrue();
    // // Encuentra todos los usuarios que están marcados como inactivos.
    // List<UserEntity> findByActiveFalse();
    // // Encuentra todos los usuarios cuya edad está en una lista específica.
    // List<UserEntity> findByAgeIn(List<Integer> ages);
    // // Encuentra todos los usuarios cuya edad no está en una lista específica.
    // List<UserEntity> findByAgeNotIn(List<Integer> ages);
    // // Encuentra todos los usuarios cuyo nombre no es igual a una cadena específica.
    // List<UserEntity> findByFirstnameNot(String firstname);
    // // Encuentra todos los usuarios cuyo nombre no contiene una cadena específica.
    // List<UserEntity> findByFirstnameNotContaining(String content);
    // // Encuentra todos los usuarios cuyo nombre no está en una lista específica.
    // List<UserEntity> findByFirstnameNotIn(List<String> firstnames);
    // // Encuentra todos los usuarios que coinciden con un nombre y apellido específicos, ignorando mayúsculas y minúsculas.
    // List<UserEntity> findByFirstnameAndLastnameAllIgnoreCase(String firstname, String lastname);
    // // Encuentra todos los usuarios que coinciden con un nombre o apellido específicos, ignorando mayúsculas y minúsculas.
    // List<UserEntity> findByFirstnameOrLastnameAllIgnoreCase(String firstname, String lastname);
    // // Encuentra todos los usuarios que coinciden con un nombre y apellido específicos, o una edad específica.
    // List<UserEntity> findByFirstnameAndLastnameOrAge(String firstname, String lastname, Integer age);
    // // Encuentra todos los usuarios que coinciden con un nombre o apellido específicos, y una edad específica.
    // List<UserEntity> findByFirstnameOrLastnameAndAge(String firstname, String lastname, Integer age);
    // // Encuentra todos los usuarios que coinciden con un nombre o apellido específicos, o una edad específica, y los ordena por apellido de forma ascendente.
    // List<UserEntity> findByFirstnameOrLastnameOrAgeOrderByLastnameAsc(String firstname, String lastname, Integer age);
    // // Similar al anterior, pero ordena los resultados por apellido de forma descendente.
    // List<UserEntity> findByFirstnameOrLastnameOrAgeOrderByLastnameDesc(String firstname, String lastname, Integer age);
    // // Encuentra todos los usuarios que coinciden con un nombre o apellido específicos, o una edad específica, y los ordena por apellido de forma ascendente y nombre de forma descendente.
    // List<UserEntity> findByFirstnameOrLastnameOrAgeOrderByLastnameAscFirstnameDesc(String firstname, String lastname, Integer age);
    // // Encuentra todos los usuarios que coinciden con un nombre o apellido específicos, o una edad específica, y los ordena por apellido de forma descendente y nombre de forma ascendente.
    // List<UserEntity> findByFirstnameOrLastnameOrAgeOrderByLastnameDescFirstnameAsc(String firstname, String lastname, Integer age);
}
