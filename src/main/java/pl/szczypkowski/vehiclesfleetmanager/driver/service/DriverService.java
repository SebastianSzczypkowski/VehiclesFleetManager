package pl.szczypkowski.vehiclesfleetmanager.driver.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.DriverRequest;
import pl.szczypkowski.vehiclesfleetmanager.driver.repository.DriverRepository;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

import java.lang.management.OperatingSystemMXBean;
import java.util.List;
import java.util.Optional;

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
            Optional<Driver> optionalDriver=driverRepository.getDriverByPeselEquals(driverRequest.getPesel());
            if(optionalDriver.isEmpty()) {
                Driver driver = new Driver();
                if (driverRequest.getName() != null)
                    driver.setName(driverRequest.getName());
                if (driverRequest.getSurname() != null)
                    driver.setSurname(driverRequest.getSurname());
                if (driverRequest.getAddress() != null)
                    driver.setAddress(driverRequest.getAddress());
                if (driverRequest.getPesel() != null)
                    driver.setPesel(driverRequest.getPesel());
                if (driverRequest.getDateofbirth() != null)
                    driver.setDateOfBirth(driverRequest.getDateofbirth());
                Driver saved = driverRepository.save(driver);
                return ResponseEntity.ok().body(saved);
            }else
            {
                return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Kierowca o tym numerze PESEL jest już w bazie"));
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie można dodać kierowcy"));
        }
    }

    public ResponseEntity<?> getAllPage(Pageable pageable) {
        try{

            return ResponseEntity.ok().body(driverRepository.findAll(pageable));
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się pobrać listy pojazdów"));
        }
    }
}
