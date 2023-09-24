package pe.edu.upc.MonolithFoodApplication.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.MonolithFoodApplication.dtos.IMCDTO;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserFitnessInfoEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.UserFitnessInfoRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserPersonalInfoRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
public class UserPersonalInfoService {
    @Autowired
    private UserRepository userRepository;

    public IMCDTO updateWeightAndGetIMC(String username, Double weight) {

        //temporal, hasta implementar el JWT
        String user= username;//
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);//
        //actualizar el peso en la tabla user personal info

        Double imc= calculateIMC(weight, userEntity.get().getUserPersonalInfo().getHeightCm());//

        userEntity.get().getUserFitnessInfo().setImc(imc);//
        

        userEntity.get().getUserPersonalInfo().setWeightKg(weight);
        userRepository.save(userEntity.get());


        IMCDTO imcDTO= new IMCDTO();
        imcDTO.setImc(imc);
        imcDTO.setClasification(getClasification(imc));


        return imcDTO;
    }

    public IMCDTO updateHeightAndGetIMC (String username, Double height)
    {
        String user= username;
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);

        Double imc= calculateIMC(userEntity.get().getUserPersonalInfo().getWeightKg(), height);
        userEntity.get().getUserFitnessInfo().setImc(imc);
        userEntity.get().getUserPersonalInfo().setHeightCm(height);
        userRepository.save(userEntity.get());

        IMCDTO imcDTO= new IMCDTO();
        imcDTO.setImc(imc);
        imcDTO.setClasification(getClasification(imc));

        return imcDTO;
    }

    public Double calculateIMC(Double weight, Double height)
    {

        Double newHeight= height/100;
        Double imc= weight/(newHeight*newHeight);
        return imc;
    }

    public String getClasification (Double imc)
    {
        if(imc < 18.5)
        {
            return "Bajo peso";
        }
        else if(imc >= 18.5 && imc < 25)
        {
            return "Normal";
        }
        else if(imc >= 25 && imc < 30)
        {
            return "Sobrepeso";
        }
        else if(imc >= 30 && imc < 35)
        {
            return "Obesidad grado 1";
        }
        else if(imc >= 35 && imc < 40)
        {
            return "Obesidad grado 2";
        }
        else
        {
            return "Obesidad grado 3";
        }
    }
}
