CREATE DATABASE  IF NOT EXISTS `MyDb` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `MyDb`;
-- MySQL dump 10.13  Distrib 5.7.17, for macos10.12 (x86_64)
--
-- Host: localhost    Database: MyDb
-- ------------------------------------------------------
-- Server version	5.7.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Records`
--

DROP TABLE IF EXISTS `Records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Records` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `LevelName` varchar(45) NOT NULL,
  `UserName` varchar(45) NOT NULL,
  `NumOfSteps` int(11) DEFAULT NULL,
  `Time` double DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=49 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Records`
--

LOCK TABLES `Records` WRITE;
/*!40000 ALTER TABLE `Records` DISABLE KEYS */;
INSERT INTO `Records` VALUES (1,'123','Ron',4,4.3),(2,'d75aaeef-3440-4d57-864d-24bad48b6a8f','Optional[rony]',3,4.103),(3,'c197abf3-949f-40d5-8b4b-94a1b3f8c1d7','Optional[ron]',3,17.23),(4,'level1','Optional[rony]',3,10.921),(5,'level1','Optional[rony]',3,1491403936.397),(6,'level1','Optional[]',3,1491403980.676),(7,'level1','Optional[rony]',3,6.089),(8,'level1','Optional[ronya]',3,8.64),(9,'level1','Optional[blabla]',3,7),(10,'level1','Optional[lkjk]',3,4),(11,'level1','Optional[fdslklsf]',3,7),(12,'level1','Optional[kjkjh]',3,6),(13,'level1','Optional[ronyrony]',3,15),(14,'level1','Optional[ronyrony]',3,17),(15,'level1','Optional[dfsf]',3,5),(16,'level1','Optional[rony]',3,10.719),(17,'level1','Optional[lmblmdblmd]',3,9.28),(18,'level1','Optional[isthisit?]',3,13.058879485),(19,'level1','Optional[jnkjhkjh]',3,6.743071521),(20,'level1','Optional[lkjlkjlk]',7,9.5155765),(21,'level1','Optional[ronyronyrony]',5,10.094132928),(22,'level1','Optional[gfdgdf]',3,3.656228874),(23,'level1','Optional[fgdg]',5,4.452146726),(24,'level1','Optional[fsdfdsfsd]',5,5.999865589),(25,'level1','Optional[kjkjhkj]',3,164.085317113),(26,'level1','Optional[rewrwrw]',5,6.527520855),(27,'level1','Optional[ronyronyrony]',5,12.655798419),(28,'level1','check',5,11.117152642),(29,'level1','ronya',9,31.145289984),(30,'level1','ronyron',5,4.584664067),(31,'level1','ronyrony',5,2.221214216),(32,'level1','lol lol',5,3.205274723),(33,'level1','rony',3,5.702915461),(34,'level2','rony',1,1.549740324),(35,'level1','rony',3,30.245508516),(36,'level1','rony',3,15.456329597),(37,'level2','rony',1,18.688736334),(38,'level1','',3,5.549592963),(39,'level1','rony',5,5.415823863),(40,'level1','rony',3,0.974432943),(41,'level1','rony',5,13.139359473),(42,'level1','ronyrony',3,3.106771062),(43,'level1','ronyrony',5,13.207221499),(44,'level1','ronyronyrony',3,1.175311335),(45,'level1','ronyronyrony',3,1.628786741),(46,'level1','ronyrony',3,2.620678873),(47,'level1','rony',3,1.039797153),(48,'level1','rony',3,3.283413832);
/*!40000 ALTER TABLE `Records` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-30 19:20:47
