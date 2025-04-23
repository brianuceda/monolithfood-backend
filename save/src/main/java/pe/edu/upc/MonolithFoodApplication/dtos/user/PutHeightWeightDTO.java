package pe.edu.upc.MonolithFoodApplication.dtos.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
public class PutHeightWeightDTO extends ResponseDTO {
    private Double heightCm;
    private Double weightKg;

    @Builder
    public PutHeightWeightDTO(String message, Integer statusCode, ResponseType type, Double heightCm, Double weightKg) {
        super(message, statusCode, type);
        this.heightCm = heightCm;
        this.weightKg = weightKg;
    }
    
}
