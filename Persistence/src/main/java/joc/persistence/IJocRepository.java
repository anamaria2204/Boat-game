package joc.persistence;

import joc.model.Joc;

import java.util.Optional;

public interface IJocRepository extends IRepository<Integer, Joc>{
    Optional<Joc> findById(Integer id);
    Optional<Joc> save(Joc joc);
}

