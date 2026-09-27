# Store Management System

A console-based Store Management System built entirely with **Core Java**. It manages inventory, product records, billing, and transaction history using clean object-oriented design — no frameworks or external libraries required.

## Features
- Add, view and restock products
- Interactive billing/checkout flow that builds a multi-item cart and generates a receipt
- Stock validation before checkout is committed (prevents partial/inconsistent deductions)
- Transaction history with total revenue tracking
- Low-stock report
- Custom exception handling (`ProductNotFoundException`) for invalid product operations

## Tech Stack
Java 17 (no external dependencies), OOP principles (encapsulation, single-responsibility services, records)

## Project Structure
```
src/main
├── StoreManagementApp.java   # Entry point + console menu
├── model/                    # Product, Bill
├── service/                  # InventoryService, BillingService
├── util/                     # ConsoleHelper (input handling)
└── exception/                 # ProductNotFoundException
```

## Running Locally
```bash
javac -d out $(find src/main -name "*.java")
java -cp out StoreManagementApp
```

The app starts with a few sample products pre-loaded (Notebook, Pen, Stapler) so you can try checkout right away.

## Sample Session
```
===== STORE MANAGEMENT SYSTEM =====
1. Add Product
2. View Inventory
3. Restock Product
4. Checkout / Billing
5. View Transaction History
6. Low Stock Report
0. Exit
Enter choice: 4
Product ID (or 'done' to finish): P001
Quantity: 2
Product ID (or 'done' to finish): done
===== RECEIPT BILL0001 =====
...
TOTAL: Rs.90.00
```
