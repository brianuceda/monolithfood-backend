package pe.edu.upc.MonolithFoodApplication.dtos.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class PutHeightWeightDTO extends ResponseDTO {
    private Double heightCm;
    private Double weightKg;

    @Builder
    public PutHeightWeightDTO(String message, Integer statusCode, Double heightCm, Double weightKg) {
        super(message, statusCode);
        this.heightCm = heightCm;
        this.weightKg = weightKg;
    }
    
}
