package pl.szczypkowski.vehiclesfleetmanager.driver.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.DriverEvaluation;
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

    @PostMapping("/create-evaluation")
    public ResponseEntity<?> createEvaluation(@RequestBody DriverEvaluation driverEvaluation)
    {
        return driverEvaluationService.saveOrUpdate(driverEvaluation);
    }
}
