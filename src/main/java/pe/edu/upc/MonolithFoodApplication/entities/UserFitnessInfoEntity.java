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
@Table(name = "user_fitness_info")
public class UserFitnessInfoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Double targetWeightKg;

    @Column(nullable = true)
    private Timestamp targetDate;
    
    @Column(nullable = true)
    private Double imc;
    
    @Column(nullable = true)
    private Double dailyCaloricIntake;
    
    @Column(nullable = true)
    private Double dailyProteinIntake;
    
    @Column(nullable = true)
    private Double dailyCarbohydrateIntake;
    
    @Column(nullable = true)
    private Double dailyFatIntake;
    
    @OneToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
    
}
