package edu.project.tests;

import edu.project.entities.offreEmploi;
import edu.project.entities.candidature;
import edu.project.tools.MyConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        MyConnection mc = new MyConnection();
        OffreDAO offreDAO = new OffreDAO(mc);
        CandidatureDAO candDAO = new CandidatureDAO(mc);
        Scanner scanner = new Scanner(System.in);

        boolean running = true;
        while (running) {
            System.out.println("\n=== MENU ===\n1. Offres\n2. Candidatures\n0. Quitter");
            System.out.print("Choix: ");
            int mainChoice = Integer.parseInt(scanner.nextLine().trim());
            switch (mainChoice) {
                case 1:
                    gestionOffres(scanner, offreDAO);
                    break;
                case 2:
                    gestionCandidatures(scanner, candDAO);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Choix invalide");
            }
        }

        scanner.close();
        mc.closeConnection();
        System.out.println("Fin.");
    }

    private static void gestionOffres(Scanner scanner, OffreDAO dao) {
        try {
            System.out.println("\n--- Offres ---\n1.Create\n2.List\n3.Read\n4.Update\n5.Delete\n0.Back");
            System.out.print("Choix: ");
            int c = Integer.parseInt(scanner.nextLine().trim());
            switch (c) {
                case 1 -> {
                    System.out.print("Titre: ");
                    String titre = scanner.nextLine();
                    System.out.print("Description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Type contrat: ");
                    String type = scanner.nextLine();
                    System.out.print("Salaire (ex: 2500.00): ");
                    double sal = Double.parseDouble(scanner.nextLine());
                    System.out.print("Date publication (YYYY-MM-DD): ");
                    LocalDate dp = LocalDate.parse(scanner.nextLine());
                    System.out.print("Date expiration (YYYY-MM-DD): ");
                    LocalDate de = LocalDate.parse(scanner.nextLine());
                    offreEmploi o = new offreEmploi(titre, desc, type, sal, dp, de);
                    dao.create(o);
                }
                case 2 -> {
                    List<offreEmploi> all = dao.readAll();
                    all.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    offreEmploi o = dao.read(id);
                    System.out.println(o == null ? "Non trouvé" : o);
                }
                case 4 -> {
                    System.out.print("ID: ");
                    int idu = Integer.parseInt(scanner.nextLine());
                    offreEmploi o = dao.read(idu);
                    if (o != null) {
                        System.out.print("Nouveau titre: ");
                        o.setTitre(scanner.nextLine());
                        System.out.print("Nouveau type: ");
                        o.setTypeContrat(scanner.nextLine());
                        System.out.print("Nouveau salaire: ");
                        o.setSalaire(Double.parseDouble(scanner.nextLine()));
                        dao.update(o);
                    } else System.out.println("Offre non trouvée");
                }
                case 5 -> {
                    System.out.print("ID: ");
                    int idd = Integer.parseInt(scanner.nextLine());
                    dao.delete(idd);
                }
                default -> {
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    private static void gestionCandidatures(Scanner scanner, CandidatureDAO dao) {
        try {
            System.out.println("\n--- Candidatures ---\n1.Create\n2.List\n3.Read\n4.Update\n5.Delete\n6.By offre\n0.Back");
            System.out.print("Choix: ");
            int c = Integer.parseInt(scanner.nextLine().trim());
            switch (c) {
                case 1 -> {
                    System.out.print("Date dépôt (YYYY-MM-DD): ");
                    LocalDate dd = LocalDate.parse(scanner.nextLine());
                    System.out.print("Statut: ");
                    String statut = scanner.nextLine();
                    System.out.print("Disponibilité: ");
                    String dispo = scanner.nextLine();
                    System.out.print("Score (ex: 85.50): ");
                    double score = Double.parseDouble(scanner.nextLine());
                    System.out.print("CV (texte): ");
                    String cv = scanner.nextLine();
                    System.out.print("ID offre: ");
                    int offreId = Integer.parseInt(scanner.nextLine());
                    candidature cand = new candidature(dd, statut, dispo, score, cv, offreId);
                    dao.create(cand);
                }
                case 2 -> {
                    List<candidature> all = dao.readAll();
                    all.forEach(System.out::println);
                }
                case 3 -> {
                    System.out.print("ID: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    candidature cnd = dao.read(id);
                    System.out.println(cnd == null ? "Non trouvé" : cnd);
                }
                case 4 -> {
                    System.out.print("ID: ");
                    int idu = Integer.parseInt(scanner.nextLine());
                    candidature cnd = dao.read(idu);
                    if (cnd != null) {
                        System.out.print("Nouveau statut: ");
                        cnd.setStatut(scanner.nextLine());
                        System.out.print("Nouveau score: ");
                        cnd.setScore(Double.parseDouble(scanner.nextLine()));
                        dao.update(cnd);
                    } else System.out.println("Non trouvé");
                }
                case 5 -> {
                    System.out.print("ID: ");
                    int idd = Integer.parseInt(scanner.nextLine());
                    dao.delete(idd);
                }
                case 6 -> {
                    System.out.print("ID offre: ");
                    int oid = Integer.parseInt(scanner.nextLine());
                    List<candidature> list = dao.readByOffre(oid);
                    list.forEach(System.out::println);
                }
                default -> {
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur: " + e.getMessage());
        }
    }

    // DAO pour offre (implémentation simple)
    static class OffreDAO {
        private final MyConnection mc;

        OffreDAO(MyConnection mc) {
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

    // DAO pour candidature
    static class CandidatureDAO {
        private final MyConnection mc;

        CandidatureDAO(MyConnection mc) {
            this.mc = mc;
        }

        public void create(candidature c) throws SQLException {
            String sql = "INSERT INTO candidature (date_depot, statut, disponibilite, score, cv, offre_id) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = mc.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                ps.setDate(1, Date.valueOf(c.getDateDepot()));
                ps.setString(2, c.getStatut());
                ps.setString(3, c.getDisponibilite());
                ps.setDouble(4, c.getScore());
                ps.setString(5, c.getCv());
                ps.setInt(6, c.getOffreId());
                ps.executeUpdate();
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) c.setId(rs.getInt(1));
                }
                System.out.println("Candidature créée (id=" + c.getId() + ")");
            }
        }

        public candidature read(int id) throws SQLException {
            String sql = "SELECT * FROM candidature WHERE id = ?";
            try (PreparedStatement ps = mc.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        candidature c = new candidature();
                        c.setId(rs.getInt("id"));
                        c.setDateDepot(rs.getDate("date_depot").toLocalDate());
                        c.setStatut(rs.getString("statut"));
                        c.setDisponibilite(rs.getString("disponibilite"));
                        c.setScore(rs.getDouble("score"));
                        c.setCv(rs.getString("cv"));
                        c.setOffreId(rs.getInt("offre_id"));
                        return c;
                    }
                }
            }
            return null;
        }

        public List<candidature> readAll() throws SQLException {
            List<candidature> list = new ArrayList<>();
            String sql = "SELECT * FROM candidature";
            try (Statement st = mc.getConnection().createStatement();
                 ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    candidature c = new candidature();
                    c.setId(rs.getInt("id"));
                    c.setDateDepot(rs.getDate("date_depot").toLocalDate());
                    c.setStatut(rs.getString("statut"));
                    c.setDisponibilite(rs.getString("disponibilite"));
                    c.setScore(rs.getDouble("score"));
                    c.setCv(rs.getString("cv"));
                    c.setOffreId(rs.getInt("offre_id"));
                    list.add(c);
                }
            }
            return list;
        }

        public List<candidature> readByOffre(int offreId) throws SQLException {
            List<candidature> list = new ArrayList<>();
            String sql = "SELECT * FROM candidature WHERE offre_id = ?";
            try (PreparedStatement ps = mc.getConnection().prepareStatement(sql)) {
                ps.setInt(1, offreId);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        candidature c = new candidature();
                        c.setId(rs.getInt("id"));
                        c.setDateDepot(rs.getDate("date_depot").toLocalDate());
                        c.setStatut(rs.getString("statut"));
                        c.setDisponibilite(rs.getString("disponibilite"));
                        c.setScore(rs.getDouble("score"));
                        c.setCv(rs.getString("cv"));
                        c.setOffreId(rs.getInt("offre_id"));
                        list.add(c);
                    }
                }
            }
            return list;
        }

        public void update(candidature c) throws SQLException {
            String sql = "UPDATE candidature SET date_depot=?, statut=?, disponibilite=?, score=?, cv=?, offre_id=? WHERE id=?";
            try (PreparedStatement ps = mc.getConnection().prepareStatement(sql)) {
                ps.setDate(1, Date.valueOf(c.getDateDepot()));
                ps.setString(2, c.getStatut());
                ps.setString(3, c.getDisponibilite());
                ps.setDouble(4, c.getScore());
                ps.setString(5, c.getCv());
                ps.setInt(6, c.getOffreId());
                ps.setInt(7, c.getId());
                ps.executeUpdate();
                System.out.println("Candidature mise à jour");
            }
        }

        public void delete(int id) throws SQLException {
            String sql = "DELETE FROM candidature WHERE id = ?";
            try (PreparedStatement ps = mc.getConnection().prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
                System.out.println("Candidature supprimée");
            }
        }
    }

}
