package com.example.backend.modeli;

public class KorisnikDTO {
    private String korisnickoIme;
    private String lozinka;
    private boolean angazovan;

    public KorisnikDTO() {}

    public String getKorisnickoIme() {
        return korisnickoIme;
    }
    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }
    public String getLozinka() {
        return lozinka;
    }
    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
    public boolean isAngazovan() {
        return angazovan;
    }
    public void setAngazovan(boolean angazovan) {
        this.angazovan = angazovan;
    }
    
    
}
