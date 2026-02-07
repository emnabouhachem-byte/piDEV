package edu.project.services;

import edu.project.entities.offreEmploi;
import edu.project.tools.MyConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class offresDAO {
    private final MyConnection mc;

    public offresDAO(MyConnection mc) {
        this.mc = mc;
    }

    public void create(offreEmploi o) throws SQLException {
        String sql = "INSERT INTO offre_emploi (titre, description, type_contrat, salaire, date_publication, date_expiration) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = mc.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, o.getTitre());
            ps.setString(2, o.getDescription());
            ps.setString(3, o.getTypeContrat());
            ps.setDouble(4, o.getSalaire());
            ps.setDate(5, Date.valueOf(o.getDatePublication()));
            ps.setDate(6, Date.valueOf(o.getDateExpiration()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) o.setId(rs.getInt(1));
            }
            System.out.println("Offre créée (id=" + o.getId() + ")");
        }
    }

    public offreEmploi read(int id) throws SQLException {
        String sql = "SELECT * FROM offre_emploi WHERE id = ?";
        try (PreparedStatement ps = mc.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    offreEmploi o = new offreEmploi();
                    o.setId(rs.getInt("id"));
                    o.setTitre(rs.getString("titre"));
                    o.setDescription(rs.getString("description"));
                    o.setTypeContrat(rs.getString("type_contrat"));
                    o.setSalaire(rs.getDouble("salaire"));
                    o.setDatePublication(rs.getDate("date_publication").toLocalDate());
                    o.setDateExpiration(rs.getDate("date_expiration").toLocalDate());
                    return o;
                }
            }
        }
        return null;
    }

    public List<offreEmploi> readAll() throws SQLException {
        List<offreEmploi> list = new ArrayList<>();
        String sql = "SELECT * FROM offre_emploi";
        try (Statement st = mc.getConnection().createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                offreEmploi o = new offreEmploi();
                o.setId(rs.getInt("id"));
                o.setTitre(rs.getString("titre"));
                o.setDescription(rs.getString("description"));
                o.setTypeContrat(rs.getString("type_contrat"));
                o.setSalaire(rs.getDouble("salaire"));
                o.setDatePublication(rs.getDate("date_publication").toLocalDate());
                o.setDateExpiration(rs.getDate("date_expiration").toLocalDate());
                list.add(o);
            }
        }
        return list;
    }

    public void update(offreEmploi o) throws SQLException {
        String sql = "UPDATE offre_emploi SET titre=?, description=?, type_contrat=?, salaire=?, date_publication=?, date_expiration=? WHERE id=?";
        try (PreparedStatement ps = mc.getConnection().prepareStatement(sql)) {
            ps.setString(1, o.getTitre());
            ps.setString(2, o.getDescription());
            ps.setString(3, o.getTypeContrat());
            ps.setDouble(4, o.getSalaire());
            ps.setDate(5, Date.valueOf(o.getDatePublication()));
            ps.setDate(6, Date.valueOf(o.getDateExpiration()));
            ps.setInt(7, o.getId());
            ps.executeUpdate();
            System.out.println("Offre mise à jour");
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM offre_emploi WHERE id = ?";
        try (PreparedStatement ps = mc.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("Offre supprimée");
        }
    }
}
