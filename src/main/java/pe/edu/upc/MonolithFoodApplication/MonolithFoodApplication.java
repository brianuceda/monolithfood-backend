package pe.edu.upc.MonolithFoodApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pe.edu.upc.MonolithFoodApplication.entities.ActivityLevelEntity;
import pe.edu.upc.MonolithFoodApplication.entities.CategoryFoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.CompositionEntity;
import pe.edu.upc.MonolithFoodApplication.entities.CompositionKey;
import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.NutrientEntity;
import pe.edu.upc.MonolithFoodApplication.entities.ObjectiveEntity;
import pe.edu.upc.MonolithFoodApplication.entities.PrivacityEnum;
import pe.edu.upc.MonolithFoodApplication.repositories.ActivityLevelRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.CategoryRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.CompositionRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.FoodRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.NutrientRepository;
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
        ObjectiveRepository objectiveRepository,
        NutrientRepository nutrientRepository,
        FoodRepository foodRepository,
        CompositionRepository compositionRepository,
        CategoryRepository categoryFoodRepository
    ){
        return args -> {

            List<ActivityLevelEntity> activityLevels = new ArrayList<>();
            List<ObjectiveEntity> objectives = new ArrayList<>();
            List<NutrientEntity> nutrients = new ArrayList<>();
            List<CategoryFoodEntity> categories = new ArrayList<>();
            List<FoodEntity> foods = new ArrayList<>();
            List<CompositionEntity> compositions = new ArrayList<>();

            // Inserta los niveles de actividad física si no existen
            if(activityLevelRepository.count() == 0) {
                activityLevels = Arrays.asList(
                    new ActivityLevelEntity(null, "Poco o ningun ejercicio", 1.2, "Ningun dia", "Personas sedentarias que realizan poco o ningún tipo de actividad física.", null),
                    new ActivityLevelEntity(null, "Ejercicio ligero", 1.375, "1 - 3 dias", "Actividad ligera como caminar casualmente o tareas diarias no exigentes.", null),
                    new ActivityLevelEntity(null, "Ejercicio moderado", 1.55, "3 - 5 dias", "Actividad moderada que puede incluir trabajos que requieran actividad física o ejercicios regulares.", null),
                    new ActivityLevelEntity(null, "Ejercicio intenso", 1.725, "5 - 7 dias", "Ejercicio intenso o trabajos físicamente demandantes.", null),
                    new ActivityLevelEntity(null, "Ejercicio muy intenso", 1.9, "Atleta profesional", "Atletas o individuos con trabajos extremadamente físicos.", null)
                );
                activityLevelRepository.saveAll(activityLevels);
            }
            // Inserta objetivos nutricionales si no existen
            if(objectiveRepository.count() == 0) {
                objectives = Arrays.asList(
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
            // Insertar nutrientes si no existen
            if(nutrientRepository.count() == 0) {
                nutrients = Arrays.asList(
                    new NutrientEntity(null, "Proteína", "Macronutriente esencial para la construcción de masa muscular.", "...", null),
                    new NutrientEntity(null, "Grasa", "Macronutriente esencial para la energía.", "...", null),
                    new NutrientEntity(null, "Carbohidratos", "Principal fuente de energía.", "...", null),
                    new NutrientEntity(null, "Vitamina B6", "Vitamina esencial para el metabolismo.", "...", null),
                    new NutrientEntity(null, "Fósforo", "Mineral esencial para la salud ósea.", "...", null),
                    new NutrientEntity(null, "Magnesio", "Mineral esencial para diversas funciones biológicas.", "...", null),
                    new NutrientEntity(null, "Vitamina C", "Antioxidante y esencial para la salud inmunológica.", "...", null),
                    new NutrientEntity(null, "Vitamina K", "Vitamina esencial para la coagulación de la sangre.", "...", null)
                );
                nutrientRepository.saveAll(nutrients);
            }
            // Inserta categorías si no existen
            if(categoryFoodRepository.count() == 0) {
                categories = Arrays.asList(
                    new CategoryFoodEntity(null, "Aves", "Información aves", "Beneficios aves", "Desventajas aves", null),
                    new CategoryFoodEntity(null, "Granos", "Información granos", "Beneficios granos", "Desventajas granos", null),
                    new CategoryFoodEntity(null, "Verduras", "Información verduras", "Beneficios verduras", "Desventajas verduras", null)
                );
                categoryFoodRepository.saveAll(categories);
            }
            // Inserta alimentos si no existen
            if(foodRepository.count() == 0) {
                foods = Arrays.asList(
                    new FoodEntity(null, "Pollo", "Pechuga sin piel, cocida", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/pechuga-de-pollo", null, getCategoryByName(categories, "Aves"), null, null),
                    new FoodEntity(null, "Arroz", "Arroz blanco, cocido", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/arroz-blanco", null, getCategoryByName(categories, "Granos"), null, null),
                    new FoodEntity(null, "Brocoli", "Brocoli cocido", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/br%C3%B3coli", null, getCategoryByName(categories, "Verduras"), null, null)
                );
                foodRepository.saveAll(foods);
            }
            // Inserta composiciones (food - nutrient) si no existen
            if(compositionRepository.count() == 0) {
                // Relaciones entre alimentos y nutrientes en la tabla composition
                Map<String, Map<String, Double>> foodNutrientMap = new HashMap<String, Map<String, Double>>() {{
                    put("Pollo", new HashMap<String, Double>() {{
                        put("Proteína", 31.0);
                        put("Grasa", 3.6);
                        put("Vitamina B6", 0.5);
                        put("Fósforo", 220.0);
                    }});
                    put("Arroz", new HashMap<String, Double>() {{
                        put("Carbohidratos", 28.0);
                        put("Proteína", 2.7);
                        put("Grasa", 0.3);
                        put("Magnesio", 19.0);
                    }});
                    put("Brocoli", new HashMap<String, Double>() {{
                        put("Proteína", 2.4);
                        put("Carbohidratos", 7.0);
                        put("Vitamina C", 64.0);
                        put("Vitamina K", 141.0);
                    }});
                }};
                for (Map.Entry<String, Map<String, Double>> foodEntry : foodNutrientMap.entrySet()) {
                    FoodEntity food = getFoodByName(foods, foodEntry.getKey());
                    for (Map.Entry<String, Double> nutrientEntry : foodEntry.getValue().entrySet()) {
                        NutrientEntity nutrient = getNutrientByName(nutrients, nutrientEntry.getKey());
                        compositions.add(new CompositionEntity(new CompositionKey(food.getId(), nutrient.getId()), food, nutrient, nutrientEntry.getValue()));
                    }
                }
                compositionRepository.saveAll(compositions);
            }
            // Otras actividades de inicialización;
        };
    }

    // Composition
    private FoodEntity getFoodByName(List<FoodEntity> foods, String name) {
        return foods.stream().filter(food -> food.getName().equals(name)).findFirst().orElse(null);
    }
    private NutrientEntity getNutrientByName(List<NutrientEntity> nutrients, String name) {
        return nutrients.stream().filter(nutrient -> nutrient.getName().equals(name)).findFirst().orElse(null);
    }
    // Food - Category
    private CategoryFoodEntity getCategoryByName(List<CategoryFoodEntity> categories, String name) {
        return categories.stream().filter(category -> category.getName().equals(name)).findFirst().orElse(null);
    }
}