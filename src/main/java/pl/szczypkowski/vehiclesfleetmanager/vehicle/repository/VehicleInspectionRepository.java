package pl.szczypkowski.vehiclesfleetmanager.vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.VehicleInspection;

import java.util.List;

@Repository
public interface VehicleInspectionRepository extends JpaRepository<VehicleInspection,Long> {



    @Query("select vehicleInspection from VehicleInspection vehicleInspection where  "+
            "(vehicleInspection.id = :id or :id is null)  and "+
            "(lower(vehicleInspection.description) like :description or :description is null) and "+
            "(lower(vehicleInspection.performedBy) like :performedBy or :performedBy is null) and " +
            "(lower(vehicleInspection.idVehicle.name) like :vehicle or :vehicle is null) and " +
            "(DATE_FORMAT(vehicleInspection.date, '%Y-%m-%d') between :dataOd and :dataDo or :dataDo  is null or :dataOd is null) and " +
            "(DATE_FORMAT(vehicleInspection.validityOfTheVehicleInspection, '%Y-%m-%d') between :validityDataOd  and :validityDataDo or :validityDataDo  is null or :validityDataOd is null) ")
    List<VehicleInspection> findByColumnFilter(@Param("id") Long id,@Param("dataOd") String dataOd, @Param("dataDo")String dataDo,
                                               @Param("validityDataOd")String validityDataOd, @Param("validityDataDo")String validityDataDo, @Param("vehicle")String vehicle,
                                               @Param("description")String description, @Param("performedBy")String performedBy);
}
