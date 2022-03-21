package pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.model.TypeOfPermissions;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.repository.TypeOfPermissionsRepository;

import java.util.List;

@Service
public class TypeOfPermissionsService {

    private TypeOfPermissionsRepository typeOfPermissionsRepository;
    private Logger logger = LoggerFactory.getLogger(TypeOfPermissionsService.class);


    public TypeOfPermissionsService(TypeOfPermissionsRepository typeOfPermissionsRepository) {
        this.typeOfPermissionsRepository = typeOfPermissionsRepository;
    }

    public List<TypeOfPermissions> getAll()
    {
        return typeOfPermissionsRepository.findAll();
    }

    public TypeOfPermissions getOne(Long id) {
        try {
            return getOne(id);
        } catch (Exception e)
        {
            e.getStackTrace();
            logger.error("Nie znaleziono upranień o podanym ID: "+ id);
            return null;
        }

    }
}
