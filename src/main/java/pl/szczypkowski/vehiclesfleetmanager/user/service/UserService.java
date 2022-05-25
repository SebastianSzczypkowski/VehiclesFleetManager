package pl.szczypkowski.vehiclesfleetmanager.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.user.model.User;
import pl.szczypkowski.vehiclesfleetmanager.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAll()
    {
        List<User> users = new ArrayList<>();
        try{

            users=userRepository.findAll();
            return users;

        }catch (Exception e)
        {
            logger.error("Nie mozna pobrac listy użytkowników");
            return users;
        }
    }

    public User getOne(Long id)
    {
        try{

            return  userRepository.getById(id);
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
