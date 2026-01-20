package com.example.backend.servisi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

@Service
public class LdapAuthService {
    
    @Autowired
    private LdapTemplate ldapTemplate;

    public boolean autentifikacija(String korisnickoIme, String lozinka, boolean angazovan) {
        //String dn = "cn=" + korisnickoIme + ",ou=Studenti,ou=Korisnici,ou=Struktura,dc=ETF,dc=LOCAL";
        String ou = angazovan ? "Angazovani" : "Studenti";
        String dn = "cn=" + korisnickoIme + ",ou=" + ou + ",ou=Korisnici,ou=Struktura,dc=ETF,dc=LOCAL";
        try {
            return ldapTemplate.authenticate("", dn, lozinka);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
