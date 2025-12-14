package com.example.backend.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.backend.modeli.Nastavnik;

@Repository
public class NastavniciRepo implements NastavniciRepoInterface {

    @Override
    public int dodavanjeNastavnika(Nastavnik nastavnik, String hesiranaLozinka) {

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
            ps.setString(4, hesiranaLozinka);
            
            return ps.executeUpdate();
             
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
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

    @Override
    public Nastavnik login(String email, String lozinka) {
        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM nastavnici WHERE email = ?"
            )) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashIzBaze = rs.getString("lozinka");
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    if (encoder.matches(lozinka, hashIzBaze)) {
                        Nastavnik n = new Nastavnik();
                        n.setId(rs.getLong("id"));
                        n.setIme(rs.getString("ime"));
                        n.setPrezime(rs.getString("prezime"));
                        n.setEmail(rs.getString("email"));
                        n.setLozinka(rs.getString("lozinka"));
                        return n;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; 
    }

    @Override
    public int promenaLozinke(String email, String staraLozinka, String hesiranaNovaLozinka) {
        try (Connection conn = DB.source().getConnection();) {
            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM nastavnici WHERE email = ?")) {
                ps.setString(1, email);

                String hashIzBaze;
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        hashIzBaze = rs.getString("lozinka");    
                    }
                    else{
                        return 0;
                    }
                }
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                if (!encoder.matches(staraLozinka, hashIzBaze)) {
                    return 0;
                }
                try (PreparedStatement ps1 = conn.prepareStatement(
                        "UPDATE nastavnici SET lozinka = ? where email= ?")) {

                    ps1.setString(1, hesiranaNovaLozinka);
                    ps1.setString(2, email);

                    return ps1.executeUpdate();

                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;

    }

}
