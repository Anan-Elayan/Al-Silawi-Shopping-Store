package com.example.alsilawi;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.LinkedList;

public class ProductsViewTable {

    SimpleIntegerProperty productId;
    SimpleStringProperty productName;
    SimpleDoubleProperty purchasingPrice;
    SimpleDoubleProperty sellingPrice;
    SimpleIntegerProperty productAmount;
    SimpleStringProperty productCategory;

    SimpleStringProperty productCaption;
    SimpleStringProperty imageURL;

    static LinkedList<ProductsViewTable> products = new LinkedList<>();

    public ProductsViewTable(int productId, String productName, double purchasingPrice, double sellingPrice, int productAmount, String productCategory, String productCaption, String imageURL) {
        this.productId = new SimpleIntegerProperty(productId);
        this.purchasingPrice = new SimpleDoubleProperty(purchasingPrice);
        this.sellingPrice = new SimpleDoubleProperty(sellingPrice);
        this.productName = new SimpleStringProperty(productName);
        this.productCategory = new SimpleStringProperty(productCategory);
        this.productAmount = new SimpleIntegerProperty(productAmount);
        this.productCaption = new SimpleStringProperty(productCaption);
        this.imageURL = new SimpleStringProperty(imageURL);
    }

    public int getProductId() {
        return productId.get();
    }

    public SimpleIntegerProperty productIdProperty() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId.set(productId);
    }

    public String getImageURL() {
        return imageURL.get();
    }

    public SimpleStringProperty imageURLProperty() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL.set(imageURL);
    }

    public String getProductCaption() {
        return productCaption.get();
    }

    public SimpleStringProperty productCaptionProperty() {
        return productCaption;
    }

    public void setProductCaption(String productCaption) {
        this.productCaption.set(productCaption);
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

    public double getPurchasingPrice() {
        return purchasingPrice.get();
    }

    public SimpleDoubleProperty purchasingPriceProperty() {
        return purchasingPrice;
    }

    public void setPurchasingPrice(double purchasingPrice) {
        this.purchasingPrice.set(purchasingPrice);
    }

    public double getSellingPrice() {
        return sellingPrice.get();
    }

    public SimpleDoubleProperty sellingPriceProperty() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice.set(sellingPrice);
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

    public String getProductCategory() {
        return productCategory.get();
    }

    public SimpleStringProperty productCategoryProperty() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory.set(productCategory);
    }
}
