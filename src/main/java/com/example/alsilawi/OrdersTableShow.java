package com.example.alsilawi;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.LinkedList;
import java.util.List;

public class OrdersTableShow {
    SimpleIntegerProperty orderId;
    SimpleStringProperty orderDate;
    SimpleStringProperty customerName;
    SimpleStringProperty customerPhone;
    SimpleStringProperty location;
    SimpleDoubleProperty totalPrice;
    SimpleStringProperty paymentType;
    static LinkedList<OrdersTableShow> orders = new LinkedList<>();

    public OrdersTableShow(int orderId, String orderDate, String customerName, String customerPhone, String location, double totalPrice, String paymentType) {
        this.orderId = new SimpleIntegerProperty(orderId);
        this.orderDate = new SimpleStringProperty(orderDate);
        this.customerPhone = new SimpleStringProperty(customerPhone);
        this.location = new SimpleStringProperty(location);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.customerName = new SimpleStringProperty(customerName);
        this.paymentType = new SimpleStringProperty(paymentType);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public SimpleIntegerProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public String getOrderDate() {
        return orderDate.get();
    }

    public SimpleStringProperty orderDateProperty() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate.set(orderDate);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public SimpleStringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
    }

    public String getCustomerPhone() {
        return customerPhone.get();
    }

    public SimpleStringProperty customerPhoneProperty() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone.set(customerPhone);
    }

    public String getLocation() {
        return location.get();
    }

    public SimpleStringProperty locationProperty() {
        return location;
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public SimpleDoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public String getPaymentType() {
        return paymentType.get();
    }

    public SimpleStringProperty paymentTypeProperty() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType.set(paymentType);
    }
}
