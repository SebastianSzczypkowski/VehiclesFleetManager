package pl.szczypkowski.vehiclesfleetmanager.vehicle.Service;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.VehicleRequest;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.repository.VehicleRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<?> save(Vehicle vehicle) {
        try{

            if(vehicle.getId()!=null)
            {
                Optional<Vehicle> vehicleDb = vehicleRepository.findById(vehicle.getId());
                if(vehicleDb.isPresent())
                {
                    if(vehicleDb.get().getName()==null || vehicle.getName()!=null &&!vehicleDb.get().getName().equals(vehicle.getName()))
                        vehicleDb.get().setName(vehicle.getName());
                    if(vehicleDb.get().getAverageFuelConsumption()==null || vehicle.getAverageFuelConsumption()!=null &&!vehicleDb.get().getAverageFuelConsumption().equals(vehicle.getAverageFuelConsumption()))
                        vehicleDb.get().setAverageFuelConsumption(vehicle.getAverageFuelConsumption());
                    if(vehicleDb.get().getCarLoadCapacity()==null || vehicle.getCarLoadCapacity()!=null &&!vehicleDb.get().getCarLoadCapacity().equals(vehicle.getCarLoadCapacity()))
                        vehicleDb.get().setCarLoadCapacity(vehicle.getCarLoadCapacity());
                    if(vehicleDb.get().getCarMileage()==null || vehicle.getCarMileage()!=null &&!vehicleDb.get().getCarMileage().equals(vehicle.getCarMileage()))
                        vehicleDb.get().setCarMileage(vehicle.getCarMileage());
                    if(vehicleDb.get().getEngineCapacity()==null || vehicle.getEngineCapacity()!=null &&!vehicleDb.get().getEngineCapacity().equals(vehicle.getEngineCapacity()))
                        vehicleDb.get().setEngineCapacity(vehicle.getEngineCapacity());
                    if(vehicleDb.get().getLorrySemitrailer()==null || vehicle.getLorrySemitrailer()!=null &&!vehicleDb.get().getLorrySemitrailer().equals(vehicle.getLorrySemitrailer()))
                        vehicleDb.get().setLorrySemitrailer(vehicle.getLorrySemitrailer());
                    if(vehicleDb.get().getCarLoadCapacity()==null || vehicle.getCarLoadCapacity()!=null &&!vehicleDb.get().getCarLoadCapacity().equals(vehicle.getCarLoadCapacity()))
                        vehicleDb.get().setCarLoadCapacity(vehicle.getCarLoadCapacity());
                    if(vehicleDb.get().getOccupied()==null || vehicle.getOccupied()!=null &&!vehicleDb.get().getOccupied().equals(vehicle.getOccupied()))
                        vehicleDb.get().setOccupied(vehicle.getOccupied());
                    if(vehicleDb.get().getNumberOfSeats()==null || vehicle.getNumberOfSeats()!=null &&!vehicleDb.get().getNumberOfSeats().equals(vehicle.getNumberOfSeats()))
                        vehicleDb.get().setNumberOfSeats(vehicle.getNumberOfSeats());
                    if(vehicleDb.get().getRegistrationNumber()==null || vehicle.getRegistrationNumber()!=null &&!vehicleDb.get().getRegistrationNumber().equals(vehicle.getRegistrationNumber()))
                        vehicleDb.get().setRegistrationNumber(vehicle.getRegistrationNumber());
                    if(vehicleDb.get().getVin()==null || vehicle.getVin()!=null &&!vehicleDb.get().getVin().equals(vehicle.getVin()))
                        vehicleDb.get().setVin(vehicle.getVin());
                    if(vehicleDb.get().getRoadworthy()==null || vehicle.getRoadworthy()!=null &&!vehicleDb.get().getRoadworthy().equals(vehicle.getRoadworthy()))
                        vehicleDb.get().setRoadworthy(vehicle.getRoadworthy());

                    Vehicle saved =vehicleRepository.save(vehicleDb.get());
                    return ResponseEntity.ok().body(saved);

                }
                else {
                    return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie znaleziono pojazdu ID: "+vehicle.getId()));
                }

            }
            else {

                vehicle.setOccupied(false);
                vehicle.setRoadworthy(true);
                Vehicle saved = vehicleRepository.save(vehicle);
                return ResponseEntity.ok().body(saved);

            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie duał się dodać pojazdu "));
        }
    }

    public ResponseEntity<?> getAllPage(Pageable pageable) {
        try{

            Page<Vehicle> vehiclePage =vehicleRepository.findAll(pageable);
            return ResponseEntity.ok().body(vehiclePage);
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("NIe udało  się pobrać listy pojazdów"));
        }
    }
}
