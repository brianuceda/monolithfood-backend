package pe.edu.upc.MonolithFoodApplication.entities;

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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="wallet")
public class WalletEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Builder.Default
    @Column(nullable = false)
    private Double balance = 0.0;
    
    @Column(nullable = true, length = 10)
    private String currency;
    
    @Column(nullable = true, length = 10)
    private String currencySymbol;
    
    @Column(nullable = true, length = 10)
    private String currencyName;

    // @ToString.Exclude
    // @OneToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "user_id")
    // private UserEntity user;
}
