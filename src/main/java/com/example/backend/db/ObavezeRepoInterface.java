package com.example.backend.db;

import java.time.LocalDateTime;
import java.util.List;

import com.example.backend.modeli.Obaveza;
import com.example.backend.modeli.StatistikaObavezaDTO;

public interface ObavezeRepoInterface {

    public Long dodavanjeObaveze(Obaveza obaveza);

    public List<Obaveza> dohvatanjeObavezaZaPredmet(Long idPredemet);

    public List<Obaveza> dohvatanjeObavezaZaStudenta(Long idStudent);

    public List<Obaveza> dohvatanjeSvihObaveza();

    public List<Obaveza> dohvatanjeObavezaZaNastavnika(Long idNastavnik);

    public Obaveza dohvatanjeObaveze(Long id);

    public List<Obaveza> dohvatanjeIsteklihObavezaZaPredmet(Long idPredmet);

    public List<Obaveza> dohvatanjeIsteklihNeobradjenihObaveza();

    public void setPoslatMejlNastavniku(long idObaveze);

    public int brojAktivnihObavezaNaPredmetu(Long idPredmet);

    public int brojIsteklihObavezaNaPredmetu(Long idPredmet);

    public List<Obaveza> dohvatanjeAktivnihNeobavestenihObaveza();

    public void setPoslatMejlStudentima(long idObaveze);

    public List<StatistikaObavezaDTO> statistikaBrojaObavezaNaPredemetima(int godina, String odsek);

    public List<Obaveza> dohvatanjeNovootvorenihObavezaZaPredmet(Long idPredmet);

    public int azuriranjeObaveze(Long id, String naziv, String opis, String tip, LocalDateTime pocetak, LocalDateTime kraj, Long predmet);

    public int brojNovihNeAktivnihObavezaNaPredmetu(Long idPredmet);
}
