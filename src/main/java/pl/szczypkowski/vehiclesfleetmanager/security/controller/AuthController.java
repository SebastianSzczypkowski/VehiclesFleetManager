package pl.szczypkowski.vehiclesfleetmanager.security.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.szczypkowski.vehiclesfleetmanager.security.model.JwtResponse;
import pl.szczypkowski.vehiclesfleetmanager.security.model.LoginRequest;
import pl.szczypkowski.vehiclesfleetmanager.security.model.MessageResponse;
import pl.szczypkowski.vehiclesfleetmanager.security.model.SignupRequest;
import pl.szczypkowski.vehiclesfleetmanager.security.service.JwtUtils;
import pl.szczypkowski.vehiclesfleetmanager.security.service.UserDetailsImpl;
import pl.szczypkowski.vehiclesfleetmanager.user.model.User;
import pl.szczypkowski.vehiclesfleetmanager.user.repository.UserRepository;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController( UserRepository userRepository,  JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest)
    {
        if(userRepository.existsByName(signupRequest.getUsername())){
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Username alredy taken!"));
        }
        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Email alredy in use!"));
        }
        User appUser= new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));
        userRepository.save(appUser);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
