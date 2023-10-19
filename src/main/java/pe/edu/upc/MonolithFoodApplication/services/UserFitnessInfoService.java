package pe.edu.upc.MonolithFoodApplication.services;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.activitylevel.ActivityLevelDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.activitylevel.ActivityLevelsResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.FitnessInfoDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.FitnessInfoResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.macronutrients.MacronutrientsDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.objectives.ObjectiveDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.objectives.ObjectivesResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.ActivityLevelEntity;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;
import pe.edu.upc.MonolithFoodApplication.entities.ObjectiveEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserFitnessInfoEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.ActivityLevelRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.EatRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.ObjectiveRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserFitnessInfoRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserFitnessInfoService {
    // ? Atributos
    // Inyección de dependencias
    private final UserRepository userRepository;
    private final ObjectiveRepository objectiveRepository;
    private final ActivityLevelRepository activityLevelRepository;
    private final EatRepository eatRepository;
    private final UserFitnessInfoRepository userFitnessInfoRepository;
    // Log de errores y eventos
    private static final Logger logger = LoggerFactory.getLogger(UserFitnessInfoService.class);

    // ? Metodos
    // * Brian: Seleccionar nivel de actividad física del usuario
    @Transactional
    public ResponseDTO selectActivityLevel(String username, String activityLevelName) {
        // Verificar que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // No se encontraron niveles de actividad física en el servidor
        List<ActivityLevelEntity> allActivityLevels = activityLevelRepository.findAll();
        if (allActivityLevels.isEmpty()) {
            logger.error("No se encontraron niveles de actividad física en el servidor.");
            return new ResponseDTO("No se encontraron niveles de actividad física en el servidor.", 404);
        }
        UserEntity user = optUser.get();
        // Verifica que el dato que haya enviado el usuario sea valido
        if (activityLevelName.isEmpty() || activityLevelName == null) {
            return new ResponseDTO("Debes seleccionar un nivel de actividad física.", 400);
        }
        // Verificar que el nivel de actividad física exista en la base de datos
        Optional<ActivityLevelEntity> optActivityLevel = activityLevelRepository.findByName(activityLevelName);
        if (!optActivityLevel.isPresent()) {
            return new ResponseDTO("No se encontró ese nivel de actividad física.", 400);
        }
        UserPersonalInfoEntity userPersonalInfo = user.getUserPersonalInfo();
        // Si el usuario aun no ha creado un UserPersonalInfoEntity
        if (userPersonalInfo == null) {
            return new ResponseDTO("Primero debes completar tu información personal.", 404);
        }
        ActivityLevelEntity myActivityLevel = userPersonalInfo.getActivityLevel();
        // Si el usuario ya tiene un nivel de actividad física seleccionado, los selecciona
        if(userPersonalInfo != null) {
            myActivityLevel = userPersonalInfo.getActivityLevel();
        }
        // Verificar que el usuario no esté intentando seleccionar el mismo nivel de actividad física que ya tiene
        if (myActivityLevel != null && myActivityLevel.getName().equals(activityLevelName)) {
            return new ResponseDTO("Ya tienes seleccionado ese nivel de actividad fisica.", 400);
        }
        // userPersonalInfo.setUser(user);
        userPersonalInfo.setActivityLevel(optActivityLevel.get());
        user.setUserPersonalInfo(userPersonalInfo);
        userRepository.save(user);
        return new ResponseDTO("Nivel de actividad física seleccionado correctamente.", 200);
    }
    // * Brian: Obtener nivel de actividad física del usuario
    public ResponseDTO getActivityLevels(String username) {
        // Verificar que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        List<ActivityLevelEntity> allActivityLevels = activityLevelRepository.findAll();
        if (allActivityLevels.isEmpty()) {
            logger.error("No se encontraron niveles de actividad física en el servidor.");
            return new ResponseDTO("No se encontraron niveles de actividad física en el servidor.", 404);
        }
        UserPersonalInfoEntity userPersonalInfo = optUser.get().getUserPersonalInfo();
        if (userPersonalInfo == null) {
            return new ResponseDTO("Primero debes completar tu información personal.", 404);
        }
        // Obtiene los niveles de actividad física de la BD y los compara con el nivel de actividad física del usuario,
        // si el usuario tiene ese nivel de actividad física, selected = true, si no, selected = false
        List<ActivityLevelDTO> activityLevelDTOs = allActivityLevels.stream()
            .map(obj -> {
                boolean selected = false;
                if(userPersonalInfo.getActivityLevel() != null)
                    selected = userPersonalInfo.getActivityLevel().getName().equals(obj.getName());
                return new ActivityLevelDTO(obj.getName(), obj.getDays(), obj.getInformation(), obj.getQuotient(), selected);
            })
        .collect(Collectors.toList());
        return new ActivityLevelsResponseDTO(null, 200, activityLevelDTOs);
    }
    // * Brian: Seleccionar objetivos de un usuario
    @Transactional
    public ResponseDTO selectObjectives(String username, List<String> objectives) {
        // Verificar que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Obtener todos los objetivos de la base de datos
        List<ObjectiveEntity> allObjectives = objectiveRepository.findAll();
        // Verificar si la lista de objetivos de la base de datos está vacía
        if (allObjectives.isEmpty()) {
            logger.error("No se encontraron objetivos en el servidor.");
            return new ResponseDTO("No se encontraron objetivos en el servidor.", 404);
        }
        UserEntity user = optUser.get();
        // Verificar si la lista de objetivos está vacía
        if (objectives.isEmpty() || objectives == null) {
            logger.error("El usuario " + username + " realizó una petición con una lista de objetivos vacía o nula.");
            return new ResponseDTO("Debes seleccionar al menos un objetivo.", 400);
        }
        // Validar que todos los objetivos enviados existen en la base de datos
        for (String objective : objectives) {
            boolean exists = allObjectives.stream().anyMatch(o -> o.getName().equals(objective));
            if (!exists) {
                logger.error("El usuario " + username + " está intentando seleccionar un objetivo que no existe.");
                return new ResponseDTO("No se encontro uno de los objetivos seleccionados.", 404);
            }
        }
        // Valida que el usuario no intente enviar el mismo objetivo dos veces o más en la lista
        if (objectives.stream().distinct().count() != objectives.size()) {
            logger.error("El usuario " + username + " está intentando seleccionar el mismo objetivo más de una vez.");
            return new ResponseDTO("No puedes seleccionar el mismo objetivo más de una vez.", 400);
        }
        // Valida que el usuario no intente seleccionar más de 3 objetivos
        if (objectives.size() > 3) {
            logger.error("El usuario " + username + " está intentando seleccionar más de 3 objetivos.");
            return new ResponseDTO("No puedes seleccionar más de 3 objetivos.", 400);
        }
        // Filtrar los objetivos de la BD que coinciden con los enviados por el usuario
        List<ObjectiveEntity> newUserObjectives = allObjectives.stream()
                .filter(o -> objectives.contains(o.getName()))
                .collect(Collectors.toList());
        // Guardar los objetivos en el usuario
        user.setObjectives(newUserObjectives);
        userRepository.save(user);
        return new ResponseDTO("Objetivos nutricionales seleccionados correctamente.", 200);
    }  
    // * Brian: Obtener todos los objetivos
    @Transactional(readOnly = true)
    public ResponseDTO getObjectives(String username) {
        // Verificar que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        List<ObjectiveEntity> allObjectives = objectiveRepository.findAll();
        if (allObjectives.isEmpty()) {
            logger.error("No se encontraron objetivos en el servidor.");
            return new ResponseDTO("No se encontraron objetivos en el servidor.", 404);
        }
        List<ObjectiveEntity> myObjectives = optUser.get().getObjectives();
        // Obtiene todos los objetivos de la BD y los compara con los del usuario que están en la lista myObjectives,
        // si el usuario tiene ese objetivo, selected = true, si no, selected = false
        List<ObjectiveDTO> objectiveDTOs = allObjectives.stream()
                .map(obj -> {
                    boolean selected = myObjectives.stream().anyMatch(o -> o.getName().equals(obj.getName()));
                    return new ObjectiveDTO(obj.getName(), obj.getInformation(), obj.getProteinPerKg(), selected);
                })
                .collect(Collectors.toList());
        return new ObjectivesResponseDTO(null, 200, objectiveDTOs);
    }
    // * Willy: Registrar información fitness de un usuario que no es modificable por el sistema
    @Transactional
    public ResponseDTO setUserFitnessInfo(String username, FitnessInfoDTO fitnessInfoDTO) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Gaurda la información fitness del usuario en una variable
        UserEntity user = optUser.get();
        UserFitnessInfoEntity ufi = user.getUserFitnessInfo();
        if (ufi == null) {
            ufi = new UserFitnessInfoEntity();
        }
        else {
            return new ResponseDTO("Ya tienes información fitness registrada.", 400);
        }
        ufi.setTargetWeightKg(fitnessInfoDTO.getTargetWeightKg());
        ufi.setTargetDate(fitnessInfoDTO.getTargetDate());
        // Guarda la información fitness del usuario en la BD
        user.setUserFitnessInfo(ufi);
        userRepository.save(user);
        // Retorna la información fitness del usuario
        return new ResponseDTO("Información fitness registrada correctamente.", 200);
    }
    // * Willy: Actualizar información fitness de un usuario
    @Transactional
    public ResponseDTO updateUserFitnessInfo(String username, FitnessInfoDTO fitnessInfoDTO) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Gaurda la información fitness del usuario en una variable
        UserEntity user = optUser.get();
        UserFitnessInfoEntity ufi = user.getUserFitnessInfo();
        if (ufi == null) {
            ufi = new UserFitnessInfoEntity();
        }
        ufi.setTargetWeightKg(fitnessInfoDTO.getTargetWeightKg());
        ufi.setTargetDate(fitnessInfoDTO.getTargetDate());
        // Guarda la información fitness del usuario en la BD
        user.setUserFitnessInfo(ufi);
        userRepository.save(user);
        // Retorna la información fitness del usuario
        return new ResponseDTO("Información fitness actualizada correctamente.", 200);
    }
    // * Willy: Calcular la información fitness del usuario
    // Por ahora calcula de forma general, luego los calculos se basarán en los objetivos del usuario
    @Transactional
    public ResponseDTO calcFitnessInfo(String username) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Variables para verificar que el usuario tenga la información necesaria para calcular su información fitness
        UserEntity user = optUser.get();
        UserPersonalInfoEntity upi = user.getUserPersonalInfo();
        UserFitnessInfoEntity ufi = user.getUserFitnessInfo();
        // Verifica que el usuario tenga información personal
        if (upi == null) {
            return new ResponseDTO("No cuentas con la información personal necesaria para calcular tu información fitness.", 404);
        }
        if (upi.getActivityLevel() == null) {
            return new ResponseDTO("Primero debes seleccionar un nivel de actividad física.", 404);
        }
        if (user.getObjectives().isEmpty()) {
            return new ResponseDTO("Primero debes seleccionar tus objetivos nutricionales.", 404);
        }
        if (ufi == null || ufi.getTargetWeightKg() == null || ufi.getTargetDate() == null) {
            return new ResponseDTO("Primero debes especificar tu fecha y peso objetivos.", 404);
        }
        if(upi.getWeightKg() == null || upi.getHeightCm() == null) {
            return new ResponseDTO("Primero debes especificar tu peso y altura en tu información personal.", 404);
        }
        // Calcula el IMC
        Double bigImc = calculateIMC(upi.getWeightKg(), upi.getHeightCm());
        Double imc = Double.valueOf(String.format("%.3f", bigImc));
        // Clasifica el IMC
        String clasification = getClasification(imc);
        // Calcula la edad del usuario
        Integer age = calculateAge(upi.getBorndate());
        // Calcula la ingesta diaria de macronutrientes basada en las necesidades diarias de energía
        calculateMacronutrientIntake(user, upi, ufi, age);
        // Guarda la información de fitness del usuario
        ufi.setImc(imc);
        // Guarda la información de fitness del usuario en la BD
        user.setUserFitnessInfo(ufi);
        userFitnessInfoRepository.save(ufi);
        userRepository.save(user);
        // Retorna la información de fitness del usuario
        return new FitnessInfoResponseDTO("Información fitness calculada correctamente.", 200,
            upi.getGender(),
            age,
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
    // * Willy: Obtener los macronutrientes consumidos en el dia de hoy
    public ResponseDTO getMacrosConsumedToday(String username) {
        logger.info("Username: " + username);
        MacronutrientsDTO dto = eatRepository.getMacrosConsumedToday(username);
        if (dto == null) {
            return new ResponseDTO("No has consumido alimentos hoy.", 404);
        }
        if (dto.getConsumedDailyCaloricIntake() == 0.0 && dto.getConsumedDailyProteinIntake() == 0.0 && dto.getConsumedDailyCarbohydrateIntake() == 0.0 && dto.getConsumedDailyFatIntake() == 0.0) {
            return new ResponseDTO("No has consumido nada hoy.", 404);
        }
        return dto;
    }

    // ? Funciones auxiliares
    // FUNCIÓN: Calcula el IMC
    public static Double calculateIMC(Double weight, Double height)
    {
        Double newHeight = height / 100;
        Double imc = weight / (newHeight*newHeight);
        return imc;
    }
    // FUNCIÓN: Classifica el IMC
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
        // Calcula la edad del usuario basado en la fecha de nacimiento
        LocalDate birthDate = birthdate.toLocalDateTime().toLocalDate();
        LocalDate currentDate = LocalDate.now();
        return Period.between(birthDate, currentDate).getYears();
    }
    private void calculateMacronutrientIntake(UserEntity user, UserPersonalInfoEntity upi, UserFitnessInfoEntity ufi, Integer age) {
        // Calcula la TMB según la fórmula proporcionada
        double tmb;
        if (upi.getGender() == GenderEnum.M)
            tmb = (10 * upi.getWeightKg()) + (6.25 * upi.getHeightCm()) + (5 * age) + 5; 
        else if (upi.getGender() == GenderEnum.F)
            tmb = (10 * upi.getWeightKg()) + (6.25 * upi.getHeightCm()) + (5 * age) - 161;
        else
            tmb = 0;
        // Multiplica la TMB por el factor de nivel de actividad física
        double dailyEnergyNeeds = tmb * upi.getActivityLevel().getQuotient();
        // Establece grams de proteína por kg de peso
        double avgProteinPerKg = calculateAverageProteinPerKg(user);
        System.out.println("Proteína por kg: " + avgProteinPerKg);
        // Calcula ingesta de proteína
        double dailyProteinIntake = avgProteinPerKg * upi.getWeightKg();
        // Calcula calorías de proteínas 
        double kCalDailyProteinIntake = dailyProteinIntake * 4;
        // Calcula calorías restantes
        double remainingCalories = dailyEnergyNeeds - kCalDailyProteinIntake;
        // Define porcentajes de macros
        double carbPercentage = 0.55;
        double fatPercentage = 0.30;
        // Calcula ingesta de carbs y grasas
        double carbIntake = (remainingCalories * carbPercentage) / 4;
        double fatIntake = (remainingCalories * fatPercentage) / 9;
        // Almacena la información calculada en la entidad UserFitnessInfo
        ufi.setDailyCaloricIntake(round(dailyEnergyNeeds));
        ufi.setDailyProteinIntake(round(dailyProteinIntake));
        ufi.setDailyCarbohydrateIntake(round(carbIntake));
        ufi.setDailyFatIntake(round(fatIntake));
        ufi.setAvgProteinPerKg(round(avgProteinPerKg));
        ufi.setTmb(round(tmb));
    }
    public double calculateAverageProteinPerKg(UserEntity user) {
        List<ObjectiveEntity> objectives = user.getObjectives();
        OptionalDouble averageProteinPerKg = objectives.stream()
            .mapToDouble(ObjectiveEntity::getProteinPerKg)
            .average();
        // Devuelve 0 si no hay objetivos
        return averageProteinPerKg.orElse(0);
    }
    private Double round(Double value) {
        return Double.valueOf(String.format("%.2f", value));
    }
}
