package edu.ProjetPi.services;

import edu.ProjetPi.entities.Evenement;
import edu.ProjetPi.interfaces.IService;
import edu.ProjetPi.tools.MyConnection;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements IService<Evenement> {

    @Override
    public void addEntity(Evenement evenement) {
        String sql = "INSERT INTO evenement (titre, description, dateDebut, dateFin, " +
                "lieu, type, statut, budget, createur_id) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, evenement.getTitre());
            ps.setString(2, evenement.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(evenement.getDateDebut()));
            ps.setTimestamp(4, Timestamp.valueOf(evenement.getDateFin()));
            ps.setString(5, evenement.getLieu());
            ps.setString(6, evenement.getType());
            ps.setString(7, evenement.getStatut());
            ps.setDouble(8, evenement.getBudget());
            ps.setInt(9, evenement.getCreateurId());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                evenement.setIdEvenement(rs.getInt(1));
            }

            System.out.println("Événement ajouté avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'événement: " + e.getMessage());
        }
    }

    @Override
    public void deleteEntity(Evenement evenement) {
        String sql = "DELETE FROM evenement WHERE idEvenement = ?";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, evenement.getIdEvenement());
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Événement supprimé avec succès");
            else
                System.out.println("Événement introuvable");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression: " + e.getMessage());
        }
    }

    @Override
    public void updateEntity(int id, Evenement evenement) {
        String sql = "UPDATE evenement SET titre=?, description=?, dateDebut=?, " +
                "dateFin=?, lieu=?, type=?, statut=?, budget=? WHERE idEvenement=?";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, evenement.getTitre());
            ps.setString(2, evenement.getDescription());
            ps.setTimestamp(3, Timestamp.valueOf(evenement.getDateDebut()));
            ps.setTimestamp(4, Timestamp.valueOf(evenement.getDateFin()));
            ps.setString(5, evenement.getLieu());
            ps.setString(6, evenement.getType());
            ps.setString(7, evenement.getStatut());
            ps.setDouble(8, evenement.getBudget());
            ps.setInt(9, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Événement modifié avec succès");
            else
                System.out.println("Événement introuvable");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification: " + e.getMessage());
        }
    }

    @Override
    public List<Evenement> getData() {
        List<Evenement> evenements = new ArrayList<>();
        String sql = "SELECT * FROM evenement ORDER BY dateDebut DESC";

        try (Connection conn = new MyConnection().getCnx();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Evenement e = new Evenement();
                e.setIdEvenement(rs.getInt("idEvenement"));
                e.setTitre(rs.getString("titre"));
                e.setDescription(rs.getString("description"));

                Timestamp dateDebut = rs.getTimestamp("dateDebut");
                if (dateDebut != null)
                    e.setDateDebut(dateDebut.toLocalDateTime());

                Timestamp dateFin = rs.getTimestamp("dateFin");
                if (dateFin != null)
                    e.setDateFin(dateFin.toLocalDateTime());

                e.setLieu(rs.getString("lieu"));
                e.setType(rs.getString("type"));
                e.setStatut(rs.getString("statut"));
                e.setBudget(rs.getDouble("budget"));
                e.setCreateurId(rs.getInt("createur_id"));

                Timestamp dateCreation = rs.getTimestamp("dateCreation");
                if (dateCreation != null)
                    e.setDateCreation(dateCreation.toLocalDateTime());

                evenements.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des événements: " + e.getMessage());
        }

        return evenements;
    }

    // Méthode pour obtenir un événement par ID
    public Evenement getById(int id) {
        String sql = "SELECT * FROM evenement WHERE idEvenement = ?";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Evenement e = new Evenement();
                e.setIdEvenement(rs.getInt("idEvenement"));
                e.setTitre(rs.getString("titre"));
                e.setDescription(rs.getString("description"));

                Timestamp dateDebut = rs.getTimestamp("dateDebut");
                if (dateDebut != null)
                    e.setDateDebut(dateDebut.toLocalDateTime());

                Timestamp dateFin = rs.getTimestamp("dateFin");
                if (dateFin != null)
                    e.setDateFin(dateFin.toLocalDateTime());

                e.setLieu(rs.getString("lieu"));
                e.setType(rs.getString("type"));
                e.setStatut(rs.getString("statut"));
                e.setBudget(rs.getDouble("budget"));
                e.setCreateurId(rs.getInt("createur_id"));

                return e;
            }

        } catch (SQLException e) {
            System.out.println("Erreur: " + e.getMessage());
        }

        return null;
    }

    // Méthode pour chercher par titre
    public List<Evenement> searchByTitre(String titre) {
        List<Evenement> evenements = new ArrayList<>();
        String sql = "SELECT * FROM evenement WHERE titre LIKE ? ORDER BY dateDebut DESC";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + titre + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Evenement e = new Evenement();
                e.setIdEvenement(rs.getInt("idEvenement"));
                e.setTitre(rs.getString("titre"));
                e.setDescription(rs.getString("description"));

                Timestamp dateDebut = rs.getTimestamp("dateDebut");
                if (dateDebut != null)
                    e.setDateDebut(dateDebut.toLocalDateTime());

                Timestamp dateFin = rs.getTimestamp("dateFin");
                if (dateFin != null)
                    e.setDateFin(dateFin.toLocalDateTime());

                e.setLieu(rs.getString("lieu"));
                e.setType(rs.getString("type"));
                e.setStatut(rs.getString("statut"));
                e.setBudget(rs.getDouble("budget"));
                e.setCreateurId(rs.getInt("createur_id"));

                evenements.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche: " + e.getMessage());
        }

        return evenements;
    }
}