package com.example.backend.kontroleri;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.PredmetiRepo;
import com.example.backend.modeli.Predmet;
import com.example.backend.modeli.StudentPredmet;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/predmeti")
@CrossOrigin(origins = "http://localhost:4200")
public class PredmetiController {

     private final PredmetiRepo predmetiRepo;

    public PredmetiController(PredmetiRepo predmetiRepo) {
        this.predmetiRepo = predmetiRepo;
    }
    
    @PostMapping("/dodavanje")
    public int provera(@RequestBody Predmet predmet) {
        return this.predmetiRepo.dodavanjePredmeta(predmet);
    }

    @GetMapping("/sviPredmeti")
    public List<Predmet> dohvatanjeSvihPredmeta() {
        return this.predmetiRepo.dohvatanjeSvihPredmeta();
    }

    @PostMapping("/izborPredmeta")
    public int izborPredmeta(@RequestBody StudentPredmet sp) {
        return this.predmetiRepo.cuvanjeIzabranihPredmetaZaPracenje(sp.getIzabraniPredmeti(), sp.getStudentId());
    }
    
    @GetMapping("/predmetiZaStudenta/{id}")
    public List<Predmet> predmetiZaStudenta(@PathVariable Long id) {
        return this.predmetiRepo.dohvatanjeIzabranihPredmetaZaStudenta(id);
    }
    
    @GetMapping("/predmetiZaNastavnika/{id}")
    public List<Predmet> predmetiZaNastavnika(@PathVariable Long id) {
        return this.predmetiRepo.dohvatanjePredmetaZaNastavnika(id);
    }

    @GetMapping("/godine/{idStudent}")
    public List<Integer> dohvatanjeGodinaKojeStudentPrati(@PathVariable Long idStudent) {
        return this.predmetiRepo.dohvatanjeGodinaKojeStudentPrati(idStudent);
    }

    @GetMapping("/predmetiSaAktivnimObavezama/{idStudent}/{godina}")
    public List<Predmet> dohvatanjePredmetaSaAktivnimObavezama(@PathVariable Long idStudent, @PathVariable int godina) {
        return this.predmetiRepo.dohvatanjePredmetaSaAktivnimObavezamaZaStudenta(idStudent, godina);
    }

    @GetMapping("/premaId/{id}")
    public Predmet dohavatanjePredmetaSaIdijem(@PathVariable Long id) {
        return this.predmetiRepo.dohvatanjePredemtaPoId(id);
    }

    @GetMapping("/aktivneObavezeZaGodinu/{godina}")
    public List<Predmet> dohvatanjePredmetaSaAktivnimObavezamaZaGodinu(@PathVariable int godina) {
        return this.predmetiRepo.dohvatanjePredmetaSaAktivnimObavezamaZaGodinu(godina);
    }

    @GetMapping("brojStudenataKojiPrate/{idPredmet}")
    public int brojStudenataKojiPrateObavestenjaZaPredmet(@PathVariable Long idPredmet){
        return this.predmetiRepo.brojStudenataKojiPrateObavestenjaZaPredmet(idPredmet);
    }
}
