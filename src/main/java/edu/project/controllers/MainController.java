package edu.project.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;

public class MainController {

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private StackPane mainContent;

    @FXML
    public void initialize() {
        // Load default view
        openOffre();
    }

    private void setMainContent(Node node) {
        mainContent.getChildren().clear();
        if (node != null) mainContent.getChildren().add(node);
    }

    @FXML
    private void openOffre() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OffreEmploi.fxml"));
            setMainContent(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openCandidature() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Candidature.fxml"));
            setMainContent(loader.load());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
