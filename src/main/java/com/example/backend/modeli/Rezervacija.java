package com.example.backend.modeli;

import java.time.LocalDate;
import java.time.LocalTime;

public class Rezervacija {
    private String nazivObaveze;
    private Long idNastavnik;
    private Long idLaboratorija;
    private LocalDate datum;
    private LocalTime vremeOd;
    private LocalTime vremeDo;
    private String akronim;

    private String imeNastavnika;
    private String prezimeNastavnika;
    private String nazivLaboratorije;
    
    public Rezervacija(){}
    
    public Rezervacija(String nazivObaveze, Long idNastavnik, Long idLaboratorija, LocalDate datum, LocalTime vremeOd,
            LocalTime vremeDo) {
        this.nazivObaveze = nazivObaveze;
        this.idNastavnik = idNastavnik;
        this.idLaboratorija = idLaboratorija;
        this.datum = datum;
        this.vremeOd = vremeOd;
        this.vremeDo = vremeDo;
    }

    public String getNazivObaveze() {
        return nazivObaveze;
    }

    public void setNazivObaveze(String nazivObaveze) {
        this.nazivObaveze = nazivObaveze;
    }

    public Long getIdNastavnik() {
        return idNastavnik;
    }

    public void setIdNastavnik(Long idNastavnik) {
        this.idNastavnik = idNastavnik;
    }

    public Long getIdLaboratorija() {
        return idLaboratorija;
    }

    public void setIdLaboratorija(Long idLaboratorija) {
        this.idLaboratorija = idLaboratorija;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public LocalTime getVremeOd() {
        return vremeOd;
    }

    public void setVremeOd(LocalTime vremeOd) {
        this.vremeOd = vremeOd;
    }

    public LocalTime getVremeDo() {
        return vremeDo;
    }

    public void setVremeDo(LocalTime vremeDo) {
        this.vremeDo = vremeDo;
    }

    public String getImeNastavnika() {
        return imeNastavnika;
    }

    public void setImeNastavnika(String imeNastavnika) {
        this.imeNastavnika = imeNastavnika;
    }

    public String getNazivLaboratorije() {
        return nazivLaboratorije;
    }

    public void setNazivLaboratorije(String nazivLaboratorije) {
        this.nazivLaboratorije = nazivLaboratorije;
    }

    public String getAkronim() {
        return akronim;
    }

    public void setAkronim(String akronim) {
        this.akronim = akronim;
    }

    public String getPrezimeNastavnika() {
        return prezimeNastavnika;
    }

    public void setPrezimeNastavnika(String prezimeNastavnika) {
        this.prezimeNastavnika = prezimeNastavnika;
    }
}
