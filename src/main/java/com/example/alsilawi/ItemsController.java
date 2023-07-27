package com.example.alsilawi;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import model.Items;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class ItemsController implements Initializable {


    SpinnerValueFactory<Integer> svf = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100000);

    @FXML
    private Spinner<Integer> spinner;
    @FXML
    private Button btnAddtoCart;
    @FXML
    private ImageView photo;
    @FXML
    private Label lblDescription;
    @FXML
    private Label lblPrice;
    @FXML
    private VBox s;


    public void setData(Items item) {
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream(item.getProduct().getImg_URL())));
        ImageView view = new ImageView(image);
        photo.setImage(image);
        lblPrice.setText(item.getProduct().getProduct_selling_price() + " NIS");
        lblDescription.setText(item.getProduct().getProduct_description());

        btnAddtoCart.setOnAction(e -> {
            Product selectedProduct = item.getProduct();
            TempOrder tempOrder = new TempOrder();
            tempOrder.setProduct(selectedProduct);
            tempOrder.setPrice(selectedProduct.getProduct_selling_price() * spinner.getValue());
            tempOrder.setQuantity(selectedProduct.getProduct_quantity());
            Store store = new Store();
            store.addOrder(tempOrder);
        });
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        svf.setValue(0);
        spinner.setValueFactory(svf);
    }
}
