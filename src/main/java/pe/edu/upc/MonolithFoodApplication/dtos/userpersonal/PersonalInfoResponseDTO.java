package pe.edu.upc.MonolithFoodApplication.dtos.userpersonal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.ResponseDTO;

@Getter
@Setter
public class PersonalInfoResponseDTO extends ResponseDTO {

    private PersonalInfoRequestDTO updatedPersonalInfo;

    @Builder
    public PersonalInfoResponseDTO(String message, Integer statusCode, PersonalInfoRequestDTO updatedPersonalInfo) {
        super(message, statusCode);
        this.updatedPersonalInfo = updatedPersonalInfo;
    }
    
}
