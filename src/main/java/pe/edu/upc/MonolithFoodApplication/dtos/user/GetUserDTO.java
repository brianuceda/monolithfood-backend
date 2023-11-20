package pe.edu.upc.MonolithFoodApplication.dtos.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
public class GetUserDTO extends ResponseDTO {
    private String username;
    private String profileImg;
    private String currencySymbol;
    private Double currency;

    public GetUserDTO(String username, String profileImg, String currencySymbol, Double currency) {
        this.username = username;
        this.profileImg = profileImg;
        this.currencySymbol = currencySymbol;
        this.currency = currency;
    }

    @Builder
    public GetUserDTO(String message, Integer statusCode, ResponseType type, String username, String profileImg, String currencySymbol, Double currency) {
        super(message, statusCode, type);
        this.username = username;
        this.profileImg = profileImg;
        this.currencySymbol = currencySymbol;
        this.currency = currency;
    }
}
