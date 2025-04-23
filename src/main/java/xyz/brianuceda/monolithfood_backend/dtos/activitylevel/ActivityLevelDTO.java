package xyz.brianuceda.monolithfood_backend.dtos.activitylevel;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityLevelDTO {
    private String name;
    private String imgUrl;
    private String days;
    private String information;
    private Double quotient;
    private Boolean selected = false;

}
