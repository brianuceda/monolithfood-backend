package xyz.brianuceda.monolithfood_backend.dtos.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

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
