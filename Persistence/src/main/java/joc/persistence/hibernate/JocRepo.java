package joc.persistence.hibernate;

import joc.model.Joc;
import joc.persistence.IJocRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class JocRepo implements IJocRepository {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<Joc> findById(Integer id) {
        logger.traceEntry("findById({})", id);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Joc joc = session.find(Joc.class, id);
            return Optional.ofNullable(joc);
        } catch (Exception e) {
            logger.error("Error in findById: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Joc> save(Joc joc) {
        logger.traceEntry("save({})", joc);
        try {
            HibernateUtils.getSessionFactory().inTransaction(session -> {
                session.merge(joc);
                session.flush();
            });
            logger.traceExit("Saved joc: {}", joc);
            return Optional.of(joc);
        } catch (Exception e) {
            logger.error("Error saving Joc: {}", e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Joc> findOne(Integer id) {
        return findById(id);
    }

    @Override
    public Iterable<Joc> findAll() {
        logger.traceEntry("findAll()");
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<Joc> jocuri = session.createQuery("FROM Joc", Joc.class).getResultList();
            return jocuri;
        } catch (Exception e) {
            logger.error("Error in findAll: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
