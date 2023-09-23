package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.upc.MonolithFoodApplication.dtos.CaloricIntakeAlertDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.FoodIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.FoodIntakeResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.RemoveFoodIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.RemoveFoodIntakeResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.UpdateFoodIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.UpdateFoodIntakeResponseDTO;
import pe.edu.upc.MonolithFoodApplication.services.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/intake")
    public ResponseEntity<FoodIntakeResponseDTO> addFoodIntake(@RequestBody FoodIntakeDTO foodIntakeDTO) {
        FoodIntakeResponseDTO response = userService.addFoodIntake(foodIntakeDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/intake/update")
    public ResponseEntity<UpdateFoodIntakeResponseDTO> updateFoodIntake(@RequestBody UpdateFoodIntakeDTO updateFoodIntakeDTO) {
        UpdateFoodIntakeResponseDTO response = userService.updateFoodIntake(updateFoodIntakeDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/intake/remove")
    public ResponseEntity<RemoveFoodIntakeResponseDTO> removeFoodIntake(@RequestBody RemoveFoodIntakeDTO removeFoodIntakeDTO) {
        RemoveFoodIntakeResponseDTO response = userService.removeFoodIntake(removeFoodIntakeDTO);
        return ResponseEntity.ok(response);
    }   


    @GetMapping("/intake/alert")
    public ResponseEntity<CaloricIntakeAlertDTO> checkCaloricIntake(@RequestParam String username) {
        CaloricIntakeAlertDTO response = userService.checkDailyCaloricIntake(username);
        return ResponseEntity.ok(response);
    }



    

}
