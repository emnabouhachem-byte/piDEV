package edu.ProjetPi.entities;

import java.time.LocalDate;

public class Sponsoring {
    private int idSponsoring;
    private int idSponsor;
    private int idEvenement;
    private double montant;
    private LocalDate dateContribution;
    private String typeContribution;
    private String details;
    private String statut;

    // Constructeurs
    public Sponsoring() {}

    public Sponsoring(int idSponsor, int idEvenement, double montant,
                      LocalDate dateContribution, String typeContribution,
                      String details, String statut) {
        this.idSponsor = idSponsor;
        this.idEvenement = idEvenement;
        this.montant = montant;
        this.dateContribution = dateContribution;
        this.typeContribution = typeContribution;
        this.details = details;
        this.statut = statut;
    }

    // Getters & Setters
    public int getIdSponsoring() { return idSponsoring; }
    public void setIdSponsoring(int idSponsoring) { this.idSponsoring = idSponsoring; }

    public int getIdSponsor() { return idSponsor; }
    public void setIdSponsor(int idSponsor) { this.idSponsor = idSponsor; }

    public int getIdEvenement() { return idEvenement; }
    public void setIdEvenement(int idEvenement) { this.idEvenement = idEvenement; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public LocalDate getDateContribution() { return dateContribution; }
    public void setDateContribution(LocalDate dateContribution) { this.dateContribution = dateContribution; }

    public String getTypeContribution() { return typeContribution; }
    public void setTypeContribution(String typeContribution) { this.typeContribution = typeContribution; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    @Override
    public String toString() {
        return "Sponsoring [id=" + idSponsoring + ", montant=" + montant + ", type=" + typeContribution + "]";
    }
}