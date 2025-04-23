package xyz.brianuceda.monolithfood_backend.dtos.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

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
