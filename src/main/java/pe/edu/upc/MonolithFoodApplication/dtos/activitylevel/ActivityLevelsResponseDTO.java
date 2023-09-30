package pe.edu.upc.MonolithFoodApplication.dtos.activitylevel;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class ActivityLevelsResponseDTO extends ResponseDTO {
    private List<ActivityLevelDTO> objectives;

    public ActivityLevelsResponseDTO(List<ActivityLevelDTO> objectives) {
        this.objectives = objectives;
    }

    @Builder
    public ActivityLevelsResponseDTO(String message, Integer statusCode, List<ActivityLevelDTO> objectives) {
        super(message, statusCode);
        this.objectives = objectives;
    }
}
