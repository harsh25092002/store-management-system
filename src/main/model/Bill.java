package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a single billing transaction made up of one or more line items.
 */
public class Bill {

    public static class LineItem {
        public final String productId;
        public final String productName;
        public final double unitPrice;
        public final int quantity;

        public LineItem(String productId, String productName, double unitPrice, int quantity) {
            this.productId = productId;
            this.productName = productName;
            this.unitPrice = unitPrice;
            this.quantity = quantity;
        }

        public double getSubtotal() {
            return unitPrice * quantity;
        }
    }

    private final String billId;
    private final LocalDateTime timestamp;
    private final List<LineItem> items = new ArrayList<>();

    public Bill(String billId) {
        this.billId = billId;
        this.timestamp = LocalDateTime.now();
    }

    public void addItem(LineItem item) {
        items.add(item);
    }

    public double getTotal() {
        return items.stream().mapToDouble(LineItem::getSubtotal).sum();
    }

    public String getBillId() {
        return billId;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public List<LineItem> getItems() {
        return items;
    }

    public String printReceipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("===== RECEIPT ").append(billId).append(" =====\n");
        sb.append("Date: ").append(timestamp).append("\n");
        sb.append(String.format("%-20s %8s %6s %10s%n", "Item", "Price", "Qty", "Subtotal"));
        for (LineItem item : items) {
            sb.append(String.format("%-20s %8.2f %6d %10.2f%n",
                    item.productName, item.unitPrice, item.quantity, item.getSubtotal()));
        }
        sb.append("--------------------------------------------\n");
        sb.append(String.format("TOTAL: Rs.%.2f%n", getTotal()));
        return sb.toString();
    }
}
