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
public class PutPersonalInfoDTO extends ResponseDTO {
    private String names;
    private GenderEnum gender;
    private Timestamp borndate;

    @Builder
    public PutPersonalInfoDTO(String message, Integer statusCode, ResponseType type, String names, GenderEnum gender, Timestamp borndate) {
        super(message, statusCode, type);
        this.names = names;
        this.gender = gender;
        this.borndate = borndate;
    }
}
