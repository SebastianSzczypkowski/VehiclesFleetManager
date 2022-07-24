package pl.szczypkowski.vehiclesfleetmanager.cargo.service;

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
import pl.szczypkowski.vehiclesfleetmanager.cargo.model.Cargo;
import pl.szczypkowski.vehiclesfleetmanager.cargo.model.CargoRequest;
import pl.szczypkowski.vehiclesfleetmanager.cargo.repository.CargoRepository;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;
import pl.szczypkowski.vehiclesfleetmanager.road.model.Road;
import pl.szczypkowski.vehiclesfleetmanager.utils.ExportExel;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class CargoService {

    private final Logger LOGGER = LoggerFactory.getLogger(CargoService.class);
    private final CargoRepository cargoRepository;
    @PersistenceContext
    private EntityManager entityManager;


    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public List<Cargo> getAll()
    {
        try{
            return cargoRepository.findAll();
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public Cargo getById(Long id)
    {
        try
        {
            if(id!=null)
                return cargoRepository.getById(id);
            else
                return null;

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public Cargo getByName(String name)
    {
        try{
            if(!name.equals(" ")){
                Optional<Cargo> optional =cargoRepository.getByName(name);

                return optional.orElse(null);
            }else
            {
                return null;
            }

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }



    public List<Cargo> getNotAssignedCargo()
    {
        try{

           // return cargoRepository.findAllByAssignedFalse();
            return null;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    public List<Cargo> getAssignedCargo()
    {
        try{

            //return cargoRepository.findAllByAssignedTrue();
            return null;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public List<Cargo> getAssignedButNotDeliveredCargo()
    {
        try{

           // return cargoRepository.finaAllByAssignedTrueAndDeliveredFalse();
            return null;
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<?> save(Cargo cargoRequest) {

        try{


           if(cargoRequest.getId()!=null)
           {
               Optional<Cargo> cargoDb= cargoRepository.findById(cargoRequest.getId());
               if(cargoDb.isPresent())
               {
                   if(cargoDb.get().getName()==null || cargoRequest.getName()!=null &&!cargoDb.get().getName().equals(cargoRequest.getName()))
                       cargoDb.get().setName(cargoRequest.getName());
                   if(cargoDb.get().getDescription()==null || cargoRequest.getDescription()!=null &&!cargoDb.get().getDescription().equals(cargoRequest.getDescription()))
                       cargoDb.get().setDescription(cargoRequest.getDescription());
                   if(cargoDb.get().getType()==null || cargoRequest.getType()!=null &&!cargoDb.get().getType().equals(cargoRequest.getType()))
                       cargoDb.get().setType(cargoRequest.getType());
                   if(cargoDb.get().getSensitivity()==null || cargoRequest.getSensitivity()!=null &&!cargoDb.get().getSensitivity().equals(cargoRequest.getSensitivity()))
                       cargoDb.get().setSensitivity(cargoRequest.getSensitivity());
                   if(cargoDb.get().getSpecialRemarks()==null || cargoRequest.getSpecialRemarks()!=null &&!cargoDb.get().getSpecialRemarks().equals(cargoRequest.getSpecialRemarks()))
                       cargoDb.get().setSpecialRemarks(cargoRequest.getSpecialRemarks());
                   if(cargoDb.get().getDelivered()==null || cargoRequest.getDelivered()!=null &&!cargoDb.get().getDelivered().equals(cargoRequest.getDelivered()))
                       cargoDb.get().setDelivered(cargoRequest.getDelivered());
                   if(cargoDb.get().getAssigned()==null || cargoRequest.getAssigned()!=null &&!cargoDb.get().getAssigned().equals(cargoRequest.getAssigned()))
                       cargoDb.get().setAssigned(cargoRequest.getAssigned());
                   if(cargoDb.get().getWeight()==null || cargoRequest.getWeight()!=null &&!cargoDb.get().getWeight().equals(cargoRequest.getWeight()))
                       cargoDb.get().setWeight(cargoRequest.getWeight());
                   if(cargoDb.get().getHeight()==null || cargoRequest.getHeight()!=null &&!cargoDb.get().getHeight().equals(cargoRequest.getHeight()))
                       cargoDb.get().setHeight(cargoRequest.getHeight());
                   if(cargoDb.get().getDepth()==null || cargoRequest.getDepth()!=null &&!cargoDb.get().getDepth().equals(cargoRequest.getDepth()))
                       cargoDb.get().setDepth(cargoRequest.getDepth());

                   Cargo saved =cargoRepository.save(cargoDb.get());
                   return ResponseEntity.ok().body(saved);

               }
               else
               {

                   return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie znaleziono ładunku o ID: "+cargoRequest.getId()));
               }


           }else {

               cargoRequest.setAssigned(false);
               cargoRequest.setDelivered(false);

               Cargo saved = cargoRepository.save(cargoRequest);
               return ResponseEntity.ok().body(saved);
           }

        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się dodać ładunku"));
        }
    }

    public List<Cargo> getFilteredList(MultiValueMap<String, String> queryParams)
    {
        try{
            String idStr = Optional.ofNullable(queryParams.getFirst("id")).filter(val -> !val.isEmpty()).orElse(null);
            Long id = null;
            if (idStr != null)
                id = Long.parseLong(idStr);

            String description = Optional.ofNullable(queryParams.getFirst("description")).filter(val -> !val.isEmpty()).orElse(null);
            if (description != null) description = '%' + description.toLowerCase(Locale.ROOT) + '%';

            String name = Optional.ofNullable(queryParams.getFirst("name")).filter(val -> !val.isEmpty()).orElse(null);
            if (name != null) name = '%' + name.toLowerCase(Locale.ROOT) + '%';

            String type = Optional.ofNullable(queryParams.getFirst("type")).filter(val -> !val.isEmpty()).orElse(null);
            if (type != null) type = '%' + type.toLowerCase(Locale.ROOT) + '%';

            String sensitivity = Optional.ofNullable(queryParams.getFirst("sensitivity")).filter(val -> !val.isEmpty()).orElse(null);
            if (sensitivity != null) sensitivity = '%' + sensitivity.toLowerCase(Locale.ROOT) + '%';

            String special_remarks = Optional.ofNullable(queryParams.getFirst("special_remarks")).filter(val -> !val.isEmpty()).orElse(null);
            if (special_remarks != null) special_remarks = '%' + special_remarks.toLowerCase(Locale.ROOT) + '%';

            String driver = Optional.ofNullable(queryParams.getFirst("driver")).filter(val -> !val.isEmpty()).orElse(null);
            if (driver != null) driver = '%' + driver.toLowerCase(Locale.ROOT) + '%';

            String assignedDateOd = Optional.ofNullable(queryParams.getFirst("assignedDateOd")).filter(val -> !val.isEmpty())
                    .orElse(null);
            if(assignedDateOd!=null)
                assignedDateOd = assignedDateOd.substring(0,assignedDateOd.indexOf("T"));

            String assignedDateDo = Optional.ofNullable(queryParams.getFirst("assignedDateDo")).filter(val -> !val.isEmpty())
                    .orElse(null);
            if(assignedDateDo!=null)
                assignedDateDo = assignedDateDo.substring(0,assignedDateDo.indexOf("T"));


            return cargoRepository.findByColumnFilter(id,name,description,type,sensitivity,special_remarks,driver,assignedDateOd,assignedDateDo);

        }catch (Exception e)
        {
            LOGGER.error("Wystąpił błąd podczas pobierania listy ładunków wiadomość: {}",e.getMessage());
            return null;
        }
    }

    public ResponseEntity<?> getAllPage(MultiValueMap<String, String> queryParams, Pageable pageable) {
        try{


            List<Cargo> list =//cargoRepository.findAll();
                    getFilteredList(queryParams);
            posortuj(list, pageable.getSort().toString().replace(":", ""));

            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), list.size());
            final Page<Cargo> page = new PageImpl<>(list.subList(start, end), pageable, list.size());

            return ResponseEntity.ok().body(page);
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się pobrać listy ładunków"));
        }
    }

    private void posortuj(List<Cargo> filtered, String how)
    {

        if (how.contains("UNSORTED"))
            filtered.sort(Comparator.comparing(Cargo::getId, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("id") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Cargo::getId, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("id") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Cargo::getId, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("name") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Cargo::getName, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("name") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Cargo::getName, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("description") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Cargo::getDescription, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("description") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Cargo::getDescription, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("type") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Cargo::getType, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("type") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Cargo::getType, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("sensitivity") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Cargo::getSensitivity, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("sensitivity") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Cargo::getSensitivity, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("special_remarks") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Cargo::getSpecialRemarks, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("special_remarks") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Cargo::getSpecialRemarks, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("assignedDate") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Cargo::getAssignedDate, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("assignedDate") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Cargo::getAssignedDate, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("driver") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(e->e.getDriver().getName(), Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("driver") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(e->e.getDriver().getName(), Comparator.nullsLast(Comparator.naturalOrder())));


    }

    public ResponseEntity<?> searchCargo(String search, Pageable pageable) {
        try {

            SearchResult<Cargo> result = Search.session(entityManager).search(
                    Cargo.class).where(f->f.wildcard().fields("name","description","type","sensitivity",
                    "specialRemarks").matching(
                    search+"*"
            )).fetchAll();


            List<Cargo> results = result.hits();
            Set<Cargo> cargoSet = new HashSet<>(results);
            results = new ArrayList<>(cargoSet);

            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), results.size());
            final Page<Cargo> page = new PageImpl<>(results.subList(start, end), pageable, results.size());
            return ResponseEntity.ok().body(page);
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się znaleść wyników"));
        }

    }

    public ResponseEntity<?> exportToExcel(MultiValueMap<String, String> params)
    {


        List<Cargo> roads = getFilteredList(params);
        List<List<String>> exportRows = new ArrayList<>();
        exportRows.add(Arrays.asList("ID", "Nazwa", "Opis", "Typ ładunku", "Wrażliwości",
                "Specjalne wytyczne", "Czy dostarczony", "Data dostarczenia","Kierowca","PESEL kierowcy","Wysokość","Szerokość","Głębokość","Waga"));

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
            if (entry.getDescription() != null)
                row.add(entry.getDescription());
            else
                row.add("");
            if (entry.getType() != null)
                row.add(entry.getType() );
            else
                row.add("");
            if (entry.getSensitivity() != null)
                row.add(entry.getSensitivity() );
            else
                row.add("");
            if (entry.getSpecialRemarks() != null)
                row.add(entry.getSpecialRemarks() );
            else
                row.add("");
            if (entry.getDelivered()!= null ) {
                if(entry.getDelivered())
                row.add("Dostarczono");
                else
                    row.add("Nie dostarczono");
            }
            else
                row.add("");

            if (entry.getDeliveredDate() != null)
                row.add(entry.getDeliveredDate().toString());
            else
                row.add("");
            if (entry.getDriver().getName() != null && entry.getDriver().getSurname() != null)
                row.add(entry.getDriver().getName()+" "+ entry.getDriver().getSurname() );
            else
                row.add("");
            if (entry.getDriver().getPesel() != null )
                row.add(String.valueOf(entry.getDriver().getPesel()));
            else
                row.add("");
            if (entry.getHeight() != null )
                row.add(String.valueOf(entry.getHeight()));
            else
                row.add("");
            if (entry.getWeight() != null )
                row.add(String.valueOf(entry.getWeight()));
            else
                row.add("");
            if (entry.getDepth() != null )
                row.add(String.valueOf(entry.getDepth()));
            else
                row.add("");
            if (entry.getWeight() != null )
                row.add(String.valueOf(entry.getWeight()));
            else
                row.add("");


            exportRows.add(row);
        });
        Path exportFile;
        try {
            exportFile = Files.createTempFile("raport_ładunków" + System.currentTimeMillis(), ".xlsx");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystąpił błąd podczas tworzenia raportu (nie udało się utworzyc pliku)");
        }

        ExportExel.export(exportFile.toString(), exportRows, false);

        try {
            Resource resource = new UrlResource(exportFile.toUri());
            if (resource.exists()) {
                HttpHeaders headers = new HttpHeaders();

                headers.add("Content-Disposition",
                        "attachment; filename=raport_ładunków" + System.currentTimeMillis() + ".xlsx");
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
