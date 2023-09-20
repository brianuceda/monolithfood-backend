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
import pe.edu.upc.MonolithFoodApplication.dtos.UserPersonalInfoDTO;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
public class UserPersonalInfoService {

    @Autowired
    private UserRepository userRepository;

    // Log para mostrar errores en la consola
    private static final Logger logger = LoggerFactory.getLogger(UserPersonalInfoService.class);

    public ResponseDTO updateUserPeronalInfo(String username, UserPersonalInfoDTO newUserPersonalInfo) {
        Optional<UserEntity> getUser = userRepository.findByUsername(username);
        // Si no se encuentra el usuario, se retorna un mensaje
        if (!getUser.isPresent()) return new ResponseDTO("Usuario no encontrado.", 404);
        // Gaurda la información personal del usuario en una variable por referencia (si se modifica la variable, se modifica el objeto original)
        UserPersonalInfoEntity userPersonalInfo = getUser.get().getUserPersonalInfo();
        // Crea una lista para guardar los campos que se actualizaron
        List<String> updatedFields = new ArrayList<>();
        // Se actualizan los campos que el usuario haya ingresado
        // Se actualizan los campos que el usuario haya ingresado
        if (newUserPersonalInfo.getGender() != null && (newUserPersonalInfo.getGender() == GenderEnum.F || newUserPersonalInfo.getGender() == GenderEnum.M)) {
            userPersonalInfo.setGender(newUserPersonalInfo.getGender());
            updatedFields.add("Genero");
        }
        if (newUserPersonalInfo.getBirthdate() != null && !newUserPersonalInfo.getBirthdate().toString().isEmpty()) {
            userPersonalInfo.setBirthdate(newUserPersonalInfo.getBirthdate());
            updatedFields.add("Nacimiento");
        }
        if (newUserPersonalInfo.getHeightCm() != null && newUserPersonalInfo.getHeightCm() != 0) {
            userPersonalInfo.setHeightCm(newUserPersonalInfo.getHeightCm());
            updatedFields.add("Altura");
        }
        if (newUserPersonalInfo.getWeightKg() != null && newUserPersonalInfo.getWeightKg() != 0) {
            userPersonalInfo.setWeightKg(newUserPersonalInfo.getWeightKg());
            updatedFields.add("Peso");
        }

        // Si no se actualizó ningún campo, no se guarda en la BD
        try {
            if(updatedFields.size() != 0) {
                userRepository.save(getUser.get());
            }
            logger.info("Campos actualizados para el usuario {}: {}", username, String.join(", ", updatedFields));
            return getMessage(updatedFields);
        } catch (DataAccessException e) {
            logger.error("Error al guardar la informacion del usuario en la BD. ", e);
            return new ResponseDTO("Error al guardar la informacion.", 500);
        }
    }

    // FUNCIÓN: Genera un mensaje con los campos que se actualizaron
    private ResponseDTO getMessage(List<String> updatedFields) {
        if (updatedFields.isEmpty()) {
            return new ResponseDTO("Ningun campo actualizado.", 400);
        } else if (updatedFields.size() == 1) {
            return new ResponseDTO(updatedFields.get(0) + " actualizado correctamente.", 200);
        } else {
            String lastField = updatedFields.remove(updatedFields.size() - 1);
            String message = String.join(", ", updatedFields) + " y " + lastField + " actualizados correctamente.";
            return new ResponseDTO(message, 200);
        }
    }
}
