package pe.edu.upc.MonolithFoodApplication.dtos.user;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.SimpleObjectDTO;

@Getter
@Setter
public class ObjectivesResponseDTO extends ResponseDTO {
    private List<SimpleObjectDTO> objectives;

    public ObjectivesResponseDTO(List<SimpleObjectDTO> objectives) {
        this.objectives = objectives;
    }

    @Builder
    public ObjectivesResponseDTO(String message, Integer statusCode, List<SimpleObjectDTO> objectives) {
        super(message, statusCode);
        this.objectives = objectives;
    }
}
