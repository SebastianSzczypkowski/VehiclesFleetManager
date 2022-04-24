package pl.szczypkowski.vehiclesfleetmanager.road.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.szczypkowski.vehiclesfleetmanager.road.service.RoadService;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

@RestController
@RequestMapping("/api/road")
@CrossOrigin(origins = "*")
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
    @GetMapping("/get-all-page")
    public ResponseEntity<?> getAllPage(Pageable pageable)
    {
        return roadService.getAllPage(pageable);
    }

//    @PostMapping("/save")
//    public ResponseEntity<?> save()
//    {
//
//    }

}
