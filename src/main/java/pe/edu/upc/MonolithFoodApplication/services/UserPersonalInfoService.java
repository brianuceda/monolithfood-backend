package pe.edu.upc.MonolithFoodApplication.services;

import java.sql.Timestamp;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.IMCDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.PersonalInfoDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.UpdateHeightWeightDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.UpdatePersonalInfoDTO;
import pe.edu.upc.MonolithFoodApplication.entities.GenderEnum;
import pe.edu.upc.MonolithFoodApplication.entities.UserConfigEntity;
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
        if (upi == null)
            return new ResponseDTO("Primero debes registrar tu información personal.", 404);
        // Vuelve a obtener los valores actualizados del usuario
        upi = optUser.get().getUserPersonalInfo();
        return new PersonalInfoDTO(null, 200, upi.getGender(), upi.getBorndate(), upi.getHeightCm(), upi.getWeightKg());
    }
    // * Naydeline: Registrar nueva información de usuario
    @Transactional
    public ResponseDTO setUserPersonalInfo(String username, PersonalInfoDTO userPersonalInfo) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        if (userPersonalInfo.getWeightKg() == null && userPersonalInfo.getWeightKg() < 0)
            return new ResponseDTO("El peso debe ser mayor a 0.", 400);
        // Gaurda la información personal del usuario en una variable
        UserEntity user = optUser.get();
        UserConfigEntity uc = user.getUserConfig();
        UserPersonalInfoEntity upi = user.getUserPersonalInfo();
        if (uc == null)
            return new ResponseDTO("Primero debes registrar tu configuración.", 400);
        if (upi == null)
            upi = new UserPersonalInfoEntity();
        else
            return new ResponseDTO("Ya tienes información personal registrada.", 400);
        // Se actualizan los campos que el usuario haya ingresado
        upi.setGender(userPersonalInfo.getGender());
        upi.setBorndate(userPersonalInfo.getBorndate());
        upi.setHeightCm(userPersonalInfo.getHeightCm());
        upi.setWeightKg(userPersonalInfo.getWeightKg());
        uc.setLastWeightUpdate(new Timestamp(System.currentTimeMillis()));
        // Se guarda la información personal del usuario en la BD
        try {
            // upi.setUser(user);
            user.setUserPersonalInfo(upi);
            user.setUserConfig(uc);
            userRepository.save(user);
            logger.info("Información personal del usuario {} guardada correctamente.", username);
            // Retorna un mensaje de éxito
            return new ResponseDTO("Información personal guardada correctamente.", 200);
        } catch (DataAccessException e) {
            logger.error("Error al guardar la informacion del usuario" + username + " en la BD. ", e);
            return new ResponseDTO("Error al guardar la informacion en la BD.", 500);
        }
    }
    // * Naydeline: Actualizar información personal del usuario
    @Transactional
    public ResponseDTO updateUserPersonalInfo(String username, UpdatePersonalInfoDTO newUserPersonalInfo) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Gaurda la información personal del usuario en una variable
        UserEntity user = optUser.get();
        UserPersonalInfoEntity upi = user.getUserPersonalInfo();
        if (upi == null)
            return new ResponseDTO("Primero debes registrar tu información personal.", 400);
        // Se actualizan los campos que el usuario haya ingresado
        if (newUserPersonalInfo.getGender() != null && (newUserPersonalInfo.getGender() == GenderEnum.F || newUserPersonalInfo.getGender() == GenderEnum.M))
            upi.setGender(newUserPersonalInfo.getGender());
        if (newUserPersonalInfo.getBorndate() != null && !newUserPersonalInfo.getBorndate().toString().isEmpty())
            upi.setBorndate(newUserPersonalInfo.getBorndate());
        try {
            user.setUserPersonalInfo(upi);
            userRepository.save(user);
            logger.info("Información personal del usuario {} actualizada correctamente.", username);
            PersonalInfoDTO dto = mapToPersonalInfoDTO(upi);
            return new PersonalInfoDTO("Datos actualizados correctamente.", 200, dto.getGender(), dto.getBorndate(), dto.getHeightCm(), dto.getWeightKg());
        } catch (DataAccessException e) {
            logger.error("Error al guardar la informacion del usuario en la BD. ", e);
            return new ResponseDTO("Error al guardar la informacion en la BD.", 500);
        }
    }
    // * Willy: Actualiza la altura o el peso del usuario y retorna el IMC y la clasificación del IMC
    @Transactional
    public ResponseDTO updateHeightWeightGetIMC(String username, UpdateHeightWeightDTO uhwDTO) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        UserEntity user = optUser.get();
        UserConfigEntity uc = user.getUserConfig();
        UserPersonalInfoEntity upi = user.getUserPersonalInfo();
        UserFitnessInfoEntity ufi = user.getUserFitnessInfo();
        if (uc == null)
            return new ResponseDTO("Primero debes registrar tu configuración.", 400);
        if (upi == null)
            return new ResponseDTO("Primero debes registrar tu información personal.", 400);
        if (ufi == null)
            ufi = new UserFitnessInfoEntity();
        Double minWeight = 0.0, minHeight = 5.0;
        if (uhwDTO.getHeightCm() == null && uhwDTO.getWeightKg() == null)
            return new ResponseDTO("Debes ingresar al menos un valor.", 400);
        // Verificación de la altura y el peso
        if ((uhwDTO.getHeightCm() != null && uhwDTO.getHeightCm() <= minHeight) ||
            (uhwDTO.getWeightKg() != null && uhwDTO.getWeightKg() <= minWeight))
            return new ResponseDTO("La altura y el peso deben ser mayores a los valores mínimos.", 400);
        // Actualizar solo el valor proporcionado
        if (uhwDTO.getHeightCm() != null)
            upi.setHeightCm(uhwDTO.getHeightCm());
        if (uhwDTO.getWeightKg() != null)
            upi.setWeightKg(uhwDTO.getWeightKg());
        // Cálculo del IMC
        Double imc = null;
        if (upi.getHeightCm() != null && upi.getWeightKg() != null) {
            imc = calculateIMC(upi.getWeightKg(), upi.getHeightCm());
            ufi.setImc(imc);
        }
        userRepository.save(user);
        return new IMCDTO("Datos actualizados correctamente.", 200, imc, imc != null ? getClasification(imc) : null, upi.getHeightCm(), upi.getWeightKg());
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
    // FUNCIÓN: Convierte la información personal del usuario a un objeto UserPersonalInfoDTO
    private PersonalInfoDTO mapToPersonalInfoDTO(UserPersonalInfoEntity entity) {
        return new PersonalInfoDTO(
            entity.getGender(),
            entity.getBorndate(),
            entity.getHeightCm(),
            entity.getWeightKg()
        );
    }

}
