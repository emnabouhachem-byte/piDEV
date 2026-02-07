package edu.project.entities;

import java.time.LocalDateTime;

public class Participation {
    private int idParticipation;
    private int idEvenement;
    private int idEmploye;
    private LocalDateTime dateInscription;
    private String statut;
    private String role;
    private boolean present;

    // Constructeurs
    public Participation() {}

    public Participation(int idEvenement, int idEmploye, String statut, String role) {
        this.idEvenement = idEvenement;
        this.idEmploye = idEmploye;
        this.statut = statut;
        this.role = role;
        this.dateInscription = LocalDateTime.now();
        this.present = false;
    }

    // Getters et Setters
    public int getIdParticipation() {
        return idParticipation;
    }

    public void setIdParticipation(int idParticipation) {
        this.idParticipation = idParticipation;
    }

    public int getIdEvenement() {
        return idEvenement;
    }

    public void setIdEvenement(int idEvenement) {
        this.idEvenement = idEvenement;
    }

    public int getIdEmploye() {
        return idEmploye;
    }

    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    public LocalDateTime getDateInscription() {
        return dateInscription;
    }

    public void setDateInscription(LocalDateTime dateInscription) {
        this.dateInscription = dateInscription;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    @Override
    public String toString() {
        return "Participation{" +
                "idParticipation=" + idParticipation +
                ", idEvenement=" + idEvenement +
                ", idEmploye=" + idEmploye +
                ", dateInscription=" + dateInscription +
                ", statut='" + statut + '\'' +
                ", role='" + role + '\'' +
                ", present=" + present +
                '}';
    }
}