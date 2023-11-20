package pe.edu.upc.MonolithFoodApplication.dtos.objectives;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
public class ObjectivesResponseDTO extends ResponseDTO {
    private List<ObjectiveDTO> objectives;

    public ObjectivesResponseDTO(List<ObjectiveDTO> objectives) {
        this.objectives = objectives;
    }

    @Builder
    public ObjectivesResponseDTO(String message, Integer statusCode, ResponseType type, List<ObjectiveDTO> objectives) {
        super(message, statusCode, type);
        this.objectives = objectives;
    }
}
