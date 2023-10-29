package pe.edu.upc.MonolithFoodApplication.dtos.objectives;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class ProgressWeightDTO extends ResponseDTO {
    private Timestamp targetDate;
    private Double percentence;
    private Double currentWeight;
    private Double startWeight;
    private Double targetWeight;

    @Builder
    public ProgressWeightDTO(String message, Integer status,Timestamp targetDate, Double percentence, Double currentWeight, Double startWeight, Double targetWeight) {
        super(message, status);
        this.targetDate = targetDate;
        this.percentence = percentence;
        this.currentWeight = currentWeight;
        this.startWeight = startWeight;
        this.targetWeight = targetWeight;
    }

    public void minusHours(Integer quantity) {
        Timestamp targetDate = this.targetDate;
        LocalDateTime dateTimeOfEat = targetDate.toLocalDateTime().minusHours(quantity);
        this.targetDate = Timestamp.valueOf(dateTimeOfEat);
    }
    
    public void plusHours(Integer quantity) {
        Timestamp targetDate = this.targetDate;
        LocalDateTime dateTimeOfEat = targetDate.toLocalDateTime().plusHours(quantity);
        this.targetDate = Timestamp.valueOf(dateTimeOfEat);
    }

}
