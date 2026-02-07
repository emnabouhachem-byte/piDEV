package edu.project.tests;

import edu.project.entities.offreEmploi;
import edu.project.entities.candidature;
import edu.project.tools.MyConnection;
import edu.project.services.offresDAO;
import edu.project.services.candidatureDAO;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {
        MyConnection mc = new MyConnection();
        offresDAO offreDAO = new offresDAO(mc);
        candidatureDAO candDAO = new candidatureDAO(mc);
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

    private static void gestionOffres(Scanner scanner, offresDAO dao) {
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

    private static void gestionCandidatures(Scanner scanner, candidatureDAO dao) {
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

}
