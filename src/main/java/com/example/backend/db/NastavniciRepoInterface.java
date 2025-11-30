package com.example.backend.db;

import java.util.List;

import com.example.backend.modeli.Nastavnik;

public interface NastavniciRepoInterface {
    
    public int dodavanjeNastavnika(Nastavnik nastavnik);

    public List<Nastavnik> dohvatanjeSvihNastavnika();

    public List<Nastavnik> dohvatanjeNastavnikaNaPredmetu(Long idPredmeta);
    
    public int dodavanjeNastavnikaNaPredmet(Long idNastavnik, Long idPredmet);

    public int brisanjeNastavnikaSaPredmeta(Long idNastavnik, Long idPredmet);
}
