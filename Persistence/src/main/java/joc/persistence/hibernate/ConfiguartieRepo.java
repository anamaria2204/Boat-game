package joc.persistence.hibernate;

import joc.model.Configuratie;
import joc.persistence.IConfiguratieRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class ConfiguartieRepo implements IConfiguratieRepository {

    private static final Logger logger= LogManager.getLogger();

    @Override
    public Optional findOne(Integer id) {
        logger.traceEntry("findOne({})", id);
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            return Optional.ofNullable(session.find(Configuratie.class, id));
        }
        catch (Exception e) {
            logger.error("Error finding Configuratie with id {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Configuratie> findAll() {
        logger.traceEntry("findAll()");
         try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Configuratie ", Configuratie.class).getResultList();
        }
        catch (Exception e) {
            logger.error("Error finding all Jucatori: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<Configuratie> getRandomConfiguratie() {
        logger.traceEntry("getRandomConfiguratie()");
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<Configuratie> list = session.createQuery("FROM Configuratie", Configuratie.class).getResultList();
            if (list.isEmpty()) {
                return Optional.empty();
            }
            Collections.shuffle(list);
            logger.traceExit("Returning random Configuratie: {}", list.get(0));
            return Optional.of(list.get(0));
        } catch (Exception e) {
            logger.error("Error getting random Configuratie: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Configuratie> save(Configuratie configuratie) {
        logger.traceEntry("save({})", configuratie);
        try {
            HibernateUtils.getSessionFactory().inTransaction(session -> {
                session.persist(configuratie);
                session.flush();
            });
            logger.traceExit("Configuratie saved successfully: {}", configuratie);
            return Optional.of(configuratie);
        } catch (Exception e) {
            logger.error("Error saving Configuratie: {}", e.getMessage());
            return Optional.empty();
        }
    }
}
