package joc.persistence;

import joc.model.Jucator;

import java.util.Optional;

public interface IJucatorRepository  extends IRepository<Integer, Jucator>{
    Optional findByName(String name);
}
