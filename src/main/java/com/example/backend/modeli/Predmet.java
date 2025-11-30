package com.example.backend.modeli;

public class Predmet {
    private Long id;
    private String naziv;
    private String sifra;
    private String odsek;
    private Integer godina;

    public Predmet() {}

    public Predmet(String naziv, String sifra, String odsek, Integer godina) {
        this.naziv = naziv;
        this.sifra = sifra;
        this.odsek = odsek;
        this.godina = godina;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getOdsek() {
        return odsek;
    }

    public void setOdsek(String odsek) {
        this.odsek = odsek;
    }

    public Integer getGodina() {
        return godina;
    }

    public void setGodina(Integer godina) {
        this.godina = godina;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
}

