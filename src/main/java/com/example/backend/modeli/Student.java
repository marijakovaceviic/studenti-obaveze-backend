package com.example.backend.modeli;

public class Student {
    
    private Long id;
    private String email;
    private String lozinka;
    private String smer;
    private Integer godinaPracenja;
    private String ime;
    private String prezime;
    private Integer godinaUpisa;
    private Integer brIndeksa;

    public Student(){}

    public Student(String email, String lozinka){
        this.email = email;
        this.lozinka = lozinka;
    }

    public String getLozinka(){
        return this.lozinka;
    }

    public String getEmail(){
        return this.email;
    }

    public String getSmer(){
        return this.smer;
    }

    public Integer getGodinaPracenja(){
        return this.godinaPracenja;
    }

    public void setGodinaPracenja(Integer godina){
        this.godinaPracenja = godina;
    }

    public void setSmer(String smer){
        this.smer = smer;
    }

    public String getIme() {
    return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public Integer getGodinaUpisa() {
        return godinaUpisa;
    }

    public void setGodinaUpisa(Integer godinaUpisa) {
        this.godinaUpisa = godinaUpisa;
    }

    public Integer getBrIndeksa() {
        return brIndeksa;
    }

    public void setBrIndeksa(Integer brIndeksa) {
        this.brIndeksa = brIndeksa;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }
}
