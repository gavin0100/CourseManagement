-- MySQL dump 10.13  Distrib 8.0.16, for Win64 (x86_64)
--
-- Host: localhost    Database: database_fams
-- ------------------------------------------------------
-- Server version	8.0.16

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `class`
--

DROP TABLE IF EXISTS `class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `class` (
  `class_id` int(11) NOT NULL AUTO_INCREMENT,
  `class_code` varchar(50) DEFAULT NULL,
  `class_name` varchar(50) DEFAULT NULL,
  `created_date` datetime(6) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `end_date` datetime(6) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `start_date` datetime(6) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `training_program_code` int(11) DEFAULT NULL,
  `created_by_user_id` int(11) DEFAULT NULL,
  `modified_by_user_id` int(11) DEFAULT NULL,
  `training_program_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`class_id`),
  KEY `FKs1boq7vwpn9ifffhp8oaw99m7` (`created_by_user_id`),
  KEY `FK2dad6rto76mkwe0uhlpuxs5eb` (`modified_by_user_id`),
  KEY `FKfdvyhw0guhq14ykqylfb3hvmn` (`training_program_id`),
  CONSTRAINT `FK2dad6rto76mkwe0uhlpuxs5eb` FOREIGN KEY (`modified_by_user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKfdvyhw0guhq14ykqylfb3hvmn` FOREIGN KEY (`training_program_id`) REFERENCES `training_program` (`training_program_code`),
  CONSTRAINT `FKs1boq7vwpn9ifffhp8oaw99m7` FOREIGN KEY (`created_by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `class`
--

LOCK TABLES `class` WRITE;
/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` VALUES (1,'HCM22_FR_DevOps_01','DevOps Foundation','2023-11-01 15:49:36.516000',10,'2002-02-10 00:00:00.000000','Ca Mau','2023-11-06 08:57:25.494000','2002-02-10 00:00:00.000000','Planing',1,1,1,1),(2,'HCM22_FR_DevOps_02','DevOps Foundation','2002-02-10 17:00:00.000000',2,'2002-02-10 17:00:00.000000','Ha Noi','2002-02-10 17:00:00.000000','2002-02-10 17:00:00.000000','Opening',1,2,2,1),(3,'HCM22_FR_DevOps_03','BA Foundation Basic','2002-02-10 17:00:00.000000',10,'2023-11-08 00:00:00.000000','Ha Noi','2023-11-06 08:43:31.753000','2023-11-09 00:00:00.000000','Scheduled',1,1,1,1),(6,'DN2302','Linearing','2023-11-06 06:13:33.586000',30,'2023-11-10 00:00:00.000000','Da Nang',NULL,'2023-11-07 00:00:00.000000','Completed',18,1,NULL,18),(9,'a2303','BA Foundation Advance','2023-11-06 09:30:37.982000',10,'2023-11-30 00:00:00.000000','Truong Sa','2023-11-06 09:36:23.457000','2023-11-10 00:00:00.000000','Planing',22,1,1,22),(11,'a2303','BA Foundation Advance','2023-11-06 09:36:35.026000',10,'2023-11-30 00:00:00.000000','Hoang Sa','2023-11-06 15:08:09.525000','2023-11-10 00:00:00.000000','Scheduled',22,1,1,22),(12,'VVN2301','Civil Struction','2023-11-06 15:46:12.046000',11,'2023-11-29 00:00:00.000000','Vo Van Ngan','2023-11-06 15:46:21.433000','2023-11-11 00:00:00.000000','Scheduled',21,1,1,21);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `file_material`
--

DROP TABLE IF EXISTS `file_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `file_material` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `url` varchar(200) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`file_id`),
  KEY `FKp274pkw8gflxagnxth3wi47hw` (`user_id`),
  CONSTRAINT `FKp274pkw8gflxagnxth3wi47hw` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `file_material`
--

LOCK TABLES `file_material` WRITE;
/*!40000 ALTER TABLE `file_material` DISABLE KEYS */;
INSERT INTO `file_material` VALUES (1,'2023-11-06 15:31:56.141000','image1.jpg','F://ThucTap',1);
/*!40000 ALTER TABLE `file_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `learning_objective`
--

DROP TABLE IF EXISTS `learning_objective`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `learning_objective` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `description` varchar(500) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `learning_objective`
--

LOCK TABLES `learning_objective` WRITE;
/*!40000 ALTER TABLE `learning_objective` DISABLE KEYS */;
INSERT INTO `learning_objective` VALUES (1,'Hoat bat, nang dong, nhiet tinh','Attitude','Skill');
/*!40000 ALTER TABLE `learning_objective` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syllabus`
--

DROP TABLE IF EXISTS `syllabus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `syllabus` (
  `topic_code` int(11) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `modifed_date` datetime(6) DEFAULT NULL,
  `publish_status` varchar(50) DEFAULT NULL,
  `technical_group` varchar(500) DEFAULT NULL,
  `topic_name` varchar(50) DEFAULT NULL,
  `training_audience` int(11) DEFAULT NULL,
  `training_principles` varchar(50) DEFAULT NULL,
  `version` varchar(50) DEFAULT NULL,
  `created_by_user_id` int(11) DEFAULT NULL,
  `modified_by_user_id` int(11) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `learning_objective` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`topic_code`),
  KEY `FKbrsydurnsear1calb0htdvrkt` (`created_by_user_id`),
  KEY `FKqarluay244f88iex8e7897mvd` (`modified_by_user_id`),
  CONSTRAINT `FKbrsydurnsear1calb0htdvrkt` FOREIGN KEY (`created_by_user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKqarluay244f88iex8e7897mvd` FOREIGN KEY (`modified_by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syllabus`
--

LOCK TABLES `syllabus` WRITE;
/*!40000 ALTER TABLE `syllabus` DISABLE KEYS */;
INSERT INTO `syllabus` VALUES (1,'2023-11-01 16:01:20.202000','2023-11-06 15:31:09.293000','Active','MySQL, Intellij','Spring Boot',20,'Attitude','1.0',1,NULL,'Advanced',1,'a'),(3,'2020-12-12 00:00:00.000000','2020-12-12 00:00:00.000000','Active','MongoDb','Spring Boot',0,'overload','i1',1,1,'Advanced',1,'a'),(4,'2023-10-24 01:57:46.000000','2020-12-12 00:00:00.000000','Active','MySQL','Spring Boot',20,'overload','i1',1,1,'Advanced',1,'a'),(5,'2023-10-24 01:57:46.000000','2020-12-12 00:00:00.000000','Active','MySQL','test2',20,'overload','i1',1,1,'Advanced',1,'a'),(6,'2023-10-24 01:57:46.000000','2020-12-12 00:00:00.000000','Drafting','MySQL','test2',20,'overload','i1',1,1,'Advanced',1,'a'),(7,'2023-10-24 01:57:46.000000','2020-12-12 00:00:00.000000','Drafting','MySQL','test2',20,'overload','i1',1,1,'Advanced',1,'a'),(8,'2023-10-29 01:10:30.343000','2020-12-12 00:00:00.000000','Drafting','MySQL','test2',20,'Skill','i1',1,1,'Advanced',1,'a'),(9,'2023-10-29 02:01:17.926000','2023-10-29 02:53:03.076000','Inactive','MySQL','test2',20,'Skill','i1',1,1,'Advanced',1,'a'),(10,'2023-10-29 03:52:36.975000','2020-12-12 00:00:00.000000','Inactive','SQL','test3',30,'Skill','i2',1,1,'Basic',1,'a'),(11,'2023-10-29 03:54:19.997000','2020-12-12 00:00:00.000000','Inactive','SQL','test3',30,'Skill','i2',1,1,'Advanced',1,'a'),(12,'2023-10-29 03:55:05.221000','2020-12-12 00:00:00.000000','Inactive','SQL','test3',30,'Skill','i2',1,1,'Advanced',1,'a'),(13,'2023-10-29 03:56:17.260000','2023-10-29 04:12:28.841000','Active','SQL','test3',30,'Skill','i2',1,1,'Basic',1,'a'),(14,'2023-10-31 16:23:28.943000','2023-10-31 16:33:27.572000','Inactive','MySQL','test09878',20,'Skill','i1',1,1,'Advanced',1,'a'),(15,'2023-10-31 16:44:17.770000','2023-10-31 16:51:00.606000','Active','MySQL','test2',20,'Skill','i1',1,1,'Advanced',1,'a'),(16,'2020-12-12 00:00:00.000000','2023-10-31 16:48:11.682000','Active','MySQL','test2',0,'Skill','i1',1,1,'Basic',1,'a'),(18,'2023-11-01 16:02:38.426000','2023-11-06 15:30:56.450000','Draft','MySQL, Intellij','Basic Python 3.0',20,'overload','1.0',1,NULL,'Advanced',1,'a'),(21,'2023-11-06 15:30:28.338000','2020-12-12 00:00:00.000000','Draft','MySQL, Intellij','Basic Python 3.0',20,'overload','1.0',1,NULL,'Advanced',0,NULL);
/*!40000 ALTER TABLE `syllabus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `syllabus_training_objective`
--

DROP TABLE IF EXISTS `syllabus_training_objective`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `syllabus_training_objective` (
  `topic_code` int(11) NOT NULL,
  `code` int(11) NOT NULL,
  KEY `FKmactsoq7uhkhe0q0eupj4f7ri` (`code`),
  KEY `FKgt2isobg5ko6po3i94lwswk3` (`topic_code`),
  CONSTRAINT `FKgt2isobg5ko6po3i94lwswk3` FOREIGN KEY (`topic_code`) REFERENCES `syllabus` (`topic_code`),
  CONSTRAINT `FKmactsoq7uhkhe0q0eupj4f7ri` FOREIGN KEY (`code`) REFERENCES `learning_objective` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `syllabus_training_objective`
--

LOCK TABLES `syllabus_training_objective` WRITE;
/*!40000 ALTER TABLE `syllabus_training_objective` DISABLE KEYS */;
INSERT INTO `syllabus_training_objective` VALUES (1,1);
/*!40000 ALTER TABLE `syllabus_training_objective` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_content`
--

DROP TABLE IF EXISTS `training_content`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `training_content` (
  `file_id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(50) DEFAULT NULL,
  `delivery_type` varchar(50) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `note` varchar(50) DEFAULT NULL,
  `training_format` bit(1) DEFAULT NULL,
  `learning_objective_id` int(11) DEFAULT NULL,
  `unit_code` int(11) DEFAULT NULL,
  PRIMARY KEY (`file_id`),
  KEY `FK5a6u7kcuuobwg2ngo5b1i4d19` (`learning_objective_id`),
  KEY `FKeo8xs2t1v32o9msh2koj2ekff` (`unit_code`),
  CONSTRAINT `FK5a6u7kcuuobwg2ngo5b1i4d19` FOREIGN KEY (`learning_objective_id`) REFERENCES `learning_objective` (`file_id`),
  CONSTRAINT `FKeo8xs2t1v32o9msh2koj2ekff` FOREIGN KEY (`unit_code`) REFERENCES `training_unit` (`unit_code`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_content`
--

LOCK TABLES `training_content` WRITE;
/*!40000 ALTER TABLE `training_content` DISABLE KEYS */;
INSERT INTO `training_content` VALUES (1,'.Net Introduction','Concept_Lecture',30,'Empty',_binary '',NULL,1),(2,'Declaration & Assignment','Assignment_Lab',30,'On time at 7am',_binary '',NULL,1),(3,'Operators','Guide_Review',20,'On time at 9am',_binary '\0',NULL,1),(4,'Operators','Guide_Review',20,'On time at 9am',_binary '\0',NULL,1),(5,'Operators','Guide_Review',20,'On time at 9am',_binary '\0',NULL,1);
/*!40000 ALTER TABLE `training_content` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_content_file_material`
--

DROP TABLE IF EXISTS `training_content_file_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `training_content_file_material` (
  `training_content_id` int(11) NOT NULL,
  `file_id` int(11) NOT NULL,
  KEY `FK1y0h2adgmt4wq6bhucmudd9ra` (`file_id`),
  KEY `FKpp7gqao4i76ajpusp8x2bxygr` (`training_content_id`),
  CONSTRAINT `FK1y0h2adgmt4wq6bhucmudd9ra` FOREIGN KEY (`file_id`) REFERENCES `file_material` (`file_id`),
  CONSTRAINT `FKpp7gqao4i76ajpusp8x2bxygr` FOREIGN KEY (`training_content_id`) REFERENCES `training_content` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_content_file_material`
--

LOCK TABLES `training_content_file_material` WRITE;
/*!40000 ALTER TABLE `training_content_file_material` DISABLE KEYS */;
INSERT INTO `training_content_file_material` VALUES (1,1);
/*!40000 ALTER TABLE `training_content_file_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_program`
--

DROP TABLE IF EXISTS `training_program`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `training_program` (
  `training_program_code` int(11) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `duration` int(11) DEFAULT NULL,
  `modifed_date` datetime(6) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  `topic_code` int(11) DEFAULT NULL,
  `created_by_user_id` int(11) DEFAULT NULL,
  `modified_by_user_id` int(11) DEFAULT NULL,
  `general_information` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`training_program_code`),
  KEY `FKdh5q9pmpmyi1i4v65o744um9a` (`created_by_user_id`),
  KEY `FKpgwas5ddepr9e2nyao9p7056s` (`modified_by_user_id`),
  CONSTRAINT `FKdh5q9pmpmyi1i4v65o744um9a` FOREIGN KEY (`created_by_user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKpgwas5ddepr9e2nyao9p7056s` FOREIGN KEY (`modified_by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_program`
--

LOCK TABLES `training_program` WRITE;
/*!40000 ALTER TABLE `training_program` DISABLE KEYS */;
INSERT INTO `training_program` VALUES (1,'2002-02-10 17:00:00.000000',10,'2023-11-06 15:39:48.357000','.Net basic program Basic','2002-02-10 17:00:00.000000','Active',0,1,1,'This class has been held for 3 decades'),(3,'2002-02-10 17:00:00.000000',12,'2002-02-10 17:00:00.000000','It business analysis foundation','2002-02-10 17:00:00.000000','Active',1,1,1,'This class has been held for 3 decades'),(4,'2002-02-10 17:00:00.000000',13,'2002-02-10 17:00:00.000000','Devops Foundation 2','2002-02-10 17:00:00.000000','Inactive',1,2,2,'This class has been held for 3 decades'),(7,'2004-02-10 17:00:00.000000',11,'2004-02-10 17:00:00.000000','Full stack java web 1','2004-02-10 17:00:00.000000','Active',1,2,2,'This class has been held for 3 decades'),(9,'2006-02-10 17:00:00.000000',13,'2006-02-10 17:00:00.000000','Full stack nodejs 2','2006-02-10 17:00:00.000000','Inactive',1,2,2,'This class has been held for 3 decades'),(11,'2008-02-10 17:00:00.000000',15,'2008-02-10 17:00:00.000000','Embeded full 2','2008-02-10 17:00:00.000000','Active',1,1,1,'This class has been held for 3 decades'),(12,'2009-02-10 17:00:00.000000',16,'2009-02-10 17:00:00.000000','Mobile Android','2009-02-10 17:00:00.000000','Active',1,2,2,'This class has been held for 3 decades'),(13,'2010-02-10 17:00:00.000000',17,'2010-02-10 17:00:00.000000','Mobile IOS','2010-02-10 17:00:00.000000','Inactive',1,1,1,'This class has been held for 3 decades'),(14,'2023-11-02 13:08:19.027000',0,'2010-02-10 17:00:00.000000','3D','2023-11-05 21:37:00.000000','Active',0,1,1,'This class has been held for 3 decades'),(17,'2023-11-05 21:57:07.713000',11,'2010-02-10 17:00:00.000000','Devops Foundation','2023-11-05 21:37:00.000000','Active',0,1,1,'This class has been held for 2 decades'),(18,'2023-11-05 22:00:54.587000',30,NULL,'Deep learning 1',NULL,'Active',0,1,NULL,'It\'s important about the way of learning'),(19,'2023-11-05 22:03:13.203000',11,'2010-02-10 17:00:00.000000','vipro','2023-11-05 21:37:00.000000','Drafting',0,1,1,'This class has been held for 3 decades'),(20,'2023-11-05 22:27:28.379000',10,'2002-02-10 17:00:00.000000','.Net basic program version pro','2002-02-10 17:00:00.000000','Drafting',1,1,1,'This class has been held for 3 decades'),(21,'2023-11-05 22:30:43.852000',11,'2010-02-10 17:00:00.000000','vipro','2023-11-05 21:37:00.000000','Drafting',0,1,1,'This class has been held for 3 decades'),(22,'2023-11-05 22:30:55.463000',10,'2002-02-10 17:00:00.000000','.Net basic program version pro 3','2002-02-10 17:00:00.000000','Drafting',1,1,1,'This class has been held for 3 decades'),(23,'2023-11-05 22:32:05.533000',13,'2002-02-10 17:00:00.000000','Devops Foundation 3','2002-02-10 17:00:00.000000','Active',1,1,2,'This class has been held for 3 decades'),(27,'2023-11-06 15:41:11.564000',13,'2023-11-06 15:41:11.564000','Devops Foundation 3','2002-02-10 17:00:00.000000','Drafting',1,1,1,'This class has been held for 3 decades');
/*!40000 ALTER TABLE `training_program` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_program_file_material`
--

DROP TABLE IF EXISTS `training_program_file_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `training_program_file_material` (
  `training_program_code` int(11) NOT NULL,
  `file_id` int(11) NOT NULL,
  KEY `FKfdb2ipsfymm8wcmf85lmn1dxe` (`file_id`),
  KEY `FKe34ighn67ywd21igyiavpsf0o` (`training_program_code`),
  CONSTRAINT `FKe34ighn67ywd21igyiavpsf0o` FOREIGN KEY (`training_program_code`) REFERENCES `training_program` (`training_program_code`),
  CONSTRAINT `FKfdb2ipsfymm8wcmf85lmn1dxe` FOREIGN KEY (`file_id`) REFERENCES `file_material` (`file_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_program_file_material`
--

LOCK TABLES `training_program_file_material` WRITE;
/*!40000 ALTER TABLE `training_program_file_material` DISABLE KEYS */;
/*!40000 ALTER TABLE `training_program_file_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_program_syllabus`
--

DROP TABLE IF EXISTS `training_program_syllabus`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `training_program_syllabus` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence` int(11) NOT NULL,
  `topic_code` int(11) DEFAULT NULL,
  `training_program_code` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8k954n114lbn3t715ypelljql` (`topic_code`),
  KEY `FKk3josv9ctn46a5rn6de1qwrue` (`training_program_code`),
  CONSTRAINT `FK8k954n114lbn3t715ypelljql` FOREIGN KEY (`topic_code`) REFERENCES `syllabus` (`topic_code`),
  CONSTRAINT `FKk3josv9ctn46a5rn6de1qwrue` FOREIGN KEY (`training_program_code`) REFERENCES `training_program` (`training_program_code`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_program_syllabus`
--

LOCK TABLES `training_program_syllabus` WRITE;
/*!40000 ALTER TABLE `training_program_syllabus` DISABLE KEYS */;
INSERT INTO `training_program_syllabus` VALUES (1,1,3,1),(7,0,1,1),(11,0,11,7),(13,0,11,4);
/*!40000 ALTER TABLE `training_program_syllabus` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_unit`
--

DROP TABLE IF EXISTS `training_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `training_unit` (
  `unit_code` int(11) NOT NULL AUTO_INCREMENT,
  `number_of_hours` int(11) DEFAULT NULL,
  `unit_name` varchar(50) DEFAULT NULL,
  `topic_code` int(11) DEFAULT NULL,
  `day` varchar(255) DEFAULT NULL,
  `unit` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`unit_code`),
  KEY `FKt4px0lple6ogeyxoygtwpsweq` (`topic_code`),
  CONSTRAINT `FKt4px0lple6ogeyxoygtwpsweq` FOREIGN KEY (`topic_code`) REFERENCES `syllabus` (`topic_code`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_unit`
--

LOCK TABLES `training_unit` WRITE;
/*!40000 ALTER TABLE `training_unit` DISABLE KEYS */;
INSERT INTO `training_unit` VALUES (1,2,'.Net Introduction',1,'Day 1','Unit 1');
/*!40000 ALTER TABLE `training_unit` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_date` datetime(6) DEFAULT NULL,
  `dob` datetime(6) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `modified_date` datetime(6) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `created_by_user_id` int(11) DEFAULT NULL,
  `modified_by_user_id` int(11) DEFAULT NULL,
  `user_permission_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrphqb03y0v2t675pjf3x5aasi` (`created_by_user_id`),
  KEY `FKl6au1cjh3ta40ec5f7w2fecv0` (`modified_by_user_id`),
  KEY `FKr387ltfofs77cpr4gqmojygpq` (`user_permission_id`),
  CONSTRAINT `FKl6au1cjh3ta40ec5f7w2fecv0` FOREIGN KEY (`modified_by_user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKr387ltfofs77cpr4gqmojygpq` FOREIGN KEY (`user_permission_id`) REFERENCES `user_permission` (`permission_id`),
  CONSTRAINT `FKrphqb03y0v2t675pjf3x5aasi` FOREIGN KEY (`created_by_user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'2023-10-17 12:06:19.562000','2002-02-11 00:00:00.000000','voduc0100@gmail.com','Male','2023-11-01 10:34:39.253781','Do Tan LInh','1','0869990186',_binary '\0',NULL,NULL,1),(2,'2023-10-17 12:06:19.615000','2002-02-11 00:00:00.000000','classadmin@gmail.com','Male','2023-10-17 12:06:19.615000','vo van duc1','duc2112002','0869990186',_binary '',NULL,3,2),(3,'2023-10-17 12:06:19.623000','2002-02-11 00:00:00.000000','trainer@gmail.com','Male','2023-10-17 12:06:19.623000','vo van duc2','duc2112002','0869990186',_binary '',NULL,NULL,3),(5,'2023-10-30 11:44:03.129457','2002-02-11 00:00:00.000000','voduc010000@gmail','string',NULL,'string','gHzLRLDa','string',_binary '',NULL,NULL,2),(7,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(8,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(9,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(10,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(11,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(12,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(13,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(14,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(15,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(16,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(17,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(18,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(19,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(20,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(21,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(22,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(23,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(24,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(25,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(26,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(27,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(28,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(29,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(30,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(31,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(32,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(33,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(34,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(35,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(36,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(37,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(38,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(39,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(40,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(41,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(42,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(43,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(44,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(45,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(46,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(47,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(48,'2023-10-30 11:44:03.129457','2023-10-30 11:44:03.129457','a','Female','2023-10-30 11:44:03.129457','a','a','a',_binary '',NULL,NULL,2),(49,'2023-11-01 15:31:58.199815','2002-02-11 00:00:00.000000','Linhdo@gmail.com','Male','2023-11-01 15:36:27.327469','Vo thi Tuyet Nhi','7ondcUJV','77777777778',_binary '\0',NULL,NULL,3),(50,'2023-11-04 18:56:51.989211','2002-02-11 00:00:00.000000','Linhdo@gmail.com','Male','2023-11-01 10:34:39.000000','Tuyet Linh Nu','PpKF3RAt','99999999',_binary '\0',NULL,NULL,2),(51,'2023-11-04 18:59:24.053096',NULL,'thai@gmail.com','Nam',NULL,'thai','BsMj1Vl7','051561651',_binary '',NULL,NULL,3),(52,'2023-11-04 19:01:54.946062',NULL,'thai@gmail.com','Nam',NULL,'thai','ArLoGc0l','051561651',_binary '',NULL,NULL,3),(53,'2023-11-04 19:06:11.857086',NULL,'thai@gmail.com','Nam',NULL,'thai','OTM4VjHL','051561651',_binary '',NULL,NULL,3),(54,'2023-11-06 09:47:23.501793','2023-11-02 00:00:00.000000','chubosuk@gmail.com','Male','2023-11-06 09:48:16.298484','Chu Bo Suk 2','YbrkZRsl','02131321',_binary '',NULL,NULL,2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_class`
--

DROP TABLE IF EXISTS `user_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_class` (
  `user_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  KEY `FKk353eww3dh4sbn6edi2edqqrq` (`class_id`),
  KEY `FK58y9x77hoabiwjdncohp5e8r2` (`user_id`),
  CONSTRAINT `FK58y9x77hoabiwjdncohp5e8r2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKk353eww3dh4sbn6edi2edqqrq` FOREIGN KEY (`class_id`) REFERENCES `class` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_class`
--

LOCK TABLES `user_class` WRITE;
/*!40000 ALTER TABLE `user_class` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_permission`
--

DROP TABLE IF EXISTS `user_permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user_permission` (
  `permission_id` int(11) NOT NULL AUTO_INCREMENT,
  `class` varchar(50) DEFAULT NULL,
  `learning_material` varchar(50) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `syllabus` varchar(50) DEFAULT NULL,
  `training_program` varchar(50) DEFAULT NULL,
  `user_management` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`permission_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_permission`
--

LOCK TABLES `user_permission` WRITE;
/*!40000 ALTER TABLE `user_permission` DISABLE KEYS */;
INSERT INTO `user_permission` VALUES (1,'FULL_ACCESS','FULL_ACCESS','SUPER_ADMIN','FULL_ACCESS','FULL_ACCESS','FULL_ACCESS'),(2,'CREATE','CREATE','CLASS_ADMIN','CREATE','CREATE','CREATE'),(3,'VIEW','VIEW','TRAINER','VIEW','CREATE','VIEW');
/*!40000 ALTER TABLE `user_permission` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-06 15:58:34
