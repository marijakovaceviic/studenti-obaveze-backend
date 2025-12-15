package com.example.backend.kontroleri;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.db.DemonstratoriRepo;
import com.example.backend.modeli.DemonstartoriForma;
import com.example.backend.modeli.DemonstratoriPrijava;
import com.example.backend.modeli.Predmet;
import com.example.backend.modeli.PredmetPrijavljeniDTO;
import com.example.backend.modeli.Student;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/demonstratori")
@CrossOrigin(origins = "http://localhost:4200")
public class DemonstratoriController {

    private final DemonstratoriRepo demonstratoriRepo;

    public DemonstratoriController(DemonstratoriRepo demonstratoriRepo) {
        this.demonstratoriRepo = demonstratoriRepo;
    }

    @PostMapping("/otvaranjePrijave")
    public ResponseEntity<?> otvaranjePrijave(@RequestParam("pdf") MultipartFile pdf, @RequestParam String pocetak,
            @RequestParam String kraj, @RequestParam Long nastavnikId) {
        try {
            boolean nastavnikZaduzen = demonstratoriRepo.daLiJeZaduzenZaDemonstratore(nastavnikId);
            if (nastavnikZaduzen) {
                if (pdf != null && !pdf.isEmpty()) {

                    Path direktorijum = Paths.get("D:/demonstratori");
                    if (!Files.exists(direktorijum)) {
                        Files.createDirectories(direktorijum);
                    }

                    String imeFajla = System.currentTimeMillis() + "_" + pdf.getOriginalFilename();
                    Path putanjaFajla = direktorijum.resolve(imeFajla);

                    Files.copy(pdf.getInputStream(), putanjaFajla, StandardCopyOption.REPLACE_EXISTING);

                    String pdfPutanja = putanjaFajla.toString();

                    DemonstartoriForma forma = new DemonstartoriForma();
                    forma.setIdNastavnik(nastavnikId);
                    forma.setPocetak(LocalDateTime.parse(pocetak));
                    forma.setKraj(LocalDateTime.parse(kraj));
                    forma.setKonkurs(pdfPutanja);

                    int status = demonstratoriRepo.cuvanjeNoveForme(forma);
                    if (status == 0){
                        return ResponseEntity.status(500).body(-1);
                    }
                }
                return ResponseEntity.ok("Sacuvano");
            }
            return ResponseEntity.status(500).body(0);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(-1);
        }
    }

    @GetMapping("/aktivnaPrijava")
    public DemonstartoriForma dohvatanjeAktivneForme(){
        return demonstratoriRepo.dohvatiAktivnuFormu();
    }

    @PostMapping("/novaPrijava")
    public int novaPrijava(@RequestBody DemonstratoriPrijava prijava){
        return demonstratoriRepo.novaPrijava(prijava);
    }

    @GetMapping("/preuzimanjeKonkursa/{id}")
    public ResponseEntity<?> preuzmiPdf(@PathVariable Long id) {
        String putanja = demonstratoriRepo.putanjaFajlaKonkursa(id);
        if (putanja == null){
            return ResponseEntity.status(404).body("PDF nije pronađen.");
        }
        try {
            File pdf = new File(putanja);

            if (!pdf.exists()) {
                return ResponseEntity.status(404).body("PDF nije pronađen.");
            }

            InputStreamResource resource = new InputStreamResource(new FileInputStream(pdf));

            return ResponseEntity.ok()
                .contentLength(pdf.length())
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + pdf.getName() + "\"")
                .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Greška prilikom preuzimanja PDF-a.");
        }
    }

    @GetMapping("/prijavljeniZaPredmet/{idPredmet}")
    public List<Student> prijavljeniStudentiZaPredmetPoslednjaForma(@PathVariable Long idPredmet){
        return demonstratoriRepo.prijavljeniStudentiZaPredmetPoslednjaForma(idPredmet);
    }

    @GetMapping("/prijavljeniStudentiPoPredmetima")
    public List<PredmetPrijavljeniDTO> prijavljeniStudentiPoPredmetima(){
        return demonstratoriRepo.prijavljeniStudentiZaPoslednjuFormu();
    }

    @GetMapping("/spisakPrijavljenih/{idPredmet}")
    public void spisakPrijavljenihDemonstratoraZaPredmet(@PathVariable Long idPredmet, HttpServletResponse odgovor) {
        List<Student> prijavljeni = demonstratoriRepo.prijavljeniStudentiZaPredmetPoslednjaForma(idPredmet);

        odgovor.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        odgovor.setHeader("Content-Disposition", "attachment; filename=prijave_predmet_" + idPredmet + ".xlsx");

        try (Workbook sveska = new XSSFWorkbook()) {
            Sheet list = sveska.createSheet("Prijavljeni");

            Row zaglavlje = list.createRow(0);
            zaglavlje.createCell(0).setCellValue("Redni broj");
            zaglavlje.createCell(1).setCellValue("Indeks");
            zaglavlje.createCell(2).setCellValue("Ime");
            zaglavlje.createCell(3).setCellValue("Prezime");
            zaglavlje.createCell(4).setCellValue("Email");

            int br = 1;
            for (Student s : prijavljeni) {
                Row red = list.createRow(br);
                red.createCell(0).setCellValue(br);
                String indeks = String.format("%04d", s.getBrIndeksa());
                red.createCell(1).setCellValue(s.getGodinaUpisa() + "/" + indeks);
                red.createCell(2).setCellValue(s.getIme());
                red.createCell(3).setCellValue(s.getPrezime());
                red.createCell(4).setCellValue(s.getEmail());
                br++;
            }

            for (int i = 0; i <= 4; i++) {
                list.autoSizeColumn(i);
            }

            sveska.write(odgovor.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
            odgovor.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/zaduzenNastavnik/{id}")
    public boolean daLiJeZaduzenZaDemonstratore(@PathVariable Long id){
        return demonstratoriRepo.daLiJeZaduzenZaDemonstratore(id);
    }

    @GetMapping("/prijavljeniPredmeti/{idStudent}/{idForma}")
    public List<Predmet> prijavljeniPredmetiZaStudenta(@PathVariable Long idStudent, @PathVariable Long idForma){
        return demonstratoriRepo.prijavljeniPredmetiZaStudenta(idStudent, idForma);
    }
}
