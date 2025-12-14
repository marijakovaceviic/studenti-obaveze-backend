package com.example.backend.db;

import java.util.List;

import com.example.backend.modeli.DemonstartoriForma;
import com.example.backend.modeli.DemonstratoriPrijava;
import com.example.backend.modeli.Predmet;
import com.example.backend.modeli.PredmetPrijavljeniDTO;
import com.example.backend.modeli.Student;

public interface DemonstartoriRepoInterface {

    public int cuvanjeNoveForme(DemonstartoriForma forma);

    public boolean daLiJeZaduzenZaDemonstratore(Long idNastavnika); 

    public DemonstartoriForma dohvatiAktivnuFormu();

    public int novaPrijava(DemonstratoriPrijava prijava);

    public String putanjaFajlaKonkursa(Long id);

    public List<Predmet> prijavljeniPredmetiZaStudenta(Long studentId, Long formaId);

    public List<Student> prijavljeniStudentiZaPredmetPoslednjaForma(Long idPredmet);

    public List<PredmetPrijavljeniDTO> prijavljeniStudentiZaPoslednjuFormu();
}
