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
@Table(name = "objective")
public class ObjectiveEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, length = 512)
    private String information;

    @Column(nullable = false)
    private Double proteinPerKg;

    @ManyToMany(mappedBy = "objectives", fetch = FetchType.LAZY)
    private List<UserEntity> users;

}
