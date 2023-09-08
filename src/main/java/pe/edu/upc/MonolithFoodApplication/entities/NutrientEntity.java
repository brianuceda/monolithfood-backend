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

    @Column(nullable = false, length = 50, unique = true)
    private String name;

    @Column(nullable = false, length = 255)
    private String information;

    @Column(nullable = true, length = 755)
    private String detailed_information;

    @OneToMany(mappedBy = "nutrient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<CompositionEntity> compositions;

}
