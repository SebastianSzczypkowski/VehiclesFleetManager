package pl.szczypkowski.vehiclesfleetmanager.driver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;
import pl.szczypkowski.vehiclesfleetmanager.driver.service.DriverService;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {

    private DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }


    @GetMapping("/getAll")
    public ResponseEntity<?> getAll()
    {
        try{

            return driverService.getAll();

        }catch (Exception e)
        {
            e.getStackTrace();
            return ResponseEntity.badRequest().body("Nir można pobrąc danych ");
        }
    }
}
