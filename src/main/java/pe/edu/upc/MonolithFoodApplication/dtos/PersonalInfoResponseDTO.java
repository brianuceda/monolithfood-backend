package pe.edu.upc.MonolithFoodApplication.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
