package pe.edu.upc.MonolithFoodApplication.dtos.activitylevel;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class ActivityLevelsResponseDTO extends ResponseDTO {
    private List<ActivityLevelDTO> activityLevels;

    public ActivityLevelsResponseDTO(List<ActivityLevelDTO> activityLevels) {
        this.activityLevels = activityLevels;
    }

    @Builder
    public ActivityLevelsResponseDTO(String message, Integer statusCode, List<ActivityLevelDTO> activityLevels) {
        super(message, statusCode);
        this.activityLevels = activityLevels;
    }
}
