package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nutrient")
public class NutrientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String information;
    private String detailed_information;
    
    @OneToMany(mappedBy = "nutrient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CompositionEntity> compositions;

}