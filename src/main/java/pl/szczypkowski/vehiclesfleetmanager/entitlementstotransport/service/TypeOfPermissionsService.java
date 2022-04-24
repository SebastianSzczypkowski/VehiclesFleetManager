package pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.model.TypeOfPermissions;
import pl.szczypkowski.vehiclesfleetmanager.entitlementstotransport.repository.TypeOfPermissionsRepository;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;

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

    public ResponseEntity<?> getAllPage(Pageable pageable) {
        try{

            return ResponseEntity.ok().body(typeOfPermissionsRepository.findAll(pageable));
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się pobrać listy rodzajów uprawnień"));
        }
    }
}
