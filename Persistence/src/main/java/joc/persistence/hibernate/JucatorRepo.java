package joc.persistence.hibernate;


import joc.model.Jucator;
import joc.persistence.IJucatorRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class JucatorRepo implements IJucatorRepository {

    private static final Logger logger= LogManager.getLogger();

    @Override
    public Optional findOne(Integer id) {
        logger.traceEntry("findOne({})", id);
        try(Session session = HibernateUtils.getSessionFactory().openSession()){
            return Optional.ofNullable(session.find(Jucator.class, id));
        }
        catch (Exception e) {
            logger.error("Error finding Jucator with id {}: {}", id, e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Jucator> findAll() {
        logger.traceEntry("findAll()");
         try(Session session = HibernateUtils.getSessionFactory().openSession()) {
            return session.createQuery("FROM Jucator", Jucator.class).getResultList();
        }
        catch (Exception e) {
            logger.error("Error finding all Jucatori: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public Optional findByName(String nume) {
         try (Session session = HibernateUtils.getSessionFactory().openSession()) {
        return session.createQuery("FROM Jucator WHERE nume = :nume", Jucator.class)
                      .setParameter("nume", nume)
                      .uniqueResultOptional();
    }
    }
}
