package pl.szczypkowski.vehiclesfleetmanager.cargo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.cargo.model.Cargo;
import pl.szczypkowski.vehiclesfleetmanager.cargo.repository.CargoRepository;

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

}
