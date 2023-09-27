package pe.edu.upc.MonolithFoodApplication.dtos;

import java.security.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaloriesConsumedLastWeekDTO {
    private String username;
    private Double totalCalories;
    // private Timestamp startDate;
    // private Timestamp endDate;
    // private Timestamp currentDate;
}
