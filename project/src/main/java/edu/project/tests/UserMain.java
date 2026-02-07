package edu.project.tests;

import edu.project.entities.Role;
import edu.project.entities.Utilisateur;
import edu.project.services.EquipeService;
import edu.project.services.ProjetService;
import edu.project.services.UtilisateurService;

import java.sql.SQLException;

public class UserMain {

    public static void main(String[] args) throws SQLException {

        UtilisateurService us = new UtilisateurService();
        EquipeService es = new EquipeService();
        ProjetService ps = new ProjetService();


         try {
           // ================== AJOUT ADMIN ==================
            Utilisateur admin = new Utilisateur(
                    "mina",
                    "tounsi",
                    "mina@gmail.com",
                    "5555",
                    Role.ResponsableRh
            );

            us.addEntity(admin);

          /*  // ================== AJOUT EMPLOYE ==================
            Utilisateur emp = new Utilisateur(
                    "Ali",
                    "Ben Ali",
                    "ali@mail.com",
                    "12345",
                    Role.EMPLOYE
            );

            emp.setPoste("Technicien");
            emp.setSalaire(1200.0);
            emp.setDateEmbauche(LocalDate.of(2024, 1, 1));
            emp.setStatut("Actif");
            emp.setCompetences("Java, SQL");
            emp.setAdresse("Tunis");
            emp.setIdEquipe(1);

            us.addEntity(emp);

            // ================== UPDATE ==================
            int idToUpdate = 2;

            Utilisateur uUpdated = new Utilisateur(
                    "Amine",
                    "Ben Ali",
                    "amine@gmail.com",
                    "9999",
                    Role.ADMIN
            );

            uUpdated.setPoste("IT");
            uUpdated.setSalaire(2000.0);
            uUpdated.setStatut("Actif");

            us.updateEntity(idToUpdate, uUpdated);
            */

           System.out.println("------ LISTE UTILISATEURS ------");
             for (Utilisateur u : us.getData()) {
                 System.out.print(u.getId() + " | " +
                         u.getNom() + " " + u.getPrenom() + " | " +
                         u.getRole());

                 if (u.getRole() == Role.EMPLOYE) { // affichage spécifique EMPLOYE
                     System.out.print(" | Poste: " + u.getPoste() +
                             " | Salaire: " + u.getSalaire() +
                             " | Date Embauche: " + u.getDateEmbauche() +
                             " | Statut: " + u.getStatut() +
                             " | Competences: " + u.getCompetences() +
                             " | Adresse: " + u.getAdresse() +
                             " | IdEquipe: " + u.getIdEquipe());
                 }
                 System.out.println();
             }

            // ================== DELETE ==================
          //  Utilisateur uDelete = new Utilisateur();
          //  uDelete.setId(25);
           // us.deleteEntity(uDelete);

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }



        // ajouter employee
          /* try {

            Utilisateur emp1 = new Utilisateur();
            emp1.setNom("emna");
            emp1.setPrenom("B'h");
            emp1.setEmail("emna@example.com");
            emp1.setMotDePasse("555");
            emp1.setRole(Role.EMPLOYE);
            emp1.setPoste("Développeur");
            emp1.setSalaire(9000.0);
            emp1.setDateEmbauche(LocalDate.now());
            emp1.setStatut("Actif");
            emp1.setCompetences("Java, SQL");
            emp1.setAdresse("Tunis");

            us.addEntity(emp1);

            Utilisateur emp2 = new Utilisateur();
            emp2.setNom("Salma");
            emp2.setPrenom("Ben Sarra");
            emp2.setEmail("salma@example.com");
            emp2.setMotDePasse("12300");
            emp2.setRole(Role.EMPLOYE);
            emp2.setPoste("Designer");
            emp2.setSalaire(1100.0);
            emp2.setDateEmbauche(LocalDate.now());
            emp2.setStatut("Actif");
            emp2.setCompetences("Photoshop, UI/UX");
            emp2.setAdresse("Ariana");

            us.addEntity(emp2);




            //ajouter equipe avec employes
            // ====== 2. Récupérer les utilisateurs déjà existants ======
            List<Utilisateur> allUsers = us.getData();

            // Filtrer pour garder uniquement les employés
            List<Utilisateur> employees = new ArrayList<>();
            for (Utilisateur u : allUsers) {
                if (u.getRole() == Role.EMPLOYE) {
                    employees.add(u);
                }
            }

            if (employees.size() < 2) {
                System.out.println("Veuillez vous assurer qu'il y a au moins deux employés dans la base de données.");
                return;
            }

            // Choisir les deux premiers employés à ajouter à l'équipe
            Utilisateur e1 = employees.get(2);
            Utilisateur e2 = employees.get(3);

            // ====== 3. Créer une nouvelle équipe ======
            List<Utilisateur> listeEmp = new ArrayList<>();
            listeEmp.add(e1);
            listeEmp.add(e2);

            Equipe equipe = new Equipe();
            equipe.setNom("Equipe Dev");
            equipe.setDescription("Equipe pour projets Java");
            equipe.setDateCreation(LocalDate.now());
            equipe.setNbr_membre(listeEmp.size());
            equipe.setBudget(5000.0);
            equipe.setListeEmployes(listeEmp);

            // ====== 4. Ajouter l'équipe et lier les employés ======
            es.addEntity(equipe);

            System.out.println("L'équipe a été ajoutée et les employés ont été liés avec succès ");

            // ====== 5. Afficher toutes les équipes avec leurs employés ======
            List<Equipe> allEquipes = es.getData();
            for (Equipe e : allEquipes) {
                System.out.println("Equipe: " + e.getNom() + ", Budget: " + e.getBudget());
                System.out.println("Membres:");
                for (Utilisateur u : e.getListeEmployes()) {
                    System.out.println(" - " + u.getNom() + " " + u.getPrenom() + " | Poste: " + u.getPoste());
                }
                System.out.println("---------------------");
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
        }
   */





          /*    // suppriemr equipe
            try {
                // ====== 1. Récupérer toutes les équipes ======
                List<Equipe> allEquipes = es.getData();

                if (allEquipes.isEmpty()) {
                    System.out.println("Il n'y a aucune équipe dans la base de données.");
                    return;
                }

                // ====== 2. Choisir l'équipe à supprimer ======
                // Ici on prend la première équipe, mais tu peux changer la logique
                Equipe equipeASupprimer = allEquipes.get(0);

                System.out.println("Suppression de l'équipe : " + equipeASupprimer.getNom());

                // ====== 3. Supprimer l'équipe ======
                es.deleteEntity(equipeASupprimer);
                System.out.println("L'équipe '" + equipeASupprimer.getNom() + "' a été supprimée avec succès 🗑️");

                // ====== 4. Vérifier après suppression ======
                allEquipes = es.getData();
                System.out.println("Liste des équipes après suppression:");
                for (Equipe e : allEquipes) {
                    System.out.println("Equipe: " + e.getNom() + ", Budget: " + e.getBudget());
                    System.out.println("Membres:");
                    for (Utilisateur u : e.getListeEmployes()) {
                        System.out.println(" - " + u.getNom() + " " + u.getPrenom() + " | Poste: " + u.getPoste());
                    }
                    System.out.println("---------------------");
                }

            } catch (SQLException e) {
                System.out.println("Erreur SQL : " + e.getMessage());
                e.printStackTrace();
            }

           */




        /* try {
            // ====== 1. Récupérer tous les employés ======
            List<Utilisateur> allUsers = us.getData();
            List<Utilisateur> employees = new ArrayList<>();
            for (Utilisateur u : allUsers) {
                if (u.getRole() == Role.EMPLOYE) {
                    employees.add(u);
                }
            }

            // ====== 2. Récupérer toutes les équipes ======
            List<Equipe> allEquipes = es.getData();
            if (allEquipes.isEmpty()) {
                System.out.println("Aucune équipe trouvée pour modification.");
                return;
            }

            // ====== 3. Choisir l'équipe à modifier ======
            Equipe equipeToUpdate = allEquipes.get(0); // par exemple, le premier

            // ====== 4. Modifier les informations de l'équipe ======
            equipeToUpdate.setNom("Equipe Web");
            equipeToUpdate.setBudget(10000.0);

            // ====== 5. Modifier la liste des employés de l'équipe ======
            equipeToUpdate.setListeEmployes(employees);
            equipeToUpdate.setNbr_membre(employees.size());

            // ====== 6. Appliquer la modification ======
            es.updateEntity(equipeToUpdate.getId(), equipeToUpdate);

            System.out.println("Equipe modifiée avec succès ");

        } catch (SQLException e) {
            System.out.println("Erreur SQL : " + e.getMessage());
            e.printStackTrace();
        } */

         /*   try {
                // ====== 1. Récupérer les équipes existantes ======
                List<Equipe> allEquipes = es.getData();

                if (allEquipes.isEmpty()) {
                    System.out.println("Veuillez ajouter des équipes dans la base de données avant de créer un projet.");
                    return;
                }

                // ====== 2. Créer un projet et lier le premier équipe ======
                Equipe equipeChoisie = allEquipes.get(0);

                Projet projet = new Projet();
                projet.setTitre("Projet Mobile");
                projet.setDescription("Développement de la plateforme ");
                projet.setDateDebut(LocalDate.of(2026, 3, 1));
                projet.setDateFin(LocalDate.of(2026, 6, 30));
                projet.setBudget(100000);
                projet.setId_equipe(equipeChoisie.getId());

                ps.addEntity(projet);
                System.out.println("Projet ajouté avec succès ");


                // ====== 3. Afficher tous les projets avec leur équipe ======
                List<Projet> allProjets = ps.getData();
                for (Projet p : allProjets) {
                    System.out.println("Projet: " + p.getTitre() + ", Budget: " + p.getBudget());
                    if (p.getEquipes() != null && !p.getEquipes().isEmpty()) {
                        Equipe e = p.getEquipes().get(0);
                        System.out.println("Équipe: " + e.getNom() + " | Budget: " + e.getBudget());
                    }
                    System.out.println("---------------------");
                }
            }catch (Exception e) {
                    e.printStackTrace();
                }
            */

             /*
                // ====== 4. Modifier le premier projet ======
                if (!allProjets.isEmpty()) {
                    Projet projetAModifier = allProjets.get(0);
                    projetAModifier.setTitre("Projet SmartCity"); // Nouveau titre
                    projetAModifier.setBudget(18000); // Nouveau budget

                    ps.updateEntity(projetAModifier.getId(), projetAModifier);
                    System.out.println("Projet modifié ");
                }

                // ====== 5. Afficher à nouveau les projets pour vérifier la modification ======
                allProjets = ps.getData();
                for (Projet p : allProjets) {
                    System.out.println("Projet: " + p.getTitre() + ", Budget: " + p.getBudget());
                    if (p.getEquipes() != null && !p.getEquipes().isEmpty()) {
                        for (Equipe e : p.getEquipes()) {
                            System.out.println(" - Équipe: " + e.getNom() + " | Budget: " + e.getBudget());
                        }
                    }
                    System.out.println("---------------------");
                } */

             /*   // ====== 6. Supprimer un projet (optionnel) ======
                List<Projet> allProjets = ps.getData();
                ps.deleteEntity(allProjets.get(0));
                 System.out.println("Projet supprimé ");

            } catch (Exception e) {
                e.printStackTrace();
            }
            */







    }

}
