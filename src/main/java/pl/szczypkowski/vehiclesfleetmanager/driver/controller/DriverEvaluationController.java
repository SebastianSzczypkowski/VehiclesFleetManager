package pl.szczypkowski.vehiclesfleetmanager.driver.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.szczypkowski.vehiclesfleetmanager.driver.service.DriverEvaluationService;

@RestController
@RequestMapping("/api/driver-evaluation")
public class DriverEvaluationController {

    private final DriverEvaluationService driverEvaluationService;

    public DriverEvaluationController(DriverEvaluationService driverEvaluationService) {
        this.driverEvaluationService = driverEvaluationService;
    }


    @GetMapping("/get-all-page")
    public ResponseEntity<?> getAllPage(Pageable pageable)
    {
        return driverEvaluationService.getAllPage(pageable);
    }

    @GetMapping("/get-all-by-driver")
    public ResponseEntity<?> getAllByDriver(@RequestParam("id") Long id ,Pageable pageable)
    {
        return driverEvaluationService.getByDriver(pageable,id);
    }
}
