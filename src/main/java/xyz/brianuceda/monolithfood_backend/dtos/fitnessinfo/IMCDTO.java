package xyz.brianuceda.monolithfood_backend.dtos.fitnessinfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import xyz.brianuceda.monolithfood_backend.dtos.general.ResponseDTO;
import xyz.brianuceda.monolithfood_backend.enums.ResponseType;

@Getter
@Setter
public class IMCDTO extends ResponseDTO {
    private Double imc;
    private String clasification;
    private Double newHeight;
    private Double newWeight;

    public IMCDTO() {
    }

    @Builder
    public IMCDTO(String message, Integer status, ResponseType type, Double imc, String clasification, Double newHeight, Double newWeight) {
        super(message, status, type);
        this.imc = imc;
        this.clasification = clasification;
        this.newHeight = newHeight;
        this.newWeight = newWeight;
    }
}
