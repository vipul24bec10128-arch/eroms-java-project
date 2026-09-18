package com.eroms.service;

import com.eroms.exception.InsufficientStockException;
import com.eroms.model.Product;
import com.eroms.repository.Repository;

import java.util.List;

public class InventoryService {
    private final Repository<Product, String> productRepository;

    public InventoryService(Repository<Product, String> productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    public Product getProduct(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public synchronized void reserveStock(String productId, int quantity) throws InsufficientStockException {
        Product p = getProduct(productId);
        if (p == null) throw new IllegalArgumentException("Product ID does not exist.");
        if (p.getStockQuantity() < quantity) {
            throw new InsufficientStockException("Insufficient stock for: " + p.getName());
        }
        p.reduceStock(quantity);
        productRepository.save(p);
    }

    public synchronized void releaseStock(String productId, int quantity) {
        Product p = getProduct(productId);
        if (p != null) {
            p.addStock(quantity);
            productRepository.save(p);
        }
    }
}