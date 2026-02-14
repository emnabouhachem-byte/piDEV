package edu.project.controllers;

import edu.project.entities.Candidature;
import edu.project.services.CandidatureService;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.scene.Node;

import java.awt.Desktop;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

public class CandidatureController {

    @FXML private DatePicker dpDepot;
    @FXML private TextField txtStatut, txtDisponibilite, txtScore, txtCv, txtOffreId, txtRecherche;
    @FXML private Button btnUpload;

    @FXML private FlowPane cardsContainer;

    @FXML private TableView<Candidature> table;

    private ObservableList<Candidature> data = FXCollections.observableArrayList();
    private final CandidatureService service = new CandidatureService();
    private Candidature selected;
    private File selectedCvFile; // holds the chosen file until stored

    @FXML
    public void initialize() {
        loadCards();
    }

    private void loadCards() {
        try {
            cardsContainer.getChildren().clear();
            List<Candidature> list = service.afficher();
            for (Candidature c : list) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Card.fxml"));
                Node cardNode = loader.load();
                CardController cc = loader.getController();
                cc.setData(c);
                cc.setTitle("Candidature #" + c.getId());
                cc.setMeta(c.getStatut() + " • " + c.getDisponibilite());
                cc.setDescription("Score: " + c.getScore() + "\nOffre: " + c.getOffreId());
                cc.setActionHandler(new CardController.CardActionHandler() {
                    @Override public void onView(Object data) {
                        // attempt to open CV if exists
                        String path = c.getCv();
                        if (path != null && !path.isEmpty()) {
                            try { Desktop.getDesktop().open(new File(path)); } catch (Exception ex) { showAlert("Erreur","Impossible d'ouvrir le CV: " + ex.getMessage()); }
                        } else showInfo("Info","Aucun CV disponible.");
                    }
                    @Override public void onEdit(Object data) { remplirFormulaire(c); }
                    @Override public void onDelete(Object data) {
                        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Supprimer cette candidature?", ButtonType.OK, ButtonType.CANCEL);
                        confirm.showAndWait().ifPresent(r -> { if (r == ButtonType.OK) { service.supprimer(c.getId()); loadCards(); clear(); showInfo("Succès","Candidature supprimée"); } });
                    }
                });
                cardsContainer.getChildren().add(cardNode);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private void remplirFormulaire(Candidature c) {
        selected = c;
        dpDepot.setValue(c.getDateDepot());
        txtStatut.setText(c.getStatut());
        txtDisponibilite.setText(c.getDisponibilite());
        txtScore.setText(String.valueOf(c.getScore()));
        txtCv.setText(c.getCv());
        txtOffreId.setText(String.valueOf(c.getOffreId()));
        selectedCvFile = null; // reset: existing CV path is shown but file object not set
    }

    @FXML
    private void chooseCv() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choisir un CV");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PDF", "*.pdf"),
                new FileChooser.ExtensionFilter("Word", "*.doc", "*.docx"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        File f = chooser.showOpenDialog(null);
        if (f != null) {
            txtCv.setText(f.getAbsolutePath());
            selectedCvFile = f;
        }
    }

    @FXML
    private void ajouter() {
        if (!controle()) return;
        try {
            String storedPath = txtCv.getText().trim();
            if (selectedCvFile != null) {
                String rel = service.storeCvFile(selectedCvFile);
                if (rel != null) storedPath = rel;
            }

            Candidature c = new Candidature(
                    0,
                    dpDepot.getValue(),
                    txtStatut.getText().trim(),
                    txtDisponibilite.getText().trim(),
                    Double.parseDouble(txtScore.getText().trim()),
                    storedPath,
                    Integer.parseInt(txtOffreId.getText().trim())
            );
            service.ajouter(c);
            loadCards();
            clear();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible d'ajouter la candidature : " + e.getMessage());
        }
    }

    @FXML
    private void modifier() {
        Candidature c = selected; // use selected from remplirFormulaire
        if (c == null) { showAlert("Erreur", "Veuillez sélectionner une candidature."); return; }
        if (!controle()) return;

        try {
            String storedPath = txtCv.getText().trim();
            if (selectedCvFile != null) {
                String rel = service.storeCvFile(selectedCvFile);
                if (rel != null) storedPath = rel;
            }

            c.setDateDepot(dpDepot.getValue());
            c.setStatut(txtStatut.getText().trim());
            c.setDisponibilite(txtDisponibilite.getText().trim());
            c.setScore(Double.parseDouble(txtScore.getText().trim()));
            c.setCv(storedPath);
            c.setOffreId(Integer.parseInt(txtOffreId.getText().trim()));

            service.modifier(c);
            loadCards();
            clear();
        } catch (Exception e) {
            showAlert("Erreur", "Impossible de modifier : " + e.getMessage());
        }
    }



    @FXML
    private void rechercher(KeyEvent event) {
        String mot = txtRecherche.getText().trim();
        try {
            cardsContainer.getChildren().clear();
            List<Candidature> found = service.rechercher(mot);
            for (Candidature c : found) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Card.fxml"));
                Node cardNode = loader.load();
                CardController cc = loader.getController();
                cc.setData(c);
                cc.setTitle("Candidature #" + c.getId());
                cc.setMeta(c.getStatut() + " • " + c.getDisponibilite());
                cc.setDescription("Score: " + c.getScore() + "\nOffre: " + c.getOffreId());
                cc.setActionHandler(new CardController.CardActionHandler() {
                    @Override public void onView(Object data) {
                        String path = c.getCv();
                        if (path != null && !path.isEmpty()) {
                            try { Desktop.getDesktop().open(new File(path)); } catch (Exception ex) { showAlert("Erreur","Impossible d'ouvrir le CV: " + ex.getMessage()); }
                        } else showInfo("Info","Aucun CV disponible.");
                    }
                    @Override public void onEdit(Object data) { remplirFormulaire(c); }
                    @Override public void onDelete(Object data) { service.supprimer(c.getId()); loadCards(); clear(); showInfo("Succès","Candidature supprimée"); }
                });
                cardsContainer.getChildren().add(cardNode);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    private boolean controle() {
        if (dpDepot.getValue() == null ||
                txtStatut.getText().isEmpty() ||
                txtDisponibilite.getText().isEmpty() ||
                txtScore.getText().isEmpty() ||
                txtCv.getText().isEmpty() ||
                txtOffreId.getText().isEmpty()) {

            showAlert("Erreur", "Tous les champs sont obligatoires !");
            return false;
        }

        try {
            Double.parseDouble(txtScore.getText().trim());
            Integer.parseInt(txtOffreId.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Score doit être un nombre et Offre ID doit être un entier.");
            return false;
        }

        return true;
    }

    private void clear() {
        dpDepot.setValue(null);
        txtStatut.clear();
        txtDisponibilite.clear();
        txtScore.clear();
        txtCv.clear();
        txtOffreId.clear();
        selected = null;
        selectedCvFile = null;
    }

    private void showAlert(String titre, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(titre); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }

    private void showInfo(String titre, String msg) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(titre); a.setHeaderText(null); a.setContentText(msg); a.showAndWait();
    }
}
