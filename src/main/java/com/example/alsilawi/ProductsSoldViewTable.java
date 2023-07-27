package com.example.alsilawi;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.LinkedList;

public class ProductsSoldViewTable {
    SimpleStringProperty productName;
    SimpleStringProperty productCategory;
    SimpleDoubleProperty productPrice;
    SimpleIntegerProperty productAmount;
    static LinkedList<ProductsSoldViewTable> products = new LinkedList<>();

    public ProductsSoldViewTable(String productName, String productCategory, double productPrice, int productAmount) {
        this.productName = new SimpleStringProperty(productName);
        this.productCategory = new SimpleStringProperty(productCategory);
        this.productPrice = new SimpleDoubleProperty(productPrice);
        this.productAmount = new SimpleIntegerProperty(productAmount);
    }

    public String getProductName() {
        return productName.get();
    }

    public SimpleStringProperty productNameProperty() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName.set(productName);
    }

    public String getProductCategory() {
        return productCategory.get();
    }

    public SimpleStringProperty productCategoryProperty() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory.set(productCategory);
    }

    public double getProductPrice() {
        return productPrice.get();
    }

    public SimpleDoubleProperty productPriceProperty() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice.set(productPrice);
    }

    public int getProductAmount() {
        return productAmount.get();
    }

    public SimpleIntegerProperty productAmountProperty() {
        return productAmount;
    }

    public void setProductAmount(int productAmount) {
        this.productAmount.set(productAmount);
    }
}
