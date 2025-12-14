package com.example.backend.db;

import java.util.List;

import com.example.backend.modeli.Laboratorija;
import com.example.backend.modeli.NedeljneRezervacijeDTO;
import com.example.backend.modeli.Rezervacija;

public interface RezervacijeRepoInterface {
    
    public int novaRezervacija(Rezervacija rezervacija);

    public List<Laboratorija> dohvatanjeSlobodnihLaboratorija(Rezervacija rezervacija);

    public List<Rezervacija> dohvatanjeNedeljnihRezervacijaSale(NedeljneRezervacijeDTO pregled);

    public List<Laboratorija> dohvatanejSvihLaboratorija();

}
