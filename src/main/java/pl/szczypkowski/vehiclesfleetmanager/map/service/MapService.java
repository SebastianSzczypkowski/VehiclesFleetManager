package pl.szczypkowski.vehiclesfleetmanager.map.service;


import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import pl.szczypkowski.vehiclesfleetmanager.map.model.Coordinates;
import pl.szczypkowski.vehiclesfleetmanager.map.repository.CoordinatesRepository;
import pl.szczypkowski.vehiclesfleetmanager.road.service.RoadService;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.*;

@Service
public class MapService {




    private final Logger LOGGER = LoggerFactory.getLogger(MapService.class);
    private final CoordinatesRepository coordinatesRepository;
    private final RoadService roadService;

    public MapService(CoordinatesRepository coordinatesRepository, RoadService roadService) {
        this.coordinatesRepository = coordinatesRepository;
        this.roadService = roadService;
    }

    public ResponseEntity<?> getCoordinatesForRoute(MultiValueMap<String, String> params) throws IOException {

        String start =  Optional.ofNullable(params.getFirst("start")).filter(val -> !val.isEmpty()).orElse(null);
        String end = Optional.ofNullable(params.getFirst("end")).filter(val -> !val.isEmpty()).orElse(null);
        String color = Optional.ofNullable(params.getFirst("color")).filter(val -> !val.isEmpty()).orElse(null);
        String name = Optional.ofNullable(params.getFirst("name")).filter(val -> !val.isEmpty()).orElse(null);

        //TODO sprawdzenie przekazanyuch parametrów + dodanie ich do link z api

        List<Coordinates> coordinatesList = new ArrayList<>();

        if(start!=null && end!=null) {
            coordinatesList.add(getCoordinates(start, color, name));
            coordinatesList.add(getCoordinates(end, color, name));

            return ResponseEntity.ok().body(coordinatesList);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ToJsonString.toJsonString("Brak początkowego/końcowego punktu trasy"));
        }

    }

    public Coordinates getCoordinates(String value,String color,String name)
    {

        value=value.replace(" ","%20");
        Client client = ClientBuilder.newClient();
        Response response = client.target("https://nominatim.openstreetmap.org/search?q="+value+"&format=json&polygon=1&addressdetails=1")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                .get();
        String json =response.readEntity(String.class);
        JSONArray array = new JSONArray(json);

        Coordinates coordinates = new Coordinates();

        coordinates.setLat(array.getJSONObject(0).getString("lat"));
        coordinates.setLon(array.getJSONObject(0).getString("lon"));
        coordinates.setDetails(array.getJSONObject(0).getString("display_name"));
        if(color!=null)
        coordinates.setColor(color);
        coordinates.setName(Objects.requireNonNullElseGet(name, () -> "road_" + System.currentTimeMillis()));

        return coordinates;
    }


    @Transactional
    public Coordinates save(Coordinates coordinates)
    {
        try{

            return  coordinatesRepository.save(coordinates);
        }catch (Exception e)
        {
            LOGGER.error("Nie udało się zapisać trasy wiadomość: {}",e.getMessage());
            return null;
        }
    }


    public ResponseEntity<?> createCoordinates(Coordinates coordinates) {

        try{

            Coordinates saved = coordinatesRepository.save(save(coordinates));
            return ResponseEntity.ok().body(saved);
        }catch (Exception e)
        {
            LOGGER.error("Nie udało się zapisać trasy");
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Wystąpił błąd podczas zapisu trasy"));
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
