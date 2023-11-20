package pe.edu.upc.MonolithFoodApplication.dtos.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
public class PhotoDTO extends ResponseDTO {
    private String photo;

    @Builder
    public PhotoDTO(String message, Integer statusCode, ResponseType type, String photo) {
        super(message, statusCode, type);
        this.photo = photo;
    }
    
}
