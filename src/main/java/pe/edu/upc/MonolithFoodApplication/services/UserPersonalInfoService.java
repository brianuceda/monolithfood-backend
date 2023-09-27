package pe.edu.upc.MonolithFoodApplication.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.IMCDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.IMCDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.userpersonal.PersonalInfoRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.userpersonal.PersonalInfoResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserPersonalInfoService {
    // * Atributos
    // Inyección de dependencias
    private final UserRepository userRepository;
    // Log de errores y eventos
    private static final Logger logger = LoggerFactory.getLogger(UserPersonalInfoService.class);

    // * Metodos
    public ResponseDTO getInformation(String username) {
        // Verifica que el usuario exista
        Optional<UserEntity> getUser = userRepository.findByUsername(username);
        if (!getUser.isPresent()) return new ResponseDTO("Usuario no encontrado.", 404);
        // Retorna la información del usuario
        UserPersonalInfoEntity u = getUser.get().getUserPersonalInfo();
        PersonalInfoRequestDTO up = new PersonalInfoRequestDTO(u.getGender(), u.getBirthdate(), u.getHeightCm(), u.getWeightKg());
        return new PersonalInfoResponseDTO("Información recuperada correctamente.", 200, up);
    }
    // Put: Actualiza la información personal del usuario
    public ResponseDTO updateUserPeronalInfo(String username, PersonalInfoRequestDTO newUserPersonalInfo) {
        Optional<UserEntity> getUser = userRepository.findByUsername(username);
        // Si no se encuentra el usuario
        if (!getUser.isPresent()) return new ResponseDTO("Usuario no encontrado.", 404);
        // Gaurda la información personal del usuario en una variable
        UserPersonalInfoEntity userPersonalInfo = getUser.get().getUserPersonalInfo();
        // Lista para guardar los campos que se actualizaron
        List<String> updatedFields = new ArrayList<>();
        // Se actualizan los campos que el usuario haya ingresado
        if (newUserPersonalInfo.getGender() != null && (newUserPersonalInfo.getGender() == GenderEnum.F || newUserPersonalInfo.getGender() == GenderEnum.M)) {
            // Si el género es diferente al que ya se tiene, se actualiza
            if(userPersonalInfo.getGender() != newUserPersonalInfo.getGender()) {
                userPersonalInfo.setGender(newUserPersonalInfo.getGender());
                updatedFields.add("Genero");
            }
        }
        if (newUserPersonalInfo.getBirthdate() != null && !newUserPersonalInfo.getBirthdate().toString().isEmpty()) {
            // Si la fecha de nacimiento es diferente a la que ya se tiene, se actualiza
            if(!userPersonalInfo.getBirthdate().equals(newUserPersonalInfo.getBirthdate())) {
                userPersonalInfo.setBirthdate(newUserPersonalInfo.getBirthdate());
                updatedFields.add("Nacimiento");
            }
        }
        if (newUserPersonalInfo.getHeightCm() != null && newUserPersonalInfo.getHeightCm() != 0) {
            // Si la altura es diferente a la que ya se tiene, se actualiza
            if(!userPersonalInfo.getHeightCm().equals(newUserPersonalInfo.getHeightCm())) {
                userPersonalInfo.setHeightCm(newUserPersonalInfo.getHeightCm());
                updatedFields.add("Altura");
            }
        }
        if (newUserPersonalInfo.getWeightKg() != null && newUserPersonalInfo.getWeightKg() != 0) {
            // Si el peso es diferente al que ya se tiene, se actualiza
            if(!userPersonalInfo.getWeightKg().equals(newUserPersonalInfo.getWeightKg())) {
                userPersonalInfo.setWeightKg(newUserPersonalInfo.getWeightKg());
                updatedFields.add("Peso");
            }
        }
        // Si no se actualizó ningún campo, no se guarda en la BD
        try {
            if(updatedFields.size() != 0) {
                userRepository.save(getUser.get());
                logger.info("Campos actualizados para el usuario {}: {}.", username, String.join(", ", updatedFields));
            }
            else {
                logger.info("No se actualizo ningun campo para el usuario {}.", username);
                return new ResponseDTO("No se actualizó ningún campo.", 200);
            }
            // Retorna un mensaje con los campos actualizados y con los campos actualizados
            return generateUpdateMessage(updatedFields, userPersonalInfo);
        } catch (DataAccessException e) {
            logger.error("Error al guardar la informacion del usuario en la BD. ", e);
            return new ResponseDTO("Error al guardar la informacion en la BD.", 500);
        }
    }
    public IMCDTO updateWeightAndGetIMC(String username, Double weight) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);
        //actualizar el peso en la tabla user personal info
        Double imc = calculateIMC(weight, userEntity.get().getUserPersonalInfo().getHeightCm());

        userEntity.get().getUserFitnessInfo().setImc(imc);
        
        userEntity.get().getUserPersonalInfo().setWeightKg(weight);
        userRepository.save(userEntity.get());

        IMCDTO imcDTO = new IMCDTO();
        imcDTO.setImc(imc);
        imcDTO.setClasification(getClasification(imc));
        imcDTO.setNewHeight(userEntity.get().getUserPersonalInfo().getHeightCm());
        imcDTO.setNewWeight(weight);

        return imcDTO;
    }

    public IMCDTO updateHeightAndGetIMC (String username, Double height)
    {
        Optional<UserEntity> userEntity = userRepository.findByUsername(username);

        Double imc = calculateIMC(userEntity.get().getUserPersonalInfo().getWeightKg(), height);
        userEntity.get().getUserFitnessInfo().setImc(imc);
        userEntity.get().getUserPersonalInfo().setHeightCm(height);
        userRepository.save(userEntity.get());

        IMCDTO imcDTO = new IMCDTO();
        imcDTO.setImc(imc);
        imcDTO.setClasification(getClasification(imc));
        imcDTO.setNewHeight(height);
        imcDTO.setNewWeight(userEntity.get().getUserPersonalInfo().getWeightKg());

        return imcDTO;
    }

    // * Funciones auxiliares
    public Double calculateIMC(Double weight, Double height)
    {
        Double newHeight = height/100;
        Double imc = weight / (newHeight*newHeight);
        return imc;
    }

    public String getClasification (Double imc)
    {
        if(imc < 18.5)
            return "Bajo peso";
        else if(imc >= 18.5 && imc < 25)
            return "Normal";
        else if(imc >= 25 && imc < 30)
            return "Sobrepeso";
        else if(imc >= 30 && imc < 35)
            return "Obesidad grado 1";
        else if(imc >= 35 && imc < 40)
            return "Obesidad grado 2";
        else
            return "Obesidad grado 3";
    }

    // FUNCIÓN: Genera un mensaje con los campos que se actualizaron
    private ResponseDTO generateUpdateMessage(List<String> updatedFields, UserPersonalInfoEntity userPersonalInfo) {
        if (updatedFields.isEmpty()) {
            return new ResponseDTO("Ningun campo actualizado.", 400);
        } else if (updatedFields.size() == 1) {
            // Convierte la información personal del usuario a un objeto UserPersonalInfoDTO
            PersonalInfoRequestDTO dto = mapToDTO(userPersonalInfo);
            // Retorna un mensaje con el campo actualizado y con la información personal del usuario actualizada (UserPersonalInfoDTO)
            return new PersonalInfoResponseDTO(updatedFields.get(0) + " actualizado correctamente.", 200, dto);
        } else {
            // Construye un mensaje con los campos actualizados
            String lastField = updatedFields.remove(updatedFields.size() - 1);
            String message = String.join(", ", updatedFields) + " y " + lastField + " actualizados correctamente.";
            // Convierte la información personal del usuario a un objeto UserPersonalInfoDTO
            PersonalInfoRequestDTO dto = mapToDTO(userPersonalInfo);
            // Retorna un mensaje con los campos actualizados y con la información personal del usuario actualizada (UserPersonalInfoDTO)
            return new PersonalInfoResponseDTO(message, 200, dto);
        }
    }
    // FUNCIÓN: Convierte la información personal del usuario a un objeto UserPersonalInfoDTO
    private PersonalInfoRequestDTO mapToDTO(UserPersonalInfoEntity entity) {
        return new PersonalInfoRequestDTO(
            entity.getGender(),
            entity.getBirthdate(),
            entity.getHeightCm(),
            entity.getWeightKg() 
        );
    }
  
}
