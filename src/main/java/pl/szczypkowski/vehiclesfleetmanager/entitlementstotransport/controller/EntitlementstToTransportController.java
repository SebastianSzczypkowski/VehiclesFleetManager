package pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.model.EntitlementstToTransport;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.service.EntitlementstotransportService;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.service.TypeOfPermissionsService;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

@RestController
@RequestMapping("/api/entitlementst")
public class EntitlementstToTransportController {

    private final EntitlementstotransportService entitlementstotransportService;
    private final TypeOfPermissionsService typeOfPermissionsService;

    public EntitlementstToTransportController(EntitlementstotransportService entitlementstotransportService, TypeOfPermissionsService typeOfPermissionsService) {
        this.entitlementstotransportService = entitlementstotransportService;
        this.typeOfPermissionsService = typeOfPermissionsService;
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> getAll()
    {
        try{

            return ResponseEntity.ok().body(entitlementstotransportService.getAll());

        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się pobrać listy uprawnień"));
        }
    }
    @GetMapping("/get-all-page")
    public ResponseEntity<?> getAllPage(Pageable pageable)
    {
        return entitlementstotransportService.getAllPage(pageable);
    }

    @PostMapping("save")
    public ResponseEntity<?> save( @RequestBody EntitlementstToTransport entitlementstToTransport)
    {
        try {
            return ResponseEntity.ok().body(entitlementstotransportService.save(entitlementstToTransport));
        }catch (Exception e)
        {
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało sie dodać uprawnień"));
        }
    }

}
