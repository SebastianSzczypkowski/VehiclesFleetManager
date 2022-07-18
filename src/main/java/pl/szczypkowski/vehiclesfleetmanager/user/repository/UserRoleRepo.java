package pl.szczypkowski.vehiclesfleetmanager.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.szczypkowski.vehiclesfleetmanager.user.model.Roles;
import pl.szczypkowski.vehiclesfleetmanager.user.model.UserRole;


import java.util.Optional;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface UserRoleRepo extends JpaRepository<UserRole,Long> {
    Optional<UserRole> findByName(Roles username);

}
