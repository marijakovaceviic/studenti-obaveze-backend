package com.example.backend.modeli;

public class KorisnikEmailDTO {
    private String email;
    private String uloga;
    
    public KorisnikEmailDTO(String email, String uloga) {
        this.email = email;
        this.uloga = uloga;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUloga() {
        return uloga;
    }
    public void setUloga(String uloga) {
        this.uloga = uloga;
    }
    
}
