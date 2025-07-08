package joc.persistence;
import joc.model.Mutare;
import java.util.Optional;

public interface IMutareRepository extends IRepository<Integer, Mutare> {
    Optional<Mutare> save(Mutare mutare);
    Iterable<Mutare> findAllByJocId(Integer jocId);
    Iterable<Mutare> findAllByJucatorId(Integer jucatorId);
}
