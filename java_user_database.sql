-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th12 11, 2024 lúc 06:40 PM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `java_user_database`
--
CREATE DATABASE IF NOT EXISTS `java_user_database` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `java_user_database`;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `customer`
--

CREATE TABLE `customer` (
  `id` int(11) NOT NULL,
  `name` varchar(127) NOT NULL,
  `phonenumber` varchar(127) NOT NULL,
  `address` varchar(127) NOT NULL,
  `date` varchar(127) NOT NULL,
  `rank` varchar(127) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `customer`
--

INSERT INTO `customer` (`id`, `name`, `phonenumber`, `address`, `date`, `rank`) VALUES
(5, 'Hoàng', '09123654784', 'Nghệ An', '30/11/2004', 'Bạc'),
(6, 'Tân', '0976123987', 'Nam Định', '29/8/2005', 'Bạc'),
(7, 'Giang', '0967543786', 'Nghệ An', '25/7/2005', 'Đồng'),
(8, 'Tú', '0986546897', 'Hà Nội', '25/3/2006', 'Vàng');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `name` varchar(125) NOT NULL,
  `salary` int(20) NOT NULL,
  `account` varchar(125) NOT NULL,
  `password` varchar(127) NOT NULL,
  `phone_number` varchar(127) NOT NULL,
  `rank` varchar(127) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `employee`
--

INSERT INTO `employee` (`id`, `name`, `salary`, `account`, `password`, `phone_number`, `rank`) VALUES
(12, 'Hoàng', 6000000, 'hoangc3kt', '312312', '0954567876', 'Bạc'),
(13, 'Hưng', 3000000, 'hungd3ko1', '12312', '0987233786', 'Đồng'),
(15, 'Tiến', 9000000, 'tien3356kyu', 'tien1', '0965446876', 'Vàng'),
(16, 'Việt', 95000000, 'viettk05uwu', 'viet2005', '0912312312', 'Vàng'),
(17, 'thu', 60000000, 'thutknabn', '123', '09765437675', 'Vàng');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `idEmployee` int(11) NOT NULL,
  `idTable` int(11) DEFAULT NULL,
  `type` varchar(50) NOT NULL DEFAULT 'local',
  `status` varchar(50) NOT NULL DEFAULT 'unpaid',
  `orderDate` timestamp NOT NULL DEFAULT current_timestamp(),
  `payDate` timestamp NULL DEFAULT NULL,
  `paidAmount` bigint(20) NOT NULL DEFAULT 0,
  `totalAmount` bigint(20) NOT NULL DEFAULT 0,
  `discount` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `orders`
--

INSERT INTO `orders` (`id`, `idEmployee`, `idTable`, `type`, `status`, `orderDate`, `payDate`, `paidAmount`, `totalAmount`, `discount`) VALUES
(1, 20, 22, 'local', 'unpaid', '2024-12-11 15:06:44', '2024-12-12 15:05:18', 3000000, 2, 300000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `orderss`
--

CREATE TABLE `orderss` (
  `id` int(11) NOT NULL,
  `founder` varchar(125) NOT NULL,
  `type` varchar(125) NOT NULL,
  `conditions` varchar(125) NOT NULL,
  `applicationdate` varchar(125) NOT NULL,
  `paymentdate` varchar(125) NOT NULL,
  `paid` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `orderss`
--

INSERT INTO `orderss` (`id`, `founder`, `type`, `conditions`, `applicationdate`, `paymentdate`, `paid`) VALUES
(26, '123123', 'Sản phẩm 1', 'Mới', '2131232', '13123', '123123'),
(28, '12312312', 'Sản phẩm 1', 'Mới', '3123', '123', '123123'),
(30, '213123123123', 'Sản phẩm Bạch Kim', 'Mới', '12312312', '3123', '123123123'),
(31, 'hải', 'Sản phẩm Kim Cương', 'Mới', '11/12/2024  11:14', '11/12/2024  11:16', '500000'),
(32, '123123', 'Sản phẩm Đồng', 'Mới', '12312', '3123', '2000000');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `name` varchar(127) NOT NULL,
  `description` varchar(127) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `imagelink` varchar(127) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `date` varchar(127) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`id`, `name`, `description`, `imagelink`, `quantity`, `price`, `date`) VALUES
(28, 'Giày', 'có 7 màu', 'E:\\unnamed.jpg', 6, 150000, '9/12/2024'),
(31, 'áo hoddie', 'không có séc', '\"D:\\11a7c3c4-64af-4f97-9c98-0a7be68c767c.jpg\"', 10, 200000, '11/12/2024');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `session`
--

CREATE TABLE `session` (
  `id` int(11) NOT NULL,
  `idEmployee` int(11) NOT NULL,
  `startTime` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `endTime` timestamp NULL DEFAULT NULL,
  `message` varchar(225) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `session`
--

INSERT INTO `session` (`id`, `idEmployee`, `startTime`, `endTime`, `message`) VALUES
(1, 20, '2024-12-03 12:23:53', '2024-12-05 12:23:53', 'logout');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `full_name` varchar(127) NOT NULL,
  `email` varchar(127) NOT NULL,
  `password` varchar(127) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `full_name`, `email`, `password`) VALUES
(1, 'hai', 'hai@gmail.com', '123'),
(2, 'manh', 'manh@gmail.com', '123'),
(3, 'dan', 'dan@gmail.com', '123'),
(4, 'hiep', 'hiep@gmail.com', '123'),
(5, 'tan', 'tan@gmai.com', '123');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `customer`
--
ALTER TABLE `customer`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `orderss`
--
ALTER TABLE `orderss`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `session`
--
ALTER TABLE `session`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `customer`
--
ALTER TABLE `customer`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT cho bảng `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT cho bảng `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `orderss`
--
ALTER TABLE `orderss`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT cho bảng `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=33;

--
-- AUTO_INCREMENT cho bảng `session`
--
ALTER TABLE `session`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
