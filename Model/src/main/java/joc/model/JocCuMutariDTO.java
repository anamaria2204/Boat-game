package joc.model;

import java.time.LocalDateTime;
import java.util.List;

public class JocCuMutariDTO {
    private int jocId;
    private LocalDateTime startTime;
    private int scor;
    private int pozitiiGhicite;
    private Configuratie configuratie;
    private List<Mutare> mutari;

    public int getJocId() {
        return jocId;
    }

    public void setJocId(int jocId) {
        this.jocId = jocId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public int getScor() {
        return scor;
    }

    public void setScor(int scor) {
        this.scor = scor;
    }

    public int getPozitiiGhicite() {
        return pozitiiGhicite;
    }

    public void setPozitiiGhicite(int pozitiiGhicite) {
        this.pozitiiGhicite = pozitiiGhicite;
    }

    public Configuratie getConfiguratie() {
        return configuratie;
    }

    public void setConfiguratie(Configuratie configuratie) {
        this.configuratie = configuratie;
    }

    public List<Mutare> getMutari() {
        return mutari;
    }

    public void setMutari(List<Mutare> mutari) {
        this.mutari = mutari;
    }
}
