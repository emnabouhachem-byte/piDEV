package edu.ProjetPi.tests;

import edu.ProjetPi.entities.*;
import edu.ProjetPi.services.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MainTest {

    public static void main(String[] args) {
        System.out.println("=== DÉBUT DES TESTS ===");

        // D'abord, créer quelques employés pour les tests
        testEmploye();

        testSponsor();
        testEvenement();
        testParticipation();
        testSponsoring();

        System.out.println("\n=== FIN DES TESTS ===");
    }

    private static void testEmploye() {
        System.out.println("\n=== TEST EMPLOYÉ ===");
        EmployeService es = new EmployeService();

        // Vérifier d'abord s'il y a des employés existants
        List<Employe> employesExistants = es.getData();
        System.out.println("Nombre d'employés existants: " + employesExistants.size());

        if (employesExistants.isEmpty()) {
            // Ajouter un admin/manager pour créer des événements
            Employe admin = new Employe();
            admin.setNom("Admin");
            admin.setPrenom("System");
            admin.setEmail("admin@entreprise.com");
            admin.setMotdepasse("admin123");
            admin.setRole("admin");
            admin.setPoste("Directeur");
            admin.setSalaire(5000.0);
            admin.setDateEmbauche(LocalDate.now());
            admin.setStatut("Actif");
            admin.setCompetences("Gestion, Administration");
            admin.setAdresse("Siège social, Tunis");

            es.addEntity(admin);
            System.out.println("Admin créé pour les tests");

            // Ajouter quelques employés pour participer aux événements
            for (int i = 1; i <= 3; i++) {
                Employe employe = new Employe();
                employe.setNom("Employe" + i);
                employe.setPrenom("Test");
                employe.setEmail("employe" + i + "@entreprise.com");
                employe.setMotdepasse("pass" + i);
                employe.setRole("employe");
                employe.setPoste("Développeur");
                employe.setSalaire(2000.0 + (i * 500));
                employe.setDateEmbauche(LocalDate.now().minusMonths(i));
                employe.setStatut("Actif");
                employe.setCompetences("Java, SQL, Spring");
                employe.setAdresse("Adresse " + i + ", Tunis");

                es.addEntity(employe);
                System.out.println("Employé " + i + " créé pour les tests");
            }
        }

        // Afficher tous les employés
        List<Employe> tousLesEmployes = es.getData();
        System.out.println("\nListe de tous les employés (" + tousLesEmployes.size() + "):");
        for (Employe e : tousLesEmployes) {
            System.out.println("- ID: " + e.getId() +
                    " | " + e.getNom() + " " + e.getPrenom() +
                    " | " + e.getPoste() + " | " + e.getEmail());
        }
    }

    private static void testSponsor() {
        System.out.println("\n=== TEST SPONSOR ===");
        SponsorService ss = new SponsorService();

        // Ajouter un sponsor
        Sponsor sponsor = new Sponsor(
                "TechCorp",
                "Entreprise",
                "contact@techcorp.com",
                "12345678",
                "123 Rue des Entrepreneurs, Tunis",
                50000.0,
                LocalDate.now(),
                LocalDate.now().plusYears(1),
                "actif",
                "Sponsor principal"
        );

        ss.addEntity(sponsor);
        System.out.println("Sponsor ajouté: " + sponsor.getNom());

        // Ajouter un deuxième sponsor
        Sponsor sponsor2 = new Sponsor(
                "InnovSoft",
                "Startup",
                "info@innovsoft.com",
                "98765432",
                "456 Avenue Innovation, Sfax",
                25000.0,
                LocalDate.now(),
                LocalDate.now().plusMonths(6),
                "actif",
                "Sponsor silver"
        );

        ss.addEntity(sponsor2);
        System.out.println("Deuxième sponsor ajouté: " + sponsor2.getNom());

        // Lister tous les sponsors
        List<Sponsor> sponsors = ss.getData();
        System.out.println("\nListe des sponsors (" + sponsors.size() + "):");
        for (Sponsor s : sponsors) {
            System.out.println("- ID: " + s.getIdSponsor() +
                    " | Nom: " + s.getNom() +
                    " (" + s.getTypeSponsor() +
                    ") | Budget: " + s.getBudgetAlloue() + " DT" +
                    " | Statut: " + s.getStatut());
        }

        // Modifier un sponsor
        if (!sponsors.isEmpty()) {
            Sponsor toUpdate = sponsors.get(0);
            toUpdate.setBudgetAlloue(75000.0);
            toUpdate.setStatut("premium");
            toUpdate.setNotes("Sponsor principal - contrat renouvelé");
            ss.updateEntity(toUpdate.getIdSponsor(), toUpdate);
            System.out.println("\nSponsor modifié: " + toUpdate.getNom() + " (Budget: " + toUpdate.getBudgetAlloue() + " DT)");
        }

        // Rechercher par nom
        System.out.println("\nRecherche de sponsors contenant 'Tech':");
        List<Sponsor> sponsorsRecherche = ss.searchByName("Tech");
        for (Sponsor s : sponsorsRecherche) {
            System.out.println("- " + s.getNom() + " (" + s.getEmail() + ")");
        }
    }

    private static void testEvenement() {
        System.out.println("\n=== TEST ÉVÉNEMENT ===");
        EvenementService es = new EvenementService();
        EmployeService empService = new EmployeService();

        // Récupérer un employé comme créateur
        List<Employe> employes = empService.getData();
        if (employes.isEmpty()) {
            System.out.println("Aucun employé trouvé pour créer l'événement");
            return;
        }

        // Prendre le premier employé comme créateur
        Employe createur = employes.get(0);
        System.out.println("Créateur de l'événement: " + createur.getNom() + " " + createur.getPrenom() + " (ID: " + createur.getId() + ")");

        // Créer un événement
        Evenement evenement = new Evenement(
                "Conférence Tech 2024",
                "Conférence annuelle sur les nouvelles technologies",
                LocalDateTime.now().plusDays(30),
                LocalDateTime.now().plusDays(31),
                "Centre des Congrès, Tunis",
                "Conférence",
                "planifié",
                20000.0,
                createur.getId()  // Utiliser l'ID de l'employé
        );

        es.addEntity(evenement);
        System.out.println("Événement ajouté: " + evenement.getTitre());

        // Ajouter un deuxième événement
        Evenement evenement2 = new Evenement(
                "Workshop Développement Web",
                "Formation intensive sur les technologies web modernes",
                LocalDateTime.now().plusDays(15),
                LocalDateTime.now().plusDays(16),
                "Espace Coworking, Ariana",
                "Formation",
                "planifié",
                5000.0,
                createur.getId()
        );

        es.addEntity(evenement2);
        System.out.println("Deuxième événement ajouté: " + evenement2.getTitre());

        // Lister les événements
        List<Evenement> evenements = es.getData();
        System.out.println("\nListe des événements (" + evenements.size() + "):");
        for (Evenement e : evenements) {
            System.out.println("- ID: " + e.getIdEvenement() +
                    " | Titre: " + e.getTitre() +
                    " (" + e.getType() +
                    ") | Date: " + e.getDateDebut().toLocalDate() +
                    " | Budget: " + e.getBudget() + " DT" +
                    " | Statut: " + e.getStatut());
        }

        // Modifier un événement
        if (!evenements.isEmpty()) {
            Evenement toUpdate = evenements.get(0);
            toUpdate.setBudget(25000.0);
            toUpdate.setStatut("confirmé");
            toUpdate.setLieu("Palais des Congrès, Tunis");
            es.updateEntity(toUpdate.getIdEvenement(), toUpdate);
            System.out.println("\nÉvénement modifié: " + toUpdate.getTitre() + " (Budget: " + toUpdate.getBudget() + " DT)");
        }

        // Rechercher par titre
        System.out.println("\nRecherche d'événements contenant 'Tech':");
        List<Evenement> evenementsRecherche = es.searchByTitre("Tech");
        for (Evenement e : evenementsRecherche) {
            System.out.println("- " + e.getTitre() + " (" + e.getLieu() + ")");
        }
    }

    private static void testParticipation() {
        System.out.println("\n=== TEST PARTICIPATION ===");
        ParticipationService ps = new ParticipationService();
        EvenementService es = new EvenementService();
        EmployeService empService = new EmployeService();

        // Récupérer des événements et des employés
        List<Evenement> evenements = es.getData();
        List<Employe> employes = empService.getData();

        if (evenements.isEmpty() || employes.size() < 2) {
            System.out.println("Données insuffisantes pour créer des participations");
            System.out.println("Événements: " + evenements.size() + ", Employés: " + employes.size());
            return;
        }

        // Créer des participations
        Evenement premierEvenement = evenements.get(0);

        // Participer les 3 premiers employés (en évitant le créateur si possible)
        int employesAjoutes = 0;
        for (int i = 0; i < Math.min(3, employes.size()); i++) {
            // Ne pas ajouter le créateur comme participant (s'il est le premier)
            if (i == 0 && employes.get(i).getId() == premierEvenement.getCreateurId()) {
                continue;
            }

            Participation participation = new Participation(
                    premierEvenement.getIdEvenement(),
                    employes.get(i).getId(),
                    "inscrit",
                    i == 0 ? "modérateur" : "participant"
            );

            ps.addEntity(participation);
            System.out.println("Participation ajoutée pour " + employes.get(i).getNom() + " " + employes.get(i).getPrenom());
            employesAjoutes++;
        }

        // Lister toutes les participations
        List<Participation> participations = ps.getData();
        System.out.println("\nListe des participations (" + participations.size() + "):");
        for (Participation p : participations) {
            System.out.println("- ID: " + p.getIdParticipation() +
                    " | Événement ID: " + p.getIdEvenement() +
                    " | Employé ID: " + p.getIdEmployee() +
                    " | Statut: " + p.getStatut() +
                    " | Rôle: " + p.getRole() +
                    " | Présent: " + (p.isPresent() ? "Oui" : "Non"));
        }

        // Modifier une participation (marquer comme présent)
        if (!participations.isEmpty()) {
            Participation toUpdate = participations.get(0);
            toUpdate.setPresent(true);
            toUpdate.setStatut("confirmé");
            ps.updateEntity(toUpdate.getIdParticipation(), toUpdate);
            System.out.println("\nParticipation modifiée: ID " + toUpdate.getIdParticipation() + " marquée comme présent");
        }

        // Voir les participants d'un événement spécifique
        if (!evenements.isEmpty()) {
            int idEvenement = premierEvenement.getIdEvenement();
            List<Participation> participantsEvenement = ps.getByEvenement(idEvenement);
            System.out.println("\nParticipants pour l'événement ID " + idEvenement + " (" + participantsEvenement.size() + "):");
            for (Participation p : participantsEvenement) {
                System.out.println("- Employé ID: " + p.getIdEmployee() +
                        " | Rôle: " + p.getRole() +
                        " | Statut: " + p.getStatut());
            }
        }
    }

    private static void testSponsoring() {
        System.out.println("\n=== TEST SPONSORING ===");
        SponsoringService ss = new SponsoringService();
        SponsorService sponsorService = new SponsorService();
        EvenementService es = new EvenementService();

        // Récupérer des sponsors et des événements
        List<Sponsor> sponsors = sponsorService.getData();
        List<Evenement> evenements = es.getData();

        if (sponsors.isEmpty() || evenements.isEmpty()) {
            System.out.println("Données insuffisantes pour créer un sponsoring");
            System.out.println("Sponsors: " + sponsors.size() + ", Événements: " + evenements.size());
            return;
        }

        // Créer plusieurs sponsorings
        int sponsoringCount = 0;
        for (int i = 0; i < Math.min(2, sponsors.size()); i++) {
            for (int j = 0; j < Math.min(2, evenements.size()); j++) {
                Sponsoring sponsoring = new Sponsoring(
                        sponsors.get(i).getIdSponsor(),
                        evenements.get(j).getIdEvenement(),
                        (i + 1) * 5000.0,  // Montant différent
                        LocalDate.now().plusDays(i),
                        i == 0 ? "Financière" : "Matérielle",
                        "Sponsoring " + (i + 1) + " pour l'événement " + (j + 1),
                        "actif"
                );

                ss.addEntity(sponsoring);
                System.out.println("Sponsoring ajouté: " +
                        sponsors.get(i).getNom() + " -> " + evenements.get(j).getTitre() +
                        " (" + sponsoring.getMontant() + " DT)");
                sponsoringCount++;
            }
        }

        // Lister tous les sponsorings
        List<Sponsoring> sponsorings = ss.getData();
        System.out.println("\nListe des sponsorings (" + sponsorings.size() + "):");
        for (Sponsoring s : sponsorings) {
            System.out.println("- ID: " + s.getIdSponsoring() +
                    " | Sponsor ID: " + s.getIdSponsor() +
                    " | Événement ID: " + s.getIdEvenement() +
                    " | Montant: " + s.getMontant() + " DT" +
                    " | Type: " + s.getTypeContribution() +
                    " | Statut: " + s.getStatut());
        }

        // Modifier un sponsoring
        if (!sponsorings.isEmpty()) {
            Sponsoring toUpdate = sponsorings.get(0);
            toUpdate.setMontant(15000.0);
            toUpdate.setStatut("finalisé");
            ss.updateEntity(toUpdate.getIdSponsoring(), toUpdate);
            System.out.println("\nSponsoring modifié: ID " + toUpdate.getIdSponsoring() +
                    " (Nouveau montant: " + toUpdate.getMontant() + " DT)");
        }

        // Voir les sponsors d'un événement spécifique
        if (!evenements.isEmpty()) {
            int idEvenement = evenements.get(0).getIdEvenement();
            List<Sponsoring> sponsorsEvenement = ss.getByEvenement(idEvenement);
            double total = ss.getTotalSponsorsByEvenement(idEvenement);

            System.out.println("\nSponsors pour l'événement ID " + idEvenement +
                    " (" + sponsorsEvenement.size() + " sponsors, total: " + total + " DT):");
            for (Sponsoring s : sponsorsEvenement) {
                System.out.println("- Sponsor ID: " + s.getIdSponsor() +
                        " | Montant: " + s.getMontant() + " DT" +
                        " | Type: " + s.getTypeContribution());
            }
        }
    }
}