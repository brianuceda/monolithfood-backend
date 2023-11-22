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
    private Double domingo = 0.0;
    private Double lunes = 0.0;
    private Double martes = 0.0;
    private Double miercoles = 0.0;
    private Double jueves = 0.0;
    private Double viernes = 0.0;
    private Double sabado = 0.0;

    public CaloriasDias(String message, Integer statusCode, ResponseType type) {
        super(message, statusCode, type);
    }

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

    public void setValuesOfObjectList(Object[] result) {
        this.domingo = (Double) result[0];
        this.lunes = (Double) result[1];
        this.martes = (Double) result[2];
        this.miercoles = (Double) result[3];
        this.jueves = (Double) result[4];
        this.viernes = (Double) result[5];
        this.sabado = (Double) result[6];
    }

    public void setToCeroAllValues() {
        this.domingo = 0.0;
        this.lunes = 0.0;
        this.martes = 0.0;
        this.miercoles = 0.0;
        this.jueves = 0.0;
        this.viernes = 0.0;
        this.sabado = 0.0;
    }
}
