package pl.szczypkowski.vehiclesfleetmanager.road.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.szczypkowski.vehiclesfleetmanager.road.service.RoadService;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

@RestController
@RequestMapping("/api/road")
public class RoadController {

    private final RoadService roadService;


    public RoadController(RoadService roadService) {
        this.roadService = roadService;
    }


    @GetMapping("/get-all")
    public ResponseEntity<?> getAll()
    {
        try{
            return ResponseEntity.ok().body(roadService.getAll());
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się pobrać listy aut"));
        }
    }

}
