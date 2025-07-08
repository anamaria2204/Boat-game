package joc.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "joc")
public class Joc extends Entity<Integer>{

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "jucator_id")
    private Jucator jucator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "configuratie_id")
    private Configuratie configuratie;

    @Column(name = "start_time")
    @Convert(converter = LocalDateTimeAttributeConverter.class)
    private LocalDateTime startTime;

    @Column(name = "scor")
    private int scor;

    @Column(name = "pozitii_ghicite")
    private int pozitiiGhicite;

    public Joc() {
    }

    public Joc(int id, Jucator jucator, Configuratie configuratie, LocalDateTime startTime, int scor, int pozitiiGhicite) {
        this.setId(id);
        this.jucator = jucator;
        this.configuratie = configuratie;
        this.startTime = startTime;
        this.scor = scor;
        this.pozitiiGhicite = pozitiiGhicite;
    }

    public Joc(Jucator jucator, Configuratie configuratie) {
        this.jucator = jucator;
        this.configuratie = configuratie;
        this.startTime = LocalDateTime.now();
        this.scor = 0;
        this.pozitiiGhicite = 0;
    }

    public Jucator getJucator() {
        return jucator;
    }
    public void setJucator(Jucator jucator) {
        this.jucator = jucator;
    }

    public Configuratie getConfiguratie() {
        return configuratie;
    }
    public void setConfiguratie(Configuratie configuratie) {
        this.configuratie = configuratie;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Joc joc)) return false;
        return scor == joc.scor &&
               pozitiiGhicite == joc.pozitiiGhicite &&
               Objects.equals(jucator, joc.jucator) &&
               Objects.equals(configuratie, joc.configuratie) &&
               Objects.equals(startTime, joc.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jucator, configuratie, startTime, scor, pozitiiGhicite);
    }

    @Override
    public String toString() {
        return "Joc{" +
                "jucator=" + jucator +
                ", configuratie=" + configuratie +
                ", startTime=" + startTime +
                ", scor=" + scor +
                ", pozitiiGhicite=" + pozitiiGhicite +
                '}';
    }


}
