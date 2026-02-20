package com.example.backend.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.backend.modeli.Admin;

@Repository
public class AdminRepo implements AdminRepoInterface{

    @Override
    public Admin prijava(Admin admin) {
        try (Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM admini WHERE email = ?"
            )) {

            ps.setString(1, admin.getEmail());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String hashIzBaze = rs.getString("lozinka");
                    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                    if (encoder.matches(admin.getLozinka(), hashIzBaze)) {
                        Admin a = new Admin();
                        a.setEmail(rs.getString("email"));
                        a.setTip("admin");
                        return a;
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public long proveraAdmina(String email) {
        try (
            Connection conn = DB.source().getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "SELECT * FROM admini WHERE email = ?")
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
    public int postavljanjePrivremeneLozinke(String email, String hesiranaLozinka) {
        try (Connection conn = DB.source().getConnection();
            PreparedStatement stmt = conn.prepareStatement(
                "UPDATE admini SET lozinka = ? WHERE email = ?")) {

            stmt.setString(1, hesiranaLozinka);
            stmt.setString(2, email);

            return stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
    
}
