package joc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "configuratie")
public class Configuratie extends Entity<Integer>{

    @Column(name="pozitie11")
    private int pozitie11;
    @Column(name = "pozitie12")
    private int pozitie12;

    @Column(name = "pozitie21")
    private int pozitie21;
    @Column(name = "pozitie22")
    private int pozitie22;

    @Column(name = "pozitie31")
    private int pozitie31;
    @Column(name = "pozitie32")
    private int pozitie32;

    public Configuratie() {
    }

    public Configuratie(int id, int pozitie11, int pozitie12, int pozitie21, int pozitie22, int pozitie31, int pozitie32) {
        this.setId(id);
        this.pozitie11 = pozitie11;
        this.pozitie12 = pozitie12;
        this.pozitie21 = pozitie21;
        this.pozitie22 = pozitie22;
        this.pozitie31 = pozitie31;
        this.pozitie32 = pozitie32;
    }

    public Configuratie(int pozitie11, int pozitie12, int pozitie21, int pozitie22, int pozitie31, int pozitie32) {
        this.pozitie11 = pozitie11;
        this.pozitie12 = pozitie12;
        this.pozitie21 = pozitie21;
        this.pozitie22 = pozitie22;
        this.pozitie31 = pozitie31;
        this.pozitie32 = pozitie32;
    }

    public int getPozitie11() {
        return pozitie11;
    }

    public void setPozitie11(int pozitie11) {
        this.pozitie11 = pozitie11;
    }

    public int getPozitie12() {
        return pozitie12;
    }
    public void setPozitie12(int pozitie12) {
        this.pozitie12 = pozitie12;
    }

    public int getPozitie21() {
        return pozitie21;
    }
    public void setPozitie21(int pozitie21) {
        this.pozitie21 = pozitie21;
    }

    public int getPozitie22() {
        return pozitie22;
    }

    public void setPozitie22(int pozitie22) {
        this.pozitie22 = pozitie22;
    }

    public int getPozitie31() {
        return pozitie31;
    }

    public void setPozitie31(int pozitie31) {
        this.pozitie31 = pozitie31;
    }

    public int getPozitie32() {
        return pozitie32;
    }

    public void setPozitie32(int pozitie32) {
        this.pozitie32 = pozitie32;
    }

    @Override
    public String toString() {
        return "Configuratie{" +
                "pozitie11=" + pozitie11 +
                ", pozitie12=" + pozitie12 +
                ", pozitie21=" + pozitie21 +
                ", pozitie22=" + pozitie22 +
                ", pozitie31=" + pozitie31 +
                ", pozitie32=" + pozitie32 +
                '}';
    }
}
