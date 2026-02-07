package edu.project.entities;

import java.time.LocalDate;
import java.util.List;
import edu.project.entities.Utilisateur;

public class Equipe {
    private int id;
    private String nom;
    private String description;
    private LocalDate dateCreation;
    private int nbr_membre;
    private double budget;

    private List<Utilisateur> listeEmployes;

    public Equipe() {
    }

    public Equipe(int id, String nom, String description, LocalDate dateCreation, int nbr_membre, double budget, List<Utilisateur> listeEmployes) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.dateCreation = dateCreation;
        this.nbr_membre = nbr_membre;
        this.budget = budget;
        this.listeEmployes = listeEmployes;
    }

    public Equipe(String nom, String description, LocalDate dateCreation, int nbr_membre, double budget, List<Utilisateur> listeEmployes) {
        this.nom = nom;
        this.description = description;
        this.dateCreation = dateCreation;
        this.nbr_membre = nbr_membre;
        this.budget = budget;
        this.listeEmployes = listeEmployes;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getDateCreation() { return dateCreation; }
    public void setDateCreation(LocalDate dateCreation) { this.dateCreation = dateCreation; }

    public int getNbr_membre() { return nbr_membre; }
    public void setNbr_membre(int nbr_membre) { this.nbr_membre = nbr_membre; }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public List<Utilisateur> getListeEmployes() { return listeEmployes; }
    public void setListeEmployes(List<Utilisateur> listeEmployes) { this.listeEmployes = listeEmployes; }
}
