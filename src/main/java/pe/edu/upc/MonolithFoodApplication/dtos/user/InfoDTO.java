package pe.edu.upc.MonolithFoodApplication.dtos.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class InfoDTO extends ResponseDTO {
    private String username;
    private String email;
    private String names;
    private String profileImg;

    public InfoDTO(String username, String email, String names, String profileImg) {
        this.username = username;
        this.email = email;
        this.names = names;
        this.profileImg = profileImg;
    }

    @Builder
    public InfoDTO(String message, Integer statusCode, String username, String email, String names, String profileImg) {
        super(message, statusCode);
        this.username = username;
        this.email = email;
        this.names = names;
        this.profileImg = profileImg;
    }
}
