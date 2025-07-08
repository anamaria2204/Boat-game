package joc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

@jakarta.persistence.Entity
@Table(name = "mutare")
public class Mutare extends Entity<Integer> {

    @Column(name = "jucator_id")
    private int jucatorId;

    @Column(name = "joc_id")
    private int jocId;

    @Column(name = "linie")
    private int linie;

    @Column(name = "coloana")
    private int coloana;

    @Column(name = "ghicit")
    private boolean ghicit;

    public Mutare() {
    }

    public Mutare(int id, int jucatorId, int jocId, int linie, int coloana, boolean ghicit) {
        this.setId(id);
        this.jucatorId = jucatorId;
        this.jocId = jocId;
        this.linie = linie;
        this.coloana = coloana;
        this.ghicit = ghicit;
    }

    public Mutare(int jucatorId, int jocId, int linie, int coloana, boolean ghicit) {
        this.jucatorId = jucatorId;
        this.jocId = jocId;
        this.linie = linie;
        this.coloana = coloana;
        this.ghicit = ghicit;
    }

    public int getJucatorId() {
        return jucatorId;
    }

    public void setJucatorId(int jucatorId) {
        this.jucatorId = jucatorId;
    }

    public int getJocId() {
        return jocId;
    }

    public void setJocId(int jocId) {
        this.jocId = jocId;
    }

    public int getLinie() {
        return linie;
    }

    public void setLinie(int linie) {
        this.linie = linie;
    }

    public int getColoana() {
        return coloana;
    }

    public void setColoana(int coloana) {
        this.coloana = coloana;
    }

    public boolean isGhicit() {
        return ghicit;
    }

    public void setGhicit(boolean ghicit) {
        this.ghicit = ghicit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mutare)) return false;
        if (!super.equals(o)) return false;

        Mutare mutare = (Mutare) o;

        if (jucatorId != mutare.jucatorId) return false;
        if (jocId != mutare.jocId) return false;
        if (linie != mutare.linie) return false;
        if (coloana != mutare.coloana) return false;
        return ghicit == mutare.ghicit;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + jucatorId;
        result = 31 * result + jocId;
        result = 31 * result + linie;
        result = 31 * result + coloana;
        result = 31 * result + (ghicit ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Mutare{" +
                "id=" + getId() +
                ", jucatorId=" + jucatorId +
                ", jocId=" + jocId +
                ", linie=" + linie +
                ", coloana=" + coloana +
                ", ghicit=" + ghicit +
                '}';
    }
}
