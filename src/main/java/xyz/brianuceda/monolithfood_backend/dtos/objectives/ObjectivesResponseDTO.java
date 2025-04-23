package xyz.brianuceda.monolithfood_backend.dtos.objectives;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

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
