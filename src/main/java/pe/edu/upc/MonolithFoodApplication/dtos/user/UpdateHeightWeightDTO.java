package pe.edu.upc.MonolithFoodApplication.dtos.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

@Getter
@Setter
public class UpdateHeightWeightDTO extends ResponseDTO {
    private Double heightCm;
    private Double weightKg;

    @Builder
    public UpdateHeightWeightDTO(String message, Integer statusCode, Double heightCm, Double weightKg) {
        super(message, statusCode);
        this.heightCm = heightCm;
        this.weightKg = weightKg;
    }
    
}
