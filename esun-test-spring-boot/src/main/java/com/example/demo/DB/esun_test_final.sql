-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- 主機： 127.0.0.1
-- 產生時間： 2023-11-26 07:25:03
-- 伺服器版本： 10.4.32-MariaDB
-- PHP 版本： 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 資料庫： `esun_test`
--
CREATE DATABASE IF NOT EXISTS `esun_test` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `esun_test`;

DELIMITER $$
--
-- 程序
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `get_all_employees` ()   BEGIN 
	SELECT * FROM employee;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `get_all_seatings_with_employee` ()   BEGIN
SELECT * FROM seatingchart LEFT JOIN employee ON seatingchart.FLOOR_SEAT_SEQ = employee.FLOOR_SEAT_SEQ ORDER BY seatingchart.FLOOR_NO, seatingchart.SEAT_NO ASC;
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `set_seat_of_employee` (IN `employeeId` CHAR(5), IN `floorSeatSeq` VARCHAR(255), OUT `rowsAffected` INT)   BEGIN
    UPDATE employee
    SET employee.FLOOR_SEAT_SEQ = floorSeatSeq
    WHERE employee.EMP_ID = employeeId;
    SELECT ROW_COUNT() INTO rowsAffected;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- 資料表結構 `employee`
--

CREATE TABLE `employee` (
  `EMP_ID` char(5) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `EMAIL` varchar(255) NOT NULL,
  `FLOOR_SEAT_SEQ` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `employee`
--

INSERT INTO `employee` (`EMP_ID`, `NAME`, `EMAIL`, `FLOOR_SEAT_SEQ`) VALUES
('00001', '郭倧僓', 'a126270095@gmail.com', 'seat_8'),
('00002', '王大明', 'daming@gmail.com', NULL),
('00003', '陳小東', 'a128270095@gmail.com', NULL),
('00004', '李大壯', 'a1561651@gmail.com', NULL);

-- --------------------------------------------------------

--
-- 資料表結構 `seatingchart`
--

CREATE TABLE `seatingchart` (
  `FLOOR_SEAT_SEQ` varchar(255) NOT NULL,
  `FLOOR_NO` int(11) NOT NULL,
  `SEAT_NO` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- 傾印資料表的資料 `seatingchart`
--

INSERT INTO `seatingchart` (`FLOOR_SEAT_SEQ`, `FLOOR_NO`, `SEAT_NO`) VALUES
('seat_0', 1, 1),
('seat_1', 1, 2),
('seat_10', 3, 3),
('seat_11', 3, 4),
('seat_12', 4, 1),
('seat_13', 4, 2),
('seat_14', 4, 3),
('seat_15', 4, 4),
('seat_2', 1, 3),
('seat_3', 1, 4),
('seat_4', 2, 1),
('seat_5', 2, 2),
('seat_6', 2, 3),
('seat_7', 2, 4),
('seat_8', 3, 1),
('seat_9', 3, 2);

--
-- 已傾印資料表的索引
--

--
-- 資料表索引 `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`EMP_ID`),
  ADD UNIQUE KEY `FLOOR_SEAT_SEQ` (`FLOOR_SEAT_SEQ`),
  ADD KEY `BindSeat` (`FLOOR_SEAT_SEQ`);

--
-- 資料表索引 `seatingchart`
--
ALTER TABLE `seatingchart`
  ADD PRIMARY KEY (`FLOOR_SEAT_SEQ`);

--
-- 已傾印資料表的限制式
--

--
-- 資料表的限制式 `employee`
--
ALTER TABLE `employee`
  ADD CONSTRAINT `BindSeat` FOREIGN KEY (`FLOOR_SEAT_SEQ`) REFERENCES `seatingchart` (`FLOOR_SEAT_SEQ`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
