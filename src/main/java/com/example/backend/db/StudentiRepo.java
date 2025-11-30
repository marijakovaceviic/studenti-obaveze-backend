package com.example.backend.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.backend.modeli.Student;

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
                "SELECT * FROM registrovani WHERE email = ? AND lozinka = ?"
            )) {
                ps.setString(1, student.getEmail());
                ps.setString(2, student.getLozinka());
                ResultSet rs = ps.executeQuery();

                if (!rs.next()) {
                    return null;
                }
            }

            //Ako je student registrovan, dohvatam ostale podatke o njemu
            try (PreparedStatement ps2 = conn.prepareStatement(
                "SELECT * FROM studenti WHERE email = ?"
            )) {
                ps2.setString(1, student.getEmail());
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    Student s = new Student();
                    s.setEmail(student.getEmail());
                    s.setIme(rs2.getString("ime"));
                    s.setPrezime(rs2.getString("prezime"));
                    s.setGodinaUpisa(rs2.getInt("godina_upisa"));
                    s.setBrIndeksa(rs2.getInt("br_indeksa"));
                    s.setSmer(rs2.getString("smer"));
                    s.setLozinka(student.getLozinka());
                    s.setId(rs2.getLong("id"));
                    return s;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; 
    }

    @Override
    public int promenaLozinke(Student student) { 
        try (Connection conn = DB.source().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
        "update registrovani set lozinka = ? where email= ?")) {
            
            stmt.setString(1, student.getLozinka());
            stmt.setString(2, student.getEmail());
    
            return stmt.executeUpdate();

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

    
}
