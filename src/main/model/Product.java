package model;

/**
 * Represents an item of inventory in the store.
 */
public class Product {

    private final String productId;
    private String name;
    private double price;
    private int quantity;

    public Product(String productId, String name, double price, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void reduceStock(int amount) {
        if (amount > quantity) {
            throw new IllegalArgumentException("Insufficient stock for product: " + name);
        }
        quantity -= amount;
    }

    public void addStock(int amount) {
        quantity += amount;
    }

    @Override
    public String toString() {
        return String.format("%-8s %-20s Rs.%-10.2f Qty:%-5d", productId, name, price, quantity);
    }
}
