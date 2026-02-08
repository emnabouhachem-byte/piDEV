package edu.ProjetPi.services;

import edu.ProjetPi.entities.Sponsor;
import edu.ProjetPi.interfaces.IService;
import edu.ProjetPi.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SponsorService implements IService<Sponsor> {

    @Override
    public void addEntity(Sponsor sponsor) {
        String sql = "INSERT INTO sponsor (nom, type_sponsor, email, telephone, adresse, " +
                "budget_alloue, date_debut, date_fin, statut, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, sponsor.getNom());
            ps.setString(2, sponsor.getTypeSponsor());
            ps.setString(3, sponsor.getEmail());
            ps.setString(4, sponsor.getTelephone());
            ps.setString(5, sponsor.getAdresse());
            ps.setDouble(6, sponsor.getBudgetAlloue());

            if (sponsor.getDateDebut() != null)
                ps.setDate(7, Date.valueOf(sponsor.getDateDebut()));
            else
                ps.setNull(7, Types.DATE);

            if (sponsor.getDateFin() != null)
                ps.setDate(8, Date.valueOf(sponsor.getDateFin()));
            else
                ps.setNull(8, Types.DATE);

            ps.setString(9, sponsor.getStatut());
            ps.setString(10, sponsor.getNotes());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                sponsor.setIdSponsor(rs.getInt(1));
            }

            System.out.println("Sponsor ajouté avec succès");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du sponsor: " + e.getMessage());
        }
    }

    @Override
    public void deleteEntity(Sponsor sponsor) {
        String sql = "DELETE FROM sponsor WHERE idSponsor = ?";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, sponsor.getIdSponsor());
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Sponsor supprimé avec succès");
            else
                System.out.println("Sponsor introuvable");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression: " + e.getMessage());
        }
    }

    @Override
    public void updateEntity(int id, Sponsor sponsor) {
        String sql = "UPDATE sponsor SET nom=?, type_sponsor=?, email=?, telephone=?, " +
                "adresse=?, budget_alloue=?, date_debut=?, date_fin=?, statut=?, notes=? " +
                "WHERE idSponsor=?";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sponsor.getNom());
            ps.setString(2, sponsor.getTypeSponsor());
            ps.setString(3, sponsor.getEmail());
            ps.setString(4, sponsor.getTelephone());
            ps.setString(5, sponsor.getAdresse());
            ps.setDouble(6, sponsor.getBudgetAlloue());

            if (sponsor.getDateDebut() != null)
                ps.setDate(7, Date.valueOf(sponsor.getDateDebut()));
            else
                ps.setNull(7, Types.DATE);

            if (sponsor.getDateFin() != null)
                ps.setDate(8, Date.valueOf(sponsor.getDateFin()));
            else
                ps.setNull(8, Types.DATE);

            ps.setString(9, sponsor.getStatut());
            ps.setString(10, sponsor.getNotes());
            ps.setInt(11, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Sponsor modifié avec succès");
            else
                System.out.println("Sponsor introuvable");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification: " + e.getMessage());
        }
    }

    @Override
    public List<Sponsor> getData() {
        List<Sponsor> sponsors = new ArrayList<>();
        String sql = "SELECT * FROM sponsor ORDER BY nom";

        try (Connection conn = new MyConnection().getCnx();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Sponsor s = new Sponsor();
                s.setIdSponsor(rs.getInt("idSponsor"));
                s.setNom(rs.getString("nom"));
                s.setTypeSponsor(rs.getString("type_sponsor"));
                s.setEmail(rs.getString("email"));
                s.setTelephone(rs.getString("telephone"));
                s.setAdresse(rs.getString("adresse"));
                s.setBudgetAlloue(rs.getDouble("budget_alloue"));

                Date dateDebut = rs.getDate("date_debut");
                if (dateDebut != null)
                    s.setDateDebut(dateDebut.toLocalDate());

                Date dateFin = rs.getDate("date_fin");
                if (dateFin != null)
                    s.setDateFin(dateFin.toLocalDate());

                s.setStatut(rs.getString("statut"));
                s.setNotes(rs.getString("notes"));

                sponsors.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des sponsors: " + e.getMessage());
        }

        return sponsors;
    }

    // Méthode pour trouver par ID
    public Sponsor getById(int id) {
        String sql = "SELECT * FROM sponsor WHERE idSponsor = ?";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Sponsor s = new Sponsor();
                s.setIdSponsor(rs.getInt("idSponsor"));
                s.setNom(rs.getString("nom"));
                s.setTypeSponsor(rs.getString("type_sponsor"));
                s.setEmail(rs.getString("email"));
                s.setTelephone(rs.getString("telephone"));
                s.setAdresse(rs.getString("adresse"));
                s.setBudgetAlloue(rs.getDouble("budget_alloue"));

                Date dateDebut = rs.getDate("date_debut");
                if (dateDebut != null)
                    s.setDateDebut(dateDebut.toLocalDate());

                Date dateFin = rs.getDate("date_fin");
                if (dateFin != null)
                    s.setDateFin(dateFin.toLocalDate());

                s.setStatut(rs.getString("statut"));
                s.setNotes(rs.getString("notes"));

                return s;
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche du sponsor: " + e.getMessage());
        }

        return null;
    }

    // Méthode pour chercher par nom
    public List<Sponsor> searchByName(String nom) {
        List<Sponsor> sponsors = new ArrayList<>();
        String sql = "SELECT * FROM sponsor WHERE nom LIKE ? ORDER BY nom";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nom + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Sponsor s = new Sponsor();
                s.setIdSponsor(rs.getInt("idSponsor"));
                s.setNom(rs.getString("nom"));
                s.setTypeSponsor(rs.getString("type_sponsor"));
                s.setEmail(rs.getString("email"));
                s.setTelephone(rs.getString("telephone"));
                s.setAdresse(rs.getString("adresse"));
                s.setBudgetAlloue(rs.getDouble("budget_alloue"));

                Date dateDebut = rs.getDate("date_debut");
                if (dateDebut != null)
                    s.setDateDebut(dateDebut.toLocalDate());

                Date dateFin = rs.getDate("date_fin");
                if (dateFin != null)
                    s.setDateFin(dateFin.toLocalDate());

                s.setStatut(rs.getString("statut"));
                s.setNotes(rs.getString("notes"));

                sponsors.add(s);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche: " + e.getMessage());
        }

        return sponsors;
    }
}