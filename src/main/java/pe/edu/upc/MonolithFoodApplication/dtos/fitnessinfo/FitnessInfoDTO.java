package pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FitnessInfoDTO {
    private Double targetWeightKg;
    private Timestamp targetDate;
}
