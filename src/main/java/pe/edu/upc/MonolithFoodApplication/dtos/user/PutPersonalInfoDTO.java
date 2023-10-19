package pe.edu.upc.MonolithFoodApplication.dtos.user;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PutPersonalInfoDTO extends ResponseDTO {
    private String names;
    private GenderEnum gender;
    private Timestamp borndate;

    @Builder
    public PutPersonalInfoDTO(String message, Integer statusCode, String names, GenderEnum gender, Timestamp borndate) {
        super(message, statusCode);
        this.names = names;
        this.gender = gender;
        this.borndate = borndate;
    }
}
