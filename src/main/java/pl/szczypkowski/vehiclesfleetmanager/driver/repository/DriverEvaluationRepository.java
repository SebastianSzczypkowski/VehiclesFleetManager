package pl.szczypkowski.vehiclesfleetmanager.driver.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.DriverEvaluation;

public interface DriverEvaluationRepository extends JpaRepository<DriverEvaluation,Long> {

    Page<DriverEvaluation> findAllByRoadId_Driver_Id(Long id, Pageable pageable);

}
