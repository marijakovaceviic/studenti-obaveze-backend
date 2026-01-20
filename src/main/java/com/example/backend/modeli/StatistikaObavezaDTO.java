package com.example.backend.modeli;

public class StatistikaObavezaDTO {
    private Long idPredmet;
    private String nazivPredmeta;
    private String sifra;
    private int kolokvijumi;
    private int labovi;
    private int domaci;

    public StatistikaObavezaDTO(){}

    public StatistikaObavezaDTO(Long idPredmet, String sifra, int kolokvijumi, int labovi, int domaci) {
        this.idPredmet = idPredmet;
        this.sifra = sifra;
        this.kolokvijumi = kolokvijumi;
        this.labovi = labovi;
        this.domaci = domaci;
    }
    public Long getIdPredmet() {
        return idPredmet;
    }
    public void setIdPredmet(Long idPredmet) {
        this.idPredmet = idPredmet;
    }
    public String getSifra() {
        return sifra;
    }
    public void setSifra(String sifra) {
        this.sifra = sifra;
    }
    public int getKolokvijumi() {
        return kolokvijumi;
    }
    public void setKolokvijumi(int kolokvijumi) {
        this.kolokvijumi = kolokvijumi;
    }
    public int getLabovi() {
        return labovi;
    }
    public void setLabovi(int labovi) {
        this.labovi = labovi;
    }
    public int getDomaci() {
        return domaci;
    }
    public void setDomaci(int domaci) {
        this.domaci = domaci;
    }

    public String getNazivPredmeta() {
        return nazivPredmeta;
    }

    public void setNazivPredmeta(String nazivPredmeta) {
        this.nazivPredmeta = nazivPredmeta;
    }
    
}
