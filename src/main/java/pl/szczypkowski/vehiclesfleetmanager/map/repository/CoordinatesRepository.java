package pl.szczypkowski.vehiclesfleetmanager.map.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.szczypkowski.vehiclesfleetmanager.map.model.Coordinates;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface CoordinatesRepository extends JpaRepository<Coordinates,Long> {
}
