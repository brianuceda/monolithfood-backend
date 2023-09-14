package pe.edu.upc.MonolithFoodApplication.entities;


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

    @Column(nullable = false)
    private Double targetWeightKg;
    
    @Column(nullable = false)
    private Double imc;
    
    @Column(nullable = false)
    private Double dailyCaloricIntake;
    
    @Column(nullable = false)
    private Double dailyProteinIntake;
    
    @Column(nullable = false)
    private Double dailyCarbohydrateIntake;
    
    @Column(nullable = false)
    private Double dailyFatIntake;
    
    @OneToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
    
}
