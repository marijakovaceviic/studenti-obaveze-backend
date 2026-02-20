package com.example.backend.kontroleri;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.ObavezeRepo;
import com.example.backend.modeli.Obaveza;
import com.example.backend.modeli.StatistikaObavezaDTO;

@RestController
@RequestMapping("/obaveze")
@CrossOrigin(origins = "http://localhost:4200")
public class ObavezeController {

    private final ObavezeRepo obavezeRepo;

    public ObavezeController(ObavezeRepo obavezeRepo) {
        this.obavezeRepo = obavezeRepo;
    }

    @PostMapping("/dodavanje")
    public Long dodavanjeObaveze(@RequestBody Obaveza obaveza) {
        return this.obavezeRepo.dodavanjeObaveze(obaveza);
    }

    @GetMapping("/zaPredmet/{id}")
    public List<Obaveza> dohvatanjeObavezaZaPredmet(@PathVariable Long id){
        return this.obavezeRepo.dohvatanjeObavezaZaPredmet(id);
    }

    @GetMapping("/zaStudenta/{id}")
    public List<Obaveza> dohvatanjeObavezaZaStudenta(@PathVariable Long id){
        return this.obavezeRepo.dohvatanjeObavezaZaStudenta(id);
    }

    @GetMapping("/sveObaveze")
    public List<Obaveza> dohvatanjeSvihObaveza(@PathVariable Long id){
        return this.obavezeRepo.dohvatanjeSvihObaveza();
    }
    
    @GetMapping("/zaNastavnika/{id}")
    public List<Obaveza> dohvatanjeObavezaZaNastavnika(@PathVariable Long id){
        return this.obavezeRepo.dohvatanjeObavezaZaNastavnika(id);
    }

    @GetMapping("/dohvatanjePoId/{id}")
    public Obaveza dohvatanjeObaveze(@PathVariable Long id){
        return this.obavezeRepo.dohvatanjeObaveze(id);
    }

    @GetMapping("/istekle/zaPredmet/{id}")
    public List<Obaveza> dohvatanjeIsteklihObavezaZaPredmet(@PathVariable Long id){
        return this.obavezeRepo.dohvatanjeIsteklihObavezaZaPredmet(id);
    }

    @GetMapping("/brojAktivnih/zaPredmet/{id}")
    public int brojAktivnihObavezaNaPredmetu(@PathVariable Long id){
        return this.obavezeRepo.brojAktivnihObavezaNaPredmetu(id);
    }

    @GetMapping("/brojIsteklih/zaPredmet/{id}")
    public int brojIsteklihObavezaNaPredmetu(@PathVariable Long id){
        return this.obavezeRepo.brojIsteklihObavezaNaPredmetu(id);
    }

    @GetMapping("/statistika/{godina}/{odsek}")
    public List<StatistikaObavezaDTO> statistikaObavezaPoPredmetima(@PathVariable int godina, @PathVariable String odsek){
        return this.obavezeRepo.statistikaBrojaObavezaNaPredemetima(godina, odsek);
    }

    @GetMapping("/nove/zaPredmet/{id}") //obaveze koje jos nisu pocele
    public List<Obaveza> dohvatanjeNovihObavezaZaPredmet(@PathVariable Long id){
        return this.obavezeRepo.dohvatanjeNovootvorenihObavezaZaPredmet(id);
    }

    @PostMapping("/azuriranje")
    public int azurirajObavezu(@RequestBody Obaveza obaveza){
        return this.obavezeRepo.azuriranjeObaveze(obaveza.getId(), obaveza.getNaziv(), obaveza.getOpis(), 
        obaveza.getTip(), obaveza.getPocetak(), obaveza.getKraj(), obaveza.getPredmet());
    }

    @GetMapping("/brojNovihNeaktivnih/zaPredmet/{id}")
    public int brojNovihNeaktivnihObavezaNaPredmetu(@PathVariable Long id){
        return this.obavezeRepo.brojNovihNeAktivnihObavezaNaPredmetu(id);
    }
}
