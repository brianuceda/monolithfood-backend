package pe.edu.upc.MonolithFoodApplication;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pe.edu.upc.MonolithFoodApplication.entities.ActivityLevelEntity;
import pe.edu.upc.MonolithFoodApplication.entities.CategoryFoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.CompositionFoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.CompositionFoodKey;
import pe.edu.upc.MonolithFoodApplication.entities.CompositionRecipeEntity;
import pe.edu.upc.MonolithFoodApplication.entities.CompositionRecipeKey;
import pe.edu.upc.MonolithFoodApplication.entities.EatEntity;
import pe.edu.upc.MonolithFoodApplication.entities.FoodEntity;
import pe.edu.upc.MonolithFoodApplication.entities.FoodImagesEntity;
import pe.edu.upc.MonolithFoodApplication.entities.IngredientEntity;
import pe.edu.upc.MonolithFoodApplication.entities.IngredientKey;
import pe.edu.upc.MonolithFoodApplication.entities.NutrientEntity;
import pe.edu.upc.MonolithFoodApplication.entities.ObjectiveEntity;
import pe.edu.upc.MonolithFoodApplication.entities.RecipeEntity;
import pe.edu.upc.MonolithFoodApplication.entities.RoleEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserConfigEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserFitnessInfoEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.entities.WalletEntity;
import pe.edu.upc.MonolithFoodApplication.enums.CategoryIntakeEnum;
import pe.edu.upc.MonolithFoodApplication.enums.GenderEnum;
import pe.edu.upc.MonolithFoodApplication.enums.RoleEnum;
import pe.edu.upc.MonolithFoodApplication.enums.UnitOfMeasurementEnum;
import pe.edu.upc.MonolithFoodApplication.repositories.ActivityLevelRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.CategoryRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.CompositionFoodRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.CompositionRecipeRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.EatRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.FoodImagesRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.FoodRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.IngredientRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.NutrientRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.ObjectiveRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.RecipeRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.RoleRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserConfigRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserFitnessInfoRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserPersonalInfoRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.WalletRepository;

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
        FoodImagesRepository foodImagesRepository,
        CompositionFoodRepository compositionFoodRepository,
        RoleRepository roleRepository,
        UserRepository userRepository,
        RecipeRepository recipeRepository,
        CompositionRecipeRepository compositionRecipeRepository,
        IngredientRepository ingredientRepository,
        UserPersonalInfoRepository userPersonalInfoRepository,
        UserFitnessInfoRepository userFitnessInfoRepository,
        UserConfigRepository userConfigRepository,
        WalletRepository walletRepository,
        EatRepository eatRepository
    ){
        return args -> {
            List<ActivityLevelEntity> activityLevels = new ArrayList<>();
            List<ObjectiveEntity> objectives = new ArrayList<>();
            List<CategoryFoodEntity> categories = new ArrayList<>();
            List<NutrientEntity> nutrients = new ArrayList<>();
            List<FoodEntity> foods = new ArrayList<>();
            List<FoodImagesEntity> foodsImages = new ArrayList<>();
            List<CompositionFoodEntity> compositionsFoods = new ArrayList<>();
            List<RoleEntity> roles = new ArrayList<>();
            List<UserEntity> users = new ArrayList<>();
            List<RecipeEntity> recipes = new ArrayList<>();
            List<CompositionRecipeEntity> compositionsRecipes = new ArrayList<>();
            List<IngredientEntity> ingredients = new ArrayList<>();
            List<UserPersonalInfoEntity> usersPersonalInfos = new ArrayList<>();
            List<UserFitnessInfoEntity> usersFitnessInfos = new ArrayList<>();
            List<UserConfigEntity> usersConfig = new ArrayList<>();
            List<WalletEntity> wallets = new ArrayList<>(); 
            List<EatEntity> eats = new ArrayList<>();

            if(activityLevelRepository.count() == 0) {
                activityLevels = Arrays.asList(
                    new ActivityLevelEntity(null, "Poco o ningun ejercicio", "https://i.ibb.co/Vg4QDzj/Poco-o-ning-n-ejercicio.webp", "Ningun dia", "Personas sedentarias que realizan poco o ningun tipo de actividad.", 1.2, null),
                    new ActivityLevelEntity(null, "Ejercicio ligero", "https://i.ibb.co/jVsHqKc/Ejercicio-ligero.webp", "1 - 3 dias", "Actividad ligera como caminatas o tareas diarias no exigentes.", 1.375, null),
                    new ActivityLevelEntity(null, "Ejercicio moderado", "https://i.ibb.co/rf51JHG/Ejercicio-moderado.webp", "3 - 5 dias", "Actividad moderada que puede o no incluir ejercicios.", 1.55, null),
                    new ActivityLevelEntity(null, "Ejercicio intenso", "https://i.ibb.co/BBk0jm0/Ejercicio-intenso.webp", "5 - 7 dias", "Ejercicio intenso o trabajos fisicamente demandantes.", 1.725, null),
                    new ActivityLevelEntity(null, "Ejercicio muy intenso", "https://i.ibb.co/PtJDTfj/Ejercicio-muy-intenso.webp", "Atleta profesional", "Atletas o individuos con trabajos extremadamente fisicos.", 1.9, null)
                    // // Mas niveles de actividad fisica
                    // new ActivityLevelEntity(null, "Caminata ocasional", "", "1-2 dias", "Caminatas cortas y ocasionales", 1.4, null),
                    // new ActivityLevelEntity(null, "Caminata diaria", "", "5-6 dias", "Caminatas diarias de al menos 30 minutos", 1.6, null),
                    // new ActivityLevelEntity(null, "Deporte recreativo", "", "2-3 dias", "Futbol, basquetbol u otro deporte recreativo durante la semana", 1.65, null),
                    // new ActivityLevelEntity(null, "Entrenamiento de fuerza", "", "3-4 dias", "Entrenamiento de pesas o calistenia varios dias a la semana", 1.8, null)
                );
                activityLevelRepository.saveAll(activityLevels);
            }
            if(objectiveRepository.count() == 0) {
                objectives = Arrays.asList(
                    new ObjectiveEntity(null, "Bajar de peso", "https://i.ibb.co/SJzd0Hb/Bajar-de-peso.webp", "Objetivo centrado en reducir el peso corporal a traves de una dieta equilibrada.", 1.6),
                    new ObjectiveEntity(null, "Aumentar masa muscular", "https://i.ibb.co/LrvqGq4/Aumentar-masa-muscular.webp", "Objetivo centrado en ganar masa muscular a traves de una dieta rica en proteinas.", 2.2),
                    new ObjectiveEntity(null, "Mantener el peso", "https://i.ibb.co/bH5b2JM/Mantener-el-peso.webp", "Objetivo centrado en mantener el peso corporal actual.", 1.4),
                    new ObjectiveEntity(null, "Aumentar resistencia", "https://i.ibb.co/zGj1xZv/Aumentar-resistencia.webp", "Objetivo centrado en aumentar la resistencia fisica a traves de una dieta equilibrada.", 1.8),
                    new ObjectiveEntity(null, "Mejorar salud cardiaca", "https://i.ibb.co/W0ZZgcN/Mejorar-salud-cardiaca.webp", "Objetivo centrado en fortalecer el corazon y sistema circulatorio.",1.4),
                    new ObjectiveEntity(null, "Mejorar salud osea", "https://i.ibb.co/pnmFHKJ/Mejorar-salud-osea.webp", "Objetivo centrado en fortalecer los huesos y prevenir enfermedades oseas.", 1.4),
                    new ObjectiveEntity(null, "Reducir grasa corporal", "https://i.ibb.co/99CQNQN/Reducir-grasa-corporal.webp", "Objetivo centrado en reducir el porcentaje de grasa corporal.", 1.8),
                    new ObjectiveEntity(null, "Mejorar digestion", "https://i.ibb.co/C0Q2nFf/Mejorar-digestion.webp", "Objetivo centrado en promover una digestion saludable a traves de una dieta rica en fibra.", 1.2)
                    // // Mas objetivos secundarios
                    // new ObjectiveEntity(null, "Mejorar sistema inmune", "", "Objetivo para fortalecer el sistema inmunologico.", 1.6),  
                    // new ObjectiveEntity(null, "Controlar el colesterol", "", "Objetivo para reducir los niveles de colesterol.", 1.4),
                    // new ObjectiveEntity(null, "Controlar la glucosa", "", "Objetivo para mantener niveles saludables de azucar en la sangre.", 1.6),
                    // new ObjectiveEntity(null, "Mejorar piel y cabello", "", "Objetivo para mejorar la apariencia de piel y cabello.", 1.6)
                );
                objectiveRepository.saveAll(objectives);
            }
            if(nutrientRepository.count() == 0) {
                nutrients = Arrays.asList(
                    new NutrientEntity(null, "Calorias", "Unidad de medida de energia que se obtiene de los alimentos.", "Las calorías provienen principalmente de los macronutrientes: proteínas, carbohidratos y grasas. El valor calórico indica la cantidad de energía almacenada en los alimentos.", "#fd5a67", null, null),
                    new NutrientEntity(null, "Proteina", "Macronutriente esencial para la construccion de masa muscular.", "Las proteínas están formadas por aminoácidos. Son importantes para la síntesis y reparación muscular, además de funciones enzimáticas, hormonales e inmunológicas.", "#4d9f0c", null, null),
                    new NutrientEntity(null, "Carbohidratos", "Principal fuente de energia.", "Los carbohidratos o glucidos proporcionan glucosa que es la principal fuente de energía para las células. Pueden ser simples (azúcares) o complejos (almidones).", "#32a7f5", null, null),
                    new NutrientEntity(null, "Grasa", "Macronutriente esencial para la energia.", "Los lípidos o grasas son fuente concentrada de energía. También ayudan en la absorción de vitaminas liposolubles y forman parte de las membranas celulares.", "#f5a623", null, null),
                    new NutrientEntity(null, "Colesterol", "Esencial para la salud celular.", "El colesterol es un lípido necesario para construir membranas celulares y producir hormonas. Existen dos tipos principales: LDL (malo) y HDL (bueno), y mantener un equilibrio es crucial para la salud cardiovascular.", "#005377", null, null),
                    new NutrientEntity(null, "Vitamina B6", "Vitamina esencial para el metabolismo.", "La vitamina B6 participa en el metabolismo de proteínas, glucidos y grasas. Contribuye a la síntesis de neurotransmisores y hemoglobina.", "#ff8928", null, null),
                    new NutrientEntity(null, "Fosforo", "Mineral esencial para la salud osea.", "El fósforo forma parte de huesos y dientes, también participa en el metabolismo energético, la contracción muscular y el equilibrio ácido-base.", "#8d93ab", null, null),
                    new NutrientEntity(null, "Magnesio", "Mineral esencial para diversas funciones biologicas.", "El magnesio participa en más de 300 reacciones enzimáticas del metabolismo energético, síntesis proteica, contracción muscular y neurotransmisión. Ayuda a regular la glucosa y la presión arterial.", "#3fada8", null, null),
                    new NutrientEntity(null, "Vitamina C", "Antioxidante y esencial para la salud inmunologica.", "La vitamina C es un potente antioxidante que protege contra el daño oxidativo. También participa en la formación de colágeno, absorción de hierro y funcionamiento del sistema inmunológico.", "#ff6f61", null, null),
                    new NutrientEntity(null, "Vitamina K", "Vitamina esencial para la coagulacion de la sangre.", "La vitamina K es necesaria para la síntesis de protrombina, un factor de coagulación sanguínea. También parece estar implicada en la salud ósea y prevención del calcio en arterias.", "#4b5320", null, null),
                    new NutrientEntity(null, "Fibra", "Mejora la digestion y reduce el colesterol.", "La fibra dietética acelera el tránsito intestinal, prolonga la sensación de saciedad y facilita la eliminación. Algunas fibras soluble reducen la absorción de grasas y azúcares.", "#8b4513", null, null),
                    new NutrientEntity(null, "Azucares", "Carbohidratos simples que proporcionan energia.", "Los azúcares o hidratos de carbono simples como glucosa, fructosa y sacarosa aportan energía de rápida absorción. Su exceso se transforma en grasa y puede causar caries dental.", "#556b2f", null, null),  
                    new NutrientEntity(null, "Calcio", "Fortalece los huesos y dientes.", "El calcio es el mineral más abundante en el cuerpo. Es esencial para formar y mantener la densidad ósea, coagulación, contracción muscular y neurotransmisión.", "#cad2c5", null, null),
                    new NutrientEntity(null, "Hierro", "Transporta oxigeno en la sangre.", "El hierro forma parte de la hemoglobina permitiendo el transporte de oxígeno en los glóbulos rojos. Su deficiencia produce anemia.", "#7b4f4b", null, null),
                    new NutrientEntity(null, "Potasio", "Ayuda en la transmision nerviosa.", "El potasio es un electrolito necesario para la transmisión del impulso nervioso, contracción muscular y balance de fluidos. Se encuentra en frutas, verduras, carnes y lácteos.", "#e55934", null, null),  
                    new NutrientEntity(null, "Zinc", "Fortalece el sistema inmunologico.", "El zinc participa en el funcionamiento de más de 100 enzimas. Ayuda en la división celular, cicatrización, síntesis de proteínas y respuesta inmunológica.", "#5f7367", null, null),
                    new NutrientEntity(null, "Vitamina D", "Vitamina esencial para la salud de los huesos.", "La vitamina D mejora la absorción de calcio y fósforo contribuyendo a la mineralización ósea. También posee acciones inmunomoduladoras.", "#f4c430", null, null),
                    new NutrientEntity(null, "Vitamina B12", "Vitamina esencial para la formacion de globulos rojos.", "La vitamina B12 participa en la hematopoyesis, síntesis de ADN y metabolismo energético. Su deficiencia produce anemia megaloblástica.", "#373a6d", null, null),
                    new NutrientEntity(null, "Omega 3", "acido graso esencial beneficioso para la salud cardiovascular.", "Los ácidos grasos omega 3 EPA y DHA previenen la inflamación y mejoran los niveles de triglicéridos, presión arterial y ritmo cardíaco.", "#3e7b91", null, null),
                    new NutrientEntity(null, "Selenio", "Mineral esencial con propiedades antioxidantes.", "El selenio forma parte de enzimas antioxidantes que protegen las membranas celulares del daño oxidativo. También mejora la respuesta inmune.", "#8a9a5b", null, null),
                    new NutrientEntity(null, "Aminoacidos BCAA", "Aminoacidos de cadena ramificada que promueven la recuperacion muscular.", "Los aminoácidos BCAA (valina, leucina e isoleucina) estimulan la síntesis proteica, disminuyen el daño muscular post-ejercicio y retardan la fatiga.", "#8e7cc3", null, null),
                    new NutrientEntity(null, "Colina", "Nutriente esencial importante para la funcion cerebral y la salud hepatica.", "La colina es precursora de la acetilcolina, un neurotransmisor. También participa en el metabolismo de grasas evitando la acumulación en el hígado.", "#d5c4a1", null, null),
                    new NutrientEntity(null, "Alcohol", "Calorias derivadas del consumo de alcohol.", "El alcohol etílico aporta 7 kcal/gramo. Su consumo excesivo es tóxico para el hígado y el sistema nervioso central. Debe consumirse con moderación.", "#993333", null, null),
                    new NutrientEntity(null, "Capsaicina", "Compuesto picante presente en los pimientos.", "La capsaicina estimula receptores de calor que incrementan el gasto energético. Parece tener propiedades anticancerígenas, analgésicas y antiinflamatorias.", "#c41e3a", null, null),
                    new NutrientEntity(null, "Fructosa", "Tipo de azucar natural presente en las frutas.", "La fructosa es un azúcar simple que se encuentra en frutas y miel. Es más dulce que la sacarosa pero tiene un índice glucémico bajo.", "#ffdb58", null, null),
                    new NutrientEntity(null, "Glucosa", "Azucar simple que es una fuente de energia inmediata para el cuerpo.", "La glucosa es el principal carbohidrato que circula en la sangre y provee energía a las células. Su elevación sostenida produce diabetes.", "#f7e1a0", null, null),
                    new NutrientEntity(null, "Sacarosa", "Azucar de mesa comun.", "La sacarosa o azúcar común es un disacárido compuesto por glucosa y fructosa. Su consumo excesivo se asocia a caries dental, sobrepeso y diabetes.", "#f0e68c", null, null),
                    new NutrientEntity(null, "Folato", "Vitamina B importante para la sintesis del ADN y el crecimiento celular.", "El folato participa en la producción de ácidos nucleicos y aminoácidos. Su deficiencia en el embarazo causa defectos del tubo neural.", "#98ff98", null, null),
                    new NutrientEntity(null, "Vitamina A", "Vitamina esencial para la vision y la salud de la piel.", "La vitamina A es esencial para la visión nocturna y la diferenciación celular en piel y mucosas. Posee acción antioxidante.", "#ed9121", null, null),
                    new NutrientEntity(null, "Vainillina", "Compuesto quimico que proporciona el sabor y aroma caracteristicos de la vainilla.", "La vainillina es el principal componente aromático de la vainilla. Se utiliza como saborizante natural en alimentos y cosmética.", "#d2b48c", null, null),
                    new NutrientEntity(null, "Vitamina E", "Vitamina liposoluble con propiedades antioxidantes.", "La vitamina E protege membranas y lipoproteínas de la peroxidación lipídica. Mejora la respuesta inmune y previene la agregación plaquetaria.", "#ffcc99", null, null),
                    new NutrientEntity(null, "Omega 6", "Tipo de acido graso esencial.", "El ácido linoleico es un omega 6 precursor de eicosanoides proinflamatorios. Debe equilibrarse con omega 3 mediante una dieta adecuada.", "#db7093", null, null),
                    new NutrientEntity(null, "Sodio", "Mineral esencial necesario para el equilibrio de liquidos y la funcion nerviosa.", "El sodio es el principal catión extracelular. Participa en el balance hídrico, contracción muscular y transmisión nerviosa. Su exceso eleva la presión arterial.", "#76d7ea", null, null),
                    new NutrientEntity(null, "Fosfolipidos", "Tipo de lipido esencial que forma parte de las membranas celulares.", "Los fosfolípidos forman la doble capa lipídica de las membranas celulares. Participan en la señalización celular y metabolismo.", "#fddde6", null, null),
                    new NutrientEntity(null, "Resveratrol", "Polifenol con propiedades antioxidantes encontrado en ciertos alimentos como el vino tinto.", "El resveratrol neutraliza radicales libres y exhibe propiedades antiinflamatorias y anticancerígenas en estudios con animales y células.", "#701f28", null, null),
                    new NutrientEntity(null, "Antioxidantes", "Compuestos que protegen las celulas del danio oxidativo.", "Los antioxidantes como vitamina C, E, carotenoides y polifenoles previenen daño a biomoléculas ocasionado por especies reactivas de oxígeno.", "#8a2be2", null, null)
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
            if(foodImagesRepository.count() == 0) {
                foodsImages = Arrays.asList(
                    new FoodImagesEntity(null, "https://i.ibb.co/3RPB4Xq/no-image-1.png", null),
                    new FoodImagesEntity(null, "https://i.ibb.co/72NgDtP/no-image-2.png", null)
                );
                foodImagesRepository.saveAll(foodsImages);
            }
            if(foodRepository.count() == 0) {
                foods = Arrays.asList(
                    new FoodEntity(null, "Pollo", "Carne magra rica en proteínas, baja en grasa. Fuente de vitaminas B6, B12, zinc, hierro.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/pechuga-de-pollo-sin-piel", "https://cdn-icons-png.flaticon.com/512/3143/3143626.png", foodsImages, null, getCategoryByName(categories, "Carnes y Aves"), null, null, null),
                    new FoodEntity(null, "Arroz", "Grano rico en almidón que constituye la base de la alimentación de gran parte del mundo. Fuente de hidratos de carbono complejos y fibra.", "https://bing.com/search?q=Arroz+blanco%2c+cocido+site%3afatsecret.es", "https://cdn-icons-png.flaticon.com/512/2714/2714036.png", null, null, getCategoryByName(categories, "Granos"), null, null, null),
                    new FoodEntity(null, "Brocoli", "Verdura crucífera rica en vitaminas C, K, folatos, potasio y fibra. Posee propiedades anticancerígenas.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/br%C3%B3coli-cocido-(fresco)", "https://cdn-icons-png.flaticon.com/512/2346/2346952.png", null, null, getCategoryByName(categories, "Vegetales"), null, null, null),  
                    new FoodEntity(null, "Platano", "Fruta tropical rica en hidratos de carbono, potasio, vitamina B6 y fibra dietética. Aporta rápida energía.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/pl%C3%A1tano-maduro", "https://cdn-icons-png.flaticon.com/512/10247/10247513.png", null, null, getCategoryByName(categories, "Frutas"), null, null, null),
                    new FoodEntity(null, "Lentejas", "Legumbre rica en proteínas, hidratos de carbono complejos, fibra, hierro, folatos y potasio.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/lentejas-cocidas?portionid=51933&portionamount=100,000", "https://cdn-icons-png.flaticon.com/512/2165/2165967.png", null, null, getCategoryByName(categories, "Granos"), null, null, null),
                    new FoodEntity(null, "Salmon", "Pescado azul rico en proteínas, grasas omega 3 y vitaminas A, D, B6, B12. Beneficioso para el corazón y el cerebro.", "https://www.fatsecret.com.mx/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/salm%C3%B3n", "https://cdn-icons-png.flaticon.com/512/4809/4809697.png", null, null, getCategoryByName(categories, "Pescados y Mariscos"), null, null, null),
                    new FoodEntity(null, "Leche", "Bebida láctea rica en proteínas, calcio, vitaminas A y B12. Fortalece huesos y dientes.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/leche-(leche-entera)", "https://cdn-icons-png.flaticon.com/512/372/372973.png", null, null, getCategoryByName(categories, "Lacteos"), null, null, null),
                    new FoodEntity(null, "Pescado", "Alimento rico en proteínas, grasas saludables y vitaminas del complejo B. Excelente para el cerebro y corazón.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/filete-de-pescado-al-horno-o-la-parrilla?portionid=50627&portionamount=100,000", "https://cdn-icons-png.flaticon.com/512/3829/3829599.png", null, null, getCategoryByName(categories, "Pescados y Mariscos"), null, null, null),
                    new FoodEntity(null, "Cebolla", "Bulbo vegetal con compuestos azufrados, vitamina C, flavonoides y fibra. Propiedades anticancerígenas.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/cebollas-rojas?portionid=340024&portionamount=1,000", "https://cdn-icons-png.flaticon.com/512/5194/5194898.png", null, null, getCategoryByName(categories, "Vegetales"), null, null, null),
                    new FoodEntity(null, "Limon", "Fruta cítrica rica en vitamina C y flavonoides. Ayuda a depurar el organismo y fortalece defensas.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/lim%C3%B3n", "https://cdn-icons-png.flaticon.com/512/4056/4056931.png", null, null, getCategoryByName(categories, "Frutas"), null, null, null),
                    new FoodEntity(null, "Aji", "Picante natural rico en capsaicina. Estimula el metabolismo y tiene propiedades anticancerígenas.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/aj%C3%AD-verde", "https://cdn-icons-png.flaticon.com/512/5371/5371600.png", null, null, getCategoryByName(categories, "Especias"), null, null, null),
                    new FoodEntity(null, "Carne", "Alimento rico en proteínas, hierro y vitaminas del grupo B. Debe consumirse con moderación por su alto contenido de grasa.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/filete-de-ternera?portionid=50263&portionamount=200,000", "https://cdn-icons-png.flaticon.com/512/869/869472.png", null, null, getCategoryByName(categories, "Carnes y Aves"), null, null, null),
                    new FoodEntity(null, "Papa", "Tubérculo rico en hidratos de carbono, vitaminas del grupo B, potasio y vitamina C. Aporta energía de fácil digestión.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/papa", "https://cdn-icons-png.flaticon.com/512/1652/1652077.png", null, null, getCategoryByName(categories, "Vegetales"), null, null, null),
                    new FoodEntity(null, "Tomate", "Fruto carnoso rico en licopeno, vitamina C y potasio. Posee propiedades antioxidantes y anticancerígenas.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/hacendado/tomate-frito/100g", "https://cdn-icons-png.flaticon.com/512/7313/7313003.png", null, null, getCategoryByName(categories, "Vegetales"), null, null, null),
                    new FoodEntity(null, "Ajo", "Bulbo conocido por sus propiedades medicinales, antibacterianas y antioxidantes. Rico en vitaminas B6 y C.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/ajo-picado", "https://cdn-icons-png.flaticon.com/512/4139/4139327.png", null, null, getCategoryByName(categories, "Especias"), null, null, null),
                    new FoodEntity(null, "Queso", "Derivado lácteo rico en proteínas, calcio, fósforo y vitaminas A y B12. Alto contenido de grasas saturadas.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/queso-cheddar", "https://cdn-icons-png.flaticon.com/512/517/517561.png", null, null, getCategoryByName(categories, "Lacteos"), null, null, null),
                    new FoodEntity(null, "Lechuga", "Verdura de hoja verde rica en vitaminas A, C, K y folatos. Muy baja en calorías.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/lechuga-iceberg-(incluye-los-arrepollos)", "https://cdn-icons-png.flaticon.com/512/424/424227.png", null, null, getCategoryByName(categories, "Vegetales"), null, null, null),
                    new FoodEntity(null, "Aceituna", "Fruto del olivo rico en grasas monoinsaturadas y compuestos bioactivos. Fuente de vitamina E.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/aceitunas-verdes", "https://cdn-icons-png.flaticon.com/512/1350/1350888.png", null, null, getCategoryByName(categories, "Frutas"), null, null, null),
                    new FoodEntity(null, "Huevo", "Alimento rico en proteínas de alto valor biológico, vitaminas y minerales. Contiene colesterol.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/huevo-cocido", "https://cdn-icons-png.flaticon.com/512/837/837560.png", null, null, getCategoryByName(categories, "Huevos"), null, null, null),
                    new FoodEntity(null, "Mayonesa", "Salsa emulsionada a base de huevo y aceite. Rico en grasas, debe consumirse con moderación.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/search?q=Mayonesa+Casera", "https://cdn-icons-png.flaticon.com/512/1812/1812069.png", null, null, getCategoryByName(categories, "Salsas y Endulzantes"), null, null, null),
                    new FoodEntity(null, "Palta", "Fruto tropical rico en grasas monoinsaturadas y vitaminas B, C, E y K. Beneficioso para el corazón.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/aguacates?portionid=32968&portionamount=0,500", "https://cdn-icons-png.flaticon.com/512/6974/6974016.png", null, null, getCategoryByName(categories, "Frutas"), null, null, null),
                    new FoodEntity(null, "Cecina", "Carne de res salada y deshidratada. Rico en proteínas. Alto contenido de sodio.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/search?q=Cecina+de+Pecho+de+Res+%28Salados%2c+Cocidos%29", "https://cdn-icons-png.flaticon.com/512/10851/10851763.png", null, null, getCategoryByName(categories, "Carnes y Aves"), null, null, null),
                    new FoodEntity(null, "Gallina", "Carne magra rica en proteínas, baja en grasa. Fuente de vitaminas B6, B12, zinc, hierro.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/pollo-asado", "https://cdn-icons-png.flaticon.com/512/8429/8429902.png", null, null, getCategoryByName(categories, "Carnes y Aves"), null, null, null),
                    new FoodEntity(null, "Vino", "Bebida alcohólica derivada de la uva. Debe consumirse con moderación por su contenido de alcohol.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/alimentos/vino-tinto", "https://cdn-icons-png.flaticon.com/512/4698/4698198.png", null, null, getCategoryByName(categories, "Bebidas"), null, null, null),
                    new FoodEntity(null, "Azucar", "Edulcorante refinado rico en calorías. Su consumo excesivo puede provocar sobrepeso y caries.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/azucarera/az%C3%BAcar-blanco/1-cucharadita", "https://cdn-icons-png.flaticon.com/512/5900/5900651.png", null, null, getCategoryByName(categories, "Salsas y Endulzantes"), null, null, null),
                    new FoodEntity(null, "Claras de huevo", "Parte del huevo rica en proteínas de alto valor biológico y baja en grasa. Muy versátil en preparaciones.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/clara-de-huevo", "https://cdn-icons-png.flaticon.com/512/4312/4312575.png", null, null, getCategoryByName(categories, "Huevos"), null, null, null),
                    new FoodEntity(null, "Esencia de vainilla", "Extracto natural que se usa para dar aroma y sabor dulce a preparaciones.", "https://www.fatsecret.es/calor%C3%ADas-nutrici%C3%B3n/gen%C3%A9rico/extracto-de-vainilla", "https://cdn-icons-png.flaticon.com/512/8008/8008186.png", null, null, getCategoryByName(categories, "Especias"), null, null, null),
                    new FoodEntity(null, "Manjarblanco", "Preparación típica chilena a base de leche y azúcar. Rico en calorías por su alto contenido de azúcares.", "https://www.myfitnesspal.com/es/food/calories/manjar-blanco-1158827675", "https://cdn-icons-png.flaticon.com/512/6715/6715320.png", null, null, getCategoryByName(categories, "Dulces"), null, null, null)
                );
                foodRepository.saveAll(foods);
                // Cambiar esta lógica completamente cuando se quiera agregar fotos por individual a cada food
                for (FoodEntity food : foods) {
                    for (FoodImagesEntity foodImage : foodsImages) {
                        foodImage.setFood(food);
                    }
                    break;
                }
            }
            if(compositionFoodRepository.count() == 0) {
                Map<String, Map<String, Double>> nutrientCompositionFoodMap = new HashMap<String, Map<String, Double>>() {
                    {
                        put("Pollo", Map.of(
                            "Calorias", 1.46, // KCAL
                            "Proteina", 0.263, // G
                            "Grasa", 0.035, // G
                            "Colesterol", 0.487, // MG
                            "Vitamina B3", 0.074, // MG
                            "Vitamina B6", 0.003, // MG
                            "Hierro", 0.006, // MG
                            "Magnesio", 0.157, // MG
                            "Fosforo", 1.241, // MG
                            "Potasio", 1.421 // MG
                        ));

                        put("Arroz", Map.of(
                            "Calorias", 2.05, // KCAL
                            "Proteina", 0.043, // G
                            "Carbohidratos", 0.445, // G
                            "Grasa", 0.004, // G
                            "Fibra", 0.006, // G
                            "Calcio", 0.158, // MG
                            "Hierro", 0.019, // MG
                            "Magnesio", 0.19, // MG
                            "Fosforo", 0.679, // MG
                            "Potasio", 0.553 // MG
                        ));

                        put("Brocoli", Map.of(
                            "Calorias", 0.98, // KCAL
                            "Proteina", 0.067, // G
                            "Carbohidratos", 0.201, // G
                            "Fibra", 0.092, // G
                            "Vitamina C", 1.817, // MG
                            "Calcio", 1.12, // MG
                            "Hierro", 0.019, // MG
                            "Magnesio", 0.588, // MG
                            "Fosforo", 1.876, // MG
                            "Potasio", 8.204 // MG
                        ));

                        put("Platano", Map.of(
                            "Calorias", 2.32, // KCAL
                            "Proteina", 0.016, // G
                            "Carbohidratos", 0.623, // G
                            "Grasa", 0.004, // G
                            "Fibra", 0.046, // G
                            "Vitamina B6", 0.005, // MG
                            "Vitamina C", 0.218, // MG
                            "Magnesio", 0.64, // MG
                            "Fosforo", 0.56, // MG
                            "Potasio", 3.8 // MG
                        ));
                        
                        put("Lentejas", Map.of(
                            "Calorias", 2.26, // KCAL
                            "Proteina", 0.179, // G
                            "Carbohidratos", 0.387, // G
                            "Fibra", 0.156, // G
                            "Hierro", 0.066, // MG
                            "Magnesio", 0.713, // MG
                            "Fosforo", 3.564, // MG
                            "Potasio", 7.306, // MG
                            "Cinc", 0.025, // MG
                            "Calcio", 0.376 // MG
                        ));
                        
                        put("Salmon", Map.of(
                            "Calorias", 7.33, // KCAL
                            "Proteina", 0.787, // G
                            "Grasa", 0.44, // G
                            "Colesterol", 2.243, // MG
                            "Vitamina B1", 0.012, // MG
                            "Vitamina B6", 0.023, // MG
                            "Calcio", 0.534, // MG
                            "Hierro", 0.012, // MG
                            "Magnesio", 1.068, // MG
                            "Potasio", 13.67 // MG
                        ));
                        
                        put("Leche", Map.of(
                            "Calorias", 0.6, // KCAL
                            "Proteina", 0.032, // G
                            "Carbohidratos", 0.045, // G
                            "Grasa", 0.033, // G
                            "Colesterol", 0.1, // MG
                            "Sodio", 0.4, // MG
                            "Azucares", 0.053, // G
                            "Calcio", 1.2, // MG
                            "Potasio", 1.5 // MG
                        ));
                        
                        put("Pescado", Map.of(
                            "Calorias", 0.82, // KCAL
                            "Proteina", 0.27, // G
                            "Carbohidratos", 0.002, // G
                            "Grasa", 0.022, // G
                            "Omega 3", 0.003, // G
                            "Calcio", 0.15, // MG
                            "Fosforo", 0.88, // MG
                            "Potasio", 2.5 // MG
                        ));
                        
                        put("Cebolla", Map.of(
                            "Calorias", 0.427, // KCAL
                            "Proteina", 0.013, // G
                            "Carbohidratos", 0.102, // G
                            "Fibra", 0.016, // G
                            "Azucares", 0.045, // G
                            "Vitamina C", 0.081, // MG
                            "Folato", 0.209, // MG
                            "Potasio", 1.61, // MG
                            "Calcio", 0.22, // MG
                            "Sodio", 0.035 // MG
                        ));
                        
                        put("Limon", Map.of(
                            "Calorias", 0.29, // KCAL
                            "Carbohidratos", 0.093, // G
                            "Proteina", 0.011, // G
                            "Grasa", 0.003, // G
                            "Fibra", 0.028, // G
                            "Azucares", 0.025, // G
                            "Vitamina C", 1.06, // MG
                            "Potasio", 2.76, // MG
                            "Calcio", 0.52, // MG
                            "Sodio", 0.02 // MG
                        ));
                        
                        put("Aji", Map.of(
                            "Calorias", 0.4, // KCAL
                            "Proteina", 0.014, // G
                            "Carbohidratos", 0.091, // G
                            "Fibra", 0.015, // G
                            "Vitamina C", 1.06, // MG
                            "Vitamina A", 0.48, // MG
                            "Potasio", 3.22, // MG
                            "Azucares", 0.052 // G
                        ));
                            
                        put("Carne", Map.of(
                            "Calorias", 2.58, // KCAL
                            "Proteina", 0.263, // G
                            "Grasa", 0.181, // G
                            "Colesterol", 0.825, // MG
                            "Hierro", 0.025, // MG
                            "Zinc", 0.05, // MG
                            "Sodio", 2.37 // MG
                        ));
                        
                        put("Papa", Map.of(
                            "Calorias", 0.913, // KCAL
                            "Proteina", 0.022, // G
                            "Carbohidratos", 0.2, // G
                            "Fibra", 0.022, // G
                            "Potasio", 4.21, // MG
                            "Vitamina C", 0.197, // MG
                            "Calcio", 0.12 // MG
                        ));
                        
                        put("Tomate", Map.of(
                            "Calorias", 0.18, // KCAL
                            "Proteina", 0.009, // G
                            "Carbohidratos", 0.039, // G
                            "Azucares", 0.026, // G
                            "Fibra", 0.012, // G
                            "Potasio", 2.37, // MG
                            "Vitamina C", 0.14, // MG
                            "Calcio", 0.1 // MG
                        ));
                        
                        put("Ajo", Map.of(
                            "Calorias", 1.49, // KCAL
                            "Proteina", 0.064, // G
                            "Carbohidratos", 0.331, // G
                            "Azucares", 0.01, // G
                            "Fibra", 0.021, // G
                            "Calcio", 2.46, // MG
                            "Fosforo", 1.53 // MG
                        ));
                        
                        put("Queso", Map.of(
                            "Calorias", 3.257, // KCAL
                            "Proteina", 0.23, // G
                            "Carbohidratos", 0.035, // G
                            "Grasa", 0.31, // G
                            "Azucares", 0.022, // G
                            "Colesterol", 0.957, // MG
                            "Calcio", 7.0, // MG
                            "Fosforo", 5.0, // MG
                            "Sodio", 7.43 // MG
                        ));
                        
                        put("Lechuga", Map.of(
                            "Calorias", 0.153, // KCAL
                            "Proteina", 0.013, // G
                            "Carbohidratos", 0.031, // G
                            "Azucares", 0.013, // G
                            "Fibra", 0.015, // G
                            "Folato", 0.384, // MG
                            "Vitamina A", 74.05, // MG
                            "Potasio", 1.94 // MG
                        ));
                        
                        put("Aceituna", Map.of(
                            "Calorias", 1.067, // KCAL
                            "Proteina", 0.004, // G
                            "Carbohidratos", 0.036, // G
                            "Grasa", 0.14, // G
                            "Fibra", 0.026, // G
                            "Azucares", 0.003, // G
                            "Calcio", 0.88, // MG
                            "Magnesio", 0.04, // MG
                            "Sodio", 10.83 // MG
                        ));
                        
                        put("Huevo", Map.of(
                            "Calorias", 1.3, // KCAL
                            "Proteina", 0.12, // G
                            "Carbohidratos", 0.007, // G
                            "Grasa", 0.085, // G
                            "Azucares", 0.006, // G
                            "Colesterol", 3.985, // MG
                            "Sodio", 1.16 // MG
                        ));
                        
                        put("Mayonesa", Map.of(
                            "Calorias", 3.9, // KCAL
                            "Carbohidratos", 0.239, // G
                            "Grasa", 0.334, // G
                            "Azucares", 0.064, // G
                            "Colesterol", 0.26, // MG
                            "Sodio", 7.11 // MG
                        ));
                        
                        put("Palta", Map.of(
                            "Calorias", 1.2, // KCAL
                            "Proteina", 0.015, // G
                            "Carbohidratos", 0.065, // G
                            "Grasa", 0.11, // G
                            "Fibra", 0.05, // G
                            "Sodio", 0.055 // MG
                        ));
                        
                        put("Cecina", Map.of(
                            "Calorias", 1.16, // KCAL
                            "Proteina", 0.094, // G
                            "Carbohidratos", 0.031, // G
                            "Grasa", 0.073, // G
                            "Fibra", 0.005, // G
                            "Azucares", 0.026, // G
                            "Colesterol", 0.14, // MG
                            "Sodio", 5.9, // MG
                            "Hierro", 0.015 // MG
                        ));
                        
                        put("Gallina", Map.of(
                            "Calorias", 1.1, // KCAL
                            "Proteina", 0.231, // G
                            "Grasa", 0.012, // G
                            "Hierro", 0.007, // MG
                            "Sodio", 0.65, // MG
                            "Vitamina B3", 0.112, // MG
                            "Vitamina B6", 0.005, // MG
                            "Vitamina B12", 0.004, // MCG
                            "Folato", 0.04, // MCG
                            "Vitamina A", 0.21 // IU
                        ));
                        
                        put("Vino", Map.of(
                            "Calorias", 0.812, // KCAL
                            "Carbohidratos", 0.0135, // G
                            "Alcohol", 0.106 // G
                        ));
                        
                        put("Azucar", Map.of(
                            "Calorias", 4.0, // KCAL
                            "Carbohidratos", 1.0 // G
                        ));

                        put("Claras de huevo", Map.of(
                            "Calorias", 0.52, // KCAL
                            "Proteina", 0.109, // G
                            "Carbohidratos", 0.007, // G
                            "Grasa", 0.0017, // G
                            "Sodio", 1.66, // MG
                            "Azucares", 0.0071 // G
                        ));
                        
                        put("Esencia de vainilla", Map.of(
                            "Calorias", 2.88, // KCAL
                            "Alcohol", 0.014, // G
                            "Azucares", 0.1265 // G
                        ));
                        
                        put("Manjarblanco", Map.of(
                            "Calorias", 3.15, // KCAL
                            "Carbohidratos", 0.554, // G
                            "Grasa", 0.074, // G
                            "Azucares", 0.497, // G
                            "Sodio", 1.29 // MG
                        ));
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
                                if (nutrient.getId() == 1)
                                    uniteOfMeasuremente = UnitOfMeasurementEnum.KCAL;
                                compositionsFoods.add(new CompositionFoodEntity(new CompositionFoodKey(food.getId(), nutrient.getId()), food, nutrient, nutrientQuantity, uniteOfMeasuremente));
                            }
                        }
                    }
                }
                compositionFoodRepository.saveAll(compositionsFoods);
            }
            if(roleRepository.count() == 0) {
                roles = Arrays.asList(
                    new RoleEntity(null, RoleEnum.ADMIN, "Como administrador, tendrás el control total del sistema con acceso a herramientas avanzadas\nGestión y supervisión de toda la actividad de la plataforma\nAcceso a todas las funcionalidades disponibles en el Plan Básico\nCapacidad para crear y compartir alimentos o recetas con otros usuarios\nExportación de reportes en formato CSV o PDF para análisis detallados\nAcceso a herramientas de comparación avanzadas, reportes detallados y gráficos de progreso\nPosibilidad de ajustar planes y objetivos\nFacilidades para la implementación y gestión de contenido exclusivo en la plataforma", 0.0),
                    new RoleEntity(null, RoleEnum.USER, "Registra tu ingesta y monitorea tu progreso con funciones esenciales\nSeguimiento de ingesta\nCálculos basados en tus objetivos nutricionales\nCálculos basados en tu nivel de actividad física\nAcceso a una amplia gama de alimentos y recetas\nReportes básicos de progreso", 0.0),
                    new RoleEntity(null, RoleEnum.VIP, "Maximiza tu experiencia con herramientas avanzadas, soporte pers\nTodas las funcionalidades del Plan Básico\nCrear y compartir alimentos o recetas\nExportar reportes en formato CSV o PDF\nComparación, reportes y gráficos de progreso\nSugerencias de comidas basadas en objetivos\nSeguimiento de progreso personalizado\nSoporte tecnológico prioritario", 30.0)
                );
                roleRepository.saveAll(roles);
            }
            if(userRepository.count() == 0) {
                // Roles disponibles
                Optional<RoleEntity> getUSER = roleRepository.findByName(RoleEnum.USER);
                Optional<RoleEntity> getADMIN = roleRepository.findByName(RoleEnum.ADMIN);
                RoleEntity USER = getUSER.get();
                RoleEntity ADMIN = getADMIN.get();
                // Creacion de usuarios
                users = Arrays.asList(
                    new UserEntity(null, "kiridepapel", "$2a$10$8YYL6YTawu99l5vFqGTKI.ul/1JoG9FJ9z2HkPRFv6znEb39jvTIe", "brian@gmail.com", "Brian Uceda", "https://i.ibb.co/WgW6HHT/yo1-centrado.jpg", null, false, false, "179.6.5.75", null, null, null, null, null, getRandomObjectives(objectives, 7), null, null, null, new HashSet<>(Arrays.asList(ADMIN, USER))),
                    new UserEntity(null, "heatherxvalencia", "$2a$10$Qk5oaC0/Hxlk.vV/CzlcC.plvHOqkiB0qX9ticZy2vexuYnCNigWC", "heather@gmail.com", "Heather Valencia", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", null, false, false, "179.6.5.75", null, null, null, null, null, getRandomObjectives(objectives, 6), null, null, null, new HashSet<>(Arrays.asList(USER))),
                    new UserEntity(null, "whoami", "$2a$10$eAs212VvPpG6hkDvS.7nQeY8zrXPW2ZwF6EvJIO5cHigEgRbOSvqi", "willy@gmail.com", "Willy Samata", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", null, false, false, "179.6.5.75", null, null, null, null, null, getRandomObjectives(objectives, 2), null, null, null, new HashSet<>(Arrays.asList(USER))),
                    new UserEntity(null, "nayde", "$2a$10$rPmZPIWQxtAXvYqKi75PnuNx6E.oZ6dhSVb5XvP9W7AAbrhDgC8D6", "nayde@gmail.com", "Naydeline Duran", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", null, false, false, "179.6.5.75", null, null, null, null, null, getRandomObjectives(objectives, 5), null, null, null, new HashSet<>(Arrays.asList(USER))),
                    new UserEntity(null, "gabi", "$2a$10$NcPc5la3asRhlY8R6bPwJufxND8mK4H9yPphe54VbhXVdNBfOT7NO", "gabriela@gmail.com", "Gabriela Reyes", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", null, false, false, "179.6.5.75", null, null, null, null, null, getRandomObjectives(objectives, 5), null, null, null, new HashSet<>(Arrays.asList(USER))),
                    new UserEntity(null, "impresora", "$2a$10$WfNOvx0qLnDec/qA1sYGaOvsiII4uKycCMJTHdtiPjdRH8ehw1XIG", "edson@gmail.com", "Edson Linares", "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png", null, false, false, "179.6.5.75", null, null, null, null, null, getRandomObjectives(objectives, 2), null, null, null, new HashSet<>(Arrays.asList(USER)))
                );
                userRepository.saveAll(users);
            }
            if(recipeRepository.count() == 0) {
                recipes = Arrays.asList(
                    new RecipeEntity(null, "Arroz con pollo", "Plato sabroso que combina el arroz con trozos de pollo y verduras.", "Cocinar el arroz y rehogar el pollo con las verduras. Mezclar y sazonar al gusto.", "Rico en carbohidratos y proteínas. Nutritivo y energético.", "Alto contenido calórico si se excede en porciones.", 4.6, null, null, null, null),
                    new RecipeEntity(null, "Ceviche", "Plato típico peruano a base de pescado marinado en jugo de limón. Se sirve con cebolla, ají y choclo.", "Marinar el pescado cortado en cubos con jugo de limón. Agregar cebolla, ají y choclo. Sazonar al gusto.", "Rico en proteínas. El limón ayuda a la digestión. Bajo en calorías.", "No recomendado en embarazadas por riesgo de parasitosis. El exceso de limón puede irritar el estómago.", 5.0, null, null, null, null),
                    new RecipeEntity(null, "Lomo saltado", "Plato sabroso de la cocina peruana hecho con tiras de lomo salteadas con tomate, cebolla y ají.", "Saltear la carne con aceite y especias. Agregar cebolla, tomate y ají. Servir con papas fritas.", "Carne magra y vegetales frescos. Rico en proteínas y vitaminas.", "Alto contenido de sodio por el uso de soya y ajinomoto. Las papas fritas aportan grasas.", 5.0, null, null, null, null),
                    new RecipeEntity(null, "Tacacho con cecina", "Plato típico de la selva hecho con plátanos verdes, cecina y salsa criolla.", "Cocinar y machacar los plátanos. Freír y hacer tortillas. Acompañar con cecina y salsa criolla.", "Combinación de carbohidratos y proteínas. La cecina aporta hierro.", "Alto contenido calórico. La cecina tiene mucho sodio. Los plátanos verdes pueden ser indigestos.", 4.2, null, null, null, null),
                    new RecipeEntity(null, "Papa a la huancaina", "Entrante cremoso de papas sancochadas bañadas en salsa de ají amarillo y queso fresco.", "Cocer las papas con cáscara. Pelar y cortar en rodajas. Bañar con salsa de ají amarillo y queso. Decorar con huevo duro y aceituna.", "Combinación nutritiva de papa, queso, huevo y ají. Rico en vitaminas.", "Alto contenido calórico por las cremas y aceites. El exceso de ají puede irritar el estómago.", 4.1, null, null, null, null),
                    new RecipeEntity(null, "Causa limenia", "Plato frío hecho con una mezcla de mashed potato y pollo desmenuzado.", "Cocinar y triturar las papas con aceite. Agregar pollo desmenuzado y limón. Decorar con mayonesa, aceituna y huevo.", "Aporte balanceado de carbohidratos y proteínas. Sabor agradable.", "Alto contenido calórico por las cremas y aceites. La mayonesa tiene colesterol.", 3.6, null, null, null, null),
                    new RecipeEntity(null, "Aji de gallina", "Plato representativo a base de trozos de pollo en una salsa cremosa de ají amarillo.", "Cocer y deshilachar el pollo. Cocinar la salsa con ají amarillo y especias. Mezclar con el pollo y crema. Servir con arroz.", "Salsa cremosa y sabrosa. Buena fuente de proteínas.", "Alto contenido calórico por la crema de leche. Picante para algunos paladares.", 4.7, null, null, null, null),
                    new RecipeEntity(null, "Suspiro limenio", "Postre criollo hecho con manjarblanco y merengue de claras de huevo.", "Batir las claras a punto de nieve con azúcar. Integrar con el manjarblanco. Decorar con ralladura de limón.", "Postre ligero con contrasts de sabores. Bajo en calorías.", "Alto contenido de azúcar. No recomendado para diabéticos ni intolerantes a la lactosa.", 2.2, null, null, null, null)
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
                                if (nutrient.getId() == 1)
                                    uniteOfMeasuremente = UnitOfMeasurementEnum.KCAL;
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
                for (Map.Entry<String, Map<String, Double>> entry : recipeIngredientMap.entrySet()) {
                    String recipeName = entry.getKey();
                    RecipeEntity recipe = recipeNameMap.get(recipeName);
                    if (recipe != null) {
                        for (Map.Entry<String, Double> ingredientEntry : entry.getValue().entrySet()) {
                            String foodName = ingredientEntry.getKey();
                            Double quantity = ingredientEntry.getValue();
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
                    UserPersonalInfoEntity personalInfo = new UserPersonalInfoEntity();
                    personalInfo.setGender(genders[rand.nextInt(genders.length)]);
                    // Fecha de nacimiento aleatoria entre 1980 y 2010
                    personalInfo.setBorndate(Timestamp.valueOf(LocalDate.of(1980 + rand.nextInt(30), 1 + rand.nextInt(11), 1 + rand.nextInt(28)).atStartOfDay()));
                    personalInfo.setCity("Lima");
                    personalInfo.setCountry("Perú");
                    personalInfo.setHeightCm(150.0 + rand.nextInt(50));
                    personalInfo.setStartWeightKg(personalInfo.getWeightKg());
                    personalInfo.setWeightKg(50.0 + rand.nextInt(5001) / 100.0);
                    personalInfo.setActivityLevel(activityLevels.get(rand.nextInt(activityLevels.size())));
                    user.setUserPersonalInfo(personalInfo);
                    usersPersonalInfos.add(personalInfo);
                }
                userPersonalInfoRepository.saveAll(usersPersonalInfos);
            }
            if(userFitnessInfoRepository.count() == 0) {
                for (UserEntity user : users) {
                    Random rand = new Random();
                    UserFitnessInfoEntity fitnessInfo = new UserFitnessInfoEntity();
                    // Obtener el peso actual del usuario desde la tabla user_personal_info
                    Double currentWeight = user.getUserPersonalInfo().getWeightKg();
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
                    // Asignacion de avgProteinPerKg
                    fitnessInfo.setAvgProteinPerKg(null);
                    // Asignacion de TMB
                    fitnessInfo.setTmb(null);
                    // Asociar el UserEntity con el UserFitnessInfoEntity
                    user.setUserFitnessInfo(fitnessInfo);
                    // fitnessInfo.setUser(user);
                    usersFitnessInfos.add(fitnessInfo);
                }
                userFitnessInfoRepository.saveAll(usersFitnessInfos);
            }
            if(userConfigRepository.count() == 0) {
                for (UserEntity user : users) {
                    Random rand = new Random();
                    UserConfigEntity config = new UserConfigEntity();
                    config.setNotifications(rand.nextBoolean());
                    config.setLastFoodEntry(Timestamp.valueOf(LocalDateTime.now().plusDays(-rand.nextInt(4))));
                    config.setLastWeightUpdate(Timestamp.valueOf(LocalDateTime.now().plusDays(-(3 + rand.nextInt(18)))));
                    config.setDarkMode(rand.nextBoolean());
                    user.setUserConfig(config);
                    usersConfig.add(config);
                }
                userConfigRepository.saveAll(usersConfig);
            }
            if(eatRepository.count() == 0) {
                for(UserEntity user : users) {
                    for(int i = 0; i < new Random().nextInt(11) + 4; i++) {
                        EatEntity eat = new EatEntity();
                        Timestamp sendTime = new Timestamp(new Random().nextLong(System.currentTimeMillis() - 5 * 24 * 60 * 60 * 1000, System.currentTimeMillis() - 3 * 60 * 60 * 1000));
                        eat.setDate(sendTime);
                        // Calculo de fecha
                        LocalTime timeOfEat = sendTime.toLocalDateTime().toLocalTime();
                        if (timeOfEat.isAfter(LocalTime.of(2, 0)) && timeOfEat.isBefore(LocalTime.of(12, 0)))
                            eat.setCategoryIntake(CategoryIntakeEnum.DESAYUNO);
                        else if (timeOfEat.isAfter(LocalTime.of(11, 59, 59, 999)) && timeOfEat.isBefore(LocalTime.of(19, 0)))
                            eat.setCategoryIntake(CategoryIntakeEnum.ALMUERZO);
                        else eat.setCategoryIntake(CategoryIntakeEnum.CENA);
                        eat.setUser(user);
                        if(new Random().nextBoolean()) {
                            eat.setFood(getRandomObject(foods));
                            eat.setRecipe(null);
                            eat.setUnitOfMeasurement(UnitOfMeasurementEnum.G);
                            if (eat.getUnitOfMeasurement() == UnitOfMeasurementEnum.UN) {
                                eat.setEatQuantity(1.0 + (double) new Random().nextInt(3));
                            } else {
                                // Genera un decimal aleatorio entre 1 y 400
                                eat.setEatQuantity(Math.round((1 + new Random().nextDouble() * 399) * 10.0) / 10.0);
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
            if (walletRepository.count() == 0) {
                for (UserEntity user : users) {
                    WalletEntity wallet = new WalletEntity();
                    wallet.setBalance(0.0);
                    wallet.setCurrency("PEN");
                    wallet.setCurrencyName("Sol");
                    wallet.setCurrencySymbol("S/.");
                    wallets.add(wallet);
                }
                walletRepository.saveAll(wallets);
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