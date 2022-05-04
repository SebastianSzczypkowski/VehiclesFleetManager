package pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.service.TypeOfPermissionsService;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

@RestController
@RequestMapping("/api/typ-of-permissions")
@CrossOrigin(origins = "*")
public class TypOfPermissionsController {

    private final TypeOfPermissionsService typeOfPermissionsService;

    public TypOfPermissionsController(TypeOfPermissionsService typeOfPermissionsService) {
        this.typeOfPermissionsService = typeOfPermissionsService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll()
    {
        try {

            return ResponseEntity.ok().body(typeOfPermissionsService.getAll());

        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie można pobrać listy typów uprawnień"));
        }
    }
    @GetMapping("/get-all-page")
    public ResponseEntity<?> getAllPage(Pageable pageable)
    {
       return typeOfPermissionsService.getAllPage(pageable);
    }

}
