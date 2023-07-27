package com.example.alsilawi;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class PaymentMethod implements Initializable {
    @FXML
    private Button btnBack;
    @FXML
    private Label lblWarning;
    @FXML
    private Pane cardCash;
    @FXML
    private Pane cardPalPay;
    @FXML
    private Pane CardCreditCard;
    @FXML
    private Button btnDone;
    @FXML
    private TextField txtCardNumber;
    @FXML
    private Label lblCCV;
    @FXML
    private Label lblCardNumber;
    @FXML
    private Label lblExpirationDate;
    @FXML
    private Label totavValue;
    @FXML
    private Label lbltotal;
    @FXML
    private Label lblYear;
    @FXML
    private ComboBox<String> ComboBoxYear;
    @FXML
    private RadioButton radioButtonCash;
    @FXML
    private RadioButton radioButtonCreditCard;
    @FXML
    private RadioButton radioButtonPalPay;
    @FXML
    private ComboBox<?> comboBoxCCV;
    @FXML
    private ToggleGroup toggolGroup;
    @FXML
    private ComboBox<String> EXPDate;
    int idCreditCard = 0;
    int idPalPay = 0;
    int idCach = 0;
    static ObservableList<TempOrder> items;
    static double totalPricaeADouble;

    @FXML
    void ActionBack(ActionEvent event) throws IOException {
        Store.setData(items, totalPricaeADouble);
        Stage stage = (Stage) btnBack.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Store.fxml")));
        stage.setTitle("Store Page");
        stage.setScene(new Scene(root));

    }

    @FXML
    void ActionSelectedCreditCard(ActionEvent event) {
        CardCreditCard.setOpacity(1);
        cardCash.setOpacity(0.5);
        cardPalPay.setOpacity(0.5);
        EXPDate.setVisible(true);
        lblCCV.setVisible(true);
        lblCardNumber.setVisible(true);
        lblExpirationDate.setVisible(true);
        txtCardNumber.setVisible(true);
        ComboBoxYear.setVisible(true);
        comboBoxCCV.setVisible(true);
        lblYear.setVisible(true);
    }

    @FXML
    void ActionSelectedPalPay(ActionEvent event) {
        CardCreditCard.setOpacity(0.5);
        cardCash.setOpacity(0.5);
        cardPalPay.setOpacity(1);
        EXPDate.setVisible(true);
        lblCCV.setVisible(true);
        lblCardNumber.setVisible(true);
        lblExpirationDate.setVisible(true);
        txtCardNumber.setVisible(true);
        ComboBoxYear.setVisible(true);
        comboBoxCCV.setVisible(true);
        lblYear.setVisible(true);
    }

    @FXML
    void actionSelectedCash(ActionEvent event) {
        CardCreditCard.setOpacity(0.5);
        cardCash.setOpacity(1);
        cardPalPay.setOpacity(0.5);
        EXPDate.setVisible(false);
        lblCCV.setVisible(false);
        lblCardNumber.setVisible(false);
        lblExpirationDate.setVisible(false);
        txtCardNumber.setVisible(false);
        ComboBoxYear.setVisible(false);
        comboBoxCCV.setVisible(false);
        lblYear.setVisible(false);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
        //return id for nay payment method name
        String quere = "select payment_method_id from payment_method where payment_method_name=?";
        String quere2 = "select payment_method_id from payment_method where payment_method_name=?";
        String quere3 = "select payment_method_id from payment_method where payment_method_name=?";

        try {
            PreparedStatement statement = LoginController.conction.prepareStatement(quere);
            PreparedStatement statement2 = LoginController.conction.prepareStatement(quere2);
            PreparedStatement statement3 = LoginController.conction.prepareStatement(quere3);

            statement.setString(1, "CreditCard");
            statement2.setString(1, "PalPay");
            statement3.setString(1, "Cash");

            ResultSet resultSet = statement.executeQuery();
            ResultSet resultSet2 = statement2.executeQuery();
            ResultSet resultSet3 = statement3.executeQuery();

            if (resultSet3.next()) {
                idCach = resultSet3.getInt("payment_method_id");  //idCach = 1
            }
            if (resultSet.next()) {
                idCreditCard = resultSet.getInt("payment_method_id");// idCreditCard = 2
            }
            if (resultSet2.next()) {
                idPalPay = resultSet2.getInt("payment_method_id");//idPalPay = 3
            }

            statement.close();
            statement2.close();
            statement3.close();
            resultSet.close();
            resultSet2.close();
            resultSet3.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        radioButtonCreditCard.setToggleGroup(toggolGroup);
        radioButtonPalPay.setToggleGroup(toggolGroup);
        radioButtonCash.setToggleGroup(toggolGroup);
        totavValue.setText(totalPricaeADouble + " Nis");
        EXPDate.setEditable(false);
        ComboBoxYear.setEditable(false);
        EXPDate.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        ComboBoxYear.getItems().addAll("2015", "2016", "2017", "2018", "2019", "2020", "2021", "2022", "2023");
    }

    @FXML
    void ActionDone(ActionEvent event) {
        // if total invoice = 0
        if (Double.parseDouble(totavValue.getText().substring(0, totavValue.getText().length() - 3)) == 0.0) {
            lblWarning.setText("You have no purchases");
            lblWarning.setVisible(true);
            return;
        }
        if (radioButtonCash.isSelected()) {
            lblWarning.setVisible(false);
            WindowSure windowSure = new WindowSure();
            lblWarning.setVisible(false);
            windowSure.getButtonSave().setOnAction(e -> {
                try {
                    executeQuery();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);

                }
                windowSure.getStage().close();
                return;
            });
            windowSure.getButtonCancel().setOnAction(c -> {
                windowSure.getStage().close();
            });


        }
        // pal pay * credit card
        else {
            if (txtCardNumber.getText().isEmpty() || EXPDate.getValue() == null ||
                    ComboBoxYear.getValue() == null || comboBoxCCV.getValue() == null) {
                lblWarning.setText("Please select payment method or incomplete information");
                lblWarning.setVisible(true);
                return;
            }
            if (!txtCardNumber.getText().matches("\\d+")) {
                lblWarning.setText("Invalid Input Card Number..try again");
                lblWarning.setVisible(true);
            } else {
                WindowSure windowSure = new WindowSure();
                windowSure.getButtonSave().setOnAction(e -> {
                    windowSure.getStage().close();
                    try {
                        executeQuery();
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
        }
    }

    public void executeQuery() throws SQLException {

        String quere = "insert into orders(order_date  ,order_price ,payment_method_id  ,customer_id) values (?,?,?,?)";
        PreparedStatement statement = LoginController.conction.prepareStatement(quere);

        String quereorder2Product = "insert into product2order (product_id, quantity, order_id) values (?,?,?)";
        PreparedStatement statmentOrder2Product = LoginController.conction.prepareStatement(quereorder2Product);


        if (radioButtonCreditCard.isSelected()) {
            statement.setInt(3, idCreditCard);
        } else if (radioButtonCash.isSelected()) {
            statement.setInt(3, idCach);
        } else {
            statement.setInt(3, idPalPay);
        }

        java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
        statement.setDate(1, sqlDate);
        statement.setDouble(2, Double.parseDouble(totavValue.getText().substring(0, totavValue.getText().length() - 3)));
        statement.setInt(4, LoginController.getGetCustomerID());
        statement.execute();
        statement.close();


        String temp = "select max(order_id) as order_id from orders";
        PreparedStatement stmTemp = LoginController.conction.prepareStatement(temp);

        ResultSet resultSet = stmTemp.executeQuery();
        resultSet.next();
        int value = resultSet.getInt("order_id");

        for (int j = 0; j < items.size(); j++) {
            // add to table order two products
            statmentOrder2Product.setInt(1, items.get(j).getProduct().getProductID());
            statmentOrder2Product.setInt(2, items.get(j).getQuantity());
            statmentOrder2Product.setInt(3, value);
            statmentOrder2Product.execute();
        }

        //to update quantity
        String updateQuantity = "update product set product.product_quantity=? where product.product_id=? ";
        PreparedStatement stmUpdateQuantity = LoginController.conction.prepareStatement(updateQuantity);

        String Quantity = "select product.product_quantity from product where product_id=? ";
        PreparedStatement stmQuantity = LoginController.conction.prepareStatement(Quantity);
        for (TempOrder item : items) {
            stmQuantity.setInt(1, item.getProduct().getProductID());
            ResultSet r = stmQuantity.executeQuery();
            r.next();
            int w = r.getInt("product_quantity");
            System.out.println(w);
            stmUpdateQuantity.setInt(1, w - item.getQuantity());
            stmUpdateQuantity.setInt(2, item.getProduct().getProductID());
            stmUpdateQuantity.executeUpdate();
            System.out.println(item.getQuantity());
        }
        statmentOrder2Product.close();


        String maxOrder = "select max(order_id) as order_id from orders";
        PreparedStatement stmMaxOrder = LoginController.conction.prepareStatement(maxOrder);

        ResultSet resultSetMaxOrder = stmMaxOrder.executeQuery();
        resultSetMaxOrder.next();
        int maxValue = resultSetMaxOrder.getInt("order_id");  //********************************** last order

        String quantity = "SELECT P.product_id, P.product_quantity FROM product2order po, product P WHERE po.order_id = ? and P.product_id = po.product_id";
        PreparedStatement stmQuna = LoginController.conction.prepareStatement(quantity);
        stmQuna.setInt(1, maxValue);
        stmQuna.executeQuery();
        ResultSet resultSet1Products = stmQuna.executeQuery();
        int q = 0;
        while (resultSet1Products.next()) {
            q = resultSet1Products.getInt(2);
            if (q == 0) {
                WindowSure w = new WindowSure();
            }
        }
        resultSet.close();
    }

    public static void setData(ObservableList<TempOrder> items, double totalPrice) {
        PaymentMethod.items = items;
        PaymentMethod.totalPricaeADouble = totalPrice;
    }
}
