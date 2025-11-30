package com.example.backend.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.backend.modeli.Nastavnik;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class NastavniciRepo implements NastavniciRepoInterface {

    @Override
    public int dodavanjeNastavnika(Nastavnik nastavnik) {

        //provera da li nastavnik vec ima nalog
        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT id FROM nastavnici WHERE email = ?"
            )) {

            ps.setString(1, nastavnik.getEmail());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }

        // dodavanje naloga
        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO nastavnici(ime, prezime, email, lozinka) VALUES(?, ?, ?, ?)"
            )) {

            ps.setString(1, nastavnik.getIme());
            ps.setString(2, nastavnik.getPrezime());
            ps.setString(3, nastavnik.getEmail());

            String lozinka = generisanjeLozinke();
            String hesiranaLozinka = hesiranjeLozinke(lozinka);
            ps.setString(4, hesiranaLozinka);
            
            return ps.executeUpdate();
             
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String generisanjeLozinke(){
        String karakteri = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!$%#@*";
        StringBuilder lozinka = new StringBuilder();
        java.util.Random rand = new java.util.Random();

        for (int i = 0; i < 10; i++) {   // lozinka od 10 karaktera
            lozinka.append(karakteri.charAt(rand.nextInt(karakteri.length())));
        }
        
        //poslati nastavniku sifru na email
        return lozinka.toString();
    }

    private String hesiranjeLozinke(String lozinka){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); 
        String hash = encoder.encode(lozinka.toString());
        return hash;
    }

    @Override
    public List<Nastavnik> dohvatanjeSvihNastavnika() {
        List<Nastavnik> nastavnici = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
        "SELECT * FROM nastavnici")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Nastavnik n = new Nastavnik(
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getString("email")
                       );
                n.setId(rs.getLong("id"));
                nastavnici.add(n);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nastavnici;
    }

    @Override
    public List<Nastavnik> dohvatanjeNastavnikaNaPredmetu(Long idPredmeta) {
       List<Nastavnik> nastavnici = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
        "SELECT n.* FROM nastavnik_predmet np JOIN nastavnici n ON (np.idNastavnik = n.id) WHERE np.idPredmet = ?")) {

            stmt.setLong(1, idPredmeta);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Nastavnik n = new Nastavnik(
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getString("email")
                       );
                n.setId(rs.getLong("id"));
                nastavnici.add(n);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nastavnici;
    }

    @Override
    public int dodavanjeNastavnikaNaPredmet(Long idNastavnik, Long idPredmet) {

        // prvo proveravam da li je nastavnik vec na predmetu
        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps1 = conn.prepareStatement(
                "SELECT 1 FROM nastavnik_predmet WHERE idNastavnik = ? AND idPredmet = ?"
            )) {

                ps1.setLong(1, idNastavnik);
                ps1.setLong(2, idPredmet);
                ResultSet rs = ps1.executeQuery();

                if (rs.next()) {
                    return 0;
                }

            } catch (SQLException e) {
                e.printStackTrace();
                return 0;
            }

            try (Connection conn = DB.source().getConnection();
                PreparedStatement ps2 = conn.prepareStatement(
                    "INSERT INTO nastavnik_predmet(idNastavnik, idPredmet) VALUES (?, ?)"
                )) {

                ps2.setLong(1, idNastavnik);
                ps2.setLong(2, idPredmet);
                return ps2.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return 0;
    }


    @Override
    public int brisanjeNastavnikaSaPredmeta(Long idNastavnik, Long idPredmet) {
        
        try (Connection conn = DB.source().getConnection();
         PreparedStatement ps = conn.prepareStatement(
            "DELETE FROM nastavnik_predmet WHERE idNastavnik = ? AND idPredmet = ?"
         )) {

            ps.setLong(1, idNastavnik);
            ps.setLong(2, idPredmet);

            return ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
    
}
