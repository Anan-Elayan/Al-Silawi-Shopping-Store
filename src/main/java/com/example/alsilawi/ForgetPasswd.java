package com.example.alsilawi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ForgetPasswd {

    @FXML
    private Button btnDone;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField txtNewPassword;
    @FXML
    private TextField txtUserName;
    @FXML
    private Label lblWarning;


    @FXML
    void ActionDone(ActionEvent event) {

        Stage stage = (Stage) btnDone.getScene().getWindow();
        String userName = txtUserName.getText();
        String email = txtEmail.getText();
        String newPass = txtNewPassword.getText();

        if (txtUserName.getText().isEmpty() || txtEmail.getText().isEmpty() || txtNewPassword.getText().isEmpty()) {
            lblWarning.setText("Please Set Data");
            lblWarning.setVisible(true);
            return;
        }
        if (txtEmail.getText().matches("\\d+")) {
            lblWarning.setText("Invalid Input Email..try again");
            lblWarning.setVisible(true);
            return;
        }
        if (txtUserName.getText().matches("\\d+")) {
            lblWarning.setText("Invalid Input Name..try again");
            lblWarning.setVisible(true);
        } else {
            try {
                ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
                LoginController.conction = a.connectDB();

                String query = "select * from customer where customer_email= ? ";
                String query2 = "select * from employee where employee_email=?";

                PreparedStatement statement = LoginController.conction.prepareStatement(query);
                PreparedStatement statement2 = LoginController.conction.prepareStatement(query2);

                statement.setString(1, email); //for customer
                ResultSet resultSet = statement.executeQuery();

                statement2.setString(1, email); //from employee
                ResultSet resultSet2 = statement2.executeQuery();

                if (resultSet.next()) {
                    String quere = "select * from customer where customer_name=? and customer_email=? ";
                    PreparedStatement stm = LoginController.conction.prepareStatement(quere);
                    stm.setString(1, userName);
                    stm.setString(2, email);
                    ResultSet resultst = stm.executeQuery();
                    if (resultst.next()) {
                        String update = "update customer set customer_password= ? where customer_email=? and customer.customer_name=? ";
                        PreparedStatement statementUpdate = LoginController.conction.prepareStatement(update);
                        statementUpdate.setString(1, newPass);
                        statementUpdate.setString(2, email);
                        statementUpdate.setString(3, userName);
                        statementUpdate.executeUpdate();
                        stm.close();
                        lblWarning.setStyle("-fx-text-fill:green");
                        lblWarning.setText("Updated Successfully");
                        lblWarning.setVisible(true);
                        btnDone.setText("Close");
                        btnDone.setOnAction(e -> {
                            stage.close();
                        });

                    } else {
                        lblWarning.setText("Invalid input data..try again");
                        lblWarning.setVisible(true);
                    }
                } else if (resultSet2.next()) {                    // ****************************************  employee
                    String quere = "select * from employee where employee_name=? and employee_email=? ";
                    PreparedStatement stm = LoginController.conction.prepareStatement(quere);
                    stm.setString(1, userName);
                    stm.setString(2, email);
                    ResultSet resultst = stm.executeQuery();
                    if (resultst.next()) {
                        String update = "update employee set employee_password= ? where employee_email=? and employee_name=?";
                        PreparedStatement statementUpdate = LoginController.conction.prepareStatement(update);
                        statementUpdate.setString(1, newPass);
                        statementUpdate.setString(2, email);
                        statementUpdate.setString(3, userName);
                        statementUpdate.executeUpdate();
                        stm.close();
                        lblWarning.setStyle("-fx-text-fill:green");
                        lblWarning.setText("Updated Successfully");
                        lblWarning.setVisible(true);
                        btnDone.setText("Close");
                        btnDone.setOnAction(e -> {
                            stage.close();
                        });
                    } else {
                        lblWarning.setText("Invalid input data..try again");
                        lblWarning.setVisible(true);
                    }
                } else {
                    lblWarning.setText("Invalid input data..try again");
                    lblWarning.setVisible(true);
                }
                statement.close();
                statement2.close();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
