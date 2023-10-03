package pe.edu.upc.MonolithFoodApplication.entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Timestamp birthdate;

    @Column(nullable = true)
    private Double heightCm;

    @Column(nullable = true)
    private Double weightKg;

    @ManyToOne
    @JoinColumn(nullable = true, name = "activity_level_id")
    private ActivityLevelEntity activityLevel;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
