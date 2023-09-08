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
    private Integer age;
    private String gender;
    private Double height_cm;
    private Double weight_kg;
    private String activity_level;
    private Double target_weight_kg;
    private Double daily_caloric_intake;
    //relation from user_personal_info to user
    @OneToOne
    @JoinColumn(name="user_id")
    private UserEntity user; 
}
