package pl.szczypkowski.vehiclesfleetmanager.vehicle.Service;


import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.VehicleInspection;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.repository.VehicleInspectionRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class VehicleInspectionService {

    private final VehicleInspectionRepository vehicleInspectionRepository;


    public VehicleInspectionService(VehicleInspectionRepository vehicleInspectionRepository) {
        this.vehicleInspectionRepository = vehicleInspectionRepository;
    }

    public List<VehicleInspection> getALl()
    {
        return vehicleInspectionRepository.findAll();
    }

    @Transactional
    public VehicleInspection save(VehicleInspection vehicleInspection)
    {
        try{

            return vehicleInspectionRepository.save(vehicleInspection);

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public ResponseEntity<?> getAllPage(Pageable pageable) {
        try{

            return ResponseEntity.ok().body(vehicleInspectionRepository.findAll(pageable));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało sie pobrać listy przeglądów technicznych"));
        }
    }
}
