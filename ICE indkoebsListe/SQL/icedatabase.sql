-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: icedatabase
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
INSERT INTO `bruger` VALUES ('Admin','Admin'),('Rolf','Rolf');
/*!40000 ALTER TABLE `bruger` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fryserliste`
--

DROP TABLE IF EXISTS `fryserliste`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fryserliste` (
  `brugernavn` varchar(45) NOT NULL,
  `varer` varchar(45) DEFAULT NULL,
  `mængde` int DEFAULT NULL,
  `afdeling` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fryserliste`
--

LOCK TABLES `fryserliste` WRITE;
/*!40000 ALTER TABLE `fryserliste` DISABLE KEYS */;
/*!40000 ALTER TABLE `fryserliste` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ingredienser`
--

DROP TABLE IF EXISTS `ingredienser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ingredienser` (
  `ingrediens_id` int NOT NULL AUTO_INCREMENT,
  `navn` varchar(100) DEFAULT NULL,
  `mængde` int DEFAULT NULL,
  `ret_navn` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ingrediens_id`),
  KEY `ingredienser_ibfk_1` (`ret_navn`),
  CONSTRAINT `ingredienser_ibfk_1` FOREIGN KEY (`ret_navn`) REFERENCES `retter` (`navn`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ingredienser`
--

LOCK TABLES `ingredienser` WRITE;
/*!40000 ALTER TABLE `ingredienser` DISABLE KEYS */;
INSERT INTO `ingredienser` VALUES (30,'Kyllingebryst',1,'Caesar Salad'),(31,'Salat',1,'Caesar Salad'),(32,'Ost',1,'Caesar Salad'),(33,'Brød',1,'Caesar Salad'),(34,'Hvidløg',1,'Caesar Salad'),(35,'Bagels',1,'Breakfast Bagel'),(36,'Æg',1,'Breakfast Bagel'),(37,'Skinke',1,'Breakfast Bagel'),(38,'Ost',1,'Breakfast Bagel'),(39,'Smør',1,'Breakfast Bagel'),(40,'Lasagne',1,'Vegetarisk Lasagne'),(41,'Tomater',1,'Vegetarisk Lasagne'),(42,'Løg',1,'Vegetarisk Lasagne'),(43,'Gulerødder',1,'Vegetarisk Lasagne'),(44,'Spinat',1,'Vegetarisk Lasagne'),(45,'Ost',1,'Vegetarisk Lasagne'),(46,'Kyllingebryst',1,'Kylling og Ris'),(47,'Ris',1,'Kylling og Ris'),(48,'Broccoli',1,'Kylling og Ris'),(49,'Peberfrugt',1,'Kylling og Ris'),(53,'hvidløg',1,'stegte ris'),(54,'ris',1,'stegte ris'),(55,'løg',1,'stegte ris'),(56,'Kyllingebryst',1,'stegte ris'),(57,'Olivenolie',1,'stegte ris');
/*!40000 ALTER TABLE `ingredienser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventar`
--

DROP TABLE IF EXISTS `inventar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `inventar` (
  `brugernavn` varchar(45) NOT NULL,
  `varer` varchar(45) DEFAULT NULL,
  `mængde` int DEFAULT NULL,
  `afdeling` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventar`
--

LOCK TABLES `inventar` WRITE;
/*!40000 ALTER TABLE `inventar` DISABLE KEYS */;
INSERT INTO `inventar` VALUES ('Admin','chips',1,'slik'),('Admin','mælk',1,'mejeri'),('Admin','Hakket oksekød',1,'køl'),('Admin','ost',1,'mejeri'),('Admin','cola',3,'sodavand'),('Admin','tyggegummi',1,'koisk');
/*!40000 ALTER TABLE `inventar` ENABLE KEYS */;
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
  `mængde` int DEFAULT NULL,
  `pris` int DEFAULT NULL,
  `afdeling` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `liste`
--

LOCK TABLES `liste` WRITE;
/*!40000 ALTER TABLE `liste` DISABLE KEYS */;
INSERT INTO `liste` VALUES ('Admin','Ris',1,20,'tørvarer'),('Admin','Kyllingebryst',1,50,'køl'),('Admin','Peberfrugt',1,20,'grønt'),('Admin','Broccoli',1,20,'grønt');
/*!40000 ALTER TABLE `liste` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `madplan`
--

DROP TABLE IF EXISTS `madplan`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `madplan` (
  `brugernavn` varchar(45) NOT NULL,
  `dag` int DEFAULT NULL,
  `ret` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `madplan`
--

LOCK TABLES `madplan` WRITE;
/*!40000 ALTER TABLE `madplan` DISABLE KEYS */;
INSERT INTO `madplan` VALUES ('Admin',1,'kylling og ris');
/*!40000 ALTER TABLE `madplan` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `retter`
--

DROP TABLE IF EXISTS `retter`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `retter` (
  `brugernavn` varchar(45) DEFAULT NULL,
  `navn` varchar(100) NOT NULL,
  `global` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`navn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `retter`
--

LOCK TABLES `retter` WRITE;
/*!40000 ALTER TABLE `retter` DISABLE KEYS */;
INSERT INTO `retter` VALUES ('global','Breakfast Bagel',1),('global','Caesar Salad',1),('global','Kylling og Ris',1),('global','Spaghetti Bolognese',1),('Admin','stegte ris',0),('global','Vegetarisk Lasagne',1);
/*!40000 ALTER TABLE `retter` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `varer`
--

DROP TABLE IF EXISTS `varer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `varer` (
  `navn` varchar(255) NOT NULL,
  `mængde` int NOT NULL,
  `pris` int NOT NULL,
  `afdeling` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `varer`
--

LOCK TABLES `varer` WRITE;
/*!40000 ALTER TABLE `varer` DISABLE KEYS */;
INSERT INTO `varer` VALUES ('Mælk',1,10,'køl'),('Brød',1,20,'bager'),('Æg',1,25,'køl'),('Smør',1,18,'køl'),('Kartofler',1,15,'grønt'),('Gulerødder',1,10,'grønt'),('Pasta',1,12,'tørvarer'),('Ris',1,20,'tørvarer'),('Tomater',1,15,'grønt'),('Kyllingebryst',1,50,'køl'),('Ost',1,30,'køl'),('Salat',1,15,'grønt'),('Agurk',1,12,'grønt'),('Paprika',1,20,'grønt'),('Rugbrød',1,22,'bager'),('Mel',1,12,'tørvarer'),('Sukker',1,10,'tørvarer'),('Kaffe',1,40,'tørvarer'),('Te',1,30,'tørvarer'),('Honning',1,35,'tørvarer'),('Appelsiner',1,20,'grønt'),('Bananer',1,18,'grønt'),('Æbler',1,22,'grønt'),('Pærer',1,25,'grønt'),('Kiks',1,15,'tørvarer'),('Chokolade',1,20,'tørvarer'),('Marmelade',1,25,'tørvarer'),('Yoghurt',1,15,'køl'),('Fløde',1,20,'køl'),('Hvedebrød',1,18,'bager'),('Skinke',1,28,'køl'),('Pølser',1,30,'køl'),('Olivenolie',1,50,'tørvarer'),('Små tomater',1,20,'grønt'),('Løg',1,12,'grønt'),('Hvidløg',1,15,'grønt'),('Gær',1,10,'køl'),('Bagels',1,20,'bager'),('Croissanter',1,25,'bager'),('Jordbær',1,30,'grønt'),('Blåbær',1,25,'grønt'),('Hindbær',1,28,'grønt'),('Vindruer',1,35,'grønt'),('Nødder',1,40,'tørvarer'),('Rosiner',1,15,'tørvarer'),('Mandler',1,50,'tørvarer'),('Peberfrugt',1,20,'grønt'),('Spinat',1,15,'grønt'),('Broccoli',1,20,'grønt'),('Blomkål',1,25,'grønt'),('Squash',1,18,'grønt'),('Aubergine',1,20,'grønt'),('Majs',1,12,'grønt'),('Frosne ærter',1,20,'frost'),('Frosne bønner',1,20,'frost'),('Is',1,50,'frost'),('Pizza',1,45,'frost'),('Lasagne',1,60,'frost'),('Fiskefilet',1,50,'køl'),('Rejer',1,40,'køl'),('Laks',1,70,'køl'),('Torsk',1,60,'køl'),('Tun',1,30,'tørvarer'),('Makrel',1,28,'tørvarer'),('Sardin',1,25,'tørvarer'),('Kyllingelår',1,45,'køl'),('Andebryst',1,80,'køl'),('Koteletter',1,50,'køl'),('Bøffer',1,75,'køl'),('Lam',1,90,'køl'),('Ribbensteg',1,100,'køl'),('Medisterpølse',1,55,'køl'),('Frikadeller',1,40,'køl'),('Leverpostej',1,20,'køl'),('Tarteletter',1,15,'tørvarer'),('Hakket tomat',1,10,'konserves'),('Spaghetti',1,12,'tørvarer');
/*!40000 ALTER TABLE `varer` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-16  9:26:04
