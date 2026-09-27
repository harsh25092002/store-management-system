package service;

import exception.ProductNotFoundException;
import model.Product;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Manages product records: add, update, remove, search and stock adjustments.
 */
public class InventoryService {

    private final Map<String, Product> products = new LinkedHashMap<>();
    private int nextId = 1;

    public Product addProduct(String name, double price, int quantity) {
        String id = "P" + String.format("%03d", nextId++);
        Product product = new Product(id, name, price, quantity);
        products.put(id, product);
        return product;
    }

    public Product getProduct(String productId) {
        return Optional.ofNullable(products.get(productId))
                .orElseThrow(() -> new ProductNotFoundException(productId));
    }

    public void updatePrice(String productId, double newPrice) {
        getProduct(productId).setPrice(newPrice);
    }

    public void restock(String productId, int amount) {
        getProduct(productId).addStock(amount);
    }

    public void removeProduct(String productId) {
        if (products.remove(productId) == null) {
            throw new ProductNotFoundException(productId);
        }
    }

    public Map<String, Product> getAllProducts() {
        return products;
    }

    public boolean isLowStock(Product product, int threshold) {
        return product.getQuantity() < threshold;
    }
}
