package com.example.backend.db;

import java.util.List;

import com.example.backend.modeli.Nastavnik;

public interface NastavniciRepoInterface {
    
    public int dodavanjeNastavnika(Nastavnik nastavnik, String hesiranaLozinka);

    public List<Nastavnik> dohvatanjeSvihNastavnika();

    public List<Nastavnik> dohvatanjeNastavnikaNaPredmetu(Long idPredmeta);
    
    public int dodavanjeNastavnikaNaPredmet(Long idNastavnik, Long idPredmet);

    public int brisanjeNastavnikaSaPredmeta(Long idNastavnik, Long idPredmet);

    public Nastavnik login(String email, String hesiranaLozinka);

    public int promenaLozinke(String email, String staraLozinka, String hesiranaNovaLozinka);

    public int upravljanjeZaduzenZaDemonstratore(long idNastavnik, boolean zaduzen);

    public Nastavnik dohvatiZaduzenogZaDemonstratore();

    public long daLiNastavnikImaNalog(String email);

    public int postavljanjePrivremeneLozinke(String email, String hesiranaLozinka); //kada korisnik zaboravi lozinku
}
