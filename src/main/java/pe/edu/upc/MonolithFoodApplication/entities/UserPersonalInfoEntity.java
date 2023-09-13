package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.Date;

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
@Table(name = "user_personal_info")
public class UserPersonalInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private GenderEnum gender;

    @Column(nullable = false)
    private Date birthdate;

    @Column(nullable = false)
    private Double heightCm;

    @Column(nullable = false)
    private Double weightKg;

    @Column(nullable = false)
    private Double targetWeightKg;
    
    @Column(nullable = false)
    private Double dailyCaloricIntake;
   
    @ManyToOne
    @JoinColumn(name = "activity_level_id", nullable = false)
    private ActivityLevelEntity activityLevel;
    
    @OneToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

}
