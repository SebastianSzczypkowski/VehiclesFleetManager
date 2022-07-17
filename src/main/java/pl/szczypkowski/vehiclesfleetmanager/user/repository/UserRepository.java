package pl.szczypkowski.vehiclesfleetmanager.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szczypkowski.vehiclesfleetmanager.user.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByName(String s);
    Boolean existsByName(String username);
    Boolean existsByEmail(String email);
}
