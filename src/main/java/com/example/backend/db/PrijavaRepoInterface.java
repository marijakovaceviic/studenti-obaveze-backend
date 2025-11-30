package com.example.backend.db;

import java.util.List;

import com.example.backend.modeli.Obaveza;
import com.example.backend.modeli.Student;

public interface PrijavaRepoInterface {
    
    public int novaPrijava(Long idStudent, Long idObaveza);

    public int daLiJePrijavljen(Long idStudent, Long idObaveza);

    public List<Student> dohvatanjeSvihPrijavaZaObavezu(Long idObaveza);

    public List<Obaveza> dohvatanjePrijavaZaStudenta(Long idStudenta, String tip);

    public int brojPrijavljenih(Long idObaveza);

    public int odjava(Long idStudent, Long idObaveza);
}
