package com.example.alsilawi;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import static javafx.fxml.FXMLLoader.load;

public class AdminSignUp {

    @FXML
    private Button adminButton1;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnLogin;
    @FXML
    private Label labelIncompletInfo;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtUserName;
    @FXML
    private Button userButton;


    @FXML
    void actionUserButton() throws IOException {
        Stage stage = (Stage) userButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UserSignUpPage.fxml")));
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
    void ActionLogin() throws IOException, SQLException, ClassNotFoundException {
        if (txtUserName.getText().isEmpty() || txtPhone.getText().isEmpty() || txtPassword.getText().isEmpty() || txtEmail.getText().isEmpty()) {
            labelIncompletInfo.setVisible(true);
            return;
        }
        if (txtEmail.getText().matches("\\d+")) {
            labelIncompletInfo.setText("Invalid Input Email..try again");
            labelIncompletInfo.setVisible(true);
            return;
        }
        if (txtUserName.getText().matches("\\d+")) {
            labelIncompletInfo.setText("Invalid Input Name..try again");
            labelIncompletInfo.setVisible(true);
            return;
        }
        if (!txtPhone.getText().matches("\\d+")) {
            labelIncompletInfo.setText("Invalid Input Phone NO..try again");
            labelIncompletInfo.setVisible(true);
        } else {
            ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
            LoginController.conction = a.connectDB();
            String query = "insert into employee (employee_name , employee_email , employee_password , employee_phone ) values( ? , ? , ? , ?)";
            PreparedStatement statement = LoginController.conction.prepareStatement(query);
            statement.setString(1, txtUserName.getText());
            statement.setString(2, txtEmail.getText());
            statement.setString(3, txtPassword.getText());
            statement.setString(4, txtPhone.getText());
            statement.executeUpdate();

            Stage stage = (Stage) btnLogin.getScene().getWindow();
            Parent root = load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
            stage.setTitle("Login Page ");
            stage.setScene(new Scene(root));
            stage.getIcons().add(new Image("/images/thinking.png"));

            txtUserName.clear();
            txtEmail.clear();
            txtPhone.clear();
            txtPassword.clear();
            statement.close();
        }
    }
}
