package pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class ListAvgDayCalDTO extends ResponseDTO {
    List<AvgDayCalDTO> consumedList;

    public ListAvgDayCalDTO(List<AvgDayCalDTO> consumedList) {
        this.consumedList = consumedList;
    }

    @Builder
    public ListAvgDayCalDTO(String message, Integer statusCode, List<AvgDayCalDTO> consumedList) {
        super(message, statusCode);
        this.consumedList = consumedList;
    }
    
}
