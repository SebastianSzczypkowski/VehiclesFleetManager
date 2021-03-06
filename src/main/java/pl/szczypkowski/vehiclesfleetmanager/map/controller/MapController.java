package pl.szczypkowski.vehiclesfleetmanager.map.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.szczypkowski.vehiclesfleetmanager.map.model.Coordinates;
import pl.szczypkowski.vehiclesfleetmanager.map.service.MapService;

import java.io.IOException;
import java.util.List;

@RestController()
@CrossOrigin()
@RequestMapping("/map")
public class MapController {


    private final MapService mapService;

    public MapController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping("/cord")
    public ResponseEntity<?> getCord(@RequestParam MultiValueMap<String,String> params) throws IOException {
        //TODO przekazywanie parametrów miejscowości
        return  mapService.getCoordinatesForRoute(params);
    }


    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody Coordinates coordinates)
    {
        return mapService.save(coordinates);
    }


    @CrossOrigin
    @PostMapping("/save-road")
    public ResponseEntity<?> saveRoad(@RequestBody List<Coordinates> list)
    {
        return mapService.saveRoad(list);
    }

}
