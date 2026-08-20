-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Aug 20, 2026 at 10:22 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cafe_pos`
--

-- --------------------------------------------------------

--
-- Table structure for table `menu_items`
--

CREATE TABLE `menu_items` (
  `item_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `category` varchar(50) DEFAULT NULL,
  `available` tinyint(1) DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `menu_items`
--

INSERT INTO `menu_items` (`item_id`, `name`, `price`, `category`, `available`) VALUES
(1, 'Coffee', 180.00, 'Drink', 1),
(2, 'Latte', 220.00, 'Drink', 1),
(3, 'Cappuccino', 250.00, 'Drink', 1),
(4, 'Espresso', 160.00, 'Drink', 1),
(5, 'Tea', 100.00, 'Drink', 1),
(6, 'Mocha', 280.00, 'Drink', 1),
(7, 'Burger', 300.00, 'Food', 1),
(8, 'Pizza', 400.00, 'Food', 1),
(9, 'Pizzafff', 400.00, 'Food', 0),
(10, 'cake', 200.00, 'Food', 1);

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `order_id` int(11) NOT NULL,
  `cashier_id` int(11) NOT NULL,
  `order_time` timestamp NOT NULL DEFAULT current_timestamp(),
  `total` decimal(10,2) NOT NULL,
  `status` enum('PENDING','COMPLETED','CANCELLED') DEFAULT 'COMPLETED'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `orders`
--

INSERT INTO `orders` (`order_id`, `cashier_id`, `order_time`, `total`, `status`) VALUES
(1, 2, '2026-07-26 15:25:50', 350.00, 'COMPLETED'),
(2, 2, '2026-07-26 18:57:27', 1100.00, 'COMPLETED'),
(3, 2, '2026-07-26 19:12:51', 700.00, 'COMPLETED'),
(4, 3, '2026-07-26 19:24:41', 400.00, 'COMPLETED'),
(5, 2, '2026-07-31 18:33:21', 550.00, 'COMPLETED'),
(6, 2, '2026-07-31 18:33:59', 840.00, 'COMPLETED'),
(7, 2, '2026-07-31 18:59:44', 620.00, 'COMPLETED'),
(8, 2, '2026-07-31 19:01:21', 880.00, 'COMPLETED'),
(9, 2, '2026-08-08 20:21:58', 1050.00, 'COMPLETED'),
(10, 2, '2026-08-14 21:51:19', 220.00, 'COMPLETED'),
(11, 2, '2026-08-18 21:00:16', 530.00, 'COMPLETED'),
(12, 2, '2026-08-18 21:01:52', 1640.00, 'COMPLETED'),
(13, 2, '2026-08-18 21:02:02', 530.00, 'COMPLETED'),
(14, 2, '2026-08-18 21:02:07', 280.00, 'COMPLETED'),
(15, 2, '2026-08-18 21:02:17', 650.00, 'COMPLETED');

-- --------------------------------------------------------

--
-- Table structure for table `order_details`
--

CREATE TABLE `order_details` (
  `order_detail_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `item_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `subtotal` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `order_details`
--

INSERT INTO `order_details` (`order_detail_id`, `order_id`, `item_id`, `quantity`, `subtotal`) VALUES
(1, 1, 5, 1, 100.00),
(2, 1, 3, 1, 250.00),
(3, 2, 7, 2, 600.00),
(4, 2, 3, 2, 500.00),
(5, 3, 10, 1, 200.00),
(6, 3, 3, 2, 500.00),
(7, 4, 5, 1, 100.00),
(8, 4, 7, 1, 300.00),
(9, 5, 3, 1, 250.00),
(10, 5, 7, 1, 300.00),
(11, 6, 8, 1, 400.00),
(12, 6, 2, 2, 440.00),
(13, 7, 2, 1, 220.00),
(14, 7, 10, 2, 400.00),
(15, 8, 2, 2, 440.00),
(16, 8, 2, 2, 440.00),
(17, 9, 3, 1, 250.00),
(18, 9, 8, 2, 800.00),
(19, 10, 2, 1, 220.00),
(20, 11, 6, 1, 280.00),
(21, 11, 3, 1, 250.00),
(22, 12, 4, 1, 160.00),
(23, 12, 10, 3, 600.00),
(24, 12, 2, 4, 880.00),
(25, 13, 3, 1, 250.00),
(26, 13, 6, 1, 280.00),
(27, 14, 6, 1, 280.00),
(28, 15, 8, 1, 400.00),
(29, 15, 3, 1, 250.00);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(100) NOT NULL,
  `role` enum('ADMIN','CASHIER') NOT NULL,
  `full_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `role`, `full_name`) VALUES
(1, 'admin', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'ADMIN', 'Admin User'),
(2, 'cashier1', '03ac674216f3e15c761ee1a5e255f067953623c8b388b4459e13f978d7c846f4', 'CASHIER', 'Cashier One'),
(3, 'cashier2', 'f8638b979b2f4f793ddb6dbd197e0ee25a7a6ea32b0ae22f5e3c5d119d839e75', 'CASHIER', 'Akhi'),
(4, 'staff', '010f4928749bd109657b1b4ef213359ac420678c72932b0d5bc110076afc52f7', 'CASHIER', 'hina');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `menu_items`
--
ALTER TABLE `menu_items`
  ADD PRIMARY KEY (`item_id`);

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `cashier_id` (`cashier_id`);

--
-- Indexes for table `order_details`
--
ALTER TABLE `order_details`
  ADD PRIMARY KEY (`order_detail_id`),
  ADD KEY `order_id` (`order_id`),
  ADD KEY `item_id` (`item_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `menu_items`
--
ALTER TABLE `menu_items`
  MODIFY `item_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `order_details`
--
ALTER TABLE `order_details`
  MODIFY `order_detail_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `orders`
--
ALTER TABLE `orders`
  ADD CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`cashier_id`) REFERENCES `users` (`user_id`);

--
-- Constraints for table `order_details`
--
ALTER TABLE `order_details`
  ADD CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`),
  ADD CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `menu_items` (`item_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
