-- MariaDB dump 10.19  Distrib 10.4.27-MariaDB, for Win64 (AMD64)
--
-- Host: booking-movie.cjvzxv4pqzmf.eu-west-1.rds.amazonaws.com    Database: booking_movie
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bill_status`
--

use booking_movie;

DROP TABLE IF EXISTS `bill_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bill_status` (
  `bill_status_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`bill_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill_status`
--

LOCK TABLES `bill_status` WRITE;
/*!40000 ALTER TABLE `bill_status` DISABLE KEYS */;
INSERT INTO `bill_status` VALUES (1,'Unpaid'),(2,'Paid'),(3,'Cancel');
/*!40000 ALTER TABLE `bill_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bills`
--

DROP TABLE IF EXISTS `bills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bills` (
  `bill_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `payment_at` datetime(6) DEFAULT NULL,
  `total` double DEFAULT NULL,
  `user_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `transaction_id` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `change_point` int DEFAULT NULL,
  `cancel_reason` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status_id` int NOT NULL,
  `create_time` datetime(6) DEFAULT NULL,
  `cancel_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`bill_id`),
  KEY `FKk8vs7ac9xknv5xp18pdiehpp1` (`user_id`),
  KEY `FK7s2dh8hdb6rthogn42qb1a3aw` (`status_id`),
  CONSTRAINT `FK7s2dh8hdb6rthogn42qb1a3aw` FOREIGN KEY (`status_id`) REFERENCES `bill_status` (`bill_status_id`),
  CONSTRAINT `FKk8vs7ac9xknv5xp18pdiehpp1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bills`
--

LOCK TABLES `bills` WRITE;
/*!40000 ALTER TABLE `bills` DISABLE KEYS */;
INSERT INTO `bills` VALUES ('031cf16b-fc94-4e75-90bb-e5e04926ec3e',NULL,50000,'49c64e33-d273-38ef-a68c-23e1aa16e499','b386b2cc-c407-3a63-b09f-c8f6230d9ac8',0,NULL,1,'2023-11-10 20:49:16.000000',NULL),('04e6d2cb-1a80-4844-8d0c-484057e05d63',NULL,150000,'49c64e33-d273-38ef-a68c-23e1aa16e499','7c9d950b-4de5-36fc-a79e-fe1f233e60f8',0,'\"Không coi nữa.\"',3,'2023-10-27 01:21:56.000000','2023-10-27 01:34:31.000000'),('0d13aba8-cca1-48ae-bde5-dcb250644705',NULL,150000,'49c64e33-d273-38ef-a68c-23e1aa16e499','cf08b8ab-b73b-3536-881b-c78da79fafba',0,NULL,1,'2023-11-22 13:50:30.849000',NULL),('442b8ee7-7f6d-48ae-8666-17a41b9ac882','2023-11-29 21:57:20.833606',100000,'49c64e33-d273-38ef-a68c-23e1aa16e499','f4a724dd-5d85-33a1-a2cb-fc809b367c1c',0,NULL,2,'2023-11-29 21:54:15.697000',NULL),('516cdf92-447d-46d2-b2f7-13c67985233f',NULL,200000,'49c64e33-d273-38ef-a68c-23e1aa16e499','3a30cb3c-7441-3f02-8012-43703cf1880d',0,NULL,1,'2023-11-14 20:58:28.000000',NULL),('577cc36a-800e-42fb-956f-d191b5cd6d94','2023-10-28 00:00:00.000000',190000,'49c64e33-d273-38ef-a68c-23e1aa16e499','15263ded-d328-3a72-8a05-b3f1f410bdde',0,NULL,2,'2023-10-28 09:29:35.000000',NULL),('5e5c2ddb-26c5-474e-8685-5be13f401599',NULL,50000,'e33ffe0f-5175-3ab0-9971-67196c3fa9dc','9821cd56-38a5-38f8-b6f6-3a40db5e8744',0,NULL,1,'2023-11-09 16:18:27.000000',NULL),('6ad7e232-c242-49a8-b658-d15dcbf2f353','2023-11-29 22:29:36.493181',100000,'49c64e33-d273-38ef-a68c-23e1aa16e499','dfb743b5-a8db-3ab7-9aa2-4b2ecb4a9835',0,NULL,2,'2023-11-29 22:28:30.593000',NULL),('78598fef-8152-4cbd-9bf8-21620ce42a45','2023-11-02 19:47:14.000000',30000,'49c64e33-d273-38ef-a68c-23e1aa16e499','679c48de-1c32-3485-8a33-f828cbb32476',20,NULL,2,'2023-11-02 19:46:06.000000',NULL),('79e2ac2b-d0c1-4ae0-878f-5e558d782d3b','2023-10-28 00:00:00.000000',210000,'49c64e33-d273-38ef-a68c-23e1aa16e499','c76bb79b-e360-32df-aace-15f907667ff2',0,NULL,2,'2023-10-28 00:26:51.000000',NULL),('b76524a5-02bd-4c51-9832-86e933863f57','2023-11-30 07:53:31.050337',210000,'76ea668f-6c6b-33bd-8a99-25f7eac2becf','2b4ffd73-00fc-3996-af24-b4be0dc0a275',0,NULL,2,'2023-11-30 07:52:23.753000',NULL),('c86a74f8-d7f2-4afb-9675-bc306c64e365','2023-11-29 10:41:06.333967',250000,'49c64e33-d273-38ef-a68c-23e1aa16e499','ded00f7f-6fc6-3fbd-89e7-f8b4024cc3a1',0,NULL,2,'2023-11-29 10:38:22.892000',NULL),('c9b50b86-fae2-4e9e-9811-baa2e968d983',NULL,120000,'49c64e33-d273-38ef-a68c-23e1aa16e499','9bdaf3aa-53e8-3dc8-b9f8-e4f3ab2f9179',0,NULL,1,'2023-11-14 08:46:03.000000',NULL),('d464eb75-b912-498e-8099-05a9f48ce78b',NULL,250000,'76ea668f-6c6b-33bd-8a99-25f7eac2becf','96da35e2-017f-310c-b0d3-314727c76a0b',0,NULL,1,'2023-11-23 14:26:06.906000',NULL),('e772c5cc-982d-45a2-a3d8-0e0a7e735fdf',NULL,200000,'76ea668f-6c6b-33bd-8a99-25f7eac2becf','ad7c039a-a6b4-3597-a6dc-c19385ee03f4',0,NULL,1,'2023-11-23 14:24:56.350000',NULL);
/*!40000 ALTER TABLE `bills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cinemas`
--

DROP TABLE IF EXISTS `cinemas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cinemas` (
  `cinema_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `city` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `district` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `cinema_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_general_ci,
  `slug` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone_number` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` enum('CLOSED','MAINTAINED','OPENING') COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`cinema_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cinemas`
--

LOCK TABLES `cinemas` WRITE;
/*!40000 ALTER TABLE `cinemas` DISABLE KEYS */;
INSERT INTO `cinemas` VALUES ('Cinema001','466','HCM','122','Galaxy Trung Chanh','Hệ thống rạp The Cinema được thiết kế sang trọng và tiện nghi. Khách hàng bước vào không gian rạp sẽ cảm nhận ngay sự thoải mái và tiện lợi từ việc sắp xếp ghế ngồi theo lối kiến trúc hiện đại, tạo nên không gian xem phim tuyệt vời. Mỗi phòng chiếu được trang bị hệ thống âm thanh vòm và màn hình siêu nét, mang đến trải nghiệm hình ảnh và âm thanh sống động, hấp dẫn.','galaxy-trung-chanh','091222','OPENING'),('Cinema002','116 Nguyễn Du, Quận 1, Tp.HCM','Tp.HCM','Quận 1','Galaxy Nguyễn Du','Hệ thống rạp The Cinema được thiết kế sang trọng và tiện nghi. Khách hàng bước vào không gian rạp sẽ cảm nhận ngay sự thoải mái và tiện lợi từ việc sắp xếp ghế ngồi theo lối kiến trúc hiện đại, tạo nên không gian xem phim tuyệt vời. Mỗi phòng chiếu được trang bị hệ thống âm thanh vòm và màn hình siêu nét, mang đến trải nghiệm hình ảnh và âm thanh sống động, hấp dẫn.','galaxy-nguyen-du','09133333333','OPENING'),('Cinema003','246 Nguyễn Hồng Đào, Q.TB, Tp.HCM','Tp.HCM','Quận Tân Bình','Galaxy Tân Bình','Hệ thống rạp The Cinema được thiết kế sang trọng và tiện nghi. Khách hàng bước vào không gian rạp sẽ cảm nhận ngay sự thoải mái và tiện lợi từ việc sắp xếp ghế ngồi theo lối kiến trúc hiện đại, tạo nên không gian xem phim tuyệt vời. Mỗi phòng chiếu được trang bị hệ thống âm thanh vòm và màn hình siêu nét, mang đến trải nghiệm hình ảnh và âm thanh sống động, hấp dẫn.','galaxy-tan-binh','09122222222','OPENING'),('Cinema004','718bis Kinh Dương Vương, Q6, TpHCM.','Tp.HCM','Quận 6','Galaxy Kinh Dương Vương',NULL,'galaxy-kinh-duong-vuong','09122222222','CLOSED'),('Cinema005','Lầu 3, TTTM CoopMart Foodcosa số 304A, Quang Trung, P.11, Q. Gò Vấp, Tp.HCM','Tp.HCM','Quận Gò vấp','Galaxy Quang Trung',NULL,'galaxy-quang-trung','09122222222','CLOSED'),('Cinema006','Lầu 1, TTTM Sense City 26A Trần Quốc Tuấn, Phường 4, TP. Bến Tre','Tp.Bến Tre','Phường 4','Galaxy Bến Tre',NULL,'galaxy-ben-tre','09122222222','CLOSED'),('Cinema007','Tầng 6, TTTM Mipec Long Biên, Số 2, Phố Long Biên 2, Ngọc Lâm, Long Biên, Hà Nội','Tp.Hà Nội','Long Biên','Galaxy Mipec Long Biên',NULL,'galaxy-mipec-long-bien','09122222222','CLOSED'),('Cinema008','Tầng 3, TTTM Coop Mart, 478 Điện Biên Phủ, Quận Thanh Khê, Đà Nẵng','Tp.Đà Nẵng','Thanh Khê','Galaxy Đà Nẵng',NULL,'galaxy-da-nang','09122222222','CLOSED'),('Cinema009','Lầu 2, TTTM Sense City, số 9, Trần Hưng Đạo, P.5, Tp. Cà Mau','Tp.Cà Mau','Phường 5','Galaxy Cà Mau',NULL,'galaxy-ca-mau','09122222222','CLOSED'),('Cinema010','TTVH Quận 12, Số 09 Quốc Lộ 22, P. Trung Mỹ Tây, Quận 12','Tp.HCM','Quận 12','Galaxy Trung Chánh',NULL,'galaxy-trung-chanh','09122222222','CLOSED'),('Cinema011','Lầu 2, TTTM Coopmart, số 1362 Huỳnh Tấn Phát, khu phố 1, Phường Phú Mỹ, Quận 7, Tp.Hồ Chí Minh, Việt Nam.','Tp.HCM','Quận 7','Galaxy Huỳnh Tấn Phát',NULL,'galaxy-huynh-tan-phat','09122222222','CLOSED'),('Cinema012','Lầu 5, Trung tâm Giải Trí City HUB – số 1 Lê Hồng Phong, thành phố Vinh','Tp.Vinh','Null','Galaxy Vinh',NULL,'galaxy-vinh','09122222222','CLOSED'),('Cinema013','Lầu 7, TTTM Nguyễn Kim - Sài Gòn Mall, số 104 Lương Khánh Thiện.','Tp.Hải Phòng','Null','Galaxy Hải Phòng',NULL,'galaxy-hai-phong','09122222222','CLOSED'),('Cinema014','119B Nguyễn Văn Quá, Phường Đông Hưng Thuận, Quận 12','Tp.HCM','Quận 12','Galaxy Nguyễn Văn Quá',NULL,'galaxy-nguyen-van-qua','09122222222','CLOSED'),('Cinema015','Tầng trệt, TTTM Coop Mart Buôn Ma Thuột, số 71 Nguyễn Tất Thành, Phường Tân An, Tp. Buôn Ma Thuột, Tỉnh Đắk Lắk, Việt Nam','Tp.Buôn Ma Thuộc','Phường Tân An','Galaxy Buôn Ma Thuột',NULL,'galaxy-buon-ma-thuot','09122222222','CLOSED'),('Cinema016','Tầng 1, TTTM Nguyễn Kim, số 01 Trần Hưng Đạo, Phường Mỹ Bình, Thành phố Long Xuyên','Tp.Long Xuyên','Phường Mỹ Đình','Galaxy Long Xuyên',NULL,'galaxy-long-xuyen','09122222222','CLOSED'),('Cinema017','Tầng trệt, TTTM Co.opXtra Linh Trung, số 934 Quốc Lộ 1A, Phường Linh Trung, Quận Thủ Đức, Thành phố Hồ Chí Minh, Việt Nam','Tp.HCM','Quanan Thủ Đức','Galaxy Co.opXtra Linh Trung',NULL,'galaxy-coopxtra-linh-trung','09122222222','CLOSED'),('Cinema018','Tầng 3, Trung Tâm Thương Mại Nha Trang Center - 20 Trần Phú, Nha Trang, Khánh Hòa','Tp.Nha Trang','Null','Galaxy Nha Trang Center',NULL,'galaxy-nha-trang-center','09122222222','CLOSED'),('Cinema019','Tầng 3 - Co.opMart TTTM Thắng Lợi - Số 2 Trường Chinh, Tây Thạnh, Tân Phú, Thành phố Hồ Chí Minh','Tp.HCM','Quận Tân Phú','Galaxy Trường Chinh',NULL,'galaxy-truong-chinh','09122222222','CLOSED');
/*!40000 ALTER TABLE `cinemas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comments` (
  `comment_id` int NOT NULL AUTO_INCREMENT,
  `content` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `movie_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `user_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `status` enum('APPROVED','DELETED','PENDING') COLLATE utf8mb4_general_ci DEFAULT NULL,
  `rating` int DEFAULT NULL,
  PRIMARY KEY (`comment_id`),
  KEY `FKr1xv5xvew7k2aed5qu5lci3kt` (`movie_id`),
  KEY `FK8omq0tc18jd43bu5tjh6jvraq` (`user_id`),
  CONSTRAINT `FK8omq0tc18jd43bu5tjh6jvraq` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
  CONSTRAINT `FKr1xv5xvew7k2aed5qu5lci3kt` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`movie_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (14,'hay nha','2023-11-09 23:17:21.000000','mv-0005','49c64e33-d273-38ef-a68c-23e1aa16e499','APPROVED',5),(15,'tạm được','2023-11-09 23:18:23.000000','mv-0005','e33ffe0f-5175-3ab0-9971-67196c3fa9dc','APPROVED',5),(16,'test post comment','2023-11-27 15:32:24.705716','mv-0001','49c64e33-d273-38ef-a68c-23e1aa16e499','APPROVED',5),(17,'test post comment','2023-11-27 15:33:44.156252','mv-0004','49c64e33-d273-38ef-a68c-23e1aa16e499','APPROVED',5),(18,'test post comment','2023-11-27 15:36:09.164633','mv-0002','49c64e33-d273-38ef-a68c-23e1aa16e499','APPROVED',5),(19,'test comment neh','2023-11-29 12:07:13.594076','mv-0003','49c64e33-d273-38ef-a68c-23e1aa16e499','APPROVED',4),(21,'nnn','2023-11-30 08:10:02.569956','mv-0004','76ea668f-6c6b-33bd-8a99-25f7eac2becf','PENDING',0),(22,'1234','2023-11-30 12:31:40.648466','mv-0004','e33ffe0f-5175-3ab0-9971-67196c3fa9dc','APPROVED',0),(23,'aaaa','2023-12-01 00:07:19.398896','mv-0003','e33ffe0f-5175-3ab0-9971-67196c3fa9dc','PENDING',4),(24,'aa','2023-12-01 00:09:15.424877','mv-0007','e33ffe0f-5175-3ab0-9971-67196c3fa9dc','PENDING',3),(25,'a','2023-12-01 00:43:15.943956','mv-0006','e33ffe0f-5175-3ab0-9971-67196c3fa9dc','PENDING',2),(26,'123','2023-12-01 00:51:04.796606','mv-0003','d733ff2a-e4c6-338b-8a27-c8d80791b5ea','APPROVED',3),(27,'aaa','2023-12-01 01:02:44.274396','mv-0006','d733ff2a-e4c6-338b-8a27-c8d80791b5ea','PENDING',4),(28,'1111','2023-12-01 01:03:29.678093','mv-0004','d733ff2a-e4c6-338b-8a27-c8d80791b5ea','PENDING',2),(29,'test','2023-12-01 01:11:16.230293','mv-0007','49c64e33-d273-38ef-a68c-23e1aa16e499','PENDING',5),(30,'Nam chính diễn xuất đỉnh cao','2023-12-01 07:30:18.901475','mv-0005','d733ff2a-e4c6-338b-8a27-c8d80791b5ea','PENDING',1);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `formats`
--

DROP TABLE IF EXISTS `formats`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `formats` (
  `format_id` int NOT NULL AUTO_INCREMENT,
  `caption` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `version` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`format_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `formats`
--

LOCK TABLES `formats` WRITE;
/*!40000 ALTER TABLE `formats` DISABLE KEYS */;
INSERT INTO `formats` VALUES (1,'Vietsub','2D'),(2,'Lồng tiếng','2D'),(3,'Vietsub','3D'),(4,'Lồng tiếng','3D');
/*!40000 ALTER TABLE `formats` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_format`
--

DROP TABLE IF EXISTS `movie_format`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_format` (
  `movie_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `format_id` int NOT NULL,
  PRIMARY KEY (`movie_id`,`format_id`),
  KEY `FKfinrg5ol8dp6c6ypemnsmqupn` (`format_id`),
  CONSTRAINT `FKfinrg5ol8dp6c6ypemnsmqupn` FOREIGN KEY (`format_id`) REFERENCES `formats` (`format_id`),
  CONSTRAINT `FKoxc87pycbnla9pa59kub6li39` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_format`
--

LOCK TABLES `movie_format` WRITE;
/*!40000 ALTER TABLE `movie_format` DISABLE KEYS */;
INSERT INTO `movie_format` VALUES ('mv-0002',1),('mv-0003',1),('mv-0004',1),('mv-0005',1),('mv-0006',1),('mv-0008',1),('mv-0001',2),('mv-0003',2),('mv-0005',2),('mv-0007',2),('mv-0005',3),('mv-0005',4);
/*!40000 ALTER TABLE `movie_format` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_genre`
--

DROP TABLE IF EXISTS `movie_genre`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_genre` (
  `movie_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `genre_id` int NOT NULL,
  PRIMARY KEY (`movie_id`,`genre_id`),
  KEY `FK87qryjrya5r6wn0su5fl0ynu6` (`genre_id`),
  CONSTRAINT `FK87qryjrya5r6wn0su5fl0ynu6` FOREIGN KEY (`genre_id`) REFERENCES `movie_genres` (`movie_genre_id`),
  CONSTRAINT `FKg7f38h6umffo51no9ywq91438` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`movie_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_genre`
--

LOCK TABLES `movie_genre` WRITE;
/*!40000 ALTER TABLE `movie_genre` DISABLE KEYS */;
INSERT INTO `movie_genre` VALUES ('mv-0004',1),('mv-0008',1),('mv-0005',2),('mv-0002',4),('mv-0001',5),('mv-0003',5),('mv-0006',5),('mv-0001',6),('mv-0008',6);
/*!40000 ALTER TABLE `movie_genre` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movie_genres`
--

DROP TABLE IF EXISTS `movie_genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movie_genres` (
  `movie_genre_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`movie_genre_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movie_genres`
--

LOCK TABLES `movie_genres` WRITE;
/*!40000 ALTER TABLE `movie_genres` DISABLE KEYS */;
INSERT INTO `movie_genres` VALUES (1,'Kinh dị'),(2,'Hài'),(3,'Hoạt hình'),(4,'Hành động'),(5,'Tình cảm'),(6,'Gia đình');
/*!40000 ALTER TABLE `movie_genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies`
--

DROP TABLE IF EXISTS `movies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movies` (
  `movie_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `cast` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_general_ci,
  `director` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `language` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `sub_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
  `number_of_ratings` int DEFAULT NULL,
  `poster` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `producer` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `rated` int DEFAULT NULL,
  `release_date` date DEFAULT NULL,
  `running_time` int DEFAULT NULL,
  `slug` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `trailer` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status_id` int NOT NULL,
  `end_date` date DEFAULT NULL,
  `horizontal_poster` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `sum_of_ratings` int DEFAULT NULL,
  PRIMARY KEY (`movie_id`),
  KEY `FKp1vkgxjs3ifhxelqlmt4hkhod` (`status_id`),
  CONSTRAINT `FKp1vkgxjs3ifhxelqlmt4hkhod` FOREIGN KEY (`status_id`) REFERENCES `movies_status` (`movie_status_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies`
--

LOCK TABLES `movies` WRITE;
/*!40000 ALTER TABLE `movies` DISABLE KEYS */;
INSERT INTO `movies` VALUES ('mv-0001','Greta Lee, Teo Yoo','Muôn Kiếp Nhân Duyên xoay quanh hai nhân vật chính - Nora (Greta Lee) và Hae Sung (Teo Yoo). Tình bạn thân thiết của họ bị chia cắt khi Nora theo gia đình di cư khỏi Hàn Quốc vào năm 12 tuổi. 20 năm sau, như một mối duyên tiền định, họ gặp lại nhau tại Mỹ','Celine','Tiếng Anh','Past Lives','Muôn Kiếp Nhân Duyên',3,'https://cdn.discordapp.com/attachments/1159668660340789259/1169947106191093840/both-water.png?ex=65574126&is=6544cc26&hm=033c5de326692af06130dcc0e32cc684a441d99a98eecdc1c8e90cc028c27727&','  A24',13,'2023-10-10',106,'past-lives','https://www.youtube.com/watch?v=lBdLBY249Do',4,'2023-11-25','https://cdn.discordapp.com/attachments/1159668660340789259/1170792832735387688/2-ga-ran.png?ex=655a54cb&is=6547dfcb&hm=9a0fcbd579af5a185b42278b807c3c3658a38c89ec598febf2d880daae1eb599&',25),('mv-0002','Patience Munchenbach, Guillaume Canet, Laetitia Dosch','Giữa một đợt nắng nóng bất thường, thì mây mưa giống như làn gió xua tan oi bức. Nhưng những đám mây kỳ lạ trên bầu trời nước Pháp lại đem tới cho con người sự huỷ diệt và chết chóc. Hàng loạt trận mưa axit đổ xuống, đốt cháy mạng sống của vạn vật dưới mặ','Just Philippot','Tiếng Anh','Acide','THẢM HỌA MƯA AXIT',1,'https://cdn.galaxycine.vn/media/2023/9/21/500x750_1695284002644.jpg','Pathé',13,'2024-03-16',101,'acide','https://www.youtube.com/watch?v=SdXxuuJmXm8',1,'2024-04-27','https://cdn.discordapp.com/attachments/1159668660340789259/1176092840787460186/dev.jpg?ex=656d9ccf&is=655b27cf&hm=d05be04a9afe9cea11e7a2104dd4ca46f184f1cbc5640454ce93aab4dd6ff022&',10),('mv-0003','Jin Seon-Kyu, Yoo Hae Jin, Kim Hee Seon','Chuyện phim xoay quanh nhân vật Chi-ho (Yoo Hae-jin) - nhà nghiên cứu bim bim với khả năng nếm vị xuất chúng, nhưng lại ngờ nghệch với mọi thứ xung quanh. Chi-ho là một người cực kỳ hướng nội, thích ở một mình và sống như một cái máy được lập trình sẵn mà','Lee Han','Tiếng Anh','Honey Sweet','NHÂN DUYÊN TIỀN ĐÌNH',3,'https://cdn.galaxycine.vn/media/2023/9/19/500x750_1695095394242.jpg','Sang',13,'2023-11-10',119,'honey-sweet','https://www.youtube.com/watch?v=k6S4DE8P7mY',2,'2023-12-30',NULL,11),('mv-0004','Kristen Bell, Mckenna Grace, James Marsden','Một mảnh thiên thạch kỳ diệu đã rơi xuống Thành Phố Phiêu Lưu, mang đến siêu năng lực cho những chú cún PAW Patrol và biến chúng thành Những Chú Cún Quyền Năng. Phim mới PAW Patrol: Phim Siêu Đẳng ra m','Cal Brunker','Tiếng An','PAW Patrol: The Mighty Movie','PAW PATROL: PHIM SIÊU ĐẲNG',5,'https://cdn.discordapp.com/attachments/1157346998136090685/1179937231541121135/VerticalPoster06.png?ex=657b992e&is=6569242e&hm=f20b348160c498dc6d02440a5df0e8919a95c63a91dae482c631c48a5ce3ef1f&','Sang',13,'2023-11-27',93,'paw-patrol-the-mighty-movie','https://www.youtube.com/watch?v=O_zaLcbqGtI',2,'2024-02-14','https://cdn.discordapp.com/attachments/1159668660340789259/1178269407802298408/4069621.jpg?ex=657587e6&is=656312e6&hm=db883b620f6f1700b739008ee1741cdd839f5d7e9ada1ff4986375bdc2dc225e&',12),('mv-0005','Minh Hoàng','Minh Hoàng: Điểm Sáng Trên Dòng Sóng Kỷ Nguyên Công Nghệ là một bộ phim tài liệu chân thực và đầy cảm hứng, khám phá cuộc đời đầy sóng gió của một lập trình viên tài năng mang tên Minh Hoàng. Phim dự kiến sẽ đưa người xem vào cuộc hành trình đầy thách thức của Minh Hoàng từ khi còn trẻ đến hình ảnh một nhà lập trình xuất sắc và sáng tạo.\n\nTrải qua những cuộc phỏng vấn chân thành và những cảnh quay tuyệt đẹp, bộ phim này sẽ khám phá sự khởi đầu của Minh Hoàng, từ việc bắt đầu học lập trình ở tuổi 12 tới những áp lực và khó khăn trong việc xây dựng sự nghiệp. Người xem sẽ thấy rằng Minh Hoàng đã phải đối mặt với những thử thách ngoạn mục, như thất bại trong các dự án quan trọng, căng thẳng tinh thần và áp lực của ngành công nghiệp công nghệ thông tin đầy cạnh tranh.\n\nTuy nhiên, bằng sự kiên trì và đam mê không ngừng, Minh Hoàng đã trở thành một lập trình viên nổi tiếng và tạo ra những ứng dụng và dự án ấn tượng. Cuộc hành trình này không chỉ đặt ra câu hỏi về sự hy sinh và tận tâm trong công việc, mà còn là câu chuyện về khát vọng, sự đổi mới và sức mạnh của kiến thức trong thời đại số hóa.\n\nMinh Hoàng: Điểm Sáng Trên Dòng Sóng Kỷ Nguyên Công Nghệ sẽ đưa người xem vào thế giới phức tạp và đầy cảm hứng của một lập trình viên tài năng, tạo nên cơ hội để họ hiểu thêm về cuộc đời và tinh thần lập trình viên xuất sắc. Phim không chỉ là một bức tranh về cá nhân, mà còn là cả một tượng đài cho sự khao khát và khả năng đổi đời của bất kỳ ai dám mơ ước và làm việc hết mình trong lĩnh vực công nghệ thông tin.','Thành Đạt','TIếng Em','Minh Hoàng đây rồi','Minh Hoang is here',1,'https://cdn.discordapp.com/attachments/1159668660340789259/1170768260313133167/255271622_957908704808243_8030076711602587439_n.jpg?ex=6575ed69&is=65637869&hm=4e259c85a4c685035526a302ae89d3e9b12a3ed5af3fbb8273b93e35cf02f596&','Khánh Hoàng',18,'2023-12-06',118,'minh-hoang-ay-roi','https://www.youtube.com/watch?v=XC3ftnZ1WYk',2,'2024-01-06','https://cdn.discordapp.com/attachments/1159668660340789259/1179820926154575932/4081103.jpg?ex=657b2cdc&is=6568b7dc&hm=3cd1450f928ea23baaa2d962d2da052f3a0e1fba9a1c56aea9a852a536587894&',1),('mv-0006','  Jason Statham, Sylvester Stallone, Megan Fox','Biệt Đội Đánh Thuê 4 (tựa gốc: Expend4ables) tiếp tục theo chân biệt đội đặc biệt này thực hiện các nhiệm vụ mới, lần này là ngăn chặn ông trùm khủng bố Suarto, với âm mưu buôn lậu vũ khí hạt nhân và kích động chiến tranh giữa 2 phe Nga và Mỹ.','Scott Waughs','Tiếng Anh','Expend4ables','Biệt Đội Đánh Thuê 4',2,'https://cdn.galaxycine.vn/media/2023/9/18/500x750_1695010127758.jpg','Campbell Grobman Films, Lionsgate',13,'2023-11-20',103,'expend4ables','https://www.youtube.com/watch?v=DhlaBO-SwVE',2,'2024-01-02','https://cdn.discordapp.com/attachments/1159668660340789259/1179774559856099368/HorizontalPoster.png?ex=657b01ae&is=65688cae&hm=137be25ce67a9dec90e89b84b6921adea39da6f22caf269cf8c360a0d29c19f0&',6),('mv-0007','Josh O\'Connor, Mike Faist, Zendaya','Theo chân ba tay vợt quen biết nhau khi còn là thanh thiếu niên đến khi họ thi đấu trong một giải đấu quần vợt để trở thành người chiến thắng giải Grand Slam nổi tiếng thế giới, đồng thời khơi lại những kỳ phùng địch thủ cũ trong và ngoài sân đấu.','Luca Guadagnino','Tiếng Anh','Challengers','NHỮNG KẺ THÁCH ĐẤU',2,'https://cdn.galaxycine.vn/media/2023/9/18/500x750_1695010127758.jpg','Warner Bros',13,'2024-01-24',120,'challengers','https://www.youtube.com/watch?v=eZtOHAkFxgI',1,'2024-02-05',NULL,8),('mv-0008','Georgia Eyers, Dan Ewing, Tim Pocock','Sau một biến cố đau thương, Lara liên tục gặp phải những ảo giác đầy kinh hãi và dần mất kiểm soát tinh thần. Những vũ điệu cuồng loạn, sự xuất hiện của quỷ dữ khiến chồng cô đặt niềm tin cứu rỗi linh hồn của vợ vào một nhóm trừ tà. Niềm tin tôn giáo sẽ c','Georgia Eyers, Dan Ewing, Tim Pocock','Tiếng Anh','Godless','VŨ ĐIỆU QUỶ DỮ',0,'https://cdn.galaxycine.vn/media/2023/9/19/500x750_1695094850730.jpg','Iris Arc Pictures',13,'2023-09-28',91,'godless','',4,'2023-10-20',NULL,0);
/*!40000 ALTER TABLE `movies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies_images`
--

DROP TABLE IF EXISTS `movies_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movies_images` (
  `image_id` int NOT NULL AUTO_INCREMENT,
  `extension` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `path` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `movie_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`image_id`),
  KEY `FKbxrlom4skf0pso8w8i2ilx7n6` (`movie_id`),
  CONSTRAINT `FKbxrlom4skf0pso8w8i2ilx7n6` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`movie_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies_images`
--

LOCK TABLES `movies_images` WRITE;
/*!40000 ALTER TABLE `movies_images` DISABLE KEYS */;
INSERT INTO `movies_images` VALUES (1,'jpg','https://media.lottecinemavn.com/Media/MovieFile/MovieImg/202310/11268_105_100003.jpg','mv-0005'),(2,'jpg','https://media.lottecinemavn.com/Media/MovieFile/MovieImg/202310/11268_105_100002.jpg','mv-0005'),(27,'png','https://cdn.discordapp.com/attachments/1159668660340789259/1162014363264241785/chips-removebg-preview.png?ex=653a6535&is=6527f035&hm=2233f8fe7cbcfebc547f598ac966fbb6049c37670e2a765a01b0df278e9baaa9&','mv-0005'),(28,'png','https://cdn.discordapp.com/attachments/1159668660340789259/1170797108794773635/6-nuggets.png?ex=655a58c7&is=6547e3c7&hm=1300493c54a63ce99d63c7921e2c040bcb3f44620c764f2a60a64ec8fcd02afd&','mv-0001'),(29,'png','https://cdn.discordapp.com/attachments/1159668660340789259/1170803129068044408/both-water.png?ex=655a5e62&is=6547e962&hm=cab20d4f38230cd1e3d15cc5d325e461a3d1728619d59d504c83d4a15cfe1181&','mv-0005'),(30,'png','https://cdn.discordapp.com/attachments/1159668660340789259/1170896724773044234/mcchicken-deluxe.png?ex=655ab58d&is=6548408d&hm=1ebd63eb051031a8d0fa31fefd6745fc7323eaa26759cf26f04153fd6e15219c&','mv-0005'),(38,'webp','https://cdn.discordapp.com/attachments/1157347026003050566/1181084806642151504/Anatomy-of-an-API-request-1-1.webp?ex=657fc5f1&is=656d50f1&hm=b96d01ccc654c0ba5c7c7129f8f3021d7aa3b630a18062d07af145ff8b06aa33&','mv-0005');
/*!40000 ALTER TABLE `movies_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movies_status`
--

DROP TABLE IF EXISTS `movies_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `movies_status` (
  `movie_status_id` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `slug` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`movie_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movies_status`
--

LOCK TABLES `movies_status` WRITE;
/*!40000 ALTER TABLE `movies_status` DISABLE KEYS */;
INSERT INTO `movies_status` VALUES (1,'Coming soon','coming-soon'),(2,'Showing now','showing-now'),(3,'Replay','replay'),(4,'No show','no-show');
/*!40000 ALTER TABLE `movies_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `role_id` int NOT NULL,
  `role_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'CUSTOMER'),(3,'GUEST');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_status`
--

DROP TABLE IF EXISTS `room_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `room_status` (
  `room_status_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`room_status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_status`
--

LOCK TABLES `room_status` WRITE;
/*!40000 ALTER TABLE `room_status` DISABLE KEYS */;
INSERT INTO `room_status` VALUES (1,'Hoạt động'),(2,'Đóng'),(3,'Bảo trì');
/*!40000 ALTER TABLE `room_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rooms`
--

DROP TABLE IF EXISTS `rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `rooms` (
  `room_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `available_seats` int DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `total_seats` int DEFAULT NULL,
  `cinema_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `slug` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status_id` int NOT NULL,
  PRIMARY KEY (`room_id`),
  KEY `FKjp9bjtvlojbw581bpq23cpw4j` (`cinema_id`),
  KEY `FK5vj17x4vimvmqdpd4ndgp4ebx` (`status_id`),
  CONSTRAINT `FK5vj17x4vimvmqdpd4ndgp4ebx` FOREIGN KEY (`status_id`) REFERENCES `room_status` (`room_status_id`),
  CONSTRAINT `FKjp9bjtvlojbw581bpq23cpw4j` FOREIGN KEY (`cinema_id`) REFERENCES `cinemas` (`cinema_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rooms`
--

LOCK TABLES `rooms` WRITE;
/*!40000 ALTER TABLE `rooms` DISABLE KEYS */;
INSERT INTO `rooms` VALUES ('Room001',150,'RAP 1-Trung Chánh',150,'Cinema001','rap-1-trung-chanh',1),('Room002',150,'RAP 2-Trung Chánh',150,'Cinema001','rap-2-trung-chanh',1),('Room003',150,'RAP 3-Trung Chánh',150,'Cinema001','rap-3-trung-chanh',1),('Room004',150,'RAP 1-Nguyễn Du',150,'Cinema002','rap-1-nguyen-du',1),('Room005',150,'RAP 2-Nguyễn Du',150,'Cinema002','rap-2-nguyen-du',1),('Room006',150,'RAP 1',150,'Cinema003','rap-1',1),('Room007',150,'RAP 2',150,'Cinema003','rap-2',1);
/*!40000 ALTER TABLE `rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seats_rooms`
--

DROP TABLE IF EXISTS `seats_rooms`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seats_rooms` (
  `seat_room_id` int NOT NULL AUTO_INCREMENT,
  `row` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `row_index` int DEFAULT NULL,
  `status` tinyint(1) DEFAULT '1',
  `room_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `seat_type_id` int NOT NULL,
  PRIMARY KEY (`seat_room_id`),
  KEY `FKfu2c510l2cl8lblj3x3x4o0vl` (`room_id`),
  KEY `FK3cwtx4s7f8dbcrghl8uv2s08d` (`seat_type_id`),
  CONSTRAINT `FK3cwtx4s7f8dbcrghl8uv2s08d` FOREIGN KEY (`seat_type_id`) REFERENCES `seats_type` (`seat_type_id`),
  CONSTRAINT `FKfu2c510l2cl8lblj3x3x4o0vl` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1229 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seats_rooms`
--

LOCK TABLES `seats_rooms` WRITE;
/*!40000 ALTER TABLE `seats_rooms` DISABLE KEYS */;
INSERT INTO `seats_rooms` VALUES (1,'A',1,1,'Room001',1),(2,'A',2,1,'Room001',1),(3,'A',3,1,'Room001',1),(4,'A',4,1,'Room001',1),(5,'A',5,1,'Room001',1),(6,'A',6,1,'Room001',1),(7,'A',7,1,'Room001',1),(8,'A',8,1,'Room001',1),(9,'A',9,1,'Room001',1),(10,'A',10,1,'Room001',1),(11,'A',11,1,'Room001',1),(12,'A',12,1,'Room001',1),(13,'A',13,1,'Room001',1),(14,'A',14,1,'Room001',1),(15,'A',15,1,'Room001',1),(16,'B',1,1,'Room001',1),(17,'B',2,1,'Room001',1),(18,'B',3,1,'Room001',1),(19,'B',4,1,'Room001',1),(20,'B',5,1,'Room001',1),(21,'B',6,1,'Room001',1),(22,'B',7,1,'Room001',1),(23,'B',8,1,'Room001',1),(24,'B',9,1,'Room001',1),(25,'B',10,1,'Room001',1),(26,'B',11,1,'Room001',1),(27,'B',12,1,'Room001',1),(28,'B',13,1,'Room001',1),(29,'B',14,1,'Room001',1),(30,'B',15,1,'Room001',1),(31,'C',1,1,'Room001',1),(32,'C',2,1,'Room001',1),(33,'C',3,1,'Room001',1),(34,'C',4,1,'Room001',1),(35,'C',5,1,'Room001',1),(36,'C',6,1,'Room001',1),(37,'C',7,1,'Room001',1),(38,'C',8,1,'Room001',1),(39,'C',9,1,'Room001',1),(40,'C',10,1,'Room001',1),(41,'C',11,1,'Room001',1),(42,'C',12,1,'Room001',1),(43,'C',13,1,'Room001',1),(44,'C',14,1,'Room001',1),(45,'C',15,1,'Room001',1),(46,'D',1,1,'Room001',1),(47,'D',2,1,'Room001',1),(48,'D',3,1,'Room001',1),(49,'D',4,1,'Room001',1),(50,'D',5,1,'Room001',1),(51,'D',6,1,'Room001',1),(52,'D',7,1,'Room001',1),(53,'D',8,1,'Room001',1),(54,'D',9,1,'Room001',1),(55,'D',10,1,'Room001',1),(56,'D',11,1,'Room001',1),(57,'D',12,1,'Room001',1),(58,'D',13,1,'Room001',1),(59,'D',14,1,'Room001',1),(60,'D',15,1,'Room001',1),(61,'E',1,1,'Room001',1),(62,'E',2,1,'Room001',1),(63,'E',3,1,'Room001',1),(64,'E',4,1,'Room001',1),(65,'E',5,1,'Room001',1),(66,'E',6,1,'Room001',2),(67,'E',7,1,'Room001',2),(68,'E',8,1,'Room001',2),(69,'E',9,1,'Room001',2),(70,'E',10,1,'Room001',2),(71,'E',11,1,'Room001',1),(72,'E',12,1,'Room001',1),(73,'E',13,1,'Room001',1),(74,'E',14,1,'Room001',1),(75,'E',15,1,'Room001',1),(76,'F',1,1,'Room001',1),(77,'F',2,1,'Room001',1),(78,'F',3,1,'Room001',1),(79,'F',4,1,'Room001',1),(80,'F',5,1,'Room001',1),(81,'F',6,1,'Room001',2),(82,'F',7,1,'Room001',2),(83,'F',8,1,'Room001',2),(84,'F',9,1,'Room001',2),(85,'F',10,1,'Room001',2),(86,'F',11,1,'Room001',1),(87,'F',12,1,'Room001',1),(88,'F',13,1,'Room001',1),(89,'F',14,1,'Room001',1),(90,'F',15,1,'Room001',1),(91,'G',1,1,'Room001',1),(92,'G',2,1,'Room001',1),(93,'G',3,1,'Room001',1),(94,'G',4,1,'Room001',1),(95,'G',5,1,'Room001',1),(96,'G',6,1,'Room001',2),(97,'G',7,1,'Room001',2),(98,'G',8,1,'Room001',2),(99,'G',9,1,'Room001',2),(100,'G',10,1,'Room001',2),(101,'G',11,1,'Room001',1),(102,'G',12,1,'Room001',1),(103,'G',13,1,'Room001',1),(104,'G',14,1,'Room001',1),(105,'G',15,1,'Room001',1),(106,'H',1,1,'Room001',1),(107,'H',2,1,'Room001',1),(108,'H',3,1,'Room001',1),(109,'H',4,1,'Room001',1),(110,'H',5,1,'Room001',1),(111,'H',6,1,'Room001',2),(112,'H',7,1,'Room001',2),(113,'H',8,1,'Room001',2),(114,'H',9,1,'Room001',2),(115,'H',10,1,'Room001',2),(116,'H',11,1,'Room001',1),(117,'H',12,1,'Room001',1),(118,'H',13,1,'Room001',1),(119,'H',14,1,'Room001',1),(120,'H',15,1,'Room001',1),(121,'I',1,1,'Room001',1),(122,'I',2,1,'Room001',1),(123,'I',3,1,'Room001',1),(124,'I',4,1,'Room001',1),(125,'I',5,1,'Room001',1),(126,'I',6,1,'Room001',1),(127,'I',7,1,'Room001',1),(128,'I',8,1,'Room001',1),(129,'I',9,1,'Room001',1),(130,'I',10,1,'Room001',1),(131,'I',11,1,'Room001',1),(132,'I',12,1,'Room001',1),(133,'I',13,1,'Room001',1),(134,'I',14,1,'Room001',1),(135,'I',15,1,'Room001',1),(136,'J',1,1,'Room001',1),(137,'J',2,1,'Room001',1),(138,'J',3,1,'Room001',1),(139,'J',4,1,'Room001',1),(140,'J',5,1,'Room001',1),(141,'J',6,1,'Room001',1),(142,'J',7,1,'Room001',1),(143,'J',8,1,'Room001',1),(144,'J',9,1,'Room001',1),(145,'J',10,1,'Room001',1),(146,'J',11,1,'Room001',1),(147,'J',12,1,'Room001',1),(148,'J',13,1,'Room001',1),(149,'J',14,1,'Room001',1),(150,'J',15,1,'Room001',1),(151,'A',1,1,'Room002',1),(152,'A',2,1,'Room002',1),(153,'A',3,1,'Room002',1),(154,'A',4,1,'Room002',1),(155,'A',5,1,'Room002',1),(156,'A',6,1,'Room002',1),(157,'A',7,1,'Room002',1),(158,'A',8,1,'Room002',1),(159,'A',9,1,'Room002',1),(160,'A',10,1,'Room002',1),(161,'A',11,1,'Room002',1),(162,'A',12,1,'Room002',1),(163,'A',13,1,'Room002',1),(164,'A',14,1,'Room002',1),(165,'A',15,1,'Room002',1),(166,'B',1,1,'Room002',1),(167,'B',2,1,'Room002',1),(168,'B',3,1,'Room002',1),(169,'B',4,1,'Room002',1),(170,'B',5,1,'Room002',1),(171,'B',6,1,'Room002',1),(172,'B',7,1,'Room002',1),(173,'B',8,1,'Room002',1),(174,'B',9,1,'Room002',1),(175,'B',10,1,'Room002',1),(176,'B',11,1,'Room002',1),(177,'B',12,1,'Room002',1),(178,'B',13,1,'Room002',1),(179,'B',14,1,'Room002',1),(180,'B',15,1,'Room002',1),(181,'C',1,1,'Room002',1),(182,'C',2,1,'Room002',1),(183,'C',3,1,'Room002',1),(184,'C',4,1,'Room002',1),(185,'C',5,1,'Room002',1),(186,'C',6,1,'Room002',1),(187,'C',7,1,'Room002',1),(188,'C',8,1,'Room002',1),(189,'C',9,1,'Room002',1),(190,'C',10,1,'Room002',1),(191,'C',11,1,'Room002',1),(192,'C',12,1,'Room002',1),(193,'C',13,1,'Room002',1),(194,'C',14,1,'Room002',1),(195,'C',15,1,'Room002',1),(196,'D',1,1,'Room002',1),(197,'D',2,1,'Room002',1),(198,'D',3,1,'Room002',1),(199,'D',4,1,'Room002',1),(200,'D',5,1,'Room002',1),(201,'D',6,1,'Room002',1),(202,'D',7,1,'Room002',1),(203,'D',8,1,'Room002',1),(204,'D',9,1,'Room002',1),(205,'D',10,1,'Room002',1),(206,'D',11,1,'Room002',1),(207,'D',12,1,'Room002',1),(208,'D',13,1,'Room002',1),(209,'D',14,1,'Room002',1),(210,'D',15,1,'Room002',1),(211,'E',1,1,'Room002',1),(212,'E',2,1,'Room002',1),(213,'E',3,1,'Room002',1),(214,'E',4,1,'Room002',1),(215,'E',5,1,'Room002',1),(216,'E',6,1,'Room002',2),(217,'E',7,1,'Room002',2),(218,'E',8,1,'Room002',2),(219,'E',9,1,'Room002',2),(220,'E',10,1,'Room002',2),(221,'E',11,1,'Room002',1),(222,'E',12,1,'Room002',1),(223,'E',13,1,'Room002',1),(224,'E',14,1,'Room002',1),(225,'E',15,1,'Room002',1),(226,'F',1,1,'Room002',1),(227,'F',2,1,'Room002',1),(228,'F',3,1,'Room002',1),(229,'F',4,1,'Room002',1),(230,'F',5,1,'Room002',1),(231,'F',6,1,'Room002',2),(232,'F',7,1,'Room002',2),(233,'F',8,1,'Room002',2),(234,'F',9,1,'Room002',2),(235,'F',10,1,'Room002',2),(236,'F',11,1,'Room002',1),(237,'F',12,1,'Room002',1),(238,'F',13,1,'Room002',1),(239,'F',14,1,'Room002',1),(240,'F',15,1,'Room002',1),(241,'G',1,1,'Room002',1),(242,'G',2,1,'Room002',1),(243,'G',3,1,'Room002',1),(244,'G',4,1,'Room002',1),(245,'G',5,1,'Room002',1),(246,'G',6,1,'Room002',2),(247,'G',7,1,'Room002',2),(248,'G',8,1,'Room002',2),(249,'G',9,1,'Room002',2),(250,'G',10,1,'Room002',2),(251,'G',11,1,'Room002',1),(252,'G',12,1,'Room002',1),(253,'G',13,1,'Room002',1),(254,'G',14,1,'Room002',1),(255,'G',15,1,'Room002',1),(256,'H',1,1,'Room002',1),(257,'H',2,1,'Room002',1),(258,'H',3,1,'Room002',1),(259,'H',4,1,'Room002',1),(260,'H',5,1,'Room002',1),(261,'H',6,1,'Room002',2),(262,'H',7,1,'Room002',2),(263,'H',8,1,'Room002',2),(264,'H',9,1,'Room002',2),(265,'H',10,1,'Room002',2),(266,'H',11,1,'Room002',1),(267,'H',12,1,'Room002',1),(268,'H',13,1,'Room002',1),(269,'H',14,1,'Room002',1),(270,'H',15,1,'Room002',1),(271,'I',1,1,'Room002',1),(272,'I',2,1,'Room002',1),(273,'I',3,1,'Room002',1),(274,'I',4,1,'Room002',1),(275,'I',5,1,'Room002',1),(276,'I',6,1,'Room002',1),(277,'I',7,1,'Room002',1),(278,'I',8,1,'Room002',1),(279,'I',9,1,'Room002',1),(280,'I',10,1,'Room002',1),(281,'I',11,1,'Room002',1),(282,'I',12,1,'Room002',1),(283,'I',13,1,'Room002',1),(284,'I',14,1,'Room002',1),(285,'I',15,1,'Room002',1),(286,'J',1,1,'Room002',1),(287,'J',2,1,'Room002',1),(288,'J',3,1,'Room002',1),(289,'J',4,1,'Room002',1),(290,'J',5,1,'Room002',1),(291,'J',6,1,'Room002',1),(292,'J',7,1,'Room002',1),(293,'J',8,1,'Room002',1),(294,'J',9,1,'Room002',1),(295,'J',10,1,'Room002',1),(296,'J',11,1,'Room002',1),(297,'J',12,1,'Room002',1),(298,'J',13,1,'Room002',1),(299,'J',14,1,'Room002',1),(300,'J',15,1,'Room002',1),(301,'A',1,1,'Room003',1),(302,'A',2,1,'Room003',1),(303,'A',3,1,'Room003',1),(304,'A',4,1,'Room003',1),(305,'A',5,1,'Room003',1),(306,'A',6,1,'Room003',1),(307,'A',7,1,'Room003',1),(308,'A',8,1,'Room003',1),(309,'A',9,1,'Room003',1),(310,'A',10,1,'Room003',1),(311,'A',11,1,'Room003',1),(312,'A',12,1,'Room003',1),(313,'A',13,1,'Room003',1),(314,'A',14,1,'Room003',1),(315,'A',15,1,'Room003',1),(316,'B',1,1,'Room003',1),(317,'B',2,1,'Room003',1),(318,'B',3,1,'Room003',1),(319,'B',4,1,'Room003',1),(320,'B',5,1,'Room003',1),(321,'B',6,1,'Room003',1),(322,'B',7,1,'Room003',1),(323,'B',8,1,'Room003',1),(324,'B',9,1,'Room003',1),(325,'B',10,1,'Room003',1),(326,'B',11,1,'Room003',1),(327,'B',12,1,'Room003',1),(328,'B',13,1,'Room003',1),(329,'B',14,1,'Room003',1),(330,'B',15,1,'Room003',1),(331,'C',1,1,'Room003',1),(332,'C',2,1,'Room003',1),(333,'C',3,1,'Room003',1),(334,'C',4,1,'Room003',1),(335,'C',5,1,'Room003',1),(336,'C',6,1,'Room003',1),(337,'C',7,1,'Room003',1),(338,'C',8,1,'Room003',1),(339,'C',9,1,'Room003',1),(340,'C',10,1,'Room003',1),(341,'C',11,1,'Room003',1),(342,'C',12,1,'Room003',1),(343,'C',13,1,'Room003',1),(344,'C',14,1,'Room003',1),(345,'C',15,1,'Room003',1),(346,'D',1,1,'Room003',1),(347,'D',2,1,'Room003',1),(348,'D',3,1,'Room003',1),(349,'D',4,1,'Room003',1),(350,'D',5,1,'Room003',1),(351,'D',6,1,'Room003',1),(352,'D',7,1,'Room003',1),(353,'D',8,1,'Room003',1),(354,'D',9,1,'Room003',1),(355,'D',10,1,'Room003',1),(356,'D',11,1,'Room003',1),(357,'D',12,1,'Room003',1),(358,'D',13,1,'Room003',1),(359,'D',14,1,'Room003',1),(360,'D',15,1,'Room003',1),(361,'E',1,1,'Room003',1),(362,'E',2,1,'Room003',1),(363,'E',3,1,'Room003',1),(364,'E',4,1,'Room003',1),(365,'E',5,1,'Room003',1),(366,'E',6,1,'Room003',2),(367,'E',7,1,'Room003',2),(368,'E',8,1,'Room003',2),(369,'E',9,1,'Room003',2),(370,'E',10,1,'Room003',2),(371,'E',11,1,'Room003',1),(372,'E',12,1,'Room003',1),(373,'E',13,1,'Room003',1),(374,'E',14,1,'Room003',1),(375,'E',15,1,'Room003',1),(376,'F',1,1,'Room003',1),(377,'F',2,1,'Room003',1),(378,'F',3,1,'Room003',1),(379,'F',4,1,'Room003',1),(380,'F',5,1,'Room003',1),(381,'F',6,1,'Room003',2),(382,'F',7,1,'Room003',2),(383,'F',8,1,'Room003',2),(384,'F',9,1,'Room003',2),(385,'F',10,1,'Room003',2),(386,'F',11,1,'Room003',1),(387,'F',12,1,'Room003',1),(388,'F',13,1,'Room003',1),(389,'F',14,1,'Room003',1),(390,'F',15,1,'Room003',1),(391,'G',1,1,'Room003',1),(392,'G',2,1,'Room003',1),(393,'G',3,1,'Room003',1),(394,'G',4,1,'Room003',1),(395,'G',5,1,'Room003',1),(396,'G',6,1,'Room003',2),(397,'G',7,1,'Room003',2),(398,'G',8,1,'Room003',2),(399,'G',9,1,'Room003',2),(400,'G',10,1,'Room003',2),(401,'G',11,1,'Room003',1),(402,'G',12,1,'Room003',1),(403,'G',13,1,'Room003',1),(404,'G',14,1,'Room003',1),(405,'G',15,1,'Room003',1),(406,'H',1,1,'Room003',1),(407,'H',2,1,'Room003',1),(408,'H',3,1,'Room003',1),(409,'H',4,1,'Room003',1),(410,'H',5,1,'Room003',1),(411,'H',6,1,'Room003',2),(412,'H',7,1,'Room003',2),(413,'H',8,1,'Room003',2),(414,'H',9,1,'Room003',2),(415,'H',10,1,'Room003',2),(416,'H',11,1,'Room003',1),(417,'H',12,1,'Room003',1),(418,'H',13,1,'Room003',1),(419,'H',14,1,'Room003',1),(420,'H',15,1,'Room003',1),(421,'I',1,1,'Room003',1),(422,'I',2,1,'Room003',1),(423,'I',3,1,'Room003',1),(424,'I',4,1,'Room003',1),(425,'I',5,1,'Room003',1),(426,'I',6,1,'Room003',1),(427,'I',7,1,'Room003',1),(428,'I',8,1,'Room003',1),(429,'I',9,1,'Room003',1),(430,'I',10,1,'Room003',1),(431,'I',11,1,'Room003',1),(432,'I',12,1,'Room003',1),(433,'I',13,1,'Room003',1),(434,'I',14,1,'Room003',1),(435,'I',15,1,'Room003',1),(436,'J',1,1,'Room003',1),(437,'J',2,1,'Room003',1),(438,'J',3,1,'Room003',1),(439,'J',4,1,'Room003',1),(440,'J',5,1,'Room003',1),(441,'J',6,1,'Room003',1),(442,'J',7,1,'Room003',1),(443,'J',8,1,'Room003',1),(444,'J',9,1,'Room003',1),(445,'J',10,1,'Room003',1),(446,'J',11,1,'Room003',1),(447,'J',12,1,'Room003',1),(448,'J',13,1,'Room003',1),(449,'J',14,1,'Room003',1),(450,'J',15,1,'Room003',1),(451,'A',1,1,'Room004',1),(452,'A',2,1,'Room004',1),(453,'A',3,1,'Room004',1),(454,'A',4,1,'Room004',1),(455,'A',5,1,'Room004',1),(456,'A',6,1,'Room004',1),(457,'A',7,1,'Room004',1),(458,'A',8,1,'Room004',1),(459,'A',9,1,'Room004',1),(460,'A',10,1,'Room004',1),(461,'A',11,1,'Room004',1),(462,'A',12,1,'Room004',1),(463,'A',13,1,'Room004',1),(464,'A',14,1,'Room004',1),(465,'A',15,1,'Room004',1),(466,'B',1,1,'Room004',1),(467,'B',2,1,'Room004',1),(468,'B',3,1,'Room004',1),(469,'B',4,1,'Room004',1),(470,'B',5,1,'Room004',1),(471,'B',6,1,'Room004',1),(472,'B',7,1,'Room004',1),(473,'B',8,1,'Room004',1),(474,'B',9,1,'Room004',1),(475,'B',10,1,'Room004',1),(476,'B',11,1,'Room004',1),(477,'B',12,1,'Room004',1),(478,'B',13,1,'Room004',1),(479,'B',14,1,'Room004',1),(480,'B',15,1,'Room004',1),(481,'C',1,1,'Room004',1),(482,'C',2,1,'Room004',1),(483,'C',3,1,'Room004',1),(484,'C',4,1,'Room004',1),(485,'C',5,1,'Room004',1),(486,'C',6,1,'Room004',1),(487,'C',7,1,'Room004',1),(488,'C',8,1,'Room004',1),(489,'C',9,1,'Room004',1),(490,'C',10,1,'Room004',1),(491,'C',11,1,'Room004',1),(492,'C',12,1,'Room004',1),(493,'C',13,1,'Room004',1),(494,'C',14,1,'Room004',1),(495,'C',15,1,'Room004',1),(496,'D',1,1,'Room004',1),(497,'D',2,1,'Room004',1),(498,'D',3,1,'Room004',1),(499,'D',4,1,'Room004',1),(500,'D',5,1,'Room004',1),(501,'D',6,1,'Room004',1),(502,'D',7,1,'Room004',1),(503,'D',8,1,'Room004',1),(504,'D',9,1,'Room004',1),(505,'D',10,1,'Room004',1),(506,'D',11,1,'Room004',1),(507,'D',12,1,'Room004',1),(508,'D',13,1,'Room004',1),(509,'D',14,1,'Room004',1),(510,'D',15,1,'Room004',1),(511,'E',1,1,'Room004',1),(512,'E',2,1,'Room004',1),(513,'E',3,1,'Room004',1),(514,'E',4,1,'Room004',1),(515,'E',5,1,'Room004',1),(516,'E',6,1,'Room004',2),(517,'E',7,1,'Room004',2),(518,'E',8,1,'Room004',2),(519,'E',9,1,'Room004',2),(520,'E',10,1,'Room004',2),(521,'E',11,1,'Room004',1),(522,'E',12,1,'Room004',1),(523,'E',13,1,'Room004',1),(524,'E',14,1,'Room004',1),(525,'E',15,1,'Room004',1),(526,'F',1,1,'Room004',1),(527,'F',2,1,'Room004',1),(528,'F',3,1,'Room004',1),(529,'F',4,1,'Room004',1),(530,'F',5,1,'Room004',1),(531,'F',6,1,'Room004',2),(532,'F',7,1,'Room004',2),(533,'F',8,1,'Room004',2),(534,'F',9,1,'Room004',2),(535,'F',10,1,'Room004',2),(536,'F',11,1,'Room004',1),(537,'F',12,1,'Room004',1),(538,'F',13,1,'Room004',1),(539,'F',14,1,'Room004',1),(540,'F',15,1,'Room004',1),(541,'G',1,1,'Room004',1),(542,'G',2,1,'Room004',1),(543,'G',3,1,'Room004',1),(544,'G',4,1,'Room004',1),(545,'G',5,1,'Room004',1),(546,'G',6,1,'Room004',2),(547,'G',7,1,'Room004',2),(548,'G',8,1,'Room004',2),(549,'G',9,1,'Room004',2),(550,'G',10,1,'Room004',2),(551,'G',11,1,'Room004',1),(552,'G',12,1,'Room004',1),(553,'G',13,1,'Room004',1),(554,'G',14,1,'Room004',1),(555,'G',15,1,'Room004',1),(556,'H',1,1,'Room004',1),(557,'H',2,1,'Room004',1),(558,'H',3,1,'Room004',1),(559,'H',4,1,'Room004',1),(560,'H',5,1,'Room004',1),(561,'H',6,1,'Room004',2),(562,'H',7,1,'Room004',2),(563,'H',8,1,'Room004',2),(564,'H',9,1,'Room004',2),(565,'H',10,1,'Room004',2),(566,'H',11,1,'Room004',1),(567,'H',12,1,'Room004',1),(568,'H',13,1,'Room004',1),(569,'H',14,1,'Room004',1),(570,'H',15,1,'Room004',1),(571,'I',1,1,'Room004',1),(572,'I',2,1,'Room004',1),(573,'I',3,1,'Room004',1),(574,'I',4,1,'Room004',1),(575,'I',5,1,'Room004',1),(576,'I',6,1,'Room004',1),(577,'I',7,1,'Room004',1),(578,'I',8,1,'Room004',1),(579,'I',9,1,'Room004',1),(580,'I',10,1,'Room004',1),(581,'I',11,1,'Room004',1),(582,'I',12,1,'Room004',1),(583,'I',13,1,'Room004',1),(584,'I',14,1,'Room004',1),(585,'I',15,1,'Room004',1),(586,'J',1,1,'Room004',1),(587,'J',2,1,'Room004',1),(588,'J',3,1,'Room004',1),(589,'J',4,1,'Room004',1),(590,'J',5,1,'Room004',1),(591,'J',6,1,'Room004',1),(592,'J',7,1,'Room004',1),(593,'J',8,1,'Room004',1),(594,'J',9,1,'Room004',1),(595,'J',10,1,'Room004',1),(596,'J',11,1,'Room004',1),(597,'J',12,1,'Room004',1),(598,'J',13,1,'Room004',1),(599,'J',14,1,'Room004',1),(600,'J',15,1,'Room004',1),(601,'A',1,1,'Room005',1),(602,'A',2,1,'Room005',1),(603,'A',3,1,'Room005',1),(604,'A',4,1,'Room005',1),(605,'A',5,1,'Room005',1),(606,'A',6,1,'Room005',1),(607,'A',7,1,'Room005',1),(608,'A',8,1,'Room005',1),(609,'A',9,1,'Room005',1),(610,'A',10,1,'Room005',1),(611,'A',11,1,'Room005',1),(612,'A',12,1,'Room005',1),(613,'A',13,1,'Room005',1),(614,'A',14,1,'Room005',1),(615,'A',15,1,'Room005',1),(616,'B',1,1,'Room005',1),(617,'B',2,1,'Room005',1),(618,'B',3,1,'Room005',1),(619,'B',4,1,'Room005',1),(620,'B',5,1,'Room005',1),(621,'B',6,1,'Room005',1),(622,'B',7,1,'Room005',1),(623,'B',8,1,'Room005',1),(624,'B',9,1,'Room005',1),(625,'B',10,1,'Room005',1),(626,'B',11,1,'Room005',1),(627,'B',12,1,'Room005',1),(628,'B',13,1,'Room005',1),(629,'B',14,1,'Room005',1),(630,'B',15,1,'Room005',1),(631,'C',1,1,'Room005',1),(632,'C',2,1,'Room005',1),(633,'C',3,1,'Room005',1),(634,'C',4,1,'Room005',1),(635,'C',5,1,'Room005',1),(636,'C',6,1,'Room005',1),(637,'C',7,1,'Room005',1),(638,'C',8,1,'Room005',1),(639,'C',9,1,'Room005',1),(640,'C',10,1,'Room005',1),(641,'C',11,1,'Room005',1),(642,'C',12,1,'Room005',1),(643,'C',13,1,'Room005',1),(644,'C',14,1,'Room005',1),(645,'C',15,1,'Room005',1),(646,'D',1,1,'Room005',1),(647,'D',2,1,'Room005',1),(648,'D',3,1,'Room005',1),(649,'D',4,1,'Room005',1),(650,'D',5,1,'Room005',1),(651,'D',6,1,'Room005',1),(652,'D',7,1,'Room005',1),(653,'D',8,1,'Room005',1),(654,'D',9,1,'Room005',1),(655,'D',10,1,'Room005',1),(656,'D',11,1,'Room005',1),(657,'D',12,1,'Room005',1),(658,'D',13,1,'Room005',1),(659,'D',14,1,'Room005',1),(660,'D',15,1,'Room005',1),(661,'E',1,1,'Room005',1),(662,'E',2,1,'Room005',1),(663,'E',3,1,'Room005',1),(664,'E',4,1,'Room005',1),(665,'E',5,1,'Room005',1),(666,'E',6,1,'Room005',2),(667,'E',7,1,'Room005',2),(668,'E',8,1,'Room005',2),(669,'E',9,1,'Room005',2),(670,'E',10,1,'Room005',2),(671,'E',11,1,'Room005',1),(672,'E',12,1,'Room005',1),(673,'E',13,1,'Room005',1),(674,'E',14,1,'Room005',1),(675,'E',15,1,'Room005',1),(676,'F',1,1,'Room005',1),(677,'F',2,1,'Room005',1),(678,'F',3,1,'Room005',1),(679,'F',4,1,'Room005',1),(680,'F',5,1,'Room005',1),(681,'F',6,1,'Room005',2),(682,'F',7,1,'Room005',2),(683,'F',8,1,'Room005',2),(684,'F',9,1,'Room005',2),(685,'F',10,1,'Room005',2),(686,'F',11,1,'Room005',1),(687,'F',12,1,'Room005',1),(688,'F',13,1,'Room005',1),(689,'F',14,1,'Room005',1),(690,'F',15,1,'Room005',1),(691,'G',1,1,'Room005',1),(692,'G',2,1,'Room005',1),(693,'G',3,1,'Room005',1),(694,'G',4,1,'Room005',1),(695,'G',5,1,'Room005',1),(696,'G',6,1,'Room005',2),(697,'G',7,1,'Room005',2),(698,'G',8,1,'Room005',2),(699,'G',9,1,'Room005',2),(700,'G',10,1,'Room005',2),(701,'G',11,1,'Room005',1),(702,'G',12,1,'Room005',1),(703,'G',13,1,'Room005',1),(704,'G',14,1,'Room005',1),(705,'G',15,1,'Room005',1),(706,'H',1,1,'Room005',1),(707,'H',2,1,'Room005',1),(708,'H',3,1,'Room005',1),(709,'H',4,1,'Room005',1),(710,'H',5,1,'Room005',1),(711,'H',6,1,'Room005',2),(712,'H',7,1,'Room005',2),(713,'H',8,1,'Room005',2),(714,'H',9,1,'Room005',2),(715,'H',10,1,'Room005',2),(716,'H',11,1,'Room005',1),(717,'H',12,1,'Room005',1),(718,'H',13,1,'Room005',1),(719,'H',14,1,'Room005',1),(720,'H',15,1,'Room005',1),(721,'I',1,1,'Room005',1),(722,'I',2,1,'Room005',1),(723,'I',3,1,'Room005',1),(724,'I',4,1,'Room005',1),(725,'I',5,1,'Room005',1),(726,'I',6,1,'Room005',1),(727,'I',7,1,'Room005',1),(728,'I',8,1,'Room005',1),(729,'I',9,1,'Room005',1),(730,'I',10,1,'Room005',1),(731,'I',11,1,'Room005',1),(732,'I',12,1,'Room005',1),(733,'I',13,1,'Room005',1),(734,'I',14,1,'Room005',1),(735,'I',15,1,'Room005',1),(736,'J',1,1,'Room005',1),(737,'J',2,1,'Room005',1),(738,'J',3,1,'Room005',1),(739,'J',4,1,'Room005',1),(740,'J',5,1,'Room005',1),(741,'J',6,1,'Room005',1),(742,'J',7,1,'Room005',1),(743,'J',8,1,'Room005',1),(744,'J',9,1,'Room005',1),(745,'J',10,1,'Room005',1),(746,'J',11,1,'Room005',1),(747,'J',12,1,'Room005',1),(748,'J',13,1,'Room005',1),(749,'J',14,1,'Room005',1),(750,'J',15,1,'Room005',1),(779,'A',1,1,'Room006',1),(780,'A',2,1,'Room006',1),(781,'A',3,1,'Room006',1),(782,'A',4,1,'Room006',1),(783,'A',5,1,'Room006',1),(784,'A',6,1,'Room006',1),(785,'A',7,1,'Room006',1),(786,'A',8,1,'Room006',1),(787,'A',9,1,'Room006',1),(788,'A',10,1,'Room006',1),(789,'A',11,1,'Room006',1),(790,'A',12,1,'Room006',1),(791,'A',13,1,'Room006',1),(792,'A',14,1,'Room006',1),(793,'A',15,1,'Room006',1),(794,'B',1,1,'Room006',1),(795,'B',2,1,'Room006',1),(796,'B',3,1,'Room006',1),(797,'B',4,1,'Room006',1),(798,'B',5,1,'Room006',1),(799,'B',6,1,'Room006',1),(800,'B',7,1,'Room006',1),(801,'B',8,1,'Room006',1),(802,'B',9,1,'Room006',1),(803,'B',10,1,'Room006',1),(804,'B',11,1,'Room006',1),(805,'B',12,1,'Room006',1),(806,'B',13,1,'Room006',1),(807,'B',14,1,'Room006',1),(808,'B',15,1,'Room006',1),(809,'C',1,1,'Room006',1),(810,'C',2,1,'Room006',1),(811,'C',3,1,'Room006',1),(812,'C',4,1,'Room006',1),(813,'C',5,1,'Room006',1),(814,'C',6,1,'Room006',1),(815,'C',7,1,'Room006',1),(816,'C',8,1,'Room006',1),(817,'C',9,1,'Room006',1),(818,'C',10,1,'Room006',1),(819,'C',11,1,'Room006',1),(820,'C',12,1,'Room006',1),(821,'C',13,1,'Room006',1),(822,'C',14,1,'Room006',1),(823,'C',15,1,'Room006',1),(824,'D',1,1,'Room006',1),(825,'D',2,1,'Room006',1),(826,'D',3,1,'Room006',1),(827,'D',4,1,'Room006',1),(828,'D',5,1,'Room006',1),(829,'D',6,1,'Room006',1),(830,'D',7,1,'Room006',1),(831,'D',8,1,'Room006',1),(832,'D',9,1,'Room006',1),(833,'D',10,1,'Room006',1),(834,'D',11,1,'Room006',1),(835,'D',12,1,'Room006',1),(836,'D',13,1,'Room006',1),(837,'D',14,1,'Room006',1),(838,'D',15,1,'Room006',1),(839,'E',1,1,'Room006',1),(840,'E',2,1,'Room006',1),(841,'E',3,1,'Room006',1),(842,'E',4,1,'Room006',1),(843,'E',5,1,'Room006',1),(844,'E',6,1,'Room006',1),(845,'E',7,1,'Room006',1),(846,'E',8,1,'Room006',1),(847,'E',9,1,'Room006',1),(848,'E',10,1,'Room006',1),(849,'E',11,1,'Room006',1),(850,'E',12,1,'Room006',1),(851,'E',13,1,'Room006',1),(852,'E',14,1,'Room006',1),(853,'E',15,1,'Room006',1),(854,'F',1,1,'Room006',1),(855,'F',2,1,'Room006',1),(856,'F',3,1,'Room006',1),(857,'F',4,1,'Room006',1),(858,'F',5,1,'Room006',1),(859,'F',6,1,'Room006',1),(860,'F',7,1,'Room006',1),(861,'F',8,1,'Room006',1),(862,'F',9,1,'Room006',1),(863,'F',10,1,'Room006',1),(864,'F',11,1,'Room006',1),(865,'F',12,1,'Room006',1),(866,'F',13,1,'Room006',1),(867,'F',14,1,'Room006',1),(868,'F',15,1,'Room006',1),(869,'G',1,1,'Room006',1),(870,'G',2,1,'Room006',1),(871,'G',3,1,'Room006',1),(872,'G',4,1,'Room006',1),(873,'G',5,1,'Room006',1),(874,'G',6,1,'Room006',1),(875,'G',7,1,'Room006',1),(876,'G',8,1,'Room006',1),(877,'G',9,1,'Room006',1),(878,'G',10,1,'Room006',1),(879,'G',11,1,'Room006',1),(880,'G',12,1,'Room006',1),(881,'G',13,1,'Room006',1),(882,'G',14,1,'Room006',1),(883,'G',15,1,'Room006',1),(884,'H',1,1,'Room006',1),(885,'H',2,1,'Room006',1),(886,'H',3,1,'Room006',1),(887,'H',4,1,'Room006',1),(888,'H',5,1,'Room006',1),(889,'H',6,1,'Room006',1),(890,'H',7,1,'Room006',1),(891,'H',8,1,'Room006',1),(892,'H',9,1,'Room006',1),(893,'H',10,1,'Room006',1),(894,'H',11,1,'Room006',1),(895,'H',12,1,'Room006',1),(896,'H',13,1,'Room006',1),(897,'H',14,1,'Room006',1),(898,'H',15,1,'Room006',1),(899,'I',1,1,'Room006',1),(900,'I',2,1,'Room006',1),(901,'I',3,1,'Room006',1),(902,'I',4,1,'Room006',1),(903,'I',5,1,'Room006',1),(904,'I',6,1,'Room006',1),(905,'I',7,1,'Room006',1),(906,'I',8,1,'Room006',1),(907,'I',9,1,'Room006',1),(908,'I',10,1,'Room006',1),(909,'I',11,1,'Room006',1),(910,'I',12,1,'Room006',1),(911,'I',13,1,'Room006',1),(912,'I',14,1,'Room006',1),(913,'I',15,1,'Room006',1),(914,'J',1,1,'Room006',1),(915,'J',2,1,'Room006',1),(916,'J',3,1,'Room006',1),(917,'J',4,1,'Room006',1),(918,'J',5,1,'Room006',1),(919,'J',6,1,'Room006',1),(920,'J',7,1,'Room006',1),(921,'J',8,1,'Room006',1),(922,'J',9,1,'Room006',1),(923,'J',10,1,'Room006',1),(924,'J',11,1,'Room006',1),(925,'J',12,1,'Room006',1),(926,'J',13,1,'Room006',1),(927,'J',14,1,'Room006',1),(928,'J',15,1,'Room006',1),(1079,'A',1,1,'Room007',1),(1080,'A',2,1,'Room007',1),(1081,'A',3,1,'Room007',1),(1082,'A',4,1,'Room007',1),(1083,'A',5,1,'Room007',1),(1084,'A',6,1,'Room007',1),(1085,'A',7,1,'Room007',1),(1086,'A',8,1,'Room007',1),(1087,'A',9,1,'Room007',1),(1088,'A',10,1,'Room007',1),(1089,'A',11,1,'Room007',1),(1090,'A',12,1,'Room007',1),(1091,'A',13,1,'Room007',1),(1092,'A',14,1,'Room007',1),(1093,'A',15,1,'Room007',1),(1094,'B',1,1,'Room007',1),(1095,'B',2,1,'Room007',1),(1096,'B',3,1,'Room007',1),(1097,'B',4,1,'Room007',1),(1098,'B',5,1,'Room007',1),(1099,'B',6,1,'Room007',1),(1100,'B',7,1,'Room007',1),(1101,'B',8,1,'Room007',1),(1102,'B',9,1,'Room007',1),(1103,'B',10,1,'Room007',1),(1104,'B',11,1,'Room007',1),(1105,'B',12,1,'Room007',1),(1106,'B',13,1,'Room007',1),(1107,'B',14,1,'Room007',1),(1108,'B',15,1,'Room007',1),(1109,'C',1,1,'Room007',1),(1110,'C',2,1,'Room007',1),(1111,'C',3,1,'Room007',1),(1112,'C',4,1,'Room007',1),(1113,'C',5,1,'Room007',1),(1114,'C',6,1,'Room007',1),(1115,'C',7,1,'Room007',1),(1116,'C',8,1,'Room007',1),(1117,'C',9,1,'Room007',1),(1118,'C',10,1,'Room007',1),(1119,'C',11,1,'Room007',1),(1120,'C',12,1,'Room007',1),(1121,'C',13,1,'Room007',1),(1122,'C',14,1,'Room007',1),(1123,'C',15,1,'Room007',1),(1124,'D',1,1,'Room007',1),(1125,'D',2,1,'Room007',1),(1126,'D',3,1,'Room007',1),(1127,'D',4,1,'Room007',1),(1128,'D',5,1,'Room007',1),(1129,'D',6,1,'Room007',1),(1130,'D',7,1,'Room007',1),(1131,'D',8,1,'Room007',1),(1132,'D',9,1,'Room007',1),(1133,'D',10,1,'Room007',1),(1134,'D',11,1,'Room007',1),(1135,'D',12,1,'Room007',1),(1136,'D',13,1,'Room007',1),(1137,'D',14,1,'Room007',1),(1138,'D',15,1,'Room007',1),(1139,'E',1,1,'Room007',1),(1140,'E',2,1,'Room007',1),(1141,'E',3,1,'Room007',1),(1142,'E',4,1,'Room007',1),(1143,'E',5,1,'Room007',1),(1144,'E',6,1,'Room007',1),(1145,'E',7,1,'Room007',1),(1146,'E',8,1,'Room007',1),(1147,'E',9,1,'Room007',1),(1148,'E',10,1,'Room007',1),(1149,'E',11,1,'Room007',1),(1150,'E',12,1,'Room007',1),(1151,'E',13,1,'Room007',1),(1152,'E',14,1,'Room007',1),(1153,'E',15,1,'Room007',1),(1154,'F',1,1,'Room007',1),(1155,'F',2,1,'Room007',1),(1156,'F',3,1,'Room007',1),(1157,'F',4,1,'Room007',1),(1158,'F',5,1,'Room007',1),(1159,'F',6,1,'Room007',1),(1160,'F',7,1,'Room007',1),(1161,'F',8,1,'Room007',1),(1162,'F',9,1,'Room007',1),(1163,'F',10,1,'Room007',1),(1164,'F',11,1,'Room007',1),(1165,'F',12,1,'Room007',1),(1166,'F',13,1,'Room007',1),(1167,'F',14,1,'Room007',1),(1168,'F',15,1,'Room007',1),(1169,'G',1,1,'Room007',1),(1170,'G',2,1,'Room007',1),(1171,'G',3,1,'Room007',1),(1172,'G',4,1,'Room007',1),(1173,'G',5,1,'Room007',1),(1174,'G',6,1,'Room007',1),(1175,'G',7,1,'Room007',1),(1176,'G',8,1,'Room007',1),(1177,'G',9,1,'Room007',1),(1178,'G',10,1,'Room007',1),(1179,'G',11,1,'Room007',1),(1180,'G',12,1,'Room007',1),(1181,'G',13,1,'Room007',1),(1182,'G',14,1,'Room007',1),(1183,'G',15,1,'Room007',1),(1184,'H',1,1,'Room007',1),(1185,'H',2,1,'Room007',1),(1186,'H',3,1,'Room007',1),(1187,'H',4,1,'Room007',1),(1188,'H',5,1,'Room007',1),(1189,'H',6,1,'Room007',1),(1190,'H',7,1,'Room007',1),(1191,'H',8,1,'Room007',1),(1192,'H',9,1,'Room007',1),(1193,'H',10,1,'Room007',1),(1194,'H',11,1,'Room007',1),(1195,'H',12,1,'Room007',1),(1196,'H',13,1,'Room007',1),(1197,'H',14,1,'Room007',1),(1198,'H',15,1,'Room007',1),(1199,'I',1,1,'Room007',1),(1200,'I',2,1,'Room007',1),(1201,'I',3,1,'Room007',1),(1202,'I',4,1,'Room007',1),(1203,'I',5,1,'Room007',1),(1204,'I',6,1,'Room007',1),(1205,'I',7,1,'Room007',1),(1206,'I',8,1,'Room007',1),(1207,'I',9,1,'Room007',1),(1208,'I',10,1,'Room007',1),(1209,'I',11,1,'Room007',1),(1210,'I',12,1,'Room007',1),(1211,'I',13,1,'Room007',1),(1212,'I',14,1,'Room007',1),(1213,'I',15,1,'Room007',1),(1214,'J',1,1,'Room007',1),(1215,'J',2,1,'Room007',1),(1216,'J',3,1,'Room007',1),(1217,'J',4,1,'Room007',1),(1218,'J',5,1,'Room007',1),(1219,'J',6,1,'Room007',1),(1220,'J',7,1,'Room007',1),(1221,'J',8,1,'Room007',1),(1222,'J',9,1,'Room007',1),(1223,'J',10,1,'Room007',1),(1224,'J',11,1,'Room007',1),(1225,'J',12,1,'Room007',1),(1226,'J',13,1,'Room007',1),(1227,'J',14,1,'Room007',1),(1228,'J',15,1,'Room007',1);
/*!40000 ALTER TABLE `seats_rooms` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seats_type`
--

DROP TABLE IF EXISTS `seats_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `seats_type` (
  `seat_type_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `price` double DEFAULT NULL,
  PRIMARY KEY (`seat_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seats_type`
--

LOCK TABLES `seats_type` WRITE;
/*!40000 ALTER TABLE `seats_type` DISABLE KEYS */;
INSERT INTO `seats_type` VALUES (1,'Normal',50000),(2,'VIP',70000);
/*!40000 ALTER TABLE `seats_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `showtimes`
--

DROP TABLE IF EXISTS `showtimes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `showtimes` (
  `showtime_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `running_time` int DEFAULT NULL,
  `start_date` date DEFAULT NULL,
  `start_time` time(6) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `format_id` int NOT NULL,
  `movie_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `room_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`showtime_id`),
  KEY `FKfsc0r2patshbupdfeipyyel67` (`format_id`),
  KEY `FKeltpyuei1d5g3n6ikpsjwwil6` (`movie_id`),
  KEY `FKrumrrbei9jppryk4teoyoetit` (`room_id`),
  CONSTRAINT `FKeltpyuei1d5g3n6ikpsjwwil6` FOREIGN KEY (`movie_id`) REFERENCES `movies` (`movie_id`),
  CONSTRAINT `FKfsc0r2patshbupdfeipyyel67` FOREIGN KEY (`format_id`) REFERENCES `formats` (`format_id`),
  CONSTRAINT `FKrumrrbei9jppryk4teoyoetit` FOREIGN KEY (`room_id`) REFERENCES `rooms` (`room_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `showtimes`
--

LOCK TABLES `showtimes` WRITE;
/*!40000 ALTER TABLE `showtimes` DISABLE KEYS */;
INSERT INTO `showtimes` VALUES ('00149756-dcee-4202-9553-6da76a19fce0',149,'2023-12-05','13:30:00.000000','',1,'mv-0003','Room002'),('03f8cc4e-b4fa-475f-a9c6-67e4240fd450',123,'2023-12-05','17:30:00.000000','',1,'mv-0004','Room005'),('054a0105-e1a3-46c3-9e03-bc3983fce9a9',131,'2023-11-28','16:30:00.000000','\0',1,'mv-0002','Room001'),('0622bfbc-e5ab-4493-b967-4254621ff2df',123,'2023-12-05','15:15:00.000000','',1,'mv-0004','Room005'),('07706194-c525-451d-9efb-ec200a88df2d',131,'2023-11-28','14:45:00.000000','\0',1,'mv-0002','Room006'),('0846bb01-ee85-4c90-86c3-482945280a6e',133,'2023-12-12','10:00:00.000000','',1,'mv-0006','Room001'),('0ad362d5-86d5-4a10-b469-9d9fbd21b7ad',150,'2023-12-05','12:45:00.000000','',2,'mv-0007','Room003'),('0e385cdb-aa71-4463-be2b-29edc1263b9b',131,'2023-12-04','17:45:00.000000','\0',1,'mv-0002','Room005'),('1',120,'2023-10-28','13:30:00.000000','\0',2,'mv-0001','Room002'),('123',120,'2023-10-29','13:00:00.000000','\0',2,'mv-0001','Room001'),('1445a9b1-7477-4ae9-ab79-a768e0b7894f',133,'2023-12-05','09:00:00.000000','\0',1,'mv-0006','Room002'),('14e902b0-a9ee-4537-b379-66bf6c56d5c9',131,'2023-12-04','13:00:00.000000','\0',1,'mv-0002','Room002'),('19d1b88d-3f7f-4b37-bf22-5ce346424aaf',131,'2023-12-04','08:30:00.000000','\0',1,'mv-0002','Room002'),('2',120,'2023-10-28','13:00:00.000000','\0',2,'mv-0001','Room004'),('269c2d83-18d1-418c-8662-df0d997cbba4',149,'2023-12-08','22:00:00.000000','',1,'mv-0003','Room006'),('28caabee-abb5-42d7-9772-b6d52be89543',133,'2023-12-01','08:00:00.000000','\0',1,'mv-0006','Room001'),('2b2c2684-0726-425a-b675-8c7638dfa679',133,'2023-11-29','09:00:00.000000','\0',1,'mv-0006','Room003'),('3',120,'2023-10-28','16:00:00.000000','\0',2,'mv-0001','Room004'),('342f0a2d-8f8e-46f6-846a-440cfe9c4825',131,'2023-12-04','17:30:00.000000','\0',1,'mv-0002','Room002'),('367cb014-ccc3-4329-94e0-641b683b1ffa',149,'2023-12-07','09:00:00.000000','',2,'mv-0003','Room003'),('3a11aa4b-b324-47a2-9dea-5a1a9defcae7',148,'2023-11-30','09:00:00.000000','\0',4,'mv-0005','Room002'),('4',120,'2023-10-28','16:00:00.000000','\0',1,'mv-0002','Room001'),('423c70bb-72f8-4549-8cb1-0a3dfe3c0caa',131,'2023-12-05','00:30:00.000000','\0',1,'mv-0002','Room005'),('47b7ce72-13da-4050-b560-5fbd21ae9073',149,'2023-12-09','10:15:00.000000','',1,'mv-0003','Room003'),('4a25f30e-394b-42ce-b5f1-9b48a765e34c',131,'2023-12-04','10:45:00.000000','\0',1,'mv-0002','Room002'),('5e89e4f9-5148-4de8-bb22-416b3dcff264',148,'2023-12-01','17:45:00.000000','\0',1,'mv-0005','Room001'),('6160107f-386c-4c7e-8b05-96ace678481c',133,'2023-12-05','22:15:00.000000','',1,'mv-0006','Room005'),('62340799-4965-446f-be95-51bc11422348',131,'2023-12-05','10:30:00.000000','\0',1,'mv-0002','Room005'),('62ea7db9-f048-4148-8ee9-e44ab01c8300',148,'2023-12-06','09:00:00.000000','',1,'mv-0005','Room001'),('63a34907-fda5-44ef-83ad-cc80c7f40356',131,'2023-12-05','16:30:00.000000','',1,'mv-0002','Room006'),('640be26d-e2ee-412f-a7f9-5d360e55b303',131,'2023-12-05','08:15:00.000000','\0',1,'mv-0002','Room005'),('68c438c1-13c8-4fd7-94ee-58634b4747ba',133,'2023-11-29','16:30:00.000000','\0',1,'mv-0006','Room001'),('6fcf601d-ff79-4a92-98f0-3b90c242010f',149,'2023-12-07','09:00:00.000000','',1,'mv-0003','Room001'),('74faae1a-175a-4663-855e-0ec1738f57c2',150,'2023-12-05','12:45:00.000000','',2,'mv-0007','Room005'),('75bc4720-e22f-4fa7-bbf2-e8f04665a851',123,'2023-12-09','18:15:00.000000','',1,'mv-0004','Room006'),('77c52cd4-194d-4aed-940d-a96dab7a34ff',123,'2023-12-05','02:00:00.000000','\0',1,'mv-0004','Room006'),('7890ab10-aee5-4775-aef4-66b1e6ccaf46',131,'2023-11-28','14:00:00.000000','\0',1,'mv-0002','Room005'),('7f54ac19-b166-4b30-b894-4a8d08a2f192',133,'2023-12-06','11:30:00.000000','',1,'mv-0006','Room003'),('85ab5ae5-9de8-497a-9cbf-17de58b84fe2',149,'2023-11-30','14:00:00.000000','\0',1,'mv-0003','Room003'),('86851ee1-dca6-4d3a-ba52-061626460872',148,'2023-12-01','12:45:00.000000','\0',1,'mv-0005','Room001'),('86e7d5ba-89e1-4e0b-9ef7-600cf506f805',131,'2023-12-11','07:45:00.000000','',1,'mv-0002','Room001'),('88fad81a-28ff-4ae9-8565-80415bb6ee9a',131,'2023-12-05','15:45:00.000000','',1,'mv-0002','Room001'),('8beb89fa-3f45-4458-a21e-cea642e931e9',131,'2023-12-06','07:00:00.000000','',1,'mv-0002','Room004'),('8c88179c-8495-4641-bbd9-7425db125da9',148,'2023-11-30','16:30:00.000000','\0',1,'mv-0005','Room003'),('8e9874a2-e071-4fcc-9f6f-851a8ae4adc0',148,'2023-11-29','14:00:00.000000','\0',2,'mv-0005','Room002'),('8f6285b5-20d6-40ae-83ff-985931aea2e2',131,'2023-12-08','16:30:00.000000','',1,'mv-0002','Room006'),('920c4c8c-2411-4eee-a294-72656c28eda7',131,'2023-12-05','19:45:00.000000','',1,'mv-0002','Room002'),('92a27614-50fd-4657-a2cc-708c8f1591a5',131,'2023-12-11','10:00:00.000000','',1,'mv-0002','Room001'),('96065179-2b58-4520-be83-2111134f1fac',131,'2023-12-11','12:15:00.000000','',1,'mv-0002','Room001'),('96b5093a-3ce7-4dac-9a9d-6bce443b64fd',131,'2023-12-04','08:15:00.000000','\0',1,'mv-0002','Room005'),('972b3180-d66b-4185-b41f-52cd81bb5517',148,'2023-12-01','15:15:00.000000','\0',1,'mv-0005','Room001'),('9a2d9107-8710-49f9-ba22-38276fe54497',150,'2023-12-04','13:00:00.000000','\0',2,'mv-0007','Room001'),('9a6b57e4-4cc9-459b-840c-436f4576d920',133,'2023-12-05','09:00:00.000000','\0',1,'mv-0006','Room001'),('9a9d10bf-fb02-4ca5-a453-058e88954311',149,'2023-12-08','09:15:00.000000','',1,'mv-0003','Room003'),('9d1dea97-d9b9-4dda-bcd8-29e526806493',149,'2023-12-04','12:45:00.000000','\0',2,'mv-0003','Room005'),('9e8e0faa-0226-49ac-9422-d28a9a39eb61',131,'2023-12-04','19:45:00.000000','\0',1,'mv-0002','Room002'),('9ecac6a8-4b72-4951-8e5a-a748c883e34f',133,'2023-12-02','15:15:00.000000','\0',1,'mv-0006','Room001'),('a2c6bf8d-2167-49c3-9bf7-dbc1918a5754',133,'2023-11-28','18:45:00.000000','\0',1,'mv-0006','Room001'),('b1468b44-e512-4275-81c8-f2b2398c64db',131,'2023-11-28','11:45:00.000000','\0',1,'mv-0002','Room004'),('b2cd0602-6d68-4cb1-856d-5eb548f02a9b',133,'2023-12-05','15:15:00.000000','',1,'mv-0006','Room003'),('b4dd0a43-ccdd-4e70-9262-8e673b4ff0e9',149,'2023-12-04','15:15:00.000000','\0',2,'mv-0003','Room005'),('b7872071-2d9b-4a69-8d35-adcdbe0c45de',133,'2023-12-06','23:15:00.000000','',1,'mv-0006','Room004'),('c0aa21ff-78ee-479b-8f15-a75e2729d76f',131,'2023-12-04','15:15:00.000000','\0',1,'mv-0002','Room002'),('c406a864-b8d8-4c48-b8ba-c7a8919c34fe',133,'2023-12-05','13:30:00.000000','',1,'mv-0006','Room001'),('c447fc6e-b5e8-4e3d-a826-9ab400a0b801',149,'2023-12-04','15:30:00.000000','\0',2,'mv-0003','Room001'),('c6a6b102-f18d-4d1b-b17e-341170b13264',148,'2023-12-01','10:15:00.000000','\0',1,'mv-0005','Room001'),('daf5a711-53ff-47d9-ad27-6ff229006c6b',150,'2023-12-13','12:30:00.000000','',2,'mv-0007','Room001'),('db621837-700e-4a1f-98b6-b855b4b2df72',131,'2023-11-29','12:00:00.000000','\0',1,'mv-0002','Room004'),('dcc79ae2-5260-457a-b577-3204a4cf3e3b',148,'2023-12-05','19:45:00.000000','',4,'mv-0005','Room005'),('de584427-847a-46f7-8263-bc4fef9c994a',133,'2023-12-04','10:30:00.000000','\0',1,'mv-0006','Room005'),('e02b4957-8860-4048-a0ba-a03627313062',148,'2023-12-06','18:30:00.000000','',4,'mv-0005','Room006'),('e0d3cbfb-481e-4dd1-9b2b-b3bb3653add6',133,'2023-12-05','11:30:00.000000','\0',1,'mv-0006','Room006'),('ea674ec8-6094-4e35-8255-e557872bae3e',133,'2023-12-05','17:30:00.000000','',1,'mv-0006','Room002'),('ec786d13-dd84-4485-a773-942da7ac0764',131,'2023-12-04','22:15:00.000000','\0',1,'mv-0002','Room005'),('f0bf82c0-326e-4488-a504-cefab3be0512',123,'2023-12-04','20:00:00.000000','\0',1,'mv-0004','Room005'),('f2cab68e-d247-4807-8675-c50309ba9062',131,'2023-12-05','11:15:00.000000','\0',1,'mv-0002','Room002'),('f32c67d6-abf4-43d9-8cfd-0ae40495c5ba',131,'2023-12-05','07:00:00.000000','\0',1,'mv-0002','Room004'),('f59c2920-9f82-49ec-8673-f8e5d7c2ee0f',131,'2023-12-05','09:00:00.000000','\0',1,'mv-0002','Room003'),('f74ddbb4-c34d-4684-97ea-99f7af0f4ef2',131,'2023-12-04','10:45:00.000000','\0',1,'mv-0002','Room001'),('fcb9eeb5-01d7-4521-aa2e-00f4de2534c7',133,'2023-12-06','09:00:00.000000','',1,'mv-0006','Room003'),('fd77ae0a-5365-47bd-9d19-9e457c675816',149,'2023-12-06','08:00:00.000000','',1,'mv-0003','Room006'),('fe9e5256-eeb1-4af3-9625-6226dce91bef',123,'2023-12-05','11:15:00.000000','\0',1,'mv-0004','Room001'),('Showtime006',120,'2023-11-02','09:00:00.000000','\0',1,'mv-0001','Room001'),('Showtime007',120,'2023-11-02','12:00:00.000000','\0',2,'mv-0001','Room001'),('Showtime008',120,'2023-11-02','15:00:00.000000','\0',1,'mv-0001','Room001'),('Showtime009',120,'2023-11-02','18:00:00.000000','\0',1,'mv-0001','Room001'),('Showtime010',120,'2023-11-02','09:30:00.000000','\0',1,'mv-0001','Room002'),('Showtime011',120,'2023-11-03','21:30:00.000000','\0',2,'mv-0001','Room004'),('Showtime012',120,'2023-11-02','23:30:00.000000','\0',1,'mv-0001','Room002'),('Showtime013',120,'2023-11-02','22:00:00.000000','\0',1,'mv-0001','Room002'),('Showtime014',120,'2023-11-01','09:00:00.000000','\0',1,'mv-0004','Room001'),('Showtime015',120,'2023-11-01','12:00:00.000000','\0',2,'mv-0004','Room001'),('Showtime016',120,'2023-11-01','15:00:00.000000','\0',1,'mv-0004','Room001'),('Showtime017',120,'2023-11-01','18:00:00.000000','\0',1,'mv-0004','Room001'),('Showtime018',120,'2023-11-01','09:30:00.000000','\0',1,'mv-0004','Room002'),('Showtime019',120,'2023-11-01','12:30:00.000000','\0',2,'mv-0004','Room002'),('Showtime020',120,'2023-11-04','09:30:00.000000','\0',1,'mv-0004','Room004'),('Showtime021',120,'2023-11-01','10:30:00.000000','\0',2,'mv-0004','Room005'),('Showtime022',120,'2023-11-07','16:30:00.000000','\0',1,'mv-0001','Room004'),('Showtime023',120,'2023-11-02','23:00:00.000000','\0',2,'mv-0001','Room005'),('Showtime024',120,'2023-11-02','09:30:00.000000','\0',1,'mv-0004','Room004'),('Showtime025',120,'2023-11-02','10:30:00.000000','\0',2,'mv-0004','Room005'),('Showtime026',120,'2023-11-01','13:30:00.000000','\0',1,'mv-0001','Room004'),('Showtime027',120,'2023-11-01','17:00:00.000000','\0',2,'mv-0001','Room005'),('Showtime028',136,'2023-11-15','09:00:00.000000','\0',1,'mv-0001','Room001'),('Showtime029',136,'2023-11-15','11:00:00.000000','\0',1,'mv-0001','Room002'),('Showtime030',136,'2023-11-16','11:30:00.000000','\0',1,'mv-0001','Room001'),('Showtime031',136,'2023-11-15','13:30:00.000000','\0',1,'mv-0001','Room002'),('Showtime032',136,'2023-11-25','14:00:00.000000','\0',1,'mv-0001','Room001'),('Showtime033',136,'2023-11-25','16:30:00.000000','\0',1,'mv-0001','Room001'),('Showtime034',123,'2023-11-25','19:00:00.000000','\0',1,'mv-0004','Room001'),('Showtime035',136,'2023-11-25','21:15:00.000000','\0',1,'mv-0001','Room001');
/*!40000 ALTER TABLE `showtimes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tickets`
--

DROP TABLE IF EXISTS `tickets`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tickets` (
  `ticket_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `bill_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `showtime_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `seat_room` int NOT NULL,
  PRIMARY KEY (`ticket_id`),
  KEY `FKaqpjta99er6ahhyyq5689cyw4` (`bill_id`),
  KEY `FKo0u22315eoxdv59tn6wsdn8b1` (`showtime_id`),
  KEY `FK4qm1s4orf1onvmo4y5qsbl5mv` (`seat_room`),
  CONSTRAINT `FK4qm1s4orf1onvmo4y5qsbl5mv` FOREIGN KEY (`seat_room`) REFERENCES `seats_rooms` (`seat_room_id`),
  CONSTRAINT `FKaqpjta99er6ahhyyq5689cyw4` FOREIGN KEY (`bill_id`) REFERENCES `bills` (`bill_id`),
  CONSTRAINT `FKo0u22315eoxdv59tn6wsdn8b1` FOREIGN KEY (`showtime_id`) REFERENCES `showtimes` (`showtime_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tickets`
--

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;
INSERT INTO `tickets` VALUES ('0d1a0762-126e-361f-87a6-0d9fe0937084','b76524a5-02bd-4c51-9832-86e933863f57','f0bf82c0-326e-4488-a504-cefab3be0512',698),('0eaa2675-01da-3e8b-81cd-da80180f7b3c','442b8ee7-7f6d-48ae-8666-17a41b9ac882','3a11aa4b-b324-47a2-9dea-5a1a9defcae7',170),('0feedaf0-4073-388d-b39e-6dbfe945058c','516cdf92-447d-46d2-b2f7-13c67985233f','Showtime028',4),('10333195-0f8e-3f40-9975-d2c089b21a51','442b8ee7-7f6d-48ae-8666-17a41b9ac882','3a11aa4b-b324-47a2-9dea-5a1a9defcae7',171),('1bd3a95b-48dc-310f-a121-0017f5743c52','78598fef-8152-4cbd-9bf8-21620ce42a45','Showtime013',167),('1bf4708e-e8a5-372d-8a88-b9ec7b8774f3','c86a74f8-d7f2-4afb-9675-bc306c64e365','8e9874a2-e071-4fcc-9f6f-851a8ae4adc0',221),('22efebe3-5c5e-3c03-93ea-a0ec5096342c','d464eb75-b912-498e-8099-05a9f48ce78b','Showtime034',148),('24a7f631-126f-3e15-aa40-f0f6e00808ff','c86a74f8-d7f2-4afb-9675-bc306c64e365','8e9874a2-e071-4fcc-9f6f-851a8ae4adc0',222),('2e5889ab-9cd2-320c-b9d6-c866cbf5ad26','e772c5cc-982d-45a2-a3d8-0e0a7e735fdf','Showtime034',76),('33e93705-b239-32c2-a347-0f1040479da9','e772c5cc-982d-45a2-a3d8-0e0a7e735fdf','Showtime034',55),('37cab3ea-d287-31ae-82a3-174ef8d59d46','577cc36a-800e-42fb-956f-d191b5cd6d94','123',81),('3eea96cb-030b-3a99-b413-c6f956e64704','79e2ac2b-d0c1-4ae0-878f-5e558d782d3b','4',111),('59dda3f9-eb7e-3abd-acb5-95ad9693da95','c86a74f8-d7f2-4afb-9675-bc306c64e365','8e9874a2-e071-4fcc-9f6f-851a8ae4adc0',223),('5a2e7641-2a6d-317e-8034-377e861f3a62','e772c5cc-982d-45a2-a3d8-0e0a7e735fdf','Showtime034',93),('5a7017e7-5f11-3f81-9956-621f09619d36','516cdf92-447d-46d2-b2f7-13c67985233f','Showtime028',2),('5dfc9455-4c58-3a48-8083-daf52da2c314','516cdf92-447d-46d2-b2f7-13c67985233f','Showtime028',3),('5e66b73b-6259-37f8-8df8-8973d0fe0587','577cc36a-800e-42fb-956f-d191b5cd6d94','123',82),('6b2a3097-1ed2-3de8-8a29-406df18a83bc','0d13aba8-cca1-48ae-bde5-dcb250644705','Showtime034',1),('76a7e4a2-6ef6-3b31-b5bd-88fcbd927858','b76524a5-02bd-4c51-9832-86e933863f57','f0bf82c0-326e-4488-a504-cefab3be0512',696),('93c1fe57-1146-36d8-87e8-192d98d7a16d','b76524a5-02bd-4c51-9832-86e933863f57','f0bf82c0-326e-4488-a504-cefab3be0512',697),('9d40cc80-22d6-3b5d-b0c0-39fed8683da8','0d13aba8-cca1-48ae-bde5-dcb250644705','Showtime034',3),('aa479837-989c-3799-9896-74d118d75585','d464eb75-b912-498e-8099-05a9f48ce78b','Showtime034',149),('afa4aab5-a802-34e7-933c-9851f309a0b4','79e2ac2b-d0c1-4ae0-878f-5e558d782d3b','4',113),('b315444b-81aa-3b77-a058-736976d88730','d464eb75-b912-498e-8099-05a9f48ce78b','Showtime034',145),('b50db180-eb1b-39c0-970b-ea4de85c8879','79e2ac2b-d0c1-4ae0-878f-5e558d782d3b','4',112),('dcad3df8-7892-3ebe-983c-8c35d8736825','d464eb75-b912-498e-8099-05a9f48ce78b','Showtime034',147),('ddcaeb02-c95a-34f6-8413-334694dd4ced','516cdf92-447d-46d2-b2f7-13c67985233f','Showtime028',1),('debbef4c-2415-3048-ab50-7f9c6a2293a4','6ad7e232-c242-49a8-b658-d15dcbf2f353','3a11aa4b-b324-47a2-9dea-5a1a9defcae7',155),('e7cc6833-7f1f-3912-ab96-95fc6a4d278f','c86a74f8-d7f2-4afb-9675-bc306c64e365','8e9874a2-e071-4fcc-9f6f-851a8ae4adc0',225),('e93933fc-87cd-3dd3-b876-2a1ca83fcb56','6ad7e232-c242-49a8-b658-d15dcbf2f353','3a11aa4b-b324-47a2-9dea-5a1a9defcae7',156),('ed0d52cc-1690-3652-b32c-37c593d0cb22','577cc36a-800e-42fb-956f-d191b5cd6d94','123',80),('f37b806c-8d8c-3a88-bd0e-f372a187c284','c86a74f8-d7f2-4afb-9675-bc306c64e365','8e9874a2-e071-4fcc-9f6f-851a8ae4adc0',224),('f9bed56b-44ff-31cc-a0b7-b879f5c517cd','0d13aba8-cca1-48ae-bde5-dcb250644705','Showtime034',2),('fb0f1568-b281-33f8-b49e-41b437b085ab','d464eb75-b912-498e-8099-05a9f48ce78b','Showtime034',146),('fecb7155-3630-3ab1-a148-1edcf8067328','e772c5cc-982d-45a2-a3d8-0e0a7e735fdf','8e9874a2-e071-4fcc-9f6f-851a8ae4adc0',200);
/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `point` int DEFAULT NULL,
  `role_id` int NOT NULL,
  `verify` bit(1) DEFAULT NULL,
  `create_date` datetime(6) DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `verify_account` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `verify_mail` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `verify_pass` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gender` enum('FEMALE','MALE','UNKNOWN') COLLATE utf8mb4_general_ci DEFAULT NULL,
  `phone_number` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `FKp56c1712k691lhsyewcssf40f` (`role_id`),
  CONSTRAINT `FKp56c1712k691lhsyewcssf40f` FOREIGN KEY (`role_id`) REFERENCES `roles` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('15f2378d-d8a6-3cb3-a012-3470dd6758aa','hn2666@gmail.com','$2a$10$DZdtvsopz3hGGXivlY2om.ZvUjcRFlEypT7hjkUQRrlg5BP.zdVh2',NULL,0,3,'\0','2023-12-01 00:37:15.081016','2sds@',NULL,NULL,NULL,'UNKNOWN',NULL,'https://cdn.discordapp.com/attachments/1168144426141499412/1169264779769155594/c6e56503cfdd87da299f72dc416023d4.jpg?ex=6554c5af&is=654250af&hm=440ebc745e441103a1824a80edb22f2d1c371743e8d7a4d413dbbc62eae31554&'),('49c64e33-d273-38ef-a68c-23e1aa16e499','nngocsang38@gmail.com','$2a$10$NhWikTHudqxY3b6AMOMwfu9f3xIQ0KUF6.lLp55ttiZQKf29cTMvu','2003-08-21',0,1,'','2023-10-16 14:20:52.000000','Nguyễn Ngọc Sang',NULL,NULL,NULL,'FEMALE','0916333333','https://cdn.discordapp.com/attachments/1168144426141499412/1179940650066653285/fried-chicken.png?ex=657b9c5d&is=6569275d&hm=0ae4a9a679ee8950ff308033178661b42f948305c6831656315da547afc97af7&'),('57806cf6-39fa-3245-8a3d-066714f57ab2','couplenamjin520@gmail.com','$2a$10$hGdqik43NIbPIYoN4A739ek9gxplXpn5FVu0/H1TXWGuXsdK77XLa','2003-11-15',0,3,'\0','2023-11-11 13:56:19.000000','Nguyễn Yến Vy',NULL,NULL,NULL,'FEMALE','0902991511','https://cdn.discordapp.com/attachments/1168144426141499412/1169264779769155594/c6e56503cfdd87da299f72dc416023d4.jpg?ex=6554c5af&is=654250af&hm=440ebc745e441103a1824a80edb22f2d1c371743e8d7a4d413dbbc62eae31554&'),('58de8aef-2b93-3db6-b210-173e93e78674','vominhhoang044@gmai','$2a$10$W5DMPchcVC01NaacmlYkQOK0QhqBFA.RHGo8u9voZ.6sgByRNh3Zq',NULL,0,3,'\0','2023-12-01 00:37:49.636937','asdasd',NULL,NULL,NULL,'UNKNOWN',NULL,'https://cdn.discordapp.com/attachments/1168144426141499412/1169264779769155594/c6e56503cfdd87da299f72dc416023d4.jpg?ex=6554c5af&is=654250af&hm=440ebc745e441103a1824a80edb22f2d1c371743e8d7a4d413dbbc62eae31554&'),('76d3c946-d2d7-3224-9952-c6fddb4526a2','vmhoafng@gmail.com','$2a$10$2tieaYzw8PVCcMArA5PDau4OEbG98Na9al2sKfXR.FTXbDH4F0uV6','2023-11-28',0,3,'\0','2023-12-01 01:49:08.239620','vmhoafng',NULL,NULL,NULL,'FEMALE','1231231231','https://cdn.discordapp.com/attachments/1159668660340789259/1179901739789463642/IMG_1657.jpeg?ex=657b7820&is=65690320&hm=d76ee3665b774fd4017eea3f149b0fd4ecebcdf5828116a153b5329b23d6983c&'),('76ea668f-6c6b-33bd-8a99-25f7eac2becf','admin@gmail.com','$2a$10$ZawinI3tONP.7PtPU13OnemH6fF69.KQtiXYLKIG09.GlFn290haq',NULL,10,1,'','2023-11-23 14:19:45.829821','admin',NULL,NULL,NULL,'UNKNOWN',NULL,'https://cdn.discordapp.com/attachments/1168144426141499412/1169264779769155594/c6e56503cfdd87da299f72dc416023d4.jpg?ex=6554c5af&is=654250af&hm=440ebc745e441103a1824a80edb22f2d1c371743e8d7a4d413dbbc62eae31554&'),('9028e620-8853-3b12-9d65-82da9585bc8a','datnguyenthanh1605@gmail.com','$2a$10$wHKTkttU3CI2If9FZVlNl.aXJiXF0RsatzM1uakVx2hxqJ/24CnIO',NULL,0,2,'','2023-11-22 12:28:43.561620','Thành Đạt Nguyễn',NULL,NULL,NULL,'UNKNOWN',NULL,'https://lh3.googleusercontent.com/a/ACg8ocLQ2DimcTxOgv7Z2vu39ZnwfeqMlkKqyBZHgnEUsCYHAxE=s96-c'),('9400d33d-87e1-389b-8472-113ae6869c79','webcamffff@gmail.com','$2a$10$m3WL0p4iYtYytBs43XiNHe7gWs5KHI9clHjkjeBleyAdAFwDvyDQS',NULL,0,3,'\0','2023-12-02 00:01:36.089398','Con Cặc Chó Sơn',NULL,NULL,NULL,'UNKNOWN',NULL,'https://cdn.discordapp.com/attachments/1168144426141499412/1169264779769155594/c6e56503cfdd87da299f72dc416023d4.jpg?ex=6554c5af&is=654250af&hm=440ebc745e441103a1824a80edb22f2d1c371743e8d7a4d413dbbc62eae31554&'),('97913044-f584-3cdf-aacc-f6a41ee91bb3','vominhhoang044@gmail.com','$2a$10$OCJAblCL2MEnNFdKOfNfnulfQbEJ2E3TXqRF7IxyifmmdzWvrXxeq','2003-07-31',0,2,'','2023-11-23 13:51:06.169499','vmhoafng',NULL,NULL,NULL,'MALE','0929829783','https://lh3.googleusercontent.com/a/ACg8ocIlPJbedl_pdcvikbIpQZjPy5Yx75TqSZ1V1ElCgl4UkvGq=s96-c'),('d733ff2a-e4c6-338b-8a27-c8d80791b5ea','datnt.653@gmail.com','$2a$10$rc3cuQ5Q1/OCFxZh/DTHKeBiyjreM63gZinxkP1ew2TkV3dy2gc3C',NULL,0,3,'\0','2023-12-01 00:50:36.195262','nguyen thanh dat',NULL,NULL,NULL,'UNKNOWN',NULL,'https://cdn.discordapp.com/attachments/1168144426141499412/1169264779769155594/c6e56503cfdd87da299f72dc416023d4.jpg?ex=6554c5af&is=654250af&hm=440ebc745e441103a1824a80edb22f2d1c371743e8d7a4d413dbbc62eae31554&'),('e33ffe0f-5175-3ab0-9971-67196c3fa9dc','hn26677@gmail.com','$2a$10$OqkCzu0.kSY1qCMPR7r5xOVt86QWOT1dJgqEobZiYM.yKd9Gl.35i','2003-11-10',0,3,'\0','2023-11-09 15:06:54.000000','Hoang ne',NULL,NULL,NULL,'UNKNOWN','09169211111','https://cdn.discordapp.com/attachments/1159668660340789259/1177446234722680893/firefox2.jpg?ex=65728942&is=65601442&hm=85442a15f481672359b9af9fe635da65cde8932cf3dfd3a9238c68f97262acbe&'),('fc51f2bf-983f-3156-8043-c339b41c4431','hn266222277@gmail.com','$2a$10$dLwayYSjxDHGwDP6BKush.HaMbgwP8PtnCYOYOx9nZMA3n6BfnMdO',NULL,0,3,'\0','2023-12-01 00:51:37.739730','Son So',NULL,NULL,NULL,'UNKNOWN',NULL,'https://cdn.discordapp.com/attachments/1168144426141499412/1169264779769155594/c6e56503cfdd87da299f72dc416023d4.jpg?ex=6554c5af&is=654250af&hm=440ebc745e441103a1824a80edb22f2d1c371743e8d7a4d413dbbc62eae31554&');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-05 12:18:29
