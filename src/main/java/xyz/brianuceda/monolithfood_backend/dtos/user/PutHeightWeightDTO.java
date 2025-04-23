package xyz.brianuceda.monolithfood_backend.dtos.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

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
