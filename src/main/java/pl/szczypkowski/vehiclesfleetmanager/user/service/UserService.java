package pl.szczypkowski.vehiclesfleetmanager.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.user.model.Roles;
import pl.szczypkowski.vehiclesfleetmanager.user.model.User;
import pl.szczypkowski.vehiclesfleetmanager.user.model.UserRole;
import pl.szczypkowski.vehiclesfleetmanager.user.repository.UserRepository;
import pl.szczypkowski.vehiclesfleetmanager.user.repository.UserRoleRepo;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(UserService.class);
    private final UserRoleRepo userRoleRepo;

    public UserService(UserRepository userRepository, UserRoleRepo userRoleRepo) {
        this.userRepository = userRepository;
        this.userRoleRepo = userRoleRepo;
    }

    public List<User> getAll()
    {
        List<User> users = new ArrayList<>();
        try{

            users=userRepository.findAll();
            return users;

        }catch (Exception e)
        {
            LOGGER.error("Nie mozna pobrac listy użytkowników");
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

    public ResponseEntity<?> getAllPage(Pageable pageable) {
        try{

            return ResponseEntity.ok().body(userRepository.findAll(pageable));
        }catch (Exception e)
        {
            LOGGER.error("Nie udało się pobrać listy użytkowników wiadomość: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ToJsonString.toJsonString("Nie udało się pobrać listy użytkowników"));
        }
    }

    public ResponseEntity<?> createOrUpdate(User user) {
        try{

            //TODO zmiana hasła
            if (user.getId() == null) {

                user.setActive(true);
                user.setDateOfRegistration(new Date());
                if (user.getRoles().size() == 0) {
                    Set<UserRole> roles = new HashSet<>();
                    UserRole userR = userRoleRepo.findByName(Roles.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userR);
                    user.setRoles(roles);
                }
            }
            return ResponseEntity.ok().body(userRepository.save(user));

        }catch (Exception e) {
            LOGGER.error("Wystąpił błąd podczas tworzenia nowego uiżytkownika");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ToJsonString.toJsonString("Wystąpił błąd podczas tworzenia nowego użytkownika"));
        }
    }

    public ResponseEntity<?> deleteUser(Long id) {
        try{

            userRepository.deleteById(id);
            return ResponseEntity.ok().body(ToJsonString.toJsonString("Usunięto użytkownika"));

        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ToJsonString.toJsonString("Nie udało się usunąć użytkownika"));
        }
    }
}
