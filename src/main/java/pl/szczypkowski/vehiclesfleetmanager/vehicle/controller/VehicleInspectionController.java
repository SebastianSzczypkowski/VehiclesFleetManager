package pl.szczypkowski.vehiclesfleetmanager.vehicle.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.Service.VehicleInspectionService;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.VehicleInspection;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/vehicle-inspection")
public class VehicleInspectionController {

    private final VehicleInspectionService vehicleInspectionService;

    public VehicleInspectionController(VehicleInspectionService vehicleInspectionService) {
        this.vehicleInspectionService = vehicleInspectionService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll()
    {
        try
        {
            return ResponseEntity.ok().body(vehicleInspectionService.getALl());
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("nie udało się pobrać danych"));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody VehicleInspection vehicleInspection)
    {
        try{
            return ResponseEntity.ok().body(vehicleInspectionService.save(vehicleInspection));
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się zapisać kontroli pojazdu"));
        }
    }
}
