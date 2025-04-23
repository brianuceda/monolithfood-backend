package pe.edu.upc.MonolithFoodApplication.services;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.activitylevel.ActivityLevelDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.activitylevel.ActivityLevelsResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.AuthResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.FitnessInfoDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.FitnessInfoResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.objectives.ObjectiveDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.objectives.ObjectivesResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.reports.MacrosPerDays;
import pe.edu.upc.MonolithFoodApplication.dtos.reports.MacrosPerWeek;
import pe.edu.upc.MonolithFoodApplication.entities.ActivityLevelEntity;
import pe.edu.upc.MonolithFoodApplication.entities.ObjectiveEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserFitnessInfoEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.enums.GenderEnum;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;
import pe.edu.upc.MonolithFoodApplication.repositories.ActivityLevelRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.ObjectiveRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserFitnessInfoRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserFitnessInfoService {
    private final UserRepository userRepository;
    private final ObjectiveRepository objectiveRepository;
    private final ActivityLevelRepository activityLevelRepository;
    private final UserFitnessInfoRepository userFitnessInfoRepository;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(UserFitnessInfoService.class);

    // * Brian: Obtener nivel de actividad física del usuario
    public ResponseDTO getActivityLevels(String username) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado");
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserPersonalInfoEntity userPersonalInfo = optUser.get().getUserPersonalInfo();
        if (userPersonalInfo == null) {
            return new ResponseDTO("Completa tu información personal", HttpStatus.NOT_FOUND.value(), ResponseType.WARN);
        }
        // Obtiene todos los niveles de actividad física y marca como seleccionado el nivel del usuario
        List<ActivityLevelEntity> allActivityLevels = activityLevelRepository.findAll();
        if (allActivityLevels.isEmpty()) {
            return new ResponseDTO("No se encontraron resultados", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        List<ActivityLevelDTO> activityLevelDTOs = allActivityLevels.stream()
            .map(obj -> {
                boolean selected = false;
                if(userPersonalInfo.getActivityLevel() != null)
                    selected = userPersonalInfo.getActivityLevel().getName().equals(obj.getName());
                return new ActivityLevelDTO(obj.getName(), obj.getImgUrl() , obj.getDays(), obj.getInformation(), obj.getQuotient(), selected);
            })
        .collect(Collectors.toList());
        return new ActivityLevelsResponseDTO(null, 200, null, activityLevelDTOs);
    }
    // * Brian: Establecer nivel de actividad física del usuario
    @Transactional
    public ResponseDTO setActivityLevel(String username, String activityLevelName) {
        
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = optUser.get();
        UserPersonalInfoEntity userPersonalInfo = user.getUserPersonalInfo();
        if (userPersonalInfo == null) {
            return new ResponseDTO("Completa tu información personal", HttpStatus.NOT_FOUND.value(), ResponseType.WARN);
        }
        if (user.getUserPersonalInfo().getActivityLevel() != null) {
            return new ResponseDTO("Ya tienes un nivel registrado", HttpStatus.BAD_REQUEST.value(), ResponseType.ERROR);
        }
        ResponseDTO response = this.defineActivityLevel(user, activityLevelName);
        if (response.getStatusCode() == 200) {
            String profileStage = jwtService.determineProfileStage(user);
            String generatedToken = jwtService.genToken(user, profileStage);
            return new AuthResponseDTO(response.getMessage(), HttpStatus.OK.value(), response.getType(), generatedToken, user.getUserConfig().getDarkMode());
        }
        return response;
    }
    // * Brian: Actualizar nivel de actividad física del usuario
    @Transactional
    public ResponseDTO updateActivityLevel(String username, String activityLevelName) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = optUser.get();
        UserPersonalInfoEntity userPersonalInfo = user.getUserPersonalInfo();
        if (userPersonalInfo == null) {
            return new ResponseDTO("Completa tu información personal", HttpStatus.NOT_FOUND.value(), ResponseType.WARN);
        }
        if (user.getUserPersonalInfo().getActivityLevel() == null) {
            return new ResponseDTO("Debes seleccionar tu actividad física", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        }
        ActivityLevelEntity myActivityLevel = userPersonalInfo.getActivityLevel();
        if (myActivityLevel != null && myActivityLevel.getName().equals(activityLevelName)) {
            return new ResponseDTO("Nivel de actividad ya seleccionado", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        }
        this.calcFitnessInfo(user.getUsername(), true);
        return this.defineActivityLevel(user, activityLevelName);
    }
    // ? Metodo comun de nivel de actividad física
    private ResponseDTO defineActivityLevel(UserEntity user, String activityLevelName) {
        if (activityLevelName.isEmpty() || activityLevelName == null) {
            return new ResponseDTO("Debes seleccionar un nivel de actividad", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        }
        List<ActivityLevelEntity> allActivityLevels = activityLevelRepository.findAll();
        if (allActivityLevels.isEmpty()) {
            return new ResponseDTO("No se encontraron niveles de actividad", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        Optional<ActivityLevelEntity> optActivityLevel = allActivityLevels.stream()
            .filter(level -> level.getName().equals(activityLevelName)).findFirst();
        if (!optActivityLevel.isPresent()) {
            return new ResponseDTO("No se encontró ese nivel de actividad", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserPersonalInfoEntity upi = user.getUserPersonalInfo();
        // Guardar el nivel de actividad física en el usuario
        upi.setActivityLevel(optActivityLevel.get());
        user.setUserPersonalInfo(upi);
        userRepository.save(user);
        return new ResponseDTO("Nivel seleccionado correctamente", HttpStatus.OK.value(), ResponseType.SUCCESS);
    }

    // * Brian: Obtener todos los objetivos de un usuario
    @Transactional(readOnly = true)
    public ResponseDTO getObjectives(String username) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        // Obtiene todos los objetivos y marca como seleccionados los objetivos del usuario
        List<ObjectiveEntity> allObjectives = objectiveRepository.findAll();
        if (allObjectives.isEmpty()) {
            return new ResponseDTO("No se encontraron resultados", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        List<ObjectiveEntity> myObjectives = optUser.get().getObjectives();
        List<ObjectiveDTO> objectiveDTOs = allObjectives.stream()
                .map(obj -> {
                    boolean selected = myObjectives.stream().anyMatch(o -> o.getName().equals(obj.getName()));
                    return new ObjectiveDTO(obj.getName(), obj.getImgUrl(), obj.getInformation(), obj.getProteinPerKg(), selected);
                })
                .collect(Collectors.toList());
        return new ObjectivesResponseDTO(null, 200, null, objectiveDTOs);
    }
    // * Brian: Establecer nuevos objetivos de un usuario
    @Transactional
    public ResponseDTO setObjectives(String username, List<String> objectives) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = optUser.get();
        if (user.getObjectives() != null && !user.getObjectives().isEmpty()) {
            return new ResponseDTO("Ya tienes objetivos registrados", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        }        
        String msg = "Objetivos registrados correctamente";
        ResponseDTO response = this.defineObjectives(user, objectives, msg);
        // Retornar el token generado junto con el mensaje de éxito y el código de estado
        if (response.getStatusCode() == 200) {
            String profileStage = jwtService.determineProfileStage(user);
            String generatedToken = jwtService.genToken(user, profileStage);
            return new AuthResponseDTO(msg, 200, null, generatedToken, user.getUserConfig().getDarkMode());
        }
        return response;
    }
    // * Brian: Actualizar objetivos de un usuario
    @Transactional
    public ResponseDTO updateObjectives(String username, List<String> objectives) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = optUser.get();
        if (user.getObjectives().isEmpty() || user.getObjectives() == null) {
            return new ResponseDTO("Debes establecer tus objetivos", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        }
        return this.defineObjectives(user, objectives, "Objetivos actualizados correctamente");
    }
    // ? Metodo comun de objetivos
    private ResponseDTO defineObjectives(UserEntity user, List<String> objectives, String msg) {
        List<ObjectiveEntity> allObjectives = objectiveRepository.findAll();
        if (allObjectives.isEmpty()) {
            return new ResponseDTO("No se encontraron objetivos", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        if (objectives.isEmpty() || objectives == null) {
            return new ResponseDTO("Selecciona al menos un objetivo", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        }
        for (String objective : objectives) {
            boolean exists = allObjectives.stream().anyMatch(o -> o.getName().equals(objective));
            if (!exists) {
                return new ResponseDTO("Objetivos no encontrados", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
            }
        }
        if (objectives.stream().distinct().count() != objectives.size()) {
            return new ResponseDTO("No selecciones el mismo objetivo dos veces", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        }
        if (objectives.size() > 3) {
            return new ResponseDTO("No puedes seleccionar más de 3 objetivos", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        }
        // Filtrar los objetivos de la BD que coinciden con los enviados por el usuario
        List<ObjectiveEntity> newUserObjectives = allObjectives.stream()
                .filter(o -> objectives.contains(o.getName()))
                .collect(Collectors.toList());
        // Guardar los objetivos en el usuario
        user.setObjectives(newUserObjectives);
        userRepository.save(user);
        this.calcFitnessInfo(user.getUsername(), true);
        return new ResponseDTO(msg, HttpStatus.OK.value(), ResponseType.SUCCESS);
    }
    // * Willy: Actualizar información fitness de un usuario
    @Transactional
    public ResponseDTO updateFitnessInfo(String username, FitnessInfoDTO fitnessInfoDTO) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = optUser.get();
        UserFitnessInfoEntity ufi = user.getUserFitnessInfo();
        if (ufi == null) {
            ufi = new UserFitnessInfoEntity();
        }
        ufi.setTargetWeightKg(fitnessInfoDTO.getTargetWeightKg());
        ufi.setTargetDate(fitnessInfoDTO.getTargetDate());
        // Guarda la información fitness del usuario
        user.setUserFitnessInfo(ufi);
        userRepository.save(user);
        return new ResponseDTO("Información actualizada", HttpStatus.OK.value(), ResponseType.SUCCESS);
    }
    // * Willy: Calcular la información fitness del usuario
    // Por ahora calcula de forma general, luego los calculos se basarán en los objetivos del usuario
    @Transactional
    public ResponseDTO calcFitnessInfo(String username, boolean isCalledFromMethod) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = optUser.get();
        UserPersonalInfoEntity upi = user.getUserPersonalInfo();
        UserFitnessInfoEntity ufi = user.getUserFitnessInfo();
        if (upi == null || upi.getWeightKg() == null || upi.getHeightCm() == null) {
            return new ResponseDTO("Completa tu información personal", HttpStatus.NOT_FOUND.value(), ResponseType.WARN);
        }
        if (upi.getActivityLevel() == null) {
            return new ResponseDTO("Debes seleccionar un nivel de actividad", HttpStatus.NOT_FOUND.value(), ResponseType.WARN);
        }
        if (user.getObjectives().isEmpty()) {
            return new ResponseDTO("Debes seleccionar al menos un objetivo", HttpStatus.NOT_FOUND.value(), ResponseType.WARN);
        }
        if (ufi == null || ufi.getTargetWeightKg() == null || ufi.getTargetDate() == null) {
            return new ResponseDTO("Completa tu información fitness", HttpStatus.NOT_FOUND.value(), ResponseType.WARN);
        }
        // Calcula el IMC
        Double bigImc = calculateIMC(upi.getWeightKg(), upi.getHeightCm());
        Double imc = Double.valueOf(String.format("%.3f", bigImc));
        String clasification = getClasification(imc);
        Integer age = calculateAge(upi.getBorndate());
        this.calculateMacronutrientIntake(user, upi, ufi, age);
        // Guarda la información de fitness del usuario
        ufi.setImc(imc);
        user.setUserFitnessInfo(ufi);
        userFitnessInfoRepository.save(ufi);
        userRepository.save(user);
        if (!isCalledFromMethod) {
            return new FitnessInfoResponseDTO(
                "null", HttpStatus.OK.value(), null,
                upi.getGender(), age,
                upi.getHeightCm(),
                upi.getWeightKg(),
                ufi.getTargetWeightKg(),
                ufi.getTargetDate(),
                upi.getActivityLevel().getName(),
                upi.getActivityLevel().getQuotient(),
                ufi.getImc(), clasification,
                ufi.getDailyCaloricIntake(),
                ufi.getDailyProteinIntake(),
                ufi.getDailyCarbohydrateIntake(),
                ufi.getDailyFatIntake(),
                ufi.getAvgProteinPerKg(),
                ufi.getTmb()
            );
        }
        return null;
    }

    // * Reportes
    public ResponseDTO getMacrosReport(String username) {
        // Ajusta al inicio del día (domingo) a las 00:00 horas
        LocalDateTime startWeekDate = LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)).atStartOfDay();
        // Ajusta al final del día (sábado) a las 23:59:59.999999999 horas
        LocalDateTime endWeekDate = LocalDate.now().with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY)).atTime(LocalTime.MAX);
        
        MacrosPerWeek macrosPerWeek = new MacrosPerWeek();
        macrosPerWeek.setCalories(getMacrosPerDays(username, "Calorias", startWeekDate, endWeekDate));
        macrosPerWeek.setProteins(getMacrosPerDays(username, "Proteina", startWeekDate, endWeekDate));
        macrosPerWeek.setCarbohydrates(getMacrosPerDays(username, "Carbohidratos", startWeekDate, endWeekDate));
        macrosPerWeek.setFats(getMacrosPerDays(username, "Grasa", startWeekDate, endWeekDate));

        return new MacrosPerWeek(null, HttpStatus.OK.value(), ResponseType.SUCCESS, macrosPerWeek.getCalories(), macrosPerWeek.getProteins(), macrosPerWeek.getCarbohydrates(), macrosPerWeek.getFats());
    }

    private MacrosPerDays getMacrosPerDays(String username, String nutrientSearch, LocalDateTime startWeekDate, LocalDateTime endWeekDate) {
        List<Object[]> results = userFitnessInfoRepository.getMacrosReport(username, nutrientSearch, startWeekDate, endWeekDate);
        if (!results.isEmpty() && results.get(0) != null) {
            Object[] result = results.get(0);
            return new MacrosPerDays(
                asDouble(result[0]), 
                asDouble(result[1]), 
                asDouble(result[2]), 
                asDouble(result[3]), 
                asDouble(result[4]), 
                asDouble(result[5]), 
                asDouble(result[6])
            );
        } else {
            return new MacrosPerDays(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        }
    }
    
    private Double asDouble(Object value) {
        return value != null ? Double.valueOf(String.format("%.2f", value)) : 0.0;
    }

    // ? Funciones auxiliares
    // Calcula el IMC
    public static Double calculateIMC(Double weight, Double height)
    {
        Double newHeight = height / 100;
        Double imc = weight / (newHeight*newHeight);
        return imc;
    }
    // Classifica el IMC
    public String getClasification(Double imc)
    {
        if(imc < 18.5) return "Bajo peso";
        else if(imc >= 18.5 && imc < 25) return "Normal";
        else if(imc >= 25 && imc < 30) return "Sobrepeso";
        else if(imc >= 30 && imc < 35) return "Obesidad grado 1";
        else if(imc >= 35 && imc < 40) return "Obesidad grado 2";
        else return "Obesidad grado 3";
    }
    // Calcula la edad del usuario
    private Integer calculateAge(Timestamp birthdate) {
        LocalDate birthDate = birthdate.toLocalDateTime().toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
    // Calcula la cantidad de macronutrientes que debe consumir el usuario diariamente
    private void calculateMacronutrientIntake(UserEntity user, UserPersonalInfoEntity upi, UserFitnessInfoEntity ufi, Integer age) {
        double tmb = 0;
        if (upi.getGender() == GenderEnum.M)
            tmb = (10 * upi.getWeightKg()) + (6.25 * upi.getHeightCm()) + (5 * age) + 5; 
        else if (upi.getGender() == GenderEnum.F)
            tmb = (10 * upi.getWeightKg()) + (6.25 * upi.getHeightCm()) + (5 * age) - 161;
        // Calorías diarias
        double dailyCaloricIntake = (tmb * upi.getActivityLevel().getQuotient());
        // Promedio de proteína por kg
        double avgProteinPerKg = calculateAverageProteinPerKg(user);
        // Gramos de proteína diarios
        double dailyProteinIntake = avgProteinPerKg * upi.getWeightKg();
        // Valor calórico de la cantidad de gramos de proteína diarios
        double kCalDailyProteinIntake = dailyProteinIntake * 4;
        // Cantidad de calorías restantes para completar el requerimiento calórico diario
        double remainingCalories = dailyCaloricIntake - kCalDailyProteinIntake;
        // Porcentajes de ingesta de carbs y grasas
        double carbPercentage = 0.55;
        double fatPercentage = 0.30;
        // Gramos de carbs y grasas diarios
        double dailyCarbohydratesIntake = (remainingCalories * carbPercentage) / 4;
        double dailyFatsIntake = (remainingCalories * fatPercentage) / 9;
        // Almacena la información calculada
        ufi.setDailyCaloricIntake(round(dailyCaloricIntake));
        ufi.setDailyProteinIntake(round(dailyProteinIntake));
        ufi.setDailyCarbohydrateIntake(round(dailyCarbohydratesIntake));
        ufi.setDailyFatIntake(round(dailyFatsIntake));
        ufi.setAvgProteinPerKg(round(avgProteinPerKg));
        ufi.setTmb(round(tmb));
    }
    // Calcula el promedio de proteína por kg de los objetivos del usuario
    private double calculateAverageProteinPerKg(UserEntity user) {
        List<ObjectiveEntity> objectives = user.getObjectives();
        OptionalDouble averageProteinPerKg = objectives.stream()
            .mapToDouble(ObjectiveEntity::getProteinPerKg)
            .average();
        return averageProteinPerKg.orElse(0);
    }
    // Redondea un número a 0 decimales
    private Double round(Double value) {
        return (double) Math.round(value);
    }
}
