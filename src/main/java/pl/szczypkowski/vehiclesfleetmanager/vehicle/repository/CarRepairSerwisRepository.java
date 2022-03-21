package pl.szczypkowski.vehiclesfleetmanager.vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.CarRepairService;

@Repository
public interface CarRepairSerwisRepository extends JpaRepository<CarRepairService,Long> {
}
