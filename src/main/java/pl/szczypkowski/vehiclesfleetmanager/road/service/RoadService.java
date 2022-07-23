package pl.szczypkowski.vehiclesfleetmanager.road.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;
import pl.szczypkowski.vehiclesfleetmanager.cargo.service.CargoService;
import pl.szczypkowski.vehiclesfleetmanager.driver.service.DriverService;
import pl.szczypkowski.vehiclesfleetmanager.map.model.Coordinates;
import pl.szczypkowski.vehiclesfleetmanager.map.repository.CoordinatesRepository;
import pl.szczypkowski.vehiclesfleetmanager.map.service.CoordinatesService;
import pl.szczypkowski.vehiclesfleetmanager.road.model.Road;
import pl.szczypkowski.vehiclesfleetmanager.road.model.RoadFromExel;
import pl.szczypkowski.vehiclesfleetmanager.road.repository.RoadRepository;
import pl.szczypkowski.vehiclesfleetmanager.utils.ExportExel;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.Service.VehicleService;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

@Service
public class RoadService {

    private final RoadRepository roadRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(RoadService.class);
    private final CargoService cargoService;
    private final DriverService driverService;
    private final VehicleService vehicleService;
    private final CoordinatesService coordinatesService;


    public RoadService(RoadRepository roadRepository, CargoService cargoService, DriverService driverService, VehicleService vehicleService,  CoordinatesService coordinatesService) {
        this.roadRepository = roadRepository;
        this.cargoService = cargoService;
        this.driverService = driverService;
        this.vehicleService = vehicleService;
        this.coordinatesService = coordinatesService;
    }


    public List<Road> getAll()
    {
        return roadRepository.findAll();
    }

    @Transactional
    public void saveFromCoordinates (Coordinates start, Coordinates end)
    {
        try
        {
            Road road = new Road();
            road.setStart(start);
            road.setEnd(end);
            road.setCreationDate(LocalDate.now());
            road.setUpdateDate(LocalDate.now());
            roadRepository.save(road);

        }catch (Exception e)
        {
            LOGGER.error("NIe udało się zapisać trasy");
        }
    }
    @Transactional
    public Road save (Road road)
    {
        try
        {
           if(road!=null) {
               road.setCreationDate(LocalDate.now());
               road.setUpdateDate(LocalDate.now());
               road.setFinished(false);
              return roadRepository.save(road);
           }
           else {
               LOGGER.error("Nie udało się zapisać trasy ponieważ przesłany objekt jest pusty");
               return null;
           }

        }catch (Exception e)
        {
            LOGGER.error("Nie udało się zapisać trasy wiadomość: {}",e.getMessage());
            return null;
        }
    }

    public List<Road> getFilteredList(MultiValueMap<String, String> queryParams)
    {
        try {
            String idStr = Optional.ofNullable(queryParams.getFirst("id")).filter(val -> !val.isEmpty()).orElse(null);
            Long id = null;
            if (idStr != null)
                id = Long.parseLong(idStr);

            String start = Optional.ofNullable(queryParams.getFirst("start")).filter(val -> !val.isEmpty()).orElse(null);
            if (start != null) start = '%' + start.toLowerCase(Locale.ROOT) + '%';

            String end = Optional.ofNullable(queryParams.getFirst("end")).filter(val -> !val.isEmpty()).orElse(null);
            if (end != null) end = '%' + end.toLowerCase(Locale.ROOT) + '%';

            String driver = Optional.ofNullable(queryParams.getFirst("driver")).filter(val -> !val.isEmpty()).orElse(null);
            if (driver != null) driver = '%' + driver.toLowerCase(Locale.ROOT) + '%';

            String cargo = Optional.ofNullable(queryParams.getFirst("cargo")).filter(val -> !val.isEmpty()).orElse(null);
            if (cargo != null) cargo = '%' + cargo.toLowerCase(Locale.ROOT) + '%';

            String vehicle = Optional.ofNullable(queryParams.getFirst("vehicle")).filter(val -> !val.isEmpty()).orElse(null);
            if (vehicle != null) vehicle = '%' + vehicle.toLowerCase(Locale.ROOT) + '%';

            String dataOd = Optional.ofNullable(queryParams.getFirst("dataOd")).filter(val -> !val.isEmpty())
                    .orElse(null);
            if (dataOd != null)
                dataOd = dataOd.substring(0, dataOd.indexOf("T"));

            String dataDo = Optional.ofNullable(queryParams.getFirst("dataDo")).filter(val -> !val.isEmpty())
                    .orElse(null);
            if (dataDo != null)
                dataDo = dataDo.substring(0, dataDo.indexOf("T"));

            return roadRepository.findByColumnFilter(id, start, end, driver, cargo, vehicle, dataOd, dataDo);
        }catch (Exception e)
        {
            LOGGER.error("Wystąpił błąd podczas pobierania listy tras wiadomość: {}",e.getMessage());
            return null;
        }
    }

    public ResponseEntity<?> getAllPage(MultiValueMap<String, String> queryParams, Pageable pageable) {
        try{

            List<Road> list= getFilteredList(queryParams);
            posortuj(list, pageable.getSort().toString().replace(":", ""));

            final int startP = (int)pageable.getOffset();
            final int endP = Math.min((startP + pageable.getPageSize()), list.size());
            final Page<Road> page = new PageImpl<>(list.subList(startP, endP), pageable, list.size());

            return ResponseEntity.ok().body(page);

        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało sie pobrać listy tras"));
        }
    }

    private void posortuj(List<Road> filtered, String how)
    {
        //TODO sortowanie dokończyć
        if (how.contains("UNSORTED"))
            filtered.sort(Comparator.comparing(Road::getId, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("id") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(Road::getId, Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("id") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(Road::getId, Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("start") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(e->e.getStart().getName(), Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("start") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(e->e.getStart().getName(), Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("end") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(e->e.getEnd().getName(), Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("end") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(e->e.getEnd().getName(), Comparator.nullsLast(Comparator.naturalOrder())));
        if (how.contains("driver") && how.contains("DESC"))
            filtered.sort(Comparator.comparing(e->e.getDriver().getName(), Comparator.nullsLast(Comparator.reverseOrder())));
        if (how.contains("driver") && how.contains("ASC"))
            filtered.sort(Comparator.comparing(e->e.getDriver().getName(), Comparator.nullsLast(Comparator.naturalOrder())));

    }

    public ResponseEntity<?> saveRoad(Road road) {

        try{

            //TODO dodać kierowce
            if(road!=null)
            {

                Coordinates startDb =null;
                if(road.getStart()!=null)
                    startDb =coordinatesService.save(road.getStart());
                Coordinates endDb=null ;
                if(road.getEnd()!=null)
                    endDb=coordinatesService.save(road.getEnd());


                if(road.getId()!=null)
                {
                    Optional<Road> dbRoad =roadRepository.findById(road.getId());
                    if(dbRoad.isPresent())
                    {
                        if( road.getCargo()!=null ) {
                            dbRoad.get().setCargo(road.getCargo());
                            dbRoad.get().getCargo().setAssigned(true);
                            dbRoad.get().getCargo().setAssignedDate(new Date());
                            cargoService.save(dbRoad.get().getCargo());

                        }
                        if(road.getDriver()!=null) {
                            dbRoad.get().setDriver(road.getDriver());
                        }
                        if(startDb!=null)
                            dbRoad.get().setStart(startDb);
                        if(endDb!=null)
                            dbRoad.get().setEnd(endDb);
                        if(road.getVehicle()!=null)
                        {
                            dbRoad.get().setVehicle(road.getVehicle());
                            dbRoad.get().getVehicle().setOccupied(true);
                        }

                        Road saved =save(dbRoad.get());
                        return ResponseEntity.ok().body(saved);
                    }
                    else {
                        return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało sie zaktualizować trasy od ID:"+road.getId()));
                    }

                }else
                {
                    if(startDb!=null)
                        road.setStart(startDb);
                    if(endDb!=null)
                        road.setEnd(endDb);
                    if(road.getVehicle()!=null) {
                        road.getVehicle().setOccupied(true);
                        vehicleService.save(road.getVehicle());
                    }
                    if(road.getCargo()!=null)
                    {
                        road.getCargo().setAssigned(true);
                        road.getCargo().setAssignedDate(new Date());
                        cargoService.save(road.getCargo());
                    }
                    Road saved = save(road);
                    return ResponseEntity.ok().body(saved);
                }

            }
            else
            {
                return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało sie zapisać trasy [puste dane]"));
            }


        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało sie zapisać trasy"));
        }
    }



    public ResponseEntity<?> createFromExisting(Long id) {
        try{
            if(id!=null) {
                Optional<Road> optionalRoad = roadRepository.findById(id);
                if(optionalRoad.isPresent())
                {
                    //TODO kopiowanie
                    Road fromExisting = optionalRoad.get();
                    fromExisting.setId(null);


                    return ResponseEntity.ok().body(save(fromExisting));
                }
                else
                {
                    return ResponseEntity.internalServerError().body(ToJsonString.toJsonString("Nie znaleziono trasy o id:"+id)) ;
                }
            }
            else {
                return ResponseEntity.internalServerError().body(ToJsonString.toJsonString("Podane id ma wartość null"));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ToJsonString.toJsonString("Nie udało się stworzyć trasy"));
        }
    }




    public ResponseEntity<?> createFromXlsx(MultipartFile file) {
        try{

            if(file !=null)
            {
                InputStream in = file.getInputStream();
                File currDir = new File(".");
                String path = currDir.getAbsolutePath();
                String fileLocation = path.substring(0, path.length() - 1) + file.getOriginalFilename();
                FileOutputStream f = new FileOutputStream(fileLocation);
                int ch = 0;
                while ((ch = in.read()) != -1) {
                    f.write(ch);
                }
                f.flush();
                f.close();
                List<String> headers = new ArrayList<>();

                List<RoadFromExel> roadsFromXlsx = new ArrayList<>();
                XSSFWorkbook myWorkBook = new XSSFWorkbook(fileLocation);
                XSSFSheet mySheet = myWorkBook.getSheetAt(0);

                for (Row row : mySheet) {
                    Iterator<Cell> cellIterator = row.cellIterator();
                    List<String> dataFromExel = new ArrayList<>();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        dataFromExel.add(cell.getStringCellValue());
                        //TODO wyciąganie danych  + dodawanie do listy roadsFromXlsx

                    }
                    //TODO obsłużyć puste wartości dla dowolnych pól
                    RoadFromExel roadFromExel = new RoadFromExel(dataFromExel.get(0), dataFromExel.get(1)
                            , dataFromExel.get(2), dataFromExel.get(3), dataFromExel.get(4));

                    roadsFromXlsx.add(roadFromExel);
                }

                File raport = new File("raport_"+System.currentTimeMillis()+"txt");
                List<String> fails = new ArrayList<>();
                int counter =0;
                //TODO zapisz liste pozyskanych tras
                roadsFromXlsx.forEach(this::saveRoadFromExel);


                return ResponseEntity.status(HttpStatus.OK).body(ToJsonString.toJsonString("Zapisano trays z pliku xlsx w ilości :"
                        +counter+" z "+roadsFromXlsx.size()+" odczytanych"));
            }
            else
            {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ToJsonString.toJsonString("Przesłany plik jest pusty"));
            }

        }catch (Exception e)
        {
            LOGGER.error("Nie udało się stworzyć trasy z pliku xlsx wiadomość: {}",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ToJsonString.toJsonString("Nie udało się stworzyc trasy na podstawie przesłanego pliku xlsx"));
        }
    }

    @Transactional
    public Road saveRoadFromExel(RoadFromExel roadFromExel)
    {
        try{

            Road toSave = new Road();
            toSave.setFinished(false);
            toSave.setCreationDate(LocalDate.now());
            toSave.setUpdateDate(LocalDate.now());
            toSave.setCargo(cargoService.getByName(roadFromExel.getCargoName()));
            toSave.setVehicle(vehicleService.getByRegistrationNumber(roadFromExel.getVehicleRegistrationNumber()));
            toSave.setDriver(driverService.getByDriverPESEL(roadFromExel.getDriverPESEL()));
            //TODO coordinbates service;
            toSave.setStart(coordinatesService.getCoordinates(roadFromExel.getStartLocation(),null,null));
            toSave.setStart(coordinatesService.getCoordinates(roadFromExel.getEndLocation(),null,null));
            return roadRepository.save(toSave);

        }
        catch (Exception e)
        {

            LOGGER.error("Wystąpił błąd podczas zapisu trasy wiadomosc {}",e.getMessage());
            return null;
        }

    }


    public ResponseEntity<?> exportToExcel(MultiValueMap<String, String> params)
    {


        List<Road> roads = getFilteredList(params);
        List<List<String>> exportRows = new ArrayList<>();
        exportRows.add(Arrays.asList("ID", "Start", "Koniec", "Nazwa ładunku", "Nazwa pojazdu", "Numer rejestracyjny pojazdu",
                "Kierowca", "PESEL kierowcy", "Data utworzenia"));

        roads.forEach(entry->{
            List<String> row = new ArrayList<>();

            if (entry.getId() != null)
                row.add(entry.getId().toString());
            else
                row.add("");
            if (entry.getStart().getName() != null)
                row.add(entry.getStart().getName());
            else
                row.add("");
            if (entry.getEnd().getName() != null)
                row.add(entry.getEnd().getName());
            else
                row.add("");
            if (entry.getCargo().getName() != null)
                row.add(entry.getCargo().getName() );
            else
                row.add("");
            if (entry.getVehicle().getName() != null)
                row.add(entry.getVehicle().getName() );
            else
                row.add("");
            if (entry.getVehicle().getRegistrationNumber() != null)
                row.add(entry.getVehicle().getRegistrationNumber() );
            else
                row.add("");
            if (entry.getDriver().getName() != null && entry.getDriver().getSurname() != null)
                row.add(entry.getDriver().getName()+" "+entry.getDriver().getSurname());
            else
                row.add("");
            if (entry.getDriver().getPesel() != null )
                row.add(String.valueOf(entry.getDriver().getPesel()));
            else
                row.add("");
            if (entry.getCreationDate() != null)
                row.add(entry.getCreationDate().toString());
            else
                row.add("");


            exportRows.add(row);
        });
        Path exportFile;
        try {
            exportFile = Files.createTempFile("raport_tras" + System.currentTimeMillis(), ".xlsx");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Wystąpił błąd podczas tworzenia raportu (nie udało się utworzyc pliku)");
        }

        ExportExel.export(exportFile.toString(), exportRows, false);

        try {
            Resource resource = new UrlResource(exportFile.toUri());
            if (resource.exists()) {
                HttpHeaders headers = new HttpHeaders();

                headers.add("Content-Disposition",
                        "attachment; filename=raport_tras" + System.currentTimeMillis() + ".xlsx");
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
