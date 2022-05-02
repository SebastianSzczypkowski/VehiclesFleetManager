package pl.szczypkowski.vehiclesfleetmanager.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;

import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver,Long> {

    Optional<Driver> getDriverByPeselEquals(Long pesle);
}
