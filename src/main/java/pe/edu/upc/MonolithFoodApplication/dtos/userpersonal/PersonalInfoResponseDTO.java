package pe.edu.upc.MonolithFoodApplication.dtos.userpersonal;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class PersonalInfoResponseDTO extends ResponseDTO {

    private PersonalInfoRequestDTO personalInfo;

    @Builder
    public PersonalInfoResponseDTO(String message, Integer statusCode, PersonalInfoRequestDTO personalInfo) {
        super(message, statusCode);
        this.personalInfo = personalInfo;
    }
    
}
