package xyz.brianuceda.monolithfood_backend.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
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
@Table(name = "activity_level") // Factor de actividad física (Harris-Benedict)
public class ActivityLevelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80, unique = true)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String imgUrl;

    @Column(nullable = true, length = 20)
    private String days;

    @Column(nullable = true, length = 512)
    private String information;

    @Column(nullable = false)
    private Double quotient;

    @ToString.Exclude
    @OneToMany(mappedBy = "activityLevel", cascade = {
            CascadeType.MERGE
    }, fetch = FetchType.LAZY)
    private List<UserPersonalInfoEntity> users;
}
