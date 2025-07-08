package joc.model;

import java.time.LocalDateTime;

public class ClasamentDTO {
    private String alias;
    private LocalDateTime start_time;
    private Integer scor;
    private Integer ghicite;

    public ClasamentDTO(String alias, LocalDateTime start_time, Integer scor, Integer ghicite) {
        this.alias = alias;
        this.start_time = start_time;
        this.scor = scor;
        this.ghicite = ghicite;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public LocalDateTime getStart_time() {
        return start_time;
    }

    public void setStart_time(LocalDateTime start_time) {
        this.start_time = start_time;
    }

    public Integer getScor() {
        return scor;
    }

    public void setScor(Integer scor) {
        this.scor = scor;
    }

    public Integer getGhicite() {
        return ghicite;
    }

    public void setGhicite(Integer ghicite) {
        this.ghicite = ghicite;
    }

    @Override
    public String toString() {
        return "ClasamentDTO{" +
                "alias='" + alias + '\'' +
                ", start_time=" + start_time +
                ", scor=" + scor +
                ", ghicite=" + ghicite +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClasamentDTO)) return false;

        ClasamentDTO that = (ClasamentDTO) o;

        if (!alias.equals(that.alias)) return false;
        if (!start_time.equals(that.start_time)) return false;
        if (!scor.equals(that.scor)) return false;
        return ghicite.equals(that.ghicite);
    }

    @Override
    public int hashCode() {
        int result = alias.hashCode();
        result = 31 * result + start_time.hashCode();
        result = 31 * result + scor.hashCode();
        result = 31 * result + ghicite.hashCode();
        return result;
    }
}
