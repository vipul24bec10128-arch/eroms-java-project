package com.eroms.model;

import java.io.Serializable;

public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private double unitPrice;
    private int stockQuantity;

    public Product(String id, String name, double unitPrice, int stockQuantity) {
        this.id = id;
        this.name = name;
        this.unitPrice = unitPrice;
        this.stockQuantity = stockQuantity;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public double getUnitPrice() { return unitPrice; }
    public int getStockQuantity() { return stockQuantity; }

    public synchronized void reduceStock(int quantity) {
        this.stockQuantity -= quantity;
    }

    public synchronized void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    @Override
    public String toString() {
        return String.format("[%s] %-15s | Price: $%.2f | Stock: %d", id, name, unitPrice, stockQuantity);
    }
}