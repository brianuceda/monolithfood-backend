package pe.edu.upc.MonolithFoodApplication.entities;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ip_login_attempt", indexes = {
        @Index(name = "ip_login_ipaddress_idx", columnList = "ipAddress"),
        @Index(name = "ip_login_userid_ipaddress_idx", columnList = "user_id,ipAddress"),
        @Index(name = "ip_login_lastattemptdate_idx", columnList = "lastAttemptDate")
})
public class IpLoginAttemptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 45)
    private String ipAddress;

    @Column(nullable = false)
    private Boolean isIpBlocked = false;
    
    @Column(nullable = false)
    private Timestamp lastAttemptDate;

    @Column(nullable = true)
    private Timestamp blockedDate;

    @Column(nullable = false)
    private Integer attemptsCount = 1;

    @ManyToOne
    @JoinColumn(nullable = true, name = "user_id")
    private UserEntity user;

}
