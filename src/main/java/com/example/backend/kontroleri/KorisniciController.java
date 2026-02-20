package com.example.backend.kontroleri;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.AdminRepo;
import com.example.backend.db.NastavniciRepo;
import com.example.backend.db.StudentiRepo;
import com.example.backend.modeli.KorisnikEmailDTO;
import com.example.backend.servisi.EmailService;

@RestController
@RequestMapping("/korisnici")
@CrossOrigin(origins = "http://localhost:4200")
public class KorisniciController {

    private final StudentiRepo studentiRepo;
    private final NastavniciRepo nastavniciRepo;
    private final AdminRepo adminRepo;
    private final EmailService emailService;

    public KorisniciController(StudentiRepo studentiRepo, NastavniciRepo nastavniciRepo, AdminRepo adminRepo, EmailService emailService) {
        this.studentiRepo = studentiRepo;
        this.nastavniciRepo = nastavniciRepo;
        this.adminRepo = adminRepo;
        this.emailService = emailService;
    }

    @PostMapping("/zaboravljenaLozinka")
    public int prijava(@RequestBody KorisnikEmailDTO korisnik){
        if (korisnik.getUloga().equals("student")){
            return promenaLozinkeStudent(korisnik.getEmail());
        }
        else if (korisnik.getUloga().equals("nastavnik")){
            return promenaLozinkeNastavnik(korisnik.getEmail());
        }
        else if (korisnik.getUloga().equals("admin")){
            return promenaLozinkeAdmin(korisnik.getEmail());
        }
        return -5;
    }

    private int promenaLozinkeStudent(String email){
        if (this.studentiRepo.daLiJeRegistrovanStudent(email) == 0L){
            return -1;
        }
        else{
            String lozinka = generisanjeLozinke();
            String hesiranaLozinka = hesiranjeLozinke(lozinka);
            if (this.studentiRepo.postavljanjePrivremeneLozinke(email, hesiranaLozinka) != 0){
                slanjePrivremeneLozinke(email, lozinka);
                return 0;
            }
            return -2;
        }
    }

    private int promenaLozinkeNastavnik(String email){
        if (this.nastavniciRepo.daLiNastavnikImaNalog(email) == 0L){
            return -1;
        }
        else{
            String lozinka = generisanjeLozinke();
            String hesiranaLozinka = hesiranjeLozinke(lozinka);
            if (this.nastavniciRepo.postavljanjePrivremeneLozinke(email, hesiranaLozinka) != 0){
                slanjePrivremeneLozinke(email, lozinka);
                return 0;
            }
            return -2;
        }
    }

    private int promenaLozinkeAdmin(String email){
        if (this.adminRepo.proveraAdmina(email) == 0L){
            return -1;
        }
        else{
            String lozinka = generisanjeLozinke();
            String hesiranaLozinka = hesiranjeLozinke(lozinka);
            if (this.adminRepo.postavljanjePrivremeneLozinke(email, hesiranaLozinka) != 0){
                slanjePrivremeneLozinke(email, lozinka);
                return 0;
            }
            return -2;
        }
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

    private void slanjePrivremeneLozinke(String email, String lozinka){
        String naslov = "Zaboravljena lozinka";
        String tekstHtml = "<html> <body>" +
            "<p>Vaša privremena lozinka za pristup RTI sajtu za prijavu obaveza i predaju radova je:</p>" + lozinka +
            "<p>Savetujemo da je što pre promenite na kartici podeševanja.</p>" +
            "<p style='font-size:smaller; color:gray;'>Ovo je automatski generisan mejl, ne odgovarajte na njega.</p>"+
            "</body> </html>";
        this.emailService.slanjeHtmlEmaila(email, naslov, tekstHtml);
    }
}
