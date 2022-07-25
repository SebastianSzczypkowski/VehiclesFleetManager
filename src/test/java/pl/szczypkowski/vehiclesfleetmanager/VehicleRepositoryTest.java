package pl.szczypkowski.vehiclesfleetmanager;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.Service.VehicleService;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.repository.VehicleRepository;

import java.util.List;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class VehicleRepositoryTest {


    private Logger LOGGER = LoggerFactory.getLogger(VehicleRepositoryTest.class);
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private TestEntityManager entityManager;


    @Test
    public void filter()
    {
        Vehicle vehicle1 = new Vehicle("MAN","34FDFSR32F24","STA 1234",
                432000,25000,true,2,13.000,25.0,true,false);
        Vehicle vehicle2 = new Vehicle("IVECO","43GDFGDGDFG","STA 5678",
                532000,30000,true,2,15.000,28.0,true,false);
        Vehicle vehicle3 = new Vehicle("MAN","T3423423GF","POS 923A",
                349000,27000,true,2,11.500,23.0,true,false);

        entityManager.persist(vehicle1);
        entityManager.persist(vehicle2);
        entityManager.persist(vehicle3);
        entityManager.flush();

            List<Vehicle> filtered = vehicleRepository.findByColumnFilter(null,"MAN",null,null,null,null);
        LOGGER.info("Pobrana lista rozmiar:{}  elementy:{}",filtered.size(),filtered);

    }



}
