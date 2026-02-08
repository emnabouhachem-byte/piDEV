package edu.project.services;

import edu.project.entities.Participation;
import edu.project.interfaces.IParticipationService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParticipationService implements IParticipationService {

    private Connection connection;

    public ParticipationService() {
        try {
            String url = "jdbc:mysql://localhost:3306/pidev";
            String login = "root";
            String pwd = "";
            connection = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connexion ParticipationService établie !");
        } catch (SQLException e) {
            System.err.println("Erreur de connexion ParticipationService : " + e.getMessage());
        }
    }

    @Override
    public void addEntity(Participation participation) {
        String query = "INSERT INTO participation (idEvenement, idEmploye, statut, role, present) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, participation.getIdEvenement());
            pst.setInt(2, participation.getIdEmploye());
            pst.setString(3, participation.getStatut());
            pst.setString(4, participation.getRole());
            pst.setBoolean(5, participation.isPresent());

            pst.executeUpdate();
            System.out.println("Participation ajoutée avec succès !");
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout de la participation : " + e.getMessage());
        }
    }

    @Override
    public void deleteEntity(Participation participation) {
        String query = "DELETE FROM participation WHERE idParticipation = ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, participation.getIdParticipation());
            int rows = pst.executeUpdate();

            if (rows > 0) {
                System.out.println("Participation supprimée avec succès !");
            } else {
                System.out.println("Aucune participation trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression de la participation : " + e.getMessage());
        }
    }

    @Override
    public void updateEntity(int id, Participation participation) {
        String query = "UPDATE participation SET statut = ?, role = ?, present = ? WHERE idParticipation = ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, participation.getStatut());
            pst.setString(2, participation.getRole());
            pst.setBoolean(3, participation.isPresent());
            pst.setInt(4, id);

            int rows = pst.executeUpdate();

            if (rows > 0) {
                System.out.println("Participation modifiée avec succès !");
            } else {
                System.out.println("Aucune participation trouvée avec cet ID.");
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification de la participation : " + e.getMessage());
        }
    }

    @Override
    public List<Participation> getData() {
        List<Participation> participations = new ArrayList<>();
        String query = "SELECT * FROM participation ORDER BY dateInscription DESC";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                participations.add(extractParticipationFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des participations : " + e.getMessage());
        }

        return participations;
    }

    @Override
    public Participation getById(int id) {
        String query = "SELECT * FROM participation WHERE idParticipation = ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return extractParticipationFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la participation : " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Participation> getByEvenement(int idEvenement) {
        List<Participation> participations = new ArrayList<>();
        String query = "SELECT * FROM participation WHERE idEvenement = ? ORDER BY dateInscription DESC";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, idEvenement);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                participations.add(extractParticipationFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des participations : " + e.getMessage());
        }

        return participations;
    }

    @Override
    public List<Participation> getByEmploye(int idEmploye) {
        List<Participation> participations = new ArrayList<>();
        String query = "SELECT * FROM participation WHERE idEmploye = ? ORDER BY dateInscription DESC";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, idEmploye);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                participations.add(extractParticipationFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des participations : " + e.getMessage());
        }

        return participations;
    }

    @Override
    public boolean isAlreadyParticipating(int idEvenement, int idEmploye) {
        String query = "SELECT COUNT(*) FROM participation WHERE idEvenement = ? AND idEmploye = ?";

        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setInt(1, idEvenement);
            pst.setInt(2, idEmploye);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification de la participation : " + e.getMessage());
        }

        return false;
    }

    private Participation extractParticipationFromResultSet(ResultSet rs) throws SQLException {
        Participation p = new Participation();
        p.setIdParticipation(rs.getInt("idParticipation"));
        p.setIdEvenement(rs.getInt("idEvenement"));
        p.setIdEmploye(rs.getInt("idEmploye"));
        p.setDateInscription(rs.getTimestamp("dateInscription").toLocalDateTime());
        p.setStatut(rs.getString("statut"));
        p.setRole(rs.getString("role"));
        p.setPresent(rs.getBoolean("present"));
        return p;
    }
}