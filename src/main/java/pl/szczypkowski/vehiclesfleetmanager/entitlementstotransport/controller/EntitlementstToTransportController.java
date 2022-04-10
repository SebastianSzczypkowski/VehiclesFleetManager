package pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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


}
