package edu.project.interfaces;

import edu.project.entities.Participation;
import java.util.List;

public interface IParticipationService extends IService<Participation> {
    // Méthodes spécifiques
    Participation getById(int id);
    List<Participation> getByEvenement(int idEvenement);
    List<Participation> getByEmploye(int idEmploye);
    boolean isAlreadyParticipating(int idEvenement, int idEmploye);
}