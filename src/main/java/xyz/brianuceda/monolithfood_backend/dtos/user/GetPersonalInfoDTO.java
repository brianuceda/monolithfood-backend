package xyz.brianuceda.monolithfood_backend.dtos.user;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.GenderEnum;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPersonalInfoDTO extends ResponseDTO {
    private String username;
    private String profileImg;
    private String email;
    private String names;
    private GenderEnum gender;
    private Timestamp borndate;
    private String location;
    private Double weightKg;
    private Double heightCm;
    private String imc;
    private String currencySymbol;
    private Double currency;

    @Builder
    public GetPersonalInfoDTO(String message, Integer statusCode, ResponseType type, String username, String profileImg, String email, String names, GenderEnum gender, Timestamp borndate, String location, Double weightKg, Double heightCm, String imc, String currencySymbol, Double currency) {
        super(message, statusCode, type);
        this.username = username;
        this.profileImg = profileImg;
        this.email = email;
        this.names = names;
        this.gender = gender;
        this.borndate = borndate;
        this.location = location;
        this.weightKg = weightKg;
        this.heightCm = heightCm;
        this.imc = imc;
        this.currencySymbol = currencySymbol;
        this.currency = currency;
    }
}
