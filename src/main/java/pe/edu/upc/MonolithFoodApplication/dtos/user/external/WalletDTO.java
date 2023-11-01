package pe.edu.upc.MonolithFoodApplication.dtos.user.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletDTO {
    private String currency;
    private String currencySymbol;
    private String currencyName;
}
