package com.example.alsilawi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static javafx.fxml.FXMLLoader.*;

public class LoginController {

    public static String dbUsername = "root";
    public static String dbPassword = "anan$elayan1512";
    public static String URL = "localhost";
    public static String port = "3306";
    public static String dbName = "al_silawi";
    public static Connection conction;


    @FXML
    private Button btnForgetPasswd;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnTopLogin;
    @FXML
    private Button btnTopSignup;
    @FXML
    private Label labelNotFoundUser;
    @FXML
    private PasswordField txfPassword;
    @FXML
    private TextField txfUserName;
    public static int getCustomerID;
    public static int getEmployeeID;


    //button sign sup
    @FXML
    void signupAction() throws IOException {
        Stage stage = (Stage) btnTopSignup.getScene().getWindow();
        Parent root = load(Objects.requireNonNull(getClass().getResource("UserSignUpPage.fxml")));
        stage.setTitle("Sign up page ");
        stage.setScene(new Scene(root));
    }

    @FXML
    void ActionLogin(ActionEvent event) throws IOException, ClassNotFoundException {
        String inputName = txfUserName.getText(); //user name
        String password = txfPassword.getText(); //password
        if (txfUserName.getText().isEmpty() && txfPassword.getText().isEmpty()) {
            FXMLLoader root = new FXMLLoader(getClass().getResource("AlartWarning.fxml"));
            Parent parent = root.load();
            Stage stage = new Stage();
            stage.setTitle("Warning");
            stage.getIcons().add(new Image("/images/warning.png"));
            stage.setScene(new Scene(parent));
            labelNotFoundUser.setVisible(false);
            stage.show();
        } else {
            try {
                ConnectionToDataBase a = new ConnectionToDataBase(URL, port, dbName, dbUsername, dbPassword);
                conction = a.connectDB();
                String query = "select * from customer where customer_name= ? and customer_password=?";
                String query2 = "select * from employee where employee_name=? and employee_password=?";

                PreparedStatement statement = conction.prepareStatement(query);
                PreparedStatement statement2 = conction.prepareStatement(query2);

                // user name + password for customer
                statement.setString(1, inputName);
                statement.setString(2, password);
                ResultSet resultSet = statement.executeQuery();

                // user name + password for employee
                statement2.setString(1, inputName);
                statement2.setString(2, password);
                ResultSet resultSet2 = statement2.executeQuery();

                if (resultSet.next()) {
                    getCustomerID = resultSet.getInt("customer_id");
                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    FXMLLoader root = new FXMLLoader(getClass().getResource("Store.fxml"));
                    Parent parent = root.load();
                    stage.setTitle("Store");
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    txfUserName.clear();
                    txfPassword.clear();
                } else {
                    labelNotFoundUser.setVisible(true);// not found user
                }
                if (resultSet2.next()) {   //employee
                    getEmployeeID = resultSet2.getInt("employee_id");
                    Stage stage = (Stage) btnLogin.getScene().getWindow();
                    FXMLLoader root = new FXMLLoader(getClass().getResource("AdminReports.fxml"));
                    Parent parent = root.load();
                    stage.setTitle("Reports page");
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    labelNotFoundUser.setVisible(false);
                    txfUserName.clear();
                    txfPassword.clear();
                } else {
                    labelNotFoundUser.setVisible(true);
                }
                statement.close();
                statement2.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    @FXML
    void btnLoginEntered() {
        btnLogin.setStyle("-fx-background-color: #eda723;-fx-background-radius: 44;-fx-fill-width: bold");
    }

    @FXML
    void btnLoginExited() {
        btnLogin.setStyle("-fx-background-color: transparent;-fx-border-color: #eda723;-fx-border-radius: 44");
    }


    //top button login
    @FXML
    public void btnTopLoginEntered() {
        btnTopLogin.setStyle("-fx-background-color: transparent;-fx-border-color: #eda723;-fx-border-radius: 44");
    }

    @FXML
    void btnTopLoginExited() {
        btnTopLogin.setStyle("-fx-background-color: #eda723;-fx-background-radius: 44;-fx-fill-width: bold");
    }


    //top button sign up
    @FXML
    void enteredSignup() {
        btnTopSignup.setStyle("-fx-background-color: transparent;-fx-border-color: #eda723;-fx-border-radius: 44");
        btnTopSignup.setOpacity(100);
    }

    @FXML
    void exitedSignUp() {
        btnTopSignup.setStyle("-fx-background-color: #eda723;-fx-background-radius: 44;-fx-fill-width: bold");

    }

    @FXML
    void ActionForgetPasswd(ActionEvent event) throws IOException {
        FXMLLoader root = new FXMLLoader(getClass().getResource("ForgetPasswd.fxml"));
        Parent parent = root.load();
        Stage stage = new Stage();
        stage.setTitle("Forget Passwd");
        stage.getIcons().add(new Image("/images/thinking.png"));
        stage.setScene(new Scene(parent));
        stage.show();
        txfUserName.clear();
        txfPassword.clear();
    }


    public static int getGetCustomerID() {
        return getCustomerID;
    }

    public static int getEmployeeID() {
        return getEmployeeID;
    }
}