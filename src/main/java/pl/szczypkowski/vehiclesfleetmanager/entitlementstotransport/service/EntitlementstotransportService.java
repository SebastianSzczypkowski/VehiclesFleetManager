package pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.model.EntitlementstToTransport;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.repository.EntitlementstotransportRepository;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

import java.util.List;

@Service
public class EntitlementstotransportService {

    private EntitlementstotransportRepository entitlementstotransportRepository;
    private Logger LOGGER = LoggerFactory.getLogger(EntitlementstotransportService.class);


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
            LOGGER.error("Nie odnaleziono upranienia o ID "+id);
            return null;
        }
    }


    public EntitlementstToTransport save(EntitlementstToTransport entitlementstToTransport) {
        try{

           return entitlementstotransportRepository.save(entitlementstToTransport);
        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public ResponseEntity<?> getAllPage(Pageable pageable) {
        try{
            return ResponseEntity.ok().body(entitlementstotransportRepository.findAll(pageable));
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się pobrać listy uprawnień"));
        }
    }
}
