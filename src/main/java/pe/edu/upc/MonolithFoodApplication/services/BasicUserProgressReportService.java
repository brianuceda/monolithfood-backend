package pe.edu.upc.MonolithFoodApplication.services;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.AverageDailyCaloriesConsumedDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.CaloriesConsumedLastWeekDTO;
import pe.edu.upc.MonolithFoodApplication.repositories.EatRepository;

@Service
@RequiredArgsConstructor
public class BasicUserProgressReportService {
    // * Atributos
    // Inyección de dependencias
    private final EatRepository eatRepository;

    // * Metodos
    // * Get Calories Consumed
    public CaloriesConsumedLastWeekDTO getCaloriesConsumedInTheLastWeek (String username)
    {   
        List<Object[]> results = eatRepository.AveragecaloriesLastWeek(username);
        Object[] firstResult = results.get(0);
        return new CaloriesConsumedLastWeekDTO(
            (String) firstResult[0],
            (Double) firstResult[1]
        );
    }
    public List<AverageDailyCaloriesConsumedDTO> getAverageDailyCaloriesConsumedDTO (String username)
    {   
        List<Object[]> results = eatRepository.AverageCalorieConsumptioDay(username);
    
        List<AverageDailyCaloriesConsumedDTO> averageDailyCaloriesConsumedDTOs = results.stream().map(result -> {
            String formattedDate = formatDate((Timestamp) result[1]);
            return new AverageDailyCaloriesConsumedDTO(
                (String) result[0],
                formattedDate,
                (Double) result[2]
            );
        }).collect(Collectors.toList());
        
        return averageDailyCaloriesConsumedDTOs;
    }
    private String formatDate(Timestamp timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd/MM/yyyy");
        Date date = new Date(timestamp.getTime());
        return dateFormat.format(date);
    }
}
