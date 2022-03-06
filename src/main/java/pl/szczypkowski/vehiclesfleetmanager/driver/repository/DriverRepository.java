package pl.szczypkowski.vehiclesfleetmanager.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;

public interface DriverRepository extends JpaRepository<Driver,Long> {
}
