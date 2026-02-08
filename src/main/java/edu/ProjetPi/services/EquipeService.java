package edu.ProjetPi.services;

import edu.ProjetPi.entities.Equipe;
import edu.ProjetPi.entities.Utilisateur;
import edu.ProjetPi.entities.Role;
import edu.ProjetPi.interfaces.IService;
import edu.ProjetPi.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EquipeService implements IService<Equipe> {

    // ================= ADD =================
    @Override
    public void addEntity(Equipe equipe) throws SQLException {
        String sql = "INSERT INTO equipe (nom, description, dateCreation, nbr_membre, budget) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = new MyConnection().getCnx().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, equipe.getNom());
            ps.setString(2, equipe.getDescription());
            ps.setDate(3, java.sql.Date.valueOf(equipe.getDateCreation()));
            ps.setInt(4, equipe.getNbr_membre());
            ps.setDouble(5, equipe.getBudget());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            int idEquipe = 0;
            if (rs.next()) {
                idEquipe = rs.getInt(1);
            }


            if (equipe.getListeEmployes() != null) {
                for (Utilisateur u : equipe.getListeEmployes()) {
                    String sqlUser = "UPDATE utilisateur SET idEquipe = ? WHERE id = ?";
                    try (PreparedStatement psUser = new MyConnection().getCnx().prepareStatement(sqlUser)) {
                        psUser.setInt(1, idEquipe);
                        psUser.setInt(2, u.getId());
                        psUser.executeUpdate();
                    }
                }
            }

            System.out.println("Equipe ajoutée avec succès ");
        }
    }

    // ================= DELETE =================
    @Override
    public void deleteEntity(Equipe equipe) throws SQLException {

        String sqlUser = "UPDATE utilisateur SET idEquipe = NULL WHERE idEquipe = ?";
        try (PreparedStatement psUser = new MyConnection().getCnx().prepareStatement(sqlUser)) {
            psUser.setInt(1, equipe.getId());
            psUser.executeUpdate();
        }

        String sqlEquipe = "DELETE FROM equipe WHERE id = ?";
        try (PreparedStatement ps = new MyConnection().getCnx().prepareStatement(sqlEquipe)) {
            ps.setInt(1, equipe.getId());
            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Equipe supprimée ");
            else
                System.out.println("Equipe introuvable ");
        }
    }

    // ================= UPDATE =================
    @Override
    public void updateEntity(int id, Equipe equipe) throws SQLException {
        String sql = "UPDATE equipe SET nom=?, description=?, dateCreation=?, nbr_membre=?, budget=? WHERE id=?";
        try (PreparedStatement ps = new MyConnection().getCnx().prepareStatement(sql)) {
            ps.setString(1, equipe.getNom());
            ps.setString(2, equipe.getDescription());
            ps.setDate(3, java.sql.Date.valueOf(equipe.getDateCreation()));
            ps.setInt(4, equipe.getNbr_membre());
            ps.setDouble(5, equipe.getBudget());
            ps.setInt(6, id);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("Equipe modifiée ");
            else
                System.out.println("Equipe introuvable ");
        }

        String sqlClear = "UPDATE utilisateur SET idEquipe = NULL WHERE idEquipe = ?";
        try (PreparedStatement psClear = new MyConnection().getCnx().prepareStatement(sqlClear)) {
            psClear.setInt(1, id);
            psClear.executeUpdate();
        }


        if (equipe.getListeEmployes() != null) {
            for (Utilisateur u : equipe.getListeEmployes()) {
                String sqlUser = "UPDATE utilisateur SET idEquipe = ? WHERE id = ?";
                try (PreparedStatement psUser = new MyConnection().getCnx().prepareStatement(sqlUser)) {
                    psUser.setInt(1, id);
                    psUser.setInt(2, u.getId());
                    psUser.executeUpdate();
                }
            }
        }
    }

    // ================= GET ALL =================
    @Override
    public List<Equipe> getData() {
        List<Equipe> equipes = new ArrayList<>();
        String sql = "SELECT * FROM equipe";

        try (Statement st = new MyConnection().getCnx().createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Equipe e = new Equipe();
                e.setId(rs.getInt("id"));
                e.setNom(rs.getString("nom"));
                e.setDescription(rs.getString("description"));
                e.setDateCreation(rs.getDate("dateCreation").toLocalDate());
                e.setNbr_membre(rs.getInt("nbr_membre"));
                e.setBudget(rs.getDouble("budget"));

                // yjib les employe
                String sqlEmp = "SELECT * FROM utilisateur WHERE idEquipe = ?";
                try (PreparedStatement psEmp = new MyConnection().getCnx().prepareStatement(sqlEmp)) {
                    psEmp.setInt(1, e.getId());
                    ResultSet rsEmp = psEmp.executeQuery();
                    List<Utilisateur> listeEmp = new ArrayList<>();
                    while (rsEmp.next()) {
                        Utilisateur u = new Utilisateur();
                        u.setId(rsEmp.getInt("id"));
                        u.setNom(rsEmp.getString("nom"));
                        u.setPrenom(rsEmp.getString("prenom"));
                        u.setEmail(rsEmp.getString("email"));

                        String roleStr = rsEmp.getString("role");
                        if (roleStr != null) {
                            try {
                                u.setRole(Role.valueOf(roleStr.toUpperCase()));
                            } catch (IllegalArgumentException ex) {
                                u.setRole(null);
                            }
                        }

                        u.setPoste(rsEmp.getString("poste"));
                        u.setSalaire(rsEmp.getObject("salaire", Double.class));
                        Date dateEmb = rsEmp.getDate("dateEmbauche");
                        u.setDateEmbauche(dateEmb != null ? dateEmb.toLocalDate() : null);
                        u.setStatut(rsEmp.getString("statut"));
                        u.setCompetences(rsEmp.getString("competences"));
                        u.setAdresse(rsEmp.getString("adresse"));
                        u.setIdEquipe(rsEmp.getObject("idEquipe", Integer.class));

                        listeEmp.add(u);
                    }
                    e.setListeEmployes(listeEmp);
                }

                equipes.add(e);
            }

        } catch (SQLException ex) {
            System.out.println("Erreur getData Equipe: " + ex.getMessage());
            ex.printStackTrace();
        }

        return equipes;
    }
}
