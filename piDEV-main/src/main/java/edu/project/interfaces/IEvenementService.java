package edu.project.interfaces;

import edu.project.entities.evenement;
import java.util.List;

public interface IEvenementService extends IService<evenement> {
    // Seulement les méthodes essentielles
    evenement getById(int id);
    List<evenement> getByCreateur(int createurId);
    List<evenement> searchByTitre(String titre);
}