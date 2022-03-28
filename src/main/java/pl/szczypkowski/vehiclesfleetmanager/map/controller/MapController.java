package pl.szczypkowski.vehiclesfleetmanager.map.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.szczypkowski.vehiclesfleetmanager.map.service.MapService;

import java.io.IOException;

@RestController()
@CrossOrigin(origins = "http://localhost:4200")
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



}
