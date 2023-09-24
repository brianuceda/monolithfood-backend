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

    @PostMapping("/enablednoti/{user}")
    public ResponseEntity<HttpStatus> enablednoti (@PathVariable ("user") Long user){
        try {
            userConfigService.updateNotiStatus(true,user);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } catch (Exception x){
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/disablednoti/{user}")
    public ResponseEntity<HttpStatus> disablednoti (@PathVariable ("user") Long user){
        try {
            userConfigService.updateNotiStatus(false,user);
            return new ResponseEntity<HttpStatus>(HttpStatus.OK);
        } catch (Exception x){
            return new ResponseEntity<HttpStatus>(HttpStatus.NOT_FOUND);
        }
    }
}
