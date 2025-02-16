-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: meal_card_authentication
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `food_menu`
--

DROP TABLE IF EXISTS `food_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `food_menu` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `food_name` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `date` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `food_menu`
--

LOCK TABLES `food_menu` WRITE;
/*!40000 ALTER TABLE `food_menu` DISABLE KEYS */;
INSERT INTO `food_menu` VALUES (1,'Chechebsa','breakfast','Monday','2025-02-07 22:44:13',NULL),(2,'Shiro Wat with Injera','lunch','Monday','2025-02-07 22:44:13',NULL),(3,'Doro Wat','dinner','Monday','2025-02-07 22:44:13',NULL),(4,'Beyaynetu (Mixed Dish)','lunch','Monday','2025-02-07 22:44:13',NULL),(5,'Kale (Salad)','lunch','Monday','2025-02-07 22:44:13',NULL),(6,'Firfir','breakfast','Tuesday','2025-02-07 22:44:13',NULL),(7,'Misir Wat with Injera','lunch','Tuesday','2025-02-07 22:44:13',NULL),(8,'Tibs (Beef Stir Fry)','dinner','Tuesday','2025-02-07 22:44:13',NULL),(9,'Gomen Wat (Collard Greens)','lunch','Tuesday','2025-02-07 22:44:13',NULL),(10,'Shiro with Beef','lunch','Tuesday','2025-02-07 22:44:13',NULL),(11,'Genfo','breakfast','Wednesday','2025-02-07 22:44:13',NULL),(12,'Doro Wat','lunch','Wednesday','2025-02-07 22:44:13',NULL),(13,'Gomen (Collard Greens)','dinner','Wednesday','2025-02-07 22:44:13',NULL),(14,'Atayef (Stuffed Pancakes)','lunch','Wednesday','2025-02-07 22:44:13',NULL),(15,'Sambusa (Savory Pastry)','lunch','Wednesday','2025-02-07 22:44:13',NULL),(16,'Injera with Foul','breakfast','Thursday','2025-02-07 22:44:13',NULL),(17,'Lunch Content Streaming: Traditional Ethiopian Dishes','lunch','Thursday','2025-02-07 22:44:13',NULL),(18,'Kitfo (Minced Raw Meat)','dinner','Thursday','2025-02-07 22:44:13',NULL),(19,'Shiro with Chickpeas','lunch','Thursday','2025-02-07 22:44:13',NULL),(20,'Beets and Carrots','lunch','Thursday','2025-02-07 22:44:13',NULL),(21,'Kita','breakfast','Friday','2025-02-07 22:44:13',NULL),(22,'Zigni (Spicy Stew)','lunch','Friday','2025-02-07 22:44:13',NULL),(23,'Fish Wat','dinner','Friday','2025-02-07 22:44:13',NULL),(24,'Dabo Kolo (Roasted Bread)','lunch','Friday','2025-02-07 22:44:13',NULL),(25,'Lentil Salad','lunch','Friday','2025-02-07 22:44:13',NULL),(26,'Alicha (Mild Stew)','breakfast','Saturday','2025-02-07 22:44:13',NULL),(27,'Siga Wat (Beef Stew)','lunch','Saturday','2025-02-07 22:44:13',NULL),(28,'Shiro with Injera','dinner','Saturday','2025-02-07 22:44:13',NULL),(29,'Tuna Salad with Injera','lunch','Saturday','2025-02-07 22:44:13',NULL),(30,'Eggplant Stew','lunch','Saturday','2025-02-07 22:44:13',NULL);
/*!40000 ALTER TABLE `food_menu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-17  0:33:02
