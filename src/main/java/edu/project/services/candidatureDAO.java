package edu.project.services;

import edu.project.entities.candidature;
import edu.project.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class candidatureDAO {
    private final MyConnection mc;

    public candidatureDAO(MyConnection mc) {
        this.mc = mc;
    }

    public void create(candidature c) throws SQLException {
        String sql = "INSERT INTO candidature (date_depot, statut, disponibilite, score, cv, offre_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = mc.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, Date.valueOf(c.getDateDepot()));
            ps.setString(2, c.getStatut());
            ps.setString(3, c.getDisponibilite());
            ps.setDouble(4, c.getScore());
            ps.setString(5, c.getCv());
            ps.setInt(6, c.getOffreId());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }
            System.out.println("Candidature créée (id=" + c.getId() + ")");
        }
    }

    public candidature read(int id) throws SQLException {
        String sql = "SELECT * FROM candidature WHERE id = ?";
        try (PreparedStatement ps = mc.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    candidature c = new candidature();
                    c.setId(rs.getInt("id"));
                    c.setDateDepot(rs.getDate("date_depot").toLocalDate());
                    c.setStatut(rs.getString("statut"));
                    c.setDisponibilite(rs.getString("disponibilite"));
                    c.setScore(rs.getDouble("score"));
                    c.setCv(rs.getString("cv"));
                    c.setOffreId(rs.getInt("offre_id"));
                    return c;
                }
            }
        }
        return null;
    }

    public List<candidature> readAll() throws SQLException {
        List<candidature> list = new ArrayList<>();
        String sql = "SELECT * FROM candidature";
        try (Statement st = mc.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                candidature c = new candidature();
                c.setId(rs.getInt("id"));
                c.setDateDepot(rs.getDate("date_depot").toLocalDate());
                c.setStatut(rs.getString("statut"));
                c.setDisponibilite(rs.getString("disponibilite"));
                c.setScore(rs.getDouble("score"));
                c.setCv(rs.getString("cv"));
                c.setOffreId(rs.getInt("offre_id"));
                list.add(c);
            }
        }
        return list;
    }

    public List<candidature> readByOffre(int offreId) throws SQLException {
        List<candidature> list = new ArrayList<>();
        String sql = "SELECT * FROM candidature WHERE offre_id = ?";
        try (PreparedStatement ps = mc.getConnection().prepareStatement(sql)) {
            ps.setInt(1, offreId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    candidature c = new candidature();
                    c.setId(rs.getInt("id"));
                    c.setDateDepot(rs.getDate("date_depot").toLocalDate());
                    c.setStatut(rs.getString("statut"));
                    c.setDisponibilite(rs.getString("disponibilite"));
                    c.setScore(rs.getDouble("score"));
                    c.setCv(rs.getString("cv"));
                    c.setOffreId(rs.getInt("offre_id"));
                    list.add(c);
                }
            }
        }
        return list;
    }

    public void update(candidature c) throws SQLException {
        String sql = "UPDATE candidature SET date_depot=?, statut=?, disponibilite=?, score=?, cv=?, offre_id=? WHERE id=?";
        try (PreparedStatement ps = mc.getConnection().prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(c.getDateDepot()));
            ps.setString(2, c.getStatut());
            ps.setString(3, c.getDisponibilite());
            ps.setDouble(4, c.getScore());
            ps.setString(5, c.getCv());
            ps.setInt(6, c.getOffreId());
            ps.setInt(7, c.getId());
            ps.executeUpdate();
            System.out.println("Candidature mise à jour");
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM candidature WHERE id = ?";
        try (PreparedStatement ps = mc.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Candidature supprimée");
        }
    }
}
