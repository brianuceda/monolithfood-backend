package xyz.brianuceda.monolithfood_backend.dtos.foodintake;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AllCategoriesIntakesDTO {
    private CategoryIntakeDTO desayuno;
    private CategoryIntakeDTO almuerzo;
    private CategoryIntakeDTO cena;

    public AllCategoriesIntakesDTO() {
        this.desayuno = null;
        this.almuerzo = null;
        this.cena = null;
    }
}
