package pe.edu.upc.MonolithFoodApplication.entities;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ip_login_attempt")
public class IpLoginAttemptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String ipAddress;

    private Boolean isAccountBlocked = false;
    private Integer attemptsCount;
    private Date lastAttemptDate;
    private Date blockedDate;
    
    @OneToOne
    @JoinColumn(name="user_id")
    private UserEntity user;
    
}