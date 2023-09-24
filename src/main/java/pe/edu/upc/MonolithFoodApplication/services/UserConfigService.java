package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.MonolithFoodApplication.entities.UserConfigEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.UserConfigRepository;

@Service
public class UserConfigService {

    @Autowired
    private final UserConfigRepository userConfigRepository;
    public UserConfigService(UserConfigRepository userConfigRepository) {
        this.userConfigRepository = userConfigRepository;
    }

    public void updateNotiStatus (boolean status, Long userId){
        UserConfigEntity userConfigEntity = userConfigRepository.findByUserId(userId); //busco la config
      
        userConfigEntity.setNotificationsEnabled(status);//true or false
        userConfigRepository.save(userConfigEntity); // actualiza la config
    }



}
