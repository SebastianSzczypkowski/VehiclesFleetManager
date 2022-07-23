package pl.szczypkowski.vehiclesfleetmanager.road.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.szczypkowski.vehiclesfleetmanager.road.model.Road;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;

import java.util.List;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface RoadRepository extends JpaRepository<Road,Long> {



    @Query("select road from Road road where  "+
            "(road.id = :id or :id is null)  and "+
            "(lower(road.start.name) like :start or :start is null) and "+
            "(lower(road.end.name) like :end or :end is null) and " +
            "(lower(road.driver.pesel) like :driver or :driver is null) and " +
            "(lower(road.cargo.name) like :cargo or :cargo is null) and " +
            "(lower(road.vehicle.name) like :vehicle or :vehicle is null) and " +
            "(DATE_FORMAT(road.creationDate, '%Y-%m-%d') between :dataOd and :dataDo or :dataDo  is null or :dataOd is null)")
    List<Road> findByColumnFilter(@Param("id") Long id,@Param("start") String start,@Param("end") String end,
                                     @Param("driver")String driver, @Param("cargo")String cargo, @Param("vehicle")String vehicle,
                                     @Param("dataOd")String dataOd,@Param("dataDo") String dataDo);
}
