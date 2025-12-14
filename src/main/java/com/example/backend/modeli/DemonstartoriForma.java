package com.example.backend.modeli;

import java.time.LocalDateTime;

public class DemonstartoriForma {
    private Long id;
    private Long idNastavnik;
    private LocalDateTime pocetak;
    private LocalDateTime kraj;
    private String konkurs;
    
    public DemonstartoriForma() {}

    public DemonstartoriForma(Long id, Long idNastavnik, LocalDateTime pocetak, LocalDateTime kraj, String konkurs) {
        this.id = id;
        this.idNastavnik = idNastavnik;
        this.pocetak = pocetak;
        this.kraj = kraj;
        this.konkurs = konkurs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdNastavnik() {
        return idNastavnik;
    }

    public void setIdNastavnik(Long idNastavnik) {
        this.idNastavnik = idNastavnik;
    }

    public LocalDateTime getPocetak() {
        return pocetak;
    }

    public void setPocetak(LocalDateTime pocetak) {
        this.pocetak = pocetak;
    }

    public LocalDateTime getKraj() {
        return kraj;
    }

    public void setKraj(LocalDateTime kraj) {
        this.kraj = kraj;
    }

    public String getKonkurs() {
        return konkurs;
    }
    
    public void setKonkurs(String konkurs) {
        this.konkurs = konkurs;
    }

    
}
