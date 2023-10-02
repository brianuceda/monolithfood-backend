package pe.edu.upc.MonolithFoodApplication.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDTO {
    private String username;
    private String password;
    private String email;
    private String names;
    private String surnames;
    private String profileImg;
    private String ipAddres;

}
