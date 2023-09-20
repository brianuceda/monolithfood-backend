package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.upc.MonolithFoodApplication.dtos.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.UserPersonalInfoDTO;
import pe.edu.upc.MonolithFoodApplication.services.UserPersonalInfoService;

@RestController
@RequestMapping("/api/userPersonalInfo")
public class UserPersonalInfoController {

    @Autowired
    private UserPersonalInfoService userPersonalInfoService;

    @PutMapping("/update")
    public ResponseEntity<ResponseDTO>updatePersonalInfo(@RequestParam String username, @RequestBody UserPersonalInfoDTO userPersonallnfoDto) {
        ResponseDTO response = userPersonalInfoService.updateUserPeronalInfo(username, userPersonallnfoDto);
        return new ResponseEntity<>(response, HttpStatus.valueOf(response.getStatusCode()));
    }

}
