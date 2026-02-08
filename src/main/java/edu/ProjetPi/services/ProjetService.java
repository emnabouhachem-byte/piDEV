package edu.ProjetPi.services;

import edu.ProjetPi.entities.Projet;
import edu.ProjetPi.entities.Equipe;
import edu.ProjetPi.interfaces.IService;
import edu.ProjetPi.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjetService implements IService<Projet> {

    // ================= ADD =================
    @Override
    public void addEntity(Projet projet) throws SQLException {
        String sql = "INSERT INTO projet (titre, description, dateDebut, dateFin, budget, id_equipe) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = new MyConnection().getCnx().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, projet.getTitre());
            ps.setString(2, projet.getDescription());
            ps.setDate(3, Date.valueOf(projet.getDateDebut()));
            ps.setDate(4, Date.valueOf(projet.getDateFin()));
            ps.setDouble(5, projet.getBudget());
            ps.setInt(6, projet.getId_equipe()); // 🔹 ربط الفريق مباشرة

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                projet.setId(rs.getInt(1));
            }

            System.out.println("Projet ajouté avec succès ");
        }
    }

    // ================= DELETE =================
    @Override
    public void deleteEntity(Projet projet) throws SQLException {
        String sql = "DELETE FROM projet WHERE id = ?";
        try (PreparedStatement ps = new MyConnection().getCnx().prepareStatement(sql)) {
            ps.setInt(1, projet.getId());
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Projet supprimé ");
            else
                System.out.println("Projet introuvable ");
        }
    }

    // ================= UPDATE =================
    @Override
    public void updateEntity(int id, Projet projet) throws SQLException {
        String sql = "UPDATE projet SET titre=?, description=?, dateDebut=?, dateFin=?, budget=?, id_equipe=? WHERE id=?";
        try (PreparedStatement ps = new MyConnection().getCnx().prepareStatement(sql)) {
            ps.setString(1, projet.getTitre());
            ps.setString(2, projet.getDescription());
            ps.setDate(3, Date.valueOf(projet.getDateDebut()));
            ps.setDate(4, Date.valueOf(projet.getDateFin()));
            ps.setDouble(5, projet.getBudget());
            ps.setInt(6, projet.getId_equipe());
            ps.setInt(7, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Projet modifié ");
            else
                System.out.println("Projet introuvable ");
        }
    }

    // ================= GET ALL =================
    @Override
    public List<Projet> getData() {
        List<Projet> projets = new ArrayList<>();
        String sql = "SELECT p.*, e.nom as equipeNom, e.description as equipeDesc, e.dateCreation, e.nbr_membre, e.budget as equipeBudget " +
                "FROM projet p LEFT JOIN equipe e ON p.id_equipe = e.id";

        try (Statement st = new MyConnection().getCnx().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Projet p = new Projet();
                p.setId(rs.getInt("id"));
                p.setTitre(rs.getString("titre"));
                p.setDescription(rs.getString("description"));
                p.setDateDebut(rs.getDate("dateDebut").toLocalDate());
                p.setDateFin(rs.getDate("dateFin").toLocalDate());
                p.setBudget(rs.getDouble("budget"));
                p.setId_equipe(rs.getInt("id_equipe"));

                // جلب بيانات الفريق
                Equipe e = new Equipe();
                e.setId(rs.getInt("id_equipe"));
                e.setNom(rs.getString("equipeNom"));
                e.setDescription(rs.getString("equipeDesc"));
                if (rs.getDate("dateCreation") != null)
                    e.setDateCreation(rs.getDate("dateCreation").toLocalDate());
                e.setNbr_membre(rs.getInt("nbr_membre"));
                e.setBudget(rs.getDouble("equipeBudget"));

                List<Equipe> equipes = new ArrayList<>();
                equipes.add(e);
                p.setEquipes(equipes);

                projets.add(p);
            }

        } catch (SQLException ex) {
            System.out.println("Erreur getData Projet: " + ex.getMessage());
            ex.printStackTrace();
        }

        return projets;
    }
}

