package edu.project.tests;

import edu.project.entities.evenement;
import edu.project.entities.Participation;
import edu.project.services.EvenementService;
import edu.project.services.ParticipationService;
import java.time.LocalDateTime;

public class MainClass {
    public static void main(String[] args) {
        System.out.println("=== TEST DES SERVICES ÉVÉNEMENTS ET PARTICIPATIONS ===\n");

        // Initialisation des services
        EvenementService evenementService = new EvenementService();
        ParticipationService participationService = new ParticipationService();

        // Test 1: Récupérer tous les événements
        System.out.println("1. Liste de tous les événements :");
        System.out.println("Nombre d'événements : " + evenementService.getData().size());

        // Test 2: Ajouter un nouvel événement
        System.out.println("\n2. Ajout d'un nouvel événement :");
        evenement nouveauEvent = new evenement(
                "Formation Java Avancé",
                "Formation sur les concepts avancés de Java",
                LocalDateTime.now().plusDays(7),
                LocalDateTime.now().plusDays(7).plusHours(8),
                "Salle de formation 3",
                "formation",
                "planifié",
                3000.0,
                8  // ID du créateur (employé)
        );

        // Décommentez pour tester l'ajout
        // evenementService.addEntity(nouveauEvent);

        // Test 3: Rechercher des événements
        System.out.println("\n3. Recherche d'événements par titre :");
        System.out.println("Résultats pour 'Formation' : " +
                evenementService.searchByTitre("Formation").size() + " événement(s)");

        // Test 4: Gestion des participations
        System.out.println("\n4. Tests des participations :");

        // Créer une participation
        Participation participation = new Participation(1, 8, "inscrit", "participant");

        // Vérifier si l'employé participe déjà
        boolean dejaInscrit = participationService.isAlreadyParticipating(1, 8);
        System.out.println("L'employé 8 participe-t-il à l'événement 1 ? " + dejaInscrit);

        if (!dejaInscrit) {
            // Décommentez pour tester l'ajout
            // participationService.addEntity(participation);
            // System.out.println("Participation ajoutée !");
        }

        // Test 5: Récupérer les participations par événement
        System.out.println("\n5. Participations pour l'événement 1 :");
        System.out.println("Nombre : " + participationService.getByEvenement(1).size());

        // Test 6: Récupérer les participations par employé
        System.out.println("\n6. Participations pour l'employé 8 :");
        System.out.println("Nombre : " + participationService.getByEmploye(8).size());

        System.out.println("\n=== TESTS TERMINÉS ===");
    }
}