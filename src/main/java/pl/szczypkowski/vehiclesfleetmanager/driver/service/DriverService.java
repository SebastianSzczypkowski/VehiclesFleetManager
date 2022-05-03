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



    public ResponseEntity<?> getAll() {

        List<Driver> drivers =driverRepository.findAll();
        return ResponseEntity.ok().body(drivers);
    }

    public ResponseEntity<?> save(Driver driver) {
        try
        {

            if(driver.getId()!=null)
            {
                Optional<Driver> getFromDb =driverRepository.findById(driver.getId());
                if(getFromDb.isPresent())
                {
                    if(getFromDb.get().getName()==null || driver.getName()!=null &&!getFromDb.get().getName().equals(driver.getName()))
                        getFromDb.get().setName(driver.getName());
                    if(getFromDb.get().getSurname()==null ||driver.getSurname()!=null &&!getFromDb.get().getSurname().equals(driver.getSurname()))
                        getFromDb.get().setSurname(driver.getSurname());
                    if(getFromDb.get().getPesel()==null ||driver.getPesel()!=null &&!getFromDb.get().getPesel().equals(driver.getPesel()))
                        getFromDb.get().setPesel(driver.getPesel());
                    if(getFromDb.get().getAddress()==null ||driver.getAddress()!=null &&!getFromDb.get().getAddress().equals(driver.getAddress()))
                        getFromDb.get().setAddress(driver.getAddress());
                    if(getFromDb.get().getDateOfBirth()==null ||driver.getDateOfBirth()!=null && !getFromDb.get().getDateOfBirth().equals(driver.getDateOfBirth()))
                        getFromDb.get().setDateOfBirth(driver.getDateOfBirth());

                    Driver saved = driverRepository.save(getFromDb.get());
                    return ResponseEntity.ok().body(saved);
                }else
                {
                    return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie znaleziono kierowcy o ID: "+driver.getId()));
                }

            }
            else {
                Optional<Driver> optionalDriver = driverRepository.getDriverByPeselEquals(driver.getPesel());
                if (optionalDriver.isEmpty()) {

                    Driver saved = driverRepository.save(driver);
                    return ResponseEntity.ok().body(saved);
                } else {
                    return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Kierowca o tym numerze PESEL jest już w bazie"));
                }
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
