package pl.szczypkowski.vehiclesfleetmanager.road.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.cargo.repository.CargoRepository;
import pl.szczypkowski.vehiclesfleetmanager.driver.repository.DriverRepository;
import pl.szczypkowski.vehiclesfleetmanager.map.model.Coordinates;
import pl.szczypkowski.vehiclesfleetmanager.map.repository.CoordinatesRepository;
import pl.szczypkowski.vehiclesfleetmanager.road.model.Road;
import pl.szczypkowski.vehiclesfleetmanager.road.repository.RoadRepository;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.repository.VehicleRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoadService {

    private final RoadRepository roadRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(RoadService.class);
    private final CoordinatesRepository coordinatesRepository;
    private final CargoRepository cargoRepository;
    private final DriverRepository driverRepository;
    private final VehicleRepository vehicleRepository;

    public RoadService(RoadRepository roadRepository, CoordinatesRepository coordinatesRepository, CargoRepository cargoRepository, DriverRepository driverRepository, VehicleRepository vehicleRepository) {
        this.roadRepository = roadRepository;
        this.coordinatesRepository = coordinatesRepository;
        this.cargoRepository = cargoRepository;
        this.driverRepository = driverRepository;
        this.vehicleRepository = vehicleRepository;
    }


    public List<Road> getAll()
    {
        return roadRepository.findAll();
    }

    public void save (Coordinates start, Coordinates end)
    {
        try
        {
            Road road = new Road();
            road.setStart(start);
            road.setEnd(end);
            road.setCreationDate(LocalDate.now());
            road.setUpdateDate(LocalDate.now());
            roadRepository.save(road);

        }catch (Exception e)
        {
            LOGGER.error("NIe udało się zapisać trasy");
        }
    }

    public ResponseEntity<?> getAllPage(Pageable pageable) {
        try{

            Page<Road>roadPage =roadRepository.findAll(pageable);
            return ResponseEntity.ok().body(roadPage);

        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało sie pobrać listy tras"));
        }
    }

    public ResponseEntity<?> saveRoad(Road road) {

        try{

            if(road!=null)
            {

                Coordinates startDb =null;
                if(road.getStart()!=null)
                    startDb =coordinatesRepository.save(road.getStart());
                Coordinates endDb=null ;
                if(road.getEnd()!=null)
                    endDb=coordinatesRepository.save(road.getEnd());


                if(road.getId()!=null)
                {
                    Optional<Road> dbRoad =roadRepository.findById(road.getId());
                    if(dbRoad.isPresent())
                    {
                        if( road.getCargo()!=null ) {
                            dbRoad.get().setCargo(road.getCargo());
                            dbRoad.get().getCargo().setAssigned(true);
                            dbRoad.get().setUpdateDate(LocalDate.now());
                            dbRoad.get().getCargo().setAssignedDate(new Date());
                            cargoRepository.save(dbRoad.get().getCargo());

                        }
                        if(road.getDriver()!=null) {
                            dbRoad.get().setDriver(road.getDriver());
                        }
                        if(startDb!=null)
                            dbRoad.get().setStart(startDb);
                        if(endDb!=null)
                            dbRoad.get().setEnd(endDb);
                        if(road.getVehicle()!=null)
                        {
                            dbRoad.get().setVehicle(road.getVehicle());
                            dbRoad.get().getVehicle().setOccupied(true);
                        }
                        dbRoad.get().setUpdateDate(LocalDate.now());

                        Road saved =roadRepository.save(dbRoad.get());

                        return ResponseEntity.ok().body(saved);
                    }
                    else {
                        return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało sie zaktualizować trasy od ID:"+road.getId()));
                    }

                }else
                {
                    if(startDb!=null)
                        road.setStart(startDb);
                    if(endDb!=null)
                        road.setEnd(endDb);
                    if(road.getVehicle()!=null) {
                        road.getVehicle().setOccupied(true);
                        vehicleRepository.save(road.getVehicle());
                    }
                    if(road.getCargo()!=null)
                    {
                        road.getCargo().setAssigned(true);
                        road.getCargo().setAssignedDate(new Date());
                        cargoRepository.save(road.getCargo());
                    }
                    road.setUpdateDate(LocalDate.now());
                    road.setCreationDate(LocalDate.now());
                    road.setFinished(false);
                    Road saved = roadRepository.save(road);
                    return ResponseEntity.ok().body(saved);
                }

            }
            else
            {
                return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało sie zapisać trasy [piste dane]"));
            }


        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało sie zapisać trasy"));
        }
    }
}
