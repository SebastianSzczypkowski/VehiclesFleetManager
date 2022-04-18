package pl.szczypkowski.vehiclesfleetmanager.cargo.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szczypkowski.vehiclesfleetmanager.cargo.model.Cargo;
import pl.szczypkowski.vehiclesfleetmanager.cargo.model.CargoRequest;
import pl.szczypkowski.vehiclesfleetmanager.cargo.service.CargoService;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

import java.util.List;

@RestController
@RequestMapping("/api/cargo")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }


    @GetMapping("/not-assigned-cargo")
    public ResponseEntity<?> getAllNotAssignedCargo()
    {
        try{
            List<Cargo> cargoList = cargoService.getNotAssignedCargo();
            return ResponseEntity.ok().body(cargoList);
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie można pobrać listy wolnych ładunków"));
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody CargoRequest cargoRequest)
    {
        return cargoService.save(cargoRequest);
    }
}
