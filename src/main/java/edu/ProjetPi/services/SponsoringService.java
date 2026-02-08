package edu.ProjetPi.services;

import edu.ProjetPi.entities.Sponsoring;
import edu.ProjetPi.interfaces.IService;
import edu.ProjetPi.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SponsoringService implements IService<Sponsoring> {

    @Override
    public void addEntity(Sponsoring sponsoring) {
        String sql = "INSERT INTO sponsoring (idSponsor, idEvenement, montant, " +
                "date_contribution, type_contribution, details, statut) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, sponsoring.getIdSponsor());
            ps.setInt(2, sponsoring.getIdEvenement());
            ps.setDouble(3, sponsoring.getMontant());

            if (sponsoring.getDateContribution() != null)
                ps.setDate(4, Date.valueOf(sponsoring.getDateContribution()));
            else
                ps.setDate(4, Date.valueOf(java.time.LocalDate.now()));

            ps.setString(5, sponsoring.getTypeContribution());
            ps.setString(6, sponsoring.getDetails());
            ps.setString(7, sponsoring.getStatut());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                sponsoring.setIdSponsoring(rs.getInt(1));
            }

            System.out.println("Sponsoring ajouté avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du sponsoring: " + e.getMessage());
        }
    }

    @Override
    public void deleteEntity(Sponsoring sponsoring) {
        String sql = "DELETE FROM sponsoring WHERE idSponsoring = ?";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sponsoring.getIdSponsoring());
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Sponsoring supprimé avec succès");
            else
                System.out.println("Sponsoring introuvable");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression: " + e.getMessage());
        }
    }

    @Override
    public void updateEntity(int id, Sponsoring sponsoring) {
        String sql = "UPDATE sponsoring SET montant=?, date_contribution=?, " +
                "type_contribution=?, details=?, statut=? WHERE idSponsoring=?";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, sponsoring.getMontant());

            if (sponsoring.getDateContribution() != null)
                ps.setDate(2, Date.valueOf(sponsoring.getDateContribution()));
            else
                ps.setNull(2, Types.DATE);

            ps.setString(3, sponsoring.getTypeContribution());
            ps.setString(4, sponsoring.getDetails());
            ps.setString(5, sponsoring.getStatut());
            ps.setInt(6, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Sponsoring modifié avec succès");
            else
                System.out.println("Sponsoring introuvable");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification: " + e.getMessage());
        }
    }

    @Override
    public List<Sponsoring> getData() {
        List<Sponsoring> sponsorings = new ArrayList<>();
        String sql = "SELECT s.*, sp.nom as sponsor_nom, ev.titre as evenement_titre " +
                "FROM sponsoring s " +
                "LEFT JOIN sponsor sp ON s.idSponsor = sp.idSponsor " +
                "LEFT JOIN evenement ev ON s.idEvenement = ev.idEvenement " +
                "ORDER BY s.date_contribution DESC";

        try (Connection conn = new MyConnection().getCnx();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Sponsoring s = new Sponsoring();
                s.setIdSponsoring(rs.getInt("idSponsoring"));
                s.setIdSponsor(rs.getInt("idSponsor"));
                s.setIdEvenement(rs.getInt("idEvenement"));
                s.setMontant(rs.getDouble("montant"));

                Date dateContribution = rs.getDate("date_contribution");
                if (dateContribution != null)
                    s.setDateContribution(dateContribution.toLocalDate());

                s.setTypeContribution(rs.getString("type_contribution"));
                s.setDetails(rs.getString("details"));
                s.setStatut(rs.getString("statut"));

                sponsorings.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des sponsorings: " + e.getMessage());
        }

        return sponsorings;
    }

    // Méthode pour obtenir les sponsors d'un événement
    public List<Sponsoring> getByEvenement(int idEvenement) {
        List<Sponsoring> sponsorings = new ArrayList<>();
        String sql = "SELECT s.*, sp.nom, sp.type_sponsor, sp.budget_alloue " +
                "FROM sponsoring s " +
                "JOIN sponsor sp ON s.idSponsor = sp.idSponsor " +
                "WHERE s.idEvenement = ? " +
                "ORDER BY s.montant DESC";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEvenement);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sponsoring s = new Sponsoring();
                s.setIdSponsoring(rs.getInt("idSponsoring"));
                s.setIdSponsor(rs.getInt("idSponsor"));
                s.setIdEvenement(rs.getInt("idEvenement"));
                s.setMontant(rs.getDouble("montant"));
                s.setTypeContribution(rs.getString("type_contribution"));
                s.setStatut(rs.getString("statut"));

                sponsorings.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Erreur: " + e.getMessage());
        }

        return sponsorings;
    }

    // Méthode pour obtenir le total des sponsors d'un événement
    public double getTotalSponsorsByEvenement(int idEvenement) {
        String sql = "SELECT SUM(montant) as total FROM sponsoring WHERE idEvenement = ? AND statut = 'actif'";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idEvenement);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getDouble("total");
            }

        } catch (SQLException e) {
            System.out.println("Erreur: " + e.getMessage());
        }

        return 0.0;
    }
}