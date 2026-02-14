package edu.project.entities;

import java.time.LocalDate;

public class OffreEmploi {

    private int id;
    private String titre;
    private String description;
    private String typeContrat;
    private double salaire;
    private LocalDate datePublication;
    private LocalDate dateExpiration;

    public OffreEmploi() {
    }

    public OffreEmploi(String titre, String description, String typeContrat,
                       double salaire, LocalDate datePublication, LocalDate dateExpiration) {
        this.titre = titre;
        this.description = description;
        this.typeContrat = typeContrat;
        this.salaire = salaire;
        this.datePublication = datePublication;
        this.dateExpiration = dateExpiration;
    }

    public OffreEmploi(int id, String titre, String description, String typeContrat,
                       double salaire, LocalDate datePublication, LocalDate dateExpiration) {
        this.id = id;
        this.titre = titre;
        this.description = description;
        this.typeContrat = typeContrat;
        this.salaire = salaire;
        this.datePublication = datePublication;
        this.dateExpiration = dateExpiration;
    }

    // Getters & Setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTypeContrat() { return typeContrat; }
    public void setTypeContrat(String typeContrat) { this.typeContrat = typeContrat; }

    public double getSalaire() { return salaire; }
    public void setSalaire(double salaire) { this.salaire = salaire; }

    public LocalDate getDatePublication() { return datePublication; }
    public void setDatePublication(LocalDate datePublication) { this.datePublication = datePublication; }

    public LocalDate getDateExpiration() { return dateExpiration; }
    public void setDateExpiration(LocalDate dateExpiration) { this.dateExpiration = dateExpiration; }

    @Override
    public String toString() {
        return titre;
    }
}
