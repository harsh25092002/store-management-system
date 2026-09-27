import exception.ProductNotFoundException;
import model.Bill;
import model.Product;
import service.BillingService;
import service.InventoryService;
import util.ConsoleHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Console-based Store Management System.
 * Manages inventory, product records, billing operations and transaction history,
 * built using core object-oriented Java concepts (encapsulation, single-responsibility
 * services, custom exceptions).
 */
public class StoreManagementApp {

    private static final int LOW_STOCK_THRESHOLD = 5;

    private final InventoryService inventoryService = new InventoryService();
    private final BillingService billingService = new BillingService(inventoryService);
    private final ConsoleHelper console = new ConsoleHelper();

    public static void main(String[] args) {
        new StoreManagementApp().run();
    }

    private void run() {
        seedSampleData();
        boolean running = true;
        while (running) {
            printMenu();
            int choice = console.readInt("Enter choice: ");
            try {
                switch (choice) {
                    case 1 -> addProduct();
                    case 2 -> viewInventory();
                    case 3 -> restockProduct();
                    case 4 -> checkout();
                    case 5 -> viewTransactionHistory();
                    case 6 -> viewLowStockReport();
                    case 0 -> {
                        running = false;
                        System.out.println("Exiting. Goodbye!");
                    }
                    default -> System.out.println("Invalid choice, try again.");
                }
            } catch (ProductNotFoundException | IllegalStateException | IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void printMenu() {
        System.out.println("\n===== STORE MANAGEMENT SYSTEM =====");
        System.out.println("1. Add Product");
        System.out.println("2. View Inventory");
        System.out.println("3. Restock Product");
        System.out.println("4. Checkout / Billing");
        System.out.println("5. View Transaction History");
        System.out.println("6. Low Stock Report");
        System.out.println("0. Exit");
    }

    private void addProduct() {
        String name = console.readLine("Product name: ");
        double price = console.readDouble("Price: ");
        int quantity = console.readInt("Initial quantity: ");
        Product product = inventoryService.addProduct(name, price, quantity);
        System.out.println("Added: " + product);
    }

    private void viewInventory() {
        Map<String, Product> products = inventoryService.getAllProducts();
        if (products.isEmpty()) {
            System.out.println("No products in inventory.");
            return;
        }
        products.values().forEach(System.out::println);
    }

    private void restockProduct() {
        String id = console.readLine("Product ID to restock: ");
        int amount = console.readInt("Amount to add: ");
        inventoryService.restock(id, amount);
        System.out.println("Restocked successfully.");
    }

    private void checkout() {
        List<BillingService.CartItem> cart = new ArrayList<>();
        boolean addingItems = true;
        while (addingItems) {
            String id = console.readLine("Product ID (or 'done' to finish): ");
            if ("done".equalsIgnoreCase(id)) {
                addingItems = false;
                continue;
            }
            int qty = console.readInt("Quantity: ");
            cart.add(new BillingService.CartItem(id, qty));
        }
        if (cart.isEmpty()) {
            System.out.println("Cart is empty, checkout cancelled.");
            return;
        }
        Bill bill = billingService.checkout(cart);
        System.out.println(bill.printReceipt());
    }

    private void viewTransactionHistory() {
        List<Bill> history = billingService.getTransactionHistory();
        if (history.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }
        history.forEach(b -> System.out.println(b.printReceipt()));
        System.out.printf("Total revenue so far: Rs.%.2f%n", billingService.getTotalRevenue());
    }

    private void viewLowStockReport() {
        inventoryService.getAllProducts().values().stream()
                .filter(p -> inventoryService.isLowStock(p, LOW_STOCK_THRESHOLD))
                .forEach(p -> System.out.println("LOW STOCK: " + p));
    }

    private void seedSampleData() {
        inventoryService.addProduct("Notebook", 45.0, 50);
        inventoryService.addProduct("Pen", 10.0, 100);
        inventoryService.addProduct("Stapler", 120.0, 15);
    }
}
