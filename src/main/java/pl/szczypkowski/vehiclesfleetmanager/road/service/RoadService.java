package pl.szczypkowski.vehiclesfleetmanager.road.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.road.model.Road;
import pl.szczypkowski.vehiclesfleetmanager.road.repository.RoadRepository;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

import java.time.LocalDate;
import java.util.List;

@Service
public class RoadService {

    private final RoadRepository roadRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(RoadService.class);

    public RoadService(RoadRepository roadRepository) {
        this.roadRepository = roadRepository;
    }


    public List<Road> getAll()
    {
        return roadRepository.findAll();
    }

    public void save (Long start,Long end)
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

    public ResponseEntity<?> getAllPage(Pageable pageable) {
        try{

            return ResponseEntity.ok().body(roadRepository.findAll(pageable));

        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało sie pobrać listy tras"));
        }
    }
}
