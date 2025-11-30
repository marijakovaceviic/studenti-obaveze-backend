package com.example.backend.kontroleri;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.NastavniciRepo;
import com.example.backend.modeli.Nastavnik;


@RestController
@RequestMapping("/nastavnici")
@CrossOrigin(origins = "http://localhost:4200")
public class NastavniciController {

    @PostMapping("/registracija")
    public int registracija(@RequestBody Nastavnik nastavnik) {
        return new NastavniciRepo().dodavanjeNastavnika(nastavnik);
    }

    @GetMapping("/sviNastavnici")
    public List<Nastavnik> dohvatanjeSvihNastavnika() {
        return new NastavniciRepo().dohvatanjeSvihNastavnika();
    }

    @GetMapping("/nastavniciNaPredmetu/{id}")
    public List<Nastavnik> dohvatanjeNastavnikaNaPredmetu(@PathVariable Long id) {
        return new NastavniciRepo().dohvatanjeNastavnikaNaPredmetu(id);
    }

    @PostMapping("/dodavanjeNastavnikaNaPredmet/{idNastavnik}/{idPredmet}")
    public int dodavanjeNastavnikaNaPredmet(@PathVariable Long idNastavnik, @PathVariable Long idPredmet) {
        return new NastavniciRepo().dodavanjeNastavnikaNaPredmet(idNastavnik, idPredmet);
    }

    @DeleteMapping("/brisanjeNastavnikaSaPredmeta/{idNastavnik}/{idPredmet}")
    public int brisanjeNastavnikaSaPredmeta(@PathVariable Long idNastavnik, @PathVariable Long idPredmet) {
        return new NastavniciRepo().brisanjeNastavnikaSaPredmeta(idNastavnik, idPredmet);
    }
}
