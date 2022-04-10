package pl.szczypkowski.vehiclesfleetmanager.cargo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.szczypkowski.vehiclesfleetmanager.cargo.model.Cargo;

import java.util.List;

public interface CargoRepository extends JpaRepository<Cargo,Long> {

    List<Cargo> findAllByAssignedFalse();
    List<Cargo> findAllByAssignedTrue();
    List<Cargo> findAllByDeliveredTrue();
    List<Cargo> findAllByDeliveredFalse();
    List<Cargo> finaAllByAssignedTrueAndDeliveredFalse();


}
