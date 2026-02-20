package com.example.backend.kontroleri;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.db.PredajaRepo;
import com.example.backend.modeli.Predaja;

@RestController
@RequestMapping("/predaje")
@CrossOrigin(origins = "http://localhost:4200")
public class PredajeController {

    @Value("${studentski.radovi.dir}")
    private String direktorijumZaRadove;
    
    @PostMapping("/nova")
    public ResponseEntity<?> novaPredaja(@RequestParam("file") MultipartFile fajl, @RequestParam("idObaveza") Long idObaveza,
        @RequestParam("idStudent") Long idStudent, @RequestParam("student") String student){
            try{
                String nazivFajla = fajl.getOriginalFilename();
                if (!nazivFajla.endsWith(".zip")) {
                    return ResponseEntity.badRequest().body(-1);
                }

                Path folder = Paths.get(direktorijumZaRadove, "obaveza" + idObaveza);
                Files.createDirectories(folder);

                String noviNaziv = student + ".zip";
                Path putanja = folder.resolve(noviNaziv);


                Files.write(putanja, fajl.getBytes());

                String putanjaZaBazu = putanja.toString();

                Integer status = new PredajaRepo().novaPredaja(idStudent, idObaveza, putanjaZaBazu);
                if (status < 0) return ResponseEntity.status(500).body(-1);

                return ResponseEntity.ok(0);
            }
             catch (Exception e) {
                return ResponseEntity.status(500).body(-1);
            }
    }

    @GetMapping("zaStudenta/{id}")
    public List<Predaja> predatiRadoviZaStudenta(@PathVariable Long id){
        return new PredajaRepo().predatiRadoviZaStudenta(id);
    }

    @GetMapping("/preuzimanje/{idObaveze}/{student}")
    public ResponseEntity<Resource> preuzimanjeRada(@PathVariable Long idObaveze, @PathVariable String student) {
        try {
            String imeFajla = student + ".zip";
            Path putanja = Paths.get(direktorijumZaRadove, "obaveza" + idObaveze, imeFajla);

            if (!Files.exists(putanja)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            Resource resource = new UrlResource(putanja.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/preuzmiSve/{idObaveze}")
    public ResponseEntity<Resource> preuzmiSveRadove(@PathVariable Long idObaveze) {
        try {
            List<Predaja> radovi = new PredajaRepo().predatiRadoviZaObavezu(idObaveze);

            if (radovi.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Path tempZip = Files.createTempFile("radoviZaObavezu" + idObaveze, ".zip");

            try (ZipOutputStream zos = new ZipOutputStream(Files.newOutputStream(tempZip))) {
                for (Predaja p : radovi) {
                    Path putanja = Paths.get(p.getPutanja());
                    if (Files.exists(putanja)) {
                        ZipEntry zipEntry = new ZipEntry(putanja.getFileName().toString());
                        zos.putNextEntry(zipEntry);
                        Files.copy(putanja, zos);
                        zos.closeEntry();
                    }
                }
            }

            Resource resource = new UrlResource(tempZip.toUri());

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"predati_radovi_obaveza" + idObaveze + ".zip\"")
                    .body(resource);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/brojRadova/{idObaveza}")
    public int brojPredatihRadova(@PathVariable Long idObaveza){
        return new PredajaRepo().brojPredatihRadova(idObaveza);
    }

}

