-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 30, 2020 at 03:58 AM
-- Server version: 10.4.13-MariaDB
-- PHP Version: 7.4.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `busticketdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `booking`
--

CREATE TABLE `booking` (
  `route_id` int(11) NOT NULL,
  `bookingID` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `ticket_number` varchar(11) NOT NULL,
  `cost` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `booking`
--

INSERT INTO `booking` (`route_id`, `bookingID`, `userid`, `ticket_number`, `cost`) VALUES
(10, 16, 21, '3', '66');

-- --------------------------------------------------------

--
-- Table structure for table `bus`
--

CREATE TABLE `bus` (
  `bid` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `description` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `bus`
--

INSERT INTO `bus` (`bid`, `name`, `price`, `description`, `created_at`, `updated_at`) VALUES
(3, 'Volvo', '123.00', 'Single deck', '2020-07-13 10:32:25', '2020-07-13 10:32:25'),
(4, 'Volvo EDT', '3455.00', 'hello', '2020-07-13 10:35:37', '2020-07-13 10:35:37');

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

CREATE TABLE `notification` (
  `nid` int(11) NOT NULL,
  `Source` varchar(255) NOT NULL,
  `Destination` varchar(255) NOT NULL,
  `created_at` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `notification`
--

INSERT INTO `notification` (`nid`, `Source`, `Destination`, `created_at`) VALUES
(9, 'Eindhoven Centraal', 'Amsterdam Centraal', '2020-07-30');

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `bookingID` int(11) NOT NULL,
  `paymentID` int(11) NOT NULL,
  `cardnumber` varchar(255) NOT NULL,
  `cvc` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `cost` varchar(255) NOT NULL,
  `created_at` timestamp(6) NOT NULL DEFAULT current_timestamp(6)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `payment`
--

INSERT INTO `payment` (`bookingID`, `paymentID`, `cardnumber`, `cvc`, `name`, `surname`, `cost`, `created_at`) VALUES
(16, 16, '1234566', '123', 'lamisha', 'rawshan', '66', '2020-07-30 00:51:57.360645');

-- --------------------------------------------------------

--
-- Table structure for table `places`
--

CREATE TABLE `places` (
  `placename` varchar(255) NOT NULL,
  `placeID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `places`
--

INSERT INTO `places` (`placename`, `placeID`) VALUES
('Amsterdam Centraal', 1),
('Eindhoven Centraal', 2),
('Rotterdam Centraal', 3),
('Utrecht Centraal', 4),
('Maastricht', 5),
('Den Haag Centraal', 6);

-- --------------------------------------------------------

--
-- Table structure for table `route`
--

CREATE TABLE `route` (
  `rid` int(11) NOT NULL,
  `source` int(255) NOT NULL,
  `destination` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `route`
--

INSERT INTO `route` (`rid`, `source`, `destination`) VALUES
(1, 1, 2),
(2, 2, 1),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 1, 6);

-- --------------------------------------------------------

--
-- Table structure for table `route_time`
--

CREATE TABLE `route_time` (
  `route_time_id` int(11) NOT NULL,
  `rid` int(11) NOT NULL,
  `bid` int(11) NOT NULL,
  `arrival_time` varchar(255) NOT NULL,
  `departure_time` varchar(255) NOT NULL,
  `rent` double NOT NULL,
  `route_date` varchar(255) NOT NULL,
  `available_seat` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `route_time`
--

INSERT INTO `route_time` (`route_time_id`, `rid`, `bid`, `arrival_time`, `departure_time`, `rent`, `route_date`, `available_seat`) VALUES
(10, 1, 3, '12:00', '11:00', 22, '1/8/2020', 19),
(11, 3, 4, '13:00', '12:00', 25, '1/8/2020', 27),
(12, 4, 4, '13', '14', 25, '2/8/2020', 30),
(13, 5, 4, '13', '12', 30, '31/7/2020', 30),
(14, 2, 4, '12', '11', 25, '31/7/2020', 30);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `usertype` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `email`, `phone`, `password`, `created_at`, `updated_at`, `usertype`) VALUES
(18, 'lamisha rawshan', 'lamisharawshan@gmail.com', '123456490', 'e10adc3949ba59abbe56e057f20f883e', '2020-07-29 09:51:00', '2020-07-29 09:51:00', 'user'),
(19, 'admin', 'admin@admin.com', '12345662', 'e10adc3949ba59abbe56e057f20f883e', '2020-07-29 10:02:38', '2020-07-29 10:02:38', 'admin'),
(21, 'lamisha', 'lamisha@gmail.com', '12345890', 'e10adc3949ba59abbe56e057f20f883e', '2020-07-30 00:50:15', '2020-07-30 00:50:15', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `booking`
--
ALTER TABLE `booking`
  ADD PRIMARY KEY (`bookingID`),
  ADD KEY `userid` (`userid`),
  ADD KEY `route_id` (`route_id`);

--
-- Indexes for table `bus`
--
ALTER TABLE `bus`
  ADD PRIMARY KEY (`bid`);

--
-- Indexes for table `notification`
--
ALTER TABLE `notification`
  ADD PRIMARY KEY (`nid`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`paymentID`);

--
-- Indexes for table `places`
--
ALTER TABLE `places`
  ADD PRIMARY KEY (`placeID`);

--
-- Indexes for table `route`
--
ALTER TABLE `route`
  ADD PRIMARY KEY (`rid`) USING BTREE,
  ADD KEY `source` (`source`),
  ADD KEY `destination` (`destination`);

--
-- Indexes for table `route_time`
--
ALTER TABLE `route_time`
  ADD PRIMARY KEY (`route_time_id`),
  ADD KEY `rid` (`rid`),
  ADD KEY `bid` (`bid`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `booking`
--
ALTER TABLE `booking`
  MODIFY `bookingID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `bus`
--
ALTER TABLE `bus`
  MODIFY `bid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `notification`
--
ALTER TABLE `notification`
  MODIFY `nid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `paymentID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `places`
--
ALTER TABLE `places`
  MODIFY `placeID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `route`
--
ALTER TABLE `route`
  MODIFY `rid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `route_time`
--
ALTER TABLE `route_time`
  MODIFY `route_time_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `booking`
--
ALTER TABLE `booking`
  ADD CONSTRAINT `booking_ibfk_1` FOREIGN KEY (`userid`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `booking_ibfk_2` FOREIGN KEY (`route_id`) REFERENCES `route_time` (`route_time_id`);

--
-- Constraints for table `payment`
--
ALTER TABLE `payment`
  ADD CONSTRAINT `payment_ibfk_1` FOREIGN KEY (`paymentID`) REFERENCES `booking` (`bookingID`);

--
-- Constraints for table `route`
--
ALTER TABLE `route`
  ADD CONSTRAINT `route_ibfk_1` FOREIGN KEY (`source`) REFERENCES `places` (`placeID`),
  ADD CONSTRAINT `route_ibfk_2` FOREIGN KEY (`source`) REFERENCES `places` (`placeID`),
  ADD CONSTRAINT `route_ibfk_3` FOREIGN KEY (`destination`) REFERENCES `places` (`placeID`);

--
-- Constraints for table `route_time`
--
ALTER TABLE `route_time`
  ADD CONSTRAINT `route_time_ibfk_1` FOREIGN KEY (`rid`) REFERENCES `route` (`rid`),
  ADD CONSTRAINT `route_time_ibfk_2` FOREIGN KEY (`bid`) REFERENCES `bus` (`bid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
