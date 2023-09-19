package pe.edu.upc.MonolithFoodApplication.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.AllArgsConstructor;

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
    
}
