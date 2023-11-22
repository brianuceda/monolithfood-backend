package pe.edu.upc.MonolithFoodApplication.dtos.reports;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;

@Getter
@Setter
@NoArgsConstructor
public class CaloriasDias extends ResponseDTO {
    private Double domingo;
    private Double lunes;
    private Double martes;
    private Double miercoles;
    private Double jueves;
    private Double viernes;
    private Double sabado;

    public CaloriasDias(Double domingo, Double lunes, Double martes, Double miercoles, Double jueves,
            Double viernes, Double sabado) {
        this.domingo = domingo;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
    }

    public CaloriasDias(String message, Integer statusCode, ResponseType type, Double domingo,
            Double lunes, Double martes, Double miercoles, Double jueves, Double viernes,
            Double sabado) {
        super(message, statusCode, type);
        this.domingo = domingo;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
        this.sabado = sabado;
    }
}
