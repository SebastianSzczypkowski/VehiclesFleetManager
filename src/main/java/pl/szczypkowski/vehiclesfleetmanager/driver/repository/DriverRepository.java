package pl.szczypkowski.vehiclesfleetmanager.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;

import java.util.List;
import java.util.Optional;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface DriverRepository extends JpaRepository<Driver,Long> {

    Optional<Driver> getDriverByPeselEquals(Long pesle);

    @Query("select driver from Driver driver where  "+
            "(driver.id = :id or :id is null)  and "+
            "(lower(driver.name) like :name or :name is null) and "+
            "(lower(driver.surname) like :surname or :surname is null) and " +
            "(driver.pesel =:pesel or :pesel is null) and " +
            "(lower(driver.dateOfBirth) like :dateOfBirth or :dateOfBirth is null) and " +
            "(lower(driver.address) like :address or :address is null)  " )
    List<Driver> findByColumnFilter(@Param("id") Long id,@Param("name") String name,@Param("surname") String surname,
                                    @Param("pesel")Long pesel,@Param("address") String address,
                                    @Param("dateOfBirth") String dateOfBirth);
}
