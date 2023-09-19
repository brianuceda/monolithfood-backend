package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.MonolithFoodApplication.dtos.UserPersonalInfoDTO;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.UserPersonalInfoRepository;

@Service
public class UserPersonalInfoService {

    @Autowired
    private UserPersonalInfoRepository userPersonalInfoRepository;

    public UserPersonalInfoDTO updateUserPeronalInfo(String username, UserPersonalInfoDTO personalInfoDTO) {
        UserPersonalInfoDTO getRepoPersonalInfo = userPersonalInfoRepository.findByUsername(username);

        //se valida para la actulización
        if (personalInfoDTO.getGender() != null) {
            getRepoPersonalInfo.setGender(personalInfoDTO.getGender());
        }
        if (personalInfoDTO.getBirthdate() != null) {
            getRepoPersonalInfo.setBirthdate(personalInfoDTO.getBirthdate());
        }
        if (personalInfoDTO.getHeightCm() != null) {
            getRepoPersonalInfo.setHeightCm(personalInfoDTO.getHeightCm());
        }
        if (personalInfoDTO.getWeightKg() != null) {
            getRepoPersonalInfo.setWeightKg(personalInfoDTO.getWeightKg());
        }

        
        UserPersonalInfoEntity returnData = new UserPersonalInfoEntity(null, null, null, null, null, null, null)

        userPersonalInfoRepository.save(getRepoPersonalInfo);
        return getRepoPersonalInfo;
        

    }













}