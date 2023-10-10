package pe.edu.upc.MonolithFoodApplication.dtos.objectives;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class ObjectivesResponseDTO extends ResponseDTO {
    private List<ObjectiveDTO> objectives;

    public ObjectivesResponseDTO(List<ObjectiveDTO> objectives) {
        this.objectives = objectives;
    }

    @Builder
    public ObjectivesResponseDTO(String message, Integer statusCode, List<ObjectiveDTO> objectives) {
        super(message, statusCode);
        this.objectives = objectives;
    }
}
