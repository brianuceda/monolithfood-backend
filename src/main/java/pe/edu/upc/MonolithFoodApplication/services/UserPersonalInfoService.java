package pe.edu.upc.MonolithFoodApplication.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import pe.edu.upc.MonolithFoodApplication.dtos.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.userpersonal.PersonalInfoRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.userpersonal.PersonalInfoResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
public class UserPersonalInfoService {

    @Autowired
    private UserRepository userRepository;

    // Log para mostrar errores en el archivo user.log
    private static final Logger logger = LoggerFactory.getLogger(UserPersonalInfoService.class);

    public ResponseDTO updateUserPeronalInfo(String username, PersonalInfoRequestDTO newUserPersonalInfo) {
        Optional<UserEntity> getUser = userRepository.findByUsername(username);
        // Si no se encuentra el usuario en la base de datos, se retorna un mensaje
        if (!getUser.isPresent()) return new ResponseDTO("Usuario no encontrado.", 404);
        // Gaurda la información personal del usuario en una variable por referencia (si se modifica la variable, se modifica el objeto original)
        UserPersonalInfoEntity userPersonalInfo = getUser.get().getUserPersonalInfo();
        // Crea una lista para guardar los campos que se actualizaron
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

    private PersonalInfoRequestDTO mapToDTO(UserPersonalInfoEntity entity) {
        return new PersonalInfoRequestDTO(
            entity.getGender(),
            entity.getBirthdate(),
            entity.getHeightCm(),
            entity.getWeightKg() 
        );
    }
}
