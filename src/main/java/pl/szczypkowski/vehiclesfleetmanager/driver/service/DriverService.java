package pl.szczypkowski.vehiclesfleetmanager.driver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.DriverRequest;
import pl.szczypkowski.vehiclesfleetmanager.driver.repository.DriverRepository;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

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

    public ResponseEntity<?> save(DriverRequest driverRequest) {
        try
        {
            Driver driver =new Driver();
            if(driverRequest.getName()!=null)
                driver.setName(driverRequest.getName());
            if(driverRequest.getSurname()!=null)
                driver.setSurname(driverRequest.getSurname());
            if(driverRequest.getAddress()!=null)
                driver.setAddress(driverRequest.getAddress());
            if(driverRequest.getPesel()!=null)
                driver.setPesel(driverRequest.getPesel());

            Driver saved =driverRepository.save(driver);
            return ResponseEntity.ok().body(saved);

        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie można dodać kierowcy"));
        }
    }
}
