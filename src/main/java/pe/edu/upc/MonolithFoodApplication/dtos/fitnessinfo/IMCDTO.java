package pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;

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
    public IMCDTO(String message, Integer status, Double imc, String clasification, Double newHeight, Double newWeight) {
        super(message, status);
        this.imc = imc;
        this.clasification = clasification;
        this.newHeight = newHeight;
        this.newWeight = newWeight;
    }
}
