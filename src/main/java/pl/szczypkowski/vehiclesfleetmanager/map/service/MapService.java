package pl.szczypkowski.vehiclesfleetmanager.map.service;


import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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

        String start = params.get("start"). get(0).toLowerCase(Locale.ROOT);
        String end = params.get("end").get(0).toLowerCase(Locale.ROOT);

        //TODO sprawdzenie przekazanyuch parametrów + dodanie ich do link z api

        List<Coordinates> coordinatesList = new ArrayList<>();

        coordinatesList.add(getCoordinates(start));
        coordinatesList.add(getCoordinates(end));

        return ResponseEntity.ok().body(coordinatesList);

    }

    public Coordinates getCoordinates(String value)
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


        return coordinates;
    }


    public ResponseEntity<?> save(Coordinates coordinates) {

        try{

            Coordinates saved = coordinatesRepository.save(coordinates);
            return ResponseEntity.ok().body(saved);
        }catch (Exception e)
        {
            LOGGER.error("Nie udało się zapisać trasy");
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Wystąpił błąd podczas zapisu trasy"));
        }
    }

    public ResponseEntity<?> saveRoad(List<Coordinates> list) {

        try {

            Coordinates start = coordinatesRepository.save(list.get(0));
            Coordinates end = coordinatesRepository.save(list.get(1));
            roadService.save(start.getId(),end.getId());

            return ResponseEntity.ok().body(ToJsonString.toJsonString("Pomyślnie zapisano trase"));
        }catch (Exception e)
        {
            LOGGER.error("Wystąpił błąd podczas zapisywania trasy");
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się zapisać trasy"));
        }
    }
}
