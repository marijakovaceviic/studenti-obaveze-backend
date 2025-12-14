package com.example.backend.modeli;

public class StudentDTO {
    private String email;
    private String lozinka;
    private String novaLozinka;
    
    public StudentDTO() {}
    
    public StudentDTO(String email, String lozinka, String novaLozinka) {
        this.email = email;
        this.lozinka = lozinka;
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
    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }
    public String getNovaLozinka() {
        return novaLozinka;
    }
    public void setNovaLozinka(String novaLozinka) {
        this.novaLozinka = novaLozinka;
    }
}
