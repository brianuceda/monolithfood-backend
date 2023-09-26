package pe.edu.upc.MonolithFoodApplication.entities;

import java.sql.Timestamp;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
    @Column(nullable = false)
    private GenderEnum gender;

    @Column(nullable = false)
    private Timestamp birthdate;

    @Column(nullable = false)
    private Double heightCm;

    @Column(nullable = false)
    private Double weightKg;
   
    @Column(name = "daily_caloric_intake", nullable = false)
    private Double dailyCaloricIntake;

    @ManyToOne
    @JoinColumn(name = "activity_level_id", nullable = false)
    private ActivityLevelEntity activityLevel;
    
    @OneToOne
    @JoinColumn(name="user_id")
    private UserEntity user;


}
