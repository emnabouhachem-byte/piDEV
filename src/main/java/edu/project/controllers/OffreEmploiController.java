package edu.project.controllers;

import edu.project.entities.OffreEmploi;
import edu.project.services.OffreEmploiService;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.Node;

import java.time.LocalDate;
import java.util.List;

public class OffreEmploiController {

    @FXML private TextField txtTitre, txtTypeContrat, txtSalaire, txtRecherche;
    @FXML private TextArea txtDescription;
    @FXML private DatePicker dpPublication, dpExpiration;
    @FXML private FlowPane cardsContainer;

    // keep references for compatibility (some code may still use table)
    @FXML private TableView<OffreEmploi> table;

    private ObservableList<OffreEmploi> data = FXCollections.observableArrayList();
    private final OffreEmploiService service = new OffreEmploiService();
    private OffreEmploi selectedOffre;

    @FXML
    public void initialize() {
        loadCards();
    }

    private void loadCards() {
        try {
            cardsContainer.getChildren().clear();
            List<OffreEmploi> list = service.afficher();
            for (OffreEmploi o : list) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Card.fxml"));
                Node cardNode = loader.load();
                CardController cc = loader.getController();
                cc.setData(o);
                cc.setTitle(o.getTitre());
                cc.setMeta(o.getTypeContrat() + " • " + o.getSalaire());
                cc.setDescription(o.getDescription());
                cc.setActionHandler(new CardController.CardActionHandler() {
                    @Override public void onView(Object data) { showInfo("Détails", o.getDescription()); }
                    @Override public void onEdit(Object data) { remplirFormulaire(o); }
                    @Override public void onDelete(Object data) {
                        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Supprimer cette offre?", ButtonType.OK, ButtonType.CANCEL);
                        confirm.showAndWait().ifPresent(r -> { if (r == ButtonType.OK) {
                            service.supprimer(o.getId()); loadCards(); clearFields(); showInfo("Succès","Offre supprimée"); }
                        });
                    }
                });
                cardsContainer.getChildren().add(cardNode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void remplirFormulaire(OffreEmploi o) {
        selectedOffre = o;

        txtTitre.setText(o.getTitre());
        txtDescription.setText(o.getDescription());
        txtTypeContrat.setText(o.getTypeContrat());
        txtSalaire.setText(String.valueOf(o.getSalaire()));
        dpPublication.setValue(o.getDatePublication());
        dpExpiration.setValue(o.getDateExpiration());
    }

    @FXML
    private void ajouter() {

        if (!controleSaisie()) return;

        try {
            OffreEmploi o = new OffreEmploi(
                    txtTitre.getText().trim(),
                    txtDescription.getText().trim(),
                    txtTypeContrat.getText().trim(),
                    Double.parseDouble(txtSalaire.getText().trim()),
                    dpPublication.getValue(),
                    dpExpiration.getValue()
            );

            service.ajouter(o);
            loadCards();
            clearFields();

            showInfo("Succès", "Offre ajoutée avec succès !");
        } catch (Exception e) {
            showAlert("Erreur", "Erreur ajout : " + e.getMessage());
        }
    }

    @FXML
    private void modifier() {

        if (selectedOffre == null) {
            showAlert("Erreur", "Sélectionnez une offre !");
            return;
        }

        if (!controleSaisie()) return;

        try {
            selectedOffre.setTitre(txtTitre.getText().trim());
            selectedOffre.setDescription(txtDescription.getText().trim());
            selectedOffre.setTypeContrat(txtTypeContrat.getText().trim());
            selectedOffre.setSalaire(Double.parseDouble(txtSalaire.getText().trim()));
            selectedOffre.setDatePublication(dpPublication.getValue());
            selectedOffre.setDateExpiration(dpExpiration.getValue());

            service.modifier(selectedOffre);
            loadCards();
            clearFields();

            showInfo("Succès", "Offre modifiée avec succès !");
        } catch (Exception e) {
            showAlert("Erreur", "Erreur modification : " + e.getMessage());
        }
    }


    @FXML
    private void rechercher(KeyEvent event) {

        String mot = txtRecherche.getText().trim();

        if (mot.isEmpty()) {
            loadCards();
        } else {
            // search via service - to keep simple, load all then filter
            List<OffreEmploi> found = service.rechercher(mot);
            try {
                cardsContainer.getChildren().clear();
                for (OffreEmploi o : found) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Card.fxml"));
                    Node cardNode = loader.load();
                    CardController cc = loader.getController();
                    cc.setData(o);
                    cc.setTitle(o.getTitre());
                    cc.setMeta(o.getTypeContrat() + " • " + o.getSalaire());
                    cc.setDescription(o.getDescription());
                    cc.setActionHandler(new CardController.CardActionHandler() {
                        @Override public void onView(Object data) { showInfo("Détails", o.getDescription()); }
                        @Override public void onEdit(Object data) { remplirFormulaire(o); }
                        @Override public void onDelete(Object data) {
                            service.supprimer(o.getId()); loadCards(); clearFields(); showInfo("Succès","Offre supprimée");
                        }
                    });
                    cardsContainer.getChildren().add(cardNode);
                }
            } catch (Exception e) { e.printStackTrace(); }
        }
    }

    private boolean controleSaisie() {

        if (txtTitre.getText().isEmpty() ||
                txtDescription.getText().isEmpty() ||
                txtTypeContrat.getText().isEmpty() ||
                txtSalaire.getText().isEmpty() ||
                dpPublication.getValue() == null ||
                dpExpiration.getValue() == null) {

            showAlert("Erreur", "Tous les champs sont obligatoires !");
            return false;
        }

        try {
            Double.parseDouble(txtSalaire.getText());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Salaire doit être un nombre !");
            return false;
        }

        if (dpExpiration.getValue().isBefore(dpPublication.getValue())) {
            showAlert("Erreur", "Date expiration doit être après publication !");
            return false;
        }

        return true;
    }

    private void clearFields() {
        txtTitre.clear();
        txtDescription.clear();
        txtTypeContrat.clear();
        txtSalaire.clear();
        dpPublication.setValue(null);
        dpExpiration.setValue(null);
        if (table != null) table.getSelectionModel().clearSelection();
        selectedOffre = null;
    }

    private void showAlert(String titre, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titre);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }

    private void showInfo(String titre, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titre);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
