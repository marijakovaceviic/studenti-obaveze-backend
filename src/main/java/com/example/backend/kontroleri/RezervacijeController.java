package com.example.backend.kontroleri;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.RezervacijeRepo;
import com.example.backend.modeli.Laboratorija;
import com.example.backend.modeli.NedeljneRezervacijeDTO;
import com.example.backend.modeli.Rezervacija;

@RestController
@RequestMapping("/rezervacije")
@CrossOrigin(origins = "http://localhost:4200")
public class RezervacijeController {
    
    private final RezervacijeRepo rezervacijeRepo;

    public RezervacijeController(RezervacijeRepo rezervacijeRepo){
        this.rezervacijeRepo = rezervacijeRepo;
    } 

    @PostMapping("/nova")
    public int novaRezervacija(@RequestBody Rezervacija rezervacija){
        return rezervacijeRepo.novaRezervacija(rezervacija);
    }

    @PostMapping("/slobodneLaboratorije")
    public List<Laboratorija> dohvatanjeSlobodnihLaboratotorija(@RequestBody Rezervacija rezervacija){
        return rezervacijeRepo.dohvatanjeSlobodnihLaboratorija(rezervacija);
    }

    @PostMapping("/nedeljneRezervacije")
    public List<Rezervacija> nedeljneRezervacije(@RequestBody NedeljneRezervacijeDTO rezervacija) {
        return rezervacijeRepo.dohvatanjeNedeljnihRezervacijaSale(rezervacija);
    }

    @GetMapping("/sveLaboratorije")
    public List<Laboratorija> dohvatanjeSvihLaboratorija(){
        return rezervacijeRepo.dohvatanejSvihLaboratorija();
    }
}
