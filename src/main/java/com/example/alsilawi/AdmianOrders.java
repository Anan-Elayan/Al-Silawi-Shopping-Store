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

public class AdmianOrders implements Initializable {
    @FXML
    private Text maxInvoice;
    @FXML
    private Button btnSignOut;
    @FXML
    private Button cancelOrderButton;
    @FXML
    private Text sumOrders;
    @FXML
    private Text minOrders;
    @FXML
    private TableColumn<OrdersTableShow, String> customerNameCol;
    @FXML
    private TableColumn<OrdersTableShow, String> dateCol;
    @FXML
    private TableColumn<OrdersTableShow, String> paymentTypeCol;
    @FXML
    private Button finishOrderButton;
    @FXML
    private TableColumn<OrdersTableShow, String> locationCol;
    @FXML
    private Button ordersButton;
    @FXML
    private Pane pane;
    @FXML
    private TableColumn<OrdersTableShow, String> customerPhoneCol;
    @FXML
    private TableColumn<OrdersTableShow, Double> totalPriceCol;
    @FXML
    private TableColumn<OrdersTableShow, Integer> idCol;
    @FXML
    private Text txtOrdersCount1;
    @FXML
    private Button productsButton;
    @FXML
    private Button reportsButton;
    @FXML
    private Button btnSetting;
    @FXML
    private TableView<OrdersTableShow> tabel;

    ObservableList<OrdersTableShow> list = FXCollections.observableList(OrdersTableShow.orders);

    {
        try {
            ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
            LoginController.conction = a.connectDB();

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tabel.getItems().clear();
        OrdersTableShow.orders.clear();

        idCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneCol.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        paymentTypeCol.setCellValueFactory(new PropertyValueFactory<>("paymentType"));


        String customerName = "", customerPhone = "", customerLocation = "", orderDate = "", paymentMethod = "";
        double totalPrice = 0;
        int orderId = 0;

        try {
            String sql = "select O.order_id, O.order_date, C.customer_name,C.customer_phone,CI.city_name,O.order_price,P.payment_method_name from orders O, customer C, payment_method P, city CI where O.customer_id = C.customer_id and O.payment_method_id = P.payment_method_id and C.city_id = CI.city_id";
            Statement statement;

            statement = LoginController.conction.createStatement();
            ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
            LoginController.conction = a.connectDB();


            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                orderId = result.getInt(1);
                orderDate = result.getString(2);
                customerName = result.getString(3);
                customerPhone = result.getString(4);
                customerLocation = result.getString(5);
                totalPrice = result.getDouble(6);
                paymentMethod = result.getString(7);

                OrdersTableShow order = new OrdersTableShow(orderId, orderDate, customerName, customerPhone, customerLocation, totalPrice, paymentMethod);
                OrdersTableShow.orders.add(order);
            }

            String max = "select max(order_price)  from orders";
            PreparedStatement p = LoginController.conction.prepareStatement(max);
            ResultSet resultSet = p.executeQuery();
            resultSet.next();
            int maxValue = resultSet.getInt("max(order_price)");


            String min = "select min(order_price)  from orders";
            PreparedStatement stmMin = LoginController.conction.prepareStatement(min);
            ResultSet resultSetMin = stmMin.executeQuery();
            resultSetMin.next();
            int minValue = resultSetMin.getInt("min(order_price)");

            String sum = "select sum(order_price)  from orders";
            PreparedStatement stmSum = LoginController.conction.prepareStatement(sum);
            ResultSet resultSetSum = stmSum.executeQuery();
            resultSetSum.next();
            int sumValue = resultSetSum.getInt("sum(order_price)");


            tabel.setItems(list);
            txtOrdersCount1.setText(String.valueOf(OrdersTableShow.orders.size()));
            maxInvoice.setText(String.valueOf(maxValue));
            minOrders.setText(String.valueOf(minValue));
            sumOrders.setText(String.valueOf(sumValue));
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
        stage.setTitle("products Page");
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
    void ActionnSignOut(ActionEvent event) throws IOException {
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
    void ActionSetting(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EmployeeSetting.fxml"));
        Parent parent = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setTitle("Setting Page");
        stage.getIcons().add(new Image("/images/settings.png"));
        stage.setScene(new Scene(parent));
        stage.show();
    }

}