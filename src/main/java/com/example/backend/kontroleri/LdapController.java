package com.example.backend.kontroleri;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.backend.modeli.KorisnikDTO;
import com.example.backend.modeli.Nastavnik;
import com.example.backend.modeli.Student;
import com.example.backend.servisi.LdapAuthService;

@RestController
@RequestMapping("/ldap")
@CrossOrigin(origins = "http://localhost:4200")
public class LdapController {

    private final LdapAuthService ldapAuthService;

    public LdapController(LdapAuthService ldapAuthService) {
        this.ldapAuthService = ldapAuthService;
    }

    
    @PostMapping("prijavaStudent")
    public Student login(@RequestBody KorisnikDTO korisnik){
        boolean ok = ldapAuthService.autentifikacija(korisnik.getKorisnickoIme(), korisnik.getLozinka(), false);
        if (ok){
            Student s = new Student();
            s.setTip("student");
            s.setId(1L);
            return s;
            //ovo je privremeno
            //inace bih proverila postoji li student sa tim emailom u lokalnoj bazi
            //ako ne postoji dodala bih ga i vratila poda0tke, a ako postoji odmah dohvatam podatke i vracam 
        }
            
        return null;
    }

    @PostMapping("prijavaNastavnik")
    public Nastavnik loginNastavnik(@RequestBody KorisnikDTO korisnik){
        boolean ok = ldapAuthService.autentifikacija(korisnik.getKorisnickoIme(), korisnik.getLozinka(), true);
        if (ok){
            //ovo je samo privremeno resenje, kao i kod studenta
            Nastavnik n = new Nastavnik();
            n.setTip("nastavnik");
            n.setId(1L);
            return n;
        } 
        return null;
    }
}
