package com.eroms;

import com.eroms.model.*;
import com.eroms.repository.FileStorageEngine;
import com.eroms.service.*;
import com.eroms.util.InputValidator;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        // Core Repository Initialization
        var userRepo = new FileStorageEngine<User, String>("users.dat");
        var productRepo = new FileStorageEngine<Product, String>("products.dat");
        var orderRepo = new FileStorageEngine<Order, String>("orders.dat");

        // Service Dependencies Wiring
        var authService = new AuthenticationService(userRepo);
        var inventoryService = new InventoryService(productRepo);
        var orderService = new OrderService(orderRepo, inventoryService);
        var analyticsService = new AnalyticsService(orderService, inventoryService);

        // Pre-populate demo baseline catalog if empty
        if (inventoryService.listAllProducts().isEmpty()) {
            inventoryService.addProduct(new Product("P101", "Mechanical Keyboard", 89.99, 15));
            inventoryService.addProduct(new Product("P102", "Wireless Mouse", 29.50, 40));
            inventoryService.addProduct(new Product("P103", "Curved Monitor 27\"", 249.00, 4));
        }

        // Pre-register student user
        authService.registerCustomer("24BEC10128", "vipul", "pass123", "vipul.malviya@domain.com");

        Scanner scanner = new Scanner(System.in);
        System.out.println("=================================================");
        System.out.println("  Enterprise Retail Order Management (EROMS)");
        System.out.println("  Developer: vipul malviya | Reg No: 24BEC10128");
        System.out.println("=================================================");

        while (true) {
            System.out.println("\n1. View Inventory Catalog");
            System.out.println("2. Place New Order");
            System.out.println("3. View My Orders");
            System.out.println("4. Cancel Order");
            System.out.println("5. Operational Analytics");
            System.out.println("6. Exit");
            System.out.print("Select Menu Option: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> {
                    System.out.println("\n--- Current Inventory ---");
                    inventoryService.listAllProducts().forEach(System.out::println);
                }
                case "2" -> {
                    System.out.print("Enter Product ID: ");
                    String pid = scanner.nextLine().trim();
                    Product prod = inventoryService.getProduct(pid);

                    if (prod == null) {
                        System.out.println("Product was not found.");
                        break;
                    }

                    System.out.print("Enter Quantity: ");
                    try {
                        int qty = Integer.parseInt(scanner.nextLine().trim());
                        if (qty <= 0) {
                            System.out.println("Quantity must be greater than zero.");
                            break;
                        }
                        String orderId = "ORD-" + UUID.randomUUID().toString().substring(0, 6);
                        OrderItem item = new OrderItem(prod, qty);
                        Order created = orderService.createOrder(orderId, "24BEC10128", List.of(item));
                        System.out.printf("Order placed successfully! ID: %s | Total: $%.2f%n", 
                                created.getOrderId(), created.getTotalAmount());
                    } catch (Exception e) {
                        System.out.println("Failed to execute order: " + e.getMessage());
                    }
                }
                case "3" -> {
                    System.out.println("\n--- Historical Orders for 24BEC10128 ---");
                    var list = orderService.getOrdersByCustomer("24BEC10128");
                    if (list.isEmpty()) {
                        System.out.println("No recorded orders.");
                    } else {
                        list.forEach(o -> System.out.printf("ID: %s | Status: %s | Total: $%.2f%n", 
                                o.getOrderId(), o.getStatus(), o.getTotalAmount()));
                    }
                }
                case "4" -> {
                    System.out.print("Enter Order ID to Cancel: ");
                    String oid = scanner.nextLine().trim();
                    try {
                        orderService.cancelOrder(oid);
                        System.out.println("Order cancelled and inventory restored.");
                    } catch (Exception e) {
                        System.out.println("Cancellation failed: " + e.getMessage());
                    }
                }
                case "5" -> analyticsService.printAnalyticsReport();
                case "6" -> {
                    System.out.println("Terminating session. Goodbye.");
                    return;
                }
                default -> System.out.println("Invalid input selection.");
            }
        }
    }
}