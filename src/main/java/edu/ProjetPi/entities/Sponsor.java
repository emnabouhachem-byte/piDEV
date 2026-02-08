package edu.ProjetPi.entities;

import java.time.LocalDate;

public class Sponsor {
    private int idSponsor;
    private String nom;
    private String typeSponsor;
    private String email;
    private String telephone;
    private String adresse;
    private double budgetAlloue;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private String statut;
    private String notes;

    // Constructeurs
    public Sponsor() {}

    public Sponsor(String nom, String typeSponsor, String email, String telephone,
                   String adresse, double budgetAlloue, LocalDate dateDebut,
                   LocalDate dateFin, String statut, String notes) {
        this.nom = nom;
        this.typeSponsor = typeSponsor;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.budgetAlloue = budgetAlloue;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
        this.notes = notes;
    }

    // Constructeur avec id
    public Sponsor(int idSponsor, String nom, String typeSponsor, String email, String telephone,
                   String adresse, double budgetAlloue, LocalDate dateDebut,
                   LocalDate dateFin, String statut, String notes) {
        this.idSponsor = idSponsor;
        this.nom = nom;
        this.typeSponsor = typeSponsor;
        this.email = email;
        this.telephone = telephone;
        this.adresse = adresse;
        this.budgetAlloue = budgetAlloue;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.statut = statut;
        this.notes = notes;
    }

    // Getters & Setters
    public int getIdSponsor() { return idSponsor; }
    public void setIdSponsor(int idSponsor) { this.idSponsor = idSponsor; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getTypeSponsor() { return typeSponsor; }
    public void setTypeSponsor(String typeSponsor) { this.typeSponsor = typeSponsor; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public double getBudgetAlloue() { return budgetAlloue; }
    public void setBudgetAlloue(double budgetAlloue) { this.budgetAlloue = budgetAlloue; }

    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }

    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    @Override
    public String toString() {
        return nom;
    }
}