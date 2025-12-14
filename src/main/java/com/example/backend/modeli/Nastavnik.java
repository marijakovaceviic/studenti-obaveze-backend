package com.example.backend.modeli;

public class Nastavnik {
    private Long id;
    private String ime;
    private String prezime;
    private String email;
    private String lozinka;

    public Nastavnik(){}

    public Nastavnik(String ime, String prezime, String email){
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
    }

    public Nastavnik(String email, String lozinka){
        this.email = email;
        this.lozinka = lozinka;
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

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
}
