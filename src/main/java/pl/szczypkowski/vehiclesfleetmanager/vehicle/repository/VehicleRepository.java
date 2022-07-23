package pl.szczypkowski.vehiclesfleetmanager.vehicle.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle,Long> {

    List<Vehicle> findAllByOccupiedFalse();


    Optional<Vehicle> getByRegistrationNumber(String registrationNumber);


    @Query("select vehicle from Vehicle vehicle where vehicle.roadworthy =true and "+
            "(vehicle.id = :id or :id is null)  and "+
            "(lower(vehicle.name) like :name or :name is null) and "+
            "(lower(vehicle.vin) like :vin or :vin is null) and "+
            "(lower(vehicle.registrationNumber) like :registrationNumber or :registrationNumber is null) and " +
            "(vehicle.carMileage =: carMileage or :carMileage is null) and " +
            "(vehicle.carLoadCapacity =: carLoadCapacity or :carLoadCapacity is null)")
    List<Vehicle> findByColumnFilter(@Param("id")Long id,@Param("name") String name,@Param("vin") String vin,
                                     @Param("registrationNumber")String registrationNumber, @Param("carMileage")Integer carMileage,
                                     @Param("carLoadCapacity") Integer carLoadCapacity);
}
