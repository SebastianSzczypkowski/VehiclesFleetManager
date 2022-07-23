package pl.szczypkowski.vehiclesfleetmanager.cargo.service;

import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.szczypkowski.vehiclesfleetmanager.cargo.model.Cargo;
import pl.szczypkowski.vehiclesfleetmanager.cargo.model.CargoRequest;
import pl.szczypkowski.vehiclesfleetmanager.cargo.repository.CargoRepository;
import pl.szczypkowski.vehiclesfleetmanager.utils.ToJsonString;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service
public class CargoService {

    private final Logger LOGGER = LoggerFactory.getLogger(CargoService.class);
    private final CargoRepository cargoRepository;
    @PersistenceContext
    private EntityManager entityManager;


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
    public Cargo getByName(String name)
    {
        try{
            if(!name.equals(" ")){
                Optional<Cargo> optional =cargoRepository.getByName(name);

                return optional.orElse(null);
            }else
            {
                return null;
            }

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

    public ResponseEntity<?> save(Cargo cargoRequest) {

        try{


           if(cargoRequest.getId()!=null)
           {
               Optional<Cargo> cargoDb= cargoRepository.findById(cargoRequest.getId());
               if(cargoDb.isPresent())
               {
                   if(cargoDb.get().getName()==null || cargoRequest.getName()!=null &&!cargoDb.get().getName().equals(cargoRequest.getName()))
                       cargoDb.get().setName(cargoRequest.getName());
                   if(cargoDb.get().getDescription()==null || cargoRequest.getDescription()!=null &&!cargoDb.get().getDescription().equals(cargoRequest.getDescription()))
                       cargoDb.get().setDescription(cargoRequest.getDescription());
                   if(cargoDb.get().getType()==null || cargoRequest.getType()!=null &&!cargoDb.get().getType().equals(cargoRequest.getType()))
                       cargoDb.get().setType(cargoRequest.getType());
                   if(cargoDb.get().getSensitivity()==null || cargoRequest.getSensitivity()!=null &&!cargoDb.get().getSensitivity().equals(cargoRequest.getSensitivity()))
                       cargoDb.get().setSensitivity(cargoRequest.getSensitivity());
                   if(cargoDb.get().getSpecialRemarks()==null || cargoRequest.getSpecialRemarks()!=null &&!cargoDb.get().getSpecialRemarks().equals(cargoRequest.getSpecialRemarks()))
                       cargoDb.get().setSpecialRemarks(cargoRequest.getSpecialRemarks());
                   if(cargoDb.get().getDelivered()==null || cargoRequest.getDelivered()!=null &&!cargoDb.get().getDelivered().equals(cargoRequest.getDelivered()))
                       cargoDb.get().setDelivered(cargoRequest.getDelivered());
                   if(cargoDb.get().getAssigned()==null || cargoRequest.getAssigned()!=null &&!cargoDb.get().getAssigned().equals(cargoRequest.getAssigned()))
                       cargoDb.get().setAssigned(cargoRequest.getAssigned());
                   if(cargoDb.get().getWeight()==null || cargoRequest.getWeight()!=null &&!cargoDb.get().getWeight().equals(cargoRequest.getWeight()))
                       cargoDb.get().setWeight(cargoRequest.getWeight());
                   if(cargoDb.get().getHeight()==null || cargoRequest.getHeight()!=null &&!cargoDb.get().getHeight().equals(cargoRequest.getHeight()))
                       cargoDb.get().setHeight(cargoRequest.getHeight());
                   if(cargoDb.get().getDepth()==null || cargoRequest.getDepth()!=null &&!cargoDb.get().getDepth().equals(cargoRequest.getDepth()))
                       cargoDb.get().setDepth(cargoRequest.getDepth());

                   Cargo saved =cargoRepository.save(cargoDb.get());
                   return ResponseEntity.ok().body(saved);

               }
               else
               {

                   return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie znaleziono ładunku o ID: "+cargoRequest.getId()));
               }


           }else {

               cargoRequest.setAssigned(false);
               cargoRequest.setDelivered(false);

               Cargo saved = cargoRepository.save(cargoRequest);
               return ResponseEntity.ok().body(saved);
           }

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

    public ResponseEntity<?> searchCargo(String search, Pageable pageable) {
        try {

            SearchSession searchSession = Search.session( entityManager );
            //TODO wyrzucić do pliku osobnego
            MassIndexer indexer = searchSession.massIndexer( Cargo.class )
                    .threadsToLoadObjects( 7 );
            indexer.startAndWait();

            SearchResult<Cargo> result = Search.session(entityManager).search(
                    Cargo.class).where(f->f.wildcard().fields("name","description","type","sensitivity",
                    "specialRemarks").matching(
                    search+"*"
            )).fetchAll();


            List<Cargo> results = result.hits();
            Set<Cargo> cargoSet = new HashSet<>(results);
            results = new ArrayList<>(cargoSet);

            final int start = (int)pageable.getOffset();
            final int end = Math.min((start + pageable.getPageSize()), results.size());
            final Page<Cargo> page = new PageImpl<>(results.subList(start, end), pageable, results.size());
            return ResponseEntity.ok().body(page);
        }catch (Exception e)
        {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(ToJsonString.toJsonString("Nie udało się znaleść wyników"));
        }

    }
}
