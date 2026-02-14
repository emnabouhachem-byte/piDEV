package edu.project.entities;

import javafx.beans.property.*;
import java.time.LocalDate;

public class Candidature {

    private IntegerProperty id = new SimpleIntegerProperty();
    private ObjectProperty<LocalDate> dateDepot = new SimpleObjectProperty<>();
    private StringProperty statut = new SimpleStringProperty();
    private StringProperty disponibilite = new SimpleStringProperty();
    private DoubleProperty score = new SimpleDoubleProperty();
    private StringProperty cv = new SimpleStringProperty();
    private IntegerProperty offreId = new SimpleIntegerProperty();

    public Candidature() {}

    public Candidature(int id, LocalDate dateDepot, String statut,
                       String disponibilite, double score,
                       String cv, int offreId) {
        this.id.set(id);
        this.dateDepot.set(dateDepot);
        this.statut.set(statut);
        this.disponibilite.set(disponibilite);
        this.score.set(score);
        this.cv.set(cv);
        this.offreId.set(offreId);
    }

    // Getters & Setters
    public int getId() { return id.get(); }
    public void setId(int id) { this.id.set(id); }
    public IntegerProperty idProperty() { return id; }

    public LocalDate getDateDepot() { return dateDepot.get(); }
    public void setDateDepot(LocalDate d) { this.dateDepot.set(d); }
    public ObjectProperty<LocalDate> dateDepotProperty() { return dateDepot; }

    public String getStatut() { return statut.get(); }
    public void setStatut(String s) { this.statut.set(s); }
    public StringProperty statutProperty() { return statut; }

    public String getDisponibilite() { return disponibilite.get(); }
    public void setDisponibilite(String d) { this.disponibilite.set(d); }
    public StringProperty disponibiliteProperty() { return disponibilite; }

    public double getScore() { return score.get(); }
    public void setScore(double s) { this.score.set(s); }
    public DoubleProperty scoreProperty() { return score; }

    public String getCv() { return cv.get(); }
    public void setCv(String c) { this.cv.set(c); }
    public StringProperty cvProperty() { return cv; }

    public int getOffreId() { return offreId.get(); }
    public void setOffreId(int id) { this.offreId.set(id); }
    public IntegerProperty offreIdProperty() { return offreId; }
}
