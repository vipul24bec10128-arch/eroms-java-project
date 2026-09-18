package com.eroms.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    private String orderId;
    private String customerId;
    private List<OrderItem> items;
    private OrderStatus status;
    private LocalDateTime timestamp;

    public Order(String orderId, String customerId) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.items = new ArrayList<>();
        this.status = OrderStatus.PENDING;
        this.timestamp = LocalDateTime.now();
    }

    public void addItem(OrderItem item) { items.add(item); }
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public List<OrderItem> getItems() { return items; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public double getTotalAmount() {
        return items.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }
}