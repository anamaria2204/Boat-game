package joc.model;


import jakarta.persistence.Column;
import jakarta.persistence.Table;

import java.util.Objects;

@jakarta.persistence.Entity
@Table(name = "Jucator")
public class Jucator extends Entity<Integer>{
    @Column(name = "nume")
    private String nume;

    public Jucator() {
    }

    public Jucator(int id, String nume) {
        this.setId(id);
        this.nume = nume;
    }

    public Jucator(String nume) {
        this.nume = nume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jucator jucator = (Jucator) o;
        return Objects.equals(nume, jucator.nume);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nume);
    }
}

