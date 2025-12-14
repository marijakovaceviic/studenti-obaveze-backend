package com.example.backend.modeli;

public class Laboratorija {
    private Long id;
    private String naziv;
    private Integer kapacitet;

    public Laboratorija(Long id, String naziv, Integer kapacitet) {
        this.id = id;
        this.naziv = naziv;
        this.kapacitet = kapacitet;
    }
    
    public Laboratorija() {}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNaziv(){
        return this.naziv;
    }

    public void setNaziv(String naziv){
        this.naziv = naziv;
    }

    public Integer getKapacitet(){
        return this.kapacitet;
    }

    public void setKapacitet(Integer kapacitet){
        this.kapacitet = kapacitet;
    }
}
