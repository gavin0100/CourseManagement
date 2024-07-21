-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: database_fams
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `syllabus`
--

DROP TABLE IF EXISTS `syllabus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `syllabus` (
  `created_by_user_id` int DEFAULT NULL,
  `modified_by_user_id` int DEFAULT NULL,
  `topic_code` int NOT NULL AUTO_INCREMENT,
  `training_audience` int DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `modifed_date` datetime(6) DEFAULT NULL,
  `publish_status` varchar(50) DEFAULT NULL,
  `topic_name` varchar(50) DEFAULT NULL,
  `training_principles` varchar(50) DEFAULT NULL,
  `version` varchar(50) DEFAULT NULL,
  `technical_group` varchar(500) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`topic_code`),
  KEY `FKbrsydurnsear1calb0htdvrkt` (`created_by_user_id`),
  KEY `FKqarluay244f88iex8e7897mvd` (`modified_by_user_id`),
  CONSTRAINT `FKbrsydurnsear1calb0htdvrkt` FOREIGN KEY (`created_by_user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKqarluay244f88iex8e7897mvd` FOREIGN KEY (`modified_by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syllabus`
--

LOCK TABLES `syllabus` WRITE;
/*!40000 ALTER TABLE `syllabus` DISABLE KEYS */;
INSERT INTO `syllabus` (`created_by_user_id`, `modified_by_user_id`, `topic_code`, `training_audience`, `created_date`, `modifed_date`, `publish_status`, `topic_name`, `training_principles`, `version`, `technical_group`, `level`) VALUES (1,1,1,20,'2020-12-12 00:00:00.000000','2020-12-12 00:00:00.000000','active','Spring Boot','overload','i1','MySQL, Intellij','Advanced'),(NULL,NULL,3,0,NULL,NULL,NULL,NULL,NULL,'i1',NULL,'Advanced'),(1,1,4,20,'2023-10-24 01:57:46.000000',NULL,'active',NULL,'overload','i1','MySQL','Advanced'),(1,1,5,20,'2023-10-24 01:57:46.000000',NULL,'active','test2','overload','i1','MySQL','Advanced'),(1,1,6,20,'2023-10-24 01:57:46.000000',NULL,'active','test2','overload','i1','MySQL','Advanced'),(1,NULL,7,20,'2023-10-24 01:57:46.000000',NULL,'active','test2','overload','i1','MySQL','Advanced'),(1,NULL,8,20,'2023-10-29 01:10:30.343000',NULL,NULL,'test2',NULL,'i1','MySQL','Advanced'),(1,NULL,9,20,'2023-10-29 02:01:17.926000','2023-10-29 02:53:03.076000',NULL,'test2',NULL,'i1','MySQL','Advanced'),(1,NULL,10,30,'2023-10-29 03:52:36.975000',NULL,'Inactive','test3',NULL,'i2','SQL','Advanced'),(1,NULL,11,30,'2023-10-29 03:54:19.997000',NULL,'Inactive','test3',NULL,'i2','SQL','Advanced'),(1,NULL,12,30,'2023-10-29 03:55:05.221000',NULL,'Inactive','test3',NULL,'i2','SQL','Advanced'),(1,NULL,13,30,'2023-10-29 03:56:17.260000','2023-10-29 04:12:28.841000','Active','test3',NULL,'i2','SQL','Advanced'),(1,NULL,14,20,'2023-10-31 16:23:28.943000','2023-10-31 16:33:27.572000','Inactive','test09878',NULL,'i1','MySQL','Advanced'),(1,NULL,15,20,'2023-10-31 16:44:17.770000','2023-10-31 16:51:00.606000','Active','test2','Skill','i1','MySQL','Advanced'),(1,NULL,16,0,NULL,'2023-10-31 16:48:11.682000','Active',NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `syllabus` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-01  8:41:31
