package com.example.backend.kontroleri;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.ObavezeRepo;
import com.example.backend.modeli.Obaveza;

@RestController
@RequestMapping("/obaveze")
@CrossOrigin(origins = "http://localhost:4200")
public class ObavezeController {

    @PostMapping("/dodavanje")
    public Long dodavanjeObaveze(@RequestBody Obaveza obaveza) {
        return new ObavezeRepo().dodavanjeObaveze(obaveza);
    }

    @GetMapping("/zaPredmet/{id}")
    public List<Obaveza> dohvatanjeObavezaZaPredmet(@PathVariable Long id){
        return new ObavezeRepo().dohvatanjeObavezaZaPredmet(id);
    }

    @GetMapping("/zaStudenta/{id}")
    public List<Obaveza> dohvatanjeObavezaZaStudenta(@PathVariable Long id){
        return new ObavezeRepo().dohvatanjeObavezaZaStudenta(id);
    }

    @GetMapping("/sveObaveze")
    public List<Obaveza> dohvatanjeSvihObaveza(@PathVariable Long id){
        return new ObavezeRepo().dohvatanjeSvihObaveza();
    }
    
    @GetMapping("/zaNastavnika/{id}")
    public List<Obaveza> dohvatanjeObavezaZaNastavnika(@PathVariable Long id){
        return new ObavezeRepo().dohvatanjeObavezaZaNastavnika(id);
    }

    @GetMapping("/dohvatanjePoId/{id}")
    public Obaveza dohvatanjeObaveze(@PathVariable Long id){
        return new ObavezeRepo().dohvatanjeObaveze(id);
    }

    @GetMapping("/istekle/zaPredmet/{id}")
    public List<Obaveza> dohvatanjeIsteklihObavezaZaNastavnika(@PathVariable Long id){
        return new ObavezeRepo().dohvatanjeIsteklihObavezaZaPredmet(id);
    }
}
