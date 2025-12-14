package com.example.backend.db;

import java.util.List;

import com.example.backend.modeli.Obaveza;

public interface ObavezeRepoInterface {

    public Long dodavanjeObaveze(Obaveza obaveza);

    public List<Obaveza> dohvatanjeObavezaZaPredmet(Long idPredemet);

    public List<Obaveza> dohvatanjeObavezaZaStudenta(Long idStudent);

    public List<Obaveza> dohvatanjeSvihObaveza();

    public List<Obaveza> dohvatanjeObavezaZaNastavnika(Long idNastavnik);

    public Obaveza dohvatanjeObaveze(Long id);

    public List<Obaveza> dohvatanjeIsteklihObavezaZaPredmet(Long idPredmet);

    public List<Obaveza> dohvatanjeIsteklihNeobradjenihObaveza();

    public void oznaciMailPoslat(long idObaveze);
}
