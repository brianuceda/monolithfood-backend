package xyz.brianuceda.monolithfood_backend.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import xyz.brianuceda.monolithfood_backend.dtos.foodintake.AllCategoriesIntakesDTO;
import xyz.brianuceda.monolithfood_backend.dtos.foodintake.CategoryIntakeDTO;
import xyz.brianuceda.monolithfood_backend.dtos.foodintake.DetailedIntakeDTO;
import xyz.brianuceda.monolithfood_backend.dtos.foodintake.IntakeDTO;
import xyz.brianuceda.monolithfood_backend.dtos.foodintake.IntakesResponseDTO;
import xyz.brianuceda.monolithfood_backend.dtos.foodintake.MacrosDetailedDTO;
import xyz.brianuceda.monolithfood_backend.dtos.foodintake.MacrosPerCategoryDTO;
import xyz.brianuceda.monolithfood_backend.dtos.foodintake.NewIntakeDTO;
import xyz.brianuceda.monolithfood_backend.dtos.foodintake.UpdateIntakeDTO;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.dtos.searches.NutrientDTO;
import xyz.brianuceda.monolithfood_backend.entities.EatEntity;
import xyz.brianuceda.monolithfood_backend.entities.FoodEntity;
import xyz.brianuceda.monolithfood_backend.entities.UserEntity;
import xyz.brianuceda.monolithfood_backend.entities.UserFitnessInfoEntity;
import xyz.brianuceda.monolithfood_backend.enums.CategoryIntakeEnum;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;
import xyz.brianuceda.monolithfood_backend.enums.UnitOfMeasurementEnum;
import xyz.brianuceda.monolithfood_backend.repositories.EatRepository;
import xyz.brianuceda.monolithfood_backend.repositories.FoodRepository;
import xyz.brianuceda.monolithfood_backend.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class EatService {
    private final UserRepository userRepository;
    private final EatRepository eatRepository;
    private final FoodRepository foodRepository;
    private static final Logger logger = LoggerFactory.getLogger(EatService.class);
    public static int hoursToSubtract = 5;

    @Value("${app.production}")
    private Boolean isInProduction;
    
    // * Heather: Obtener todos los macronutrientes consumidos, por consumir y su porcentaje de consumo actual
    public ResponseDTO getMacrosDetailed(String username, LocalDateTime startDate, LocalDateTime endDate, Boolean isSeparatedSearch) {
        // Verifica que el usuario exista
            Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (isSeparatedSearch == true) {
            if(!optUser.isPresent()) {
                logger.info("Usuario encontrado");
                return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
            }
        }
        UserFitnessInfoEntity ufi = optUser.get().getUserFitnessInfo();
        if (ufi == null) {
            return new ResponseDTO("Completa tu información fitness", HttpStatus.BAD_REQUEST.value(), ResponseType.ERROR);
        }
        MacrosDetailedDTO dto = eatRepository.getMacrosDetailed(username, startDate, endDate);
        if (dto == null) {
            MacrosDetailedDTO data = new MacrosDetailedDTO(null, null, null, 0.0, ufi.getDailyCaloricIntake(), 0.0, ufi.getDailyProteinIntake(), 0.0, ufi.getDailyCarbohydrateIntake() ,0.0, ufi.getDailyFatIntake());
            if (isSeparatedSearch == true) {
                data.setMessage(null);
                data.setStatusCode(200);
            }
            else {
                data.setMessage(null);
                data.setStatusCode(null);
                data.setType(null);
            }
            return data;
        }
        dto.roundAllValues();
        dto.setMessage(null);
        dto.setStatusCode(isSeparatedSearch ? 200 : null);
        dto.setType(isSeparatedSearch ? ResponseType.SUCCESS : null);
        return dto;
    }
    // * Heather: Obtener todos los macronutrientes consumidos, por consumir, su porcentaje de consumo actual y los alimentos consumidos en un rango de fechas de UNA categoría de ingesta (desayuno, almuerzo o cena)
    public ResponseDTO getSpecificMacrosAndIntakes(String username, CategoryIntakeEnum category, LocalDateTime startDate, LocalDateTime endDate) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if(!optUser.isPresent())
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        AllCategoriesIntakesDTO specificCategory = new AllCategoriesIntakesDTO();
        if (category == CategoryIntakeEnum.DESAYUNO)
            specificCategory.setDesayuno(getCatMacrosAndIntakes(username, CategoryIntakeEnum.DESAYUNO, startDate, endDate));
        else if (category == CategoryIntakeEnum.ALMUERZO)
            specificCategory.setAlmuerzo(getCatMacrosAndIntakes(username, CategoryIntakeEnum.ALMUERZO, startDate, endDate));
        else if (category == CategoryIntakeEnum.CENA)
            specificCategory.setCena(getCatMacrosAndIntakes(username, CategoryIntakeEnum.CENA, startDate, endDate));
        return new IntakesResponseDTO(
            null, HttpStatus.OK.value(), ResponseType.SUCCESS,
            (MacrosDetailedDTO) getMacrosDetailed(username, startDate, endDate, false), specificCategory
        );
    }
    // * Heather: Obtener todos los macronutrientes consumidos, por consumir, su porcentaje de consumo actual y los alimentos consumidos en un rango de fechas de TODAS las categorías de ingestas (desayuno, almuerzo y cena)
    public ResponseDTO getAllMacrosAndIntakes(String username, LocalDateTime startDate, LocalDateTime endDate) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
    
        AllCategoriesIntakesDTO allCategoriesIntakesDTO = new AllCategoriesIntakesDTO(
            getCatMacrosAndIntakes(username, CategoryIntakeEnum.DESAYUNO, startDate, endDate),
            getCatMacrosAndIntakes(username, CategoryIntakeEnum.ALMUERZO, startDate, endDate),
            getCatMacrosAndIntakes(username, CategoryIntakeEnum.CENA, startDate, endDate)
        );
    
        // Imprimir las fechas de las ingestas para cada categoría
        printDatesOfIntakes("Desayuno", allCategoriesIntakesDTO.getDesayuno());
        printDatesOfIntakes("Almuerzo", allCategoriesIntakesDTO.getAlmuerzo());
        printDatesOfIntakes("Cena", allCategoriesIntakesDTO.getCena());

        // Calibrar las horas de las ingestas con Perú
        calibrateHoursWithPeru(allCategoriesIntakesDTO.getDesayuno());
        calibrateHoursWithPeru(allCategoriesIntakesDTO.getAlmuerzo());
        calibrateHoursWithPeru(allCategoriesIntakesDTO.getCena());
    
        return new IntakesResponseDTO(
            null, HttpStatus.OK.value(), ResponseType.SUCCESS,
            (MacrosDetailedDTO) getMacrosDetailed(username, startDate, endDate, false),
            allCategoriesIntakesDTO
        );
    }
    
    // * Heather: Obtener la información de un alimento consumido
    public ResponseDTO getDetailedIntake(String username, Long id) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if(!optUser.isPresent())
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        DetailedIntakeDTO dto = eatRepository.findDetailedIntake(username, id);
        if (dto == null)
            return new ResponseDTO("Registro no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        dto.minusHours(5);
        List<Object[]> nutrients = foodRepository.findNutrientsOfFood(dto.getFoodId());
        if (nutrients.isEmpty())
            return new ResponseDTO("Registro no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        dto.setFoodId(null);
        // Convierte la tupla a una lista de DTOs
        List<NutrientDTO> nutrientsOfFood = nutrients.stream()
            .map(n -> new NutrientDTO(
                (Long) n[0],
                (String) n[1],
                this.round((Double) n[2]),
                (UnitOfMeasurementEnum) n[3],
                (String) n[4]
            ))
            .collect(Collectors.toList());
        dto.setNutrients(nutrientsOfFood);
        dto.noMessageAndStatusCode();
        return dto;
    }

    // * Heather: Agregar un alimento a la lista de alimentos consumidos
    @Transactional
    public ResponseDTO addFoodIntake(String username, NewIntakeDTO foodIntakeDTO) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if(!optUser.isPresent()) {
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        Optional<FoodEntity> getFood = foodRepository.findById(foodIntakeDTO.getFoodId());
        if(!getFood.isPresent()) {
            return new ResponseDTO("Alimento no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = optUser.get();
        EatEntity newEat = new EatEntity();
        newEat.setUser(user);
        newEat.setFood(getFood.get());
        newEat.setEatQuantity(foodIntakeDTO.getQuantity());
        newEat.setUnitOfMeasurement(foodIntakeDTO.getUnitOfMeasurement());
        
        // Convertir la fecha a UTC antes de guardarla
        LocalDateTime dateTimeOfEat = foodIntakeDTO.getDate().toLocalDateTime();
        ZonedDateTime zonedDateTime = dateTimeOfEat.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
        newEat.setDate(Timestamp.valueOf(zonedDateTime.toLocalDateTime()));

        // Calcular la categoría de la ingesta
        dateTimeOfEat = dateTimeOfEat.plusHours(hoursToSubtract);
        newEat.setCategoryIntake(calculateCategory(dateTimeOfEat));

        user.getEats().add(newEat);
        userRepository.save(user);
        return new ResponseDTO("Alimento registrado exitosamente", HttpStatus.OK.value(), ResponseType.SUCCESS);
    }

    // * Heather: Actualizar un alimento de la lista de alimentos consumidos por un usuario
    @Transactional
    public ResponseDTO updateFoodIntake(String username, UpdateIntakeDTO niDTO) {
        // Verifica que todos los campos estén completos
        if(
            niDTO.getEatId() == null ||
            (niDTO.getQuantity() == null || niDTO.getQuantity() <= 0.0) ||
            niDTO.getUnitOfMeasurement() == null ||
            niDTO.getDate() == null
        ) {
            return new ResponseDTO("Completa todos los campos", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        }

        // El usuario es dueño del registro de ingesta?
        Optional<EatEntity> eat = eatRepository.findById(niDTO.getEatId());
        if(!eat.isPresent() || !eat.get().getUser().getUsername().equals(username))
            return new ResponseDTO("Registro no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
            
        Optional<EatEntity> getEat = eatRepository.findById(niDTO.getEatId());
        // No se envió un alimento
        Optional<FoodEntity> getFood = Optional.empty();
        if (niDTO.getFoodId() != null) {
            getFood = foodRepository.findById(niDTO.getFoodId());
            if(!getFood.isPresent())
                return new ResponseDTO("Alimento no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }

        // Es receta?
        EatEntity newEat = getEat.get();
        if(newEat.getFood() == null) {
            return new ResponseDTO("Las recetas no pueden ser modificadas", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        }
        if (niDTO.getFoodId() != null) {
            newEat.setFood(getFood.get());
        }

        newEat.setEatQuantity(niDTO.getQuantity());
        newEat.setUnitOfMeasurement(niDTO.getUnitOfMeasurement());

        // Convertir la fecha a UTC antes de guardarla
        LocalDateTime dateTimeOfEat = niDTO.getDate().toLocalDateTime();
        dateTimeOfEat = dateTimeOfEat.plusHours(hoursToSubtract);
        ZonedDateTime zonedDateTime = dateTimeOfEat.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC);
        newEat.setDate(Timestamp.valueOf(zonedDateTime.toLocalDateTime()));

        // Calcular la categoría de la ingesta
        dateTimeOfEat = dateTimeOfEat.plusHours(hoursToSubtract);
        newEat.setCategoryIntake(calculateCategory(dateTimeOfEat));

        try {
            eatRepository.save(newEat);
            return new ResponseDTO("Registro actualizado exitosamente", HttpStatus.OK.value(), ResponseType.SUCCESS);
        } catch (Exception e) {
            return new ResponseDTO("Error al actualizar el registro", HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseType.ERROR);
        }
    }
    
    // * Heather: Quitar un alimento de la lista de alimentos consumidos por un usuario
    @Transactional
    public ResponseDTO deleteFoodIntake(String username, Long eatId) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent())
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        // Verificar que el registro de ingesta exista
        Optional<EatEntity> getEat = eatRepository.findById(eatId);
        if (!getEat.isPresent() || !getEat.get().getUser().getUsername().equals(username))
            return new ResponseDTO("Registro no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        try {
            eatRepository.deleteId(getEat.get().getId());
            return new ResponseDTO("Registro eliminado exitosamente", HttpStatus.OK.value(), ResponseType.SUCCESS);
        } catch (Exception e) {
            return new ResponseDTO("Error al eliminar el registro", HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseType.ERROR);
        }
    }
    
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
                System.out.println(result[5]);
                return new IntakeDTO(
                    (Long) result[0],
                    (String) result[1],
                    (String) result[2],
                    (Double) result[3],
                    (UnitOfMeasurementEnum) result[4],
                    (Timestamp) result[5]
                );
            }).collect(Collectors.toList());
        }
        return new CategoryIntakeDTO(null, null, null, macrosConsumedPerCategory, myIntakes);
    }

    // ? Funciones
    // Imprimir las fechas (exactamente igual a como está en la BD) de las ingestas
    private void printDatesOfIntakes(String nombreCategoria, CategoryIntakeDTO categoryIntake) {
        if (categoryIntake != null && categoryIntake.getMyIntakes() != null) {
            System.out.println("Categoría: " + nombreCategoria);
            for (IntakeDTO intake : categoryIntake.getMyIntakes()) {
                System.out.println("Fecha de ingesta: " + intake.getCreatedAt());
            }
        }
    }

    // Calibrar las horas de las ingestas con Perú al momento de mostrarlas
    public void calibrateHoursWithPeru(CategoryIntakeDTO categoryIntakeDTO) {
        if (categoryIntakeDTO != null && categoryIntakeDTO.getMyIntakes() != null) {
            for (IntakeDTO intake : categoryIntakeDTO.getMyIntakes()) {
                Timestamp timestamp = intake.getCreatedAt();
                if (timestamp != null) {
                    LocalDateTime localDateTime = timestamp.toLocalDateTime();
                    // La diferencia de horas entre Perú y UTC es de 5 horas menos en Perú
                    LocalDateTime adjustedDateTime = localDateTime.minusHours(hoursToSubtract);
                    intake.setCreatedAt(Timestamp.valueOf(adjustedDateTime));
                }
            }
        }
    }

    // Método para redondear un valor de forma concisa
    public Double round(Double value) {
        if (value == null) return null;
        return Double.valueOf(String.format("%.3f", value));
    }

    // Asigna una categoría de ingesta según la hora de la ingesta
    public CategoryIntakeEnum calculateCategory(LocalDateTime dateTimeOfEat) {
        LocalTime timeOfEat = dateTimeOfEat.toLocalTime();

        if (isInProduction)
            timeOfEat = timeOfEat.minusHours(hoursToSubtract);

        if (timeOfEat.isBefore(LocalTime.of(12, 0))) {
            return CategoryIntakeEnum.DESAYUNO;
        } else if (timeOfEat.isBefore(LocalTime.of(19, 0))) {
            return CategoryIntakeEnum.ALMUERZO;
        } else {
            return CategoryIntakeEnum.CENA;
        }
        // Desayuno: 0:00 hasta las 11:59
        // Almuerzo: 12:00 hasta las 18:59
        // Cena: 19:00 hasta las 23:59
    }
}
