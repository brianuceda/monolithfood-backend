package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.MonolithFoodApplication.dtos.UserPersonallnfoDto;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.UserPersonaIInfoRepository;

@Service
public class UserPersonalInfoService {

    @Autowired
    private final UserPersonaIInfoRepository userPersonaIInfoRepository;

    public UserPersonalInfoService(UserPersonaIInfoRepository userPersonaIInfoRepository) {
        this.userPersonaIInfoRepository = userPersonaIInfoRepository;
    }

    public UserPersonallnfoDto updateUserPeronalInfo(UserPersonallnfoDto userPersonallnfoDto) {
        UserPersonalInfoEntity personal = userPersonaIInfoRepository.findById(userPersonallnfoDto.getId())
                .orElseThrow(() -> new RuntimeException("User personal not found"));
        UserPersonalInfoEntity personalInfoEntity;

        //se valida para la actulización
        if (userPersonallnfoDto.getGender() != null) {
            personal.setGender(userPersonallnfoDto.getGender());
        }
        if (userPersonallnfoDto.getBirthdate() != null) {
            personal.setBirthdate(userPersonallnfoDto.getBirthdate());
        }
        if (userPersonallnfoDto.getHeightCm() != null) {
            personal.setHeightCm(userPersonallnfoDto.getHeightCm());
        }
        if (userPersonallnfoDto.getWeightKg() != null) {
            personal.setWeightKg(userPersonallnfoDto.getWeightKg());
        }
        if (userPersonallnfoDto.getActivityLevel() != null) {
            personal.setActivityLevel(userPersonallnfoDto.getActivityLevel());
        }
        if (userPersonallnfoDto.getUser() != null) {
            personal.setUser(userPersonallnfoDto.getUser());
        }

        personalInfoEntity = userPersonaIInfoRepository.save(personal);

        return new UserPersonallnfoDto(personalInfoEntity.getId(), personalInfoEntity.getGender(), personalInfoEntity.getBirthdate(),
                personalInfoEntity.getHeightCm(), personalInfoEntity.getWeightKg(), personalInfoEntity.getActivityLevel(),
                personalInfoEntity.getUser());


    }
}