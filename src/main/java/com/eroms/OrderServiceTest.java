package com.eroms;

import com.eroms.exception.InsufficientStockException;
import com.eroms.exception.InvalidOrderException;
import com.eroms.model.*;
import com.eroms.repository.FileStorageEngine;
import com.eroms.service.InventoryService;
import com.eroms.service.OrderService;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {
    private InventoryService inventoryService;
    private OrderService orderService;
    private final String testProductFile = "test_products.dat";
    private final String testOrderFile = "test_orders.dat";

    @BeforeEach
    void setUp() {
        new File(testProductFile).delete();
        new File(testOrderFile).delete();

        var pRepo = new FileStorageEngine<Product, String>(testProductFile);
        var oRepo = new FileStorageEngine<Order, String>(testOrderFile);

        inventoryService = new InventoryService(pRepo);
        orderService = new OrderService(oRepo, inventoryService);

        inventoryService.addProduct(new Product("ITEM1", "Test Product", 50.0, 10));
    }

    @AfterEach
    void tearDown() {
        new File(testProductFile).delete();
        new File(testOrderFile).delete();
    }

    @Test
    void testOrderCreationSuccess() throws InsufficientStockException, InvalidOrderException {
        Product p = inventoryService.getProduct("ITEM1");
        OrderItem item = new OrderItem(p, 2);

        Order order = orderService.createOrder("O-100", "24BEC10128", List.of(item));

        assertEquals(OrderStatus.CONFIRMED, order.getStatus());
        assertEquals(100.0, order.getTotalAmount());
        assertEquals(8, inventoryService.getProduct("ITEM1").getStockQuantity());
    }

    @Test
    void testOrderFailsWhenStockInsufficient() {
        Product p = inventoryService.getProduct("ITEM1");
        OrderItem item = new OrderItem(p, 20);

        assertThrows(InsufficientStockException.class, () -> {
            orderService.createOrder("O-101", "24BEC10128", List.of(item));
        });
    }

    @Test
    void testOrderCancellationRestoresInventory() throws InsufficientStockException, InvalidOrderException {
        Product p = inventoryService.getProduct("ITEM1");
        OrderItem item = new OrderItem(p, 4);

        orderService.createOrder("O-102", "24BEC10128", List.of(item));
        assertEquals(6, inventoryService.getProduct("ITEM1").getStockQuantity());

        orderService.cancelOrder("O-102");
        assertEquals(10, inventoryService.getProduct("ITEM1").getStockQuantity());
    }
}