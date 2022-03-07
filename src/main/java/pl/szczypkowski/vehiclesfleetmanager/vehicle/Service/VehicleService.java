package pl.szczypkowski.vehiclesfleetmanager.vehicle.Service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;
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

}
