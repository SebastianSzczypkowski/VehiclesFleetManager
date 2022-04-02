package pl.szczypkowski.vehiclesfleetmanager.road.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.road.model.Road;
import pl.szczypkowski.vehiclesfleetmanager.road.repository.RoadRepository;

import java.time.LocalDate;

@Service
public class RoadService {

    private final RoadRepository roadRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(RoadService.class);

    public RoadService(RoadRepository roadRepository) {
        this.roadRepository = roadRepository;
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
}
