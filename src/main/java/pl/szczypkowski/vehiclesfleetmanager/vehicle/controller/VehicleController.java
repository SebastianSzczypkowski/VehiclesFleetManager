package pl.szczypkowski.vehiclesfleetmanager.vehicle.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.Service.VehicleService;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.VehicleRequest;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/vehicle")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> getAll()
    {
        try{

            return ResponseEntity.ok().body(vehicleService.getAll());
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie można pobrac listy pojazdów"));
        }
    }

    @GetMapping("/get-all-page")
    public ResponseEntity<?> getAllPage(Pageable pageable)
    {
        return vehicleService.getAllPage(pageable);
    }

    @GetMapping("/get-all-free")
    public ResponseEntity<?> getAllFree()
    {
        return ResponseEntity.ok().body(vehicleService.getAllNotOccupied());
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Vehicle vehicle)
    {
        return vehicleService.save(vehicle);
    }
}
