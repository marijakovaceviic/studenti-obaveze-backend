package com.example.backend.kontroleri;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.PrijavaRepo;
import com.example.backend.modeli.Obaveza;
import com.example.backend.modeli.Student;
import com.example.backend.servisi.PrijaveService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/prijave")
@CrossOrigin(origins = "http://localhost:4200")
public class PrijaveController {
    
    private final PrijavaRepo prijavaRepo;
    private final PrijaveService prijaveService;

    public PrijaveController(PrijavaRepo prijavaRepo, PrijaveService prijaveService) {
        this.prijavaRepo = prijavaRepo;
        this.prijaveService = prijaveService;
    }

    @PostMapping("/nova/{idStudent}/{idObaveza}")
    public int novaPrijava(@PathVariable Long idStudent, @PathVariable Long idObaveza) {
        return this.prijaveService.prijavaObaveze(idObaveza, idStudent);
    }

    @GetMapping("/provera/{idStudent}/{idObaveza}")
    public int proveraPrijave(@PathVariable Long idStudent, @PathVariable Long idObaveza){
        return this.prijavaRepo.daLiJePrijavljen(idStudent, idObaveza);
    }

    @GetMapping("/svePrijaveZaObavezu/{idObaveza}")
    public List<Student> dohvatanjeSvihPrijavaZaObavezu(@PathVariable Long idObaveza){
        return this.prijavaRepo.dohvatanjeSvihPrijavaZaObavezu(idObaveza);
    }

    @GetMapping("/preuzimanjeSpiska/{idObaveza}")
    public void preuzimanjeSpiska(@PathVariable Long idObaveza, HttpServletResponse odgovor){
        List<Student> prijavljeni = new PrijavaRepo().dohvatanjeSvihPrijavaZaObavezu(idObaveza);

        odgovor.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        odgovor.setHeader("Content-Disposition", "attachment; filename=prijave_obaveza_" + idObaveza + ".xlsx");

        try (Workbook sveska = new XSSFWorkbook()) {
            Sheet list = sveska.createSheet("Prijave");

            Row zaglavlje = list.createRow(0);
            zaglavlje.createCell(0).setCellValue("Redni broj");
            zaglavlje.createCell(1).setCellValue("Indeks");
            zaglavlje.createCell(2).setCellValue("Ime");
            zaglavlje.createCell(3).setCellValue("Prezime");

            int br = 1;
            for (Student s: prijavljeni){
                Row red = list.createRow(br);
                red.createCell(0).setCellValue(br);
                String indeks = String.format("%04d", s.getBrIndeksa());
                red.createCell(1).setCellValue(s.getGodinaUpisa() + "/" + indeks);
                red.createCell(2).setCellValue(s.getIme());
                red.createCell(3).setCellValue(s.getPrezime());
                br++;
            }

            for (int i = 0; i < 4; i++) {
                list.autoSizeColumn(i);
            }

            sveska.write(odgovor.getOutputStream());

        } catch (IOException e) {
            e.printStackTrace();
            odgovor.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/prijaveZaLab/student/{id}")
    public List<Obaveza> prijaveLab(@PathVariable Long id) { 
        return this.prijavaRepo.dohvatanjePrijavaZaStudenta(id, "lab");
    }

    @GetMapping("/prijaveZaOdbrane/student/{id}")
    public List<Obaveza> prijaveOdbrane(@PathVariable long id) { 
        return this.prijavaRepo.dohvatanjePrijavaZaStudenta(id, "odbrana");
    }

    @GetMapping("/prijaveZaKolokvijum/student/{id}")
    public List<Obaveza> prijaveKolokvijum(@PathVariable long id) { 
        return this.prijavaRepo.dohvatanjePrijavaZaStudenta(id, "kolokvijum");
    }

    @GetMapping("/prijaveZaIspit/student/{id}")
    public List<Obaveza> prijaveIspit(@PathVariable long id) { 
        return this.prijavaRepo.dohvatanjePrijavaZaStudenta(id, "ispit");
    }

    @GetMapping("/brojPrijava/{idObaveza}")
    public int brojPrijavljenih(@PathVariable Long idObaveza){
        return this.prijavaRepo.brojPrijavljenih(idObaveza);
    }

    @DeleteMapping("/odjava/{idStudent}/{idObaveza}")
    public int odjava(@PathVariable Long idStudent, @PathVariable Long idObaveza){
        return this.prijaveService.odjavaObaveze(idObaveza, idStudent);
    }
}
