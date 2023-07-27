package com.example.alsilawi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeSetting implements Initializable {
    @FXML
    private Button btnSave = new Button();
    ;

    @FXML
    private Label lblName;

    @FXML
    private Label lblWarning;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtPhone;
    int EmployeeID = LoginController.getEmployeeID();


    @FXML
    void ActionSave(ActionEvent event) throws SQLException {
        if (txtName.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
            lblWarning.setText("Please Fill All Data");
            lblWarning.setVisible(true);
            return;
        }
        if (txtEmail.getText().matches("\\d+")) {
            lblWarning.setText("Invalid Input Email..try again");
            lblWarning.setVisible(true);
            return;
        }
        if (txtName.getText().matches("\\d+")) {
            lblWarning.setText("Invalid Input Name..try again");
            lblWarning.setVisible(true);
            return;
        }
        if (!txtPhone.getText().matches("\\d+")) {
            lblWarning.setText("Invalid Input Phone NO..try again");
            lblWarning.setVisible(true);
            return;
        } else {
            String update = "update employee set employee_name=?, employee_email=?,employee_password=? ,employee_phone=?   where employee_id=?";
            PreparedStatement preparedStatement = LoginController.conction.prepareStatement(update);
            preparedStatement.setString(1, txtName.getText());
            preparedStatement.setString(2, txtEmail.getText());
            preparedStatement.setInt(3, Integer.parseInt(txtPassword.getText()));
            preparedStatement.setString(4, txtPhone.getText());
            preparedStatement.setInt(5, EmployeeID);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String getName = "select * from employee where employee_id=?";
        try {
            PreparedStatement preparedStatement = LoginController.conction.prepareStatement(getName);
            preparedStatement.setInt(1, EmployeeID);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            lblName.setText(resultSet.getString(2));
            txtName.setText(resultSet.getString(2));
            txtEmail.setText(resultSet.getString(3));
            txtPassword.setText(resultSet.getString(4));
            txtPhone.setText(resultSet.getString(5));
            preparedStatement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
