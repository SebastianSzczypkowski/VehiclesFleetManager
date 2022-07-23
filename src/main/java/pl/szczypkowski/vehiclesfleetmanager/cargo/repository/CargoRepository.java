package pl.szczypkowski.vehiclesfleetmanager.cargo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.szczypkowski.vehiclesfleetmanager.cargo.model.Cargo;

import java.util.List;
import java.util.Optional;

public interface CargoRepository extends JpaRepository<Cargo,Long> {


    Optional<Cargo> getByName(String name);



    @Query("select cargo from Cargo cargo where  "+
            "(cargo.id = :id or :id is null)  and "+
            "(lower(cargo.name) like :name or :name is null) and "+
            "(lower(cargo.description) like :description or :description is null) and " +
            "(lower(cargo.type) like :cargoType or :cargoType is null) and " +
            "(lower(cargo.sensitivity) like :sensitivity or :sensitivity is null) and " +
            "(lower(cargo.specialRemarks) like :special_remarks or :special_remarks is null) and " +
            "(lower(cargo.driver.pesel) like :driver or :driver is null) and " +
            "(DATE_FORMAT(cargo.assignedDate, '%Y-%m-%d') between :assignedDateOd and :assignedDateDo or :assignedDateOd  is null or :assignedDateDo is null)")
    List<Cargo> findByColumnFilter(@Param("id") Long id,@Param("name") String name,@Param("description") String description,
                                   @Param("cargoType")String type,@Param("sensitivity") String sensitivity,@Param("special_remarks") String special_remarks,
                                   @Param("driver")String driver,@Param("assignedDateOd") String assignedDateOd,@Param("assignedDateDo") String assignedDateDo);



//    List<Cargo> findAllByAssignedFalse();
//
//    List<Cargo> finaAllByAssignedTrueAndDeliveredFalse();


}
