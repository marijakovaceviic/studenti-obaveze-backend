package com.example.backend.kontroleri;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.StudentiRepo;
import com.example.backend.modeli.Student;


@RestController
@RequestMapping("/studenti")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentiController {
    
    @PostMapping("/provera")
    public long provera(@RequestBody String email) {
        return new StudentiRepo().proveraStudenta(email);
    }

    @PostMapping("/registracija")
    public int registracija(@RequestBody Student student) {
        return new StudentiRepo().registracijaStudenta(student);
    }
    
    @PostMapping("/prijava")
    public Student prijava(@RequestBody Student student) {
        return new StudentiRepo().prijavaStudenta(student);
    }
    
    @PostMapping("/promenaLozinke")
    public int promenaLozinke(@RequestBody Student student) {        
        return new StudentiRepo().promenaLozinke(student);
    }

        
}
