package pe.edu.upc.MonolithFoodApplication.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    private String username;
    private String password;
    private String ipAddress;

}
