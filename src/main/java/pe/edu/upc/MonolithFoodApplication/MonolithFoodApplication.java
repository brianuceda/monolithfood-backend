package pe.edu.upc.MonolithFoodApplication;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

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
    
    // CommandLineRunner iniciara el codigo despues de que se hayan cargado los beans de Spring.
    @Bean
    public CommandLineRunner initData(
        ActivityLevelRepository activityLevelRepository,
        ObjectiveRepository objectiveRepository,
        CategoryRepository categoryFoodRepository,
        NutrientRepository nutrientRepository,
        FoodRepository foodRepository,
        CompositionRepository compositionRepository,
        RoleRepository roleRepository,
        UserRepository userRepository,
        RecipeRepository recipeRepository,
        IngredientRepository ingredientRepository,
        UserPersonalInfoRepository userPersonalInfoRepository,
        UserFitnessInfoRepository userFitnessInfoRepository,
        UserConfigRepository userConfigRepository,
        IpLoginAttemptRepository ipLoginAttemptRepository,
        EatRepository eatRepository
    ){
        return args -> {

            List<ActivityLevelEntity> activityLevels = new ArrayList<>();
            List<ObjectiveEntity> objectives = new ArrayList<>();
            List<CategoryFoodEntity> categories = new ArrayList<>();
            List<NutrientEntity> nutrients = new ArrayList<>();
            List<FoodEntity> foods = new ArrayList<>();
            List<CompositionEntity> compositions = new ArrayList<>();
            List<RoleEntity> roles = new ArrayList<>();
            List<UserEntity> users = new ArrayList<>();
            List<RecipeEntity> recipes = new ArrayList<>();
            List<IngredientEntity> ingredients = new ArrayList<>();
            List<UserPersonalInfoEntity> usersPersonalInfos = new ArrayList<>();
            List<UserFitnessInfoEntity> usersFitnessInfos = new ArrayList<>();
            List<UserConfigEntity> usersConfig = new ArrayList<>();
            List<IpLoginAttemptEntity> ipsLoginsAttempts = new ArrayList<>();
            // List<EatEntity> eats = new ArrayList<>();

            if(activityLevelRepository.count() == 0) {
                activityLevels = Arrays.asList(
                    new ActivityLevelEntity(null, "Poco o ningun ejercicio", 1.2, "Ningun dia", "Personas sedentarias que realizan poco o ningun tipo de actividad fisica.", null),
                    new ActivityLevelEntity(null, "Ejercicio ligero", 1.375, "1 - 3 dias", "Actividad ligera como caminar casualmente o tareas diarias no exigentes.", null),
                    new ActivityLevelEntity(null, "Ejercicio moderado", 1.55, "3 - 5 dias", "Actividad moderada que puede incluir trabajos que requieran actividad fisica o ejercicios regulares.", null),
                    new ActivityLevelEntity(null, "Ejercicio intenso", 1.725, "5 - 7 dias", "Ejercicio intenso o trabajos fisicamente demandantes.", null),
                    new ActivityLevelEntity(null, "Ejercicio muy intenso", 1.9, "Atleta profesional", "Atletas o individuos con trabajos extremadamente fisicos.", null),
                    // Mas niveles de actividad fisica
                    new ActivityLevelEntity(null, "Caminata ocasional", 1.4, "1-2 dias", "Caminatas cortas y ocasionales", null),
                    new ActivityLevelEntity(null, "Caminata diaria", 1.6, "5-6 dias", "Caminatas diarias de al menos 30 minutos", null),
                    new ActivityLevelEntity(null, "Deporte recreativo", 1.65, "2-3 dias", "Futbol, basquetbol u otro deporte recreativo durante la semana", null),
                    new ActivityLevelEntity(null, "Entrenamiento de fuerza", 1.8, "3-4 dias", "Entrenamiento de pesas o calistenia varios dias a la semana", null)
                );
                activityLevelRepository.saveAll(activityLevels);
            }
            if(objectiveRepository.count() == 0) {
                objectives = Arrays.asList(
                    new ObjectiveEntity(null, "Bajar de peso", "Objetivo centrado en reducir el peso corporal a traves de una dieta equilibrada.", null),
                    new ObjectiveEntity(null, "Aumentar masa muscular", "Objetivo centrado en ganar masa muscular a traves de una dieta rica en proteinas.", null),
                    new ObjectiveEntity(null, "Mantener el peso", "Objetivo centrado en mantener el peso corporal actual.", null),
                    new ObjectiveEntity(null, "Aumentar resistencia", "Objetivo centrado en aumentar la resistencia fisica a traves de una dieta equilibrada.", null),
                    new ObjectiveEntity(null, "Mejorar salud cardiaca", "Objetivo centrado en fortalecer el corazon y sistema circulatorio.", null),
                    new ObjectiveEntity(null, "Mejorar salud osea", "Objetivo centrado en fortalecer los huesos y prevenir enfermedades oseas.", null),
                    new ObjectiveEntity(null, "Reducir grasa corporal", "Objetivo centrado en reducir el porcentaje de grasa corporal.", null),
                    new ObjectiveEntity(null, "Mejorar digestion", "Objetivo centrado en promover una digestion saludable a traves de una dieta rica en fibra.", null),
                    // Mas objetivos secundarios
                    new ObjectiveEntity(null, "Mejorar sistema inmune", "Objetivo para fortalecer el sistema inmunologico.", null),  
                    new ObjectiveEntity(null, "Controlar el colesterol", "Objetivo para reducir los niveles de colesterol.", null),
                    new ObjectiveEntity(null, "Controlar la glucosa", "Objetivo para mantener niveles saludables de azucar en la sangre.", null),
                    new ObjectiveEntity(null, "Mejorar piel y cabello", "Objetivo para mejorar la apariencia de piel y cabello.", null)
                );
                objectiveRepository.saveAll(objectives);
            }
            if(nutrientRepository.count() == 0) {
                nutrients = Arrays.asList(
                    new NutrientEntity(null, "Calorias", "Unidad de medida de energia que se obtiene de los alimentos.", "...", null),
                    new NutrientEntity(null, "Proteina", "Macronutriente esencial para la construccion de masa muscular.", "...", null),
                    new NutrientEntity(null, "Grasa", "Macronutriente esencial para la energia.", "...", null),
                    new NutrientEntity(null, "Carbohidratos", "Principal fuente de energia.", "...", null),
                    new NutrientEntity(null, "Vitamina B6", "Vitamina esencial para el metabolismo.", "...", null),
                    new NutrientEntity(null, "Fosforo", "Mineral esencial para la salud osea.", "...", null),
                    new NutrientEntity(null, "Magnesio", "Mineral esencial para diversas funciones biologicas.", "...", null),
                    new NutrientEntity(null, "Vitamina C", "Antioxidante y esencial para la salud inmunologica.", "...", null),
                    new NutrientEntity(null, "Vitamina K", "Vitamina esencial para la coagulacion de la sangre.", "...", null),
                    new NutrientEntity(null, "Fibra", "Mejora la digestion y reduce el colesterol.", "...", null),
                    new NutrientEntity(null, "Azucares", "Carbohidratos simples que proporcionan energia.", "...", null),
                    new NutrientEntity(null, "Calcio", "Fortalece los huesos y dientes.", "...", null),
                    new NutrientEntity(null, "Hierro", "Transporta oxigeno en la sangre.", "...", null),
                    new NutrientEntity(null, "Potasio", "Ayuda en la transmision nerviosa.", "...", null),
                    new NutrientEntity(null, "Zinc", "Fortalece el sistema inmunologico.", "...", null),
                    new NutrientEntity(null, "Vitamina D", "Vitamina esencial para la salud de los huesos.", "...", null),
                    new NutrientEntity(null, "Vitamina B12", "Vitamina esencial para la formacion de globulos rojos.", "...", null),
                    new NutrientEntity(null, "Omega 3", "acido graso esencial beneficioso para la salud cardiovascular.", "...", null),
                    new NutrientEntity(null, "Selenio", "Mineral esencial con propiedades antioxidantes.", "...", null),
                    new NutrientEntity(null, "Aminoacidos BCAA", "Aminoacidos de cadena ramificada que promueven la recuperacion muscular.", "...", null),
                    new NutrientEntity(null, "Colina", "Nutriente esencial importante para la funcion cerebral y la salud hepatica.", "...", null),
                    new NutrientEntity(null, "Alcohol", "Calorias derivadas del consumo de alcohol.", "...", null),
                    new NutrientEntity(null, "Capsaicina", "Compuesto picante presente en los pimientos.", "...", null),
                    new NutrientEntity(null, "Fructosa", "Tipo de azucar natural presente en las frutas.", "...", null),
                    new NutrientEntity(null, "Glucosa", "Azucar simple que es una fuente de energia inmediata para el cuerpo.", "...", null),
                    new NutrientEntity(null, "Sacarosa", "Azucar de mesa comun.", "...", null),
                    new NutrientEntity(null, "Folato", "Vitamina B importante para la sintesis del ADN y el crecimiento celular.", "...", null),
                    new NutrientEntity(null, "Vitamina A", "Vitamina esencial para la vision y la salud de la piel.", "...", null),
                    new NutrientEntity(null, "Vainillina", "Compuesto quimico que proporciona el sabor y aroma caracteristicos de la vainilla.", "...", null),
                    new NutrientEntity(null, "Vitamina E", "Vitamina liposoluble con propiedades antioxidantes.", "...", null),
                    new NutrientEntity(null, "acido Graso Omega 6", "Tipo de acido graso esencial.", "...", null),
                    new NutrientEntity(null, "Sodio", "Mineral esencial necesario para el equilibrio de liquidos y la funcion nerviosa.", "...",null),
                    new NutrientEntity(null, "Fosfolipidos", "Tipo de lipido esencial que forma parte de las membranas celulares.", "...", null),
                    new NutrientEntity(null, "Resveratrol", "Polifenol con propiedades antioxidantes encontrado en ciertos alimentos como el vino tinto.", "...", null),
                    new NutrientEntity(null, "Antioxidantes", "Compuestos que protegen las celulas del danio oxidativo.", "...", null)
                );
                nutrientRepository.saveAll(nutrients);
            }
            if(categoryFoodRepository.count() == 0) {
                categories = Arrays.asList(
                    new CategoryFoodEntity(null, "Carnes y Aves", "Informacion sobre carnes y aves.", "Beneficios de carnes y aves.", "Desventajas de carnes y aves", null),
                    new CategoryFoodEntity(null, "Pescados y Mariscos", "Informacion sobre pescados y mariscos.", "Beneficios de pescados y mariscos.", "Desventajas de pescados y mariscos", null),
                    new CategoryFoodEntity(null, "Vegetales", "Informacion sobre vegetales.", "Beneficios de vegetales.", "Desventajas de vegetales", null),
                    new CategoryFoodEntity(null, "Frutas", "Informacion sobre frutas.", "Beneficios de frutas.", "Desventajas de frutas", null),
                    new CategoryFoodEntity(null, "Lacteos", "Informacion sobre lacteos.", "Beneficios de lacteos.", "Desventajas de lacteos", null),
                    new CategoryFoodEntity(null, "Huevos", "Informacion sobre huevos.", "Beneficios de huevos.", "Desventajas de huevos", null),
                    new CategoryFoodEntity(null, "Salsas y Endulzantes", "Informacion sobre salsas y endulzantes.", "Beneficios de salsas y endulzantes.", "Desventajas de salsas y endulzantes", null),
                    new CategoryFoodEntity(null, "Especias", "Informacion sobre especias.", "Beneficios de especias.", "Desventajas de especias", null),
                    new CategoryFoodEntity(null, "Dulces", "Informacion sobre dulces.", "Beneficios de dulces.", "Desventajas de dulces", null),
                    new CategoryFoodEntity(null, "Bebidas", "Informacion sobre bebidas.", "Beneficios de bebidas.", "Desventajas de bebidas", null)
                );
                categoryFoodRepository.saveAll(categories);
            }
            if(foodRepository.count() == 0) {
                foods = Arrays.asList(
                    new FoodEntity(null, "Pollo", "Pechuga sin piel, cocida", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/pechuga-de-pollo-sin-piel", null, getCategoryByName(categories, "Aves"), null, null),
                    new FoodEntity(null, "Arroz", "Arroz blanco, cocido", PrivacityEnum.PUBLIC, false, "https://bing.com/search?q=Arroz+blanco%2c+cocido+site%3afatsecret.es", null, getCategoryByName(categories, "Granos"), null, null),
                    new FoodEntity(null, "Brocoli", "Brocoli cocido", PrivacityEnum.PUBLIC, false, "https://bing.com/search?q=Brocoli+cocido+site%3afatsecret.es", null, getCategoryByName(categories, "Verduras"), null, null),
                    new FoodEntity(null, "Platano", "Platano maduro crudo", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/pl%C3%A1tano-maduro", null, getCategoryByName(categories, "Frutas"), null, null),
                    new FoodEntity(null, "Lentejas", "Lentejas cocidas", PrivacityEnum.PUBLIC, false, "https://bing.com/search?q=Lentejas+cocidas+site%3afatsecret.es", null, getCategoryByName(categories, "Legumbres"), null, null),
                    new FoodEntity(null, "Salmon", "Salmon a la parrilla", PrivacityEnum.PUBLIC, false, "https://bing.com/search?q=Arroz+blanco%2c+cocido+site%3afatsecret.es", null, getCategoryByName(categories, "Mariscos"), null, null),
                    new FoodEntity(null, "Leche", "Leche entera", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/leche-(leche-entera)", null, getCategoryByName(categories, "Lacteos"), null, null),
                    new FoodEntity(null, "Pescado", "Filete de pescado a la parrilla", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/filete-de-pescado-al-horno-o-la-parrilla?portionid=50627&portionamount=100,000", null, getCategoryByName(categories, "Mariscos"), null, null),
                    new FoodEntity(null, "Cebolla", "Cebolla cruda picada", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/cebollas-rojas?portionid=340024&portionamount=1,000", null, getCategoryByName(categories, "Verduras"), null, null),
                    new FoodEntity(null, "Limon", "Limon fresco", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/lim%C3%B3n", null, getCategoryByName(categories, "Frutas"), null, null),
                    new FoodEntity(null, "Aji", "Aji picante", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/aj%C3%AD-verde", null, getCategoryByName(categories, "Especias"), null, null),
                    new FoodEntity(null, "Carne", "Filete de carne a la parrilla", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/filete-de-ternera?portionid=50263&portionamount=200,000", null, getCategoryByName(categories, "Carnes"), null, null),
                    new FoodEntity(null, "Papas", "Papas fritas", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/papas-fritas", null, getCategoryByName(categories, "Tuberculos"), null, null),
                    new FoodEntity(null, "Tomate", "Tomate fresco", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/hacendado/tomate-frito/100g", null, getCategoryByName(categories, "Verduras"), null, null),
                    new FoodEntity(null, "Ajo", "Ajo picado", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/ajo-picado", null, getCategoryByName(categories, "Especias"), null, null),
                    new FoodEntity(null, "Queso", "Queso cheddar", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/queso-cheddar", null, getCategoryByName(categories, "Lacteos"), null, null),
                    new FoodEntity(null, "Lechuga", "Lechuga iceberg", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/lechuga-iceberg-(incluye-los-arrepollos)", null, getCategoryByName(categories, "Verduras"), null, null),
                    new FoodEntity(null, "Aceituna", "Aceitunas verdes", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/aceitunas-verdes", null, getCategoryByName(categories, "Frutas"), null, null),
                    new FoodEntity(null, "Huevo", "Huevo cocido", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/huevo-cocido", null, getCategoryByName(categories, "Huevos"), null, null),
                    new FoodEntity(null, "Mayonesa", "Mayonesa casera", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/search?q=Mayonesa+Casera", null, getCategoryByName(categories, "Salsas"), null, null),
                    new FoodEntity(null, "Palta", "Palta (aguacate)", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/aguacates?portionid=32968&portionamount=0,500", null, getCategoryByName(categories, "Frutas"), null, null),
                    new FoodEntity(null, "Cecina", "Cecina de res", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/search?q=Cecina+de+Pecho+de+Res+%28Salados%2c+Cocidos%29", null, getCategoryByName(categories, "Carnes"), null, null),
                    new FoodEntity(null, "Gallina", "Gallina asada", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/pollo-asado", null, getCategoryByName(categories, "Aves"), null, null),
                    new FoodEntity(null, "Vino", "Vino tinto", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/alimentos/vino-tinto", null, getCategoryByName(categories, "Bebidas"), null, null),
                    new FoodEntity(null, "Azucar", "Azucar blanco", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/azucarera/az%C3%BAcar-blanco/1-cucharadita", null, getCategoryByName(categories, "Endulzantes"), null, null),
                    new FoodEntity(null, "Claras de huevo", "Claras de huevo batidas", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/clara-de-huevo", null, getCategoryByName(categories, "Huevos"), null, null),
                    new FoodEntity(null, "Esencia de vainilla", "Esencia de vainilla pura", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/extracto-de-vainilla", null, getCategoryByName(categories, "Especias"), null, null),
                    new FoodEntity(null, "Manjarblanco", "Manjar blanco casero", PrivacityEnum.PUBLIC, false, "https://www.myfitnesspal.com/es/food/calories/manjar-blanco-1158827675", null, getCategoryByName(categories, "Dulces"), null, null)
                );
                foodRepository.saveAll(foods);
            }
            if(compositionRepository.count() == 0) {
                Map<String, Map<String, Double>> nutrientCompositionMap = new HashMap<String, Map<String, Double>>() {
                    {
                        put("Pollo", Map.of("Proteina", 31.0, "Grasa", 3.6, "Vitamina B6", 0.5, "Fosforo", 220.0));
                        put("Arroz", Map.of("Carbohidratos", 28.0, "Proteina", 2.7, "Grasa", 0.3, "Magnesio", 19.0));
                        put("Brocoli", Map.of("Proteina", 2.4, "Carbohidratos", 7.0, "Vitamina C", 64.0, "Vitamina K", 141.0));
                        put("Platano", Map.of("Carbohidratos", 27.0, "Fibra", 3.1, "Potasio", 422.0));
                        put("Lentejas", Map.of("Proteina", 9.0, "Fibra", 8.0, "Hierro", 3.3));
                        put("Salmon", Map.of("Proteina", 25.0, "Grasa", 13.6, "Vitamina D", 13.1, "Vitamina B12", 5.0, "Omega 3", 2.0));
                        put("Leche", Map.of("Proteina", 3.3, "Grasa", 3.3, "Calcio", 119.0));
                        put("Pescado", Map.of("Proteina", 22.0, "Grasa", 6.5, "Vitamina D", 10.2, "Selenio", 41.0, "Omega 3", 1.8));
                        put("Cebolla", Map.of("Carbohidratos", 9.3, "Fibra", 1.7, "Vitamina C", 7.4, "Folato", 23.0));
                        put("Limon", Map.of("Carbohidratos", 8.2, "Vitamina C", 53.0, "Calcio", 26.0, "Potasio", 138.0));
                        put("Aji", Map.of("Carbohidratos", 6.0, "Fibra", 2.4, "Vitamina A", 1033.0, "Capsaicina", 21.0));
                        put("Carne", Map.of("Proteina", 26.0, "Grasa", 9.0, "Hierro", 2.5, "Zinc", 3.6, "Vitamina B12", 2.0));
                        put("Papas", Map.of("Carbohidratos", 17.5, "Fibra", 2.2, "Vitamina C", 19.0, "Potasio", 429.0));
                        put("Tomate", Map.of("Carbohidratos", 3.9, "Fibra", 1.2, "Vitamina C", 14.0, "Vitamina K", 7.9));
                        put("Ajo", Map.of("Carbohidratos", 33.1, "Fibra", 2.1, "Vitamina C", 31.0, "Selenio", 14.0));
                        put("Queso", Map.of("Proteina", 25.0, "Grasa", 33.0, "Calcio", 721.0, "Fosforo", 509.0));
                        put("Lechuga", Map.of("Carbohidratos", 1.4, "Fibra", 1.0, "Vitamina A", 430.0, "Vitamina K", 74.8));
                        put("Aceituna", Map.of("Grasa", 14.0, "Fibra", 3.0, "Calcio", 88.0, "Sodio", 1462.0));
                        put("Huevo", Map.of("Proteina", 6.3, "Grasa", 4.8, "Vitamina D", 1.1, "Colina", 126.0));
                        put("Mayonesa", Map.of("Grasa", 75.0, "Vitamina K", 2.2, "Vitamina E", 3.0, "acido Graso Omega 6", 22.0));
                        put("Palta", Map.of("Grasa", 14.7, "Fibra", 6.7, "Vitamina K", 21.0, "Folato", 81.0));
                        put("Cecina", Map.of("Proteina", 25.0, "Grasa", 3.0, "Hierro", 3.5, "Sodio", 1100.0));
                        put("Gallina", Map.of("Proteina", 27.0, "Grasa", 3.6, "Hierro", 1.1, "Selenio", 22.0));
                        put("Vino", Map.of("Alcohol", 13.5, "Resveratrol", 0.9, "Antioxidantes", 12.0));
                        put("Azucar", Map.of("Carbohidratos", 99.8, "Calorias", 387.0, "Fructosa", 50.0, "Glucosa", 50.0, "Sacarosa", 50.0));
                        put("Claras de huevo", Map.of("Proteina", 10.9, "Aminoacidos BCAA", 4.7, "Calcio", 6.0, "Fosfolipidos", 40.0));
                        put("Esencia de vainilla", Map.of("Calorias", 288.0, "Azucares", 71.9, "Alcohol", 35.0, "Vainillina", 200.0));
                        put("Manjarblanco", Map.of("Carbohidratos", 77.0, "Azucar", 56.0, "Grasa", 7.0, "Calcio", 187.0, "Hierro", 1.2));
                    }
                };
                // Crear un mapa para buscar FoodEntity por nombre
                Map<String, FoodEntity> foodNameMap = generateNameMap(foods, FoodEntity::getName);
                // Recorrer la estructura de datos y realizar inserciones
                for (Map.Entry<String, Map<String, Double>> foodEntry : nutrientCompositionMap.entrySet()) {
                    String foodName = foodEntry.getKey();
                    FoodEntity food = foodNameMap.get(foodName);
                    if (food != null) {
                        for (Map.Entry<String, Double> nutrientEntry : foodEntry.getValue().entrySet()) {
                            String nutrientName = nutrientEntry.getKey();
                            Double nutrientQuantity = nutrientEntry.getValue();
                            NutrientEntity nutrient = getNutrientByName(nutrients, nutrientName);
                            if (nutrient != null) {
                                compositions.add(new CompositionEntity(new CompositionKey(food.getId(), nutrient.getId()), food, nutrient, nutrientQuantity));
                            }
                        }
                    }
                }
                compositionRepository.saveAll(compositions);
            }
            if(roleRepository.count() == 0) {
                roles = Arrays.asList(
                    new RoleEntity(null, RoleEnum.ADMIN),
                    new RoleEntity(null, RoleEnum.USER)
                );
                roleRepository.saveAll(roles);
            }
            // Crea un user con objectives y roles
            if(userRepository.count() == 0) {
                // Roles disponibles
                RoleEntity USER = roleRepository.findByName(RoleEnum.USER);
                RoleEntity ADMIN = roleRepository.findByName(RoleEnum.ADMIN);
                // Creacion de usuarios
                users = Arrays.asList(
                    new UserEntity(null, "kiridepapel", "40se02j7", "brian@gmail.com", "Brian", "Uceda", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", false, null, null, null, null, getRandomObjectives(objectives, 7), Arrays.asList(ADMIN, USER), null, null),
                    new UserEntity(null, "heatherxvalencia", "mongolita123", "heather@gmail.com", "Heather", "Valencia", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", false, null, null, null, null, getRandomObjectives(objectives, 6), Arrays.asList(USER), null, null),
                    new UserEntity(null, "whoami", "1_^+=1,.22", "willy@gmail.com", "Willy", "Samata", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", false, null, null, null, null, getRandomObjectives(objectives, 2), Arrays.asList(USER), null, null),
                    new UserEntity(null, "nayde", "gatitobello123", "nayde@gmail.com", "Naydeline", "Duran", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", false, null, null, null, null, getRandomObjectives(objectives, 5), Arrays.asList(USER), null, null),
                    new UserEntity(null, "gabi", "prolenvalorant", "gabriela@gmail.com", "Gabriela", "Reyes", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", false, null, null, null, null, getRandomObjectives(objectives, 5), Arrays.asList(USER), null, null),
                    new UserEntity(null, "impresora", "lenovo", "edson@gmail.com", "Edson", "Linares", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", false, null, null, null, null, getRandomObjectives(objectives, 2), Arrays.asList(USER), null, null)
                );
                userRepository.saveAll(users);
            }
            if(recipeRepository.count() == 0) {
                recipes = Arrays.asList(
                    new RecipeEntity(null, "Arroz con pollo", "Plato tradicional peruano...", "1. Cocinar el arroz...", null, null, null, PrivacityEnum.PUBLIC, false, null, null),
                    new RecipeEntity(null, "Ceviche", "Plato bandera del Peru...", "1. Cortar el pescado...", null, null, null, PrivacityEnum.PUBLIC, false, null, null),
                    new RecipeEntity(null, "Lomo saltado", "Plato de la cocina criolla...", "1. Saltear la carne...", null, null, null, PrivacityEnum.PUBLIC, false, null, null),
                    new RecipeEntity(null, "Tacacho con cecina", "Plato amazonico...", "1. Cocinar el platano...", null, null, null, PrivacityEnum.PUBLIC, false, null, null),
                    new RecipeEntity(null, "Papa a la huancaina", "Entrante cremoso y picante...", "1. Cocinar las papas...", null, null, null, PrivacityEnum.PUBLIC, false, null, null),
                    new RecipeEntity(null, "Causa limenia", "Plato frio peruano...", "1. Cocinar el pollo y papas...", null, null, null, PrivacityEnum.PUBLIC, false, null, null),
                    new RecipeEntity(null, "Aji de gallina", "Plato representativo del Peru...", "1. Cocinar el pollo en salsa...", null, null, null, PrivacityEnum.PUBLIC, false, null, null),
                    new RecipeEntity(null, "Suspiro limenio", "Postre tradicional de manjarblanco...", "1. Batir las claras a nieve...", null, null, null, PrivacityEnum.PUBLIC, false, null, null)
                );
                recipeRepository.saveAll(recipes);
            }
            if(ingredientRepository.count() == 0) {
                Map<String, Map<String, Double>> recipeIngredientMap = new HashMap<String, Map<String, Double>>() {
                    {
                        put("Arroz con pollo", Map.of("Pollo", 0.5, "Arroz", 0.2, "Brocoli", 0.1, "Ajo", 0.05));
                        put("Ceviche", Map.of("Pescado", 0.3, "Cebolla", 0.1, "Limon", 0.1, "Aji", 0.05));
                        put("Lomo saltado", Map.of("Carne", 0.4, "Cebolla", 0.1, "Tomate", 0.1, "Arroz", 0.2, "Papas", 0.2));
                        put("Tacacho con cecina", Map.of("Platano", 0.3, "Cecina", 0.2, "Tomate", 0.1, "Cebolla", 0.1, "Aji", 0.1));
                        put("Papa a la huancaina", Map.of("Papa", 0.4, "Queso", 0.2, "Lechuga", 0.1, "Aceituna", 0.05, "Huevo", 0.05, "Aji", 0.1));
                        put("Causa limenia", Map.of("Papa", 0.3, "Pollo", 0.2, "Mayonesa", 0.1, "Palta", 0.1, "Limon", 0.05, "Aji", 0.05));
                        put("Aji de gallina", Map.of("Gallina", 0.3, "Papas", 0.2, "Queso", 0.1, "Aji", 0.1, "Cebolla", 0.05, "Ajo", 0.05, "Vino", 0.05));
                        put("Suspiro limenio", Map.of("Azucar", 0.1, "Claras de huevo", 0.05, "Esencia de vainilla", 0.01, "Manjarblanco", 0.2));
                    }
                };
                // Crear un mapa para buscar los tipos por nombre
                Map<String, FoodEntity> foodNameMap = generateNameMap(foods, FoodEntity::getName);
                Map<String, RecipeEntity> recipeNameMap = generateNameMap(recipes, RecipeEntity::getName);
                // Recorrer la estructura de datos y realizar inserciones
                for (Map.Entry<String, Map<String, Double>> entry : recipeIngredientMap.entrySet()) {
                    String recipeName = entry.getKey();
                    RecipeEntity recipe = recipeNameMap.get(recipeName);
                    if (recipe != null) {
                        for (Map.Entry<String, Double> ingredientEntry : entry.getValue().entrySet()) {
                            String foodName = ingredientEntry.getKey();
                            Double quantity = ingredientEntry.getValue();
                            // Buscar FoodEntity por nombre y crear un nuevo IngredientEntity
                            FoodEntity food = foodNameMap.get(foodName);
                            if (food != null) {
                                ingredients.add(new IngredientEntity(new IngredientKey(food.getId(), recipe.getId()), food, recipe, quantity));
                            }
                        }
                    }
                }
                ingredientRepository.saveAll(ingredients);
            }
            if(userPersonalInfoRepository.count() == 0) {
                GenderEnum[] genders = GenderEnum.values();
                for (UserEntity user : users) {
                    Random rand = new Random();
                    GenderEnum gender = genders[rand.nextInt(genders.length)];
                    Timestamp birthdate = Timestamp.valueOf(LocalDate.of(1980 + rand.nextInt(30), 1 + rand.nextInt(11), 1 + rand.nextInt(28)).atStartOfDay());
                    // Altura aleatoria entre 150 y 200 cm
                    double height = 150.0 + rand.nextInt(50);
                    // Peso aleatorio entre 50 y 100 kg
                    double weight = 50.0 + rand.nextInt(50);
                    ActivityLevelEntity activityLevel = activityLevels.get(rand.nextInt(activityLevels.size()));
                    UserPersonalInfoEntity userPF = new UserPersonalInfoEntity(null, gender, birthdate, height, weight, activityLevel, user);
                    usersPersonalInfos.add(userPF);
                }
                userPersonalInfoRepository.saveAll(usersPersonalInfos);
            }
            if(userFitnessInfoRepository.count() == 0) {
                for (UserEntity user : users) {
                    Random rand = new Random();
                    UserFitnessInfoEntity fitnessInfo = new UserFitnessInfoEntity();
                    // Obtener el peso actual del usuario desde la tabla user_personal_info
                    Double currentWeight = usersPersonalInfos.stream()
                        .filter(info -> info.getUser().getId().equals(user.getId()))
                        .map(UserPersonalInfoEntity::getWeightKg)
                        .findFirst()
                        .orElse(0.0);
                    // Peso objetivo basado en el peso actual más un 20% - 50% adicional
                    fitnessInfo.setTargetWeightKg(currentWeight * (1 + (0.20 + (0.30 * rand.nextDouble()))));
                    // IMC aleatorio entre 18 y 30
                    fitnessInfo.setImc(18 + rand.nextDouble() * 12);
                    // Ingesta calórica aleatoria entre 1500 y 2500
                    fitnessInfo.setDailyCaloricIntake(1500 + rand.nextDouble(1000));
                    // Ingesta de proteínas diarias aleatoria entre 50g y 150g
                    fitnessInfo.setDailyProteinIntake(50.0 + rand.nextDouble() * 100);
                    // Ingesta de carbohidratos diarios aleatoria entre 150g y 400g
                    fitnessInfo.setDailyCarbohydrateIntake(150.0 + rand.nextDouble() * 250);
                    // Ingesta de grasas diarias aleatoria entre 20g y 70g
                    fitnessInfo.setDailyFatIntake(20.0 + rand.nextDouble() * 50);
                    // Asociar el UserEntity con el UserFitnessInfoEntity
                    fitnessInfo.setUser(user);
                    usersFitnessInfos.add(fitnessInfo);
                }
                userFitnessInfoRepository.saveAll(usersFitnessInfos);
            }
            if(userConfigRepository.count() == 0) {
                for (UserEntity user : users) {
                    Random rand = new Random();
                    UserConfigEntity config = new UserConfigEntity();
                    // Generación aleatoria de true y false
                    config.setNotificationsEnabled(rand.nextBoolean());
                    config.setDarkMode(rand.nextBoolean());
                    // Generación de LocalDateTime aleatorio entre hace 4 días (4) y hoy (0) para lastFoodEntry
                    config.setLastFoodEntry(Timestamp.valueOf(LocalDateTime.now().plusDays(-rand.nextInt(4))));
                    // Generación de LocalDateTime aleatorio entre hace 3 semanas (-20) y hace 3 días (-3) para lastWeightUpdate
                    config.setLastWeightUpdate(Timestamp.valueOf(LocalDateTime.now().plusDays(-(3 + rand.nextInt(18)))));
                    // Asignación de usuario
                    config.setUser(user);
                    usersConfig.add(config);
                }
                userConfigRepository.saveAll(usersConfig);
            }
            if(ipLoginAttemptRepository.count() == 0) {
                for (UserEntity user : users) {
                    Random rand = new Random();
                    // Generamos entre 1 y 3 intentos de inicio de sesión por usuario
                    int loginAttempts = 1 + rand.nextInt(3);
                    for (int i = 0; i < loginAttempts; i++) {
                        IpLoginAttemptEntity ipLoginAttempt = new IpLoginAttemptEntity();
                        // Genera una dirección IPv4 aleatoria
                        ipLoginAttempt.setIpAddress(rand.nextInt(256) + "." + rand.nextInt(256) + "." + rand.nextInt(256) + "." + rand.nextInt(256));
                        // Establece si la cuenta está bloqueada aleatoriamente
                        ipLoginAttempt.setIsAccountBlocked(false);
                        // Establece la fecha en que se bloqueó
                        ipLoginAttempt.setBlockedDate(null);
                        // Suponemos un máximo de 2 intentos
                        ipLoginAttempt.setAttemptsCount(rand.nextInt(2)); 
                        // Fecha de último intento en los últimos 3 días
                        ipLoginAttempt.setLastAttemptDate(Timestamp.valueOf(LocalDateTime.now().minusDays(rand.nextInt(3))));
                        // Asignación de usuario
                        ipLoginAttempt.setUser(user);
                        ipsLoginsAttempts.add(ipLoginAttempt);
                    }
                }
                ipLoginAttemptRepository.saveAll(ipsLoginsAttempts);
            }
        };
    }

    // Metodos auxiliares
    // User
    public UserEntity getUserByName(List<UserEntity> users, String userName) {
        return users.stream().filter(user -> user.getUsername().equalsIgnoreCase(userName)).findFirst().orElse(null);
    }
    // Objectives
    public List<ObjectiveEntity> getRandomObjectives(List<ObjectiveEntity> objectives, int numberOfObjectives) {
        List<ObjectiveEntity> shuffledObjectives = new ArrayList<>(objectives);
        Collections.shuffle(shuffledObjectives);  // Mezclamos los objetivos
        // Asegurarnos de que el número de objetivos solicitado esté en el rango permitido
        if (numberOfObjectives < 2) numberOfObjectives = 2;
        if (numberOfObjectives > 5) numberOfObjectives = 5;
        // Tomar un subconjunto de los objetivos mezclados
        return shuffledObjectives.subList(0, numberOfObjectives);
    }
    // ActivityLevel
    public ActivityLevelEntity getActivityLevelByName(List<ActivityLevelEntity> activityLevels, String activityLevelName) {
        return activityLevels.stream().filter(activityLevel -> activityLevel.getName().equalsIgnoreCase(activityLevelName)).findFirst().orElse(null);
    }
    // Category
    public CategoryFoodEntity getCategoryByName(List<CategoryFoodEntity> categories, String name) {
        return categories.stream().filter(category -> category.getName().equals(name)).findFirst().orElse(null);
    }
    // Create map of specific type
    public <T> Map<String, T> generateNameMap(List<T> entities, Function<T, String> nameFunction) {
        return entities.stream().collect(Collectors.toMap(nameFunction, Function.identity()));
    }
    // Composition
    public NutrientEntity getNutrientByName(List<NutrientEntity> nutrients, String name) {
        return nutrients.stream().filter(nutrient -> nutrient.getName().equals(name)).findFirst().orElse(null);
    }
}