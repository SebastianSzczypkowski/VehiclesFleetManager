package pl.szczypkowski.vehiclesfleetmanager.driver.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.DriverEvaluation;
import pl.szczypkowski.vehiclesfleetmanager.driver.repository.DriverEvaluationRepository;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

import java.util.Date;
import java.util.Optional;

@Service
public class DriverEvaluationService {

    private final Logger LOGGER = LoggerFactory.getLogger(DriverEvaluationService.class);
    private final DriverEvaluationRepository driverEvaluationRepository;


    public DriverEvaluationService(DriverEvaluationRepository driverEvaluationRepository) {
        this.driverEvaluationRepository = driverEvaluationRepository;
    }

    public ResponseEntity<?> saveOrUpdate(DriverEvaluation driverEvaluation)
    {
        try{

            if(driverEvaluation.getId()!=null)
            {
                Optional<DriverEvaluation> dbDriverEvaluation =driverEvaluationRepository.findById(driverEvaluation.getId());
                if(dbDriverEvaluation.isPresent()) {

                    DriverEvaluation updated = update(driverEvaluation,dbDriverEvaluation.get());
                    return ResponseEntity.ok().body(updated);
                }
                else
                {
                    return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się zaktualizować oceny kierowcy " +
                            "ponieważ ocena o przesłanym ID nie istnieje w bazie danych !"));
                }

            }
            else {

                DriverEvaluation saved = save(driverEvaluation);
                return ResponseEntity.ok().body(saved);
            }

        }catch (Exception e)
        {
            LOGGER.error("Wystąpił błąd podczas zapisywania oceny kierowcy wiadomość:{} ", e.getMessage());
            return null;
        }
    }

    @Transactional
    public DriverEvaluation save(DriverEvaluation driverEvaluation)
    {
        try{
            driverEvaluation.setDate(new Date());
            driverEvaluation.setUpdateDate(new Date());
            driverEvaluation.setValid(true);
            return driverEvaluationRepository.save(driverEvaluation);
        } catch (Exception e)
        {
            LOGGER.error("Wystąpił błąd podczas zapisywania oceny kierowcy wiadomość:{} ", e.getMessage());
            return null;
        }
    }

    @Transactional
    public DriverEvaluation update(DriverEvaluation driverEvaluation,DriverEvaluation dbDriverEvaluation)
    {
        try{

            if(driverEvaluation.getRate()!=null)
                dbDriverEvaluation.setRate(driverEvaluation.getRate());
            if(driverEvaluation.getDescription()!=null)
                dbDriverEvaluation.setDescription(driverEvaluation.getDescription());
            if(dbDriverEvaluation.getRoadId()!=null)
                dbDriverEvaluation.setRoadId(dbDriverEvaluation.getRoadId());
            dbDriverEvaluation.setUpdateDate(new Date());
            dbDriverEvaluation.setValid(true);
            return driverEvaluationRepository.save(dbDriverEvaluation);

        }catch (Exception e)
        {
            LOGGER.error("Wystąpił błąd podczas aktualizacji oceny kierowcy wiadomość:{} ", e.getMessage());
            return null;
        }

    }

    public ResponseEntity<?> getAllPage(Pageable pageable)
    {
        try{

            Page<DriverEvaluation> page = driverEvaluationRepository.findAll(pageable);
            return ResponseEntity.ok().body(page);

        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się pobrac danych"));
        }
    }

    public ResponseEntity<?> getByDriver(Pageable pageable,Long driver)
    {
        try{

           Page<DriverEvaluation> page= driverEvaluationRepository.findAllByRoadId_Driver_Id(driver,pageable);

           return ResponseEntity.ok().body(page);
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się pobrać oceny kierowcy"));
        }
    }


}
