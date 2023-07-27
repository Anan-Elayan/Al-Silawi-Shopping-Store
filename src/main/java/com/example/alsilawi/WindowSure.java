package com.example.alsilawi;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WindowSure extends Pane {

    Stage stage = new Stage();
    Scene scene = new Scene(this, 400, 150);
    Label lbl = new Label("Are you sure to save all changes");
    Button btnSave = new Button("Save");
    Button btnCancel = new Button("Cancel");

    public WindowSure() {

        this.setStyle("-fx-backGround-color:black");
        Font font = Font.font(20);
        lbl.setFont(font);
        lbl.setStyle("-fx-text-fill:white");
        lbl.setLayoutX(60);
        lbl.setLayoutY(30);
        btnSave.setLayoutX(250);
        btnSave.setLayoutY(90);
        btnCancel.setLayoutX(130);
        btnCancel.setLayoutY(90);
        btnSave.setStyle("-fx-background-color:#eda723;-fx-text-fill :white;-fx-font-size:15");
        btnCancel.setStyle("-fx-background-color:#eda723;-fx-text-fill :white;-fx-font-size:15");
        btnSave.setUnderline(true);
        this.getChildren().addAll(lbl, btnCancel, btnSave);
        stage.setScene(scene);
        stage.setTitle("ConfirmChanges");
        stage.getIcons().add(new Image("/images/logout(1).png"));
        stage.setScene(scene);
        stage.show();
    }

    public Button getButtonSave() {
        return btnSave;
    }

    public Button getButtonCancel() {
        return btnCancel;
    }

    public Label getLbl() {
        return lbl;
    }

    public Stage getStage() {
        return stage;
    }

}

