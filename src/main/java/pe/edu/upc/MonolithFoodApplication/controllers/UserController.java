package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.bfoodintake.NewIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.bfoodintake.UpdateIntakeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.services.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    // * Atributos
    // Inyección de dependencias
    private final UserService userService;

    @GetMapping("/intake/all")
    public ResponseEntity<?> getAllFoodIntake(@RequestParam("username") String username) {
        ResponseDTO response = userService.getAllFoodIntake(username);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PostMapping("/intake/add")
    public ResponseEntity<?> addFoodIntake(@RequestParam String username, @RequestBody NewIntakeDTO foodIntakeDTO) {
        ResponseDTO response = userService.addFoodIntake(username, foodIntakeDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @PutMapping("/intake/update")
    public ResponseEntity<?> updateFoodIntake(@RequestParam String username, @RequestBody UpdateIntakeDTO newFoodIntakeDTO) {
        ResponseDTO response = userService.updateFoodIntake(username, newFoodIntakeDTO);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    @DeleteMapping("/intake/delete")
    public ResponseEntity<?> deleteFoodIntake(@RequestParam String username, @RequestParam Long foodId) {
        ResponseDTO response = userService.deleteFoodIntake(username, foodId);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

    // @GetMapping("/intake/alert")
    // public ResponseEntity<CaloricIntakeAlertDTO> checkCaloricIntake(@RequestParam String username) {
    //     CaloricIntakeAlertDTO response = userService.checkDailyCaloricIntake(username);
    //     return ResponseEntity.ok(response);
    // }
}
