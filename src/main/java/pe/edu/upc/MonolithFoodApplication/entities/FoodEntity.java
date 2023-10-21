package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "food")
public class FoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80, unique = true)
    private String name;

    @Column(nullable = false, length = 256)
    private String information;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrivacityEnum privacity = PrivacityEnum.PRIVATE;

    @Column(nullable = true)
    private Boolean isFavorite = false;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String sourceOfOrigin;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, name = "creator_user_id")
    private UserEntity creatorUser; 

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "category_food_id")
    private CategoryFoodEntity categoryFood;

    @ToString.Exclude
    @OneToMany(mappedBy = "food", fetch = FetchType.EAGER)
    private List<CompositionFoodEntity> compositions;

    @ToString.Exclude
    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY)
    private List<IngredientEntity> ingredients;

    // Se puede usar para:
    // 1. Obtener la cantidad de veces que se ha comido un alimento
    // 2. Obtener la cantidad de veces que se ha usado un alimento en una receta
    // 3. Obtener los alimentos que se han comido en un rango de fechas por TODOS LOS USUARIOS o por los usuarios de un rango de edades específico o por un rol específico (ADMIN, USER, etc)
    // 4. Obtener los alimentos que se han usado en una receta en un rango de fechas por TODOS LOS USUARIOS o por los usuarios de un rango de edades específico o por un rol específico (ADMIN, USER, etc)
    @ToString.Exclude
    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<EatEntity> eats;

}
