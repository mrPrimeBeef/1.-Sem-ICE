-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: ice
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `bruger`
--

DROP TABLE IF EXISTS `bruger`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bruger` (
  `brugernavn` varchar(100) NOT NULL,
  `kodeord` varchar(100) NOT NULL,
  PRIMARY KEY (`brugernavn`),
  UNIQUE KEY `brugernavn_UNIQUE` (`brugernavn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bruger`
--

LOCK TABLES `bruger` WRITE;
/*!40000 ALTER TABLE `bruger` DISABLE KEYS */;
/*!40000 ALTER TABLE `bruger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `koeleskab`
--

DROP TABLE IF EXISTS `koeleskab`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `koeleskab` (
  `brugernavn` varchar(100) NOT NULL,
  `Ingredienser` mediumtext,
  `Fryseliste` mediumtext,
  PRIMARY KEY (`brugernavn`),
  KEY `brugernavnk_idx` (`brugernavn`),
  CONSTRAINT `brugernavnk` FOREIGN KEY (`brugernavn`) REFERENCES `bruger` (`brugernavn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `koeleskab`
--

LOCK TABLES `koeleskab` WRITE;
/*!40000 ALTER TABLE `koeleskab` DISABLE KEYS */;
/*!40000 ALTER TABLE `koeleskab` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `liste`
--

DROP TABLE IF EXISTS `liste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `liste` (
  `brugernavn` varchar(100) NOT NULL,
  `Varer` mediumtext,
  `Tema` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`brugernavn`),
  CONSTRAINT `brugernavnl` FOREIGN KEY (`brugernavn`) REFERENCES `bruger` (`brugernavn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liste`
--

LOCK TABLES `liste` WRITE;
/*!40000 ALTER TABLE `liste` DISABLE KEYS */;
/*!40000 ALTER TABLE `liste` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `retter`
--

DROP TABLE IF EXISTS `retter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `retter` (
  `Navn` varchar(225) NOT NULL,
  `Ingredienser` mediumtext,
  `Instruktioner` mediumtext,
  `Kategori` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`Navn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `retter`
--

LOCK TABLES `retter` WRITE;
/*!40000 ALTER TABLE `retter` DISABLE KEYS */;
/*!40000 ALTER TABLE `retter` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-08 13:05:53
