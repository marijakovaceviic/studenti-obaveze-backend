package com.example.backend.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.example.backend.modeli.DemonstartoriForma;
import com.example.backend.modeli.DemonstratoriPrijava;
import com.example.backend.modeli.Predmet;
import com.example.backend.modeli.PredmetPrijavljeniDTO;
import com.example.backend.modeli.Student;

@Repository
public class DemonstratoriRepo implements DemonstartoriRepoInterface {

    @Override
    public int cuvanjeNoveForme(DemonstartoriForma forma) {
        try (Connection conn = DB.source().getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO demonstratori_forme \n" +
                    "(idNastavnik, pocetak, kraj, konkurs) \n" +
                    "VALUES (?, ?, ?, ?)"
                )) {

            stmt.setLong(1, forma.getIdNastavnik());
            stmt.setTimestamp(2, java.sql.Timestamp.valueOf(forma.getPocetak()));
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(forma.getKraj()));
            stmt.setString(4, forma.getKonkurs()); 

            return stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean daLiJeZaduzenZaDemonstratore(Long idNastavnika) {

        try (Connection conn = DB.source().getConnection();
                PreparedStatement stmt = conn.prepareStatement("SELECT zaduzen_demo FROM nastavnici WHERE id = ?")) {

            stmt.setLong(1, idNastavnika);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {
                    return rs.getBoolean("zaduzen_demo");
                } else {
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public DemonstartoriForma dohvatiAktivnuFormu() {
        try (Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT * FROM demonstratori_forme \n" +
                    "WHERE pocetak <= NOW() AND kraj >= NOW() \n" +
                    "ORDER BY pocetak DESC LIMIT 1")) {

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                DemonstartoriForma f = new DemonstartoriForma();
                f.setId(rs.getLong("id"));
                f.setIdNastavnik(rs.getLong("idNastavnik"));
                f.setPocetak(rs.getTimestamp("pocetak").toLocalDateTime());
                f.setKraj(rs.getTimestamp("kraj").toLocalDateTime());
                f.setKonkurs(rs.getString("konkurs"));
                return f;
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public int novaPrijava(DemonstratoriPrijava prijava) {
        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO demonstratori_prijave (idStudent, idForma, idPredmet)\n" + 
                "SELECT (?, ?, ?) WHERE NOT EXISTS (\n" +
                " SELECT 1 FROM demonstratori_prijave WHERE idStudent = ? AND idForma = ? AND idPredmet = ?\n" +
                ")"
            )) {
            
            int brDodatih = 0;    
            for (Long predmet: prijava.getPredmeti()){
                ps.setLong(1, prijava.getIdStudent());
                ps.setLong(2, prijava.getIdForma());
                ps.setLong(3, predmet);

                ps.setLong(4, prijava.getIdStudent());
                ps.setLong(5, prijava.getIdForma());
                ps.setLong(6, predmet);
                brDodatih += ps.executeUpdate();
            }
            
            return brDodatih;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public String putanjaFajlaKonkursa(Long id) {
        try (Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                    "SELECT konkurs FROM demonstratori_forme WHERE id = ? \n")) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                 return rs.getString("konkurs");
            }

            return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Predmet> prijavljeniPredmetiZaStudenta(Long studentId, Long formaId) {
        List<Predmet> predmeti = new ArrayList<>();

        String sql = "SELECT p.* FROM demonstratori_prijave dp\n" +
                    "JOIN predmeti p ON dp.idPredmet = p.id\n" +
                    "WHERE dp.idStudent = ? AND dp.idForma = ?";

        try (Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, studentId);
            stmt.setLong(2, formaId);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Predmet p = new Predmet();
                p.setId(rs.getLong("id"));
                p.setNaziv(rs.getString("naziv"));
                p.setSifra(rs.getString("sifra"));
                p.setGodina(rs.getInt("godina"));
                predmeti.add(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return predmeti;
        }

        return predmeti;
    }

    @Override
    public List<Student> prijavljeniStudentiZaPredmetPoslednjaForma(Long idPredmet) {
        List<Student> studenti = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT s.*\n" +
                "FROM studenti s JOIN demonstratori_prijave dp ON s.id = dp.idStudent\n" +
                "WHERE dp.idForma = (SELECT MAX(id) FROM demonstratori_forme) AND dp.idPredmet = ?\n" +
                "ORDER BY s.godina_upisa, s.br_indeksa"
            )) {

            ps.setLong(1, idPredmet);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Student s = new Student();
                    s.setId(rs.getLong("id"));
                    s.setIme(rs.getString("ime"));
                    s.setPrezime(rs.getString("prezime"));
                    s.setEmail(rs.getString("email"));
                    s.setBrIndeksa(rs.getInt("br_indeksa"));
                    s.setGodinaUpisa(rs.getInt("godina_upisa"));
                    studenti.add(s);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studenti;
    }

    @Override
    public List<PredmetPrijavljeniDTO> prijavljeniStudentiZaPoslednjuFormu() {
        Map<Long, PredmetPrijavljeniDTO> mapa = new HashMap<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT p.id AS idPredmet, p.naziv, p.sifra, s.id AS idStudent, s.ime, s.prezime, s.br_indeksa, s.godina_upisa, s.email\n" +
                "FROM demonstratori_prijave dp\n" +
                "JOIN predmeti p ON dp.idPredmet = p.id\n" +
                "JOIN studenti s ON dp.idStudent = s.id\n" +
                "WHERE dp.idForma = (SELECT MAX(id) FROM demonstratori_forme)\n" +
                "ORDER BY p.id, s.prezime, s.ime"
            );
            ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Long idPredmet = rs.getLong("idPredmet");
                PredmetPrijavljeniDTO dto = mapa.get(idPredmet);
                if (dto == null){
                    dto = new PredmetPrijavljeniDTO();
                    dto.setIdPredmet(idPredmet);
                    dto.setNazivPredmeta(rs.getString("naziv"));
                    dto.setSifraPredmeta(rs.getString("sifra"));
                    dto.setStudenti(new ArrayList<>());
                    mapa.put(idPredmet, dto);
                }
                Student student = new Student();
                student.setId(rs.getLong("idStudent"));
                student.setIme(rs.getString("ime"));
                student.setPrezime(rs.getString("prezime"));
                student.setGodinaUpisa(rs.getInt("godina_upisa"));
                student.setBrIndeksa(rs.getInt("br_indeksa"));
                student.setEmail(rs.getString("email"));
                dto.getStudenti().add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(mapa.values());
    }

}
