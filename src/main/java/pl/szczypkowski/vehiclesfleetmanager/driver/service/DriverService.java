package pl.szczypkowski.vehiclesfleetmanager.driver.service;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.EntitleRequest;
import pl.szczypkowski.vehiclesfleetmanager.driver.repository.DriverRepository;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.model.EntitlementstToTransport;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.repository.EntitlementstotransportRepository;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class DriverService {

    private Logger logger = LoggerFactory.getLogger(DriverService.class);
    private DriverRepository driverRepository;
    private EntitlementstotransportRepository entitlementstotransportRepository;
    @PersistenceContext
    private EntityManager entityManager;
    public DriverService(DriverRepository driverRepository, EntitlementstotransportRepository entitlementstotransportRepository) {
        this.driverRepository = driverRepository;
        this.entitlementstotransportRepository = entitlementstotransportRepository;
    }

    public Driver getById(Long id)
    {
        return driverRepository.getById(id);
    }



    public ResponseEntity<?> getAll() {

        List<Driver> drivers =driverRepository.findAll();
        return ResponseEntity.ok().body(drivers);
    }

    @Transactional
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

                    List<EntitleRequest> listToSave =driver.getEntitlement();
                    Driver saved = driverRepository.save(driver);
                    if(listToSave.size()>0) {
                        for (EntitleRequest request : listToSave) {
                            EntitlementstToTransport driverEntitle = new EntitlementstToTransport();
                            driverEntitle.setIdDriver(saved.getId());
                            driverEntitle.setDrivers(driver);
                            if (request.getDocumentTyp() != null)
                                driverEntitle.setDocumentTyp(request.getDocumentTyp());
                            if (request.getExpiryDate() != null)
                                driverEntitle.setExpiryDate(request.getExpiryDate());
                            entitlementstotransportRepository.save(driverEntitle);
                        }
                    }
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

    public ResponseEntity<?> searchDriver(String search, Pageable pageable) {
        try {
            SearchSession searchSession = Search.session( entityManager );

            MassIndexer indexer = searchSession.massIndexer( Driver.class )
                    .threadsToLoadObjects( 7 );
            indexer.startAndWait();

            SearchResult<Driver> result = Search.session(entityManager).search(
                    Driver.class).where(f->f.wildcard().fields("name","surname","address").matching(
                    search+"*"
            )).fetchAll();


            List<Driver> results = result.hits();
            Set<Driver> driverSet = new HashSet<>(results);
            results = new ArrayList<>(driverSet);

            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), results.size());
            final Page<Driver> page = new PageImpl<>(results.subList(start, end), pageable, results.size());
            return ResponseEntity.ok().body(page);
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się znaleść wyników"));
        }
    }
}
