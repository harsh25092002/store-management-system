package service;

import model.Bill;
import model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles the billing/checkout process and keeps a history of past transactions.
 */
public class BillingService {

    private final InventoryService inventoryService;
    private final List<Bill> transactionHistory = new ArrayList<>();
    private int nextBillId = 1;

    public BillingService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    public Bill checkout(List<CartItem> cartItems) {
        String billId = "BILL" + String.format("%04d", nextBillId++);
        Bill bill = new Bill(billId);

        // First pass: validate stock for every item before committing any change,
        // so a failed item does not leave inventory partially deducted.
        for (CartItem item : cartItems) {
            Product product = inventoryService.getProduct(item.productId());
            if (product.getQuantity() < item.quantity()) {
                throw new IllegalStateException("Insufficient stock for: " + product.getName());
            }
        }

        for (CartItem item : cartItems) {
            Product product = inventoryService.getProduct(item.productId());
            product.reduceStock(item.quantity());
            bill.addItem(new Bill.LineItem(product.getProductId(), product.getName(), product.getPrice(), item.quantity()));
        }

        transactionHistory.add(bill);
        return bill;
    }

    public List<Bill> getTransactionHistory() {
        return transactionHistory;
    }

    public double getTotalRevenue() {
        return transactionHistory.stream().mapToDouble(Bill::getTotal).sum();
    }

    public record CartItem(String productId, int quantity) {
    }
}
