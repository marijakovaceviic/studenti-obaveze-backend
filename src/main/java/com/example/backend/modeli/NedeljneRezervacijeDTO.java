package com.example.backend.modeli;

import java.time.LocalDate;

public class NedeljneRezervacijeDTO{
    private Long idLaboratorija;
    private LocalDate pocetak;
    private LocalDate kraj;

    public NedeljneRezervacijeDTO(){}
    
    public NedeljneRezervacijeDTO(Long idLaboratorija, LocalDate pocetak, LocalDate kraj) {
        this.idLaboratorija = idLaboratorija;
        this.pocetak = pocetak;
        this.kraj = kraj;
    }
    public Long getIdLaboratorija() {
        return idLaboratorija;
    }
    public void setIdLaboratorija(Long idLaboratorija) {
        this.idLaboratorija = idLaboratorija;
    }
    public LocalDate getPocetak() {
        return pocetak;
    }
    public void setPocetak(LocalDate pocetak) {
        this.pocetak = pocetak;
    }
    public LocalDate getKraj() {
        return kraj;
    }
    public void setKraj(LocalDate kraj) {
        this.kraj = kraj;
    }
}