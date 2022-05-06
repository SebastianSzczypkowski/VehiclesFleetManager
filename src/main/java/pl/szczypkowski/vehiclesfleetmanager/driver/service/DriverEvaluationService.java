package pl.szczypkowski.vehiclesfleetmanager.driver.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.DriverEvaluation;
import pl.szczypkowski.vehiclesfleetmanager.driver.repository.DriverEvaluationRepository;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

import java.util.Date;
import java.util.Optional;

@Service
public class DriverEvaluationService {

    private final DriverEvaluationRepository driverEvaluationRepository;


    public DriverEvaluationService(DriverEvaluationRepository driverEvaluationRepository) {
        this.driverEvaluationRepository = driverEvaluationRepository;
    }

    public ResponseEntity<?> save(DriverEvaluation driverEvaluation)
    {
        try{

            if(driverEvaluation.getId()!=null)
            {
                Optional<DriverEvaluation> dbDriverEvaluation =driverEvaluationRepository.findById(driverEvaluation.getId());
                if(dbDriverEvaluation.isPresent()) {

                    if(driverEvaluation.getRate()!=null)
                    dbDriverEvaluation.get().setRate(driverEvaluation.getRate());
                    dbDriverEvaluation.get().setUpdateDate(new Date());
                    DriverEvaluation saved =driverEvaluationRepository.save(dbDriverEvaluation.get());
                    return ResponseEntity.ok().body(saved);

                }
                else
                {
                    return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się zaktualizować oceny kierowcy"));
                }

            }
            else {
                driverEvaluation.setDate(new Date());
                driverEvaluation.setUpdateDate(new Date());
                driverEvaluation.setValid(true);
                DriverEvaluation saved = driverEvaluationRepository.save(driverEvaluation);

                return ResponseEntity.ok().body(saved);

            }

        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się dodac oceny kierowcy"));
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
