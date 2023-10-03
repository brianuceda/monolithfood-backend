package pe.edu.upc.MonolithFoodApplication.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.AvgDayCalDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.CaloriesConsumedLastWeekDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.ListAvgDayCalDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.repositories.EatRepository;

@Service
@RequiredArgsConstructor
public class BasicUserProgressReportService {
    // ? Atributos
    // Inyección de dependencias
    private final EatRepository eatRepository;

    // ? Metodos
    // * Willy: Obtener calorias consumidas en la ultima semana
    public ResponseDTO getCaloriesConsumedInTheLastWeek (String username) {   
        List<Object[]> results = eatRepository.AveragecaloriesLastWeek(username);
        if (results.isEmpty()) return new ResponseDTO("No has consumido alimentos en la última semana.", 200);
        Object[] firstResult = results.get(0);
        return new CaloriesConsumedLastWeekDTO("Cantidad de calorias consumidas en la última semana.", 200,
            (String) firstResult[0],
            (Double) firstResult[1]
        );
    }
    // * Willy: Obtener un promedio de calorias consumidas por dia en la ultima semana
    public ResponseDTO getAverageDailyCaloriesConsumedDTO (String username) {
        List<Object[]> results = eatRepository.AverageCalorieConsumptioDay(username);
        if (results.isEmpty()) return new ResponseDTO("No has consumido alimentos en la última semana.", 200);
        List<AvgDayCalDTO> averageDailyCaloriesConsumedDTOs = results.stream().map(result -> {
            String formattedDate = formatDate((Timestamp) result[1]);
            return new AvgDayCalDTO(
                (String) result[0],
                formattedDate,
                (Double) result[2]
            );
        }).collect(Collectors.toList());
        ListAvgDayCalDTO listAvgDayCalDTO = new ListAvgDayCalDTO("Promedio de calorias consumidas por el día en la última semana.", 200, averageDailyCaloriesConsumedDTOs);
        return listAvgDayCalDTO;
    }

    // ? Funciones auxiliares
    // FUNCIÓN: Formatea una fecha a un string con el formato "EEE dd/MM/yyyy"
    private String formatDate(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd/MM/yyyy");
        Date date = new Date(timestamp.getTime());
        return dateFormat.format(date);
    }
}
