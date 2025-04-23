package xyz.brianuceda.monolithfood_backend.dtos.reports;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MacrosPerDays {
    private Double domingo;
    private Double lunes;
    private Double martes;
    private Double miercoles;
    private Double jueves;
    private Double viernes;
    private Double sabado;
}
