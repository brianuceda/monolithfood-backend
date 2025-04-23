package pe.edu.upc.MonolithFoodApplication.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.AvgDayCalDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.CaloriesConsumedLastWeekDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.ListAvgDayCalDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;
import pe.edu.upc.MonolithFoodApplication.repositories.EatRepository;

@Service
@RequiredArgsConstructor
public class UserReportService {
    private final EatRepository eatRepository;

    // * Willy: Obtener calorias consumidas en la ultima semana
    public ResponseDTO getCaloriesConsumedInTheLastWeek (String username) {   
        List<Object[]> results = eatRepository.getAveragecaloriesLastWeek(username);
        if (results.isEmpty()) return new ResponseDTO("No has consumido alimentos en la última semana", HttpStatus.OK.value(), ResponseType.INFO);
        Object[] firstResult = results.get(0);
        return new CaloriesConsumedLastWeekDTO("Cantidad de calorías consumidas en la última semana", HttpStatus.OK.value(), ResponseType.SUCCESS,
            (Double) firstResult[0]
        );
    }
    // * Willy: Obtener un promedio de calorias consumidas por dia en la ultima semana
    public ResponseDTO getAverageDailyCaloriesConsumedDTO (String username) {
        List<Object[]> results = eatRepository.getAverageCalorieConsumptioDay(username);
        if (results.isEmpty())
            return new ResponseDTO("No has consumido alimentos en la última semana", HttpStatus.OK.value(), ResponseType.INFO);
        List<AvgDayCalDTO> averageDailyCaloriesConsumedDTOs = results.stream().map(result -> {
            String formattedDate = convertToFormatDateDdMmYyyy((Timestamp) result[0]);
            return new AvgDayCalDTO(
                formattedDate,
                (Double) result[1]
            );
        }).collect(Collectors.toList());
        return new ListAvgDayCalDTO("Promedio de calorías consumidas por día en la última semana",
            HttpStatus.OK.value(), ResponseType.SUCCESS, averageDailyCaloriesConsumedDTOs
        );
    }

    // ? Funciones auxiliares
    private String convertToFormatDateDdMmYyyy(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd/MM/yyyy");
        Date date = new Date(timestamp.getTime());
        return dateFormat.format(date);
    }
}
