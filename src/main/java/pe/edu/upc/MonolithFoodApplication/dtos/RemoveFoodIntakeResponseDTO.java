package pe.edu.upc.MonolithFoodApplication.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveFoodIntakeResponseDTO {
    private String foodName;
    private Double removedCalories;
}

