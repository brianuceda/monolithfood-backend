package xyz.brianuceda.monolithfood_backend.dtos.fitnessinfo;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

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
