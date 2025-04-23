package pe.edu.upc.MonolithFoodApplication.entities;

import java.sql.Timestamp;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.enums.GenderEnum;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_personal_info")
public class UserPersonalInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private GenderEnum gender;

    @Column(nullable = true)
    private Timestamp borndate;

    @Column(nullable = true, length = 64)
    private String city;

    @Column(nullable = true, length = 64)
    private String country;

    @Column(nullable = true)
    private Double heightCm;

    @Column(nullable = true)
    private Double startWeightKg;

    @Column(nullable = true)
    private Double weightKg;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = true, name = "activity_level_id")
    private ActivityLevelEntity activityLevel;

    // @ToString.Exclude
    // @OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "user_id")
    // private UserEntity user;

}
