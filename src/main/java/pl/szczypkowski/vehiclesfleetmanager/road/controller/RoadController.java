package pl.szczypkowski.vehiclesfleetmanager.road.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.szczypkowski.vehiclesfleetmanager.road.model.Road;
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
    public ResponseEntity<?> getAllPage(@RequestParam MultiValueMap<String, String> queryParams, Pageable pageable)
    {
        return roadService.getAllPage(queryParams,pageable);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Road road)
    {
        return roadService.saveRoad(road);
    }



    @PostMapping("/create-from-xlsx")
    public ResponseEntity<?> createFromXlsx( @RequestParam("file") MultipartFile file )
    {
        return roadService.createFromXlsx(file);
    }


    @PostMapping("/creat-from-existing/{id}")
    public ResponseEntity<?> creatFromExisting(@PathVariable Long id)
    {
        return roadService.createFromExisting(id);
    }

    @GetMapping("/export")
    public ResponseEntity<?> export(@RequestParam MultiValueMap<String, String> queryParams)
    {
        return roadService.exportToExcel(queryParams);
    }
}
