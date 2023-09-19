package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.MonolithFoodApplication.dtos.UserPersonalInfoDTO;
import pe.edu.upc.MonolithFoodApplication.services.UserPersonalInfoService;

@RestController
@RequestMapping("/api/userpersonalInfo")
public class UserPersonalInfoController {

    @Autowired
    private final UserPersonalInfoService userPersonalInfoService;
    public UserPersonalInfoController(UserPersonalInfoService userPersonalInfoService) {
        this.userPersonalInfoService = userPersonalInfoService;
    }

    @PutMapping
    public ResponseEntity<UserPersonalInfoDTO>updatePersonalInfo(@RequestBody UserPersonalInfoDTO userPersonallnfoDto){
        return new ResponseEntity<>(userPersonalInfoService.updateUserPeronalInfo(userPersonallnfoDto), HttpStatus.OK);
    }

}
