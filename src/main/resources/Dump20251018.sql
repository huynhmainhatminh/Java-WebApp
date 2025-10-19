-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: db_pin_vin
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `batteries`
--

DROP TABLE IF EXISTS `batteries`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `batteries` (
  `battery_id` int NOT NULL AUTO_INCREMENT,
  `station_id` int NOT NULL,
  `name_battery` varchar(50) COLLATE utf8mb3_bin NOT NULL,
  `status` tinytext COLLATE utf8mb3_bin NOT NULL,
  `soh` decimal(5,2) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`battery_id`),
  UNIQUE KEY `name_battery_UNIQUE` (`name_battery`),
  KEY `station_id` (`station_id`),
  CONSTRAINT `batteries_ibfk_1` FOREIGN KEY (`station_id`) REFERENCES `stations` (`station_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `batteries`
--

LOCK TABLES `batteries` WRITE;
/*!40000 ALTER TABLE `batteries` DISABLE KEYS */;
INSERT INTO `batteries` VALUES (1,1,'PIN-2892872','full',NULL,'2025-10-09 15:51:05'),(2,1,'PIN-2892399','full',NULL,'2025-10-09 15:52:01'),(6,1,'PIN-2892729','charging',NULL,'2025-10-09 15:52:40');
/*!40000 ALTER TABLE `batteries` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stations`
--

DROP TABLE IF EXISTS `stations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stations` (
  `station_id` int NOT NULL AUTO_INCREMENT,
  `station_name` varchar(100) COLLATE utf8mb3_bin NOT NULL,
  `address` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  `contact_number` varchar(20) COLLATE utf8mb3_bin DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`station_id`),
  UNIQUE KEY `station_name_UNIQUE` (`station_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stations`
--

LOCK TABLES `stations` WRITE;
/*!40000 ALTER TABLE `stations` DISABLE KEYS */;
INSERT INTO `stations` VALUES (1,'59-M283HA','34 đường võ thị sáu, quận 1',NULL,'2025-10-09 15:43:04');
/*!40000 ALTER TABLE `stations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `swap_transactions`
--

DROP TABLE IF EXISTS `swap_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `swap_transactions` (
  `transaction_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `vehicle_id` int NOT NULL,
  `station_id` int NOT NULL,
  `old_battery_id` int NOT NULL,
  `new_battery_id` int NOT NULL,
  `transaction_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `payment_status` enum('paid','unpaid') COLLATE utf8mb3_bin DEFAULT 'unpaid',
  PRIMARY KEY (`transaction_id`),
  KEY `user_id` (`user_id`),
  KEY `vehicle_id` (`vehicle_id`),
  KEY `station_id` (`station_id`),
  KEY `old_battery_id` (`old_battery_id`),
  KEY `new_battery_id` (`new_battery_id`),
  CONSTRAINT `swap_transactions_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `swap_transactions_ibfk_2` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicles` (`vehicle_id`),
  CONSTRAINT `swap_transactions_ibfk_3` FOREIGN KEY (`station_id`) REFERENCES `stations` (`station_id`),
  CONSTRAINT `swap_transactions_ibfk_4` FOREIGN KEY (`old_battery_id`) REFERENCES `batteries` (`battery_id`),
  CONSTRAINT `swap_transactions_ibfk_5` FOREIGN KEY (`new_battery_id`) REFERENCES `batteries` (`battery_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `swap_transactions`
--

LOCK TABLES `swap_transactions` WRITE;
/*!40000 ALTER TABLE `swap_transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `swap_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8mb3_bin NOT NULL,
  `password` varchar(100) COLLATE utf8mb3_bin NOT NULL,
  `full_name` varchar(100) COLLATE utf8mb3_bin NOT NULL,
  `email` varchar(100) COLLATE utf8mb3_bin DEFAULT NULL,
  `phone` varchar(20) COLLATE utf8mb3_bin DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `role_name` varchar(255) COLLATE utf8mb3_bin NOT NULL DEFAULT 'driver',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'nhatminh123','minhdz123','Nhật Minh',NULL,NULL,'2025-10-09 15:18:23','admin'),(2,'nhatnam3','namdz123','Nhật Nam',NULL,NULL,'2025-10-09 15:19:03','driver'),(3,'nguyenvy2903','vynguyen20001','Vy Nguyễn',NULL,NULL,'2025-10-10 03:31:59','driver'),(4,'nguyenmy9921','myhgb200382','Nguyễn My',NULL,NULL,NULL,'driver'),(5,'tunguyen','123456789@@','ThanhTu',NULL,NULL,NULL,'driver'),(6,'vunguyen11122','vunguyen00281@@','Thanh Vu',NULL,NULL,NULL,'driver'),(7,'xuanmy9928','taodepzai@@','Xuan My',NULL,NULL,NULL,'driver'),(10,'NhatMinhPro','1234567','Nhat Minh',NULL,NULL,NULL,'driver'),(11,'NhatMinhPro123456','123456789','Nhat Minh',NULL,NULL,NULL,'driver'),(12,'NhatMinhPro8892','12345678910','Hoàng Phương',NULL,NULL,NULL,'driver'),(13,'NhatMinhPro8888','123456789','Hoàng Phương Minh',NULL,NULL,NULL,'driver'),(14,'NhatMinhPro66666666','12345678910','Nhat Minh',NULL,NULL,NULL,'driver');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehicles`
--

DROP TABLE IF EXISTS `vehicles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vehicles` (
  `vehicle_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `vin` varchar(50) COLLATE utf8mb3_bin NOT NULL,
  `battery_type` varchar(50) COLLATE utf8mb3_bin DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`vehicle_id`),
  UNIQUE KEY `vin_UNIQUE` (`vin`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `vehicles_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehicles`
--

LOCK TABLES `vehicles` WRITE;
/*!40000 ALTER TABLE `vehicles` DISABLE KEYS */;
INSERT INTO `vehicles` VALUES (1,1,'Feliz',NULL,'2025-10-09 15:29:34'),(4,2,'Test',NULL,'2025-10-09 15:32:29');
/*!40000 ALTER TABLE `vehicles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `wallets`
--

DROP TABLE IF EXISTS `wallets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `wallets` (
  `wallet_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int NOT NULL,
  `balance` decimal(15,2) NOT NULL DEFAULT '0.00',
  `currency` varchar(10) COLLATE utf8mb3_bin NOT NULL DEFAULT 'VND',
  `last_updated` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`wallet_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `wallets_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `wallets`
--

LOCK TABLES `wallets` WRITE;
/*!40000 ALTER TABLE `wallets` DISABLE KEYS */;
INSERT INTO `wallets` VALUES (1,3,500000.00,'VND','2025-10-12 19:42:00');
/*!40000 ALTER TABLE `wallets` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-18 22:08:21
