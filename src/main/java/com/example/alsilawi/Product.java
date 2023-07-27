package com.example.alsilawi;

public class Product {

    private int ProductID;
    private String ProductName;
    private Double ProductPurchasingPrice;
    private Double product_selling_price;
    private int product_quantity;
    private String product_description;
    private int categories_id;
    private String img_URL;

    public Product(int productID, String productName, Double productPurchasingPrice, Double product_selling_price, int product_quantity, String product_description, int categories_id, String img_URL) {
        ProductID = productID;
        ProductName = productName;
        ProductPurchasingPrice = productPurchasingPrice;
        this.product_selling_price = product_selling_price;
        this.product_quantity = product_quantity;
        this.product_description = product_description;
        this.categories_id = categories_id;
        this.img_URL = img_URL;
    }

    public Product() {
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public Double getProductPurchasingPrice() {
        return ProductPurchasingPrice;
    }

    public void setProductPurchasingPrice(Double productPurchasingPrice) {
        ProductPurchasingPrice = productPurchasingPrice;
    }

    public Double getProduct_selling_price() {
        return product_selling_price;
    }

    public void setProduct_selling_price(Double product_selling_price) {
        this.product_selling_price = product_selling_price;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public int getCategories_id() {
        return categories_id;
    }

    public void setCategories_id(int categories_id) {
        this.categories_id = categories_id;
    }

    public String getImg_URL() {
        return img_URL;
    }

    public void setImg_URL(String img_URL) {
        this.img_URL = img_URL;
    }
}
