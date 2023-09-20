package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.upc.MonolithFoodApplication.dtos.IMCDTO;
import pe.edu.upc.MonolithFoodApplication.services.UserPersonalInfoService;

@RestController
@RequestMapping("/api/personalInfo")
public class UserPersonalInfoController {
    @Autowired
    private UserPersonalInfoService userPersonalInfoService;

    
    @PutMapping("/updateWeight")
    public ResponseEntity<IMCDTO> updateWeightAndGetIMC(@RequestParam String username, @RequestParam Double weight) {
        IMCDTO imcDTO = userPersonalInfoService.updateWeightAndGetIMC(username, weight);
        return new ResponseEntity<>(imcDTO, HttpStatus.OK);
    }

    @PutMapping("/updateHeight")
    public ResponseEntity<IMCDTO> updateHeightAndGetIMC(@RequestParam String username,@RequestParam Double height) {
        IMCDTO imcDTO = userPersonalInfoService.updateHeightAndGetIMC(username, height);
        return new ResponseEntity<>(imcDTO, HttpStatus.OK);
    }
}
