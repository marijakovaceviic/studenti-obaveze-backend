package com.example.backend.modeli;

import java.util.List;

public class PredmetPrijavljeniDTO {
    private Long idPredmet;
    private String nazivPredmeta;
    private String sifraPredmeta;
    private List<Student> studenti;

    public PredmetPrijavljeniDTO() {}

    public Long getIdPredmet() {
        return idPredmet;
    }
    public void setIdPredmet(Long idPredmet) {
        this.idPredmet = idPredmet;
    }
    public List<Student> getStudenti() {
        return studenti;
    }
    public void setStudenti(List<Student> studenti) {
        this.studenti = studenti;
    }

    public String getNazivPredmeta() {
        return nazivPredmeta;
    }

    public void setNazivPredmeta(String nazivPredmeta) {
        this.nazivPredmeta = nazivPredmeta;
    }

    public String getSifraPredmeta() {
        return sifraPredmeta;
    }

    public void setSifraPredmeta(String sifraPredmeta) {
        this.sifraPredmeta = sifraPredmeta;
    }
    
}
