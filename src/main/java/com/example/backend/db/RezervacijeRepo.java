package com.example.backend.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.backend.modeli.Laboratorija;
import com.example.backend.modeli.NedeljneRezervacijeDTO;
import com.example.backend.modeli.Rezervacija;

@Repository
public class RezervacijeRepo implements RezervacijeRepoInterface {

    @Override
    public int novaRezervacija(Rezervacija rezervacija) {    
        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO rezervacije_laboratorija (idLaboratorija, nazivObaveze, idNastavnik, datum, vremeOd, vremeDo, akronim) VALUES (?, ?, ?, ?, ?, ?, ?)"
            )) {
            
            ps.setLong(1, rezervacija.getIdLaboratorija());
            ps.setString(2, rezervacija.getNazivObaveze());
            ps.setLong(3, rezervacija.getIdNastavnik());
            ps.setDate(4, Date.valueOf(rezervacija.getDatum()));
            ps.setTime(5, Time.valueOf(rezervacija.getVremeOd()));
            ps.setTime(6, Time.valueOf(rezervacija.getVremeDo()));
            ps.setString(7, rezervacija.getAkronim());
            
            return ps.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public List<Laboratorija> dohvatanjeSlobodnihLaboratorija(Rezervacija rezervacija) {
        List<Laboratorija> slobodne = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM laboratorije l " +
                "WHERE l.id NOT IN (" +
                "    SELECT r.idLaboratorija FROM rezervacije_laboratorija r " +
                "    WHERE r.datum = ? AND NOT (r.vremeDo <= ? OR r.vremeOd >= ?)" +
                ")"
            )) {

            ps.setDate(1, Date.valueOf(rezervacija.getDatum()));
            ps.setTime(2, Time.valueOf(rezervacija.getVremeOd()));
            ps.setTime(3, Time.valueOf(rezervacija.getVremeDo()));

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Laboratorija lab = new Laboratorija();
                lab.setId(rs.getLong("id"));
                lab.setNaziv(rs.getString("naziv"));
                lab.setKapacitet(rs.getInt("kapacitet"));
                slobodne.add(lab);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return slobodne;
    }

    @Override
    public List<Rezervacija> dohvatanjeNedeljnihRezervacijaSale(NedeljneRezervacijeDTO pregled) {
        List<Rezervacija> rezervacije = new ArrayList<>();
    
        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT r.*, l.naziv AS naziv, n.ime AS ime, n.prezime AS prezime \n" +
                "FROM rezervacije_laboratorija r JOIN laboratorije l ON (r.idLaboratorija = l.id) \n" +
                "JOIN nastavnici n ON (r.idNastavnik = n.id)\n" +
                "WHERE r.idLaboratorija = ? AND r.datum BETWEEN ? AND ? \n" +
                "ORDER BY r.datum, r.vremeOd"
            )) {
            
            ps.setLong(1, pregled.getIdLaboratorija());
            ps.setDate(2, java.sql.Date.valueOf(pregled.getPocetak())); 
            ps.setDate(3, java.sql.Date.valueOf(pregled.getKraj()));
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Rezervacija r = new Rezervacija();
                r.setIdLaboratorija(rs.getLong("idLaboratorija"));
                r.setNazivLaboratorije(rs.getString("naziv"));
                r.setNazivObaveze(rs.getString("nazivObaveze"));
                r.setImeNastavnika(rs.getString("ime"));
                r.setDatum(rs.getDate("datum").toLocalDate());
                r.setVremeOd(rs.getTime("vremeOd").toLocalTime());
                r.setVremeDo(rs.getTime("vremeDo").toLocalTime());
                r.setAkronim(rs.getString("akronim"));
                r.setPrezimeNastavnika(rs.getString("prezime"));
                
                rezervacije.add(r);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        
        return rezervacije;  
    }

    @Override
    public List<Laboratorija> dohvatanejSvihLaboratorija() {
        List<Laboratorija> laboratorije = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM laboratorije " 
            )) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Laboratorija l = new Laboratorija(
                    rs.getLong("id"), 
                    rs.getString("naziv"), 
                    rs.getInt("kapacitet"));
                laboratorije.add(l);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return laboratorije;
    }

}
