package pe.edu.upc.MonolithFoodApplication.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletAndLocationDTO {
    private String currency;
    private String currencySymbol;
    private String currencyName;
    private String location;
}
