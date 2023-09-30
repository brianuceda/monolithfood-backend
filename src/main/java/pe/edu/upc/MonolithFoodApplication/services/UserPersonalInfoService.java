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
import pe.edu.upc.MonolithFoodApplication.dtos.userpersonal.PersonalInfoRequestDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.userpersonal.PersonalInfoResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserFitnessInfoEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserPersonalInfoService {
    // ? Atributos
    // Inyección de dependencias
    private final UserRepository userRepository;
    // Log de errores y eventos
    private static final Logger logger = LoggerFactory.getLogger(UserPersonalInfoService.class);

    // ? Metodos
    // * Naydeline: Obtener información personal del usuario
    public ResponseDTO getInformation(String username) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Retorna la información personal del usuario
        UserPersonalInfoEntity upi = optUser.get().getUserPersonalInfo();
        PersonalInfoRequestDTO responseUpi = new PersonalInfoRequestDTO(upi.getGender(), upi.getBirthdate());
        return new PersonalInfoResponseDTO(null, 200, responseUpi);
    }
    // * Naydeline: Actualizar información personal del usuario
    public ResponseDTO updateUserPeronalInfo(String username, PersonalInfoRequestDTO newUserPersonalInfo) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Gaurda la información personal del usuario en una variable
        UserPersonalInfoEntity upi = optUser.get().getUserPersonalInfo();
        // Lista para guardar los campos que se actualizaron
        List<String> updatedFields = new ArrayList<>();
        // Se actualizan los campos que el usuario haya ingresado
        if (newUserPersonalInfo.getGender() != null && (newUserPersonalInfo.getGender() == GenderEnum.F || newUserPersonalInfo.getGender() == GenderEnum.M)) {
            // Si el género es diferente al que ya se tiene, se actualiza
            if(upi.getGender() != newUserPersonalInfo.getGender()) {
                upi.setGender(newUserPersonalInfo.getGender());
                updatedFields.add("Genero");
            }
        }
        if (newUserPersonalInfo.getBirthdate() != null && !newUserPersonalInfo.getBirthdate().toString().isEmpty()) {
            // Si la fecha de nacimiento es diferente a la que ya se tiene, se actualiza
            if(!upi.getBirthdate().equals(newUserPersonalInfo.getBirthdate())) {
                upi.setBirthdate(newUserPersonalInfo.getBirthdate());
                updatedFields.add("Nacimiento");
            }
        }
        // Si no se actualizó ningún campo, no se guarda en la BD
        try {
            if(updatedFields.size() != 0) {
                userRepository.save(optUser.get());
                logger.info("Campos actualizados para el usuario {}: {}.", username, String.join(", ", updatedFields));
            }
            else {
                logger.info("No se actualizo ningun campo para el usuario {}.", username);
                return new ResponseDTO("No se actualizó ningún campo.", 200);
            }
            // Retorna un mensaje con los campos actualizados y con los campos actualizados
            return generateUpdateMessage(updatedFields, upi);
        } catch (DataAccessException e) {
            logger.error("Error al guardar la informacion del usuario en la BD. ", e);
            return new ResponseDTO("Error al guardar la informacion en la BD.", 500);
        }
    }
    // * Willy: Actualiza la altura del usuario y retorna el IMC y la clasificación del IMC
    public ResponseDTO updateHeightAndGetIMC(String username, Double heightCm) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        UserEntity user = optUser.get();
        // Obtiene la información personal y fitness del usuario
        UserPersonalInfoEntity userUpi = user.getUserPersonalInfo();
        UserFitnessInfoEntity userUfi = user.getUserFitnessInfo();
        // Actualizar el peso y el IMC de la información fitness del usuario
        Double imc = calculateIMC(userUpi.getWeightKg(), heightCm);
        userUfi.setImc(imc);
        userUpi.setHeightCm(heightCm);
        userRepository.save(user);
        // Retorna el nuevo IMC calculado y la clasificación del IMC
        return new IMCDTO("Altura e IMC actualizados correctamente.", 200, imc, getClasification(imc), heightCm, userUpi.getWeightKg());
    }
    // * Willy: Actualiza el peso del usuario y retorna el IMC y la clasificación del IMC
    public ResponseDTO updateWeightAndGetIMC(String username, Double weightKg) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        UserEntity user = optUser.get();
        // Obtiene la información personal y fitness del usuario
        UserPersonalInfoEntity userUpi = user.getUserPersonalInfo();
        UserFitnessInfoEntity userUfi = user.getUserFitnessInfo();
        // Actualizar el peso y el IMC de la información fitness del usuario
        Double imc = calculateIMC(weightKg, userUpi.getHeightCm());
        userUfi.setImc(imc);
        userUpi.setWeightKg(weightKg);
        userRepository.save(user);
        // Retorna el nuevo IMC calculado y la clasificación del IMC
        return new IMCDTO("Peso e IMC actualizados correctamente.", 200, imc, getClasification(imc), userUpi.getHeightCm(), weightKg);
    }

    // ? Funciones auxiliares
    // FUNCIÓN: Calcula el IMC
    public static Double calculateIMC(Double weight, Double height)
    {
        Double newHeight = height / 100;
        Double imc = weight / (newHeight*newHeight);
        return imc;
    }
    // FUNCIÓN: Classifica el IMC
    public String getClasification(Double imc)
    {
        if(imc < 18.5) return "Bajo peso";
        else if(imc >= 18.5 && imc < 25) return "Normal";
        else if(imc >= 25 && imc < 30) return "Sobrepeso";
        else if(imc >= 30 && imc < 35) return "Obesidad grado 1";
        else if(imc >= 35 && imc < 40) return "Obesidad grado 2";
        else return "Obesidad grado 3";
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
            entity.getBirthdate()
        );
    }

}
