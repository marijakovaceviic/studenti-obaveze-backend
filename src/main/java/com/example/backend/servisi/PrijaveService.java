package com.example.backend.servisi;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.backend.db.ObavezeRepo;
import com.example.backend.db.PrijavaRepo;
import com.example.backend.modeli.Obaveza;
import com.example.backend.modeli.Student;

@Service
public class PrijaveService {
    private final ObavezeRepo obavezeRepo;
    private final PrijavaRepo prijavaRepo;

    public PrijaveService(ObavezeRepo obavezeRepo, PrijavaRepo prijavaRepo){
        this.obavezeRepo = obavezeRepo;
        this.prijavaRepo = prijavaRepo;
    }

    public byte[] generisanjeListePrijavljenihStudenata(Long idObaveza) {
        List<Student> studenti = new PrijavaRepo().dohvatanjeSvihPrijavaZaObavezu(idObaveza);
        try (Workbook sveska = new XSSFWorkbook()) {
            Sheet list = sveska.createSheet("Prijavljeni studenti");

            Row zaglavlje = list.createRow(0);
            zaglavlje.createCell(0).setCellValue("RB");
            zaglavlje.createCell(1).setCellValue("Indeks");
            zaglavlje.createCell(2).setCellValue("Ime");
            zaglavlje.createCell(3).setCellValue("Prezime");

            int br = 1;
            for (Student s: studenti){
                Row red = list.createRow(br);
                red.createCell(0).setCellValue(br);
                String indeks = String.format("%04d", s.getBrIndeksa());
                red.createCell(1).setCellValue(s.getGodinaUpisa() + "/" + indeks);
                red.createCell(2).setCellValue(s.getIme());
                red.createCell(3).setCellValue(s.getPrezime());
                br++;
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            sveska.write(bos);
            return bos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int prijavaObaveze(Long idObaveza, Long idStudent){
        Obaveza obaveza = this.obavezeRepo.dohvatanjeObaveze(idObaveza);
        if (obaveza == null){
            return -1;
        }
        LocalDateTime sada = LocalDateTime.now();

        if (sada.isBefore(obaveza.getPocetak()) || sada.isAfter(obaveza.getKraj())) {
            return -2;
        }

        return this.prijavaRepo.novaPrijava(idStudent, idObaveza);
    }

    public int odjavaObaveze(Long idObaveza, Long idStudent){
        Obaveza obaveza = this.obavezeRepo.dohvatanjeObaveze(idObaveza);
        if (obaveza == null){
            return -1;
        }
        LocalDateTime sada = LocalDateTime.now();

        if (sada.isBefore(obaveza.getPocetak()) || sada.isAfter(obaveza.getKraj())) {
            return -2;
        }
        return this.prijavaRepo.odjava(idStudent, idObaveza);
    }
}
