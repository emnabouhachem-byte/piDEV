package edu.ProjetPi.services;

import edu.ProjetPi.entities.Employe;
import edu.ProjetPi.interfaces.IService;
import edu.ProjetPi.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeService implements IService<Employe> {

    @Override
    public void addEntity(Employe employe) {
        String sql = "INSERT INTO employe (nom, prenom, email, motdepasse, role, poste, " +
                "salaire, dateEmbauche, statut, competences, adresse, idEquipe) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, employe.getNom());
            ps.setString(2, employe.getPrenom());
            ps.setString(3, employe.getEmail());
            ps.setString(4, employe.getMotdepasse());
            ps.setString(5, employe.getRole());
            ps.setString(6, employe.getPoste());

            if (employe.getSalaire() != null)
                ps.setDouble(7, employe.getSalaire());
            else
                ps.setNull(7, Types.DOUBLE);

            if (employe.getDateEmbauche() != null)
                ps.setDate(8, Date.valueOf(employe.getDateEmbauche()));
            else
                ps.setNull(8, Types.DATE);

            ps.setString(9, employe.getStatut());
            ps.setString(10, employe.getCompetences());
            ps.setString(11, employe.getAdresse());

            if (employe.getIdEquipe() != null)
                ps.setInt(12, employe.getIdEquipe());
            else
                ps.setNull(12, Types.INTEGER);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                employe.setId(rs.getInt(1));
            }

            System.out.println("Employé ajouté avec succès: " + employe.getNom() + " " + employe.getPrenom());

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de l'employé: " + e.getMessage());
        }
    }

    @Override
    public void deleteEntity(Employe employe) {
        String sql = "DELETE FROM employe WHERE id = ?";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, employe.getId());
            int rows = ps.executeUpdate();

            if (rows > 0)
                System.out.println("Employé supprimé avec succès");
            else
                System.out.println("Employé introuvable");

        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression: " + e.getMessage());
        }
    }

    @Override
    public void updateEntity(int id, Employe employe) {
        String sql = "UPDATE employe SET nom=?, prenom=?, email=?, motdepasse=?, role=?, " +
                "poste=?, salaire=?, dateEmbauche=?, statut=?, competences=?, " +
                "adresse=?, idEquipe=? WHERE id=?";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, employe.getNom());
            ps.setString(2, employe.getPrenom());
            ps.setString(3, employe.getEmail());
            ps.setString(4, employe.getMotdepasse());
            ps.setString(5, employe.getRole());
            ps.setString(6, employe.getPoste());

            if (employe.getSalaire() != null)
                ps.setDouble(7, employe.getSalaire());
            else
                ps.setNull(7, Types.DOUBLE);

            if (employe.getDateEmbauche() != null)
                ps.setDate(8, Date.valueOf(employe.getDateEmbauche()));
            else
                ps.setNull(8, Types.DATE);

            ps.setString(9, employe.getStatut());
            ps.setString(10, employe.getCompetences());
            ps.setString(11, employe.getAdresse());

            if (employe.getIdEquipe() != null)
                ps.setInt(12, employe.getIdEquipe());
            else
                ps.setNull(12, Types.INTEGER);

            ps.setInt(13, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Employé modifié avec succès");
            else
                System.out.println("Employé introuvable");

        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification: " + e.getMessage());
        }
    }

    @Override
    public List<Employe> getData() {
        List<Employe> employes = new ArrayList<>();
        String sql = "SELECT * FROM employe ORDER BY nom, prenom";

        try (Connection conn = new MyConnection().getCnx();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Employe e = new Employe();
                e.setId(rs.getInt("id"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setEmail(rs.getString("email"));
                e.setMotdepasse(rs.getString("motdepasse"));
                e.setRole(rs.getString("role"));
                e.setPoste(rs.getString("poste"));
                e.setSalaire(rs.getObject("salaire", Double.class));

                Date dateEmb = rs.getDate("dateEmbauche");
                if (dateEmb != null)
                    e.setDateEmbauche(dateEmb.toLocalDate());

                e.setStatut(rs.getString("statut"));
                e.setCompetences(rs.getString("competences"));
                e.setAdresse(rs.getString("adresse"));
                e.setIdEquipe(rs.getObject("idEquipe", Integer.class));

                employes.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des employés: " + e.getMessage());
        }

        return employes;
    }

    // Méthode pour obtenir un employé par ID
    public Employe getById(int id) {
        String sql = "SELECT * FROM employe WHERE id = ?";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Employe e = new Employe();
                e.setId(rs.getInt("id"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setEmail(rs.getString("email"));
                e.setMotdepasse(rs.getString("motdepasse"));
                e.setRole(rs.getString("role"));
                e.setPoste(rs.getString("poste"));
                e.setSalaire(rs.getObject("salaire", Double.class));

                Date dateEmb = rs.getDate("dateEmbauche");
                if (dateEmb != null)
                    e.setDateEmbauche(dateEmb.toLocalDate());

                e.setStatut(rs.getString("statut"));
                e.setCompetences(rs.getString("competences"));
                e.setAdresse(rs.getString("adresse"));
                e.setIdEquipe(rs.getObject("idEquipe", Integer.class));

                return e;
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche de l'employé: " + e.getMessage());
        }

        return null;
    }

    // Méthode pour chercher par nom
    public List<Employe> searchByName(String nom) {
        List<Employe> employes = new ArrayList<>();
        String sql = "SELECT * FROM employe WHERE nom LIKE ? OR prenom LIKE ? ORDER BY nom, prenom";

        try (Connection conn = new MyConnection().getCnx();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nom + "%");
            ps.setString(2, "%" + nom + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Employe e = new Employe();
                e.setId(rs.getInt("id"));
                e.setNom(rs.getString("nom"));
                e.setPrenom(rs.getString("prenom"));
                e.setEmail(rs.getString("email"));
                e.setMotdepasse(rs.getString("motdepasse"));
                e.setRole(rs.getString("role"));
                e.setPoste(rs.getString("poste"));
                e.setSalaire(rs.getObject("salaire", Double.class));

                Date dateEmb = rs.getDate("dateEmbauche");
                if (dateEmb != null)
                    e.setDateEmbauche(dateEmb.toLocalDate());

                e.setStatut(rs.getString("statut"));
                e.setCompetences(rs.getString("competences"));
                e.setAdresse(rs.getString("adresse"));
                e.setIdEquipe(rs.getObject("idEquipe", Integer.class));

                employes.add(e);
            }

        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche: " + e.getMessage());
        }

        return employes;
    }
}