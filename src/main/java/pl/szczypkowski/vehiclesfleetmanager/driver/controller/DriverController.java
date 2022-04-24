package pl.szczypkowski.vehiclesfleetmanager.driver.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.DriverRequest;
import pl.szczypkowski.vehiclesfleetmanager.driver.service.DriverService;

import java.util.List;

@RestController
@RequestMapping("/api/driver")
@CrossOrigin(origins = "*")
public class DriverController {

    private DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }


    @GetMapping("/get-all")
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
    @GetMapping("/get-all-page")
    public ResponseEntity<?> getAllPage(Pageable pageable)
    {
        return driverService.getAllPage(pageable);
    }




    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody DriverRequest driverRequest)
    {
        return driverService.save(driverRequest);
    }


}

