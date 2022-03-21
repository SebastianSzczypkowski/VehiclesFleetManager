package pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.model.EntitlementstToTransport;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.repository.EntitlementstotransportRepository;

import java.util.List;

@Service
public class EntitlementstotransportService {

    private EntitlementstotransportRepository entitlementstotransportRepository;
    private Logger logger = LoggerFactory.getLogger(EntitlementstotransportService.class);


    public EntitlementstotransportService(EntitlementstotransportRepository entitlementstotransportRepository) {
        this.entitlementstotransportRepository = entitlementstotransportRepository;
    }

    public List<EntitlementstToTransport> getAll()
    {

        return entitlementstotransportRepository.findAll();
    }

    public EntitlementstToTransport getOne(Long id) {
        try {
            return entitlementstotransportRepository.getById(id);
        } catch (Exception e)
        {
            e.printStackTrace();
            logger.error("Nie odnaleziono upranienia o ID "+id);
            return null;
        }
    }


}
