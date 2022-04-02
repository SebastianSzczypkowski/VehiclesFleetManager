package pl.szczypkowski.vehiclesfleetmanager.road.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.szczypkowski.vehiclesfleetmanager.road.model.Road;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface RoadRepository extends JpaRepository<Road,Long> {
}
