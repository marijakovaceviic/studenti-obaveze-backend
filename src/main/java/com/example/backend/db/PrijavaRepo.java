package com.example.backend.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.backend.modeli.Obaveza;
import com.example.backend.modeli.Student;

@Repository
public class PrijavaRepo implements PrijavaRepoInterface{

    @Override
    public int novaPrijava(Long idStudent, Long idObaveza) {
        //provera da li se student vec prijavio
        try (Connection conn = DB.source().getConnection();
            PreparedStatement psProvera = conn.prepareStatement(
                "SELECT 1 FROM prijave WHERE idStudent = ? AND idObaveza = ?"
            )) {

            psProvera.setLong(1, idStudent);
            psProvera.setLong(2, idObaveza);

            ResultSet rs = psProvera.executeQuery();
            if (rs.next()) {
                return -1; 
            }

            try (PreparedStatement psInsert = conn.prepareStatement(
                "INSERT INTO prijave (idStudent, idObaveza) VALUES (?, ?)"
            )) {
                psInsert.setLong(1, idStudent);
                psInsert.setLong(2, idObaveza);
                return psInsert.executeUpdate(); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return 0; 
        }
    }

    @Override
    public int daLiJePrijavljen(Long idStudent, Long idObaveza) {

        try(Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT 1 FROM prijave WHERE idStudent = ? AND idObaveza = ?"
            )){

            ps.setLong(1, idStudent);
            ps.setLong(2, idObaveza);

            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                return 1;
            } 

        } catch (SQLException e){
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public List<Student> dohvatanjeSvihPrijavaZaObavezu(Long idObaveza) {
        List<Student> lista = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT s.* FROM prijave p \n" +
                "JOIN studenti s ON s.id = p.idStudent\n" + 
                "WHERE p.idObaveza = ? ORDER BY s.godina_upisa, s.br_indeksa"
            )) {

            ps.setLong(1, idObaveza);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student s = new Student();
                s.setIme(rs.getString("ime"));
                s.setPrezime(rs.getString("prezime"));
                s.setId(rs.getLong("id"));
                s.setEmail(rs.getString("email"));
                s.setGodinaUpisa(rs.getInt("godina_upisa"));
                s.setBrIndeksa(rs.getInt("br_indeksa"));
                lista.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<Obaveza> dohvatanjePrijavaZaStudenta(Long idStudenta, String tip) {
        List<Obaveza> obaveze = new ArrayList<>();

        LocalDateTime danasnjiDatum = LocalDateTime.now();
        int godina = danasnjiDatum.getMonthValue() >= 10 ? danasnjiDatum.getYear() : danasnjiDatum.getYear() - 1;

        LocalDateTime pocetakSkolskeGodine = LocalDateTime.of(godina, 10, 1, 0, 0);

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT o.*, pr.naziv AS nazivPredmeta FROM obaveze o JOIN prijave p ON (o.id = p.idObaveza)\n" + 
                "JOIN predmeti pr ON (o.predmet = pr.id) WHERE p.idStudent = ? AND o.tip = ? AND o.pocetak >= ?\n"+
                "ORDER BY o.kraj DESC"
            )) {
            
            ps.setLong(1, idStudenta);
            ps.setString(2, tip);
            ps.setObject(3, pocetakSkolskeGodine);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Obaveza o = new Obaveza(
                        rs.getString("tip"),
                        rs.getString("naziv"),
                        rs.getLong("predmet"),
                        rs.getString("opis"),
                        rs.getObject("pocetak", LocalDateTime.class),
                        rs.getObject("kraj", LocalDateTime.class)
                    );
                    o.setId(rs.getLong("id"));
                    o.setNazivPredmeta(rs.getString("nazivPredmeta"));
                    obaveze.add(o);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obaveze;
    }

    @Override
    public int brojPrijavljenih(Long idObaveza) {
        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) AS broj FROM prijave WHERE idObaveza = ?"
            )) {

            ps.setLong(1, idObaveza);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("broj");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int odjava(Long idStudent, Long idObaveza) {
        try (Connection conn = DB.source().getConnection();
         PreparedStatement ps = conn.prepareStatement(
             "DELETE FROM prijave WHERE idStudent = ? AND idObaveza = ?"
         )) {

            ps.setLong(1, idStudent);
            ps.setLong(2, idObaveza);

            return ps.executeUpdate(); 

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
