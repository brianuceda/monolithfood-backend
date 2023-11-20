package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "nutrient")
public class NutrientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80, unique = true)
    private String name;

    @Column(nullable = false, length = 256)
    private String information;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String detailedInformation;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String color;

    @ToString.Exclude
    @OneToMany(mappedBy = "nutrient", fetch = FetchType.LAZY)
    private List<CompositionFoodEntity> compositionsFood;
    
    @ToString.Exclude
    @OneToMany(mappedBy = "nutrient", fetch = FetchType.LAZY)
    private List<CompositionRecipeEntity> compositionsRecipe;

}
