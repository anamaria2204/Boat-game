package joc.persistence.hibernate;

import joc.model.Mutare;
import joc.persistence.IMutareRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class MutareRepo implements IMutareRepository {

    private static final Logger logger = LogManager.getLogger();

    @Override
    public Optional<Mutare> save(Mutare mutare) {
        logger.traceEntry("Saving mutare: {}", mutare);
        Transaction tx = null;
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.persist(mutare);
            tx.commit();
            logger.traceExit("Saved mutare with id: {}", mutare.getId());
            return Optional.of(mutare);
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            logger.error("Error saving mutare: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Mutare> findAllByJocId(Integer jocId) {
        logger.traceEntry("Finding mutari for jocId: {}", jocId);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<Mutare> result = session
                    .createQuery("FROM Mutare m WHERE m.jocId = :jocId", Mutare.class)
                    .setParameter("jocId", jocId)
                    .getResultList();
            return result;
        } catch (Exception e) {
            logger.error("Error finding mutari for jocId {}: {}", jocId, e.getMessage(), e);
            return List.of(); // evită null
        }
    }

    @Override
    public Iterable<Mutare> findAllByJucatorId(Integer jucatorId) {
        logger.traceEntry("Finding mutari for jucatorId: {}", jucatorId);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<Mutare> result = session
                    .createQuery("FROM Mutare m WHERE m.jucatorId = :jucatorId", Mutare.class)
                    .setParameter("jucatorId", jucatorId)
                    .getResultList();
            return result;
        } catch (Exception e) {
            logger.error("Error finding mutari for jucatorId {}: {}", jucatorId, e.getMessage(), e);
            return List.of(); // evită null
        }
    }


    @Override
    public Optional<Mutare> findOne(Integer id) {
        logger.traceEntry("Finding mutare with id: {}", id);
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            Mutare mutare = session.find(Mutare.class, id);
            return Optional.ofNullable(mutare);
        } catch (Exception e) {
            logger.error("Error finding mutare with id {}: {}", id, e.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public Iterable<Mutare> findAll() {
        logger.traceEntry("Finding all mutari");
        try (Session session = HibernateUtils.getSessionFactory().openSession()) {
            List<Mutare> result = session.createQuery("FROM Mutare", Mutare.class).getResultList();
            logger.traceExit("Found {} mutari", result.size());
            return result;
        } catch (Exception e) {
            logger.error("Error finding all mutari: {}", e.getMessage(), e);
            return List.of(); // avoid returning null
        }
    }
}
