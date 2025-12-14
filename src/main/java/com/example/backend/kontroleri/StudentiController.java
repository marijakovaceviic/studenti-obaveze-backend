package com.example.backend.kontroleri;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.db.StudentiRepo;
import com.example.backend.modeli.Student;
import com.example.backend.modeli.StudentDTO;


@RestController
@RequestMapping("/studenti")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentiController {
    
    private final StudentiRepo studentiRepo;

    public StudentiController(StudentiRepo studentiRepo){
        this.studentiRepo = studentiRepo;
    }

    @PostMapping("/provera")
    public long provera(@RequestBody String email) {
        return this.studentiRepo.proveraStudenta(email);
    }

    @PostMapping("/registracija")
    public int registracija(@RequestBody Student student) {
        String hesiranaLozinka = hesiranjeLozinke(student.getLozinka());
        student.setLozinka(hesiranaLozinka);
        return this.studentiRepo.registracijaStudenta(student);
    }
    
    @PostMapping("/prijava")
    public Student prijava(@RequestBody Student student) {
        return this.studentiRepo.prijavaStudenta(student);
    }
    
    @PostMapping("/promenaLozinke")
    public int promenaLozinke(@RequestBody StudentDTO student) {        
        String hesiranaNovaLozinka = hesiranjeLozinke(student.getNovaLozinka());
        return this.studentiRepo.promenaLozinke(student.getEmail(), student.getLozinka(), hesiranaNovaLozinka);
    }

    private String hesiranjeLozinke(String lozinka){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
        String hash = encoder.encode(lozinka.toString());
        return hash;
    }    
}
