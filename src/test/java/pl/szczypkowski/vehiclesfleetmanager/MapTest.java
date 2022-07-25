package pl.szczypkowski.vehiclesfleetmanager;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import pl.szczypkowski.vehiclesfleetmanager.map.model.Coordinates;
import pl.szczypkowski.vehiclesfleetmanager.map.service.CoordinatesService;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MapTest {


    private Logger LOGGER = LoggerFactory.getLogger(MapTest.class);
    @Autowired
    private CoordinatesService coordinatesService;


    @Test
    public void getCoords()
    {
        Coordinates coordinates
                = coordinatesService.getCoordinates("Tarnowskie Góry","","test");
        LOGGER.info("Pobrane współrzędne: {}",coordinates);

    }

}
