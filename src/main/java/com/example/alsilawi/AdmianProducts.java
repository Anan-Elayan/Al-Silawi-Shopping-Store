package com.example.alsilawi;

import javafx.scene.control.SpinnerValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;


public class AdmianProducts implements Initializable {

    @FXML
    private Button btnImportPhoto;

    @FXML
    private Button btnSignOut;

    @FXML
    private Button btuAddProduct;

    @FXML
    private Button btuDeleteProduct;

    @FXML
    private ImageView btuSignOut;

    @FXML
    private Button btuUpdateProduct;

    @FXML
    private ComboBox<String> categoriesComboBox;

    @FXML
    private TableColumn<ProductsViewTable, String> categoryCol;

    @FXML
    private ImageView imageViewProduct = new ImageView();

    @FXML
    private TableColumn<ProductsViewTable, String> nameCol;

    @FXML
    private Button ordersButton;

    @FXML
    private TableColumn<ProductsViewTable, Integer> productIdCol;

    @FXML
    private Button productsButton;

    @FXML
    private TableView<ProductsViewTable> productsTable;

    @FXML
    private TableColumn<ProductsViewTable, Double> purchasePriceCol;

    @FXML
    private TableColumn<ProductsViewTable, Integer> quantitiyCol;

    @FXML
    private Button reportsButton;

    @FXML
    private TableColumn<ProductsViewTable, Double> sellingPriceCol;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private TextField tfProductName;

    @FXML
    private TextField tfPurchasingPrice;

    @FXML
    private Spinner<Integer> tfQuantity;
    @FXML
    private Button btnSetting;

    @FXML
    private TextField tfSellingPrice;

    ObservableList<ProductsViewTable> list = FXCollections.observableList(ProductsViewTable.products);


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
        stage.getIcons().add(new Image("/images/Manegment.png"));
        stage.setScene(new Scene(root));
    }

    @FXML
    void openReports(ActionEvent event) throws IOException {
        Stage stage = (Stage) reportsButton.getScene().getWindow();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AdminReports.fxml")));
        stage.setTitle("Reports Page");
        stage.setScene(new Scene(root));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(
                0, Integer.MAX_VALUE, 10);
        tfQuantity.setValueFactory(valueFactory);

        productsTable.getItems().clear();
        ProductsViewTable.products.clear();

        categoryCol.setCellValueFactory(new PropertyValueFactory<>("productCategory"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        purchasePriceCol.setCellValueFactory(new PropertyValueFactory<>("purchasingPrice"));
        quantitiyCol.setCellValueFactory(new PropertyValueFactory<>("productAmount"));
        sellingPriceCol.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));

        String productCategory = "", productName = "", productDescription = "", imageURL = "";
        double purchasingPrice = 0, sellingPrice = 0;
        int productId = 0, productAmount = 0;

        try {
            ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
            LoginController.conction = a.connectDB();

            String sql = "select P.product_id,  P.product_name, P.product_purchasing_price,P.product_selling_price,P.product_quantity,P.product_description,C.categorise_name,P.img_URL from product P, categories C where P.categories_id = C.categories_id Order by P.product_Id asc";
            Statement statement = LoginController.conction.createStatement();

            ResultSet result = statement.executeQuery(sql);
            while (result.next()) {
                productId = result.getInt(1);
                productName = result.getString(2);
                purchasingPrice = result.getDouble(3);
                sellingPrice = result.getDouble(4);
                productAmount = result.getInt(5);
                productDescription = result.getString(6);
                productCategory = result.getString(7);
                imageURL = result.getString(8);
                ProductsViewTable product = new ProductsViewTable(productId, productName, purchasingPrice, sellingPrice, productAmount, productCategory, productDescription, imageURL);
                ProductsViewTable.products.add(product);
            }

            productsTable.setItems(list);

            statement.close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        categoriesComboBox.getItems().addAll("Games", "Antiques", "Kitchen Appliances");

        productsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ProductsViewTable product = productsTable.getSelectionModel().getSelectedItem();
                textAreaDescription.setText(product.getProductCaption());
                tfProductName.setText(product.getProductName());
                tfPurchasingPrice.setText(String.valueOf(product.getPurchasingPrice()));
                tfQuantity.getValueFactory().setValue(product.getProductAmount());
                tfSellingPrice.setText(String.valueOf(product.getSellingPrice()));
                if (product.getImageURL() != null) {
                    imageViewProduct.setImage(new Image(product.getImageURL()));
                } else {
                    imageViewProduct.setImage(null);
                }
                categoriesComboBox.setValue(product.getProductCategory());
            }
        });

        btuAddProduct.setOnAction(event -> {
            productsTable.refresh();
            String sqlCommand = "insert into product (product_name, product_purchasing_price, product_selling_price, product_quantity, product_description, categories_id, img_URL) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement statement;
            try {
                ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
                LoginController.conction = a.connectDB();
                statement = LoginController.conction.prepareStatement(sqlCommand);

                statement.setString(1, tfProductName.getText());
                statement.setString(2, tfPurchasingPrice.getText());
                statement.setString(3, tfSellingPrice.getText());
                statement.setString(4, String.valueOf(tfQuantity.getValue()));
                statement.setString(4, String.valueOf(tfQuantity.getValue()));
                statement.setString(5, textAreaDescription.getText());
                if (categoriesComboBox.getSelectionModel().getSelectedItem().equals("Games")) {
                    statement.setString(6, "1");
                } else if (categoriesComboBox.getSelectionModel().getSelectedItem().equals("Antiques")) {
                    statement.setString(6, "2");
                } else {
                    statement.setString(6, "3");
                }
                String imageUrl = imageViewProduct.getImage().getUrl();
                statement.setString(7, imageUrl);
                statement.executeUpdate();
                statement.close();
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            int max = 0;
            try {
                PreparedStatement statementt = LoginController.conction.prepareStatement(" select max(product_id) as product_id from product ");
                ResultSet resultSet = statementt.executeQuery();

                if (resultSet.next()) {
                    max = resultSet.getInt(1);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ProductsViewTable newProduct = new ProductsViewTable(max, tfProductName.getText(), Double.parseDouble(tfPurchasingPrice.getText()), Double.parseDouble(tfSellingPrice.getText()),
                    tfQuantity.getValue(), categoriesComboBox.getValue(), textAreaDescription.getText(), imageViewProduct.getImage().getUrl());
            productsTable.refresh();
            ProductsViewTable.products.add(newProduct);
            productsTable.refresh();

            tfProductName.clear();
            tfPurchasingPrice.clear();
            tfSellingPrice.clear();
            tfQuantity.getValueFactory().setValue(0);
            categoriesComboBox.getSelectionModel().clearSelection();
            textAreaDescription.clear();
            imageViewProduct.setImage(null);

        });

        btnImportPhoto.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose a Photo");

            Stage stage = (Stage) btnImportPhoto.getScene().getWindow();
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                Image image = new Image(selectedFile.toURI().toString());
                imageViewProduct.setImage(image);
            }
        });

        btuDeleteProduct.setOnAction(e -> {

            String fromSql = "delete from product2order where product_id=?";
            PreparedStatement deleteStatementt = null;
            try {
                deleteStatementt = LoginController.conction.prepareStatement(fromSql);
                int id = productsTable.getSelectionModel().getSelectedItem().getProductId();
                deleteStatementt.setInt(1, id);
                deleteStatementt.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }


            String sqlCommand = "delete from product where product_id = ?";
            try {
                ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
                LoginController.conction = a.connectDB();

                PreparedStatement deleteStatement = LoginController.conction.prepareStatement(sqlCommand);
                int id = productsTable.getSelectionModel().getSelectedItem().getProductId();
                deleteStatement.setInt(1, id);
                deleteStatement.executeUpdate();

                tfProductName.clear();
                tfPurchasingPrice.clear();
                tfSellingPrice.clear();
                tfQuantity.getValueFactory().setValue(0);
                categoriesComboBox.getSelectionModel().clearSelection();
                textAreaDescription.clear();
                imageViewProduct.setImage(null);

                deleteStatement.close();
                productsTable.refresh();

            } catch (SQLException | ClassNotFoundException e2) {
                throw new RuntimeException(e2);
            }
            ProductsViewTable.products.remove(productsTable.getSelectionModel().getSelectedItem());
            productsTable.refresh();


        });

        btuUpdateProduct.setOnAction(e -> {
            productsTable.refresh();

            ProductsViewTable product = productsTable.getSelectionModel().getSelectedItem();
            product.setProductCaption(textAreaDescription.getText());
            product.setProductAmount(tfQuantity.getValue());
            product.setProductCategory(categoriesComboBox.getValue());
            product.setProductName(tfProductName.getText());
            product.setPurchasingPrice(Double.parseDouble(tfPurchasingPrice.getText()));
            product.setSellingPrice(Double.parseDouble(tfSellingPrice.getText()));
            product.setImageURL(imageViewProduct.getImage().getUrl());
            String updateQuery = "update product set product_name = ?, product_purchasing_price = ?, product_selling_price = ?, product_quantity = ?, product_description = ?, img_URL = ? where product_id = ?";
            PreparedStatement statement;
            try {
                ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
                LoginController.conction = a.connectDB();
                statement = LoginController.conction.prepareStatement(updateQuery);
                statement.setString(1, product.getProductName());
                statement.setDouble(2, product.getPurchasingPrice());
                statement.setDouble(3, product.getSellingPrice());
                statement.setInt(4, product.getProductAmount());
                statement.setString(5, product.getProductCaption());
                statement.setString(6, product.getImageURL());
                statement.setInt(7, product.getProductId());
                statement.executeUpdate();
                statement.close();
                productsTable.refresh();

            } catch (SQLException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        });
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
