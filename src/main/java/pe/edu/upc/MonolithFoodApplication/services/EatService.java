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
import pe.edu.upc.MonolithFoodApplication.dtos.foodintake.DetailedIntakeDTO;
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
    // * Heather: Obtener todos los macronutrientes consumidos, por consumir, su porcentaje de consumo actual y los alimentos consumidos en un rango de fechas de UNA categoría de ingesta (desayuno, almuerzo o cena)
    public ResponseDTO getSpecificMacrosAndIntakes(String username, CategoryIntakeEnum category, LocalDateTime startDate, LocalDateTime endDate) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if(!optUser.isPresent())
            return new ResponseDTO("Usuario no encontrado.", 404);
        AllCategoriesIntakesDTO specificCategory = new AllCategoriesIntakesDTO();
        if (category == CategoryIntakeEnum.DESAYUNO)
            specificCategory.setDesayuno(getCatMacrosAndIntakes(username, CategoryIntakeEnum.DESAYUNO, startDate, endDate));
        else if (category == CategoryIntakeEnum.ALMUERZO)
            specificCategory.setAlmuerzo(getCatMacrosAndIntakes(username, CategoryIntakeEnum.ALMUERZO, startDate, endDate));
        else if (category == CategoryIntakeEnum.CENA)
            specificCategory.setCena(getCatMacrosAndIntakes(username, CategoryIntakeEnum.CENA, startDate, endDate));
        return new IntakesResponseDTO(
            null, 200,
            (MacrosDetailedDTO) getMacrosDetailed(username, startDate, endDate, false), specificCategory
        );
    }
    // * Heather: Obtener todos los macronutrientes consumidos, por consumir, su porcentaje de consumo actual y los alimentos consumidos en un rango de fechas de TODAS las categorías de ingestas (desayuno, almuerzo y cena)
    public ResponseDTO getAllMacrosAndIntakes(String username, LocalDateTime startDate, LocalDateTime endDate) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if(!optUser.isPresent())
            return new ResponseDTO("Usuario no encontrado.", 404);
        return new IntakesResponseDTO(
            null, 200,
            (MacrosDetailedDTO) getMacrosDetailed(username, startDate, endDate, false),
            new AllCategoriesIntakesDTO(
                getCatMacrosAndIntakes(username, CategoryIntakeEnum.DESAYUNO, startDate, endDate),
                getCatMacrosAndIntakes(username, CategoryIntakeEnum.ALMUERZO, startDate, endDate),
                getCatMacrosAndIntakes(username, CategoryIntakeEnum.CENA, startDate, endDate)
            )
        );
    }
    // * Heather: Obtener la información de un alimento consumido
    public ResponseDTO getDetailedIntake(String username, Long id) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if(!optUser.isPresent())
            return new ResponseDTO("Usuario no encontrado.", 404);
        DetailedIntakeDTO dto = eatRepository.findDetailedIntake(username, id);
        if (dto == null)
            return new ResponseDTO("Registro de ingesta no encontrado.", 404);
        dto.minusHours(5);
        dto.roundAllValues();
        dto.noMessageAndStatusCode();
        return dto;
    }
    // * Heather: Agregar un alimento a la lista de alimentos consumidos
    @Transactional
    public ResponseDTO addFoodIntake(String username, NewIntakeDTO foodIntakeDTO) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if(!optUser.isPresent()) {
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        Optional<FoodEntity> getFood = foodRepository.findById(foodIntakeDTO.getFoodId());
        if(!getFood.isPresent()) {
            return new ResponseDTO("Alimento no encontrado.", 404);
        }
        UserEntity user = optUser.get();
        EatEntity newEat = new EatEntity();
        newEat.setUser(user);
        newEat.setFood(getFood.get());
        newEat.setEatQuantity(foodIntakeDTO.getQuantity());
        newEat.setUnitOfMeasurement(foodIntakeDTO.getUnitOfMeasurement());
        LocalDateTime dateTimeOfEat = foodIntakeDTO.getDate().toLocalDateTime().plusHours(5);
        newEat.setDate(Timestamp.valueOf(dateTimeOfEat));
        newEat.setCategoryIntake(calculateCategory(dateTimeOfEat));
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
    public ResponseDTO updateFoodIntake(String username, UpdateIntakeDTO niDTO) {
        // Verifica que todos los campos estén completos
        if(
            niDTO.getEatId() == null ||
            niDTO.getFoodId() == null ||
            (niDTO.getQuantity() == null || niDTO.getQuantity() <= 0.0) ||
            niDTO.getUnitOfMeasurement() == null ||
            niDTO.getDate() == null
        ) {
            return new ResponseDTO("Faltan campos por completar.", 400);
        }
        // Verifica que el registro de ingesta que se está intentando actualizar, exista y sea del usuario
        Optional<EatEntity> eat = eatRepository.findById(niDTO.getEatId());
        if(!eat.isPresent() || eat.get().getUser().getUsername() != username)
            return new ResponseDTO("Registro de ingesta no encontrado.", 404);
        // Obtiene el registro de ingesta actual del usuario
        Optional<EatEntity> getEat = eatRepository.findById(niDTO.getEatId());
        // Obtiene el nuevo alimento que se quiere consumir
        Optional<FoodEntity> getFood = foodRepository.findById(niDTO.getFoodId());
        if(!getFood.isPresent())
            return new ResponseDTO("Alimento no encontrado.", 404);
        // Es receta
        EatEntity newEat = getEat.get();
        if(newEat.getFood() == null) {
            return new ResponseDTO("Aún no se puede actualizar el registro de ingesta de una receta.", 400);
        }
        // Actualizar TODOS los campos
        newEat.setFood(getFood.get());
        newEat.setEatQuantity(niDTO.getQuantity());
        newEat.setUnitOfMeasurement(niDTO.getUnitOfMeasurement());
        LocalDateTime dateTimeOfEat = niDTO.getDate().toLocalDateTime().plusHours(5);
        newEat.setDate(Timestamp.valueOf(dateTimeOfEat));
        newEat.setCategoryIntake(calculateCategory(dateTimeOfEat));
        try {
            eatRepository.save(newEat);
            return new ResponseDTO("Registro actualizado correctamente.", 200);
        } catch (Exception e) {
            return new ResponseDTO("Error al actualizar el registro.", 500);
        }
    }
    // * Heather: Quitar un alimento de la lista de alimentos consumidos por un usuario
    @Transactional
    public ResponseDTO deleteFoodIntake(String username, Long eatId) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent())
            return new ResponseDTO("Usuario no encontrado.", 404);
        // Verificar que el registro de ingesta exista
        Optional<EatEntity> getEat = eatRepository.findById(eatId);
        if (!getEat.isPresent() || !getEat.get().getUser().getUsername().equals(username))
            return new ResponseDTO("Registro de ingesta no encontrado.", 404);
        try {
            eatRepository.deleteId(getEat.get().getId());
            return new ResponseDTO("Registro eliminado correctamente.", 200);
        } catch (Exception e) {
            return new ResponseDTO("Error al eliminar el registro de ingesta.", 500);
        }
    }

    // ? Funciones
    // Obtener los macronutrientes consumidos y los alimentos consumidos en un rango de fechas
    public CategoryIntakeDTO getCatMacrosAndIntakes(String username, CategoryIntakeEnum category, LocalDateTime startDate, LocalDateTime endDate) {
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
        return new CategoryIntakeDTO(null, null, macrosConsumedPerCategory, myIntakes);
    }
    // Calcular fecha de ingesta
    public CategoryIntakeEnum calculateCategory(LocalDateTime dateTimeOfEat) {
        LocalTime timeOfEat = dateTimeOfEat.toLocalTime();
        if (timeOfEat.isAfter(LocalTime.of(2, 0)) && timeOfEat.isBefore(LocalTime.of(12, 0)))
            return CategoryIntakeEnum.DESAYUNO;
        else if (timeOfEat.isAfter(LocalTime.of(11, 59, 59, 999)) && timeOfEat.isBefore(LocalTime.of(19, 0)))
            return CategoryIntakeEnum.ALMUERZO;
        else
            return CategoryIntakeEnum.CENA;
    }
}
