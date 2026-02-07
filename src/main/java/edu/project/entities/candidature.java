package edu.project.entities;

import java.time.LocalDate;

public class candidature {
    private int id;
    private LocalDate dateDepot;
    private String statut;
    private String disponibilite;
    private Double score;
    private String cv;
    private int offreId;

    public candidature() {}

    public candidature(LocalDate dateDepot, String statut, String disponibilite,
                       Double score, String cv, int offreId) {
        this.dateDepot = dateDepot;
        this.statut = statut;
        this.disponibilite = disponibilite;
        this.score = score;
        this.cv = cv;
        this.offreId = offreId;
    }

    // getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getDateDepot() { return dateDepot; }
    public void setDateDepot(LocalDate dateDepot) { this.dateDepot = dateDepot; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getDisponibilite() { return disponibilite; }
    public void setDisponibilite(String disponibilite) { this.disponibilite = disponibilite; }

    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }

    public String getCv() { return cv; }
    public void setCv(String cv) { this.cv = cv; }

    public int getOffreId() { return offreId; }
    public void setOffreId(int offreId) { this.offreId = offreId; }

    @Override
    public String toString() {
        return "Candidature{id=" + id +
                ", dateDepot=" + dateDepot +
                ", statut='" + statut + '\'' +
                ", score=" + score +
                ", offreId=" + offreId +
                '}';
    }
}
