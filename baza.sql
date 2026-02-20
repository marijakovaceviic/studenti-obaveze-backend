-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: diplomski
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `admini`
--

DROP TABLE IF EXISTS `admini`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admini` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(145) NOT NULL,
  `lozinka` varchar(145) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admini`
--

LOCK TABLES `admini` WRITE;
/*!40000 ALTER TABLE `admini` DISABLE KEYS */;
INSERT INTO `admini` VALUES (1,'km210300d@student.etf.bg.ac.rs','$2a$10$2lnmNRcXX7ikinrRT9UTcOy9g/BWOgR5egIqKSxGZfwAN8oKs9v2.');
/*!40000 ALTER TABLE `admini` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `demonstratori_forme`
--

DROP TABLE IF EXISTS `demonstratori_forme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demonstratori_forme` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idNastavnik` int NOT NULL,
  `pocetak` datetime NOT NULL,
  `kraj` datetime NOT NULL,
  `konkurs` varchar(415) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_nastavnik_idx` (`idNastavnik`),
  CONSTRAINT `fk_nastavnik` FOREIGN KEY (`idNastavnik`) REFERENCES `nastavnici` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demonstratori_forme`
--

LOCK TABLES `demonstratori_forme` WRITE;
/*!40000 ALTER TABLE `demonstratori_forme` DISABLE KEYS */;
INSERT INTO `demonstratori_forme` VALUES (2,1,'2026-01-07 10:00:00','2026-01-16 20:00:00','demonstratori\\1765313172540_konkurs.pdf'),(9,1,'2026-02-19 07:45:00','2026-02-27 07:45:00','demonstratori\\1771548272391_Postavka.pdf');
/*!40000 ALTER TABLE `demonstratori_forme` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `demonstratori_prijave`
--

DROP TABLE IF EXISTS `demonstratori_prijave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `demonstratori_prijave` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idStudent` int NOT NULL,
  `idForma` int NOT NULL,
  `idPredmet` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_student_idx` (`idStudent`),
  KEY `fk_forma_idx` (`idForma`),
  KEY `fk_predmet_idx` (`idPredmet`),
  CONSTRAINT `fk_forma` FOREIGN KEY (`idForma`) REFERENCES `demonstratori_forme` (`id`),
  CONSTRAINT `fk_predmet` FOREIGN KEY (`idPredmet`) REFERENCES `predmeti` (`id`),
  CONSTRAINT `fk_student` FOREIGN KEY (`idStudent`) REFERENCES `studenti` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `demonstratori_prijave`
--

LOCK TABLES `demonstratori_prijave` WRITE;
/*!40000 ALTER TABLE `demonstratori_prijave` DISABLE KEYS */;
INSERT INTO `demonstratori_prijave` VALUES (1,1,2,19),(2,1,2,77),(3,1,2,81),(4,1,2,74),(5,30,2,64),(6,30,2,77),(7,30,2,19),(8,30,2,48),(9,30,2,11),(10,30,2,13),(11,30,2,12),(12,30,2,14),(13,22,2,19),(14,22,2,77),(15,22,2,81),(16,22,2,74),(17,22,2,48),(18,28,2,48),(19,28,2,64),(20,28,2,19),(21,28,2,77),(22,32,2,65),(23,32,2,19),(24,32,2,64),(25,32,2,48),(26,26,2,64),(27,26,2,48),(28,26,2,11),(29,26,2,12),(30,26,2,65),(31,25,2,64),(32,1,2,71),(33,1,2,65);
/*!40000 ALTER TABLE `demonstratori_prijave` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `laboratorije`
--

DROP TABLE IF EXISTS `laboratorije`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `laboratorije` (
  `id` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(45) NOT NULL,
  `kapacitet` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `laboratorije`
--

LOCK TABLES `laboratorije` WRITE;
/*!40000 ALTER TABLE `laboratorije` DISABLE KEYS */;
INSERT INTO `laboratorije` VALUES (1,'p25',40),(2,'p26',80),(3,'70',70),(4,'60',20),(5,'p26b',10),(6,'314',15),(7,'315',12);
/*!40000 ALTER TABLE `laboratorije` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nastavnici`
--

DROP TABLE IF EXISTS `nastavnici`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nastavnici` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(45) NOT NULL,
  `prezime` varchar(45) NOT NULL,
  `email` varchar(100) NOT NULL,
  `lozinka` varchar(100) NOT NULL,
  `zaduzen_demo` tinyint NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nastavnici`
--

LOCK TABLES `nastavnici` WRITE;
/*!40000 ALTER TABLE `nastavnici` DISABLE KEYS */;
INSERT INTO `nastavnici` VALUES (1,'Milan','Milivojević','km210300d@student.etf.bg.ac.rs','$2a$10$8NSYo1NWitVILVPLCHAVneV1vouCmEpCZ0RKbn7FciF9.2FdqzaSu',1),(2,'Jovan','Jović','mk210300d@student.etf.bg.ac.rs','$2a$10$doDm2AFE.vYDdmJNzl4NNua4Oj0EPD/uTXkPSX7tsH9TyVNKB5nkm',0),(3,'Milica','Milićević','kmm210300d@student.etf.bg.ac.rs','$2a$10$2lnmNRcXX7ikinrRT9UTcOy9g/BWOgR5egIqKSxGZfwAN8oKs9v2.',0);
/*!40000 ALTER TABLE `nastavnici` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nastavnik_predmet`
--

DROP TABLE IF EXISTS `nastavnik_predmet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nastavnik_predmet` (
  `idPredmet` int NOT NULL,
  `idNastavnik` int NOT NULL,
  PRIMARY KEY (`idPredmet`,`idNastavnik`),
  KEY `fk_nastavnik_idx` (`idNastavnik`),
  CONSTRAINT `nnastavnik_fk` FOREIGN KEY (`idNastavnik`) REFERENCES `nastavnici` (`id`),
  CONSTRAINT `ppredmet_fk` FOREIGN KEY (`idPredmet`) REFERENCES `predmeti` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nastavnik_predmet`
--

LOCK TABLES `nastavnik_predmet` WRITE;
/*!40000 ALTER TABLE `nastavnik_predmet` DISABLE KEYS */;
INSERT INTO `nastavnik_predmet` VALUES (1,1),(2,1),(46,1),(70,1),(77,1),(2,2),(7,2),(8,2),(9,2),(11,2),(30,2),(44,2),(56,3),(69,3),(70,3),(71,3);
/*!40000 ALTER TABLE `nastavnik_predmet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `obaveze`
--

DROP TABLE IF EXISTS `obaveze`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `obaveze` (
  `id` int NOT NULL AUTO_INCREMENT,
  `tip` varchar(45) NOT NULL,
  `predmet` int NOT NULL,
  `naziv` varchar(150) NOT NULL,
  `opis` varchar(500) DEFAULT NULL,
  `pocetak` datetime NOT NULL,
  `kraj` datetime NOT NULL,
  `poslat_email_nastavniku` tinyint(1) DEFAULT '0',
  `poslat_email_studentima` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `obaveze`
--

LOCK TABLES `obaveze` WRITE;
/*!40000 ALTER TABLE `obaveze` DISABLE KEYS */;
INSERT INTO `obaveze` VALUES (1,'kolokvijum',12,'Prijava za izradu prvog kolokvijuma',NULL,'2025-11-20 08:00:00','2025-12-01 20:00:00',1,1),(2,'ispit',46,'Prijava za ispit u januarskom roku',NULL,'2025-11-22 12:17:00','2025-11-30 12:40:00',1,1),(3,'ispit',70,'Prijava za ispit u januarskom roku',NULL,'2025-12-23 12:34:00','2025-12-30 12:40:00',1,1),(4,'lab',77,'Prijava za drugu laboratorijsku vežbu',NULL,'2025-11-19 13:41:00','2025-11-30 13:41:00',1,1),(5,'domaci',70,'Predaja projektnog zadatka u januarskom roku','Odbrana projektnog zadatka će biti organizovana pre termina ispita. Projektni zadatak mora biti predat kao .zip fajl maksimalne velicine 5MB','2025-12-20 13:00:00','2025-12-27 13:00:00',0,1),(6,'domaci',66,'Predaja projektnog zadatka ','Projekat mora biti predat kao .zip arhiva','2025-11-26 11:00:00','2025-12-17 18:00:00',0,1),(7,'domaci',71,'Predaja projekta',NULL,'2025-11-24 20:00:00','2025-12-11 10:00:00',0,1),(8,'odbrana',12,'Prijava za odbranu drugog domaćeg zadatka',NULL,'2025-12-13 16:00:00','2025-12-20 20:00:00',1,1),(9,'ispit',77,'Ispit u januarskom roku',NULL,'2025-12-20 14:00:00','2025-12-25 19:00:00',1,1),(10,'domaci',1,'Predaja domaćeg zadatka',NULL,'2025-12-21 12:00:00','2025-12-25 11:00:00',0,1),(11,'kolokvijum',12,'Prijava za izradu drugog kolokvijuma',NULL,'2025-12-20 20:00:00','2025-12-26 20:00:00',1,1),(12,'odbrana',56,'Prijava za odbranu domaćeg zadatka',NULL,'2025-12-19 10:00:00','2025-12-25 20:00:00',1,1),(13,'kolokvijum',69,'Prijava za prvi kolokvijum',NULL,'2025-12-22 14:30:00','2025-12-27 12:00:00',1,1),(14,'lab',70,'Prva lab vežba',NULL,'2025-11-25 19:00:00','2025-11-28 19:00:00',1,1),(15,'kolokvijum',70,'Prijava za kolokvijum',NULL,'2025-11-30 10:00:00','2025-12-06 10:00:00',1,1),(16,'kolokvijum',71,'Prvi kolokvijum',NULL,'2025-11-08 12:00:00','2025-11-14 20:00:00',1,1),(17,'kolokvijum',71,'Drugi kolokvijum',NULL,'2025-12-12 13:00:00','2025-12-18 12:00:00',1,1),(18,'ispit',12,'Prijava za ispit u januarskom roku',NULL,'2026-01-04 08:00:00','2026-01-09 20:00:00',1,1),(19,'domaci',70,'Predaja projekta u februarskom roku',NULL,'2026-01-09 11:45:00','2026-01-11 20:00:00',0,1),(20,'kolokvijum',1,'Prijava za kolokvijum',NULL,'2025-12-02 15:00:00','2025-12-09 18:00:00',1,1),(21,'kolokvijum',69,'Druga predispitna obaveza',NULL,'2026-01-20 12:00:00','2026-01-27 18:00:00',1,1),(22,'kolokvijum',7,'Prijava za prvi kolokvijum',NULL,'2026-01-09 12:00:00','2026-01-13 12:00:00',1,1),(23,'kolokvijum',11,'Prijava za kolokvijum',NULL,'2026-01-11 12:00:00','2026-01-24 17:00:00',1,1),(24,'domaci',2,'Predaja domaćeg zadatka',NULL,'2026-01-17 13:00:00','2026-02-20 13:00:00',0,1),(25,'odbrana',2,'Prijava za odbranu domaćeg zadatka','Kako biste mogli da pristupite odbrani domaćeg zadatka, morate predati rad i prijaviti se putem ove forme.','2026-01-19 14:00:00','2026-01-26 15:00:00',1,1),(26,'kolokvijum',30,'Prvi kolokvijum',NULL,'2026-01-12 20:00:00','2026-01-25 20:00:00',1,1),(27,'kolokvijum',44,'Prvi kolokvijum',NULL,'2026-01-16 20:00:00','2026-01-28 20:00:00',1,1),(28,'domaci',70,'Predaja projekta',NULL,'2026-01-17 11:00:00','2026-01-20 12:00:00',0,1),(29,'ispit',77,'Februarski rok',NULL,'2026-02-25 10:00:00','2026-02-28 10:00:00',0,0);
/*!40000 ALTER TABLE `obaveze` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `predaje`
--

DROP TABLE IF EXISTS `predaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `predaje` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idStudent` int NOT NULL,
  `idObaveze` int NOT NULL,
  `putanja` varchar(150) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_idstudent_idx` (`idStudent`),
  KEY `fk_idobaveze_idx` (`idObaveze`),
  CONSTRAINT `fk_idobaveze` FOREIGN KEY (`idObaveze`) REFERENCES `obaveze` (`id`),
  CONSTRAINT `fk_idstudent_fk` FOREIGN KEY (`idStudent`) REFERENCES `studenti` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `predaje`
--

LOCK TABLES `predaje` WRITE;
/*!40000 ALTER TABLE `predaje` DISABLE KEYS */;
INSERT INTO `predaje` VALUES (1,1,5,'studentskiRadovi\\obaveza5\\km210300.zip'),(2,1,6,'studentskiRadovi\\obaveza6\\km210300.zip'),(3,25,5,'studentskiRadovi\\obaveza5\\lm220176.zip'),(4,31,5,'studentskiRadovi\\obaveza5\\sv210129.zip'),(5,28,5,'studentskiRadovi\\obaveza5\\vv220163.zip'),(6,1,19,'studentskiRadovi\\obaveza19\\km210300.zip'),(7,25,19,'studentskiRadovi\\obaveza19\\lm220176.zip'),(8,28,19,'studentskiRadovi\\obaveza19\\vv220163.zip'),(9,18,24,'studentskiRadovi\\obaveza24\\ns250022.zip'),(10,22,19,'studentskiRadovi\\obaveza19\\pj220078.zip'),(11,32,19,'studentskiRadovi\\obaveza19\\dp220700.zip'),(12,26,19,'studentskiRadovi\\obaveza19\\kb210242.zip'),(13,24,19,'studentskiRadovi\\obaveza19\\mm22077d.zip'),(14,23,19,'studentskiRadovi\\obaveza19\\jn220023.zip'),(15,4,19,'studentskiRadovi\\obaveza19\\jj220667.zip'),(16,34,19,'studentskiRadovi\\obaveza19\\nd220132.zip'),(17,12,24,'studentskiRadovi\\obaveza24\\bt220017.zip'),(19,1,24,'studentskiRadovi\\obaveza24\\km210300.zip'),(20,17,24,'studentskiRadovi\\obaveza24\\mj240221.zip');
/*!40000 ALTER TABLE `predaje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `predmeti`
--

DROP TABLE IF EXISTS `predmeti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `predmeti` (
  `id` int NOT NULL AUTO_INCREMENT,
  `naziv` varchar(100) NOT NULL,
  `sifra` varchar(45) NOT NULL,
  `odsek` varchar(3) NOT NULL,
  `godina` int NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=83 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `predmeti`
--

LOCK TABLES `predmeti` WRITE;
/*!40000 ALTER TABLE `predmeti` DISABLE KEYS */;
INSERT INTO `predmeti` VALUES (1,'Algoritmi i strukture podataka','13E112ASP','rti',2),(2,'Algoritmi i strukture podataka 1','13S111ASP1','si',1),(3,'Algoritmi i strukture podataka 2','13S112ASP2','si',2),(4,'Osnovi računarske tehnike 1','13S111ORT1','si',1),(5,'Osnovi računarske tehnike 1','19E111ORT','rti',1),(6,'Praktikum iz osnova računarske tehnike','13S111PORT','si',1),(7,'Praktikum iz programiranja 1','13E111PP1','rti',1),(8,'Praktikum iz programiranja 1','13S111PP1','si',1),(9,'Praktikum iz programiranja 2','13E111PP2','rti',1),(10,'Praktikum iz programiranja 2','13S111PP2','si',1),(11,'Programiranje 1','19E111P1','rti',1),(12,'Programiranje 1','13S111P1','si',1),(13,'Programiranje 2','19E111P2','rti',1),(14,'Programiranje 2','13S111P2','si',1),(15,'Uvod u računarstvo','19E111URA','rti',1),(16,'Uvod u računarstvo','24S111URA','si',1),(17,'Arhitektura računara','13E112AP','rti',2),(18,'Arhitektura računara','13S112AR','si',2),(19,'Baze podataka 1','13E113BP1','rti',3),(20,'Baze podataka 1','13S112BP1','si',2),(21,'Veb dizajn','13S112VD','si',2),(22,'Praktikum iz poslovne komunikacije i prezentacije','13S111PPK','si',1),(23,'Objektno orijentisano programiranje 1','13S112OO1','si',2),(24,'Osnovi računarske tehnike 2','13S112ORT2','si',2),(25,'Operativni sistemi 1','13S112OS1','si',2),(26,'Objektno orijentisano programiranje 2','13S112OO2','si',2),(27,'Računarske mreže 1','13S112RM1','si',2),(28,'Praktikum iz operativnih sistema','13S112POS','si',2),(29,'Praktikum iz objektno orjentisanog programiranja','13S112POOP','si',2),(30,'Informacioni sistemi 1','13S113IS1','si',3),(31,'Projektovanje softvera','13S113PS','si',3),(32,'Računarske mreže 2','13S113RM2','si',3),(33,'Operativni sistemi 2','13S113OS2','si',3),(34,'Arhitektura i organizacija računara 1','13S113AOR1','si',3),(35,'Testiranje softvera','13S113TS','si',3),(36,'Inteligentni sistemi','13S113IS','si',3),(37,'Konkurentno i distribuirano programiranje','13S113KDP','si',3),(38,'Principi softverskog inženjerstva','13S112PSI','si',3),(39,'Infrastruktura za elektronsko poslovanje','13S113IEP','si',3),(40,'Sistemski softver','13S113SS','si',3),(41,'Baze podataka 2','13S113BP2','si',3),(42,'Arhitektura i organizacija računara 2','13S113AOR2','si',3),(43,'Upravljanje softverskim projektima','13S113USP','si',3),(44,'Softverski alati baza podataka','13S113SAB','si',3),(45,'Računarska grafika','13S113RG','si',3),(46,'Programski prevodioci 1','13S114PP1','si',4),(47,'Mikroprocesorski sistemi','13S114MIPS','si',4),(48,'Programiranje internet aplikacija','13S114PIA','si',4),(49,'Informacioni sistemi 2','13S114IS2','si',4),(50,'Multiprocesorski sistemi','13S114MUPS','si',4),(51,'Računarski VLSI sistemi','13S114VLSI','si',4),(52,'Programiranje mobilnih uređaja','13S114PMU','si',4),(53,'Zaštita podataka','13S114ZP','si',4),(54,'Performanse računarskih sistema','13S114PRS','si',4),(55,'Objektno orijentisano programiranje 1','13E112OO1','rti',2),(56,'Osnovi računarske tehnike 2','13E112ORT2','rti',2),(57,'Objektno orijentisano programiranje 2','13E112OO2','rti',2),(58,'Operativni sistemi 1','13E112OS1','rti',2),(59,'Računarske mreže 1','13E112RM1','rti',2),(60,'Praktikum iz operativnih sistema','13E112POS','rti',2),(61,'Arhitektura i organizacija računara 1','13E113AOR1','rti',3),(62,'Konkurentno i distribuirano programiranje','13E113KDP','rti',3),(63,'Operativni sistemi 2','13E113OS2','rti',3),(64,'Programiranje internet aplikacija','13E113PIA','rti',3),(65,'Zaštita podataka','13E113ZP','rti',3),(66,'Sistemski softver','13E113SS','rti',3),(67,'Arhitektutra i organizacija računara 2','13E113AOR2','rti',3),(68,'Upravljanje softverskim projektima','13E113USP','rti',3),(69,'Mikroprocesorski sistemi','13E114MIPS','rti',4),(70,'Programski prevodioci 1','13E114PP1','rti',4),(71,'Računarski VLSI sistemi','13E114VLSI','rti',4),(72,'Računarske mreže 2','13E114RM2','rti',4),(73,'Projektovanje softvera','13E114PS','rti',4),(74,'Inteligentni sistemi','13E114IS','rti',4),(75,'Performanse računarskih sistema','13E114PRS','rti',4),(76,'Baze podataka 2','13E114BP2','rti',4),(77,'Informacioni sistemi 1','13E114IS1','rti',4),(78,'Infrastruktura za elektronsko poslovanje','13E114IEP','rti',4),(79,'Multiprocesorski sistemi','13E114MUPS','rti',4),(80,'Računarska grafika','13E114RG','rti',4),(81,'Softverski alati baza podataka','13E114SAB','rti',4),(82,'Genomska informatika','13M111GI','si',5);
/*!40000 ALTER TABLE `predmeti` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prijave`
--

DROP TABLE IF EXISTS `prijave`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prijave` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idStudent` int NOT NULL,
  `idObaveza` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idStudent_idx` (`idStudent`),
  KEY `idObaveza_idx` (`idObaveza`),
  CONSTRAINT `fk_idObaveza` FOREIGN KEY (`idObaveza`) REFERENCES `obaveze` (`id`),
  CONSTRAINT `fk_idStudent` FOREIGN KEY (`idStudent`) REFERENCES `studenti` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prijave`
--

LOCK TABLES `prijave` WRITE;
/*!40000 ALTER TABLE `prijave` DISABLE KEYS */;
INSERT INTO `prijave` VALUES (1,1,3),(2,2,3),(9,1,4),(12,1,9),(13,1,14),(14,2,14),(15,3,14),(16,4,4),(17,4,9),(18,3,7),(19,1,13),(20,2,13),(21,2,15),(22,1,15),(23,4,3),(24,18,1),(25,18,11),(26,21,1),(27,21,8),(28,18,8),(29,18,18),(30,21,18),(31,6,18),(32,22,14),(33,23,14),(34,24,14),(35,26,14),(36,28,14),(37,30,14),(38,22,15),(39,23,15),(40,24,15),(41,25,15),(42,27,15),(43,28,15),(44,32,15),(45,31,15),(46,34,15),(47,33,15),(48,29,15),(49,3,14),(50,26,3),(51,27,3),(52,28,3),(53,29,3),(54,30,3),(57,1,21),(58,18,23),(59,18,25),(60,18,22),(63,12,27);
/*!40000 ALTER TABLE `prijave` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registrovani`
--

DROP TABLE IF EXISTS `registrovani`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registrovani` (
  `id` int NOT NULL,
  `email` varchar(45) NOT NULL,
  `lozinka` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  CONSTRAINT `student_fk_` FOREIGN KEY (`id`) REFERENCES `studenti` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registrovani`
--

LOCK TABLES `registrovani` WRITE;
/*!40000 ALTER TABLE `registrovani` DISABLE KEYS */;
INSERT INTO `registrovani` VALUES (1,'km210300d@student.etf.bg.ac.rs','$2a$10$ZkPOAcLOhYQngH.6fK12e./TKbD6xHLsI4S30mdbfIiWFv8iHBT46'),(2,'ii210088d@student.etf.bg.ac.rs','$2a$10$ZkPOAcLOhYQngH.6fK12e./TKbD6xHLsI4S30mdbfIiWFv8iHBT46'),(3,'pp200156d@student.etf.bg.ac.rs','$2a$10$ZkPOAcLOhYQngH.6fK12e./TKbD6xHLsI4S30mdbfIiWFv8iHBT46'),(4,'jj220667d@student.etf.bg.ac.rs','$2a$10$ZkPOAcLOhYQngH.6fK12e./TKbD6xHLsI4S30mdbfIiWFv8iHBT46'),(5,'jn230014d@student.etf.bg.ac.rs','$2a$10$ZkPOAcLOhYQngH.6fK12e./TKbD6xHLsI4S30mdbfIiWFv8iHBT46'),(6,'ns240188d@student.etf.bg.ac.rs','$2a$10$ZkPOAcLOhYQngH.6fK12e./TKbD6xHLsI4S30mdbfIiWFv8iHBT46'),(8,'jl210767d@student.etf.bg.ac.rs','$2a$10$ZkPOAcLOhYQngH.6fK12e./TKbD6xHLsI4S30mdbfIiWFv8iHBT46'),(10,'mm190376d@student.etf.bg.ac.rs','$2a$10$ZkPOAcLOhYQngH.6fK12e./TKbD6xHLsI4S30mdbfIiWFv8iHBT46'),(11,'ds230211d@student.etf.bg.ac.rs','$2a$10$AMwOu52Peqmtn7mWPF9mFeQZej6eEtMhh10I931PLTsszHE6c2pSa'),(12,'bt220017d@student.etf.bg.ac.rs','$2a$10$wsBOJYKnR4eaKbcSwhRe4Oe9R89uolHybt50P3u7fNuCZm7sQVdf.'),(15,'mn220557d@student.etf.bg.ac.rs','$2a$10$kinMY/8YQE72uCL7yRyneu.YFUBEsm5OGv4cHehaLCehKJ1bR3vyq'),(17,'mj240221d@student.etf.bg.ac.rs','$2a$10$Yoftx.60CZtK5FUIqzZnS.laZoRwgzqUHm6bRo.3KL1A3WJvlTWh6'),(18,'ns250022d@student.etf.bg.ac.rs','$2a$10$WPvOUo.3jgeKxB7xkr82luvOPQQQMM2IUgjJSGPVHNkllqhJ.lU5u'),(19,'mm250148d@student.etf.bg.ac.rs','$2a$10$YCeU1Nr5xpyQA25PI2DIi.KUuHEuBGtpQOY1AWwRxl4Kil/AXdarW'),(21,'jz250176d@student.etf.bg.ac.rs','$2a$10$YT.dFxpVsSrvyneRYlFf0e1BuxGkzkj4BggaXeX3C81zRMcNujGr.'),(22,'pj220078d@student.etf.bg.ac.rs','$2a$10$YT.dFxpVsSrvyneRYlFf0e1BuxGkzkj4BggaXeX3C81zRMcNujGr.'),(23,'jn220023d@student.etf.bg.ac.rs','$2a$10$YT.dFxpVsSrvyneRYlFf0e1BuxGkzkj4BggaXeX3C81zRMcNujGr.'),(24,'mm22077d@student.etf.bg.ac.rs','$2a$10$YT.dFxpVsSrvyneRYlFf0e1BuxGkzkj4BggaXeX3C81zRMcNujGr.'),(25,'lm220176d@student.etf.bg.ac.rs','$2a$10$YT.dFxpVsSrvyneRYlFf0e1BuxGkzkj4BggaXeX3C81zRMcNujGr.'),(26,'kb210242d@student.etf.bg.ac.rs','$2a$10$YT.dFxpVsSrvyneRYlFf0e1BuxGkzkj4BggaXeX3C81zRMcNujGr.'),(27,'dj220095d@student.etf.bg.ac.rs','$2a$10$YT.dFxpVsSrvyneRYlFf0e1BuxGkzkj4BggaXeX3C81zRMcNujGr.'),(28,'vv220163d@student.etf.bg.ac.rs','$2a$10$YT.dFxpVsSrvyneRYlFf0e1BuxGkzkj4BggaXeX3C81zRMcNujGr.'),(29,'nm21008d@student.etf.bg.ac.rs','$2a$10$8lXTtx6FOYGNy5j/L.YqQOnjlN/.lzXgmgGk/BnRqt0H1VCtwCkX2'),(30,'mj210088d@student.etf.bg.ac.rs','$2a$10$8lXTtx6FOYGNy5j/L.YqQOnjlN/.lzXgmgGk/BnRqt0H1VCtwCkX2'),(31,'sv210129d@student.etf.bg.ac.rs','$2a$10$8lXTtx6FOYGNy5j/L.YqQOnjlN/.lzXgmgGk/BnRqt0H1VCtwCkX2'),(32,'dp220700d@student.etf.bg.ac.rs','$2a$10$8lXTtx6FOYGNy5j/L.YqQOnjlN/.lzXgmgGk/BnRqt0H1VCtwCkX2'),(33,'pm210200d@student.etf.bg.ac.rs','$2a$10$8lXTtx6FOYGNy5j/L.YqQOnjlN/.lzXgmgGk/BnRqt0H1VCtwCkX2'),(34,'nd220132d@student.etf.bg.ac.rs','$2a$10$8lXTtx6FOYGNy5j/L.YqQOnjlN/.lzXgmgGk/BnRqt0H1VCtwCkX2');
/*!40000 ALTER TABLE `registrovani` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rezervacije_laboratorija`
--

DROP TABLE IF EXISTS `rezervacije_laboratorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rezervacije_laboratorija` (
  `id` int NOT NULL AUTO_INCREMENT,
  `idLaboratorija` int NOT NULL,
  `nazivObaveze` varchar(145) NOT NULL,
  `idNastavnik` int NOT NULL,
  `datum` date NOT NULL,
  `vremeOd` time NOT NULL,
  `vremeDo` time NOT NULL,
  `akronim` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_idNastavnik_fk_idx` (`idNastavnik`),
  KEY `fk_idLab_fk_idx` (`idLaboratorija`),
  CONSTRAINT `fk_idLab_fk` FOREIGN KEY (`idLaboratorija`) REFERENCES `laboratorije` (`id`),
  CONSTRAINT `fk_idNastavnik_fk` FOREIGN KEY (`idNastavnik`) REFERENCES `nastavnici` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rezervacije_laboratorija`
--

LOCK TABLES `rezervacije_laboratorija` WRITE;
/*!40000 ALTER TABLE `rezervacije_laboratorija` DISABLE KEYS */;
INSERT INTO `rezervacije_laboratorija` VALUES (3,2,'Odbrana projekta',1,'2025-12-08','09:00:00','15:00:00','PP1'),(4,1,'Odbrana projekta',1,'2025-12-08','09:00:00','15:00:00','PP1'),(5,1,'Odbrana projekta ',2,'2025-12-10','15:00:00','16:30:00','VLSI'),(6,1,'Lab vezba',1,'2025-12-16','10:15:00','14:00:00','IS1'),(7,3,'Kolokvijum',3,'2025-12-24','11:00:00','15:00:00','VLSI'),(8,4,'Kolokvijum',3,'2025-12-24','11:00:00','15:00:00','VLSI'),(9,2,'Laboratorijska vezba 2',3,'2025-12-24','15:00:00','17:00:00','BP1'),(10,1,'Laboratorijska vezba 2',3,'2025-12-24','15:00:00','17:00:00','BP1'),(11,2,'Odbrana domaćeg',3,'2025-12-25','08:00:00','14:00:00','ASP'),(12,1,'Odbrana domaćeg',3,'2025-12-25','08:00:00','14:00:00','ASP'),(13,2,'Prvi kolokvijum SI',3,'2025-12-26','13:30:00','16:30:00','MIPS'),(14,1,'Prvi kolokvijum IR',3,'2025-12-26','10:00:00','13:00:00','MIPS'),(15,1,'Odbrana domaćeg',1,'2025-12-27','09:00:00','17:00:00','P1'),(16,4,'Odbrana domaćeg',1,'2025-12-27','09:00:00','17:00:00','P1'),(17,2,'Odbrana domaćeg',1,'2025-12-27','09:00:00','17:00:00','P1'),(18,3,'Odbrana domaćeg',1,'2025-12-27','09:00:00','17:00:00','P1'),(19,2,'Kolokvijum IR',1,'2025-12-22','12:00:00','15:00:00','IS1'),(20,4,'Kolokvijum IR',1,'2025-12-22','12:00:00','15:00:00','IS1'),(21,3,'Kolokvijum SI',1,'2025-12-22','15:15:00','18:30:00','IS1'),(22,5,'prijava',1,'2026-01-23','19:02:00','20:05:00','pp2'),(23,3,'prijava',1,'2026-01-23','19:02:00','20:05:00','pp2'),(24,2,'prijava',1,'2026-01-23','19:02:00','20:05:00','pp2'),(25,3,'Ispit februar',1,'2026-02-21','10:00:00','14:30:00','AOR1'),(26,2,'Ispit februar',1,'2026-02-21','10:00:00','14:30:00','AOR1'),(27,5,'Ispit februar',1,'2026-02-21','10:00:00','14:30:00','AOR1'),(28,4,'Ispit februar',1,'2026-02-21','10:00:00','14:30:00','AOR1'),(29,1,'Projekat februar',1,'2026-02-21','10:15:00','15:00:00','SAB'),(30,4,'Projekat februar',1,'2026-02-20','15:15:00','18:00:00','SAB'),(31,3,'Projekat februar',1,'2026-02-20','15:15:00','18:00:00','SAB'),(32,1,'Projekat februar',1,'2026-02-20','15:15:00','18:00:00','SAB');
/*!40000 ALTER TABLE `rezervacije_laboratorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_predmet`
--

DROP TABLE IF EXISTS `student_predmet`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `student_predmet` (
  `idStudent` int NOT NULL,
  `idPredmet` int NOT NULL,
  PRIMARY KEY (`idStudent`,`idPredmet`),
  KEY `idPredmet_idx` (`idPredmet`),
  CONSTRAINT `idPredmet` FOREIGN KEY (`idPredmet`) REFERENCES `predmeti` (`id`),
  CONSTRAINT `idStudent` FOREIGN KEY (`idStudent`) REFERENCES `studenti` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_predmet`
--

LOCK TABLES `student_predmet` WRITE;
/*!40000 ALTER TABLE `student_predmet` DISABLE KEYS */;
INSERT INTO `student_predmet` VALUES (1,2),(18,8),(6,12),(18,12),(21,12),(3,39),(3,43),(15,46),(15,47),(3,48),(15,48),(3,49),(15,49),(15,50),(3,51),(15,51),(3,53),(15,53),(15,54),(1,66),(2,66),(2,70),(4,70),(22,70),(23,70),(24,70),(25,70),(26,70),(27,70),(28,70),(29,70),(30,70),(31,70),(32,70),(33,70),(34,70),(2,71),(1,74),(1,77),(2,77),(4,77),(1,81),(1,82);
/*!40000 ALTER TABLE `student_predmet` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `studenti`
--

DROP TABLE IF EXISTS `studenti`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `studenti` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ime` varchar(45) NOT NULL,
  `prezime` varchar(45) NOT NULL,
  `godina_upisa` int NOT NULL,
  `br_indeksa` int NOT NULL,
  `smer` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `studenti`
--

LOCK TABLES `studenti` WRITE;
/*!40000 ALTER TABLE `studenti` DISABLE KEYS */;
INSERT INTO `studenti` VALUES (1,'Marija','Kovačević',2021,300,'rti','km210300d@student.etf.bg.ac.rs'),(2,'Iva','Ivanović',2021,88,'rti','ii210088d@student.etf.bg.ac.rs'),(3,'Petar','Petrović',2020,156,'si','pp200156d@student.etf.bg.ac.rs'),(4,'Jana','Janković',2022,667,'rti','jj220667d@student.etf.bg.ac.rs'),(5,'Nikola','Jovanović',2023,14,'si','jn230014d@student.etf.bg.ac.rs'),(6,'Sonja','Nenadović',2024,188,'si','ns240188d@student.etf.bg.ac.rs'),(7,'Milan','Slović',2022,101,'rti','sm20101d@student.etf.bg.ac.rs'),(8,'Lana','Jović',2021,767,'si','jl210767d@student.etf.bg.ac.rs'),(9,'Bojan','Bogdanović',2024,8,'rti','bb240008d@student.etf.bg.ac.rs'),(10,'Miroslav','Miković',2019,376,'si','mm190376d@student.etf.bg.ac.rs'),(11,'Sara','Dragić',2023,211,'rti','ds230211d@student.etf.bg.ac.rs'),(12,'Tina','Božić',2022,17,'si','bt220017d@student.etf.bg.ac.rs'),(13,'Pavle','Negojević',2024,265,'rti','np240265d@student.etf.bg.ac.rs'),(14,'Sofija','Maslać',2020,118,'rti','ms200118d@student.etf.bg.ac.rs'),(15,'Nemanja','Maksić',2022,557,'si','mn220557d@student.etf.bg.ac.rs'),(16,'Stefan','Jovanović',2021,111,'si','js210111d@student.etf.bg.ac.rs'),(17,'Jovan','Milović',2024,221,'rti','mj240221d@student.etf.bg.ac.rs'),(18,'Srđan','Nikolić',2025,22,'si','ns250022d@student.etf.bg.ac.rs'),(19,'Mina','Milosavljević',2025,148,'rti','mm250148d@student.etf.bg.ac.rs'),(20,'Nevena','Mitić',2023,653,'si','mn230653d@student.etf.bg.ac.rs'),(21,'Zoran','Jakšić',2025,176,'si','jz250176d@student.etf.bg.ac.rs'),(22,'Jasna','Pavlović',2022,78,'rti','pj220078d@student.etf.bg.ac.rs'),(23,'Nina','Jolkić',2022,23,'rti','jn220023d@student.etf.bg.ac.rs'),(24,'Milan','Majstorović',2022,777,'rti','mm22077d@student.etf.bg.ac.rs'),(25,'Milica','Lalić',2022,176,'rti','lm220176d@student.etf.bg.ac.rs'),(26,'Bogdan','Kragović',2021,242,'rti','kb210242d@student.etf.bg.ac.rs'),(27,'Jovan','Dukić',2022,95,'rti','dj220095d@student.etf.bg.ac.rs'),(28,'Vuk','Vesović',2022,163,'rti','vv220163d@student.etf.bg.ac.rs'),(29,'Miloš','Nikolić',2021,89,'rti','nm210089d@student.etf.bg.ac.rs'),(30,'Jovana','Mišić',2022,111,'rti','mj210088d@student.etf.bg.ac.rs'),(31,'Vukašin','Selimović',2021,129,'rti','sv210129d@student.etf.bg.ac.rs'),(32,'Petar','Dragović',2022,700,'rti','dp220700d@student.etf.bg.ac.rs'),(33,'Milica','Petrović',2021,200,'rti','pm210200d@student.etf.bg.ac.rs'),(34,'Dragan','Nedić',2022,132,'rti','nd220132d@student.etf.bg.ac.rs');
/*!40000 ALTER TABLE `studenti` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-02-20  7:35:58
