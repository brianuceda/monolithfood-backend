package pe.edu.upc.MonolithFoodApplication.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.edu.upc.MonolithFoodApplication.dtos.IMCDTO;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserFitnessInfoEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.UserFitnessInfoRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserPersonalInfoRespository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
public class UserPersonalInfoService {
    @Autowired
    private UserPersonalInfoRespository imcRepository;
    @Autowired
    private UserFitnessInfoRepository fitnessRepository;
    @Autowired
    private UserRepository userRepository;

    public IMCDTO updateWeightAndGetIMC(String username, Double weight) {

        //temporal, hasta implementar el JWT
        String user= username;
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);

        //con esto se optiene todos los datos de la tabla user personal info
        Optional<UserPersonalInfoEntity> userPersonalInfoEntityOptional = imcRepository.findById(userEntity.get().getId());
        UserPersonalInfoEntity userPersonalInfoEntity = userPersonalInfoEntityOptional
            .orElseThrow(()-> new RuntimeException("User not found"));

        Optional<UserFitnessInfoEntity> userFitnessInfoEntityOptional = fitnessRepository.findById(userEntity.get().getId());
        UserFitnessInfoEntity  fitnessEntity= userFitnessInfoEntityOptional
            .orElseThrow(()-> new RuntimeException("User not found"));
    
        userPersonalInfoEntity.setWeightKg(weight);
        imcRepository.save(userPersonalInfoEntity);

        //calculo del imc
        if(userPersonalInfoEntityOptional.isPresent())
        {
            Double imc= calculateIMC(weight, userPersonalInfoEntity.getHeightCm());
            String clasification= getClasification(imc);

            IMCDTO imcDTO= new IMCDTO();
            imcDTO.setImc(imc);
            imcDTO.setClasification(clasification);

            //se asigna el imc a la tabla user fitness info
            fitnessEntity.setImc(imc);
            fitnessRepository.save(fitnessEntity);

            return imcDTO;
        }
        else
        {
            throw new RuntimeException("User not found");
        }

        
    }

    public IMCDTO updateHeightAndGetIMC (String username, Double height)
    {
        //temporal, hasta implementar el JWT
        String user= username;
        Optional<UserEntity> userEntity = userRepository.findByUsername(user);

        //con esto se optiene todos los datos de la tabla user personal info
        Optional<UserPersonalInfoEntity> userPersonalInfoEntityOptional = imcRepository.findById(userEntity.get().getId());
        UserPersonalInfoEntity userPersonalInfoEntity = userPersonalInfoEntityOptional
            .orElseThrow(()-> new RuntimeException("User not found"));

        Optional<UserFitnessInfoEntity> userFitnessInfoEntityOptional = fitnessRepository.findById(userEntity.get().getId());
        UserFitnessInfoEntity  fitnessEntity= userFitnessInfoEntityOptional
            .orElseThrow(()-> new RuntimeException("User not found"));
    
        userPersonalInfoEntity.setHeightCm(height);
        imcRepository.save(userPersonalInfoEntity);

        //calculo del imc
        if(userPersonalInfoEntityOptional.isPresent())
        {
            Double imc= calculateIMC(userPersonalInfoEntity.getWeightKg(), height);
            String clasification= getClasification(imc);

            IMCDTO imcDTO= new IMCDTO();
            imcDTO.setImc(imc);
            imcDTO.setClasification(clasification);

            //se asigna el imc a la tabla user fitness info
            fitnessEntity.setImc(imc);
            fitnessRepository.save(fitnessEntity);

            return imcDTO;
        }
        else
        {
            throw new RuntimeException("User not found");
        }
    }

    public Double calculateIMC(Double weight, Double height)
    {
        Double imc= weight/(height*height);
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
