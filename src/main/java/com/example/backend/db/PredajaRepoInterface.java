package com.example.backend.db;

import java.util.List;

import com.example.backend.modeli.Predaja;

public interface PredajaRepoInterface {
    
    public int novaPredaja(Long idStudent, Long idObaveze, String putanja);

    public List<Predaja> predatiRadoviZaStudenta(Long idStudent);

    public List<Predaja> predatiRadoviZaObavezu(Long idObaveza);

    public int brojPredatihRadova(Long idObaveza);
}
