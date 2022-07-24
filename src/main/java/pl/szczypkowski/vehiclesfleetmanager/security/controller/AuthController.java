package pl.szczypkowski.vehiclesfleetmanager.security.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.szczypkowski.vehiclesfleetmanager.security.model.JwtResponse;
import pl.szczypkowski.vehiclesfleetmanager.security.model.LoginRequest;
import pl.szczypkowski.vehiclesfleetmanager.security.model.MessageResponse;
import pl.szczypkowski.vehiclesfleetmanager.security.model.SignupRequest;
import pl.szczypkowski.vehiclesfleetmanager.security.service.JwtUtils;
import pl.szczypkowski.vehiclesfleetmanager.security.service.UserDetailsImpl;
import pl.szczypkowski.vehiclesfleetmanager.user.model.Roles;
import pl.szczypkowski.vehiclesfleetmanager.user.model.User;
import pl.szczypkowski.vehiclesfleetmanager.user.model.UserRole;
import pl.szczypkowski.vehiclesfleetmanager.user.repository.UserRepository;
import pl.szczypkowski.vehiclesfleetmanager.user.repository.UserRoleRepo;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/auth")
public class AuthController {



    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserRoleRepo userRoleRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, UserRoleRepo userRoleRepo, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userRoleRepo = userRoleRepo;
        this.passwordEncoder = passwordEncoder;
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
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest)
    {
        if(userRepository.existsByName(signupRequest.getUsername())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ToJsonString.toJsonString("Nazwa użytkownika zajęta!"));
        }
        if(userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ToJsonString.toJsonString("Użytkownika o takim emailu już istnieje !"));
        }
        User user= new User(signupRequest.getUsername(), signupRequest.getEmail(), passwordEncoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRole();
        Set<UserRole> roles= new HashSet<>();
        if(strRoles==null)
        {
            UserRole userRole= userRoleRepo.findByName(Roles.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        UserRole adminRole = userRoleRepo.findByName(Roles.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "user":
                        UserRole userRole = userRoleRepo.findByName(Roles.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                    default:
                        UserRole userR = userRoleRepo.findByName(Roles.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userR);
                }
            });
        }
        user.setLogin(signupRequest.getUsername().toLowerCase(Locale.ROOT));
        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
