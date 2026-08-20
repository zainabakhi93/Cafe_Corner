# Cafe Corner – Café Management System

A Java Swing-based Café Management System developed as an Advanced Programming project.

## Technologies Used

* Java
* Java Swing
* MySQL
* JDBC
* MySQL Connector/J

## Main Features

* Admin Login
* Cashier Login
* Menu Management
* Staff Management
* New Order
* Cart Management
* Receipt Generation
* Order History
* Sales Report

## Project Structure

The project backend contains:

* `DBConnection.java` – Database connectivity
* `User.java` – User model
* `UserDAO.java` – User database operations
* `MenuItem.java` – Menu item model
* `MenuDAO.java` – Menu database operations
* `CartItem.java` – Cart item and subtotal management
* `OrderDAO.java` – Order processing and database operations
* `PasswordUtil.java` – Password hashing
* `ClockThread.java` – Real-time clock using multithreading

## Requirements

Before running the project, make sure the following are installed:

* Java JDK
* XAMPP
* MySQL
* IntelliJ IDEA or another Java IDE

## Database Setup

1. Open XAMPP.
2. Start **MySQL**.
3. Open **phpMyAdmin**.
4. Create/import the `cafe_pos` database.
5. Import the provided `cafe_pos.sql` file into the database.

## How to Run

1. Start MySQL from XAMPP.
2. Make sure the `cafe_pos` database has been imported.
3. Open the project in a Java IDE.
4. Make sure the MySQL Connector/J library is available to the project.
5. Run `Main.java`.
6. The Cafe Corner login screen will appear.
7. Log in using one of the demo accounts provided below.

## Database Configuration

The application currently uses:

* Database: `cafe_pos`
* Host: `localhost`
* Port: `3306`
* Username: `root`
* Password: empty

These settings are configured in `DBConnection.java`.

## Demo Login

### Admin

* Username: `admin`
* Password: `1234`

### Cashier

* Username: `cashier1`
* Password: `1234`

## Java Concepts Implemented

* Object-Oriented Programming
* Exception Handling
* Multithreading
* JDBC Database Connectivity
* DAO (Data Access Object) Pattern

## Presentation

This repository contains the source code used for the Cafe Corner project presentation, with particular focus on the Java backend implementation.
