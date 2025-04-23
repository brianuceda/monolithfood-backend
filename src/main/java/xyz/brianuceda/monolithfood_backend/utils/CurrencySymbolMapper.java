package xyz.brianuceda.monolithfood_backend.utils;

import java.util.HashMap;
import java.util.Map;

public class CurrencySymbolMapper {
    private static final Map<String, String> CURRENCY_SYMBOLS = new HashMap<>();

    static {
        CURRENCY_SYMBOLS.put("PEN", "S/.");
        CURRENCY_SYMBOLS.put("USD", "$");
        CURRENCY_SYMBOLS.put("EUR", "€");
        CURRENCY_SYMBOLS.put("GBP", "£");
        CURRENCY_SYMBOLS.put("JPY", "¥");
    }
    public static String getCurrencySymbol(String currency) {
        return CURRENCY_SYMBOLS.getOrDefault(currency, null);
    }
}
