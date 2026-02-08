package edu.ProjetPi.entities;

import java.time.LocalDateTime;

public class Participation {
    private int idParticipation;
    private int idEvenement;
    private int idEmployee;
    private LocalDateTime dateInscription;
    private String statut;
    private String role;
    private boolean present;

    // Constructeurs
    public Participation() {}

    public Participation(int idEvenement, int idEmployee, String statut, String role) {
        this.idEvenement = idEvenement;
        this.idEmployee = idEmployee;
        this.statut = statut;
        this.role = role;
        this.present = false;
    }

    // Getters & Setters
    public int getIdParticipation() { return idParticipation; }
    public void setIdParticipation(int idParticipation) { this.idParticipation = idParticipation; }

    public int getIdEvenement() { return idEvenement; }
    public void setIdEvenement(int idEvenement) { this.idEvenement = idEvenement; }

    public int getIdEmployee() { return idEmployee; }
    public void setIdEmployee(int idEmployee) { this.idEmployee = idEmployee; }

    public LocalDateTime getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDateTime dateInscription) { this.dateInscription = dateInscription; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public boolean isPresent() { return present; }
    public void setPresent(boolean present) { this.present = present; }

    @Override
    public String toString() {
        return "Participation [id=" + idParticipation + ", statut=" + statut + ", role=" + role + "]";
    }
}