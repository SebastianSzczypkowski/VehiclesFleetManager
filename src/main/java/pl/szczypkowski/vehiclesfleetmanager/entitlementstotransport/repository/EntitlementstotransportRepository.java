package pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.model.Entitlementstotransport;


@Repository
public interface EntitlementstotransportRepository extends JpaRepository<Entitlementstotransport,Long> {
}
