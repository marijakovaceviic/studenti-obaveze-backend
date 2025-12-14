package com.example.backend.servisi;

import java.io.ByteArrayOutputStream;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.example.backend.db.PrijavaRepo;
import com.example.backend.modeli.Student;

@Service
public class PrijaveService {
    
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
}
