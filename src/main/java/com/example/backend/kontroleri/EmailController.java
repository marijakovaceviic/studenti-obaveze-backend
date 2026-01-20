package com.example.backend.kontroleri;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.DemonstratoriRepo;
import com.example.backend.db.ObavezeRepo;
import com.example.backend.db.StudentiRepo;
import com.example.backend.modeli.DemonstartoriForma;
import com.example.backend.modeli.Obaveza;
import com.example.backend.modeli.Predmet;
import com.example.backend.modeli.Student;
import com.example.backend.servisi.EmailService;

@RestController
@RequestMapping("/email")
@CrossOrigin(origins = "http://localhost:4200")
public class EmailController {

    private final EmailService emailService;
    private final DemonstratoriRepo demonstratoriRepo;
    private final StudentiRepo studentiRepo;
    private final ObavezeRepo obavezeRepo;

    public EmailController(EmailService emailService, DemonstratoriRepo dr, StudentiRepo sr, ObavezeRepo or){
        this.emailService = emailService;
        this.demonstratoriRepo = dr;
        this.studentiRepo = sr;
        this.obavezeRepo = or;
    }

    @GetMapping("uspesnaPrijava/{idStudent}/{idObaveza}")
    public int slanjeMejlaOUspesnostiPrijave(@PathVariable Long idStudent, @PathVariable Long idObaveza){
        String email = studentiRepo.dohvatanjeMejlaStudenta(idStudent);
        Obaveza obaveza = obavezeRepo.dohvatanjeObaveze(idObaveza);

        String naslov = "Uspešna prijava za obavezu";
        LocalDateTime datum = LocalDateTime.now();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formatiranDatum = datum.format(formater);

        String html = "<html>" + "<body>" +
                  "<p>Uspešno ste se prijavili za obavezu: <br> <strong>" + obaveza.getNaziv() + "</strong> na predmetu: <br>" 
                  + obaveza.getNazivPredmeta() + " (" + obaveza.getSifraPredmeta() + ").</p>" +
                  "<p>Datum i vreme prijave: " + formatiranDatum + "</p>" +
                  "<hr>" +
                  "<p style='font-size:smaller; color:gray;'>Ovo je automatski generisan mejl, ne odgovarajte na njega.</p>" +
                  "</body>" +
                  "</html>";
        return emailService.slanjeHtmlEmaila(email, naslov, html);
    }

    @GetMapping("odjava/{idStudent}/{idObaveza}")
    public int slanjeMejlaOOdjaviObaveze(@PathVariable Long idStudent, @PathVariable Long idObaveza){
        String email = studentiRepo.dohvatanjeMejlaStudenta(idStudent);
        Obaveza obaveza = obavezeRepo.dohvatanjeObaveze(idObaveza);

        String naslov = "Odjava obaveze";
        LocalDateTime datum = LocalDateTime.now();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formatiranDatum = datum.format(formater);

        String html = "<html>" + "<body>" +
                  "<p>Uklonili ste prijavu za obavezu: <br> <strong>" + obaveza.getNaziv() + "</strong> na predmetu: <br>" 
                  + obaveza.getNazivPredmeta() + " (" + obaveza.getSifraPredmeta() + ").</p>" +
                  "<p>Datum i vreme prijave: " + formatiranDatum + "</p>" +
                  "<p> Možete se prijaviti ponovo do isteka roka obaveze!</p>"+
                  "<hr>" +
                  "<p style='font-size:smaller; color:gray;'>Ovo je automatski generisan mejl, ne odgovarajte na njega.</p>" +
                  "</body>" +
                  "</html>";
        return emailService.slanjeHtmlEmaila(email, naslov, html);
    }

    @GetMapping("uspesnaPredaja/{idStudent}/{idObaveza}")
    public int slanjeMejlaOUspesnostiPredaje(@PathVariable Long idStudent, @PathVariable Long idObaveza){
        String email = studentiRepo.dohvatanjeMejlaStudenta(idStudent);
        Obaveza obaveza = obavezeRepo.dohvatanjeObaveze(idObaveza);

        String naslov = "Uspešna predaja rada";
        LocalDateTime datum = LocalDateTime.now();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formatiranDatum = datum.format(formater);

        String html = "<html>" + "<body>" +
                  "<p>Uspešno ste predali rad za obavezu: <br> <strong>" + obaveza.getNaziv() + "</strong> na predmetu: <br>" 
                  + obaveza.getNazivPredmeta() + " (" + obaveza.getSifraPredmeta() + ").</p>" +
                  "<p>Datum i vreme predaje: " + formatiranDatum + "</p>" +
                  "<hr>" +
                  "<p style='font-size:smaller; color:gray;'>Ovo je automatski generisan mejl, ne odgovarajte na njega.</p>" +
                  "</body>" +
                  "</html>";
        return emailService.slanjeHtmlEmaila(email, naslov, html);
    }


    @GetMapping("otvorenaObaveza/{idObaveza}")
    public int otvorenaObaveza(@PathVariable Long idObaveza){
        Obaveza o = obavezeRepo.dohvatanjeObaveze(idObaveza);
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

        int status = 0;
        for (Student s: studenti){
            status = emailService.slanjeHtmlEmaila(s.getEmail(), naslov, html);
            if (status < 0){
                return status;
            }
        }
        return status;
    }

    @GetMapping("otvorenaPrijavaZaDemonstratore")
    public int otvorenaPrijavaZaDemonstratore(@PathVariable Long idForma){
        DemonstartoriForma forma = demonstratoriRepo.dohvatiAktivnuFormu();
        List<Student> studenti = studentiRepo.dohvatanjeRegistrovanihStudenata();

        String naslov = "Otvorena prijava za demonstratore";
        LocalDateTime datum = forma.getKraj();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formatiranDatum = datum.format(formater);

        String html = "<html>" + "<body>" +
                  "<p>Otvorena je prijava za demonstratorski tim na katedri za Računarsku tehniku i informatiku.</p>" 
                   + "<p>Rok za prijavu je: " + formatiranDatum + "</p>" +
                  "<hr>" +
                  "<p style='font-size:smaller; color:gray;'>Ovo je automatski generisan mejl, ne odgovarajte na njega.</p>" +
                  "</body>" +
                  "</html>";
         
        int status = 0;
        for (Student s: studenti){
            status = emailService.slanjeHtmlEmaila(s.getEmail(), naslov, html);
            if (status < 0){
                return status;
            }
        }
        return status;
    }
    
    @GetMapping("uspesnaPrijavaZaDemonstratora/{idStudent}")
    public int uspesnaPrijavaZaDemonstratora(@PathVariable Long idStudent){
        DemonstartoriForma forma = demonstratoriRepo.dohvatiAktivnuFormu();
        List<Predmet> predmeti = demonstratoriRepo.prijavljeniPredmetiZaStudenta(idStudent, forma.getId());
        String email = studentiRepo.dohvatanjeMejlaStudenta(idStudent);

        String naslov = "Uspešna prijava za demonstratorski tim";
        LocalDateTime datum = LocalDateTime.now();
        DateTimeFormatter formater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        String formatiranDatum = datum.format(formater);

        StringBuilder lista = new StringBuilder();
        lista.append("<ul>");

        for (Predmet p : predmeti) {
            lista.append("<li>").append(p.getNaziv()).append(" (").append(p.getSifra()).append(")")
                .append("</li>");
        }
        lista.append("</ul>");

        String html = "<html>" + "<body>" +
                  "<p>Uspešno ste se prijavili za demonstratorski tim katedre za Računarsku tehniku i informatiku.</p>" +
                  "<p>Datum i vreme prijave: " + formatiranDatum + "</p>" +
                  "<p><strong>Prijavili ste se za sledeće predmete:</strong></p>" + lista.toString() +
                  "<hr>"  +
                  "<p style='font-size:smaller; color:gray;'>Ovo je automatski generisan mejl, ne odgovarajte na njega.</p>" +
                  "</body>" +
                  "</html>";
        return emailService.slanjeHtmlEmaila(email, naslov, html);
    }
}
