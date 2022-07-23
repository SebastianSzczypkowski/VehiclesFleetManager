package pl.szczypkowski.vehiclesfleetmanager.map.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import pl.szczypkowski.vehiclesfleetmanager.map.model.Coordinates;
import pl.szczypkowski.vehiclesfleetmanager.map.repository.CoordinatesRepository;
import pl.szczypkowski.vehiclesfleetmanager.road.service.RoadService;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

import java.io.IOException;
import java.util.*;

@Service
public class MapService {




    private final Logger LOGGER = LoggerFactory.getLogger(MapService.class);
    private final CoordinatesRepository coordinatesRepository;
    private final RoadService roadService;
    private final CoordinatesService coordinatesService;

    public MapService(CoordinatesRepository coordinatesRepository, RoadService roadService, CoordinatesService coordinatesService) {
        this.coordinatesRepository = coordinatesRepository;
        this.roadService = roadService;
        this.coordinatesService = coordinatesService;
    }

    public ResponseEntity<?> getCoordinatesForRoute(MultiValueMap<String, String> params) throws IOException {

        String start =  Optional.ofNullable(params.getFirst("start")).filter(val -> !val.isEmpty()).orElse(null);
        String end = Optional.ofNullable(params.getFirst("end")).filter(val -> !val.isEmpty()).orElse(null);
        String color = Optional.ofNullable(params.getFirst("color")).filter(val -> !val.isEmpty()).orElse(null);
        String name = Optional.ofNullable(params.getFirst("name")).filter(val -> !val.isEmpty()).orElse(null);

        //TODO sprawdzenie przekazanyuch parametrów + dodanie ich do link z api

        List<Coordinates> coordinatesList = new ArrayList<>();

        if(start!=null && end!=null) {
            coordinatesList.add(coordinatesService.getCoordinates(start, color, name));
            coordinatesList.add(coordinatesService.getCoordinates(end, color, name));

            return ResponseEntity.ok().body(coordinatesList);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ToJsonString.toJsonString("Brak początkowego/końcowego punktu trasy"));
        }

    }




    public ResponseEntity<?> saveRoad(List<Coordinates> list) {

        try {

            if(list.size()>0) {
                Coordinates start = coordinatesRepository.save(list.get(0));
                Coordinates end = coordinatesRepository.save(list.get(1));
                roadService.saveFromCoordinates(start, end);
                return ResponseEntity.ok().body(ToJsonString.toJsonString("Pomyślnie zapisano trase"));
            }
            else
                return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Przesłana lista jest pusta"));
        }catch (Exception e)
        {
            LOGGER.error("Wystąpił błąd podczas zapisywania trasy");
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się zapisać trasy"));
        }
    }
}
