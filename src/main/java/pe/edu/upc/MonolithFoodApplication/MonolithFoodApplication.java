package pe.edu.upc.MonolithFoodApplication;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
        CompositionFoodRepository compositionFoodRepository,
        RoleRepository roleRepository,
        UserRepository userRepository,
        RecipeRepository recipeRepository,
        CompositionRecipeRepository compositionRecipeRepository,
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
            List<CompositionFoodEntity> compositionsFoods = new ArrayList<>();
            List<RoleEntity> roles = new ArrayList<>();
            List<UserEntity> users = new ArrayList<>();
            List<RecipeEntity> recipes = new ArrayList<>();
            List<CompositionRecipeEntity> compositionsRecipes = new ArrayList<>();
            List<IngredientEntity> ingredients = new ArrayList<>();
            List<UserPersonalInfoEntity> usersPersonalInfos = new ArrayList<>();
            List<UserFitnessInfoEntity> usersFitnessInfos = new ArrayList<>();
            List<UserConfigEntity> usersConfig = new ArrayList<>();
            List<IpLoginAttemptEntity> ipsLoginsAttempts = new ArrayList<>();
            List<EatEntity> eats = new ArrayList<>();

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
                    new NutrientEntity(null, "Calorias", "Unidad de medida de energia que se obtiene de los alimentos.", "Las calorías provienen principalmente de los macronutrientes: proteínas, carbohidratos y grasas. El valor calórico indica la cantidad de energía almacenada en los alimentos.", null, null),
                    new NutrientEntity(null, "Proteina", "Macronutriente esencial para la construccion de masa muscular.", "Las proteínas están formadas por aminoácidos. Son importantes para la síntesis y reparación muscular, además de funciones enzimáticas, hormonales e inmunológicas.", null, null),
                    new NutrientEntity(null, "Grasa", "Macronutriente esencial para la energia.", "Los lípidos o grasas son fuente concentrada de energía. También ayudan en la absorción de vitaminas liposolubles y forman parte de las membranas celulares.", null, null),
                    new NutrientEntity(null, "Carbohidratos", "Principal fuente de energia.", "Los carbohidratos o glucidos proporcionan glucosa que es la principal fuente de energía para las células. Pueden ser simples (azúcares) o complejos (almidones).", null, null),
                    new NutrientEntity(null, "Vitamina B6", "Vitamina esencial para el metabolismo.", "La vitamina B6 participa en el metabolismo de proteínas, glucidos y grasas. Contribuye a la síntesis de neurotransmisores y hemoglobina.", null, null),
                    new NutrientEntity(null, "Fosforo", "Mineral esencial para la salud osea.", "El fósforo forma parte de huesos y dientes, también participa en el metabolismo energético, la contracción muscular y el equilibrio ácido-base.", null, null),
                    new NutrientEntity(null, "Magnesio", "Mineral esencial para diversas funciones biologicas.", "El magnesio participa en más de 300 reacciones enzimáticas del metabolismo energético, síntesis proteica, contracción muscular y neurotransmisión. Ayuda a regular la glucosa y la presión arterial.", null, null),
                    new NutrientEntity(null, "Vitamina C", "Antioxidante y esencial para la salud inmunologica.", "La vitamina C es un potente antioxidante que protege contra el daño oxidativo. También participa en la formación de colágeno, absorción de hierro y funcionamiento del sistema inmunológico.", null, null),
                    new NutrientEntity(null, "Vitamina K", "Vitamina esencial para la coagulacion de la sangre.", "La vitamina K es necesaria para la síntesis de protrombina, un factor de coagulación sanguínea. También parece estar implicada en la salud ósea y prevención del calcio en arterias.", null, null),
                    new NutrientEntity(null, "Fibra", "Mejora la digestion y reduce el colesterol.", "La fibra dietética acelera el tránsito intestinal, prolonga la sensación de saciedad y facilita la eliminación. Algunas fibras soluble reducen la absorción de grasas y azúcares.", null, null),
                    new NutrientEntity(null, "Azucares", "Carbohidratos simples que proporcionan energia.", "Los azúcares o hidratos de carbono simples como glucosa, fructosa y sacarosa aportan energía de rápida absorción. Su exceso se transforma en grasa y puede causar caries dental.", null, null),  
                    new NutrientEntity(null, "Calcio", "Fortalece los huesos y dientes.", "El calcio es el mineral más abundante en el cuerpo. Es esencial para formar y mantener la densidad ósea, coagulación, contracción muscular y neurotransmisión.", null, null),
                    new NutrientEntity(null, "Hierro", "Transporta oxigeno en la sangre.", "El hierro forma parte de la hemoglobina permitiendo el transporte de oxígeno en los glóbulos rojos. Su deficiencia produce anemia.", null, null),
                    new NutrientEntity(null, "Potasio", "Ayuda en la transmision nerviosa.", "El potasio es un electrolito necesario para la transmisión del impulso nervioso, contracción muscular y balance de fluidos. Se encuentra en frutas, verduras, carnes y lácteos.", null, null),  
                    new NutrientEntity(null, "Zinc", "Fortalece el sistema inmunologico.", "El zinc participa en el funcionamiento de más de 100 enzimas. Ayuda en la división celular, cicatrización, síntesis de proteínas y respuesta inmunológica.", null, null),
                    new NutrientEntity(null, "Vitamina D", "Vitamina esencial para la salud de los huesos.", "La vitamina D mejora la absorción de calcio y fósforo contribuyendo a la mineralización ósea. También posee acciones inmunomoduladoras.", null, null),
                    new NutrientEntity(null, "Vitamina B12", "Vitamina esencial para la formacion de globulos rojos.", "La vitamina B12 participa en la hematopoyesis, síntesis de ADN y metabolismo energético. Su deficiencia produce anemia megaloblástica.", null, null),
                    new NutrientEntity(null, "Omega 3", "acido graso esencial beneficioso para la salud cardiovascular.", "Los ácidos grasos omega 3 EPA y DHA previenen la inflamación y mejoran los niveles de triglicéridos, presión arterial y ritmo cardíaco.", null, null),
                    new NutrientEntity(null, "Selenio", "Mineral esencial con propiedades antioxidantes.", "El selenio forma parte de enzimas antioxidantes que protegen las membranas celulares del daño oxidativo. También mejora la respuesta inmune.", null, null),
                    new NutrientEntity(null, "Aminoacidos BCAA", "Aminoacidos de cadena ramificada que promueven la recuperacion muscular.", "Los aminoácidos BCAA (valina, leucina e isoleucina) estimulan la síntesis proteica, disminuyen el daño muscular post-ejercicio y retardan la fatiga.", null, null),
                    new NutrientEntity(null, "Colina", "Nutriente esencial importante para la funcion cerebral y la salud hepatica.", "La colina es precursora de la acetilcolina, un neurotransmisor. También participa en el metabolismo de grasas evitando la acumulación en el hígado.", null, null),
                    new NutrientEntity(null, "Alcohol", "Calorias derivadas del consumo de alcohol.", "El alcohol etílico aporta 7 kcal/gramo. Su consumo excesivo es tóxico para el hígado y el sistema nervioso central. Debe consumirse con moderación.", null, null),
                    new NutrientEntity(null, "Capsaicina", "Compuesto picante presente en los pimientos.", "La capsaicina estimula receptores de calor que incrementan el gasto energético. Parece tener propiedades anticancerígenas, analgésicas y antiinflamatorias.", null, null),
                    new NutrientEntity(null, "Fructosa", "Tipo de azucar natural presente en las frutas.", "La fructosa es un azúcar simple que se encuentra en frutas y miel. Es más dulce que la sacarosa pero tiene un índice glucémico bajo.", null, null),
                    new NutrientEntity(null, "Glucosa", "Azucar simple que es una fuente de energia inmediata para el cuerpo.", "La glucosa es el principal carbohidrato que circula en la sangre y provee energía a las células. Su elevación sostenida produce diabetes.", null, null),
                    new NutrientEntity(null, "Sacarosa", "Azucar de mesa comun.", "La sacarosa o azúcar común es un disacárido compuesto por glucosa y fructosa. Su consumo excesivo se asocia a caries dental, sobrepeso y diabetes.", null, null),
                    new NutrientEntity(null, "Folato", "Vitamina B importante para la sintesis del ADN y el crecimiento celular.", "El folato participa en la producción de ácidos nucleicos y aminoácidos. Su deficiencia en el embarazo causa defectos del tubo neural.", null, null),
                    new NutrientEntity(null, "Vitamina A", "Vitamina esencial para la vision y la salud de la piel.", "La vitamina A es esencial para la visión nocturna y la diferenciación celular en piel y mucosas. Posee acción antioxidante.", null, null),
                    new NutrientEntity(null, "Vainillina", "Compuesto quimico que proporciona el sabor y aroma caracteristicos de la vainilla.", "La vainillina es el principal componente aromático de la vainilla. Se utiliza como saborizante natural en alimentos y cosmética.", null, null),
                    new NutrientEntity(null, "Vitamina E", "Vitamina liposoluble con propiedades antioxidantes.", "La vitamina E protege membranas y lipoproteínas de la peroxidación lipídica. Mejora la respuesta inmune y previene la agregación plaquetaria.", null, null),
                    new NutrientEntity(null, "acido Graso Omega 6", "Tipo de acido graso esencial.", "El ácido linoleico es un omega 6 precursor de eicosanoides proinflamatorios. Debe equilibrarse con omega 3 mediante una dieta adecuada.", null, null),
                    new NutrientEntity(null, "Sodio", "Mineral esencial necesario para el equilibrio de liquidos y la funcion nerviosa.", "El sodio es el principal catión extracelular. Participa en el balance hídrico, contracción muscular y transmisión nerviosa. Su exceso eleva la presión arterial.", null, null),
                    new NutrientEntity(null, "Fosfolipidos", "Tipo de lipido esencial que forma parte de las membranas celulares.", "Los fosfolípidos forman la doble capa lipídica de las membranas celulares. Participan en la señalización celular y metabolismo.", null, null),
                    new NutrientEntity(null, "Resveratrol", "Polifenol con propiedades antioxidantes encontrado en ciertos alimentos como el vino tinto.", "El resveratrol neutraliza radicales libres y exhibe propiedades antiinflamatorias y anticancerígenas en estudios con animales y células.", null, null),
                    new NutrientEntity(null, "Antioxidantes", "Compuestos que protegen las celulas del danio oxidativo.", "Los antioxidantes como vitamina C, E, carotenoides y polifenoles previenen daño a biomoléculas ocasionado por especies reactivas de oxígeno.", null, null)
                );
                nutrientRepository.saveAll(nutrients);
            }
            if(categoryFoodRepository.count() == 0) {
                categories = Arrays.asList(
                    new CategoryFoodEntity(null, "Carnes y Aves", "Las carnes y aves son fuentes de proteínas de alto valor biológico. Incluyen res, cerdo, pollo, pavo, etc.", "Son fuentes de proteínas, vitaminas del complejo B (B12, B6, niacina), minerales como hierro, zinc y fósforo.", "El consumo excesivo se ha relacionado con mayor riesgo de enfermedades cardiovasculares. Deben consumirse con moderación.", null),
                    new CategoryFoodEntity(null, "Pescados y Mariscos", "Los pescados y mariscos son fuentes de proteínas, ácidos grasos omega 3 y yodo. Incluyen salmón, atún, trucha, camarones, etc.", "Son fuentes de proteínas, ácidos grasos omega 3 EPA y DHA, vitaminas A y D, yodo. Beneficios para la salud cardiovascular y cerebral.", "Algunas especies pueden contener niveles altos de mercurio y otros contaminantes. El consumo excesivo puede provocar intoxicación.", null),
                    new CategoryFoodEntity(null, "Vegetales", "Los vegetales son alimentos de origen vegetal ricos en vitaminas, minerales, fibra y antioxidantes. Incluyen verduras y hortalizas.", "Gran fuente de vitaminas, minerales y fibra dietética. Antioxidantes que previenen enfermedades. Bajo aporte calórico.", "Algunos vegetales pueden interferir con medicamentos como anticoagulantes. Consumo excesivo de vegetales crudos puede irritar el intestino.", null),
                    new CategoryFoodEntity(null, "Frutas", "Las frutas son alimentos ricos en vitaminas, minerales, antioxidantes y fibra. Incluyen frutas cítricas, frutas rojas, plátanos, manzanas, etc.", "Ricas en vitaminas C, A, potasio y fibra dietética. Contienen antioxidantes que previenen enfermedades.", "Alto contenido de azúcares naturales. Consumo excesivo puede provocar aumento de peso. Algunas frutas irritan el intestino.", null),
                    new CategoryFoodEntity(null, "Lacteos", "Los lácteos provienen de la leche de vaca u otros mamíferos. Incluyen leche, queso, yogurt, etc.", "Fuente de calcio, vitaminas A, D y B12. Ayudan a mantener huesos y dientes sanos.", "No recomendados para intolerantes a la lactosa. Alto aporte de grasas saturadas en algunos derivados.", null),
                    new CategoryFoodEntity(null, "Huevos", "Los huevos son alimentos ricos en proteínas, vitaminas y minerales. Pueden consumirse enteros o claras.", "Fuente de proteínas de alto valor biológico, vitaminas A, D, E, B12 y minerales.", "Contienen colesterol, por lo que su consumo debe ser moderado en personas con colesterol alto o enfermedades cardiovasculares.", null),
                    new CategoryFoodEntity(null, "Salsas y Endulzantes", "Las salsas y endulzantes se utilizan para sazonar o endulzar alimentos y bebidas.", "Permiten realzar el sabor de preparaciones de forma segura. Los endulzantes bajos en calorías son útiles para diabéticos.", "Algunas salsas y endulzantes pueden contener sodio, azúcares refinados u otros ingredientes no saludables si se consumen en exceso.", null),
                    new CategoryFoodEntity(null, "Especias", "Las especias son sustancias aromáticas de origen vegetal que se utilizan para sazonar alimentos.", "Aportan sabor sin calorías. Algunas tienen propiedades medicinales y antioxidantes.", "Deben consumirse en pequeñas cantidades. Algunas pueden interactuar con medicamentos o irritar el intestino.", null),
                    new CategoryFoodEntity(null, "Dulces", "Los dulces incluyen productos ricos en azúcares como caramelos, chocolates, helados, pasteles, etc.", " Fuente de placer y satisfacción sensorial en pequeñas porciones. Los derivados de cacao aportan antioxidantes.", "Alto contenido de azúcares y calorías. Su consumo excesivo puede producir caries dental, sobrepeso y diabetes.", null),
                    new CategoryFoodEntity(null, "Bebidas", "Las bebidas incluyen agua, jugos, gaseosas, té, café, bebidas energéticas, alcohol, etc.", "El agua es esencial para la vida. Algunas bebidas aportan vitaminas, minerales y antioxidantes.", "Las gaseosas, bebidas energéticas y alcohol aportan 'calorías vacías'. El alcohol en exceso es nocivo.", null),
                    new CategoryFoodEntity(null, "Granos", "Los granos o cereales incluyen trigo, arroz, maíz, avena, cebada, etc. Son la base de alimentos como pan, pasta y tortillas.", "Fuente de hidratos de carbono complejos, proteínas, vitaminas del grupo B, minerales y fibra.", "Deben consumirse integrales y no refinados. Su consumo excesivo puede aumentar el riesgo de enfermedades.", null),
                    new CategoryFoodEntity(null, "Comida rápida", "La comida rápida incluye hamburguesas, papas fritas, pizzas, etc. Se caracteriza por ser rápida de preparar y rica en grasas, sal y azúcares.", "Es una opción práctica y de rápido acceso para algunas ocasiones. Aporta sabor y placer sensorial.", "Muy alta en calorías, grasas saturadas, sodio y azúcares. Su consumo habitual está relacionado con sobrepeso, obesidad, diabetes y enfermedades cardiovasculares.", null)
                );
                categoryFoodRepository.saveAll(categories);
            }
            if(foodRepository.count() == 0) {
                foods = Arrays.asList(
                    new FoodEntity(null, "Pollo", "Carne magra rica en proteínas, baja en grasa. Fuente de vitaminas B6, B12, zinc, hierro.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/pechuga-de-pollo-sin-piel", null, getCategoryByName(categories, "Carnes y Aves"), null, null, null),
                    new FoodEntity(null, "Arroz", "Grano rico en almidón que constituye la base de la alimentación de gran parte del mundo. Fuente de hidratos de carbono complejos y fibra.", PrivacityEnum.PUBLIC, false, "https://bing.com/search?q=Arroz+blanco%2c+cocido+site%3afatsecret.es", null, getCategoryByName(categories, "Granos"), null, null, null),
                    new FoodEntity(null, "Brocoli", "Verdura crucífera rica en vitaminas C, K, folatos, potasio y fibra. Posee propiedades anticancerígenas.", PrivacityEnum.PUBLIC, false, "https://bing.com/search?q=Brocoli+cocido+site%3afatsecret.es", null, getCategoryByName(categories, "Vegetales"), null, null, null),  
                    new FoodEntity(null, "Platano", "Fruta tropical rica en hidratos de carbono, potasio, vitamina B6 y fibra dietética. Aporta rápida energía.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/pl%C3%A1tano-maduro", null, getCategoryByName(categories, "Frutas"), null, null, null),
                    new FoodEntity(null, "Lentejas", "Legumbre rica en proteínas, hidratos de carbono complejos, fibra, hierro, folatos y potasio.", PrivacityEnum.PUBLIC, false, "https://bing.com/search?q=Lentejas+cocidas+site%3afatsecret.es", null, getCategoryByName(categories, "Granos"), null, null, null),
                    new FoodEntity(null, "Salmon", "Pescado azul rico en proteínas, grasas omega 3 y vitaminas A, D, B6, B12. Beneficioso para el corazón y el cerebro.", PrivacityEnum.PUBLIC, false, "https://bing.com/search?q=Arroz+blanco%2c+cocido+site%3afatsecret.es", null, getCategoryByName(categories, "Pescados y Mariscos"), null, null, null),
                    new FoodEntity(null, "Leche", "Bebida láctea rica en proteínas, calcio, vitaminas A y B12. Fortalece huesos y dientes.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/leche-(leche-entera)", null, getCategoryByName(categories, "Lacteos"), null, null, null),
                    new FoodEntity(null, "Pescado", "Alimento rico en proteínas, grasas saludables y vitaminas del complejo B. Excelente para el cerebro y corazón.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/filete-de-pescado-al-horno-o-la-parrilla?portionid=50627&portionamount=100,000", null, getCategoryByName(categories, "Pescados y Mariscos"), null, null, null),
                    new FoodEntity(null, "Cebolla", "Bulbo vegetal con compuestos azufrados, vitamina C, flavonoides y fibra. Propiedades anticancerígenas.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/cebollas-rojas?portionid=340024&portionamount=1,000", null, getCategoryByName(categories, "Vegetales"), null, null, null),
                    new FoodEntity(null, "Limon", "Fruta cítrica rica en vitamina C y flavonoides. Ayuda a depurar el organismo y fortalece defensas.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/lim%C3%B3n", null, getCategoryByName(categories, "Frutas"), null, null, null),
                    new FoodEntity(null, "Aji", "Picante natural rico en capsaicina. Estimula el metabolismo y tiene propiedades anticancerígenas.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/aj%C3%AD-verde", null, getCategoryByName(categories, "Especias"), null, null, null),
                    new FoodEntity(null, "Carne", "Alimento rico en proteínas, hierro y vitaminas del grupo B. Debe consumirse con moderación por su alto contenido de grasa.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/filete-de-ternera?portionid=50263&portionamount=200,000", null, getCategoryByName(categories, "Carnes y Aves"), null, null, null),
                    new FoodEntity(null, "Papa", "Tubérculo rico en hidratos de carbono, vitaminas del grupo B, potasio y vitamina C. Aporta energía de fácil digestión.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/papa", null, getCategoryByName(categories, "Vegetales"), null, null, null),
                    new FoodEntity(null, "Tomate", "Fruto carnoso rico en licopeno, vitamina C y potasio. Posee propiedades antioxidantes y anticancerígenas.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/hacendado/tomate-frito/100g", null, getCategoryByName(categories, "Vegetales"), null, null, null),
                    new FoodEntity(null, "Ajo", "Bulbo conocido por sus propiedades medicinales, antibacterianas y antioxidantes. Rico en vitaminas B6 y C.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/ajo-picado", null, getCategoryByName(categories, "Especias"), null, null, null),
                    new FoodEntity(null, "Queso", "Derivado lácteo rico en proteínas, calcio, fósforo y vitaminas A y B12. Alto contenido de grasas saturadas.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/queso-cheddar", null, getCategoryByName(categories, "Lacteos"), null, null, null),
                    new FoodEntity(null, "Lechuga", "Verdura de hoja verde rica en vitaminas A, C, K y folatos. Muy baja en calorías.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/lechuga-iceberg-(incluye-los-arrepollos)", null, getCategoryByName(categories, "Vegetales"), null, null, null),
                    new FoodEntity(null, "Aceituna", "Fruto del olivo rico en grasas monoinsaturadas y compuestos bioactivos. Fuente de vitamina E.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/aceitunas-verdes", null, getCategoryByName(categories, "Frutas"), null, null, null),
                    new FoodEntity(null, "Huevo", "Alimento rico en proteínas de alto valor biológico, vitaminas y minerales. Contiene colesterol.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/huevo-cocido", null, getCategoryByName(categories, "Huevos"), null, null, null),
                    new FoodEntity(null, "Mayonesa", "Salsa emulsionada a base de huevo y aceite. Rico en grasas, debe consumirse con moderación.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/search?q=Mayonesa+Casera", null, getCategoryByName(categories, "Salsas y Endulzantes"), null, null, null),
                    new FoodEntity(null, "Palta", "Fruto tropical rico en grasas monoinsaturadas y vitaminas B, C, E y K. Beneficioso para el corazón.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/aguacates?portionid=32968&portionamount=0,500", null, getCategoryByName(categories, "Frutas"), null, null, null),
                    new FoodEntity(null, "Cecina", "Carne de res salada y deshidratada. Rico en proteínas. Alto contenido de sodio.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/search?q=Cecina+de+Pecho+de+Res+%28Salados%2c+Cocidos%29", null, getCategoryByName(categories, "Carnes y Aves"), null, null, null),
                    new FoodEntity(null, "Gallina", "Carne magra rica en proteínas, baja en grasa. Fuente de vitaminas B6, B12, zinc, hierro.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/pollo-asado", null, getCategoryByName(categories, "Carnes y Aves"), null, null, null),
                    new FoodEntity(null, "Vino", "Bebida alcohólica derivada de la uva. Debe consumirse con moderación por su contenido de alcohol.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/alimentos/vino-tinto", null, getCategoryByName(categories, "Bebidas"), null, null, null),
                    new FoodEntity(null, "Azucar", "Edulcorante refinado rico en calorías. Su consumo excesivo puede provocar sobrepeso y caries.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/azucarera/az%C3%BAcar-blanco/1-cucharadita", null, getCategoryByName(categories, "Salsas y Endulzantes"), null, null, null),
                    new FoodEntity(null, "Claras de huevo", "Parte del huevo rica en proteínas de alto valor biológico y baja en grasa. Muy versátil en preparaciones.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/clara-de-huevo", null, getCategoryByName(categories, "Huevos"), null, null, null),
                    new FoodEntity(null, "Esencia de vainilla", "Extracto natural que se usa para dar aroma y sabor dulce a preparaciones.", PrivacityEnum.PUBLIC, false, "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/extracto-de-vainilla", null, getCategoryByName(categories, "Especias"), null, null, null),
                    new FoodEntity(null, "Manjarblanco", "Preparación típica chilena a base de leche y azúcar. Rico en calorías por su alto contenido de azúcares.", PrivacityEnum.PUBLIC, false, "https://www.myfitnesspal.com/es/food/calories/manjar-blanco-1158827675", null, getCategoryByName(categories, "Dulces"), null, null, null)
                );
                foodRepository.saveAll(foods);
            }
            if(compositionFoodRepository.count() == 0) {
                Map<String, Map<String, Double>> nutrientCompositionFoodMap = new HashMap<String, Map<String, Double>>() {
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
                for (Map.Entry<String, Map<String, Double>> foodEntry : nutrientCompositionFoodMap.entrySet()) {
                    String foodName = foodEntry.getKey();
                    FoodEntity food = foodNameMap.get(foodName);
                    if (food != null) {
                        for (Map.Entry<String, Double> nutrientEntry : foodEntry.getValue().entrySet()) {
                            String nutrientName = nutrientEntry.getKey();
                            Double nutrientQuantity = nutrientEntry.getValue();
                            UnitOfMeasurementEnum uniteOfMeasuremente = UnitOfMeasurementEnum.G;
                            NutrientEntity nutrient = getNutrientByName(nutrients, nutrientName);
                            if (nutrient != null) {
                                compositionsFoods.add(new CompositionFoodEntity(new CompositionFoodKey(food.getId(), nutrient.getId()), food, nutrient, nutrientQuantity, uniteOfMeasuremente));
                            }
                        }
                    }
                }
                compositionFoodRepository.saveAll(compositionsFoods);
            }
            if(roleRepository.count() == 0) {
                roles = Arrays.asList(
                    new RoleEntity(null, RoleEnum.ADMIN),
                    new RoleEntity(null, RoleEnum.USER)
                );
                roleRepository.saveAll(roles);
            }
            if(userRepository.count() == 0) {
                // Roles disponibles
                RoleEntity USER = roleRepository.findByName(RoleEnum.USER);
                RoleEntity ADMIN = roleRepository.findByName(RoleEnum.ADMIN);
                // Creacion de usuarios
                users = Arrays.asList(
                    new UserEntity(null, "kiridepapel", "$2a$10$8YYL6YTawu99l5vFqGTKI.ul/1JoG9FJ9z2HkPRFv6znEb39jvTIe", "brian@gmail.com", "Brian", "Uceda", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", false, null, null, null, null, getRandomObjectives(objectives, 7), null, null, new HashSet<>(Arrays.asList(ADMIN, USER))),
                    new UserEntity(null, "heatherxvalencia", "$2a$10$Qk5oaC0/Hxlk.vV/CzlcC.plvHOqkiB0qX9ticZy2vexuYnCNigWC", "heather@gmail.com", "Heather", "Valencia", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", false, null, null, null, null, getRandomObjectives(objectives, 6), null, null, new HashSet<>(Arrays.asList(USER))),
                    new UserEntity(null, "whoami", "$2a$10$eAs212VvPpG6hkDvS.7nQeY8zrXPW2ZwF6EvJIO5cHigEgRbOSvqi", "willy@gmail.com", "Willy", "Samata", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", false, null, null, null, null, getRandomObjectives(objectives, 2), null, null, new HashSet<>(Arrays.asList(USER))),
                    new UserEntity(null, "nayde", "$2a$10$rPmZPIWQxtAXvYqKi75PnuNx6E.oZ6dhSVb5XvP9W7AAbrhDgC8D6", "nayde@gmail.com", "Naydeline", "Duran", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", false, null, null, null, null, getRandomObjectives(objectives, 5), null, null, new HashSet<>(Arrays.asList(USER))),
                    new UserEntity(null, "gabi", "$2a$10$NcPc5la3asRhlY8R6bPwJufxND8mK4H9yPphe54VbhXVdNBfOT7NO", "gabriela@gmail.com", "Gabriela", "Reyes", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", false, null, null, null, null, getRandomObjectives(objectives, 5), null, null, new HashSet<>(Arrays.asList(USER))),
                    new UserEntity(null, "impresora", "$2a$10$WfNOvx0qLnDec/qA1sYGaOvsiII4uKycCMJTHdtiPjdRH8ehw1XIG", "edson@gmail.com", "Edson", "Linares", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", false, null, null, null, null, getRandomObjectives(objectives, 2), null, null, new HashSet<>(Arrays.asList(USER)))
                );
                userRepository.saveAll(users);
            }
            if(recipeRepository.count() == 0) {
                recipes = Arrays.asList(
                    new RecipeEntity(null, "Arroz con pollo", "Plato sabroso que combina el arroz con trozos de pollo y verduras.", "Cocinar el arroz y rehogar el pollo con las verduras. Mezclar y sazonar al gusto.", "Rico en carbohidratos y proteínas. Nutritivo y energético.", "Alto contenido calórico si se excede en porciones.", 4.6, PrivacityEnum.PUBLIC, false, null, null, null, null),
                    new RecipeEntity(null, "Ceviche", "Plato típico peruano a base de pescado marinado en jugo de limón. Se sirve con cebolla, ají y choclo.", "Marinar el pescado cortado en cubos con jugo de limón. Agregar cebolla, ají y choclo. Sazonar al gusto.", "Rico en proteínas. El limón ayuda a la digestión. Bajo en calorías.", "No recomendado en embarazadas por riesgo de parasitosis. El exceso de limón puede irritar el estómago.", 5.0, PrivacityEnum.PUBLIC, false, null, null, null, null),
                    new RecipeEntity(null, "Lomo saltado", "Plato sabroso de la cocina peruana hecho con tiras de lomo salteadas con tomate, cebolla y ají.", "Saltear la carne con aceite y especias. Agregar cebolla, tomate y ají. Servir con papas fritas.", "Carne magra y vegetales frescos. Rico en proteínas y vitaminas.", "Alto contenido de sodio por el uso de soya y ajinomoto. Las papas fritas aportan grasas.", 5.0, PrivacityEnum.PUBLIC, false, null, null, null, null),
                    new RecipeEntity(null, "Tacacho con cecina", "Plato típico de la selva hecho con plátanos verdes, cecina y salsa criolla.", "Cocinar y machacar los plátanos. Freír y hacer tortillas. Acompañar con cecina y salsa criolla.", "Combinación de carbohidratos y proteínas. La cecina aporta hierro.", "Alto contenido calórico. La cecina tiene mucho sodio. Los plátanos verdes pueden ser indigestos.", 4.2, PrivacityEnum.PUBLIC, false, null, null, null, null),
                    new RecipeEntity(null, "Papa a la huancaina", "Entrante cremoso de papas sancochadas bañadas en salsa de ají amarillo y queso fresco.", "Cocer las papas con cáscara. Pelar y cortar en rodajas. Bañar con salsa de ají amarillo y queso. Decorar con huevo duro y aceituna.", "Combinación nutritiva de papa, queso, huevo y ají. Rico en vitaminas.", "Alto contenido calórico por las cremas y aceites. El exceso de ají puede irritar el estómago.", 4.1, PrivacityEnum.PUBLIC, false, null, null, null, null),
                    new RecipeEntity(null, "Causa limenia", "Plato frío hecho con una mezcla de mashed potato y pollo desmenuzado.", "Cocinar y triturar las papas con aceite. Agregar pollo desmenuzado y limón. Decorar con mayonesa, aceituna y huevo.", "Aporte balanceado de carbohidratos y proteínas. Sabor agradable.", "Alto contenido calórico por las cremas y aceites. La mayonesa tiene colesterol.", 3.6, PrivacityEnum.PUBLIC, false, null, null, null, null),
                    new RecipeEntity(null, "Aji de gallina", "Plato representativo a base de trozos de pollo en una salsa cremosa de ají amarillo.", "Cocer y deshilachar el pollo. Cocinar la salsa con ají amarillo y especias. Mezclar con el pollo y crema. Servir con arroz.", "Salsa cremosa y sabrosa. Buena fuente de proteínas.", "Alto contenido calórico por la crema de leche. Picante para algunos paladares.", 4.7, PrivacityEnum.PUBLIC, false, null, null, null, null),
                    new RecipeEntity(null, "Suspiro limenio", "Postre criollo hecho con manjarblanco y merengue de claras de huevo.", "Batir las claras a punto de nieve con azúcar. Integrar con el manjarblanco. Decorar con ralladura de limón.", "Postre ligero con contrasts de sabores. Bajo en calorías.", "Alto contenido de azúcar. No recomendado para diabéticos ni intolerantes a la lactosa.", 2.2, PrivacityEnum.PUBLIC, false, null, null, null, null)
                );
                recipeRepository.saveAll(recipes);
            }
            if(compositionRecipeRepository.count() == 0) {
                Map<String, Map<String, Double>> nutrientCompositionRecipeMap = new HashMap<String, Map<String, Double>>() {
                    {
                        put("Arroz con pollo", Map.of("Carbohidratos", 45.0, "Proteina", 25.0, "Grasa", 12.0, "Vitamina B6", 0.5));
                        put("Ceviche", Map.of("Proteina", 30.0, "Vitamina C", 40.0, "Grasa", 5.0, "Omega 3", 2.0));
                        put("Lomo saltado", Map.of("Proteina", 28.0, "Carbohidratos", 30.0, "Grasa", 15.0, "Vitamina B12", 2.5));
                        put("Tacacho con cecina", Map.of("Carbohidratos", 50.0, "Proteina", 20.0, "Grasa", 10.0, "Hierro", 3.5));
                        put("Papa a la huancaina", Map.of("Carbohidratos", 40.0, "Proteina", 8.0, "Grasa", 12.0, "Vitamina C", 20.0));
                        put("Causa limenia", Map.of("Carbohidratos", 35.0, "Proteina", 15.0, "Grasa", 10.0, "Vitamina B6", 0.6));
                        put("Aji de gallina", Map.of("Proteina", 22.0, "Carbohidratos", 25.0, "Grasa", 15.0, "Vitamina A", 150.0));
                        put("Suspiro limenio", Map.of("Carbohidratos", 60.0, "Grasa", 10.0, "Proteina", 5.0, "Calcio", 50.0));
                    }
                };
                // Crear un mapa para buscar RecipeEntity por nombre
                Map<String, RecipeEntity> RecipeNameMap = generateNameMap(recipes, RecipeEntity::getName);
                // Recorrer la estructura de datos y realizar inserciones
                for (Map.Entry<String, Map<String, Double>> recipeEntry : nutrientCompositionRecipeMap.entrySet()) {
                    String recipeName = recipeEntry.getKey();
                    RecipeEntity recipe = RecipeNameMap.get(recipeName);
                    if (recipe != null) {
                        for (Map.Entry<String, Double> nutrientEntry : recipeEntry.getValue().entrySet()) {
                            String nutrientName = nutrientEntry.getKey();
                            Double nutrientQuantity = nutrientEntry.getValue();
                            UnitOfMeasurementEnum uniteOfMeasuremente = UnitOfMeasurementEnum.G;
                            NutrientEntity nutrient = getNutrientByName(nutrients, nutrientName);
                            if (nutrient != null) {
                                compositionsRecipes.add(new CompositionRecipeEntity(new CompositionRecipeKey(recipe.getId(), nutrient.getId()), recipe, nutrient, nutrientQuantity, uniteOfMeasuremente));
                            }
                        }
                    }
                }
                compositionRecipeRepository.saveAll(compositionsRecipes);
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
                    // Fecha de nacimiento aleatoria entre 1980 y 2010
                    Timestamp birthdate = Timestamp.valueOf(LocalDate.of(1980 + rand.nextInt(30), 1 + rand.nextInt(11), 1 + rand.nextInt(28)).atStartOfDay());
                    // Altura aleatoria entre 150 y 200 cm
                    double height = 150.0 + rand.nextInt(50);
                    // Peso aleatorio entre 50 y 100 kg
                    double weight = 50.0 + rand.nextInt(5001) / 100.0;
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
                    fitnessInfo.setTargetWeightKg((double) Math.round(currentWeight * (1 + (0.20 + (0.30 * rand.nextDouble())))));
                    // Fecha objetivo aleatoria entre 1 y 6 meses a partir de hoy
                    fitnessInfo.setTargetDate(Timestamp.valueOf(LocalDate.now().plusMonths(1 + rand.nextInt(6)).withDayOfMonth(1 + rand.nextInt(LocalDate.now().plusMonths(1 + rand.nextInt(6)).lengthOfMonth())).atStartOfDay()));
                    // IMC aleatorio entre 18 y 30
                    fitnessInfo.setImc(Math.round((18 + rand.nextDouble() * 12) * 100.0) / 100.0);
                    // Ingesta calorica aleatoria entre 1500 y 2500
                    fitnessInfo.setDailyCaloricIntake((double) Math.round(1500 + rand.nextDouble() * 1000));
                    // Ingesta de proteínas diarias aleatoria entre 50g y 150g
                    fitnessInfo.setDailyProteinIntake((double) Math.round(50.0 + rand.nextDouble() * 100));
                    // Ingesta de carbohidratos diarios aleatoria entre 150g y 400g
                    fitnessInfo.setDailyCarbohydrateIntake((double) Math.round(150.0 + rand.nextDouble() * 250));
                    // Ingesta de grasas diarias aleatoria entre 20g y 70g
                    fitnessInfo.setDailyFatIntake((double) Math.round(20.0 + rand.nextDouble() * 50));
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
                    // Generacion aleatoria de true y false
                    config.setNotificationsEnabled(rand.nextBoolean());
                    config.setDarkMode(rand.nextBoolean());
                    // Generacion de LocalDateTime aleatorio entre hace 4 días (4) y hoy (0) para lastFoodEntry
                    config.setLastFoodEntry(Timestamp.valueOf(LocalDateTime.now().plusDays(-rand.nextInt(4))));
                    // Generacion de LocalDateTime aleatorio entre hace 3 semanas (-20) y hace 3 días (-3) para lastWeightUpdate
                    config.setLastWeightUpdate(Timestamp.valueOf(LocalDateTime.now().plusDays(-(3 + rand.nextInt(18)))));
                    // Asignacion de usuario
                    config.setUser(user);
                    usersConfig.add(config);
                }
                userConfigRepository.saveAll(usersConfig);
            }
            if(ipLoginAttemptRepository.count() == 0) {
                for (UserEntity user : users) {
                    Random rand = new Random();
                    // Generamos entre 1 y 3 intentos de inicio de sesion por usuario
                    int loginAttempts = 1 + rand.nextInt(3);
                    for (int i = 0; i < loginAttempts; i++) {
                        IpLoginAttemptEntity ipLoginAttempt = new IpLoginAttemptEntity();
                        // Genera una direccion IPv4 aleatoria
                        ipLoginAttempt.setIpAddress(rand.nextInt(256) + "." + rand.nextInt(256) + "." + rand.nextInt(256) + "." + rand.nextInt(256));
                        // Establece si la cuenta está bloqueada aleatoriamente
                        ipLoginAttempt.setIsAccountBlocked(false);
                        // Establece la fecha en que se bloqueo
                        ipLoginAttempt.setBlockedDate(null);
                        // Fecha de último intento en los últimos 3 días
                        ipLoginAttempt.setLastAttemptDate(Timestamp.valueOf(LocalDateTime.now().minusDays(rand.nextInt(3))));
                        // Asignacion de usuario
                        ipLoginAttempt.setUser(user);
                        ipsLoginsAttempts.add(ipLoginAttempt);
                    }
                }
                ipLoginAttemptRepository.saveAll(ipsLoginsAttempts);
            }
            if(eatRepository.count() == 0) {
                for(UserEntity user : users) {
                    for(int i = 0; i < new Random().nextInt(11) + 4; i++) {
                        EatEntity eat = new EatEntity();
                        eat.setDate(new Timestamp(new Random().nextLong(System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000, System.currentTimeMillis() - 3 * 60 * 60 * 1000)));
                        eat.setUser(user);
                        if(new Random().nextBoolean()) {
                            eat.setFood(getRandomObject(foods));
                            eat.setRecipe(null);
                            eat.setUnitOfMeasurement(getRandomUnitOfMeasurement());
                            if (eat.getUnitOfMeasurement() == UnitOfMeasurementEnum.UN) {
                                // Genera un entero aleatorio entre 1 y 3
                                eat.setEatQuantity(1.0 + (double) new Random().nextInt(3));
                            } else {
                                // Genera un decimal aleatorio entre 1 y 15
                                eat.setEatQuantity(Math.round((1 + new Random().nextDouble() * 14) * 10.0) / 10.0);
                            }
                        } else {
                            eat.setRecipe(getRandomObject(recipes));
                            eat.setFood(null);
                            eat.setEatQuantity(1.0 + new Random().nextInt(3));
                            eat.setUnitOfMeasurement(UnitOfMeasurementEnum.UN);
                        }
                        eats.add(eat);
                    }
                }
                eatRepository.saveAll(eats);
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
    // Composition
    public NutrientEntity getNutrientByName(List<NutrientEntity> nutrients, String name) {
        return nutrients.stream().filter(nutrient -> nutrient.getName().equals(name)).findFirst().orElse(null);
    }
    // Food
    public FoodEntity getFoodByName(List<FoodEntity> foods, String name) {
        return foods.stream().filter(food -> food.getName().equals(name)).findFirst().orElse(null);
    }
    // Recipe
    public RecipeEntity getRecipeByName(List<RecipeEntity> recipes, String name) {
        return recipes.stream().filter(recipe -> recipe.getName().equals(name)).findFirst().orElse(null);
    }
    // Create map of specific type
    public <T> Map<String, T> generateNameMap(List<T> entities, Function<T, String> nameFunction) {
        return entities.stream().collect(Collectors.toMap(nameFunction, Function.identity()));
    }
    // Obtiene un elemento aleatorio de una lista
    public <T> T getRandomObject(List<T> list) {
        return list.get(new Random().nextInt(list.size())); 
    }
    // Obtener unidad de medida aleatoria
    public UnitOfMeasurementEnum getRandomUnitOfMeasurement() {
        UnitOfMeasurementEnum[] values = UnitOfMeasurementEnum.values();
        return values[new Random().nextInt(values.length)]; 
    }
}