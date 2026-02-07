package edu.project.entities;

import java.time.LocalDateTime;

public class evenement {
    private int idEvenement;
    private String titre;
    private String description;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String lieu;
    private String type;
    private String statut;
    private double budget;
    private int createur_id;
    private LocalDateTime dateCreation;

    // Constructeurs
    public evenement() {}

    public evenement(String titre, String description, LocalDateTime dateDebut, LocalDateTime dateFin,
                     String lieu, String type, String statut, double budget, int createur_id) {
        this.titre = titre;
        this.description = description;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.lieu = lieu;
        this.type = type;
        this.statut = statut;
        this.budget = budget;
        this.createur_id = createur_id;
        this.dateCreation = LocalDateTime.now();
    }

    // Getters et Setters
    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public int getCreateur_id() {
        return createur_id;
    }

    public void setCreateur_id(int createur_id) {
        this.createur_id = createur_id;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public String toString() {
        return "evenement{" +
                "idEvenement=" + idEvenement +
                ", titre='" + titre + '\'' +
                ", dateDebut=" + dateDebut +
                ", dateFin=" + dateFin +
                ", lieu='" + lieu + '\'' +
                ", type='" + type + '\'' +
                ", statut='" + statut + '\'' +
                ", budget=" + budget +
                '}';
    }
}