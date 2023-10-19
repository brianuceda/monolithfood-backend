package pe.edu.upc.MonolithFoodApplication.dtos.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class GetUserDTO extends ResponseDTO {
    private String username;
    private String profileImg;

    public GetUserDTO(String username, String profileImg) {
        this.username = username;
        this.profileImg = profileImg;
    }

    @Builder
    public GetUserDTO(String message, Integer statusCode, String username, String profileImg) {
        super(message, statusCode);
        this.username = username;
        this.profileImg = profileImg;
    }
}
