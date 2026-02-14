package edu.project.services;

import edu.project.entities.Candidature;
import edu.project.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.*;
import java.io.File;
import java.io.IOException;

public class CandidatureService {

    private Connection cnx;

    public CandidatureService() {
        cnx = MyConnection.getConnection();
    }

    public int ajouter(Candidature c) {
        String sql = "INSERT INTO candidature (date_depot, statut, disponibilite, score, cv, offre_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(c.getDateDepot()));
            ps.setString(2, c.getStatut());
            ps.setString(3, c.getDisponibilite());
            ps.setDouble(4, c.getScore());
            ps.setString(5, c.getCv());
            ps.setInt(6, c.getOffreId());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    c.setId(id);
                    return id;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void modifier(Candidature c) {
        String sql = "UPDATE candidature SET date_depot=?, statut=?, disponibilite=?, score=?, cv=?, offre_id=? WHERE id=?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(c.getDateDepot()));
            ps.setString(2, c.getStatut());
            ps.setString(3, c.getDisponibilite());
            ps.setDouble(4, c.getScore());
            ps.setString(5, c.getCv());
            ps.setInt(6, c.getOffreId());
            ps.setInt(7, c.getId());

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void supprimer(int id) {
        String sql = "DELETE FROM candidature WHERE id=?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Candidature> afficher() {
        List<Candidature> list = new ArrayList<>();
        String sql = "SELECT * FROM candidature";
        try (Statement st = cnx.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Candidature(
                        rs.getInt("id"),
                        rs.getDate("date_depot").toLocalDate(),
                        rs.getString("statut"),
                        rs.getString("disponibilite"),
                        rs.getDouble("score"),
                        rs.getString("cv"),
                        rs.getInt("offre_id")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Candidature> rechercher(String mot) {
        List<Candidature> list = new ArrayList<>();
        String sql = "SELECT * FROM candidature WHERE statut LIKE ? OR disponibilite LIKE ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setString(1, "%" + mot + "%");
            ps.setString(2, "%" + mot + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new Candidature(
                            rs.getInt("id"),
                            rs.getDate("date_depot").toLocalDate(),
                            rs.getString("statut"),
                            rs.getString("disponibilite"),
                            rs.getDouble("score"),
                            rs.getString("cv"),
                            rs.getInt("offre_id")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Store the provided CV file into the application's uploads/cvs directory.
     * Returns a relative path like "uploads/cvs/1634234234_uuid.pdf" on success,
     * or null on failure.
     */
    public String storeCvFile(File src) {
        if (src == null || !src.exists()) return null;
        try {
            Path uploadsDir = Paths.get("uploads", "cvs");
            Files.createDirectories(uploadsDir);

            String orig = src.getName();
            String ext = "";
            int dot = orig.lastIndexOf('.');
            if (dot >= 0) ext = orig.substring(dot);

            String fileName = System.currentTimeMillis() + "_" + java.util.UUID.randomUUID().toString().substring(0,8) + ext;
            Path dest = uploadsDir.resolve(fileName);

            Files.copy(src.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);

            // return relative path
            Path rel = Paths.get("uploads", "cvs", fileName);
            return rel.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
