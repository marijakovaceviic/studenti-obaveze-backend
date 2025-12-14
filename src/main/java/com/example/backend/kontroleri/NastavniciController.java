package com.example.backend.kontroleri;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import com.example.backend.modeli.NastavnikDTO;
import com.example.backend.servisi.EmailService;


@RestController
@RequestMapping("/nastavnici")
@CrossOrigin(origins = "http://localhost:4200")
public class NastavniciController {

    private final EmailService emailService;
    private final NastavniciRepo nastavniciRepo;

    public NastavniciController(EmailService emailService, NastavniciRepo nastavniciRepo){
        this.emailService = emailService;
        this.nastavniciRepo = nastavniciRepo;
    }

    @PostMapping("/registracija")
    public int registracija(@RequestBody Nastavnik nastavnik) {
        String lozinka = generisanjeLozinke();
        String hesiranaLozinka = hesiranjeLozinke(lozinka);
        int rezultat = this.nastavniciRepo.dodavanjeNastavnika(nastavnik, hesiranaLozinka);

        if (rezultat == 1){
            String naslov = "Otvoren nalog na platformi za prijavu studentskih obaveza";
            String tekstHtml = "<html> <body>" +
                "<p>Za Vas je otvoren nalog na platformi za prijavu studentskih obaveza i predaju zadataka.</p>" +
                "<p>Automatski generisana loznika je: </p>" + lozinka +
                "<p>Možete je promeniti na kartici podeševanja.</p>" +
                "<p style='font-size:smaller; color:gray;'>Ovo je automatski generisan mejl, ne odgovarajte na njega.</p>"+
                "</body> </html>";
            this.emailService.slanjeHtmlEmaila(nastavnik.getEmail(), naslov, tekstHtml);
        }
        return rezultat;
    }

    @PostMapping("/prijava")
    public Nastavnik prijava(@RequestBody NastavnikDTO nastavnik){
        return this.nastavniciRepo.login(nastavnik.getEmail(), nastavnik.getLozinka());
    }

    @PostMapping("/promenaLozinke")
    public int promenaLozinke(@RequestBody NastavnikDTO nastavnik){
        String hesiranaNovaLozinka = hesiranjeLozinke(nastavnik.getNovaLozinka());
        return this.nastavniciRepo.promenaLozinke(nastavnik.getEmail(), nastavnik.getLozinka(), hesiranaNovaLozinka);
    }

    @GetMapping("/sviNastavnici")
    public List<Nastavnik> dohvatanjeSvihNastavnika() {
        return this.nastavniciRepo.dohvatanjeSvihNastavnika();
    }

    @GetMapping("/nastavniciNaPredmetu/{id}")
    public List<Nastavnik> dohvatanjeNastavnikaNaPredmetu(@PathVariable Long id) {
        return this.nastavniciRepo.dohvatanjeNastavnikaNaPredmetu(id);
    }

    @PostMapping("/dodavanjeNastavnikaNaPredmet/{idNastavnik}/{idPredmet}")
    public int dodavanjeNastavnikaNaPredmet(@PathVariable Long idNastavnik, @PathVariable Long idPredmet) {
        return this.nastavniciRepo.dodavanjeNastavnikaNaPredmet(idNastavnik, idPredmet);
    }

    @DeleteMapping("/brisanjeNastavnikaSaPredmeta/{idNastavnik}/{idPredmet}")
    public int brisanjeNastavnikaSaPredmeta(@PathVariable Long idNastavnik, @PathVariable Long idPredmet) {
        return this.nastavniciRepo.brisanjeNastavnikaSaPredmeta(idNastavnik, idPredmet);
    }

    private String generisanjeLozinke(){
        String karakteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!$%#@*";
        StringBuilder lozinka = new StringBuilder();
        java.util.Random rand = new java.util.Random();

        for (int i = 0; i < 10; i++) {   
            lozinka.append(karakteri.charAt(rand.nextInt(karakteri.length())));
        }
        
        return lozinka.toString();
    }

    private String hesiranjeLozinke(String lozinka){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
        String hash = encoder.encode(lozinka.toString());
        return hash;
    }
}
