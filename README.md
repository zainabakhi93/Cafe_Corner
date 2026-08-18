# Café Point of Sale (POS) System

A Java Swing desktop application for managing a café's daily orders, menu, and staff — built with a MySQL backend, role-based authentication, and multithreading.

Built for the **Advanced Programming** course.

**Team:** Zainab & Habiba

---

## What It Does

A single desktop app that runs a café's front counter and back office:

- **Cashiers** log in, browse the live menu, build a cart, and place orders.
- **Admins** log in to manage the menu (add/edit/soft-delete items), manage staff accounts, and view sales reports and full order history.
- Every order is saved to a real MySQL database — with a live-updating clock running on its own thread, and password security via SHA-256 hashing.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java |
| UI | Java Swing |
| Database | MySQL (via XAMPP / phpMyAdmin) |
| Connectivity | JDBC (`mysql-connector-j`) |
| Architecture | DAO pattern (`UserDAO`, `MenuDAO`, `OrderDAO`) |
| Security | SHA-256 password hashing |
| Concurrency | Custom `Thread` subclass for the live clock |

---

## Project Structure

```
Project/
├── src/
│   ├── Backend/          # Data layer — DB connection, DAOs, models, hashing, threading
│   │   ├── DBConnection.java
│   │   ├── UserDAO.java
│   │   ├── MenuDAO.java
│   │   ├── OrderDAO.java
│   │   ├── User.java
│   │   ├── MenuItem.java
│   │   ├── CartItem.java
│   │   ├── PasswordUtil.java
│   │   └── ClockThread.java
│   ├── Frontend/         # Swing UI — every screen in the app
│   │   ├── LoginFrame.java
│   │   ├── AdminDashboard.java
│   │   ├── CashierDashboard.java
│   │   ├── NewOrderFrame.java
│   │   ├── ManageMenuFrame.java
│   │   ├── ManageStaffFrame.java
│   │   ├── AllOrdersFrame.java
│   │   ├── OrderHistoryFrame.java
│   │   ├── SalesReportFrame.java
│   │   ├── UITheme.java
│   │   └── cafe_bg.jpg
│   └── Main.java         # Entry point
├── lib/
│   └── mysql-connector-j-9.7.0/mysql-connector-j-9.7.0.jar
└── .vscode/settings.json
```

`Backend` and `Frontend` are plain folders (default Java package) — kept separate purely for readability, not as Java packages, so no imports are needed between them.

---

## Database Schema

Four MySQL tables:

- **users** — login credentials, role (`cashier` / `admin`), full name
- **menu_items** — name, price, category, active/soft-deleted flag
- **orders** — order ID, cashier, timestamp, total
- **order_details** — line items per order (menu item + quantity)

---

## How to Run

1. Start **MySQL** (and Apache, if you want phpMyAdmin) in XAMPP Control Panel.
2. Import the database schema via phpMyAdmin (`localhost/phpmyadmin`).
3. Open the project in **VS Code** with the Java Extension Pack installed.
4. Open `src/Main.java` and click the **Run** link above `public static void main`.
5. Log in with a seeded admin/cashier account (see database setup notes).

---

## Key Backend Concepts

- **DAO Pattern** — all SQL lives in `UserDAO` / `MenuDAO` / `OrderDAO`; UI classes never touch JDBC directly.
- **Transactions** — `OrderDAO.placeOrder()` inserts an order and its line items as a single transaction; if any part fails, it rolls back so no partial orders are ever saved.
- **Multithreading** — `ClockThread` updates the dashboard clock every second on a background thread, using `SwingUtilities.invokeLater()` to stay safe on the Event Dispatch Thread.
- **Security** — passwords are hashed with SHA-256 before being stored or compared; nothing is ever stored in plain text.

---

## License

Course project — Advanced Programming.
