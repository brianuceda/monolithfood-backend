package pe.edu.upc.MonolithFoodApplication.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users_personal_info")
public class UserPersonalInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private GenderEnum gender;

    @Column(nullable = false)
    private Double heightCm;

    @Column(nullable = false)
    private Double weightKg;

    @Column(nullable = false, length = 50)
    private String activityLevel;

    @Column(nullable = false)
    private Double targetWeightKg;
    
    @Column(nullable = false)
    private Double daily_CaloricIntake;
    
    @OneToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

}
