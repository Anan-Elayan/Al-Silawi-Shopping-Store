package model;


import com.example.alsilawi.Product;
import javafx.scene.control.Button;


public class Items {

    private Product product;
    private int quantity;
    private Button button = new Button();

    public Items() {
        this.product = new Product();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }


}
