package pl.szczypkowski.vehiclesfleetmanager.map.service;


import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szczypkowski.vehiclesfleetmanager.map.model.Coordinates;
import pl.szczypkowski.vehiclesfleetmanager.map.repository.CoordinatesRepository;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;

@Service
public class CoordinatesService {

    private final Logger LOGGER = LoggerFactory.getLogger(CoordinatesService.class);
    private final CoordinatesRepository coordinatesRepository;

    public CoordinatesService(CoordinatesRepository coordinatesRepository) {
        this.coordinatesRepository = coordinatesRepository;
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
}
