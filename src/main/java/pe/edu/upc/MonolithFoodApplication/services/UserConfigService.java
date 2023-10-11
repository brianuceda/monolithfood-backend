package pe.edu.upc.MonolithFoodApplication.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.userconfig.DarkModeDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.userconfig.NotificationsDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.userconfig.UserConfigDTO;
import pe.edu.upc.MonolithFoodApplication.entities.UserConfigEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserConfigService {
    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserConfigService.class);

    // * Naydeline: Obtener la configuración del usuario
    public ResponseDTO getConfig(String username) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        UserEntity user = userOpt.get();
        UserConfigEntity uc = user.getUserConfig();
        // Retorna la configuración del usuario
        try {
            return new UserConfigDTO(
                null, 200,
                uc.getNotifications(), uc.getLastFoodEntry(), uc.getLastWeightUpdate(), uc.getDarkMode()
            );
        } catch (DataAccessException e) {
            logger.error("Error al guardar la configuración del usuario en la BD. ", e);
            return new ResponseDTO("Error al guardar la configuración en la BD.", 500);
        }
    }
    // * Naydeline: Activar/desactivar el modo oscuro
    public ResponseDTO changeDarkMode(String username, Boolean newDarkMode) {
        return updateConfig(username, newDarkMode, "Modo oscuro");
    }
    // * Naydeline: Activar/desactivar las notificaciones
    public ResponseDTO changeNotifications(String username, Boolean newNotifications) {
        return updateConfig(username, newNotifications, "Notificaciones");
    }

    // ? Funciones auxiliares
    // Función: Actualiza la configuración del usuario
    @Transactional
    private ResponseDTO updateConfig(String username, Boolean newValue, String fieldName) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (!userOpt.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        // Guarda la configuración del usuario en una variable por referencia (si se modifica la variable, se modifica el objeto original)
        UserConfigEntity userConfig = userOpt.get().getUserConfig();
        // Compara el nuevo valor con el valor actual de la configuración del usuario
        if (fieldName.equals("Notificaciones") && newValue != null && !newValue.equals(userConfig.getNotifications())) {
            userConfig.setNotifications(newValue);
        } else if (fieldName.equals("Modo oscuro") && newValue != null && !newValue.equals(userConfig.getDarkMode())) {
            userConfig.setDarkMode(newValue);
        } else {
            logger.info("No se actualizo ningun campo para el usuario {}.", username);
            return new ResponseDTO("No se actualizó ningún campo.", 200);
        }
        // Guarda la configuración del usuario en la BD
        try {
            userRepository.save(userOpt.get());
            logger.info("{} actualizado para el usuario {}. Nuevo valor: {}.", fieldName, username, newValue);
            return generateUpdateMessage(userConfig, fieldName);
        } catch (DataAccessException e) {
            logger.error("Error al guardar la configuración del usuario en la BD. ", e);
            return new ResponseDTO("Error al guardar la configuración en la BD.", 500);
        }
    }
    // FUNCIÓN: Genera un mensaje de respuesta con los campos actualizados
    private ResponseDTO generateUpdateMessage(UserConfigEntity userConfig, String fieldName) {
        if (fieldName.equals("Notificaciones")) {
            return new NotificationsDTO("Notificaciones actualizado correctamente.", 200, userConfig.getNotifications());
        } else if (fieldName.equals("Modo oscuro")) {
            return new DarkModeDTO("Modo oscuro actualizado correctamente.", 200, userConfig.getDarkMode());
        } else {
            return new ResponseDTO("Configuración actualizada.", 200);
        }
    }
}
