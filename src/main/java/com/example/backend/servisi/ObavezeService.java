package com.example.backend.servisi;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.backend.db.NastavniciRepo;
import com.example.backend.db.ObavezeRepo;
import com.example.backend.db.StudentiRepo;
import com.example.backend.modeli.Nastavnik;
import com.example.backend.modeli.Obaveza;
import com.example.backend.modeli.Student;

@Service
public class ObavezeService {

    private final EmailService emailService;
    private final PrijaveService prijaveService;
    private final ObavezeRepo obavezeRepo;
    private final NastavniciRepo nastavniciRepo;
    private final StudentiRepo studentiRepo;

    public ObavezeService(EmailService emailService, PrijaveService prijaveService, ObavezeRepo obavezeRepo, NastavniciRepo nastavniciRepo,
        StudentiRepo studentiRepo
    ){
        this.emailService = emailService;
        this.prijaveService = prijaveService;
        this.obavezeRepo = obavezeRepo;
        this.nastavniciRepo = nastavniciRepo;
        this.studentiRepo = studentiRepo;
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

            this.obavezeRepo.setPoslatMejlNastavniku(o.getId());
        }
    }

    @Scheduled(cron = "0 0 * * * *")
    public void obavestenjeOtvorenaObaveza(){
        List<Obaveza> obaveze = obavezeRepo.dohvatanjeAktivnihNeobavestenihObaveza();

        for (Obaveza o: obaveze){
            List<Student> studenti = studentiRepo.dohvatanjeStudenataKojiPratePredmet(o.getPredmet());

            String naslov = "Obaveštenje o otvorenoj obavezi";
            LocalDateTime datum = o.getKraj();
            DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            String formatiranDatum = datum.format(formater);
            String html = "<html><body>";

            if ("domaci".equalsIgnoreCase(o.getTip())) {
                html += "<p>Otvorena je predaja domaćeg zadatka: <br><strong>" + o.getNaziv() + "</strong></p>";
            } else {
                html += "<p>Otvorena je prijava za obavezu: <br><strong>" + o.getNaziv() + "</strong></p>";
            }

            html += "<p>Predmet: " + o.getNazivPredmeta() + " (" + o.getSifraPredmeta() + ").</p>"; 

            if ("domaci".equalsIgnoreCase(o.getTip())) {
                html += "<p>Rok predaje: " + formatiranDatum + "</p>";
            } else {
                html += "<p>Rok prijave: " + formatiranDatum + "</p>";
            }
            html += "<hr>" +
                "<p style='font-size:smaller; color:gray;'>Ovo je automatski generisan mejl, ne odgovarajte na njega.</p>" +
                "</body></html>";
 
            for (Student s: studenti){
                emailService.slanjeHtmlEmaila(s.getEmail(), naslov, html);     
            } 
            
            this.obavezeRepo.setPoslatMejlStudentima(o.getId());
        }
    }


}
