package com.example.alsilawi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;

import static javafx.fxml.FXMLLoader.load;


public class UserSignupPage {

    public ToggleGroup Group11;
    @FXML
    private Button adminButton1;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnCreate;
    @FXML
    private Label labelIncompletInfo;
    @FXML
    private TextField txtBuildingNumber;
    @FXML
    private TextField txtCity;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtStreetName;
    @FXML
    private Button userButton;


    @FXML
    void actionButtonAdmin() throws IOException {
        Stage stage = (Stage) adminButton1.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminSignUp.fxml")));
        stage.setTitle("Sign up ");
        stage.setScene(new Scene(root));
    }


    @FXML
    void ActionBack() throws IOException {
        Stage stage = (Stage) btnBack.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
        stage.setTitle("Login Page ");
        stage.setScene(new Scene(root));
    }


    @FXML
    void ActionCreate(ActionEvent event) throws IOException, SQLException, ClassNotFoundException {

        if (txtName.getText().isEmpty() || txtPhone.getText().isEmpty() || txtPassword.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            labelIncompletInfo.setVisible(true);
            return;
        }
        if (txtEmail.getText().matches("\\d+")) {
            labelIncompletInfo.setText("Invalid Input Email..try again");
            labelIncompletInfo.setVisible(true);
            return;
        }
        if (txtName.getText().matches("\\d+")) {
            labelIncompletInfo.setText("Invalid Input Name..try again");
            labelIncompletInfo.setVisible(true);
            return;
        }
        if (!txtPhone.getText().matches("\\d+")) {
            labelIncompletInfo.setText("Invalid Input Phone NO..try again");
            labelIncompletInfo.setVisible(true);
            return;
        }
        if (txtCity.getText().matches("\\d+")) {
            labelIncompletInfo.setText("Invalid Input City..try again");
            labelIncompletInfo.setVisible(true);
            return;
        }
        if (txtStreetName.getText().matches("\\d+")) {
            labelIncompletInfo.setText("Invalid Input Street Name..try again");
            labelIncompletInfo.setVisible(true);
            return;
        }
        if (!txtBuildingNumber.getText().matches("\\d+")) {
            labelIncompletInfo.setText("Invalid Input Building No..try again");
            labelIncompletInfo.setVisible(true);

        } else {
            ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
            LoginController.conction = a.connectDB();

            String querycity = "insert into city (city_name,streetName,buildingNumber) values (?, ?,?)";
            PreparedStatement statementcity = LoginController.conction.prepareStatement(querycity);
            statementcity.setString(1, txtCity.getText());
            statementcity.setString(2, txtStreetName.getText());
            statementcity.setInt(3, Integer.parseInt(txtBuildingNumber.getText()));
            statementcity.executeUpdate();

            String cityID = "select max(city_id) as city_id from city";
            PreparedStatement statementcityID = LoginController.conction.prepareStatement(cityID);
            ResultSet resultSet = statementcityID.executeQuery();
            resultSet.next();
            int id = resultSet.getInt("city_id");


            String query = "insert into customer (city_id,customer_name , customer_email  , customer_phone ,customer_city,customer_streetName  ,customer_buildingNumber, customer_password ) values(?,? , ? ,?,? , ?,?,?)";
            PreparedStatement statement = LoginController.conction.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, txtName.getText());
            statement.setString(3, txtEmail.getText());
            statement.setString(4, txtPhone.getText());
            statement.setString(5, txtCity.getText());
            statement.setString(6, txtStreetName.getText());
            statement.setInt(7, Integer.parseInt(txtBuildingNumber.getText()));
            statement.setString(8, txtPassword.getText());
            statement.executeUpdate();

            Stage stage = (Stage) btnCreate.getScene().getWindow();
            Parent root = load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
            stage.setTitle("Login Page ");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/images/thinking.png"));


            txtName.clear();
            txtEmail.clear();
            txtPhone.clear();
            txtPassword.clear();
            txtBuildingNumber.clear();
            txtStreetName.clear();
            txtCity.clear();
            statementcity.close();
            statementcityID.close();
            statement.close();

        }
    }
}
