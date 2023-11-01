package pe.edu.upc.MonolithFoodApplication.dtos.user;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPersonalInfoDTO extends ResponseDTO {
    private String username;
    private String email;
    private String names;
    private GenderEnum gender;
    private Timestamp borndate;
    private String location;
    private Double weightKg;
    private Double heightCm;
    private String imc;

    @Builder
    public GetPersonalInfoDTO(String message, Integer statusCode, ResponseType type, String username, String email, String names, GenderEnum gender, Timestamp borndate, String location, Double weightKg, Double heightCm, String imc) {
        super(message, statusCode, type);
        this.username = username;
        this.email = email;
        this.names = names;
        this.gender = gender;
        this.borndate = borndate;
        this.location = location;
        this.weightKg = weightKg;
        this.heightCm = heightCm;
        this.imc = imc;

    }
}
