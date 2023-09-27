package pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@AllArgsConstructor
@NoArgsConstructor
public class IMCDTO {
    private Double imc;
    private String clasification;
    private Double newHeight;
    private Double newWeight;
}
