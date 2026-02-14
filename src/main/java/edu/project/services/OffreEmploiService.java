package edu.project.services;

import edu.project.entities.OffreEmploi;
import edu.project.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OffreEmploiService {

    private final Connection cnx;

    public OffreEmploiService() {
        cnx = MyConnection.getConnection();
    }

    // ============================
    // AJOUTER
    // ============================
    public int ajouter(OffreEmploi o) {

        String sql = "INSERT INTO offre_emploi " +
                "(titre, description, type_contrat, salaire, date_publication, date_expiration) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, o.getTitre());
            ps.setString(2, o.getDescription());
            ps.setString(3, o.getTypeContrat());
            ps.setDouble(4, o.getSalaire());

            // ✅ Protection contre NullPointerException
            ps.setDate(5, o.getDatePublication() != null
                    ? Date.valueOf(o.getDatePublication())
                    : null);

            ps.setDate(6, o.getDateExpiration() != null
                    ? Date.valueOf(o.getDateExpiration())
                    : null);

            int affected = ps.executeUpdate();
            if (affected == 0) return -1;

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    o.setId(id);
                    return id;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur ajout: " + e.getMessage());
        }

        return -1;
    }

    // ============================
    // AFFICHER
    // ============================
    public List<OffreEmploi> afficher() {

        List<OffreEmploi> list = new ArrayList<>();
        String sql = "SELECT * FROM offre_emploi";

        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                Date dPub = rs.getDate("date_publication");
                Date dExp = rs.getDate("date_expiration");

                list.add(new OffreEmploi(
                        rs.getInt("id"),
                        rs.getString("titre"),
                        rs.getString("description"),
                        rs.getString("type_contrat"),
                        rs.getDouble("salaire"),
                        dPub != null ? dPub.toLocalDate() : null,
                        dExp != null ? dExp.toLocalDate() : null
                ));
            }

        } catch (SQLException e) {
            System.out.println("Erreur affichage: " + e.getMessage());
        }

        return list;
    }

    // ============================
    // MODIFIER
    // ============================
    public void modifier(OffreEmploi o) {

        String sql = "UPDATE offre_emploi SET " +
                "titre=?, description=?, type_contrat=?, salaire=?, " +
                "date_publication=?, date_expiration=? WHERE id=?";

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, o.getTitre());
            ps.setString(2, o.getDescription());
            ps.setString(3, o.getTypeContrat());
            ps.setDouble(4, o.getSalaire());

            ps.setDate(5, o.getDatePublication() != null
                    ? Date.valueOf(o.getDatePublication())
                    : null);

            ps.setDate(6, o.getDateExpiration() != null
                    ? Date.valueOf(o.getDateExpiration())
                    : null);

            ps.setInt(7, o.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erreur modification: " + e.getMessage());
        }
    }

    // ============================
    // SUPPRIMER
    // ============================
    public void supprimer(int id) {

        String sql = "DELETE FROM offre_emploi WHERE id=?";

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur suppression: " + e.getMessage());
        }
    }

    // ============================
    // RECHERCHER
    // ============================
    public List<OffreEmploi> rechercher(String mot) {

        List<OffreEmploi> list = new ArrayList<>();
        String sql = "SELECT * FROM offre_emploi WHERE titre LIKE ? OR type_contrat LIKE ?";

        try (PreparedStatement ps = cnx.prepareStatement(sql)) {

            ps.setString(1, "%" + mot + "%");
            ps.setString(2, "%" + mot + "%");

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Date dPub = rs.getDate("date_publication");
                    Date dExp = rs.getDate("date_expiration");

                    list.add(new OffreEmploi(
                            rs.getInt("id"),
                            rs.getString("titre"),
                            rs.getString("description"),
                            rs.getString("type_contrat"),
                            rs.getDouble("salaire"),
                            dPub != null ? dPub.toLocalDate() : null,
                            dExp != null ? dExp.toLocalDate() : null
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erreur recherche: " + e.getMessage());
        }

        return list;
    }
}
