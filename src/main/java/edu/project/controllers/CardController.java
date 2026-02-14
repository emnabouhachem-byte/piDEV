package edu.project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class CardController {

    @FXML private AnchorPane root;
    @FXML private Label lblTitle;
    @FXML private Label lblMeta;
    @FXML private Label lblDescription;
    @FXML private Button btnView;
    @FXML private Button btnEdit;
    @FXML private Button btnDelete;

    private Object data;
    private CardActionHandler handler;

    public interface CardActionHandler {
        void onView(Object data);
        void onEdit(Object data);
        void onDelete(Object data);
    }

    public void setActionHandler(CardActionHandler handler) {
        this.handler = handler;
        btnView.setOnAction(e -> { if (this.handler != null) this.handler.onView(data); });
        btnEdit.setOnAction(e -> { if (this.handler != null) this.handler.onEdit(data); });
        btnDelete.setOnAction(e -> { if (this.handler != null) this.handler.onDelete(data); });
    }

    public void setData(Object data) {
        this.data = data;
    }

    public void setTitle(String title) { lblTitle.setText(title); }
    public void setMeta(String meta) { lblMeta.setText(meta); }
    public void setDescription(String desc) { lblDescription.setText(desc); }
}

