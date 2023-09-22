package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pe.edu.upc.MonolithFoodApplication.dtos.general.ResponseDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.general.SimpleObjectDTO;
import pe.edu.upc.MonolithFoodApplication.dtos.user.ObjectivesResponseDTO;
import pe.edu.upc.MonolithFoodApplication.entities.ObjectiveEntity;
import pe.edu.upc.MonolithFoodApplication.entities.UserEntity;
import pe.edu.upc.MonolithFoodApplication.repositories.ObjectiveRepository;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectiveRepository objectiveRepository;


    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    public ResponseDTO getObjectives(String username) {
        Optional<UserEntity> user = userRepository.findByUsername(username);
        if(!user.isPresent()) {

            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        List<ObjectiveEntity> objectives = user.get().getObjectives();
        List<SimpleObjectDTO> objectiveDTOs = objectives.stream()
            .map(obj -> new SimpleObjectDTO(obj.getName(), obj.getInformation()))
            .collect(Collectors.toList());
        return new ObjectivesResponseDTO("Objetivos recuperados correctamente.", 200, objectiveDTOs);
    }

    public ResponseDTO setUserObjectives(String username, List<String> objectives) {
        Optional<UserEntity> getUser = userRepository.findByUsername(username);
        if(!getUser.isPresent()) {
            logger.error("Usuario no encontrado.");
            return new ResponseDTO("Usuario no encontrado.", 404);
        }
        List<ObjectiveEntity> allObjectives = objectiveRepository.findAll();
        // Validar que todos los objetivos enviados existen en la base de datos
        for(String objective : objectives) {
            boolean exists = allObjectives.stream().anyMatch(o -> o.getName().equals(objective));
            if(!exists) {
                logger.error("No se encontro uno de los objetivos ingresados.");
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
            .map(objectiveEntity -> new SimpleObjectDTO(
                objectiveEntity.getName(),
                objectiveEntity.getInformation())
            )
            .collect(Collectors.toList());
        return new ObjectivesResponseDTO("Objetivos guardados", 200, savedObjectives);
    }
}
