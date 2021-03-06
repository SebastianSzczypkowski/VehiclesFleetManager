package pl.szczypkowski.vehiclesfleetmanager.vehicle.Service;


import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.VehicleInspection;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.repository.VehicleInspectionRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class VehicleInspectionService {

    private final VehicleInspectionRepository vehicleInspectionRepository;
    @PersistenceContext
    private EntityManager entityManager;


    public VehicleInspectionService(VehicleInspectionRepository vehicleInspectionRepository) {
        this.vehicleInspectionRepository = vehicleInspectionRepository;
    }

    public List<VehicleInspection> getALl()
    {
        return vehicleInspectionRepository.findAll();
    }

    @Transactional
    public VehicleInspection save(VehicleInspection vehicleInspection)
    {
        try{

            return vehicleInspectionRepository.save(vehicleInspection);

        }catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }


    public ResponseEntity<?> getAllPage(Pageable pageable) {
        try{

            return ResponseEntity.ok().body(vehicleInspectionRepository.findAll(pageable));
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie uda??o sie pobra?? listy przegl??d??w technicznych"));
        }
    }

    public ResponseEntity<?> searchVehicleInspection(String search, Pageable pageable) {

        try {
            SearchSession searchSession = Search.session( entityManager );

            MassIndexer indexer = searchSession.massIndexer( VehicleInspection.class )
                    .threadsToLoadObjects( 7 );
            indexer.startAndWait();

            SearchResult<VehicleInspection> result = Search.session(entityManager).search(
                    VehicleInspection.class).where(f->f.wildcard().fields("carRepairShopName","description","performedBy").matching(
                    search+"*"
            )).fetchAll();


            List<VehicleInspection> results = result.hits();
            Set<VehicleInspection> vehicleSet = new HashSet<>(results);
            results = new ArrayList<>(vehicleSet);

            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), results.size());
            final Page<VehicleInspection> page = new PageImpl<>(results.subList(start, end), pageable, results.size());
            return ResponseEntity.ok().body(page);
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie uda??o si?? znale???? wynik??w"));
        }

    }
}
