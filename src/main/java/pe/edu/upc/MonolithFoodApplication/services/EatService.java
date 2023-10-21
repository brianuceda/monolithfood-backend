package pe.edu.upc.MonolithFoodApplication.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.AllCategoriesIntakesDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.CategoryIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.IntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.IntakesResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.MacrosDetailedDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.MacrosPerCategoryDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.NewIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.UpdateIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.CategoryIntakeEnum;
import pe.edu.upc.MonolithFoodApplication.entities.EatEntity;
import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UnitOfMeasurementEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.EatRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.FoodRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class EatService {
    private final UserRepository userRepository;
    private final EatRepository eatRepository;
    private final FoodRepository foodRepository;
    private static final Logger logger = LoggerFactory.getLogger(EatService.class);
    
    // * Heather: Obtener todos los macronutrientes consumidos, por consumir, su porcentaje de consumo actual y los alimentos consumidos en un rango de fechas
    public ResponseDTO getAllMacrosAndIntakes(String username, LocalDateTime startDate, LocalDateTime endDate) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if(!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        return new IntakesResponseDTO(
            null, 200,
            (MacrosDetailedDTO) getMacrosDetailed(username, startDate, endDate, false),
            new AllCategoriesIntakesDTO(
                (CategoryIntakeDTO) getCatMacrosAndIntakes(username, CategoryIntakeEnum.DESAYUNO, startDate, endDate, false),
                (CategoryIntakeDTO) getCatMacrosAndIntakes(username, CategoryIntakeEnum.ALMUERZO, startDate, endDate, false),
                (CategoryIntakeDTO) getCatMacrosAndIntakes(username, CategoryIntakeEnum.CENA, startDate, endDate, false)
            )
        );
    }
    // * Heather: Obtener los macronutrientes consumidos y los alimentos consumidos en un rango de fechas
    public ResponseDTO getCatMacrosAndIntakes(String username, CategoryIntakeEnum category, LocalDateTime startDate, LocalDateTime endDate, Boolean isSpecificCategory) {
        // Verifica que el usuario exista
        if (isSpecificCategory == true) {
            Optional<UserEntity> optUser = userRepository.findByUsername(username);
            if(!optUser.isPresent())
                return new ResponseDTO("Usuario no encontrado.", 404);
        }
        MacrosPerCategoryDTO macrosConsumedPerCategory = eatRepository.findMacrosPerCategory(username, category, startDate, endDate);
        if (macrosConsumedPerCategory == null)
            macrosConsumedPerCategory = new MacrosPerCategoryDTO(0.0, 0.0, 0.0, 0.0);
        macrosConsumedPerCategory.roundAllValues();
        List<Object[]> results = eatRepository.findIntakesPerCategory(username, category, startDate, endDate);
        List<IntakeDTO> myIntakes = new ArrayList<>();
        if (results.isEmpty()) {
            myIntakes.add(new IntakeDTO("No has registrado ningun alimento."));
        }
        else {
            myIntakes = results.stream().map(result -> {
                return new IntakeDTO(
                    (Long) result[0],
                    (String) result[1],
                    (String) result[2],
                    (UnitOfMeasurementEnum) result[3],
                    (Double) result[4],
                    ((Timestamp) result[5]).toLocalDateTime()
                );
            }).collect(Collectors.toList());
        }
        if (isSpecificCategory == true)
            return new CategoryIntakeDTO(null, 200, macrosConsumedPerCategory, myIntakes);
        else
            return new CategoryIntakeDTO(null, null, macrosConsumedPerCategory, myIntakes);
    }
    // * Heather: Obtener todos los macronutrientes consumidos, por consumir y su porcentaje de consumo actual
    public ResponseDTO getMacrosDetailed(String username, LocalDateTime startDate, LocalDateTime endDate, Boolean isSeparatedSearch) {
        // Verifica que el usuario exista
        if (isSeparatedSearch == true) {
            Optional<UserEntity> optUser = userRepository.findByUsername(username);
            if(!optUser.isPresent())
                return new ResponseDTO("Usuario no encontrado.", 404);
        }
        MacrosDetailedDTO dto = eatRepository.getMacrosDetailed(username, startDate, endDate);        
        if (dto == null)
            if (isSeparatedSearch == true)
                return new MacrosDetailedDTO(null, 200, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
            else
                return new MacrosDetailedDTO(null, null, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        dto.roundAllValues();
        dto.setMessage(null);
        dto.setStatusCode(isSeparatedSearch ? 200 : null);
        return dto;
    }
    // * Heather: Agregar un alimento a la lista de alimentos consumidos por un usuario
    @Transactional
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
        // Fecha de la ingesta
        LocalDateTime dateTimeOfEat = foodIntakeDTO.getDate().toLocalDateTime().plusHours(5);
        foodIntakeDTO.setDate(Timestamp.valueOf(dateTimeOfEat));
        newEat.setDate(foodIntakeDTO.getDate());
        // Calculo de fecha
        LocalTime timeOfEat = dateTimeOfEat.toLocalTime();
        if (timeOfEat.isAfter(LocalTime.of(2, 0)) && timeOfEat.isBefore(LocalTime.of(12, 0)))
            newEat.setCategoryIntake(CategoryIntakeEnum.DESAYUNO);
        else if (timeOfEat.isAfter(LocalTime.of(11, 59, 59, 999)) && timeOfEat.isBefore(LocalTime.of(19, 0)))
            newEat.setCategoryIntake(CategoryIntakeEnum.ALMUERZO);
        else
            newEat.setCategoryIntake(CategoryIntakeEnum.CENA);
        // else return new ResponseDTO("Fecha de registro invalida.", 400);
        // Guardar EatEntity en la lista de Alimentos del usuario
        user.getEats().add(newEat);
        // Guardar cambios en la base de datos
        userRepository.save(user);
        // Retornar mensaje de éxito
        logger.info("Alimento registrado correctamente para el usuario " + username + ".");
        return new ResponseDTO("Alimento registrado correctamente.", 200);
    }
    // * Heather: Actualizar un alimento de la lista de alimentos consumidos por un usuario
    @Transactional
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
            logger.error("El usuario " + username + " no tiene permisos para actualizar el registro " + newFoodIntakeDTO.getEatId() + ".");
            return new ResponseDTO("No tienes permisos para actualizar este registro.", 403);
        }
        // Si el elemento de eat tiene .getFood() == null, significa que es un registro de ingesta de una receta
        if(newEat.getFood() == null) {
            logger.error("Aún no se puede actualizar el registro de ingesta de una receta para el usuario " + username + ".");
            return new ResponseDTO("Aún no se puede actualizar el registro de ingesta de una receta.", 400);
        }
        // Lista para guardar los campos que el usuario haya actualizado
        List<String> updatedFields = new ArrayList<>();
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
    @Transactional
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
            logger.error("El usuario " + username + " no tiene permisos para eliminar el registro " + eatId + ".");
            return new ResponseDTO("No tienes permisos para eliminar este registro.", 403);
        }
        // Eliminar el registro de ingesta de la lista de ingestas del usuario
        try {
            eatRepository.deleteId(eatToDelete.getId());
            logger.info("Registro de ingesta eliminado correctamente para el usuario " + username + ".");
            return new ResponseDTO("Registro eliminado correctamente.", 200);
        } catch (Exception e) {
            logger.error("Error al eliminar el registro de ingesta del usuario " + username + ": " + e.getMessage());
            return new ResponseDTO("Error al eliminar el registro de ingesta.", 500);
        }
    }
}
