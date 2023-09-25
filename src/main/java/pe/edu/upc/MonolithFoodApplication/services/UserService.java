package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.SimpleObjectDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.InfoDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.ObjectivesResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.PhotoDTO;
import pe.edu.upc.MonolithFoodApplication.entities.ObjectiveEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.ObjectiveRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    // * Atributos
    // Inyección de dependencias
    private final UserRepository userRepository;
    private final ObjectiveRepository objectiveRepository;

    // Log de errores y eventos
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // * Informacion
    public ResponseDTO getInformation(String username) {
        // Verifica que el usuario exista
        Optional<UserEntity> getUser = userRepository.findByUsername(username);
        if (!getUser.isPresent()) return new ResponseDTO("Usuario no encontrado.", 404);
        // Retorna la información del usuario
        UserEntity u = getUser.get();
        return new InfoDTO("Información recuperada correctamente.", 200,
            u.getUsername(),
            u.getEmail(),
            u.getNames(),
            u.getSurnames(),
            u.getProfileImg()
        );
    }
    public ResponseDTO updatePhoto(String username, String photoUrl) {
        // Verifica que el usuario exista
        Optional<UserEntity> getUser = userRepository.findByUsername(username);
        if (!getUser.isPresent()) return new ResponseDTO("Usuario no encontrado.", 404);
        // Actualiza y guarda la foto de perfil del usuario
        getUser.get().setProfileImg(photoUrl);
        userRepository.save(getUser.get());
        return new PhotoDTO("Foto actualizada correctamente", 200, photoUrl);
    }
    // * Objetivos
    // Obtener todos los usuarios
    public ResponseDTO getAllObjectives() {
        List<ObjectiveEntity> objectives = objectiveRepository.findAll();
        if (objectives.isEmpty()) {
            logger.error("No se encontraron objetivos.");
            return new ResponseDTO("No se encontraron objetivos.", 404);
        }
        List<SimpleObjectDTO> objectiveDTOs = objectives.stream()
                .map(obj -> new SimpleObjectDTO(obj.getName(), obj.getInformation()))
                .collect(Collectors.toList());
        return new ObjectivesResponseDTO("Objetivos recuperados correctamente.", 200, objectiveDTOs);
    }
    // Obtener los objetivos de un usuario
    public ResponseDTO getUserObjectives(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        List<ObjectiveEntity> objectives = user.get().getObjectives();
        List<SimpleObjectDTO> objectiveDTOs = objectives.stream()
                .map(obj -> new SimpleObjectDTO(obj.getName(), obj.getInformation()))
                .collect(Collectors.toList());
        return new ObjectivesResponseDTO("Objetivos recuperados correctamente.", 200, objectiveDTOs);
    }
    // Guardar o actualizar los objetivos de un usuario
    public ResponseDTO setUserObjectives(String username, List<String> objectives) {
        // Verificar si la lista de objetivos está vacía
        if (objectives.isEmpty()) {
            logger.error("No se seleccionó ningún objetivo.");
            return new ResponseDTO("Debes seleccionar al menos un objetivo.", 400);
        }
        Optional<UserEntity> getUser = userRepository.findByUsername(username);
        if (!getUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        List<ObjectiveEntity> allObjectives = objectiveRepository.findAll();
        // Validar que todos los objetivos enviados existen en la base de datos
        for (String objective : objectives) {
            boolean exists = allObjectives.stream().anyMatch(o -> o.getName().equals(objective));
            if (!exists) {
                logger.error("No se encontró uno de los objetivos ingresados.");
                return new ResponseDTO("No se encontro uno de los objetivos ingresados.", 404);
            }
        }
        // Filtrar los objetivos que coinciden con los enviados por el usuario
        List<ObjectiveEntity> newUserObjectives = allObjectives.stream()
                .filter(o -> objectives.contains(o.getName()))
                .collect(Collectors.toList());
        // Guardar los objetivos en el usuario
        getUser.get().setObjectives(newUserObjectives);
        userRepository.save(getUser.get());
        // Convertir la lista de ObjectiveEntity a SimpleObjectDTO
        List<SimpleObjectDTO> savedObjectives = newUserObjectives.stream()
                .map(entity -> new SimpleObjectDTO(entity.getName(), entity.getInformation()))
                .collect(Collectors.toList());
        return new ObjectivesResponseDTO("Objetivos guardados", 200, savedObjectives);
    }
}
