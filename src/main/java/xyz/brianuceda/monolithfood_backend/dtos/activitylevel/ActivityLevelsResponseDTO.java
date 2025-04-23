package xyz.brianuceda.monolithfood_backend.dtos.activitylevel;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

@Getter
@Setter
public class ActivityLevelsResponseDTO extends ResponseDTO {
    private List<ActivityLevelDTO> activityLevels;

    public ActivityLevelsResponseDTO(List<ActivityLevelDTO> activityLevels) {
        this.activityLevels = activityLevels;
    }

    @Builder
    public ActivityLevelsResponseDTO(String message, Integer statusCode, ResponseType type, List<ActivityLevelDTO> activityLevels) {
        super(message, statusCode, type);
        this.activityLevels = activityLevels;
    }
}
