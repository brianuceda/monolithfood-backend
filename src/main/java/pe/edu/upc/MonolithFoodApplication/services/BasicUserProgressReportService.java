package pe.edu.upc.MonolithFoodApplication.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.MonolithFoodApplication.dtos.ProgressReportDTO;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
public class BasicUserProgressReportService {
    @Autowired
    private UserRepository userRepository;

    public ProgressReportDTO TotalCaloriesConsumedInTheWeek(String username, Double totalCalories)
    {

        // List<EatEntity> eatEntityList = eatRepository.findByUserName(userEntity.get());

        return null;
    }

    public Double CalculateTotalCaloriesConsumedInTheWeek(Double totalCalories)
    {   
        //suma total de calorias consumidas en la semana
        Double totalCaloriesConsumedInTheWeek=0.0;
        //totalCaloriesConsumedInTheWeek= totalCaloriesConsumedInTheWeek+totalCalories;
        return null;
    }
}
