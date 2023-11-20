package pe.edu.upc.MonolithFoodApplication.services;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.GetUserDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.PhotoDTO;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.enums.ResponseType;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // ? Metodos
    // * Naydeline: Obtener información general del usuario
    public ResponseDTO getGeneralInformation(String username) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            logger.error("Usuario no encontrado");
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = optUser.get();

        // ! MODIFICAR CUANDO SE ARREGLE EL INICIO POR OAUTH2

        String currencySymbol = "S/.";
        Double currency = 0.0;

        if (user.getWallet() != null) {
            currencySymbol = user.getWallet().getCurrencySymbol();
            currency = user.getWallet().getBalance();
        }

        return new GetUserDTO(null, 200, null,
            user.getUsername(),
            user.getProfileImg(),
            currencySymbol,
            currency
        );
    }
    // * Naydeline: Actualizar la foto de perfil del usuario
    @Transactional
    public ResponseDTO updatePhoto(String username, String photoUrl) {
        // Verifica que el usuario exista
        Optional<UserEntity> optUser = userRepository.findByUsername(username);
        if (!optUser.isPresent()) {
            return new ResponseDTO("Usuario no encontrado", HttpStatus.NOT_FOUND.value(), ResponseType.ERROR);
        }
        UserEntity user = optUser.get();
        if (photoUrl == null || photoUrl.isEmpty()) {
            return new ResponseDTO("Debes enviar una foto", HttpStatus.BAD_REQUEST.value(), ResponseType.ERROR);
        }
        if (user.getProfileImg().equals(photoUrl)) {
            return new ResponseDTO("Ya tienes esa foto", HttpStatus.BAD_REQUEST.value(), ResponseType.WARN);
        }
        user.setProfileImg(photoUrl);
        userRepository.save(user);
        return new PhotoDTO("Foto actualizada.", HttpStatus.OK.value(), ResponseType.SUCCESS, photoUrl);
    }
  
}
