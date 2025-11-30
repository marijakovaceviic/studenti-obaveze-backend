package com.example.backend.servisi;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.backend.db.NastavniciRepo;
import com.example.backend.db.ObavezeRepo;
import com.example.backend.modeli.Nastavnik;
import com.example.backend.modeli.Obaveza;

@Service
public class ObavezeService {

    private EmailService emailService;
    private PrijaveService prijaveService;

    public ObavezeService(EmailService emailService, PrijaveService prijaveService){
        this.emailService = emailService;
        this.prijaveService = prijaveService;
    }
    
    @Scheduled(cron = "0 0 * * * *")
    public void slanjeSpiskaPrijavljenih(){
        List<Obaveza> obaveze = new ObavezeRepo().dohvatanjeIsteklihNeobradjenihObaveza();

        for (Obaveza o: obaveze){
            if ("domaci".equalsIgnoreCase(o.getTip())) continue;

            byte[] prilog = prijaveService.generisanjeListePrijavljenihStudenata(o.getId());

            List<Nastavnik> nastavnici = new NastavniciRepo().dohvatanjeNastavnikaNaPredmetu(o.getPredmet());

            for (Nastavnik n: nastavnici){
                this.emailService.slanjeEmailaSaPrilogom(n.getEmail(), null, null, null, null);
            }
        }
    }
}
