package com.example.backend.modeli;

import java.util.List;

public class DemonstratoriPrijava {
    private List<Long> predmeti;
    private Long idStudent;
    private Long idForma;

    public DemonstratoriPrijava() {}
    
    public List<Long> getPredmeti() {
        return predmeti;
    }
    public void setPredmeti(List<Long> predmeti) {
        this.predmeti = predmeti;
    }
    public Long getIdStudent() {
        return idStudent;
    }
    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }
    public Long getIdForma() {
        return idForma;
    }
    public void setIdForma(Long idForma) {
        this.idForma = idForma;
    }
    
}
