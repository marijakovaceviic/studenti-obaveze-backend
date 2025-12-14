package com.example.backend.db;

import java.util.List;

import com.example.backend.modeli.Student;

public interface StudentiRepoInterface {
    
    public long proveraStudenta(String email);

    public int registracijaStudenta(Student student);

    public Student prijavaStudenta(Student student);

    public int promenaLozinke(String email, String staraLozinka, String hesiranaNovaLozinka);

    public String dohvatanjeMejlaStudenta(Long idStudenta);

    public List<Student> dohvatanjeStudenataKojiPratePredmet(Long idPredmet);

    public List<Student> dohvatanjeRegistrovanihStudenata();
}
