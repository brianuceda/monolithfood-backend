package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.UserPersonaIInfoRepository;

@Service
public class UserPersonalInfoService {

    @Autowired
    private final UserPersonaIInfoRepository userPersonaIInfoRepository;
    public UserPersonalInfoService(UserPersonaIInfoRepository userPersonaIInfoRepository) {
        this.userPersonaIInfoRepository = userPersonaIInfoRepository;
    }

    //public UserPersonalInfoEntity

}
