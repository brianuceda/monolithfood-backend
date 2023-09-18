package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "nutrients")
public class NutrientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80, unique = true)
    private String name;

    @Column(nullable = false, length = 256)
    private String information;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String detailed_information;

    @OneToMany(
        mappedBy = "nutrient",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER
    )
    private List<CompositionEntity> compositions;

}
