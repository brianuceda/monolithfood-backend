package pe.edu.upc.MonolithFoodApplication.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.SimpleObjectDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.InfoDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.ObjectivesResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.PhotoDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.GetIntakesDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.IntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.NewIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.UpdateIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.entities.EatEntity;
import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UnitOfMeasurementEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.ObjectiveEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.EatRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.FoodRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.ObjectiveRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    // ? Atributos
    // Inyección de dependencias
    private final UserRepository userRepository;
    private final ObjectiveRepository objectiveRepository;
    private final EatRepository eatRepository;
    private final FoodRepository foodRepository;
    // Log de errores y eventos
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // ? Metodos
    // * Heather: Obtener todos los alimentos consumidos por un usuario
    public ResponseDTO getAllFoodIntake(String username) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if(!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Se obtiene una lista de Object[] con los resultados de la consulta SQL personalizada
        List<Object[]> results = eatRepository.findAllIntakesByUsername(username);
        if(results.isEmpty()) return new ResponseDTO("No se encontraron alimentos consumidos por el usuario", 200);
        // Mapea el resultado manualmente de una lista de Object[] a una lista de IntakeDTO
        List<IntakeDTO> myIntakes = results.stream().map(result -> {
            return new IntakeDTO(
                (String) result[0],
                (String) result[1],
                (UnitOfMeasurementEnum) result[2],
                (Double) result[3],
                (Double) result[4],
                (Timestamp) result[5]
            );
        }).collect(Collectors.toList());
        return new GetIntakesDTO("Alimentos recuperados correctamente.", 200, myIntakes);
    }
    // * Heather Obtener todos los alimentos consumidos por un usuario entre dos fechas
    public ResponseDTO getAllFoodIntakeBetweenDates(String username, LocalDateTime startDate, LocalDateTime endDate) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if(!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Se obtiene una lista de Object[] con los resultados de la consulta SQL personalizada
        List<Object[]> results = eatRepository.findAllIntakesByUsernameBetweenDates(username, startDate, endDate);
        if(results.isEmpty()) return new ResponseDTO("No se encontraron alimentos consumidos entre las fechas indicadas.", 200);
        // Mapea el resultado manualmente de una lista de Object[] a una lista de IntakeDTO
        List<IntakeDTO> myIntakes = results.stream().map(result -> {
            return new IntakeDTO(
                (String) result[0],
                (String) result[1],
                (UnitOfMeasurementEnum) result[2],
                (Double) result[3],
                (Double) result[4],
                (Timestamp) result[5]
            );
        }).collect(Collectors.toList());
        return new GetIntakesDTO("Alimentos recuperados correctamente.", 200, myIntakes);
    }
    // * Heather: Agregar un alimento a la lista de alimentos consumidos por un usuario
    public ResponseDTO addFoodIntake(String username, NewIntakeDTO foodIntakeDTO) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if(!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        Optional<FoodEntity> getFood = foodRepository.findByName(foodIntakeDTO.getName());
        if(!getFood.isPresent()) {
            logger.error("Alimento no encontrado.");
            return new ResponseDTO("Alimento no encontrado.", 404);
        }
        UserEntity user = optUser.get();
        EatEntity newEat = new EatEntity();
        newEat.setUser(user);
        newEat.setFood(getFood.get());
        newEat.setEatQuantity(foodIntakeDTO.getQuantity());
        newEat.setUnitOfMeasurement(foodIntakeDTO.getUnitOfMeasurement());
        newEat.setDate(foodIntakeDTO.getDate());
        // Guardar EatEntity en la lista de Alimentos del usuario
        user.getEats().add(newEat);
        // Guardar cambios en la base de datos
        userRepository.save(user);
        // Retornar mensaje de éxito
        logger.info("Alimento registrado correctamente para el usuario " + username + ".");
        return new ResponseDTO("Alimento registrado correctamente.", 200);
    }
    // * Heather: Actualizar un alimento de la lista de alimentos consumidos por un usuario
    public ResponseDTO updateFoodIntake(String username, UpdateIntakeDTO newFoodIntakeDTO) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if(!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Verifica que el registro de ingesta exista
        Optional<EatEntity> getEat = eatRepository.findById(newFoodIntakeDTO.getEatId());
        if(!getEat.isPresent()) {
            logger.error("Registro de ingesta no encontrado.");
            return new ResponseDTO("Registro de ingesta no encontrado.", 404);
        }
        // Verifica que el alimento exista
        Optional<FoodEntity> getFood = foodRepository.findByName(newFoodIntakeDTO.getName());
        if(!getFood.isPresent()) {
            logger.error("Alimento no encontrado.");
            return new ResponseDTO("Alimento no encontrado.", 404);
        }
        // Si la solicitud realizada pertenece al usuario que la realizó
        EatEntity newEat = getEat.get();
        if(!newEat.getUser().getUsername().equals(username)) {
            logger.error("No tiene permisos para actualizar este registro.");
            return new ResponseDTO("No tiene permisos para actualizar este registro.", 403);
        }
        // Lista para guardar los campos que el usuario haya actualizado
        List<String> updatedFields = new ArrayList<>();
        // Si el elemento de eat tiene .getFood() == null, significa que es un registro de ingesta de una receta
        if(newEat.getFood() == null) {
            logger.error("No se puede actualizar el registro de ingesta de una receta para el usuario " + username + ".");
            return new ResponseDTO("No se puede actualizar el registro de ingesta de una receta.", 400);
        }
        // Actualizar los campos que el usuario haya ingresado
        newEat.setUser(optUser.get());
        if(newFoodIntakeDTO.getName() != null && !newFoodIntakeDTO.getName().isEmpty()) {
            if(!newEat.getFood().getName().equals(newFoodIntakeDTO.getName())) {
                newEat.setFood(getFood.get());
                updatedFields.add("Alimento");
            }
        }
        if(newFoodIntakeDTO.getQuantity() != null && newFoodIntakeDTO.getQuantity() > 0) {
            if(!newEat.getEatQuantity().equals(newFoodIntakeDTO.getQuantity())) {
                newEat.setEatQuantity(newFoodIntakeDTO.getQuantity());
                updatedFields.add("Cantidad");
            }
        }
        if(newFoodIntakeDTO.getUnitOfMeasurement() != null) {
            if(!newEat.getUnitOfMeasurement().equals(newFoodIntakeDTO.getUnitOfMeasurement())) {
                newEat.setUnitOfMeasurement(newFoodIntakeDTO.getUnitOfMeasurement());
                updatedFields.add("Unidad de medida");
            }
        }
        if(newFoodIntakeDTO.getDate() != null) {
            if(!newEat.getDate().equals(newFoodIntakeDTO.getDate())) {
                newEat.setDate(newFoodIntakeDTO.getDate());
                updatedFields.add("Fecha");
            }
        }
        try {
            // Si la lista de campos actualizados no está vacía, se guarda en la BD
            if(updatedFields.size() != 0) {
                eatRepository.save(getEat.get());
                logger.info("Registro de ingesta actualizado correctamente para el usuario " + username + ".");
            }
            // Si no se actualizó ningún campo, no se guarda en la BD
            else {
                logger.info("No se eactualizó ningún campo para el usuario " + username + ".");
                return new ResponseDTO("No se actualizó ningún campo.", 200);
            }
            return new ResponseDTO("Registro de ingesta actualizado correctamente.", 200);
        } catch (Exception e) {
            logger.error("Error al actualizar el registro de ingesta del usuario " + username + ": " + e.getMessage());
            return new ResponseDTO("Error al actualizar el registro de ingesta.", 500);
        }
    }
    // * Heather: Quitar un alimento de la lista de alimentos consumidos por un usuario
    public ResponseDTO deleteFoodIntake(String username, Long eatId) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Verificar que el registro de ingesta exista
        Optional<EatEntity> getEat = eatRepository.findById(eatId);
        if (!getEat.isPresent()) {
            logger.error("Registro de ingesta no encontrado.");
            return new ResponseDTO("Registro de ingesta no encontrado.", 404);
        }
        // Si la solicitud realizada pertenece al usuario que la realizó
        EatEntity eatToDelete = getEat.get();
        if (!eatToDelete.getUser().getUsername().equals(username)) {
            logger.error("No tiene permisos para eliminar este registro.");
            return new ResponseDTO("El usuario " + username + " no tiene permisos para eliminar el registro " + eatId + ".", 403);
        }
        // Eliminar el registro de ingesta de la lista de ingestas del usuario
        try {
            eatRepository.delete(eatToDelete);
            logger.info("Registro de ingesta eliminado correctamente para el usuario " + username + ".");
            return new ResponseDTO("Registro de ingesta eliminado correctamente.", 200);
        } catch (Exception e) {
            logger.error("Error al eliminar el registro de ingesta del usuario " + username + ": " + e.getMessage());
            return new ResponseDTO("Error al eliminar el registro de ingesta.", 500);
        }
    }
    // * Naydeline: Obtener información personal del usuario
    public ResponseDTO getInformation(String username) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Retorna la información del usuario
        UserEntity user = optUser.get();
        return new InfoDTO("Información recuperada correctamente.", 200,
            user.getUsername(),
            user.getEmail(),
            user.getNames(),
            user.getSurnames(),
            user.getProfileImg()
        );
    }
    // * Naydeline: Actualizar la foto de perfil del usuario
    public ResponseDTO updatePhoto(String username, String photoUrl) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        UserEntity user = optUser.get();
        if (photoUrl == null || photoUrl.isEmpty()) {
            logger.error("El usuario " + username + " no envió ninguna foto para actualizar.");
            return new ResponseDTO("Debes enviar una foto.", 400);
        }
        if (user.getProfileImg().equals(photoUrl)) {
            logger.error("El usuario " + username + " ya tiene esa foto de perfil.");
            return new ResponseDTO("Ya tienes esa foto de perfil.", 400);
        }
        // Actualiza y guarda la foto de perfil del usuario
        user.setProfileImg(photoUrl);
        userRepository.save(user);
        return new PhotoDTO("Foto actualizada correctamente.", 200, photoUrl);
    }
    // * Brian: Obtener todos los objetivos de la base de datos
    public ResponseDTO getAllObjectives() {
        List<ObjectiveEntity> objectives = objectiveRepository.findAll();
        if (objectives.isEmpty()) {
            logger.error("No se encontraron objetivos.");
            return new ResponseDTO("No se encontraron objetivos.", 404);
        }
        List<SimpleObjectDTO> objectiveDTOs = objectives.stream()
                .map(obj -> new SimpleObjectDTO(obj.getName(), obj.getInformation()))
                .collect(Collectors.toList());
        return new ObjectivesResponseDTO("Objetivos recuperados correctamente.", 200, objectiveDTOs);
    }
    // * Brian: Obtener todos los objetivos de un usuario
    public ResponseDTO getMyObjectives(String username) {
        // Verificar que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        List<ObjectiveEntity> objectives = optUser.get().getObjectives();
        // Obtener todos los objetivos de la base de datos
        List<SimpleObjectDTO> objectiveDTOs = objectives.stream()
                .map(obj -> new SimpleObjectDTO(obj.getName(), obj.getInformation()))
                .collect(Collectors.toList());
        return new ObjectivesResponseDTO("Objetivos recuperados correctamente.", 200, objectiveDTOs);
    }
    // * Brian: Actualizar los objetivos de un usuario
    public ResponseDTO selectObjectives(String username, List<String> objectives) {
        // Verificar que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        UserEntity user = optUser.get();
        // Verificar si la lista de objetivos está vacía
        if (objectives.isEmpty()) {
            logger.error("No se seleccionó ningún objetivo.");
            return new ResponseDTO("Debes seleccionar al menos un objetivo.", 400);
        }
        List<ObjectiveEntity> allObjectives = objectiveRepository.findAll();
        // Validar que todos los objetivos enviados existen en la base de datos
        for (String objective : objectives) {
            boolean exists = allObjectives.stream().anyMatch(o -> o.getName().equals(objective));
            if (!exists) {
                logger.error("No se encontró uno de los objetivos ingresados.");
                return new ResponseDTO("No se encontro uno de los objetivos ingresados.", 404);
            }
        }
        // Filtrar los objetivos que coinciden con los enviados por el usuario
        List<ObjectiveEntity> newUserObjectives = allObjectives.stream()
                .filter(o -> objectives.contains(o.getName()))
                .collect(Collectors.toList());
        // Guardar los objetivos en el usuario
        user.setObjectives(newUserObjectives);
        userRepository.save(user);
        // Convertir la lista de ObjectiveEntity a SimpleObjectDTO
        List<SimpleObjectDTO> savedObjectives = newUserObjectives.stream()
                .map(entity -> new SimpleObjectDTO(entity.getName(), entity.getInformation()))
                .collect(Collectors.toList());
        return new ObjectivesResponseDTO("Objetivos guardados correctamente.", 200, savedObjectives);
    }
  
}
