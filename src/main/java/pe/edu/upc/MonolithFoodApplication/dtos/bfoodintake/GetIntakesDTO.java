package pe.edu.upc.MonolithFoodApplication.dtos.bfoodintake;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class GetIntakesDTO extends ResponseDTO {
    private List<IntakeDTO> myIntakes;

    public GetIntakesDTO(List<IntakeDTO> myIntakes) {
        this.myIntakes = myIntakes;
    }

    @Builder
    public GetIntakesDTO(String message, Integer status, List<IntakeDTO> myIntakes) {
        super(message, status);
        this.myIntakes = myIntakes;
    }
}
