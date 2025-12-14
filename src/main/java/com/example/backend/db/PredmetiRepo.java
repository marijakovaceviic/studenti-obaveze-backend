package com.example.backend.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.backend.modeli.Predmet;

public class PredmetiRepo implements PredmetiRepoInterface{

    @Override
    public int dodavanjePredmeta(Predmet predmet) {
        try (Connection conn = DB.source().getConnection()) {
            // Provera da li predmet vec postoji
            try (PreparedStatement ps1 = conn.prepareStatement(
            "SELECT 1 FROM predmeti WHERE sifra = ?")) {
                ps1.setString(1, predmet.getSifra());
                ResultSet rs = ps1.executeQuery();
                if (rs.next()) {
                    return 0;
                }
            }
            // Dodavanje predmeta
            try (PreparedStatement ps2 = conn.prepareStatement(
            "INSERT INTO predmeti(naziv, sifra, godina, odsek) VALUES(?,?,?,?)")) {
                ps2.setString(1, predmet.getNaziv());
                ps2.setString(2, predmet.getSifra());
                ps2.setInt(3, predmet.getGodina());
                ps2.setString(4, predmet.getOdsek());
                return ps2.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Predmet> dohvatanjeSvihPredmeta() {
        List<Predmet> predmeti = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
        "SELECT * FROM predmeti")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Predmet p = new Predmet(
                        rs.getString("naziv"),
                        rs.getString("sifra"),
                        rs.getString("odsek"),
                        rs.getInt("godina")
                       );
                p.setId(rs.getLong("id"));
                predmeti.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return predmeti;
    }

    @Override
    public List<Predmet> dohvatanjePredmetaZaGodinu(int godina) {
        List<Predmet> predmeti = new ArrayList<>();
        
        try (Connection conn = DB.source().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
        "SELECT * FROM predmeti WHERE godina = ?")) {

            stmt.setInt(1, godina);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Predmet k = new Predmet(
                        rs.getString("naziv"),
                        rs.getString("sifra"),
                        rs.getString("odsek"),
                        rs.getInt("godina")
                       );
                predmeti.add(k);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return predmeti;
    }

    @Override
    public List<Predmet> dohvatanjePredmetaZaGodinuIOdsek(int godina, String odsek) {
        List<Predmet> predmeti = new ArrayList<>();
        
        try (Connection conn = DB.source().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
        "SELECT * FROM predmeti WHERE godina = ? AND odsek = ?")) {

            stmt.setInt(1, godina);
            stmt.setString(2, odsek);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Predmet p = new Predmet(
                        rs.getString("naziv"),
                        rs.getString("sifra"),
                        rs.getString("odsek"),
                        rs.getInt("godina")
                       );
                predmeti.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return predmeti;
    }

    @Override
    public int cuvanjeIzabranihPredmetaZaPracenje(List<Long> izabrani, Long studentId) {
        //prvo brisem sve iz tabele za ovog studenta
        brisanjePredmetaZaStudenta(studentId);

        int brojDodatih = 0;
        try (Connection conn = DB.source().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
        "INSERT INTO student_predmet(idStudent, idPredmet) VALUES(?,?)")) {

            for(Long idPredmet: izabrani){
                stmt.setLong(1, studentId);
                stmt.setLong(2, idPredmet);
                brojDodatih += stmt.executeUpdate();
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return brojDodatih;
    }
    
    private void brisanjePredmetaZaStudenta(Long id){
        
        try (Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM student_predmet WHERE idStudent = ?"
            )) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Predmet> dohvatanjeIzabranihPredmetaZaStudenta(Long studentId) {
        List<Predmet> lista = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT p.* FROM predmeti p " +
                "JOIN student_predmet sp ON p.id = sp.idPredmet " +
                "WHERE sp.idStudent = ?"
            )) {

            ps.setLong(1, studentId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Predmet p = new Predmet();
                p.setId(rs.getLong("id"));
                p.setNaziv(rs.getString("naziv"));
                p.setSifra(rs.getString("sifra"));
                p.setGodina(rs.getInt("godina"));
                p.setOdsek(rs.getString("odsek"));

                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<Predmet> dohvatanjePredmetaZaNastavnika(Long idNastavnik) {
        List<Predmet> lista = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT p.* FROM predmeti p " +
                "JOIN nastavnik_predmet np ON p.id = np.idPredmet " +
                "WHERE np.idNastavnik = ?"
            )) {

            ps.setLong(1, idNastavnik);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Predmet p = new Predmet();
                p.setId(rs.getLong("id"));
                p.setNaziv(rs.getString("naziv"));
                p.setSifra(rs.getString("sifra"));
                p.setGodina(rs.getInt("godina"));
                p.setOdsek(rs.getString("odsek"));

                lista.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    @Override
    public List<Integer> dohvatanjeGodinaKojeStudentPrati(Long idStudent) {
        List<Integer> godine = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT DISTINCT p.godina \n" +
                "FROM student_predmet sp \n" +
                "JOIN predmeti p ON sp.idPredmet = p.id \n" +
                "WHERE sp.idStudent = ?"
            )) {

            ps.setLong(1, idStudent);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                godine.add(rs.getInt("godina"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return godine;
    }

    @Override
    public List<Predmet> dohvatanjePredmetaSaAktivnimObavezamaZaStudenta(Long idStudent, int godina) {
        List<Predmet> predmeti = new ArrayList<>();

        String sql = "SELECT DISTINCT p.* " +
                    "FROM predmeti p " +
                    "JOIN student_predmet sp ON p.id = sp.idPredmet " +
                    "JOIN obaveze o ON o.predmet = p.id " +
                    "WHERE sp.idStudent = ? AND p.godina = ? " +
                    "AND o.pocetak <= NOW() AND o.kraj >= NOW()";

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, idStudent);
            ps.setInt(2, godina);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Predmet p = new Predmet(
                    rs.getString("naziv"),
                    rs.getString("sifra"),
                    rs.getString("odsek"),
                    rs.getInt("godina")
                );
                p.setId(rs.getLong("id"));
                predmeti.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return predmeti;
    }

    @Override
    public Predmet dohvatanjePredemtaPoId(Long id) {        
        try (Connection conn = DB.source().getConnection();
        PreparedStatement stmt = conn.prepareStatement(
        "SELECT * FROM predmeti WHERE id = ?")) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Predmet p = new Predmet(
                        rs.getString("naziv"),
                        rs.getString("sifra"),
                        rs.getString("odsek"),
                        rs.getInt("godina")
                       );
                return p;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Predmet> dohvatanjePredmetaSaAktivnimObavezamaZaGodinu(int godina) {
        List<Predmet> predmeti = new ArrayList<>();

        String sql = "SELECT DISTINCT p.* " +
                    "FROM predmeti p " +
                    "JOIN obaveze o ON o.predmet = p.id " +
                    "WHERE p.godina = ? " +
                    "AND o.pocetak <= NOW() AND o.kraj >= NOW()";

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, godina);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Predmet p = new Predmet(
                    rs.getString("naziv"),
                    rs.getString("sifra"),
                    rs.getString("odsek"),
                    rs.getInt("godina")
                );
                p.setId(rs.getLong("id"));
                predmeti.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return predmeti;
    }

}
