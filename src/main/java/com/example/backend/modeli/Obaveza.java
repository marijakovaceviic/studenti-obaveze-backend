package com.example.backend.modeli;

import java.time.LocalDateTime;

public class Obaveza {
    private Long id;
    private String tip;
    private String naziv;
    private Long predmet;
    private String opis;
    private LocalDateTime pocetak;
    private LocalDateTime kraj;
    private Boolean poslat_email_nastavniku;
    private Boolean poslat_email_studentima;

    private String nazivPredmeta;
    private String sifraPredmeta;

    public Obaveza() {
    }

    public Obaveza(String tip, String naziv, Long predmet, String opis, LocalDateTime pocetak, LocalDateTime kraj) {
        this.tip = tip;
        this.naziv = naziv;
        this.predmet = predmet;
        this.opis = opis;
        this.pocetak = pocetak;
        this.kraj = kraj;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Long getPredmet() {
        return predmet;
    }

    public void setPredmet(Long predmet) {
        this.predmet = predmet;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
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

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
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

    public void setPoslat_email_nastavniku(Boolean poslat){
        this.poslat_email_nastavniku = poslat;
    }

    public Boolean getPoslat_email_nastavniku(){
        return this.poslat_email_nastavniku;
    }

    public Boolean getPoslat_email_studentima() {
        return poslat_email_studentima;
    }

    public void setPoslat_email_studentima(Boolean poslat_email_studentima) {
        this.poslat_email_studentima = poslat_email_studentima;
    }
}
