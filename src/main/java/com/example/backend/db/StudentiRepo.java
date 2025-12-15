package com.example.backend.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.backend.modeli.Student;

@Repository
public class StudentiRepo implements StudentiRepoInterface {

    @Override
    public long proveraStudenta(String email) {
        try (
            Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM studenti WHERE email = ?")
        ) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return rs.getLong("id");
            } 

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0L;
    }

    
    @Override
    public int registracijaStudenta(Student student) {
        try (Connection conn = DB.source().getConnection()) {
            // Provera da li je student vec registrovan
            try (PreparedStatement ps1 = conn.prepareStatement(
            "SELECT 1 FROM registrovani WHERE email = ?")) {
                ps1.setString(1, student.getEmail());
                ResultSet rs = ps1.executeQuery();
                if (rs.next()) {
                    return 0;
                }
            }
            // Registrovanje studenta
            try (PreparedStatement ps2 = conn.prepareStatement(
            "INSERT INTO registrovani(id, email, lozinka) VALUES(?, ?, ?)")) {
                ps2.setLong(1, student.getId());
                ps2.setString(2, student.getEmail());
                ps2.setString(3, student.getLozinka());
                return ps2.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


   @Override
    public Student prijavaStudenta(Student student) {
        try (Connection conn = DB.source().getConnection()) {

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT lozinka FROM registrovani WHERE email = ?")) {

                ps.setString(1, student.getEmail());

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return null; 
                    }

                    String hashIzBaze = rs.getString("lozinka");
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    if (!encoder.matches(student.getLozinka(), hashIzBaze)) {
                        return null; 
                    }
                }
            }

            // Ako je dobra lozinka dohvatam podatke o studentu 
            try (PreparedStatement ps2 = conn.prepareStatement(
                    "SELECT * FROM studenti WHERE email = ?")) {

                ps2.setString(1, student.getEmail());

                try (ResultSet rs2 = ps2.executeQuery()) {
                    if (rs2.next()) {
                        Student s = new Student();
                        s.setId(rs2.getLong("id"));
                        s.setEmail(student.getEmail());
                        s.setIme(rs2.getString("ime"));
                        s.setPrezime(rs2.getString("prezime"));
                        s.setGodinaUpisa(rs2.getInt("godina_upisa"));
                        s.setBrIndeksa(rs2.getInt("br_indeksa"));
                        s.setSmer(rs2.getString("smer"));
                        s.setTip("student");
                        return s;
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
         try (Connection conn = DB.source().getConnection()) {

            try (PreparedStatement ps = conn.prepareStatement(
                    "SELECT lozinka FROM registrovani WHERE email = ?")) {

                ps.setString(1, email);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        return 0; 
                    }

                    String hashIzBaze = rs.getString("lozinka");
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    if (!encoder.matches(staraLozinka, hashIzBaze)) {
                        return 0; 
                    }
                }
            }

            try (PreparedStatement ps2 = conn.prepareStatement(
                    "UPDATE registrovani SET lozinka = ? WHERE email= ?")) {

                    ps2.setString(1, hesiranaNovaLozinka);
                    ps2.setString(2, email);
                    return ps2.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
        
    }


    @Override
    public String dohvatanjeMejlaStudenta(Long idStudenta) {
        try (Connection conn = DB.source().getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "SELECT email FROM studenti WHERE id = ?"
             )) {

            stmt.setLong(1, idStudenta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("email");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public List<Student> dohvatanjeStudenataKojiPratePredmet(Long idPredmet) {
        List<Student> studenti = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT s.* FROM student_predmet sp \n" +
                "JOIN studenti s ON s.id = sp.idStudent\n" + 
                "WHERE sp.idPredmet = ?"
            )) {

            ps.setLong(1, idPredmet);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setIme(rs.getString("ime"));
                s.setPrezime(rs.getString("prezime"));
                s.setId(rs.getLong("id"));
                s.setEmail(rs.getString("email"));
                s.setGodinaUpisa(rs.getInt("godina_upisa"));
                s.setBrIndeksa(rs.getInt("br_indeksa"));
                studenti.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studenti;
    }


    @Override
    public List<Student> dohvatanjeRegistrovanihStudenata() {
        List<Student> studenti = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT s.* FROM studentit s \n" +
                "JOIN registrovani r ON s.email = r.email" 
            )) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setIme(rs.getString("ime"));
                s.setPrezime(rs.getString("prezime"));
                s.setId(rs.getLong("id"));
                s.setEmail(rs.getString("email"));
                s.setGodinaUpisa(rs.getInt("godina_upisa"));
                s.setBrIndeksa(rs.getInt("br_indeksa"));
                studenti.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studenti;
    }

    
}
