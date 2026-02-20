package com.example.backend.db;

import com.example.backend.modeli.Admin;

public interface AdminRepoInterface {
    
    public Admin prijava(Admin admin);

    public long proveraAdmina(String email);

    public int postavljanjePrivremeneLozinke(String email, String hesiranaLozinka); //kada korisnik zaboravi lozinku
}
