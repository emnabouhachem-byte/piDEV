package edu.project.entities;

import java.time.LocalDate;

public class offreEmploi {
    private int id;
    private String titre;
    private String description;
    private String typeContrat;
    private Double salaire;
    private LocalDate datePublication;
    private LocalDate dateExpiration;

    public offreEmploi() {}

    public offreEmploi(String titre, String description, String typeContrat, Double salaire,
                       LocalDate datePublication, LocalDate dateExpiration) {
        this.titre = titre;
        this.description = description;
        this.typeContrat = typeContrat;
        this.salaire = salaire;
        this.datePublication = datePublication;
        this.dateExpiration = dateExpiration;
    }

    // getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTypeContrat() { return typeContrat; }
    public void setTypeContrat(String typeContrat) { this.typeContrat = typeContrat; }

    public Double getSalaire() { return salaire; }
    public void setSalaire(Double salaire) { this.salaire = salaire; }

    public LocalDate getDatePublication() { return datePublication; }
    public void setDatePublication(LocalDate datePublication) { this.datePublication = datePublication; }

    public LocalDate getDateExpiration() { return dateExpiration; }
    public void setDateExpiration(LocalDate dateExpiration) { this.dateExpiration = dateExpiration; }

    @Override
    public String toString() {
        return "Offre{id=" + id +
                ", titre='" + titre + '\'' +
                ", typeContrat='" + typeContrat + '\'' +
                ", salaire=" + salaire +
                ", datePublication=" + datePublication +
                ", dateExpiration=" + dateExpiration +
                '}';
    }
}
