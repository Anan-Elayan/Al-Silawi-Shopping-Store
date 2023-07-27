package com.example.alsilawi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

import static javafx.fxml.FXMLLoader.load;

public class Admian {

    @FXML
    private Button ordersButton;

    @FXML
    private Pane pane;

    @FXML
    private Button productsButton;

    @FXML
    private Button reportsButton;

    @FXML
    private TableView<?> tabel;

    @FXML
    private TableView<?> table2;


    @FXML
    void openOrders(ActionEvent event) throws IOException {

        // we have to load the info inside the tableview of the orders to show it to the admin
        Stage stage = (Stage) ordersButton.getScene().getWindow();
        Parent root = load(Objects.requireNonNull(getClass().getResource("AdmenOrders.fxml")));
        stage.setTitle("Orders");
        stage.setScene(new Scene(root));
    }

    @FXML
    void openProducts(ActionEvent event) throws IOException {
        Stage stage = (Stage) productsButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminReports.fxml")));
        stage.setTitle("Reports Page");
        stage.setScene(new Scene(root));
    }

    @FXML
    void openReports(ActionEvent event) throws IOException {
        Stage stage = (Stage) reportsButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminReports.fxml")));
        stage.setTitle("Reports Page");
        stage.setScene(new Scene(root));
    }
}
