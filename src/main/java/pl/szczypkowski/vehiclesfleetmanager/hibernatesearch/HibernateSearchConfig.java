package pl.szczypkowski.vehiclesfleetmanager.hibernatesearch;

import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.massindexing.MassIndexer;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.szczypkowski.vehiclesfleetmanager.cargo.model.Cargo;
import pl.szczypkowski.vehiclesfleetmanager.driver.model.Driver;
import pl.szczypkowski.vehiclesfleetmanager.road.model.Road;
import pl.szczypkowski.vehiclesfleetmanager.vehicle.model.Vehicle;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Configuration
public class HibernateSearchConfig {

    private final Logger LOGGER = LoggerFactory.getLogger(HibernateSearchConfig.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    @Transactional
    public void indexServiceBean()
    {
        SearchSession searchSession = Search.session(entityManager);

        //TODO dodać dla tras
        MassIndexer indexer = searchSession.massIndexer(Driver.class, Cargo.class, Vehicle.class ).threadsToLoadObjects(10);
        LOGGER.warn("Wyszukiwanie - rozpoczynam indeksowanie danych...");
        try
        {
            LOGGER.warn("Wyszukiwanie może zwracać niepełne wyniki do czasu zakończenia procesu!");
            indexer.start().whenComplete((input, exception) -> LOGGER.warn("Wyszukiwanie - zakończono budowanie indeksów"));
        }
        catch (Exception e)
        {
            LOGGER.error("Przerwano indeksowanie danych do wyszukiwania", e);
        }
    }
}
