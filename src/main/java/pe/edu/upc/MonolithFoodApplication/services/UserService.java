package pe.edu.upc.MonolithFoodApplication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.edu.upc.MonolithFoodApplication.repositories.UserRepository;
@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUser (Long id){
        userRepository.deleteById(id);
    }

}
