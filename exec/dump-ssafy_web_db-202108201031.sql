-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: 3.35.0.121    Database: ssafy_web_db
-- ------------------------------------------------------
-- Server version	5.7.35

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
-- Table structure for table `chatting`
--

DROP TABLE IF EXISTS `chatting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `chatting` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `contents` varchar(255) DEFAULT NULL,
  `time` datetime(6) DEFAULT NULL,
  `conference_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKejle61xskhs900r93jmkmtrke` (`conference_id`),
  KEY `FKq6nviowmr99ttn5pcxy1xcrrt` (`user_id`),
  CONSTRAINT `FKejle61xskhs900r93jmkmtrke` FOREIGN KEY (`conference_id`) REFERENCES `conference` (`id`),
  CONSTRAINT `FKq6nviowmr99ttn5pcxy1xcrrt` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chatting`
--

LOCK TABLES `chatting` WRITE;
/*!40000 ALTER TABLE `chatting` DISABLE KEYS */;
/*!40000 ALTER TABLE `chatting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comment`
--

DROP TABLE IF EXISTS `comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `time` datetime(6) DEFAULT NULL,
  `descript` varchar(255) DEFAULT NULL,
  `community_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbwy7a8hwdjqbm26qadw82ikqg` (`community_id`),
  KEY `FK8kcum44fvpupyw6f5baccx25c` (`user_id`),
  CONSTRAINT `FK8kcum44fvpupyw6f5baccx25c` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKbwy7a8hwdjqbm26qadw82ikqg` FOREIGN KEY (`community_id`) REFERENCES `community` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comment`
--

LOCK TABLES `comment` WRITE;
/*!40000 ALTER TABLE `comment` DISABLE KEYS */;
INSERT INTO `comment` VALUES (1,'2021-08-20 03:13:18.853000','z리네요',1,3),(2,'2021-08-20 03:14:31.656000','당장 수강하러 갑니다',1,5),(3,'2021-08-20 03:27:50.338000','인증이 없으면 뭐다?',3,5),(4,'2021-08-20 03:28:58.120000','넵넵! 기초부터 천천히 강의해 주셔서 따라가기 어렵지 않더라구요',2,6);
/*!40000 ALTER TABLE `comment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community`
--

DROP TABLE IF EXISTS `community`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `community` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `time` datetime(6) DEFAULT NULL,
  `descript` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `conference_category_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK91fm9h3lhk9flt2cq0fj5qbf0` (`conference_category_id`),
  KEY `FKfsfwlfb2ummfsb30q78wo6se0` (`user_id`),
  CONSTRAINT `FK91fm9h3lhk9flt2cq0fj5qbf0` FOREIGN KEY (`conference_category_id`) REFERENCES `conference_category` (`id`),
  CONSTRAINT `FKfsfwlfb2ummfsb30q78wo6se0` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community`
--

LOCK TABLES `community` WRITE;
/*!40000 ALTER TABLE `community` DISABLE KEYS */;
INSERT INTO `community` VALUES (1,'2021-08-20 03:12:55.959000','아이유처럼 노래부르기 강의 듣는데 강사님 완전 잘가르쳐주시네요!!','아이유처럼 노래부르기',1,6),(2,'2021-08-20 03:25:50.839000','알고리즘 완전 처음하는 수준인데 들어도 괜찮을까요?? 들어보신 분 후기 좀 부탁드립니다.','하반기 코테 대비 강좌 들어보신분 있나요?',4,5),(3,'2021-08-20 03:25:58.672000','따라만 하면 실버 탈출 강의 듣고 챌린저 찍었습니다.','따라만 하면 실버 탈출 강의 강추!',3,6),(4,'2021-08-20 03:32:52.943000','친구들이 음식을 먹고 구름을 떠다니는 느낌을 받았다고 하네요. ㅎㅎ 요리왕 비룡? 백x원? 비켜! 이게 짱이야','너도 밥 해먹을 수 있어! 후기입니다.',8,5),(5,'2021-08-20 03:33:16.893000','그림을 그리면서 무의식 중 가질 수 있는 나쁜 습관과 하기 쉬운 실수 등을 구체적으로 짚어주셔서 정말 좋았어요! 또한, 스스로 그림을 피드백하며 개선하는 법을 알 수 있었습니다.','스케치 강의',5,4),(6,'2021-08-20 03:39:30.348000','마법 쓰면서 마술인척 ㅋㅋ','해리포터처럼 마술쓰기 강좌 완전 사기네요',7,5);
/*!40000 ALTER TABLE `community` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conference`
--

DROP TABLE IF EXISTS `conference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conference` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `conference_day` varchar(255) DEFAULT NULL,
  `conference_time` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `is_free` bit(1) DEFAULT NULL,
  `price` int(11) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `conference_category_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs6799k7l5weo7u36umt4yvq5s` (`conference_category_id`),
  KEY `FK5ombydw0brnonv2d2gxum0hdk` (`user_id`),
  CONSTRAINT `FK5ombydw0brnonv2d2gxum0hdk` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKs6799k7l5weo7u36umt4yvq5s` FOREIGN KEY (`conference_category_id`) REFERENCES `conference_category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference`
--

LOCK TABLES `conference` WRITE;
/*!40000 ALTER TABLE `conference` DISABLE KEYS */;
INSERT INTO `conference` VALUES (1,NULL,NULL,'기출문제 풀이','\0',NULL,20000,'images/2552656454112112.png','하반기 코테 대비',4,5),(2,NULL,NULL,'꾀꼬리같은 목소리로 노래를 불러 보자!','',NULL,25000,'images/2552747621549668.jpg','아이유처럼 노래 부르기 ',1,6),(3,NULL,NULL,'피아노의 기초를 배우고 싶은사람들 컴컴~','\0',NULL,6000,'images/2552915916634560.png','피아노 기초 강의',9,6),(4,NULL,NULL,'자취생들이 하기 좋은 요리 강좌','\0',NULL,10000,'images/2552978792428236.png','너도 밥 해먹을 수 있어!',8,5),(5,NULL,NULL,'로꼬, 박재범, 그레이에게 배우면 당신도 할수있다','\0',NULL,99000,'images/2553008180137160.jpg','AOMG에게 배우는 랩 레슨',1,3),(6,NULL,NULL,'고급 마술을 배울 수 있습니다.','\0',NULL,25500,'images/2553109810308567.jpg','해리포터처럼 마술쓰기',7,6),(7,NULL,NULL,'롤 브론즈 아이언 탈출 시켜주는 강의','\0',NULL,10000,'images/2553445413322744.png','따라만 하면 실버탈출 가능',3,5),(8,NULL,NULL,'초보자도 쉽게 스케치를 배울 수 있다~!~!','\0',NULL,5000,'images/2553575938216978.png','초보자를 위한 스케치 기본 강의',5,6),(9,NULL,NULL,'소근육도 확실하게 행잉레그레이즈','\0',NULL,0,'images/2553647074074636.png','소확행! 헬스 기초 강좌',6,5),(10,NULL,NULL,'요즘 대세인 kpop 댄스를 배워보자','\0',NULL,10000,'images/2553991948285448.jpg','kpop 댄스 배우기',2,6),(11,NULL,NULL,'래더 2800 토스의 기본기 강좌 입니다.','\0',NULL,300,'images/2554199417558633.jpg','스타크래프트 프로토스 강의 ( 고수용 )',3,6),(12,NULL,NULL,'래더 S 테란 기본기 강좌입니다.  래더 B이하분들 대상으로 하는 강의입니다.','',NULL,0,'images/2555910328344560.jpg','스타크래프트 테란 입문용 강의',3,6),(13,NULL,NULL,'무지개 별장갑의 카트 라인 강의입니다.','\0',NULL,0,'images/2556610454146171.jpg','무지개 별장갑의 카트 라인 강의',3,6),(14,NULL,NULL,'10살 때부터 기타연주를 시작, 클래식 기','\0',NULL,0,'images/2556641990792729.jpg','정성하의 기타교실',9,3),(15,NULL,NULL,'10살 때부터 기타연주를 시작, 클래식 기타를 배우다가 아버지 정우창 씨가 핑거스타일 주법을 가르쳤고 이후 대가들의 비디오를 보면서 핑거스타일 주법을 독학했다. 아들이 핑거스타일 주법을 잘 소화하는 것을 본 아버지가 2006년부터 유튜브에 연주 동영상을 올리며 유명세를 타기 시작했다. 초기 사람들의 반응은 조그만 녀석이 대단하다, 신기하다 수준이었으나 점차로 그 음악적 가치를 인정받기 시작했다. 셀마기타를 시작으로 후원하는 사람도','\0',NULL,0,'images/2577469012721217.jpg','정성하의 기타교실2',9,3);
/*!40000 ALTER TABLE `conference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conference_category`
--

DROP TABLE IF EXISTS `conference_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conference_category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference_category`
--

LOCK TABLES `conference_category` WRITE;
/*!40000 ALTER TABLE `conference_category` DISABLE KEYS */;
INSERT INTO `conference_category` VALUES (1,'노래'),(2,'춤'),(3,'게임'),(4,'알고리즘'),(5,'그림'),(6,'헬스'),(7,'마술'),(8,'요리'),(9,'악기');
/*!40000 ALTER TABLE `conference_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conference_history`
--

DROP TABLE IF EXISTS `conference_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conference_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `action` smallint(6) NOT NULL,
  `inserted_time` datetime(6) DEFAULT NULL,
  `conference_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKda70ddbr5qsee4nr5e2h8yjhq` (`conference_id`),
  KEY `FKa7e25fsr5045birrnju69xkbs` (`user_id`),
  CONSTRAINT `FKa7e25fsr5045birrnju69xkbs` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKda70ddbr5qsee4nr5e2h8yjhq` FOREIGN KEY (`conference_id`) REFERENCES `conference` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference_history`
--

LOCK TABLES `conference_history` WRITE;
/*!40000 ALTER TABLE `conference_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `conference_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conference_recorded`
--

DROP TABLE IF EXISTS `conference_recorded`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `conference_recorded` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `end_time` datetime(6) DEFAULT NULL,
  `start_time` datetime(6) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `conference_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtkcc6lj20f0y5kbktr3qfdc9f` (`conference_id`),
  CONSTRAINT `FKtkcc6lj20f0y5kbktr3qfdc9f` FOREIGN KEY (`conference_id`) REFERENCES `conference` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conference_recorded`
--

LOCK TABLES `conference_recorded` WRITE;
/*!40000 ALTER TABLE `conference_recorded` DISABLE KEYS */;
/*!40000 ALTER TABLE `conference_recorded` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kurento`
--

DROP TABLE IF EXISTS `kurento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kurento` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `image_name` varchar(255) DEFAULT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjfdw3atlol34lg8q4ojvl976o` (`user_id`),
  CONSTRAINT `FKjfdw3atlol34lg8q4ojvl976o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kurento`
--

LOCK TABLES `kurento` WRITE;
/*!40000 ALTER TABLE `kurento` DISABLE KEYS */;
INSERT INTO `kurento` VALUES (1,'dpr.jpg','kurentos/2577607549332035.jpg',3),(2,'킹타크래프트.jpg','kurentos/2577778446718195.jpg',6),(3,'카트카트.jpg','kurentos/2578104169219061.jpg',6),(4,'algorithm.webp','kurentos/2578151382102104.webp',5),(5,'가상배경-3.png','kurentos/2578168006629967.png',6),(6,'aaa.png','kurentos/2578223420979223.png',4),(7,'aaa.png','kurentos/2578236747884090.png',4);
/*!40000 ALTER TABLE `kurento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `my_conference`
--

DROP TABLE IF EXISTS `my_conference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `my_conference` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `conference_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt951thotc68ei0ftog7hbamuj` (`conference_id`),
  KEY `FKdj5t04jiabkfimm5d8anym6f0` (`user_id`),
  CONSTRAINT `FKdj5t04jiabkfimm5d8anym6f0` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKt951thotc68ei0ftog7hbamuj` FOREIGN KEY (`conference_id`) REFERENCES `conference` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `my_conference`
--

LOCK TABLES `my_conference` WRITE;
/*!40000 ALTER TABLE `my_conference` DISABLE KEYS */;
/*!40000 ALTER TABLE `my_conference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phonenumber` varchar(255) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'yourasd_email','naasdme','$2a$10$ANvk0fLH26CEn8E7RfRsr.pJdVBcaNERJJvhk5O6rIW/n3s/MmG92','your_phoasdnenumber','ssaasdb'),(3,NULL,'최준원','$2a$10$Rou0pp9MR3zROR/y2MGxkOrwSnQAIuHxeeBSV9vt0iB.BzN20xMc2',NULL,'joon'),(4,NULL,'변준형','$2a$10$w85utc8xDNPDpvg0N6azRezv7Yci4RPcNSuIUZkryoFSLB2nnXZau',NULL,'bjh2754'),(5,NULL,'j','$2a$10$dXcuP6BjbPTx/WGlsQPcmO6vo2OEGVRlIA6fbRyQf30HiQG/XTT9a',NULL,'j'),(6,NULL,'배태호','$2a$10$BaXRuMF8VyTE.DzCs2Y/Vu6dsqAyNSwT8dE5BI388VJN0Tg5gmM6u',NULL,'tdj03063'),(7,NULL,'ssafy','$2a$10$KIE6aY9Gnic7UfXLZp8v2uu6lqHd8WwPW8Gq6ASeopvNJ65L49mp.',NULL,'ssafy5common');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_conference`
--

DROP TABLE IF EXISTS `user_conference`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_conference` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `conference_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKixwgtx9i00w7rblemcwmtgt54` (`conference_id`),
  KEY `FK31od1ca8dy6piesngukay5udr` (`user_id`),
  CONSTRAINT `FK31od1ca8dy6piesngukay5udr` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKixwgtx9i00w7rblemcwmtgt54` FOREIGN KEY (`conference_id`) REFERENCES `conference` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_conference`
--

LOCK TABLES `user_conference` WRITE;
/*!40000 ALTER TABLE `user_conference` DISABLE KEYS */;
INSERT INTO `user_conference` VALUES (1,1,5),(2,2,6),(3,3,6),(4,4,5),(5,5,3),(6,6,6),(7,7,5),(8,8,6),(9,9,5),(10,10,6),(11,11,6),(12,9,6),(13,11,4),(14,12,6),(15,13,6),(16,14,3),(17,12,3),(18,15,3),(19,12,4),(20,12,5);
/*!40000 ALTER TABLE `user_conference` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'ssafy_web_db'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-20 10:31:19
