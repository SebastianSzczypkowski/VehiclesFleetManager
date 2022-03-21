package pl.szczypkowski.vehiclesfleetmanager.map.service;


import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import pl.szczypkowski.vehiclesfleetmanager.map.model.Coordinates;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MapService {



    private final Logger LOGGER = LoggerFactory.getLogger(MapService.class);

    public ResponseEntity<?> getCoordinates(MultiValueMap<String, String> params) throws IOException {

        //TODO sprawdzenie przekazanyuch parametrów + dodanie ich do link z api
        List<Coordinates> coordinatesList = new ArrayList<>();
        Client client = ClientBuilder.newClient();
        Response response = client.target("https://nominatim.openstreetmap.org/search?q=135+pilkington+avenue,+birmingham&format=json&polygon=1&addressdetails=1")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Accept", "application/json, application/geo+json, application/gpx+xml, img/png; charset=utf-8")
                .get();
        String json =response.readEntity(String.class);;
        JSONArray array = new JSONArray(json);

        for(int i=0;i<array.length();i++)
        {
            Coordinates coordinates = new Coordinates();
            String lon = array.getJSONObject(i).getString("lon");
            String lat = array.getJSONObject(i).getString("lat");
            coordinates.setLat(lat);
            coordinates.setLon(lon);
            coordinatesList.add(coordinates);

        }


        return ResponseEntity.ok().body(coordinatesList);

    }

//
//    public List<Event> getData(int limit) throws IOException
//    {
//        List<Event> list = new ArrayList<>();
//        JSONObject json = readJsonFromUrl("https://eonet.sci.gsfc.nasa.gov/api/v2.1/events?limit="+limit);
//        JSONArray array = json.getJSONArray("events");
//        for(int i=0;i<array.length();i++)
//        {
//            String id = array.getJSONObject(i).getString("id");
//            String title = array.getJSONObject(i).getString("title");
//            String link = array.getJSONObject(i).getString("link");
//            Category category = new Category();
//            JSONArray categoriesArray = array.getJSONObject(i).getJSONArray("categories");
//            for(int j=0;j<categoriesArray.length();j++)
//            {
//                category.setId(categoriesArray.getJSONObject(j).getLong("id"));
//                category.setTitle(categoriesArray.getJSONObject(j).getString("title"));
//            }
//
//            List<Sources> sourcesList = new ArrayList<>();
//            JSONArray sourcesArray = array.getJSONObject(i).getJSONArray("sources");
//            for(int j=0;j<sourcesArray.length();j++)
//            {
//                sourcesList.add(new Sources(sourcesArray.getJSONObject(j).getString("id"),sourcesArray.getJSONObject(j).getString("url")));
//            }
//
//            List<Geometries> geometriesList = new ArrayList<>();
//            JSONArray geometriesArray = array.getJSONObject(i).getJSONArray("geometries");
//            for(int j=0;j<geometriesArray.length();j++)
//            {
//                double coordinates[] = new double[2];
//                String dateString = geometriesArray.getJSONObject(j).getString("date");
//                LocalDateTime localdatetime = LocalDateTime.parse(dateString.substring(0,19));
//
//                JSONArray coordinatesArray = geometriesArray.getJSONObject(j).getJSONArray("coordinates");
//                for(int k=0;k<coordinatesArray.length();k++)
//                {
//                    coordinates[k] = coordinatesArray.getDouble(k);
//                }
//                geometriesList.add(new Geometries(localdatetime,
//                        geometriesArray.getJSONObject(j).getString("type"),coordinates[0],coordinates[1]
//                ));
//            }
//            list.add(new Event(id,title,link,sourcesList,category,geometriesList));
//        }
//        log.info("Passing "+list.size()+" elements from events list to controller");
//        return list;
//    }
}
