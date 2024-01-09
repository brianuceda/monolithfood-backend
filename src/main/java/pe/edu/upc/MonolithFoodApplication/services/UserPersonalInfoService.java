package pe.edu.upc.MonolithFoodApplication.services;

import java.sql.Timestamp;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.auth.AuthResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.fitnessinfo.IMCDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.objectives.ProgressWeightDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.GetPersonalInfoDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.PutHeightWeightDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.PutPersonalInfoDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.SetInformationDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.external.LocationDTO;
import pe.edu.upc.MonolithFoodApplication.entities.UserConfigEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserFitnessInfoEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserPersonalInfoEntity;
import pe.edu.upc.MonolithFoodApplication.enums.GenderEnum;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserPersonalInfoService {
    private final UserRepository userRepository;
    private final ExternalApisService externalApisService;
    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(UserPersonalInfoService.class);

    // * Naydeline: Registrar nueva información de usuario
    @Transactional
    public ResponseDTO setUserPersonalInfo(String username, SetInformationDTO request) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado");
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = optUser.get();
        UserConfigEntity uc = user.getUserConfig();
        UserPersonalInfoEntity upi = user.getUserPersonalInfo();
        if (request.getGender() == null || request.getBorndate() == null || request.getWeightKg() == null || request.getHeightCm() == null || request.getTargetWeightKg() == null || request.getTargetDate() == null)
            return new ResponseDTO("Completa todos los campos.", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        if (upi == null)
            upi = new UserPersonalInfoEntity();
        else
            return new ResponseDTO("Ya has registrado información", HttpStatus.BAD_REQUEST.value(), ResponseType.ERROR);
        UserFitnessInfoEntity ufi = new UserFitnessInfoEntity();
        try {
            LocationDTO locationDTO = externalApisService.getLocationFromIp(user.getIpAddress());
            upi.setCity(locationDTO.getCity());
            upi.setCountry(locationDTO.getCountry());
        } catch (Exception e) {
            upi.setCity("No encontrado");
            upi.setCountry("No encontrado");
        }
        upi.setGender(request.getGender());
        upi.setBorndate(request.getBorndate());
        upi.setWeightKg(request.getWeightKg());
        upi.setStartWeightKg(request.getWeightKg());
        upi.setHeightCm(request.getHeightCm());
        uc.setLastWeightUpdate(new Timestamp(System.currentTimeMillis()));
        ufi.setTargetWeightKg(request.getTargetWeightKg());
        ufi.setTargetDate(request.getTargetDate());

        upi.setCity("Lima");
        upi.setCountry("Perú");

        try {
            user.setUserConfig(uc);
            user.setUserPersonalInfo(upi);
            user.setUserFitnessInfo(ufi);
            userRepository.save(user);
            // Token
            String profileStage = jwtService.determineProfileStage(user);
            String token = jwtService.genToken(user, profileStage);
            return new AuthResponseDTO("Información guardada", HttpStatus.OK.value(), ResponseType.SUCCESS, token, null);
        } catch (DataAccessException e) {
            return new ResponseDTO("Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseType.ERROR);
        }
    }
    // * Naydeline: Obtener información personal del usuario
    public ResponseDTO getInformation(String username) {
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = optUser.get();
        UserPersonalInfoEntity upi = user.getUserPersonalInfo();
        if (upi == null || upi.getWeightKg() == null || upi.getHeightCm() == null)
            return new ResponseDTO("Debes registrar tu información personal", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);

        // ! MODIFICAR CUANDO SE ARREGLE EL INICIO POR OAUTH2

        String currencySymbol = "S/.";
        Double currency = 0.0;

        if (user.getWallet() != null) {
            currencySymbol = user.getWallet().getCurrencySymbol();
            currency = user.getWallet().getBalance();
        }

        GetPersonalInfoDTO dto = GetPersonalInfoDTO.builder()
            .message(null)
            .statusCode(200)
            .username(user.getUsername())
            .profileImg(user.getProfileImg())
            .email(user.getEmail())
            .names(user.getNames())
            .gender(upi.getGender())
            .borndate(upi.getBorndate())
            .location(upi.getCity() + ", " + upi.getCountry())
            .weightKg(upi.getWeightKg())
            .heightCm(upi.getHeightCm())
            .imc(String.format("%.2f", calculateIMC(upi.getWeightKg(), upi.getHeightCm())) + " % | " + getClasification(calculateIMC(upi.getWeightKg(), upi.getHeightCm())))
            .currencySymbol(currencySymbol)
            .currency(currency)
            .build();
        return dto;
    }
    // * Naydeline: Actualizar información personal del usuario
    @Transactional
    public ResponseDTO updateUserPersonalInfo(String username, PutPersonalInfoDTO newUserPersonalInfo) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        // Gaurda la información personal del usuario en una variable
        UserEntity user = optUser.get();
        UserPersonalInfoEntity upi = user.getUserPersonalInfo();
        if (upi == null)
            return new ResponseDTO("Debes registrar tu información personal", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        // Se actualizan los campos que el usuario haya ingresado
        if (newUserPersonalInfo.getNames() != null && !newUserPersonalInfo.getNames().isEmpty())
            user.setNames(newUserPersonalInfo.getNames());
        if (newUserPersonalInfo.getGender() != null && (newUserPersonalInfo.getGender() == GenderEnum.F || newUserPersonalInfo.getGender() == GenderEnum.M))
            upi.setGender(newUserPersonalInfo.getGender());
        if (newUserPersonalInfo.getBorndate() != null && !newUserPersonalInfo.getBorndate().toString().isEmpty())
            upi.setBorndate(newUserPersonalInfo.getBorndate());
        try {
            user.setUserPersonalInfo(upi);
            userRepository.save(user);
            logger.info("Información personal del usuario {} actualizada correctamente.", username);
            return new ResponseDTO("Información actualizada", HttpStatus.OK.value(), ResponseType.SUCCESS);
        } catch (DataAccessException e) {
            logger.error("Error al guardar la informacion del usuario en la BD. ", e);
            return new ResponseDTO("Ocurrío un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseType.ERROR);
        }
    }
    // * Willy: Actualiza la altura o el peso del usuario y retorna el IMC y la clasificación del IMC
    @Transactional
    public ResponseDTO updateHeightWeightGetIMC(String username, PutHeightWeightDTO uhwDTO) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = optUser.get();
        UserConfigEntity uc = user.getUserConfig();
        UserPersonalInfoEntity upi = user.getUserPersonalInfo();
        UserFitnessInfoEntity ufi = user.getUserFitnessInfo();
        if (uc == null)
            return new ResponseDTO("Contacta a un asistente", HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseType.ERROR);
        if (upi == null)
            return new ResponseDTO("Debes registrar tu información personal", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        if (ufi == null)
            ufi = new UserFitnessInfoEntity();
        Double minWeight = 0.0, minHeight = 5.0;
        if (uhwDTO.getHeightCm() == null && uhwDTO.getWeightKg() == null)
            return new ResponseDTO("Ingresa al menos un valor", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        // Verificación de la altura y el peso
        if ((uhwDTO.getHeightCm() != null && uhwDTO.getHeightCm() <= minHeight) ||
            (uhwDTO.getWeightKg() != null && uhwDTO.getWeightKg() <= minWeight))
            return new ResponseDTO("Valores inválidos", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
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
        return new IMCDTO("Información actualizada", HttpStatus.OK.value(), ResponseType.SUCCESS, imc, (imc != null ? getClasification(imc) : null), upi.getHeightCm(), upi.getWeightKg());
    }

    // Willy: Calcular el progreso del peso del usuario
    @Transactional
    public ResponseDTO progressWeight(String username) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = optUser.get();
        UserPersonalInfoEntity upi = user.getUserPersonalInfo();
        UserFitnessInfoEntity ufi = user.getUserFitnessInfo();
        Double porcentaje =null;
        //Calcular el porcentaje de progreso
        if (ufi ==null)
            return new ResponseDTO("Debes registrar tu información personal", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        if (ufi.getTargetWeightKg()!=null && upi.getStartWeightKg()!=null && upi.getWeightKg()!=null)
            porcentaje = calculateProgress(upi.getStartWeightKg(),upi.getWeightKg(), ufi.getTargetWeightKg());
        ProgressWeightDTO responseDTO = new ProgressWeightDTO(null, 200, null,
            ufi.getTargetDate(), porcentaje, upi.getWeightKg(), upi.getStartWeightKg(),
            ufi.getTargetWeightKg());
        responseDTO.minusHours(5);
        return responseDTO;
    }

    // ? Funciones auxiliares
    // Calcula el IMC
    public static Double calculateIMC(Double weight, Double height)
    {
        Double newHeight = height / 100;
        Double imc = weight / (newHeight*newHeight);
        return imc;
    }
    // Classifica el IMC
    public String getClasification(Double imc)
    {
        if(imc < 18.5) return "Bajo peso";
        else if(imc >= 18.5 && imc < 25) return "Normal";
        else if(imc >= 25 && imc < 30) return "Sobrepeso";
        else if(imc >= 30 && imc < 35) return "Obesidad grado 1";
        else if(imc >= 35 && imc < 40) return "Obesidad grado 2";
        else return "Obesidad grado 3";
    }
     // Calular el porcentaje de progreso del peso, (target-start/actual-start)*100
    public Double calculateProgress(Double start, Double actual, Double target) {
        Double diferencia = target - start;
        Double diferenciaActual = actual - start;
        Double porcentaje = (diferenciaActual / diferencia) * 100;
        return porcentaje;
    }

}
