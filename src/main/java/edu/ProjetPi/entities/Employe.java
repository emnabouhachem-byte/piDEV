package edu.ProjetPi.entities;

import java.time.LocalDate;

public class Employe {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motdepasse;
    private String role;
    private String poste;
    private Double salaire;
    private LocalDate dateEmbauche;
    private String statut;
    private String competences;
    private String adresse;
    private Integer idEquipe;

    // Constructeurs
    public Employe() {}

    public Employe(int id, String nom, String prenom, String email, String motdepasse,
                   String role, String poste, Double salaire, LocalDate dateEmbauche,
                   String statut, String competences, String adresse, Integer idEquipe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motdepasse = motdepasse;
        this.role = role;
        this.poste = poste;
        this.salaire = salaire;
        this.dateEmbauche = dateEmbauche;
        this.statut = statut;
        this.competences = competences;
        this.adresse = adresse;
        this.idEquipe = idEquipe;
    }

    // Getters & Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotdepasse() { return motdepasse; }
    public void setMotdepasse(String motdepasse) { this.motdepasse = motdepasse; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPoste() { return poste; }
    public void setPoste(String poste) { this.poste = poste; }

    public Double getSalaire() { return salaire; }
    public void setSalaire(Double salaire) { this.salaire = salaire; }

    public LocalDate getDateEmbauche() { return dateEmbauche; }
    public void setDateEmbauche(LocalDate dateEmbauche) { this.dateEmbauche = dateEmbauche; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public String getCompetences() { return competences; }
    public void setCompetences(String competences) { this.competences = competences; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    public Integer getIdEquipe() { return idEquipe; }
    public void setIdEquipe(Integer idEquipe) { this.idEquipe = idEquipe; }

    @Override
    public String toString() {
        return nom + " " + prenom + " (" + poste + ")";
    }
}