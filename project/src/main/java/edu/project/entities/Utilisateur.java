package edu.project.entities;

import java.time.LocalDate;
import java.util.Objects;

public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private Role role;

    private String poste;
    private Double salaire;
    private LocalDate dateEmbauche;
    private String statut;
    private String competences;
    private String adresse;
    private Integer idEquipe;


    public Utilisateur() {
    }

    public Utilisateur(String nom, String prenom, String email, String motDePasse, Role role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;

        // champs optionnels
        this.poste = null;
        this.salaire = null;
        this.dateEmbauche = null;
        this.statut = null;
        this.competences = null;
        this.adresse = null;
        this.idEquipe = null;
    }


    public Utilisateur(int id, String nom, String prenom, String email, String motDePasse, Role role, String poste, Double salaire, LocalDate dateEmbauche, String statut, String competences, String adresse, Integer idEquipe) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
        this.poste = poste;
        this.salaire = salaire;
        this.dateEmbauche = dateEmbauche;
        this.statut = statut;
        this.competences = competences;
        this.adresse = adresse;
        this.idEquipe = idEquipe;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPoste() {
        return poste;
    }

    public void setPoste(String poste) {
        this.poste = poste;
    }

    public Double getSalaire() {
        return salaire;
    }

    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getCompetences() {
        return competences;
    }

    public void setCompetences(String competences) {
        this.competences = competences;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Integer getIdEquipe() {
        return idEquipe;
    }

    public void setIdEquipe(Integer  idEquipe) {
        this.idEquipe = idEquipe;
    }

    public Utilisateur(String nom, String prenom, String email, String motDePasse, Role role, String poste, Double salaire, LocalDate dateEmbauche, String statut, String competences, String adresse, Integer idEquipe) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.motDePasse = motDePasse;
        this.role = role;
        this.poste = poste;
        this.salaire = salaire;
        this.dateEmbauche = dateEmbauche;
        this.statut = statut;
        this.competences = competences;
        this.adresse = adresse;
        this.idEquipe = idEquipe;
    }

    @Override
    public String toString() {
        return "Utilisateur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", motDePasse='" + motDePasse + '\'' +
                ", role=" + role +
                ", poste='" + poste + '\'' +
                ", salaire=" + salaire +
                ", dateEmbauche=" + dateEmbauche +
                ", statut='" + statut + '\'' +
                ", competences='" + competences + '\'' +
                ", adresse='" + adresse + '\'' +
                ", idEquipe=" + idEquipe +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return id == that.id ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, email, motDePasse, role);
    }
}

