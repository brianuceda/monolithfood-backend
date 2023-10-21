package pe.edu.upc.MonolithFoodApplication.services;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.user.WalletAndLocationDTO;
import pe.edu.upc.MonolithFoodApplication.services.impl.CurrencySymbolMapper;

@Service
@RequiredArgsConstructor
public class ExternalApisService {

    public WalletAndLocationDTO getLocationAndWalletFromIp(String ip) {
        final String uri = "https://ipapi.co/" + ip + "/json/";
        // Hace una petición GET a la API de ipapi.co
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
            uri,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<Map<String, Object>>() {}
        );
        Map<String, Object> result = response.getBody();
        // Retorna la ciudad y el país del usuario
        if (result != null) {
            WalletAndLocationDTO dto = WalletAndLocationDTO.builder()
                .city((String) result.get("city"))
                .country((String) result.get("country_name"))
                .currency((String) result.get("currency"))
                .currencyName((String) result.get("currency_name"))
                .currencySymbol(CurrencySymbolMapper.getCurrencySymbol((String) result.get("currency")))
                .build();
            return dto;
        }
        else
            return null;
    }
}
