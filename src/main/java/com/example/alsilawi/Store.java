package com.example.alsilawi;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class Store implements Initializable {
    @FXML
    private AnchorPane bacsepane = new AnchorPane();
    @FXML
    private ColorPicker theam;
    @FXML
    private ScrollPane ScrollPane;
    @FXML
    private Button btnSetting;
    @FXML
    private Button btnSignOut;
    @FXML
    private Button btnCancel;
    @FXML
    private Button btnPay;
    @FXML
    private Button btnReject;
    @FXML
    private ComboBox<String> comboBoxCategories;
    @FXML
    private GridPane gridItems;
    @FXML
    private Label lblPrice;
    @FXML
    private RadioButton radioButtonFromHight;
    @FXML
    private RadioButton radioButtonFromLow;
    @FXML
    private ToggleGroup groupFilter;
    @FXML
    private TableColumn<TempOrder, Double> price;
    @FXML
    private TableColumn<TempOrder, Integer> qun;
    @FXML
    private TableColumn<TempOrder, String> name;
    @FXML
    private TableView<TempOrder> products;
    static ObservableList<TempOrder> items;
    private double total;
    static double tempPrice;


    @FXML
    void ActionExit(ActionEvent event)  {
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
        FXMLLoader root = new FXMLLoader(getClass().getResource("Setting.fxml"));
        Parent parent = root.load();
        Stage stage = new Stage();
        stage.setTitle("Setting Page");
        stage.getIcons().add(new Image("/images/settings.png"));
        stage.setScene(new Scene(parent));
        stage.show();
    }


    @FXML
    void ActionPay(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnPay.getScene().getWindow();
        PaymentMethod.setData(products.getItems(), Double.parseDouble(lblPrice.getText()));
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("payment method.fxml")));
        stage.setTitle("My Cart Page");
        stage.setScene(new Scene(root));
    }

    public void addOrder(TempOrder tempOrder) {
        products.getItems().add(tempOrder);
        products.refresh();
    }


    // action pay + cancel + reject
    public VBox addItem(Product product) throws SQLException {

        VBox basecVBox = new VBox();
        HBox topHbox = new HBox();
        HBox buttonHbox = new HBox(10);
        AnchorPane anchorPane = new AnchorPane();
        Button btnAddToCart = new Button("Add to Cart");
        Label lblDescription = new Label();
        Label productPrice = new Label();
        ImageView imageView = new ImageView();
        Spinner<Integer> spinner = new Spinner<>();
        SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000);
        svf.setValue(0);
        spinner.setValueFactory(svf);

        lblDescription.setText(product.getProduct_description());
        productPrice.setText(String.valueOf(product.getProduct_selling_price()));
        imageView.setImage(new Image(product.getImg_URL()));

        basecVBox.setStyle("-fx-background-color:black");
        basecVBox.setAlignment(Pos.TOP_LEFT);
        basecVBox.setScaleX(1);
        basecVBox.setScaleY(1);
        basecVBox.setScaleZ(1);

        topHbox.setAlignment(Pos.CENTER_LEFT);
        topHbox.setPadding(new Insets(5, 3, 10, 0));
        lblDescription.setPrefHeight(24);
        lblDescription.setPrefWidth(300);
        lblDescription.setPadding(new Insets(0, 0, 0, 9));
        productPrice.setPrefHeight(19);
        productPrice.setPrefWidth(150);
        productPrice.setPadding(new Insets(0, 0, 0, 14));


        topHbox.setScaleX(1);
        topHbox.setScaleY(1);
        topHbox.setScaleZ(1);
        lblDescription.setStyle("-fx-font-size:20;-fx-font-style:bold;-fx-text-fill: white");
        price.setStyle("-fx-text-fill: red;-fx-font-size:15;-fx-font-style:bold");
        productPrice.setStyle("-fx-text-fill: red;-fx-font-size:15;-fx-font-style:bold");
        topHbox.getChildren().addAll(lblDescription, productPrice);


        imageView.setFitHeight(130);
        imageView.setFitWidth(200);
        anchorPane.setScaleX(1);
        anchorPane.setScaleY(1);
        anchorPane.setScaleZ(1);

        AnchorPane.setLeftAnchor(imageView, 55.0);
        anchorPane.getChildren().addAll(imageView);


        buttonHbox.setAlignment(Pos.CENTER);
        btnAddToCart.setStyle("-fx-background-radius:0;-fx-border-color: #eda723;-fx-border-radius:0;-fx-background-color:black;-fx-text-fill:white;-fx-font-size:17;-fx-font-style:bold");
        btnAddToCart.setAlignment(Pos.CENTER_LEFT);

        btnAddToCart.setScaleX(1);
        btnAddToCart.setScaleY(1);
        btnAddToCart.setScaleZ(1);
        HBox.setMargin(btnAddToCart, new Insets(0, 10, 8, 10));
        HBox.setMargin(spinner, new Insets(0, 8, 8, 5));
        spinner.setPrefHeight(29);
        spinner.setPrefWidth(150);

        spinner.setScaleX(1);
        spinner.setScaleY(1);
        spinner.setScaleZ(1);

        buttonHbox.setScaleX(1);
        buttonHbox.setScaleY(1);
        buttonHbox.setScaleZ(1);
        buttonHbox.setPadding(new Insets(10, 0, 0, 0));

        buttonHbox.getChildren().addAll(btnAddToCart, spinner);
        basecVBox.getChildren().addAll(topHbox, anchorPane, buttonHbox);


        btnAddToCart.setOnAction(e -> {  //************************************************************** add to cart
            String Quantity = "select product.product_quantity from product where product_id=? ";
            try {
                PreparedStatement stmQuantity = LoginController.conction.prepareStatement(Quantity);
                stmQuantity.setInt(1, product.getProductID());
                ResultSet result = stmQuantity.executeQuery();
                result.next();
                int q = result.getInt("product_quantity");
                if (spinner.getValue() > q) {
                    WindowSure windowSure = new WindowSure();
                    windowSure.getLbl().setText("The quantity ordered is not enough");
                    windowSure.getButtonSave().setText("OK");
                    windowSure.getButtonSave().setLayoutX(180);
                    windowSure.getButtonCancel().setVisible(false);
                    windowSure.getStage().show();
                    windowSure.getButtonSave().setOnAction(e2 -> {
                        windowSure.getStage().close();
                    });

                } else {
                    TempOrder temp = new TempOrder();
                    temp.setQuantity(spinner.getValue());
                    temp.setProduct(product);
                    int edited = 0;
                    for (int i = 0; i < products.getItems().size(); i++) {
                        if (product.getProductID() == products.getItems().get(i).getProduct().getProductID()) {
                            this.total -= products.getItems().get(i).getPrice();
                            products.getItems().get(i).setQuantity(temp.getQuantity());
                            products.getItems().get(i).setPrice(temp.getProduct().getProduct_selling_price() * temp.getQuantity());
                            edited = 1;
                            this.total += products.getItems().get(i).getPrice();
                            lblPrice.setText(this.total + "");
                            break;
                        }
                        products.refresh();
                    }

                    double total = spinner.getValue() * product.getProduct_selling_price();
                    temp.setPrice(total);
                    if (spinner.getValue() != 0 && edited == 0) {
                        this.total += spinner.getValue() * product.getProduct_selling_price();
                        lblPrice.setText(this.total + "");
                        products.getItems().add(temp);//*************************************************** add to table
                    }
                    if (products.getSelectionModel().getSelectedItem() == null) {
                        products.refresh();

                        btnCancel.setOnAction(c -> { /////////////////////*************************************** cancel
                            if (products.getSelectionModel().getSelectedItem() != null) {
                                TempOrder tempOrder = products.getSelectionModel().getSelectedItem();
                                products.getItems().remove(tempOrder);
                                this.total -= tempOrder.getPrice();
                                lblPrice.setText(this.total + "");
                                products.refresh();
                            } else {
                                WindowSure windowSure = new WindowSure();
                                windowSure.getLbl().setText("\t\t Please select item \nthat you want to delete from the table");
                                windowSure.getButtonSave().setText("Ok");
                                windowSure.getLbl().setLayoutX(50);
                                windowSure.getStage().setMaxWidth(599);
                                windowSure.getButtonSave().setLayoutY(105);
                                windowSure.getButtonSave().setLayoutX(190);
                                windowSure.getButtonCancel().setVisible(false);
                                windowSure.getButtonSave().setOnAction(event -> {
                                    windowSure.getStage().close();
                                });
                            }
                        });
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            btnReject.setOnAction(ca -> {  //************************************************ reject
                products.getItems().removeAll(products.getItems());
                products.refresh();
                this.total = 0;
                lblPrice.setText("0.0");
                products.refresh();
                products.refresh();
            });
        });//// ********************************************************** action add to cart
        return basecVBox;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        theam.setOnAction(event -> { // to change them
            Color color = theam.getValue();
            gridItems.setStyle("-fx-background-color:" + toRgbString(color));
        });


        if (items != null) {
            products.setItems(items);
        }
        lblPrice.setText(String.valueOf(tempPrice));
        radioButtonFromHight.setSelected(true);

        radioButtonFromHight.setToggleGroup(groupFilter);
        radioButtonFromLow.setToggleGroup(groupFilter);
        name.setCellValueFactory(data -> new ReadOnlyStringWrapper(data.getValue().getProduct().getProductName()));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        qun.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        comboBoxCategories.getItems().addAll("Games", "Antiques", "Kitchen appliances");

        final Product[] product = new Product[1];
        final int[] column = {1};
        final int[] row = {1};
        try {
            ConnectionToDataBase a = new ConnectionToDataBase(LoginController.URL, LoginController.port, LoginController.dbName, LoginController.dbUsername, LoginController.dbPassword);
            LoginController.conction = a.connectDB();

            AtomicReference<String> queryToFillDesc = new AtomicReference<>("select product_id, product_name,product_selling_price,product_description, img_URL from product order by product_selling_price desc");
            AtomicReference<PreparedStatement> stm = new AtomicReference<>(LoginController.conction.prepareStatement(queryToFillDesc.get()));
            AtomicReference<ResultSet> results = new AtomicReference<>(stm.get().executeQuery());


            // as a default
            if (radioButtonFromHight.isSelected()) {
                column[0] = 1;
                row[0] = 1;
                while (results.get().next()) {
                    product[0] = new Product();
                    product[0].setProductID(results.get().getInt("product_id"));
                    product[0].setProductName(results.get().getString("product_name"));
                    product[0].setProduct_selling_price(results.get().getDouble("product_selling_price"));
                    product[0].setProduct_description(results.get().getString("product_description"));
                    product[0].setImg_URL(results.get().getString("img_URL"));
                    VBox vBox = this.addItem(product[0]);
                    if (column[0] == 3) {
                        column[0] = 1;
                        ++row[0];
                    }
                    gridItems.add(vBox, column[0]++, row[0]);
                }
            }

            // action filter for price
            groupFilter.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (radioButtonFromLow != null && radioButtonFromLow.isSelected()) {
                    column[0] = 1;
                    row[0] = 1;
                    gridItems.getChildren().clear();
                    try {
                        String queryToFillAsc = "select product_id, product_name,product_selling_price,product_description, img_URL from product order by product_selling_price asc";
                        PreparedStatement stmAsc = LoginController.conction.prepareStatement(queryToFillAsc);
                        ResultSet resultsAsc;
                        resultsAsc = stmAsc.executeQuery();
                        while (resultsAsc.next()) {
                            product[0] = new Product();
                            product[0].setProductID(resultsAsc.getInt("product_id"));
                            product[0].setProductName(resultsAsc.getString("product_name"));
                            product[0].setProduct_selling_price(resultsAsc.getDouble("product_selling_price"));
                            product[0].setProduct_description(resultsAsc.getString("product_description"));
                            product[0].setImg_URL(resultsAsc.getString("img_URL"));
                            VBox vBox = this.addItem(product[0]);
                            if (column[0] == 3) {
                                column[0] = 1;
                                ++row[0];
                            }
                            gridItems.add(vBox, column[0]++, row[0]);
                        }
                        stmAsc.close();
                        resultsAsc.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (radioButtonFromHight != null && radioButtonFromHight.isSelected()) {
                    column[0] = 1;
                    row[0] = 1;
                    gridItems.getChildren().clear();
                    try {
                        queryToFillDesc.set("select product_id, product_name,product_selling_price,product_description, img_URL from product order by product_selling_price desc");
                        stm.set(LoginController.conction.prepareStatement(queryToFillDesc.get()));
                        results.set(stm.get().executeQuery());
                        while (results.get().next()) {
                            product[0] = new Product();
                            product[0].setProductID(results.get().getInt("product_id"));
                            product[0].setProductName(results.get().getString("product_name"));
                            product[0].setProduct_selling_price(results.get().getDouble("product_selling_price"));
                            product[0].setProduct_description(results.get().getString("product_description"));
                            product[0].setImg_URL(results.get().getString("img_URL"));
                            VBox vBox = this.addItem(product[0]);
                            if (column[0] == 3) {
                                column[0] = 1;
                                ++row[0];
                            }
                            gridItems.add(vBox, column[0]++, row[0]);
                        }
                        stm.get().close();
                        results.get().close();
                    } catch (SQLException E) {
                    }
                }
            });


            // filter combo box by categories
            comboBoxCategories.setOnAction(event -> {
                if (comboBoxCategories.getValue() == "Games") {
                    column[0] = 1;
                    row[0] = 1;
                    gridItems.getChildren().clear();
                    try {
                        String StatmentGames = "select * from product where categories_id=1;";// 1 = game
                        PreparedStatement stmGames = LoginController.conction.prepareStatement(StatmentGames);
                        ResultSet resultSetGames;
                        resultSetGames = stmGames.executeQuery();

                        while (resultSetGames.next()) {
                            product[0] = new Product();
                            product[0].setProductID(resultSetGames.getInt("product_id"));
                            product[0].setProductName(resultSetGames.getString("product_name"));
                            product[0].setProduct_selling_price(resultSetGames.getDouble("product_selling_price"));
                            product[0].setProduct_description(resultSetGames.getString("product_description"));
                            product[0].setImg_URL(resultSetGames.getString("img_URL"));
                            VBox vBox = this.addItem(product[0]);
                            if (column[0] == 3) {
                                column[0] = 1;
                                ++row[0];
                            }
                            gridItems.add(vBox, column[0]++, row[0]);
                        }
                        stmGames.close();
                        resultSetGames.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (comboBoxCategories.getValue() == "Antiques") {
                    column[0] = 1;
                    row[0] = 1;
                    gridItems.getChildren().clear();
                    try {
                        String StatmentGames = "select * from product where categories_id=2;";
                        PreparedStatement stmGames = LoginController.conction.prepareStatement(StatmentGames);
                        ResultSet resultSetGames;
                        resultSetGames = stmGames.executeQuery();

                        while (resultSetGames.next()) {
                            product[0] = new Product();
                            product[0].setProductID(resultSetGames.getInt("product_id"));
                            product[0].setProductName(resultSetGames.getString("product_name"));
                            product[0].setProduct_selling_price(resultSetGames.getDouble("product_selling_price"));
                            product[0].setProduct_description(resultSetGames.getString("product_description"));
                            product[0].setImg_URL(resultSetGames.getString("img_URL"));
                            VBox vBox = this.addItem(product[0]);
                            if (column[0] == 3) {
                                column[0] = 1;
                                ++row[0];
                            }
                            gridItems.add(vBox, column[0]++, row[0]);
                        }
                        stmGames.close();
                        resultSetGames.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
                if (comboBoxCategories.getValue() == "Kitchen appliances") {
                    column[0] = 1;
                    row[0] = 1;
                    gridItems.getChildren().clear();
                    try {
                        String StatmentGames = "select * from product where categories_id=3;";
                        PreparedStatement stmGames = LoginController.conction.prepareStatement(StatmentGames);
                        ResultSet resultSetGames;
                        resultSetGames = stmGames.executeQuery();

                        while (resultSetGames.next()) {
                            product[0] = new Product();
                            product[0].setProductID(resultSetGames.getInt("product_id"));
                            product[0].setProductName(resultSetGames.getString("product_name"));
                            product[0].setProduct_selling_price(resultSetGames.getDouble("product_selling_price"));
                            product[0].setProduct_description(resultSetGames.getString("product_description"));
                            product[0].setImg_URL(resultSetGames.getString("img_URL"));
                            VBox vBox = this.addItem(product[0]);
                            if (column[0] == 3) {
                                column[0] = 1;
                                ++row[0];
                            }
                            gridItems.add(vBox, column[0]++, row[0]);
                        }
                        stmGames.close();
                        resultSetGames.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            });


            stm.get().close();
            results.get().close();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }








    public static void setData(ObservableList<TempOrder> items, double totalPrice) {
        Store.items = items;
        tempPrice = totalPrice;
    }
    private String toRgbString(Color color) {
        return String.format("rgb(%d, %d, %d)",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
    }
}