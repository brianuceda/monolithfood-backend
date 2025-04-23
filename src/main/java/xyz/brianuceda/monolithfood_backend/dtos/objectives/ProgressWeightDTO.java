package xyz.brianuceda.monolithfood_backend.dtos.objectives;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

@Getter
@Setter
public class ProgressWeightDTO extends ResponseDTO {
    private Timestamp targetDate;
    private Double percentence;
    private Double currentWeight;
    private Double startWeight;
    private Double targetWeight;

    @Builder
    public ProgressWeightDTO(String message, Integer status, ResponseType type,Timestamp targetDate, Double percentence, Double currentWeight, Double startWeight, Double targetWeight) {
        super(message, status, type);
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
