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

import pe.edu.upc.MonolithFoodApplication.entities.*;
import pe.edu.upc.MonolithFoodApplication.repositories.*;

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
        CategoryRepository categoryFoodRepository,
        FoodRepository foodRepository,
        RecipeRepository recipeRepository,
        RoleRepository roleRepository,
        UserRepository userRepository,
        UserPersonalInfoRepository userPersonalInfoRepository,
        UserFitnessInfoRepository userFitnessInfoRepository,
        UserConfigRepository userConfigRepository,
        IpLoginAttemptRepository ipLoginAttemptRepository,
        CompositionRepository compositionRepository,
        EatRepository eatRepository
    ){
        return args -> {

            List<ActivityLevelEntity> activityLevels = new ArrayList<>();
            List<ObjectiveEntity> objectives = new ArrayList<>();
            List<CategoryFoodEntity> categories = new ArrayList<>();
            List<NutrientEntity> nutrients = new ArrayList<>();
            List<FoodEntity> foods = new ArrayList<>();
            List<RecipeEntity> recipes = new ArrayList<>();
            List<RoleEntity> roles = new ArrayList<>();
            List<UserEntity> users = new ArrayList<>();
            List<UserPersonalInfoEntity> usersPersonalsInfos = new ArrayList<>();
            List<UserFitnessInfoEntity> usersFitnessInfos = new ArrayList<>();
            List<UserConfigEntity> userConfig = new ArrayList<>();
            List<IpLoginAttemptEntity> ipsLoginsAttempts = new ArrayList<>();
            List<CompositionEntity> compositions = new ArrayList<>();
            List<EatEntity> eats = new ArrayList<>();

            if(activityLevelRepository.count() == 0) {
                activityLevels = Arrays.asList(
                    new ActivityLevelEntity(null, "Poco o ningun ejercicio", 1.2, "Ningun dia", "Personas sedentarias que realizan poco o ningún tipo de actividad física.", null),
                    new ActivityLevelEntity(null, "Ejercicio ligero", 1.375, "1 - 3 dias", "Actividad ligera como caminar casualmente o tareas diarias no exigentes.", null),
                    new ActivityLevelEntity(null, "Ejercicio moderado", 1.55, "3 - 5 dias", "Actividad moderada que puede incluir trabajos que requieran actividad física o ejercicios regulares.", null),
                    new ActivityLevelEntity(null, "Ejercicio intenso", 1.725, "5 - 7 dias", "Ejercicio intenso o trabajos físicamente demandantes.", null),
                    new ActivityLevelEntity(null, "Ejercicio muy intenso", 1.9, "Atleta profesional", "Atletas o individuos con trabajos extremadamente físicos.", null),
                    // Más niveles de actividad física
                    new ActivityLevelEntity(null, "Caminata ocasional", 1.4, "1-2 días", "Caminatas cortas y ocasionales", null),
                    new ActivityLevelEntity(null, "Caminata diaria", 1.6, "5-6 días", "Caminatas diarias de al menos 30 minutos", null),
                    new ActivityLevelEntity(null, "Deporte recreativo", 1.65, "2-3 días", "Fútbol, básquetbol u otro deporte recreativo durante la semana", null),
                    new ActivityLevelEntity(null, "Entrenamiento de fuerza", 1.8, "3-4 días", "Entrenamiento de pesas o calistenia varios días a la semana", null)
                );
                activityLevelRepository.saveAll(activityLevels);
            }
            if(objectiveRepository.count() == 0) {
                objectives = Arrays.asList(
                    new ObjectiveEntity(null, "Bajar de peso", "Objetivo centrado en reducir el peso corporal a través de una dieta equilibrada.", null),
                    new ObjectiveEntity(null, "Aumentar masa muscular", "Objetivo centrado en ganar masa muscular a través de una dieta rica en proteínas.", null),
                    new ObjectiveEntity(null, "Mantener el peso", "Objetivo centrado en mantener el peso corporal actual.", null),
                    new ObjectiveEntity(null, "Aumentar resistencia", "Objetivo centrado en aumentar la resistencia física a través de una dieta equilibrada.", null),
                    new ObjectiveEntity(null, "Mejorar salud cardiaca", "Objetivo centrado en fortalecer el corazón y sistema circulatorio.", null),
                    new ObjectiveEntity(null, "Mejorar salud ósea", "Objetivo centrado en fortalecer los huesos y prevenir enfermedades óseas.", null),
                    new ObjectiveEntity(null, "Reducir grasa corporal", "Objetivo centrado en reducir el porcentaje de grasa corporal.", null),
                    new ObjectiveEntity(null, "Mejorar digestión", "Objetivo centrado en promover una digestión saludable a través de una dieta rica en fibra.", null),
                    // Más objetivos secundarios
                    new ObjectiveEntity(null, "Mejorar sistema inmune", "Objetivo para fortalecer el sistema inmunológico.", null),  
                    new ObjectiveEntity(null, "Controlar el colesterol", "Objetivo para reducir los niveles de colesterol.", null),
                    new ObjectiveEntity(null, "Controlar la glucosa", "Objetivo para mantener niveles saludables de azúcar en la sangre.", null),
                    new ObjectiveEntity(null, "Mejorar piel y cabello", "Objetivo para mejorar la apariencia de piel y cabello.", null)
                );
                objectiveRepository.saveAll(objectives);
            }
            if(nutrientRepository.count() == 0) {
                nutrients = Arrays.asList(
                    new NutrientEntity(null, "Proteína", "Macronutriente esencial para la construcción de masa muscular.", "...", null),
                    new NutrientEntity(null, "Grasa", "Macronutriente esencial para la energía.", "...", null),
                    new NutrientEntity(null, "Carbohidratos", "Principal fuente de energía.", "...", null),
                    new NutrientEntity(null, "Vitamina B6", "Vitamina esencial para el metabolismo.", "...", null),
                    new NutrientEntity(null, "Fósforo", "Mineral esencial para la salud ósea.", "...", null),
                    new NutrientEntity(null, "Magnesio", "Mineral esencial para diversas funciones biológicas.", "...", null),
                    new NutrientEntity(null, "Vitamina C", "Antioxidante y esencial para la salud inmunológica.", "...", null),
                    new NutrientEntity(null, "Vitamina K", "Vitamina esencial para la coagulación de la sangre.", "...", null),
                    new NutrientEntity(null, "Fibra", "Mejora la digestión y reduce el colesterol.", "...", null),
                    new NutrientEntity(null, "Calcio", "Fortalece los huesos y dientes.", "...", null),
                    new NutrientEntity(null, "Hierro", "Transporta oxígeno en la sangre.", "...", null),
                    new NutrientEntity(null, "Potasio", "Ayuda en la transmisión nerviosa.", "...", null),
                    new NutrientEntity(null, "Zinc", "Fortalece el sistema inmunológico.", "...", null)
                );
                nutrientRepository.saveAll(nutrients);
            }
            if(categoryFoodRepository.count() == 0) {
                categories = Arrays.asList(
                    new CategoryFoodEntity(null, "Aves", "Información aves", "Beneficios aves", "Desventajas aves", null),
                    new CategoryFoodEntity(null, "Granos", "Información granos", "Beneficios granos", "Desventajas granos", null),
                    new CategoryFoodEntity(null, "Verduras", "Información verduras", "Beneficios verduras", "Desventajas verduras", null),
                    new CategoryFoodEntity(null, "Frutas", "Información frutas", "Beneficios frutas", "Desventajas frutas", null),
                    new CategoryFoodEntity(null, "Legumbres", "Información legumbres", "Beneficios legumbres", "Desventajas legumbres", null),
                    new CategoryFoodEntity(null, "Mariscos", "Información mariscos", "Beneficios mariscos", "Desventajas mariscos", null),
                    new CategoryFoodEntity(null, "Lácteos", "Información lácteos", "Beneficios lácteos", "Desventajas lácteos", null)
                );
                categoryFoodRepository.saveAll(categories);
            }
            if(foodRepository.count() == 0) {
                foods = Arrays.asList(
                    new FoodEntity(null, "Pollo", "Pechuga sin piel, cocida", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/pechuga-de-pollo", null, getCategoryByName(categories, "Aves"), null, null),
                    new FoodEntity(null, "Arroz", "Arroz blanco, cocido", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/arroz-blanco", null, getCategoryByName(categories, "Granos"), null, null),
                    new FoodEntity(null, "Brocoli", "Brocoli cocido", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/br%C3%B3coli", null, getCategoryByName(categories, "Verduras"), null, null),
                    new FoodEntity(null, "Plátano", "Plátano maduro crudo", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/pl%C3%A1tano-(crudo)", null, getCategoryByName(categories, "Frutas"), null, null),
                    new FoodEntity(null, "Lentejas", "Lentejas cocidas", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/lentejas-cocidas", null, getCategoryByName(categories, "Legumbres"), null, null),    
                    new FoodEntity(null, "Salmón", "Salmón a la parrilla", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/salm%C3%B3n-a-la-parrilla", null, getCategoryByName(categories, "Mariscos"), null, null),
                    new FoodEntity(null, "Leche", "Leche entera", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/leche-entera-(3.25%25-grasa)", null, getCategoryByName(categories, "Lácteos"), null, null)
                );
                foodRepository.saveAll(foods);
            }
            // CAMBIAR POR EL NOMBRE DE PERSONAS REALES
            if(recipeRepository.count() == 0) {
                recipes = Arrays.asList(
                    new RecipeEntity(null, "Arroz con pollo", "Delicioso arroz con pollo y verduras", "1. Cocinar el pollo.\n2. Cocinar el arroz y mezclar con el pollo.\n3. Añadir brócoli al gusto.", "Rico en proteínas y carbohidratos", "Puede ser alto en calorías", 4, PrivacityEnum.PUBLIC, false, getUserByName(users, "usernameEjemplo"), Arrays.asList(getFoodByName(foods, "Pollo"), getFoodByName(foods, "Arroz"), getFoodByName(foods, "Brocoli"))),
                    new RecipeEntity(null, "Ensalada de salmón", "Ensalada fresca con salmón a la parrilla", "1. Asar el salmón.\n2. Servir sobre lechuga y añadir rodajas de plátano.", "Rico en omega 3 y potasio", "Cuidado con el consumo excesivo de sal", 5, PrivacityEnum.PUBLIC, false, getUserByName(users, "usernameEjemplo"), Arrays.asList(getFoodByName(foods, "Salmón"), getFoodByName(foods, "Plátano"))),
                    new RecipeEntity(null, "Crema de brócoli", "Sopa cremosa de brócoli", "1. Cocer el brócoli.\n2. Licuar con un poco de leche y sazonar al gusto.", "Bajo en calorías y rico en vitamina C", "Puede ser bajo en proteínas", 4, PrivacityEnum.PUBLIC, false, getUserByName(users, "usernameEjemplo"), Arrays.asList(getFoodByName(foods, "Brocoli"), getFoodByName(foods, "Leche"))),
                    new RecipeEntity(null, "Batido de plátano", "Batido refrescante de plátano", "1. Pelar y picar el plátano.\n2. Licuar con leche y servir frío.", "Rico en potasio y calcio", "Alto en azúcares naturales", 5, PrivacityEnum.PUBLIC, false, getUserByName(users, "usernameEjemplo"), Arrays.asList(getFoodByName(foods, "Plátano"), getFoodByName(foods, "Leche")))
                );
                recipeRepository.saveAll(recipes);
            }
            if(roleRepository.count() == 0) {
                roles = Arrays.asList(
                    new RoleEntity(null, RoleEnum.ADMIN),
                    new RoleEntity(null, RoleEnum.USER)
                );
                roleRepository.saveAll(roles);
            }
            if(userRepository.count() == 0) {
                RoleEntity USER = roleRepository.findByName(RoleEnum.USER);
                RoleEntity ADMIN = roleRepository.findByName(RoleEnum.ADMIN);
                List<RoleEntity> rolesUser = Arrays.asList(USER);
                List<RoleEntity> rolesAdmin = Arrays.asList(USER, ADMIN);

                users = Arrays.asList(
                    new UserEntity(null, "kiridepapel", "password123", "john@example.com", "John", "Doe", "linkImagenJohn", false, null, null, null, null, null, rolesAdmin, null, null),
                    new UserEntity(null, "heatherxvalencia", "password456", "jane@example.com", "Jane", "Doe", "linkImagenJane", false, null, null, null, null, null, rolesUser, null, null),
                    new UserEntity(null, "adminUser", "adminPass", "admin@example.com", "Admin", "User", "linkImagenAdmin", false, null, null, null, null, null, rolesUser, null, null),
                    new UserEntity(null, "editorUser", "editorPass", "editor@example.com", "Editor", "User", "linkImagenEditor", false, null, null, null, null, null, rolesUser, null, null),
                    new UserEntity(null, "viewerUser", "viewerPass", "viewer@example.com", "Viewer", "User", "linkImagenViewer", false, null, null, null, null, null, rolesUser, null, null),
                    new UserEntity(null, "contributorUser", "contributorPass", "contributor@example.com", "Contributor", "User", "linkImagenContributor", false, null, null, null, null, null, rolesUser, null, null)
                );
                userRepository.saveAll(users);
            }
            






            //
            if(compositionRepository.count() == 0) {

                // Relaciones entre alimentos y nutrientes
                Map<String, Map<String, Double>> foodNutrientMap = new HashMap<String, Map<String, Double>>() {
                    {
                        put("Pollo", new HashMap<String, Double>() {
                            {
                                put("Proteína", 31.0);
                                put("Grasa", 3.6);
                                put("Vitamina B6", 0.5);
                                put("Fósforo", 220.0);
                            }
                        });
                    
                        put("Arroz", new HashMap<String, Double>() {
                            {
                                put("Carbohidratos", 28.0);
                                put("Proteína", 2.7);
                                put("Grasa", 0.3);
                                put("Magnesio", 19.0);
                            }
                        });
                        
                        put("Brocoli", new HashMap<String, Double>() {
                            {
                                put("Proteína", 2.4);
                                put("Carbohidratos", 7.0);
                                put("Vitamina C", 64.0); 
                                put("Vitamina K", 141.0);
                            }
                        });
                        
                        put("Plátano", new HashMap<String, Double>() {
                            {
                                put("Carbohidratos", 27.0);
                                put("Fibra", 3.1);
                                put("Potasio", 422.0);
                            }
                        });
                            
                        put("Lentejas", new HashMap<String, Double>() {
                            {
                                put("Proteína", 9.0);
                                put("Fibra", 8.0); 
                                put("Hierro", 3.3);
                            }
                        });
                        
                        put("Salmón", new HashMap<String, Double>() {
                            {
                                put("Proteína", 25.0);
                                put("Grasa", 13.6);
                                put("Vitamina D", 13.1);
                                put("Vitamina B12", 5.0);
                                put("Omega 3", 2.0); 
                            }
                        });
                            
                        put("Leche", new HashMap<String, Double>() {
                            {
                                put("Proteína", 3.3);
                                put("Grasa", 3.3);
                                put("Calcio", 119.0);
                            }
                        });
                    }
                };
            
                for (Map.Entry<String, Map<String, Double>> foodEntry : foodNutrientMap.entrySet()) {
                FoodEntity food = getFoodByName(foods, foodEntry.getKey());
                for (Map.Entry<String, Double> nutrientEntry : foodEntry.getValue().entrySet()) {
                    NutrientEntity nutrient = getNutrientByName(nutrients, nutrientEntry.getKey());
                    compositions.add(new CompositionEntity(new CompositionKey(food.getId(), nutrient.getId()), food, nutrient, nutrientEntry.getValue())); 
                }
                }
            
                compositionRepository.saveAll(compositions); 
            }


            
        };
    }

    // Métodos auxiliares
    // User
    public UserEntity getUserByName(List<UserEntity> users, String userName) {
        return users.stream().filter(user -> user.getUsername().equalsIgnoreCase(userName)).findFirst().orElse(null);
    }
    // ActivityLevel
    public ActivityLevelEntity getActivityLevelByName(List<ActivityLevelEntity> activityLevels, String activityLevelName) {
        return activityLevels.stream().filter(activityLevel -> activityLevel.getName().equalsIgnoreCase(activityLevelName)).findFirst().orElse(null);
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