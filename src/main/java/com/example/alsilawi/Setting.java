package com.example.alsilawi;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Setting implements Initializable {
    @FXML
    private Button btnSave = new Button();
    @FXML
    private Label lblWarning;

    @FXML
    private Label lblName;

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
    int CustomerId = LoginController.getGetCustomerID();

    @FXML
    void ActionSave(ActionEvent event) throws SQLException {
        if (txtName.getText().trim().isEmpty() || txtEmail.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty() || txtCity.getText().trim().isEmpty()
                || txtStreetName.getText().trim().isEmpty() || txtBuildingNumber.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty()) {
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
        }
        if (txtCity.getText().matches("\\d+")) {
            lblWarning.setText("Invalid Input City..try again");
            lblWarning.setVisible(true);
            return;
        }
        if (txtStreetName.getText().matches("\\d+")) {
            lblWarning.setText("Invalid Input Street Name..try again");
            lblWarning.setVisible(true);
            return;
        }
        if (!txtBuildingNumber.getText().matches("\\d+")) {
            lblWarning.setText("Invalid Input Building No..try again");
            lblWarning.setVisible(true);
            return;
        } else {
            String update = "update customer set customer_name=? , customer_email=? , customer_phone=?,customer_city=?,customer_streetName=? ,customer_buildingNumber=?,customer_password=? where customer_id=?";
            PreparedStatement preparedStatement = LoginController.conction.prepareStatement(update);
            preparedStatement.setString(1, txtName.getText());
            preparedStatement.setString(2, txtEmail.getText());
            preparedStatement.setString(3, txtPhone.getText());
            preparedStatement.setString(4, txtCity.getText());
            preparedStatement.setString(5, txtStreetName.getText());
            preparedStatement.setInt(6, Integer.parseInt(txtBuildingNumber.getText()));
            preparedStatement.setInt(7, Integer.parseInt(txtPassword.getText()));
            preparedStatement.setInt(8, CustomerId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        }
        Stage stage = (Stage) btnSave.getScene().getWindow();
        stage.close();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String getName = "select * from customer where customer_id=?";
        try {
            PreparedStatement statement = LoginController.conction.prepareStatement(getName);
            statement.setInt(1, CustomerId);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            txtName.setText(resultSet.getString(3));
            lblName.setText(resultSet.getString(3));
            txtEmail.setText(resultSet.getString(4));
            txtPhone.setText(resultSet.getString(5));
            txtCity.setText(resultSet.getString(6));
            txtStreetName.setText(resultSet.getString(7));
            txtBuildingNumber.setText(resultSet.getString(8));
            txtPassword.setText(resultSet.getString(9));
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
