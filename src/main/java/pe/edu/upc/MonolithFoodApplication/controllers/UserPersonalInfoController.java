package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.userpersonal.PersonalInfoRequestDTO;
import pe.edu.upc.MonolithFoodApplication.services.UserPersonalInfoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/personal-info")
public class UserPersonalInfoController {
    // * Atributos
    // Inyección de dependencias
    private final UserPersonalInfoService userPersonalInfoService;

    // * Metodos
    @PutMapping("/update")
    public ResponseEntity<ResponseDTO>updatePersonalInfo(@RequestParam String username, @RequestBody PersonalInfoRequestDTO userPersonallnfoDto) {
        ResponseDTO response = userPersonalInfoService.updateUserPeronalInfo(username, userPersonallnfoDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

}
