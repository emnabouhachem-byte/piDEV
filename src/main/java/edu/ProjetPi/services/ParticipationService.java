package edu.ProjetPi.services;

import edu.ProjetPi.entities.Participation;
import edu.ProjetPi.interfaces.IService;
import edu.ProjetPi.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipationService implements IService<Participation> {

    @Override
    public void addEntity(Participation participation) {
        // Vérifiez le nom exact des colonnes dans votre table MySQL
        String sql = "INSERT INTO participation (idEvenement, idEmploye, statut, role) " +
                "VALUES (?, ?, ?, ?)";
        // OU "idEmployee" si c'est le nom exact dans votre table

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, participation.getIdEvenement());
            ps.setInt(2, participation.getIdEmployee()); // Changez si nécessaire
            ps.setString(3, participation.getStatut());
            ps.setString(4, participation.getRole());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                participation.setIdParticipation(rs.getInt(1));
            }

            System.out.println("Participation ajoutée avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la participation: " + e.getMessage());
            // Afficher l'erreur complète pour debug
            e.printStackTrace();
        }
    }

    @Override
    public void deleteEntity(Participation participation) {
        String sql = "DELETE FROM participation WHERE idParticipation = ?";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, participation.getIdParticipation());
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Participation supprimée avec succès");
            else
                System.out.println("Participation introuvable");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression: " + e.getMessage());
        }
    }

    @Override
    public void updateEntity(int id, Participation participation) {
        String sql = "UPDATE participation SET statut=?, role=?, present=? WHERE idParticipation=?";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, participation.getStatut());
            ps.setString(2, participation.getRole());
            ps.setBoolean(3, participation.isPresent());
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Participation modifiée avec succès");
            else
                System.out.println("Participation introuvable");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification: " + e.getMessage());
        }
    }

    @Override
    public List<Participation> getData() {
        List<Participation> participations = new ArrayList<>();
        // Vérifiez le nom exact de la colonne date
        String sql = "SELECT * FROM participation ORDER BY dateInscription DESC";
        // OU "datelnscription" ou autre

        try (Connection conn = new MyConnection().getCnx();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Participation p = new Participation();
                p.setIdParticipation(rs.getInt("idParticipation"));
                p.setIdEvenement(rs.getInt("idEvenement"));
                p.setIdEmployee(rs.getInt("idEmploye")); // Changez selon votre table

                // Vérifiez le nom exact de la colonne date
                Timestamp dateInscription = rs.getTimestamp("dateInscription");
                if (dateInscription != null)
                    p.setDateInscription(dateInscription.toLocalDateTime());

                p.setStatut(rs.getString("statut"));
                p.setRole(rs.getString("role"));
                p.setPresent(rs.getBoolean("present"));

                participations.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des participations: " + e.getMessage());
            e.printStackTrace();
        }

        return participations;
    }

    // Méthode pour obtenir les participants d'un événement
    public List<Participation> getByEvenement(int idEvenement) {
        List<Participation> participations = new ArrayList<>();
        // Joindre avec employe, pas utilisateur
        String sql = "SELECT p.*, e.nom, e.prenom, e.poste " +
                "FROM participation p " +
                "JOIN employe e ON p.idEmploye = e.id " +  // Changez selon votre table
                "WHERE p.idEvenement = ? " +
                "ORDER BY p.dateInscription DESC";  // Changez le nom de la colonne date

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEvenement);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Participation p = new Participation();
                p.setIdParticipation(rs.getInt("idParticipation"));
                p.setIdEvenement(rs.getInt("idEvenement"));
                p.setIdEmployee(rs.getInt("idEmploye"));  // Changez selon votre table

                // Vérifiez le nom exact de la colonne date
                Timestamp dateInscription = rs.getTimestamp("dateInscription");
                if (dateInscription != null)
                    p.setDateInscription(dateInscription.toLocalDateTime());

                p.setStatut(rs.getString("statut"));
                p.setRole(rs.getString("role"));
                p.setPresent(rs.getBoolean("present"));

                participations.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des participants: " + e.getMessage());
            e.printStackTrace();
        }

        return participations;
    }

    // Méthode pour marquer la présence
    public void marquerPresence(int idParticipation, boolean present) {
        String sql = "UPDATE participation SET present = ? WHERE idParticipation = ?";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setBoolean(1, present);
            ps.setInt(2, idParticipation);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Présence mise à jour: " + (present ? "présent" : "absent"));
            }
        } catch (SQLException e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }
}