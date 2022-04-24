package pl.szczypkowski.vehiclesfleetmanager.cargo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.cargo.model.Cargo;
import pl.szczypkowski.vehiclesfleetmanager.cargo.model.CargoRequest;
import pl.szczypkowski.vehiclesfleetmanager.cargo.repository.CargoRepository;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

import java.util.List;

@Service
public class CargoService {

    private final Logger LOGGER = LoggerFactory.getLogger(CargoService.class);
    private final CargoRepository cargoRepository;


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

    public ResponseEntity<?> save(CargoRequest cargoRequest) {

        try{
            Cargo cargo = new Cargo();
            if(cargoRequest.getName()!=null)
                cargo.setName(cargoRequest.getName());
            if(cargoRequest.getDescription()!=null)
                cargo.setDescription(cargoRequest.getDescription());
            if(cargoRequest.getSensitivity()!=null)
                cargo.setSensitivity(cargoRequest.getSensitivity());
            if(cargoRequest.getDepth()!=null)
                cargo.setDepth(cargoRequest.getDepth());
            if(cargoRequest.getHeight()!=null)
                cargo.setHeight(cargoRequest.getHeight());
            if(cargoRequest.getWeight()!=null)
                cargo.setWeight(cargoRequest.getWeight());
            if(cargoRequest.getWidth()!=null)
                cargo.setWidth(cargoRequest.getWidth());
            if(cargoRequest.getSpecialRemarks()!=null)
                cargo.setSpecialRemarks(cargoRequest.getSpecialRemarks());
            cargo.setAssigned(false);
            cargo.setDelivered(false);

            //TODO przypisywanie typu ładunku
            //cargo.setType();

            Cargo saved =cargoRepository.save(cargo);
            return ResponseEntity.ok().body(saved);

        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się dodać ładunku"));
        }
    }

    public ResponseEntity<?> getAllPage(Pageable pageable) {
        try{

            return ResponseEntity.ok().body(cargoRepository.findAll(pageable));
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się pobrać listy ładunków"));
        }
    }
}
