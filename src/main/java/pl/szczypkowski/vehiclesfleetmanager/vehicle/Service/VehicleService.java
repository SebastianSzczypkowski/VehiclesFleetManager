package pl.szczypkowski.vehiclesfleetmanager.vehicle.Service;

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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import pl.szczypkowski.vehiclesfleetmanager.utils.ExportExel;
import org.springframework.util.MultiValueMap;
import pl.szczypkowski.vehiclesfleetmanager.utils.ExportExel;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.repository.VehicleRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(VehicleService.class);
    @PersistenceContext
    private EntityManager entityManager;

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAll() {
        try {

            return vehicleRepository.findAll();
        } catch (Exception e) {
            LOGGER.error("Błąd podczas pobierania listy pojazdów  wiadomość: {}",e.getMessage());

            return null;
        }
    }

    public Vehicle getOne(Long id) {
        try {
            return vehicleRepository.getById(id);
        }
        catch (Exception e) {
            LOGGER.error("Błąd podczas pobierania pojazdu o ID:{}| wiadomość: {}",id,e.getMessage());
            return null;
        }
    }

    public Vehicle getByRegistrationNumber(String vehicleRegistrationNumber) {
        try{

            if(!vehicleRegistrationNumber.equals(" "))
            {
                Optional<Vehicle> optional = vehicleRepository.getByRegistrationNumber(vehicleRegistrationNumber);
                return optional.orElse(null);
            }
            else {
                return null;
            }

        }catch (Exception e)
        {
            LOGGER.error("Błąd podczas pobierania pojazdu o numerze rejestracyjnym: {}| wiadomość: {}",vehicleRegistrationNumber,e.getMessage());

            return null;
        }
    }

    public ResponseEntity<?> getAllNotOccupied() {
        try {

            return ResponseEntity.ok().body(vehicleRepository.findAllByOccupiedFalse());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie można pobrać listy wolnych pojazdów"));
        }
    }

    @Transactional
    public ResponseEntity<?> save(Vehicle vehicle) {
        try {

            if (vehicle.getId() != null) {
                Optional<Vehicle> vehicleDb = vehicleRepository.findById(vehicle.getId());
                if (vehicleDb.isPresent()) {
                    if (vehicleDb.get().getName() == null || vehicle.getName() != null && !vehicleDb.get().getName().equals(vehicle.getName()))
                        vehicleDb.get().setName(vehicle.getName());
                    if (vehicleDb.get().getAverageFuelConsumption() == null || vehicle.getAverageFuelConsumption() != null && !vehicleDb.get().getAverageFuelConsumption().equals(vehicle.getAverageFuelConsumption()))
                        vehicleDb.get().setAverageFuelConsumption(vehicle.getAverageFuelConsumption());
                    if (vehicleDb.get().getCarLoadCapacity() == null || vehicle.getCarLoadCapacity() != null && !vehicleDb.get().getCarLoadCapacity().equals(vehicle.getCarLoadCapacity()))
                        vehicleDb.get().setCarLoadCapacity(vehicle.getCarLoadCapacity());
                    if (vehicleDb.get().getCarMileage() == null || vehicle.getCarMileage() != null && !vehicleDb.get().getCarMileage().equals(vehicle.getCarMileage()))
                        vehicleDb.get().setCarMileage(vehicle.getCarMileage());
                    if (vehicleDb.get().getEngineCapacity() == null || vehicle.getEngineCapacity() != null && !vehicleDb.get().getEngineCapacity().equals(vehicle.getEngineCapacity()))
                        vehicleDb.get().setEngineCapacity(vehicle.getEngineCapacity());
                    if (vehicleDb.get().getLorrySemitrailer() == null || vehicle.getLorrySemitrailer() != null && !vehicleDb.get().getLorrySemitrailer().equals(vehicle.getLorrySemitrailer()))
                        vehicleDb.get().setLorrySemitrailer(vehicle.getLorrySemitrailer());
                    if (vehicleDb.get().getCarLoadCapacity() == null || vehicle.getCarLoadCapacity() != null && !vehicleDb.get().getCarLoadCapacity().equals(vehicle.getCarLoadCapacity()))
                        vehicleDb.get().setCarLoadCapacity(vehicle.getCarLoadCapacity());
                    if (vehicleDb.get().getOccupied() == null || vehicle.getOccupied() != null && !vehicleDb.get().getOccupied().equals(vehicle.getOccupied()))
                        vehicleDb.get().setOccupied(vehicle.getOccupied());
                    if (vehicleDb.get().getNumberOfSeats() == null || vehicle.getNumberOfSeats() != null && !vehicleDb.get().getNumberOfSeats().equals(vehicle.getNumberOfSeats()))
                        vehicleDb.get().setNumberOfSeats(vehicle.getNumberOfSeats());
                    if (vehicleDb.get().getRegistrationNumber() == null || vehicle.getRegistrationNumber() != null && !vehicleDb.get().getRegistrationNumber().equals(vehicle.getRegistrationNumber()))
                        vehicleDb.get().setRegistrationNumber(vehicle.getRegistrationNumber());
                    if (vehicleDb.get().getVin() == null || vehicle.getVin() != null && !vehicleDb.get().getVin().equals(vehicle.getVin()))
                        vehicleDb.get().setVin(vehicle.getVin());
                    if (vehicleDb.get().getRoadworthy() == null || vehicle.getRoadworthy() != null && !vehicleDb.get().getRoadworthy().equals(vehicle.getRoadworthy()))
                        vehicleDb.get().setRoadworthy(vehicle.getRoadworthy());

                    Vehicle saved = vehicleRepository.save(vehicleDb.get());
                    return ResponseEntity.ok().body(saved);

                } else {
                    return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie znaleziono pojazdu ID: " + vehicle.getId()));
                }

            } else {

                vehicle.setOccupied(false);
                vehicle.setRoadworthy(true);
                Vehicle saved = vehicleRepository.save(vehicle);
                return ResponseEntity.ok().body(saved);

            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie duał się dodać pojazdu "));
        }
    }

//    public ResponseEntity<?> getAllPage(Pageable pageable) {
//        try {
//
//            Page<Vehicle> vehiclePage = vehicleRepository.findAll(pageable);
//            return ResponseEntity.ok().body(vehiclePage);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("NIe udało  się pobrać listy pojazdów"));
//        }
//    }

    public ResponseEntity<?> searchVehicle(String search,Pageable pageable) {
        try {

            SearchResult<Vehicle> result = Search.session(entityManager).search(
                    Vehicle.class).where(f->f.wildcard().fields("name","vin","registrationNumber").matching(
                            search+"*"
            )).fetchAll();

            List<Vehicle> results = result.hits();
            Set<Vehicle> vehicleSet = new HashSet<>(results);
            results = new ArrayList<>(vehicleSet);

            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), results.size());
            final Page<Vehicle> page = new PageImpl<>(results.subList(start, end), pageable, results.size());
            return ResponseEntity.ok().body(page);
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się znaleść wyników"));
        }

    }

    public List<Vehicle> getFilteredList(MultiValueMap<String, String> queryParams)
    {
        try {
            String idStr = Optional.ofNullable(queryParams.getFirst("id")).filter(val -> !val.isEmpty()).orElse(null);
            Long id = null;
            if (idStr != null)
                id = Long.parseLong(idStr);

            String name = Optional.ofNullable(queryParams.getFirst("name")).filter(val -> !val.isEmpty()).orElse(null);
            if (name != null) name = '%' + name.toLowerCase(Locale.ROOT) + '%';

            String vin = Optional.ofNullable(queryParams.getFirst("vin")).filter(val -> !val.isEmpty()).orElse(null);
            if (vin != null) vin = '%' + vin.toLowerCase(Locale.ROOT) + '%';

            String registrationNumber = Optional.ofNullable(queryParams.getFirst("registrationNumber")).filter(val -> !val.isEmpty()).orElse(null);
            if (registrationNumber != null)
                registrationNumber = '%' + registrationNumber.toLowerCase(Locale.ROOT) + '%';

            String carMileageStr = Optional.ofNullable(queryParams.getFirst("carMileage")).filter(val -> !val.isEmpty()).orElse(null);
            Integer carMileage = null;
            if (carMileageStr != null)
                carMileage = Integer.parseInt(carMileageStr);

            String carLoadCapacityStr = Optional.ofNullable(queryParams.getFirst("carLoadCapacity")).filter(val -> !val.isEmpty()).orElse(null);
            Integer carLoadCapacity = null;
            if (carLoadCapacityStr != null)
                carLoadCapacity = Integer.parseInt(carLoadCapacityStr);


            return vehicleRepository.findByColumnFilter(id, name, vin, registrationNumber, carMileage, carLoadCapacity);
        }catch (Exception e)
        {
            LOGGER.error("Wystąpił błąd podczas pobierania listy pojazdów");
            return null;
        }
    }


    public ResponseEntity<?> getAllPage(MultiValueMap<String, String> queryParams, Pageable pageable)
    {
        try
        {
            List<Vehicle> list =getFilteredList(queryParams);
            posortuj(list, pageable.getSort().toString().replace(":", ""));

            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), list.size());
            final Page<Vehicle> page = new PageImpl<>(list.subList(start, end), pageable, list.size());
            return ResponseEntity.ok().body(page);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ToJsonString.toJsonString("Błąd podczas pobierania danych! Komunikat: "+e.getMessage()));
        }
    }


    private void posortuj(List<Vehicle> filtered, String how)
    {
        if (how.contains("UNSORTED"))
            filtered.sort(Comparator.comparing(Vehicle::getId, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("id") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Vehicle::getId, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("id") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Vehicle::getId, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("name") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Vehicle::getName, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("name") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Vehicle::getName, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("vin") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Vehicle::getVin, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("vin") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Vehicle::getVin, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("registrationNumber") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Vehicle::getRegistrationNumber, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("registrationNumber") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Vehicle::getRegistrationNumber, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("carMileage") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Vehicle::getCarMileage, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("carMileage") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Vehicle::getCarMileage, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("carLoadCapacity") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Vehicle::getCarLoadCapacity, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("carLoadCapacity") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Vehicle::getCarLoadCapacity, Comparator.nullsLast(Comparator.naturalOrder())));
    }


    public ResponseEntity<?> exportToExcel(MultiValueMap<String, String> params)
    {


        List<Vehicle> vehicles = getFilteredList(params);
        List<List<String>> exportRows = new ArrayList<>();
        exportRows.add(Arrays.asList("ID", "Name", "VIN", "Registration Number", "Car Mileage", "Car LoadCapacity",
                "Lorry Semitrailer", "Number Of Seats", "Engine Capacity",  "Average Fuel Consumption", "Roadworthy", "Occupied"));

        vehicles.forEach(entry->{
            List<String> row = new ArrayList<>();

            if (entry.getId() != null)
                row.add(entry.getId().toString());
            else
                row.add("");
            if (entry.getName() != null)
                row.add(entry.getName());
            else
                row.add("");
            if (entry.getVin() != null)
                row.add(entry.getVin());
            else
                row.add("");
            if (entry.getRegistrationNumber() != null)
                row.add(entry.getRegistrationNumber());
            else
                row.add("");
            if (entry.getCarMileage() != null)
                row.add(entry.getCarMileage().toString());
            else
                row.add("");
            if (entry.getCarLoadCapacity() != null)
                row.add(entry.getCarLoadCapacity().toString());
            else
                row.add("");
            if (entry.getLorrySemitrailer() != null)
                row.add(entry.getLorrySemitrailer().toString());
            else
                row.add("");
            if (entry.getNumberOfSeats() != null)
                row.add(entry.getNumberOfSeats().toString());
            else
                row.add("");
            if (entry.getEngineCapacity() != null)
                row.add(entry.getEngineCapacity().toString());
            else
                row.add("");
            if (entry.getAverageFuelConsumption() != null)
                row.add(entry.getAverageFuelConsumption().toString());
            else
                row.add("");
            if (entry.getRoadworthy() != null)
                row.add(entry.getRoadworthy().toString());
            else
                row.add("");
            if (entry.getRoadworthy() != null)
                row.add(entry.getRoadworthy().toString());
            else
                row.add("");
            if (entry.getOccupied() != null)
                row.add(entry.getOccupied().toString());
            else
                row.add("");

            exportRows.add(row);
        });
        Path exportFile;
        try {
            exportFile = Files.createTempFile("vehicle_raport" + System.currentTimeMillis(), ".xlsx");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystąpił błąd podczas tworzenia raportu (nie udało się utworzyc pliku)");
        }

        ExportExel.export(exportFile.toString(), exportRows,null, false);

        try {
            Resource resource = new UrlResource(exportFile.toUri());
            if (resource.exists()) {
                HttpHeaders headers = new HttpHeaders();

                headers.add("Content-Disposition",
                        "attachment; filename=vehicle_raport" + System.currentTimeMillis() + ".xlsx");
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
