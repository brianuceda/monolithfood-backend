package pe.edu.upc.MonolithFoodApplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.upc.MonolithFoodApplication.services.UserConfigService;

@RestController
@RequestMapping("/api/userConfig")
public class UserConfigController {

    @Autowired

    private final UserConfigService userConfigService;
    public UserConfigController(UserConfigService userConfigService) {
        this.userConfigService = userConfigService;
    }

    @PostMapping("/enablednoti/{user}/status/{status}")
    public ResponseEntity<String> enablednoti (@PathVariable ("user") Long user, @PathVariable ("status") boolean status){
        try {
            userConfigService.updateNotiStatus(status,user);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

}
