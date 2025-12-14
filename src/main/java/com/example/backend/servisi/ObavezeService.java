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
    private ObavezeRepo obavezeRepo;
    private NastavniciRepo nastavniciRepo;

    public ObavezeService(EmailService emailService, PrijaveService prijaveService, ObavezeRepo obavezeRepo, NastavniciRepo nastavniciRepo){
        this.emailService = emailService;
        this.prijaveService = prijaveService;
        this.obavezeRepo = obavezeRepo;
        this.nastavniciRepo = nastavniciRepo;
    }
    
    @Scheduled(cron = "0 0 * * * *")
    public void slanjeSpiskaPrijavljenih(){
        List<Obaveza> obaveze = obavezeRepo.dohvatanjeIsteklihNeobradjenihObaveza();

        for (Obaveza o: obaveze){
            if ("domaci".equalsIgnoreCase(o.getTip())) continue;

            List<Nastavnik> nastavnici = nastavniciRepo.dohvatanjeNastavnikaNaPredmetu(o.getPredmet());

            String naslov = "[" + o.getSifraPredmeta() + "]" +"Spisak prijavljenih studenata";
            String tekstHtml = "<html> <body>" +
                "<p>Rok za prijavu obaveze je istekao.</p>" +
                "<p><strong>Obaveza:</strong><br>" + o.getNaziv() + "</p>" +
                "<p><strong>Predmet:</strong><br>" + o.getNazivPredmeta() + "</p>" +
                "<p>U prilogu se nalazi spisak studenata koji su se prijavili za izradu ove obaveze.</p>" +
                "</body> </html>";

            String nazivFajla = "spisak" + o.getId() + ".xlsx";
            byte[] prilog = prijaveService.generisanjeListePrijavljenihStudenata(o.getId());

            for (Nastavnik n: nastavnici){
                this.emailService.slanjeEmailaSaPrilogom(n.getEmail(), naslov, tekstHtml, prilog, nazivFajla);
            }

            this.obavezeRepo.oznaciMailPoslat(o.getId());
        }
    }
}
