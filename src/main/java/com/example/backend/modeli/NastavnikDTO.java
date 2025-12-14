package com.example.backend.modeli;

public class NastavnikDTO {
    private String email;
    private String lozinka;
    private String novaLozinka;

    public NastavnikDTO() {}

    public NastavnikDTO(String email, String staraLozinka, String novaLozinka) {
        this.email = email;
        this.lozinka = staraLozinka;
        this.novaLozinka = novaLozinka;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLozinka() {
        return lozinka;
    }
    public void setLozinka(String staraLozinka) {
        this.lozinka = staraLozinka;
    }
    public String getNovaLozinka() {
        return novaLozinka;
    }
    public void setNovaLozinka(String novaLozinka) {
        this.novaLozinka = novaLozinka;
    }
    
}
