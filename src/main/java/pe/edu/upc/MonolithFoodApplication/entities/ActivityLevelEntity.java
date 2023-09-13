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
@Table(name = "activity_level") // Factor de actividad física (Harris-Benedict)
public class ActivityLevelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80, unique = true)
    private String name;

    @Column(nullable = false)
    private Double quotient;

    @Column(length = 20)
    private String days;

    @Column(length = 512)
    private String information;

    @OneToMany(mappedBy = "activityLevel", cascade = CascadeType.ALL)
    private List<UserPersonalInfoEntity> users;
}
