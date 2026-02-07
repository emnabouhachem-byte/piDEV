package edu.project.services;

import edu.project.entities.Role;
import edu.project.entities.Utilisateur;
import edu.project.interfaces.IService;
import edu.project.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurService implements IService<Utilisateur> {

    @Override
    public void addEntity(Utilisateur u) throws SQLException {

        String requete = "INSERT INTO utilisateur " +
                "(nom, prenom, email, motDePasse, role, poste, salaire, dateEmbauche, statut, competences, adresse, idEquipe) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = new MyConnection().getCnx().prepareStatement(requete);

        pst.setString(1, u.getNom());
        pst.setString(2, u.getPrenom());
        pst.setString(3, u.getEmail());
        pst.setString(4, u.getMotDePasse());
        pst.setString(5, u.getRole().name());

        // Champs employé (optionnels)
        pst.setString(6, u.getPoste());

        if (u.getSalaire() != null)
            pst.setDouble(7, u.getSalaire());
        else
            pst.setNull(7, Types.DOUBLE);

        if (u.getDateEmbauche() != null)
            pst.setDate(8, Date.valueOf(u.getDateEmbauche()));
        else
            pst.setNull(8, Types.DATE);

        pst.setString(9, u.getStatut());
        pst.setString(10, u.getCompetences());
        pst.setString(11, u.getAdresse());

        if (u.getIdEquipe() != null)
            pst.setInt(12, u.getIdEquipe());
        else
            pst.setNull(12, Types.INTEGER);

        pst.executeUpdate();
        System.out.println("Utilisateur ajouté avec succès ");
    }

    // ================= DELETE =================
    @Override
    public void deleteEntity(Utilisateur utilisateur) throws SQLException {
        String requete = "DELETE FROM utilisateur WHERE id = ?";
        PreparedStatement ps = new MyConnection().getCnx().prepareStatement(requete);
        ps.setInt(1, utilisateur.getId());

        if (ps.executeUpdate() > 0)
            System.out.println("Utilisateur supprimé ");
        else
            System.out.println("Utilisateur introuvable ");
    }

    // ================= UPDATE =================
    @Override
    public void updateEntity(int id, Utilisateur u) throws SQLException {

        String requete = "UPDATE utilisateur SET " +
                "nom=?, prenom=?, email=?, motDePasse=?, role=?, poste=?, salaire=?, dateEmbauche=?, statut=?, competences=?, adresse=?, idEquipe=? " +
                "WHERE id=?";

        PreparedStatement ps = new MyConnection().getCnx().prepareStatement(requete);

        ps.setString(1, u.getNom());
        ps.setString(2, u.getPrenom());
        ps.setString(3, u.getEmail());
        ps.setString(4, u.getMotDePasse());
        ps.setString(5, u.getRole().name());
        ps.setString(6, u.getPoste());

        if (u.getSalaire() != null)
            ps.setDouble(7, u.getSalaire());
        else
            ps.setNull(7, Types.DOUBLE);

        if (u.getDateEmbauche() != null)
            ps.setDate(8, Date.valueOf(u.getDateEmbauche()));
        else
            ps.setNull(8, Types.DATE);

        ps.setString(9, u.getStatut());
        ps.setString(10, u.getCompetences());
        ps.setString(11, u.getAdresse());

        if (u.getIdEquipe() != null)
            ps.setInt(12, u.getIdEquipe());
        else
            ps.setNull(12, Types.INTEGER);

        ps.setInt(13, id);

        if (ps.executeUpdate() > 0)
            System.out.println("Utilisateur modifié ");
        else
            System.out.println("Utilisateur introuvable ");
    }

    // ================= GET ALL =================
    @Override
    public List<Utilisateur> getData() {

        List<Utilisateur> utilisateurs = new ArrayList<>();
        String requete = "SELECT * FROM utilisateur";

        try (Statement st = new MyConnection().getCnx().createStatement();
             ResultSet rs = st.executeQuery(requete)) {

            while (rs.next()) {

                Utilisateur u = new Utilisateur();

                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                u.setMotDePasse(rs.getString("motDePasse"));

                // ===== ROLE safe =====
                String roleStr = rs.getString("role");
                if (roleStr != null) {
                    try {
                        u.setRole(Role.valueOf(roleStr.toUpperCase())); // EMPLOYE, ADMIN, RH
                    } catch (IllegalArgumentException ex) {
                        u.setRole(null);
                    }
                }

                // ===== Champs spécifiques pour EMPLOYE =====
                if (u.getRole() == Role.EMPLOYE) {
                    u.setPoste(rs.getString("poste"));
                    u.setSalaire(rs.getObject("salaire", Double.class));
                    Date dateEmb = rs.getDate("dateEmbauche");
                    u.setDateEmbauche(dateEmb != null ? dateEmb.toLocalDate() : null);
                    u.setStatut(rs.getString("statut"));
                    u.setCompetences(rs.getString("competences"));
                }

                // ===== Champs communs =====
                u.setAdresse(rs.getString("adresse"));
                u.setIdEquipe(rs.getObject("idEquipe", Integer.class));

                utilisateurs.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Erreur getData : " + e.getMessage());
            e.printStackTrace();
        }

        return utilisateurs;


    }
}


