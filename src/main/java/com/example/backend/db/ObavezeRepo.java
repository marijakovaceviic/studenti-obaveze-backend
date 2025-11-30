package com.example.backend.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.backend.modeli.Obaveza;

public class ObavezeRepo implements ObavezeRepoInterface{

    @Override
    public Long dodavanjeObaveze(Obaveza obaveza) {

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO obaveze (tip, naziv, predmet, opis, pocetak, kraj) VALUES (?, ?, ?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS
            )) {

            ps.setString(1, obaveza.getTip());
            ps.setString(2, obaveza.getNaziv());
            ps.setLong(3, obaveza.getPredmet());
            ps.setString(4, obaveza.getOpis());
            ps.setObject(5, obaveza.getPocetak());
            ps.setObject(6, obaveza.getKraj());

            if (ps.executeUpdate() == 0){
                return null;
            }

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getLong(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Obaveza> dohvatanjeObavezaZaPredmet(Long idPredmet) {
        List<Obaveza> obaveze = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM obaveze WHERE predmet = ? AND pocetak <= NOW() AND kraj >= NOW()"
            )) {

            ps.setLong(1, idPredmet);

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
                    obaveze.add(o);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obaveze;
    }

    @Override
    public List<Obaveza> dohvatanjeObavezaZaStudenta(Long idStudent) {
        List<Obaveza> obaveze = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT o.* FROM obaveze o JOIN student_predmet sp ON (o.predmet = sp.idPredmet) \n" + 
                "WHERE sp.idStudent = ? AND o.pocetak <= NOW() AND o.kraj >= NOW()"
            )) {

            ps.setLong(1, idStudent);

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
                    obaveze.add(o);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obaveze;
    }

    @Override
    public List<Obaveza> dohvatanjeSvihObaveza() {
        List<Obaveza> obaveze = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM obaveze WHERE pocetak <= NOW() AND kraj >= NOW()"
            )) {

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
                    obaveze.add(o);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obaveze;
    }

    @Override
    public List<Obaveza> dohvatanjeObavezaZaNastavnika(Long idNastavnik) {
        List<Obaveza> obaveze = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT o.* FROM obaveze o JOIN nastavnik_predmet np ON (o.predmet = np.idPredmet) \n" + 
                "WHERE np.idNastavnik = ? AND o.pocetak <= NOW() AND o.kraj >= NOW()"
            )) {

            ps.setLong(1, idNastavnik);

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
                    obaveze.add(o);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obaveze;
    }

    @Override
    public Obaveza dohvatanjeObaveze(Long id) {
        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT o.*, p.naziv as nazivPredmeta, p.sifra as sifra\n" 
                +"FROM obaveze o JOIN predmeti p ON(o.predmet = p.id) WHERE o.id = ?"
            )) {
            
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {

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
                    o.setSifraPredmeta(rs.getString("sifra"));
                    return o;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Obaveza> dohvatanjeIsteklihObavezaZaPredmet(Long idPredmet) {
        List<Obaveza> obaveze = new ArrayList<>();

        LocalDateTime danasnjiDatum = LocalDateTime.now();
        int godina = danasnjiDatum.getMonthValue() >= 10 ? danasnjiDatum.getYear() : danasnjiDatum.getYear() - 1;

        LocalDateTime pocetakSkolskeGodine = LocalDateTime.of(godina, 10, 1, 0, 0);

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM obaveze WHERE predmet = ? AND pocetak >= ? AND kraj < NOW() ORDER BY kraj"
            )) {

            ps.setLong(1, idPredmet);
            ps.setObject(2, pocetakSkolskeGodine);
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
                    obaveze.add(o);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obaveze;
    }

    @Override
    public List<Obaveza> dohvatanjeIsteklihNeobradjenihObaveza() {
        List<Obaveza> obaveze = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM obaveze\n" + 
                "WHERE  poslat_email = false AND kraj < NOW()"
            )) {

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
                    obaveze.add(o);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return obaveze;
    }

    
}
