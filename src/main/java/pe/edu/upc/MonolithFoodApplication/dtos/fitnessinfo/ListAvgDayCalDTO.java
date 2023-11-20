package pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
public class ListAvgDayCalDTO extends ResponseDTO {
    List<AvgDayCalDTO> consumedList;

    public ListAvgDayCalDTO(List<AvgDayCalDTO> consumedList) {
        this.consumedList = consumedList;
    }

    @Builder
    public ListAvgDayCalDTO(String message, Integer statusCode, ResponseType type, List<AvgDayCalDTO> consumedList) {
        super(message, statusCode, type);
        this.consumedList = consumedList;
    }
    
}
