package pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.model.Entitlementstotransport;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.service.EntitlementstotransportService;

import java.util.List;

@RestController("/entitlementstotransport")
public class EntitlementstotransportController {

    private EntitlementstotransportService entitlementstotransportService;

    public EntitlementstotransportController(EntitlementstotransportService entitlementstotransportService) {
        this.entitlementstotransportService = entitlementstotransportService;
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll()
    {
        try{
            List<Entitlementstotransport> lista = entitlementstotransportService.getAll();
            return ResponseEntity.ok().body(lista);
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Nie udało sie pobrac danych o upranieniach kierowców");
        }
    }

    @GetMapping("/getOne/{id}")
    public ResponseEntity<?>getOne(@PathVariable("id") Long id)
    {
        try{
            Entitlementstotransport entitlementstotransport = entitlementstotransportService.getOne(id);
            return ResponseEntity.ok().body(entitlementstotransport);
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Nie można porać upranien o ID: "+id);
        }
    }

}
