package com.example.alsilawi;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminReports implements Initializable {
    @FXML
    private Button btnSignOut;
    @FXML
    private Button btnSetting;
    @FXML
    private TableColumn<ProductsSoldViewTable, Integer> amountSoldCol;
    @FXML
    private TableView<ProductsSoldViewTable> inventoryProductsTable;
    @FXML
    private Button ordersButton;
    @FXML
    private TableColumn<ProductsSoldViewTable, String> productCategoryCol;
    @FXML
    private TableColumn<ProductsSoldViewTable, Double> productPrice;
    @FXML
    private Button productsButton;
    @FXML
    private Button reportsButton;
    @FXML
    private TableView<ProductsSoldViewTable> soldProductsTable;
    @FXML
    private Text txtNetProfet;
    @FXML
    private Text txtNoOfCategories;
    @FXML
    private Text txtNoOfProducts;
    @FXML
    private TableColumn<ProductsViewTable, String> unitNameCol;
    ObservableList<ProductsSoldViewTable> productsSoldObservableList = FXCollections.observableList(ProductsSoldViewTable.products);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        soldProductsTable.getItems().clear();

        ProductsSoldViewTable.products.clear();

        amountSoldCol.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        productCategoryCol.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
        unitNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));

        //clear out the tables before they being initialized
        String productSoldCategory = "", productSoldName = "";
        double productSoldPrice = 0;
        int productSoldAmount = 0;

        try {
            ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
            LoginController.conction = a.connectDB();
            String sql1 = "select P.product_name, C.categorise_name, P.product_selling_price, po.quantity from product2order po, product p, categories C where po.product_id = p.product_id and C.categories_id = P.categories_id";
            Statement statement1 = LoginController.conction.createStatement();
            ResultSet result1 = statement1.executeQuery(sql1);


            while (result1.next()) {
                productSoldName = result1.getString(1);
                productSoldCategory = result1.getString(2);
                productSoldPrice = result1.getDouble(3);
                productSoldAmount = result1.getInt(4);
                ProductsSoldViewTable product = new ProductsSoldViewTable(productSoldName, productSoldCategory, productSoldPrice, productSoldAmount);
                ProductsSoldViewTable.products.add(product);
            }

            soldProductsTable.setItems(productsSoldObservableList);
            statement1.close();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        double netProfit = 0;
        try {
//            connection = DriverManager.getConnection(dataBaseUrl, username, password);
            ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
            LoginController.conction = a.connectDB();
            String sql = "SELECT SUM((p.product_selling_price - p.product_purchasing_price) * po.quantity) AS order_net_profit FROM product2order po, product p WHERE po.product_id = p.product_id";
            Statement statement = LoginController.conction.createStatement();

            ResultSet result = statement.executeQuery(sql);
            result.next(); // Move the cursor to the first row
            netProfit = result.getInt(1); // Retrieve the count value from the first column of the current row

            txtNetProfet.setText(String.valueOf(netProfit));
            statement.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        int numberOfCategories = 0;
        try {
            ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
            LoginController.conction = a.connectDB();
//            connection = DriverManager.getConnection(dataBaseUrl, username, password);
            String sql = "SELECT COUNT(*) FROM categories";
            Statement statement = LoginController.conction.createStatement();

            ResultSet result = statement.executeQuery(sql);
            result.next(); // Move the cursor to the first row
            numberOfCategories = result.getInt(1); // Retrieve the count value from the first column of the current row

            txtNoOfCategories.setText(String.valueOf(numberOfCategories));
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        int numberOfProducts = 0;
        try {
            ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
            LoginController.conction = a.connectDB();
            String sql = "SELECT COUNT(*) FROM product";
            Statement statement = LoginController.conction.createStatement();

            ResultSet result = statement.executeQuery(sql);
            result.next(); // Move the cursor to the first row
            numberOfProducts = result.getInt(1); // Retrieve the count value from the first column of the current row

            txtNoOfProducts.setText(String.valueOf(numberOfProducts));
            statement.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void openOrders(ActionEvent event) throws IOException {
        Stage stage = (Stage) ordersButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdmenOrders.fxml")));
        stage.setTitle("Orders Page");
        stage.setScene(new Scene(root));
    }

    @FXML
    void openProducts(ActionEvent event) throws IOException {
        Stage stage = (Stage) productsButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminProducts.fxml")));
        stage.setTitle("Products Page");
        stage.setScene(new Scene(root));
    }

    @FXML
    void openReports(ActionEvent event) throws IOException {
        Stage stage = (Stage) reportsButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminReports.fxml")));
        stage.setTitle("Reports Page");
        stage.setScene(new Scene(root));
    }

    @FXML
    void ActionnSignOut(ActionEvent event) {
        WindowSure windowSure = new WindowSure();
        windowSure.getButtonSave().setText("Yes");
        windowSure.getButtonCancel().setText("NO");
        windowSure.getLbl().setText("Are you sure to Exit ");
        windowSure.getLbl().setLayoutX(117);
        windowSure.getButtonSave().setOnAction(e -> {
            Stage stage = (Stage) btnSignOut.getScene().getWindow();
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            stage.setTitle("Login Page");
            stage.setScene(new Scene(root));
            windowSure.getStage().close();
        });
        windowSure.getButtonCancel().setOnAction(e -> {
            Stage s = (Stage) windowSure.getScene().getWindow();
            s.close();
        });
    }

    @FXML
    void SettingAction(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeSetting.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Setting Page");
        stage.getIcons().add(new Image("/images/settings.png"));
        stage.setScene(new Scene(parent));
        stage.show();
    }
}
