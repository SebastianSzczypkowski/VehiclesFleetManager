package pl.szczypkowski.vehiclesfleetmanager.driver.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;
import pl.szczypkowski.vehiclesfleetmanager.driver.service.DriverService;

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
    public ResponseEntity<?> save(@RequestBody Driver driver)
    {
        return driverService.save(driver);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchVehicle(@RequestParam("search") String search,Pageable pageable)
    {
        return driverService.searchDriver(search,pageable);
    }


}

