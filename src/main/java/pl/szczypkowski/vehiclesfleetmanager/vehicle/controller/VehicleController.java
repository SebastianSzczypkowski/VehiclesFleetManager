package pl.szczypkowski.vehiclesfleetmanager.vehicle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.Service.VehicleService;

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
    @GetMapping("/get-all-free")
    public ResponseEntity<?> getAllFree()
    {
        return ResponseEntity.ok().body(vehicleService.getAllNotOccupied());
    }
}
