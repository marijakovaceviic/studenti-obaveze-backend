package com.example.backend.db;

import java.util.List;

import com.example.backend.modeli.Predmet;

public interface PredmetiRepoInterface {

    public int dodavanjePredmeta(Predmet predmet);

    public List<Predmet> dohvatanjeSvihPredmeta();

    public List<Predmet> dohvatanjePredmetaZaGodinu(int godina);

    public List<Predmet> dohvatanjePredmetaZaGodinuIOdsek(int godina, String odsek);

    public int cuvanjeIzabranihPredmetaZaPracenje(List<Long> izabrani, Long studentId);

    public List<Predmet> dohvatanjeIzabranihPredmetaZaStudenta(Long studentId) ;

    public List<Predmet> dohvatanjePredmetaZaNastavnika(Long idNastavnik);

    public List<Integer> dohvatanjeGodinaKojeStudentPrati(Long idStudent);

    public List<Predmet> dohvatanjePredmetaSaAktivnimObavezamaZaStudenta(Long idStudent, int godina);

    public Predmet dohvatanjePredemtaPoId(Long id);

    public List<Predmet> dohvatanjePredmetaSaAktivnimObavezamaZaGodinu(int godina);
}
