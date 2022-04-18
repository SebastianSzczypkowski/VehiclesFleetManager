package pl.szczypkowski.vehiclesfleetmanager.vehicle.Service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.VehicleRequest;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.repository.VehicleRepository;

import java.util.List;

@Service
public class VehicleService {

    private VehicleRepository vehicleRepository;
    private Logger logger = LoggerFactory.getLogger(VehicleService.class);

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAll()
    {
        try{

            return vehicleRepository.findAll();
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Vehicle getOne(Long id)
    {
        try{
            return vehicleRepository.getById(id);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<?> getAllNotOccupied()
    {
        try{

            return ResponseEntity.ok().body(vehicleRepository.findAllByOccupiedFalse());
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie można pobrać listy wolnych pojazdów"));
        }
    }

    public ResponseEntity<?> save(VehicleRequest vehicleRequest) {
        try{

            Vehicle vehicle = new Vehicle();
            if(vehicleRequest.getName()!=null)
            vehicle.setName(vehicleRequest.getName());
            if(vehicleRequest.getEngineCapacity()!=null)
                vehicle.setEngineCapacity(vehicleRequest.getEngineCapacity());
            if(vehicleRequest.getLoadCapacity()!=null)
                vehicle.setCarLoadCapacity(vehicleRequest.getLoadCapacity());
            if(vehicleRequest.getRegistration()!=null)
                vehicle.setRegistrationNumber(vehicleRequest.getRegistration());
            if(vehicleRequest.getVin()!=null)
                vehicle.setVin(vehicleRequest.getVin());
            if(vehicleRequest.getMileage()!=null)
                vehicle.setCarMileage(vehicleRequest.getMileage());

            vehicle.setOccupied(false);
            vehicle.setRoadworthy(true);

            Vehicle saved  = vehicleRepository.save(vehicle);
            return ResponseEntity.ok().body(saved);

        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie duał się dodać pojazdu "));
        }
    }
}
