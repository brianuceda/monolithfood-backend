package pe.edu.upc.MonolithFoodApplication.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users_personal_info")
@Data
@AllArgsConstructor
@NoArgsConstructor

// Willy

public class UserPersonalInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private GenderEnum gender;

    @Column(nullable = false, precision = 5, scale = 3)
    private Double height_cm;

    @Column(nullable = false, precision = 5, scale = 3)
    private Double weight_kg;

    @Column(nullable = false, length = 50)
    private String activity_level;

    @Column(nullable = false, precision = 5, scale = 3)
    private Double target_weight_kg;
    
    @Column(nullable = false, precision = 5, scale = 3)
    private Double daily_caloric_intake;
    
    @OneToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
}
