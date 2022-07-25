package pl.szczypkowski.vehiclesfleetmanager.driver.service;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.EntitleRequest;
import pl.szczypkowski.vehiclesfleetmanager.driver.repository.DriverRepository;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.model.EntitlementstToTransport;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.repository.EntitlementstotransportRepository;
import pl.szczypkowski.vehiclesfleetmanager.road.model.Road;
import pl.szczypkowski.vehiclesfleetmanager.utils.ExportExel;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class DriverService {

    private Logger LOGGER = LoggerFactory.getLogger(DriverService.class);
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


    public Driver getByDriverPESEL(String pesel) {
        try{

            //TODO walidacja tylko liczby + długość musi być równa 11
            if(!pesel.equals(" "))
            {
                Optional<Driver> optional = driverRepository.getDriverByPeselEquals(Long.valueOf(pesel));
                return optional.orElse(null);
            }
            else{
                return null;
            }

        }catch (Exception e)
        {
            LOGGER.error("Wystąpił bład podczas pobierania kierowcy o peselu: {} ",pesel);
            return null;
        }
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

    public List<Driver> getFilteredList(MultiValueMap<String, String> queryParams)
    {
        try{

            String idStr = Optional.ofNullable(queryParams.getFirst("id")).filter(val -> !val.isEmpty()).orElse(null);
            Long id = null;
            if (idStr != null)
                id = Long.parseLong(idStr);

            String name = Optional.ofNullable(queryParams.getFirst("name")).filter(val -> !val.isEmpty()).orElse(null);
            if (name != null) name = '%' + name.toLowerCase(Locale.ROOT) + '%';

            String surname = Optional.ofNullable(queryParams.getFirst("surname")).filter(val -> !val.isEmpty()).orElse(null);
            if (surname != null) surname = '%' + surname.toLowerCase(Locale.ROOT) + '%';

            String peselStr = Optional.ofNullable(queryParams.getFirst("pesel")).filter(val -> !val.isEmpty()).orElse(null);
            Long pesel = null;
            if (peselStr != null) pesel = Long.parseLong(peselStr);

            String address = Optional.ofNullable(queryParams.getFirst("address")).filter(val -> !val.isEmpty()).orElse(null);
            if (address != null) address = '%' + address.toLowerCase(Locale.ROOT) + '%';

            String entitlementstToTransport = Optional.ofNullable(queryParams.getFirst("entitlementstToTransport.name")).filter(val -> !val.isEmpty()).orElse(null);
            if (entitlementstToTransport != null) entitlementstToTransport = '%' + entitlementstToTransport.toLowerCase(Locale.ROOT) + '%';

            String dateOfBirth = Optional.ofNullable(queryParams.getFirst("dateOfBirth")).filter(val -> !val.isEmpty()).orElse(null);
            if (dateOfBirth != null) dateOfBirth = '%' + dateOfBirth.toLowerCase(Locale.ROOT) + '%';


            //TODO sortowanie po uprawnieniach
            return driverRepository.findByColumnFilter(id,name,surname,pesel,address,dateOfBirth);

        }catch (Exception e)
        {
            LOGGER.error("Wystąpił błąd podczas pobierania listy kierowców wiadomość: {}",e.getMessage());
            return null;
        }
    }
    public ResponseEntity<?> getAllPage(MultiValueMap<String, String> queryParams, Pageable pageable) {
        try{

            List<Driver> list =getFilteredList(queryParams);
            posortuj(list, pageable.getSort().toString().replace(":", ""));

            final int startP = (int)pageable.getOffset();
            final int endP = Math.min((startP + pageable.getPageSize()), list.size());
            final Page<Driver> page = new PageImpl<>(list.subList(startP, endP), pageable, list.size());

            return ResponseEntity.ok().body(page);
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się pobrać listy pojazdów"));
        }
    }

    private void posortuj(List<Driver> filtered, String how)
    {
        //TODO sortowanie dokończyć
        if (how.contains("UNSORTED"))
            filtered.sort(Comparator.comparing(Driver::getId, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("id") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Driver::getId, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("id") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Driver::getId, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("name") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Driver::getName, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("name") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Driver::getName, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("end") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Driver::getSurname, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("end") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Driver::getSurname, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("pesel") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Driver::getPesel, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("pesel") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Driver::getPesel, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("address") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Driver::getAddress, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("address") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Driver::getAddress, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("dateOfBirth") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Driver::getDateOfBirth, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("dateOfBirth") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Driver::getDateOfBirth, Comparator.nullsLast(Comparator.naturalOrder())));

    }

    public ResponseEntity<?> searchDriver(String search, Pageable pageable) {
        try {

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

    public ResponseEntity<?> exportToExcel(MultiValueMap<String, String> params)
    {


        List<Driver> roads = getFilteredList(params);
        List<List<String>> exportRows = new ArrayList<>();
        exportRows.add(Arrays.asList("ID", "Imię", "Nazwisko", "PESEL", "Data urodzenia", "Adres", "Uprawnienia"));

        roads.forEach(entry->{
            List<String> row = new ArrayList<>();

            if (entry.getId() != null)
                row.add(entry.getId().toString());
            else
                row.add("");
            if (entry.getName() != null)
                row.add(entry.getName());
            else
                row.add("");
            if (entry.getSurname() != null)
                row.add(entry.getSurname());
            else
                row.add("");
            if (entry.getPesel() != null)
                row.add(String.valueOf(entry.getPesel()));
            else
                row.add("");
            if (entry.getDateOfBirth() != null)
                row.add(String.valueOf(entry.getDateOfBirth()));
            else
                row.add("");
            if (entry.getAddress() != null)
                row.add(entry.getAddress() );
            else
                row.add("");
            StringBuilder entitles = new StringBuilder("");

            for(EntitlementstToTransport en : entry.getEntitlementstToTransport()) {
                entitles.append(en.getTypeOfPermission().getName());
            }
                row.add(entitles.toString());


            exportRows.add(row);
        });
        Path exportFile;
        try {
            exportFile = Files.createTempFile("raport_kierowców" + System.currentTimeMillis(), ".xlsx");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystąpił błąd podczas tworzenia raportu (nie udało się utworzyc pliku)");
        }

        ExportExel.export(exportFile.toString(), exportRows, null,false);

        try {
            Resource resource = new UrlResource(exportFile.toUri());
            if (resource.exists()) {
                HttpHeaders headers = new HttpHeaders();

                headers.add("Content-Disposition",
                        "attachment; filename=raport_kierowców" + System.currentTimeMillis() + ".xlsx");
                return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).headers(headers).body(resource);
                //return resource;
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystąpił błąd podczas tworzenia raportu (pliki nie istnieje)");
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystąpił błąd podczas tworzenia raportu (błąd podczas przesyłania pliku)");
        }

    }


}
