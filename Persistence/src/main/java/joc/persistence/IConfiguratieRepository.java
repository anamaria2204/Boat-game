package joc.persistence;

import joc.model.Configuratie;

import java.util.Optional;

public interface IConfiguratieRepository extends IRepository<Integer, Configuratie> {
    Optional<Configuratie> getRandomConfiguratie();
    Optional<Configuratie> save (Configuratie configuratie);
}