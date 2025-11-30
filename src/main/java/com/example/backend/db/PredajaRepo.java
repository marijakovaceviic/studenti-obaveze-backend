package com.example.backend.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.backend.modeli.Predaja;

public class PredajaRepo implements PredajaRepoInterface{

    @Override
    public int novaPredaja(Long idStudent, Long idObaveze, String putanja) {
        try (Connection conn = DB.source().getConnection();
            PreparedStatement psProvera = conn.prepareStatement(
                "SELECT 1 FROM predaje WHERE idStudent = ? AND idObaveze = ?"
            )) {

            psProvera.setLong(1, idStudent);
            psProvera.setLong(2, idObaveze);

            ResultSet rs = psProvera.executeQuery();
            if (rs.next()) {
                return 0; 
            }

            try (PreparedStatement psInsert = conn.prepareStatement(
                "INSERT INTO predaje (idStudent, idObaveze, putanja) VALUES (?, ?, ?)"
            )) {
                psInsert.setLong(1, idStudent);
                psInsert.setLong(2, idObaveze);
                psInsert.setString(3, putanja);
                return psInsert.executeUpdate(); 
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return -1; 
        }
    }

    @Override
    public List<Predaja> predatiRadoviZaStudenta(Long idStudent) {
        List<Predaja> predato = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT o.naziv AS nazivObaveze, o.kraj AS rok, pr.naziv AS nazivPredmeta, p.*\n"+ 
                "FROM obaveze o JOIN predaje p ON (o.id = p.idObaveze)\n" + 
                "JOIN predmeti pr ON (o.predmet = pr.id) WHERE p.idStudent = ?"
            )) {
            
            ps.setLong(1, idStudent);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Predaja p = new Predaja(
                        rs.getLong("idStudent"),
                        rs.getString("putanja"),
                        rs.getString("nazivPredmeta"),
                        rs.getString("nazivObaveze"),
                        rs.getObject("rok", LocalDateTime.class)
                    );
                    p.setObavezaId(rs.getLong("idObaveze"));
                    predato.add(p);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return predato;
    }

    @Override
    public List<Predaja> predatiRadoviZaObavezu(Long idObaveza) {
        List<Predaja> lista = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT o.naziv AS nazivObaveze, o.kraj AS rok, pr.naziv AS nazivPredmeta, p.*\n"+ 
                "FROM obaveze o JOIN predaje p ON (o.id = p.idObaveze)\n" + 
                "JOIN predmeti pr ON (o.predmet = pr.id) WHERE o.id = ?"
            )) {
            ps.setLong(1, idObaveza);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Predaja p = new Predaja(
                        rs.getLong("idStudent"),
                        rs.getString("putanja"),
                        rs.getString("nazivPredmeta"),
                        rs.getString("nazivObaveze"),
                        rs.getObject("rok", LocalDateTime.class)
                    );
                    lista.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public int brojPredatihRadova(Long idObaveza) {
        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT COUNT(*) AS broj FROM predaje WHERE idObaveze = ?"
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

    
}
