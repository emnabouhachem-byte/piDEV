package edu.project.services;

import edu.project.entities.evenement;
import edu.project.interfaces.IEvenementService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements IEvenementService {

    private Connection connection;

    public EvenementService() {
        try {
            String url = "jdbc:mysql://localhost:3306/pidev";
            String login = "root";
            String pwd = "";
            connection = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion EvenementService établie !");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion EvenementService : " + e.getMessage());
        }
    }

    @Override
    public void addEntity(evenement evenement) {
        String query = "INSERT INTO evenement (titre, description, dateDebut, dateFin, lieu, type, statut, budget, createur_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, evenement.getTitre());
            pst.setString(2, evenement.getDescription());
            pst.setTimestamp(3, Timestamp.valueOf(evenement.getDateDebut()));
            pst.setTimestamp(4, Timestamp.valueOf(evenement.getDateFin()));
            pst.setString(5, evenement.getLieu());
            pst.setString(6, evenement.getType());
            pst.setString(7, evenement.getStatut());
            pst.setDouble(8, evenement.getBudget());
            pst.setInt(9, evenement.getCreateur_id());

            pst.executeUpdate();
            System.out.println("Événement ajouté avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de l'événement : " + e.getMessage());
        }
    }

    @Override
    public void deleteEntity(evenement evenement) {
        String query = "DELETE FROM evenement WHERE idEvenement = ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, evenement.getIdEvenement());
            int rows = pst.executeUpdate();

            if (rows > 0) {
                System.out.println("Événement supprimé avec succès !");
            } else {
                System.out.println("Aucun événement trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de l'événement : " + e.getMessage());
        }
    }

    @Override
    public void updateEntity(int id, evenement evenement) {
        String query = "UPDATE evenement SET titre = ?, description = ?, dateDebut = ?, dateFin = ?, lieu = ?, type = ?, statut = ?, budget = ?, createur_id = ? WHERE idEvenement = ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, evenement.getTitre());
            pst.setString(2, evenement.getDescription());
            pst.setTimestamp(3, Timestamp.valueOf(evenement.getDateDebut()));
            pst.setTimestamp(4, Timestamp.valueOf(evenement.getDateFin()));
            pst.setString(5, evenement.getLieu());
            pst.setString(6, evenement.getType());
            pst.setString(7, evenement.getStatut());
            pst.setDouble(8, evenement.getBudget());
            pst.setInt(9, evenement.getCreateur_id());
            pst.setInt(10, id);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                System.out.println("Événement modifié avec succès !");
            } else {
                System.out.println("Aucun événement trouvé avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de l'événement : " + e.getMessage());
        }
    }

    @Override
    public List<evenement> getData() {
        List<evenement> evenements = new ArrayList<>();
        String query = "SELECT * FROM evenement ORDER BY dateDebut DESC";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                evenements.add(extractEvenementFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des événements : " + e.getMessage());
        }

        return evenements;
    }

    @Override
    public evenement getById(int id) {
        String query = "SELECT * FROM evenement WHERE idEvenement = ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return extractEvenementFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de l'événement : " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<evenement> getByCreateur(int createurId) {
        List<evenement> evenements = new ArrayList<>();
        String query = "SELECT * FROM evenement WHERE createur_id = ? ORDER BY dateDebut DESC";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, createurId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                evenements.add(extractEvenementFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des événements : " + e.getMessage());
        }

        return evenements;
    }

    @Override
    public List<evenement> searchByTitre(String titre) {
        List<evenement> evenements = new ArrayList<>();
        String query = "SELECT * FROM evenement WHERE titre LIKE ? ORDER BY dateDebut DESC";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, "%" + titre + "%");
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                evenements.add(extractEvenementFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche des événements : " + e.getMessage());
        }

        return evenements;
    }

    private evenement extractEvenementFromResultSet(ResultSet rs) throws SQLException {
        evenement e = new evenement();
        e.setIdEvenement(rs.getInt("idEvenement"));
        e.setTitre(rs.getString("titre"));
        e.setDescription(rs.getString("description"));
        e.setDateDebut(rs.getTimestamp("dateDebut").toLocalDateTime());
        e.setDateFin(rs.getTimestamp("dateFin").toLocalDateTime());
        e.setLieu(rs.getString("lieu"));
        e.setType(rs.getString("type"));
        e.setStatut(rs.getString("statut"));
        e.setBudget(rs.getDouble("budget"));
        e.setCreateur_id(rs.getInt("createur_id"));

        Timestamp dateCreation = rs.getTimestamp("dateCreation");
        if (dateCreation != null) {
            e.setDateCreation(dateCreation.toLocalDateTime());
        }

        return e;
    }
}