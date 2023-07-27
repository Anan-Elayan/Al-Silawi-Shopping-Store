package com.example.alsilawi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

import static javafx.fxml.FXMLLoader.load;

public class AlertWarning {

    @FXML
    private Button btnClose;
    @FXML
    private Label lblWarning;

    @FXML
    void ActionClose(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    public void setText(String warning) {
        this.lblWarning.setText(warning);
    }
}
