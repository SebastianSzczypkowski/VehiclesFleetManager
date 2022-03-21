package pl.szczypkowski.vehiclesfleetmanager.driver.service;

import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;
import pl.szczypkowski.vehiclesfleetmanager.driver.repository.DriverRepository;

import java.util.List;

@Service
public class DriverService {

    private Logger logger = LoggerFactory.getLogger(DriverService.class);
    private DriverRepository driverRepository;

    public DriverService(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    public Driver getById(Long id)
    {
        return driverRepository.getById(id);
    }

    public void save(Driver driver)
    {
        try {
            driverRepository.save(driver);
        }catch (Exception e)
        {
            e.getStackTrace();
            logger.error("Wystąpił błąd podczas zapisu danych kierowcy");
        }
    }


    public ResponseEntity<?> getAll() {

        List<Driver> drivers =driverRepository.findAll();
        return ResponseEntity.ok().body(drivers);
    }
}
