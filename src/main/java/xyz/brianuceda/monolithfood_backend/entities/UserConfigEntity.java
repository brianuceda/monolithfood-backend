package xyz.brianuceda.monolithfood_backend.entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_config")
public class UserConfigEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean notifications;

    @Column(nullable = true)
    private Timestamp lastFoodEntry;

    @Column(nullable = true)
    private Timestamp lastWeightUpdate;

    @Column(nullable = false)
    private Boolean darkMode;

    // @ToString.Exclude
    // @OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "user_id")
    // private UserEntity user;

}
