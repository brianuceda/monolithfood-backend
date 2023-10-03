package pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class ListAvgDayCalDTO extends ResponseDTO {
    List<AvgDayCalDTO> avgDayCalDTOList;

    public ListAvgDayCalDTO(List<AvgDayCalDTO> avgDayCalDTOList) {
        this.avgDayCalDTOList = avgDayCalDTOList;
    }

    @Builder
    public ListAvgDayCalDTO(String message, Integer statusCode, List<AvgDayCalDTO> avgDayCalDTOList) {
        super(message, statusCode);
        this.avgDayCalDTOList = avgDayCalDTOList;
    }
    
}
