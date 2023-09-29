package pe.edu.upc.MonolithFoodApplication.services;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.FitnessInfoResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserFitnessInfoEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.UserFitnessInfoRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserFitnessInfoService {
    // ? Atributos
    // Inyección de dependencias
    private final UserRepository userRepository;
    private final UserFitnessInfoRepository userFitnessInfoRepository;
    // Log de errores y eventos
    private static final Logger logger = LoggerFactory.getLogger(UserFitnessInfoService.class);

    // ? Metodos
    // * Willy: Obtener la información de fitness del usuario
    // Por ahora calcula de forma general, luego los calculos se basarán en los objetivos del usuario
    public ResponseDTO calcFitnessInfo(String username) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        UserEntity user = optUser.get();
        UserPersonalInfoEntity upi = user.getUserPersonalInfo();
        UserFitnessInfoEntity ufi = user.getUserFitnessInfo();
        // Calcula el IMC
        Double imc = calculateIMC(upi.getWeightKg(), upi.getHeightCm());
        // Clasifica el IMC
        String clasification = getClasification(imc);
        // Calcula la edad del usuario
        Integer age = calculateAge(upi.getBirthdate());
        // Calcula la TMB usando la ecuación de Harris-Benedict
        Double tmb = calculateTMB(upi.getWeightKg(), upi.getHeightCm(), age, upi.getGender());
        // Calcula las necesidades diarias de energía
        Double dailyEnergyNeeds = calculateDailyEnergyNeeds(tmb, upi.getActivityLevel().getQuotient());
        // Calcula la ingesta diaria de macronutrientes basada en las necesidades diarias de energía
        calculateMacronutrientIntake(ufi, dailyEnergyNeeds);
        // Guarda la información de fitness del usuario
        ufi.setImc(imc);
        ufi.setTmb(tmb);
        ufi.setDailyEnergyNeeds(dailyEnergyNeeds);
        userFitnessInfoRepository.save(ufi);
        // Retorna la información de fitness del usuario
        return new FitnessInfoResponseDTO("Información de fitness calculada correctamente.", 200,
            upi.getGender(),
            upi.getBirthdate(),
            upi.getHeightCm(),
            upi.getWeightKg(),
            upi.getActivityLevel().getName(),
            upi.getActivityLevel().getQuotient(),
            ufi.getImc(), clasification,
            ufi.getDailyCaloricIntake(),
            ufi.getDailyProteinIntake(),
            ufi.getDailyCarbohydrateIntake(),
            ufi.getDailyFatIntake(),
            tmb,
            dailyEnergyNeeds
        );
    }
    // * Willy: Obtener la información de fitness del usuario
    public ResponseDTO getFitnessInfo(String username) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Retorna la información de fitness del usuario
        UserEntity user = optUser.get();
        UserPersonalInfoEntity upi = user.getUserPersonalInfo();
        UserFitnessInfoEntity ufi = user.getUserFitnessInfo();
        if(
            ufi == null ||
            ufi.getDailyCaloricIntake() == null ||
            ufi.getDailyProteinIntake() == null ||
            ufi.getDailyCarbohydrateIntake() == null ||
            ufi.getDailyFatIntake() == null
        ) {
            return calcFitnessInfo(username);
        }
        // Retorna la información de fitness del usuario
        Double imc = ufi.getImc();
        String clasification = getClasification(imc);
        return new FitnessInfoResponseDTO("Información de fitness calculada correctamente.", 200,
            upi.getGender(),
            upi.getBirthdate(),
            upi.getHeightCm(),
            upi.getWeightKg(),
            upi.getActivityLevel().getName(),
            upi.getActivityLevel().getQuotient(),
            ufi.getImc(), clasification,
            ufi.getDailyCaloricIntake(),
            ufi.getDailyProteinIntake(),
            ufi.getDailyCarbohydrateIntake(),
            ufi.getDailyFatIntake(),
            ufi.getTmb(),
            ufi.getDailyEnergyNeeds()
        );
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
    private Double calculateTMB(Double weight, Double height, Integer age, GenderEnum gender) {
        // Calcula la TMB usando la ecuación de Harris-Benedict
        if (gender == GenderEnum.M) return 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        else return 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
    }
    private Double calculateDailyEnergyNeeds(Double tmb, Double activityLevel) {
        // Calcula las necesidades diarias de energía
        return tmb * activityLevel;
    }
    private void calculateMacronutrientIntake(UserFitnessInfoEntity ufi, Double dailyEnergyNeeds) {
        // Calcula la ingesta diaria de macronutrientes basada en las necesidades diarias de energía
        ufi.setDailyCaloricIntake(dailyEnergyNeeds);
        ufi.setDailyProteinIntake(dailyEnergyNeeds * 0.25 / 4);  // El 25% de la energía viene de las proteínas
        ufi.setDailyCarbohydrateIntake(dailyEnergyNeeds * 0.55 / 4);  // El 55% de la energía viene de los carbohidratos
        ufi.setDailyFatIntake(dailyEnergyNeeds * 0.20 / 9);  // El 20% de la energía viene de las grasas
    }
    
}
