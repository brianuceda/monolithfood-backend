package pe.edu.upc.MonolithFoodApplication;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pe.edu.upc.MonolithFoodApplication.entities.ActivityLevelEntity;
import pe.edu.upc.MonolithFoodApplication.entities.ObjectiveEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.ActivityLevelRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.ObjectiveRepository;

@SpringBootApplication
public class MonolithFoodApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonolithFoodApplication.class, args);
    }
    
    // CommandLineRunner iniciará el código después de que se hayan cargado los beans de Spring.
    @Bean
    public CommandLineRunner initData(
        ActivityLevelRepository activityLevelRepository,
        ObjectiveRepository objectiveRepository
    ){
        return args -> {
            // Inserta los niveles de actividad física si no existen
            if (activityLevelRepository.count() == 0) {
                List<ActivityLevelEntity> activityLevels = Arrays.asList(
                    new ActivityLevelEntity(null, "Poco o ningun ejercicio", 1.2, "Ningun dia", "Personas sedentarias que realizan poco o ningún tipo de actividad física.", null),
                    new ActivityLevelEntity(null, "Ejercicio ligero", 1.375, "1 - 3 dias", "Actividad ligera como caminar casualmente o tareas diarias no exigentes.", null),
                    new ActivityLevelEntity(null, "Ejercicio moderado", 1.55, "3 - 5 dias", "Actividad moderada que puede incluir trabajos que requieran actividad física o ejercicios regulares.", null),
                    new ActivityLevelEntity(null, "Ejercicio intenso", 1.725, "5 - 7 dias", "Ejercicio intenso o trabajos físicamente demandantes.", null),
                    new ActivityLevelEntity(null, "Ejercicio muy intenso", 1.9, "Atleta profesional", "Atletas o individuos con trabajos extremadamente físicos.", null)
                );
                activityLevelRepository.saveAll(activityLevels);
            }
            
            // Inserta objetivos nutricionales si no existen
            if (objectiveRepository.count() == 0) {
                List<ObjectiveEntity> objectives = Arrays.asList(
                    new ObjectiveEntity(null, "Bajar de peso", "Objetivo centrado en reducir el peso corporal a través de una dieta equilibrada.", null),
                    new ObjectiveEntity(null, "Aumentar masa muscular", "Objetivo centrado en ganar masa muscular a través de una dieta rica en proteínas.", null),
                    new ObjectiveEntity(null, "Mantener el peso", "Objetivo centrado en mantener el peso corporal actual.", null),
                    new ObjectiveEntity(null, "Aumentar resistencia", "Objetivo centrado en aumentar la resistencia física a través de una dieta equilibrada.", null),
                    new ObjectiveEntity(null, "Mejorar salud cardiaca", "Objetivo centrado en fortalecer el corazón y sistema circulatorio.", null),
                    new ObjectiveEntity(null, "Mejorar salud ósea", "Objetivo centrado en fortalecer los huesos y prevenir enfermedades óseas.", null),
                    new ObjectiveEntity(null, "Reducir grasa corporal", "Objetivo centrado en reducir el porcentaje de grasa corporal.", null),
                    new ObjectiveEntity(null, "Mejorar digestión", "Objetivo centrado en promover una digestión saludable a través de una dieta rica en fibra.", null)
                );
                objectiveRepository.saveAll(objectives);
            }

            // Otras actividades de inicialización;
        };
    }
}