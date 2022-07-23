package pl.szczypkowski.vehiclesfleetmanager.user.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szczypkowski.vehiclesfleetmanager.user.model.User;
import pl.szczypkowski.vehiclesfleetmanager.user.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/get-all-page")
    public ResponseEntity<?> getAllPage(Pageable pageable)
    {
        return userService.getAllPage(pageable);
    }

    @PostMapping("/create-update-user")
    public ResponseEntity<?> createOrUpdateUser(@RequestBody User user)
    {
        return userService.createOrUpdate(user);
    }

    @DeleteMapping("/deleted-user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id")Long id)
    {
        return userService.deleteUser(id);
    }



}
