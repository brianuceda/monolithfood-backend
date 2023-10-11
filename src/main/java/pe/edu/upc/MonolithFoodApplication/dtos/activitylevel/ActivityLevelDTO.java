package pe.edu.upc.MonolithFoodApplication.dtos.activitylevel;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLevelDTO {
    private String name;
    private String days;
    private String information;
    private Double quotient;
    private Boolean selected = false;

    // @Override
    // public String toString() {
    //     return "ActivityLevelDTO {"
    //         + "name='" + name + '\''
    //         + ", days='" + days + '\''
    //         + ", information='" + information + '\''
    //         + ", quotient=" + quotient
    //         + ", selected=" + selected
    //         + '}';
    // }

}
