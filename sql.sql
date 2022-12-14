/*
SQLyog Ultimate v10.00 Beta1
MySQL - 8.0.31 : Database - order
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`order` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `order`;

/*Table structure for table `food` */

DROP TABLE IF EXISTS `food`;

CREATE TABLE `food` (
  `name` varchar(50) NOT NULL,
  `price` double unsigned NOT NULL DEFAULT '0',
  `availability` tinyint(1) NOT NULL DEFAULT '1',
  `ingredients` varchar(255) NOT NULL,
  `picture` varchar(255) NOT NULL COMMENT 'url of picture',
  `calorie` double NOT NULL DEFAULT '0',
  `type` varchar(50) NOT NULL,
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `restaurant` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `restaurant` (`restaurant`),
  CONSTRAINT `food_ibfk_1` FOREIGN KEY (`restaurant`) REFERENCES `restaurant` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `food` */

insert  into `food`(`name`,`price`,`availability`,`ingredients`,`picture`,`calorie`,`type`,`id`,`restaurant`) values ('Apple',39,1,'Apple 100%','https://tse1-mm.cn.bing.net/th/id/OIP-C.yB6BAfNWrimtDWxEqNhuigHaIE?w=160&h=180&c=7&r=0&o=5&dpr=1.6&pid=1.7',43,'Fruit',2,2),('Pear',41,1,'Pear 100%','https://tse1-mm.cn.bing.net/th/id/OIP-C.5reEZ0JJ2MQNos0tLB3zNwHaIc?w=170&h=193&c=7&r=0&o=5&dpr=1.6&pid=1.7',38,'Fruit',10,2);

/*Table structure for table `order` */

DROP TABLE IF EXISTS `order`;

CREATE TABLE `order` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `total_price` double unsigned NOT NULL DEFAULT '0',
  `status` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'Unpaid' COMMENT 'Unpaid Paid Cancel',
  `restaurant` int unsigned NOT NULL,
  `user` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `restaurant` (`restaurant`),
  KEY `user` (`user`),
  CONSTRAINT `order_ibfk_1` FOREIGN KEY (`restaurant`) REFERENCES `restaurant` (`id`),
  CONSTRAINT `order_ibfk_2` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `order` */

insert  into `order`(`id`,`time`,`total_price`,`status`,`restaurant`,`user`) values (1,'2001-10-13 06:58:46',39,'Cancel',2,1),(2,'2022-11-14 14:48:14',39,'Paid',2,1),(3,'2022-11-14 07:17:21',39,'Unpaid',2,1),(4,'2022-11-14 07:26:43',39,'Unpaid',2,1),(5,'1974-04-27 11:19:12',77,'Paid',2,1),(6,'2022-11-14 09:11:53',39,'Unpaid',2,1),(7,'2022-11-18 02:18:32',39,'Cancel',2,1);

/*Table structure for table `ordered_food` */

DROP TABLE IF EXISTS `ordered_food`;

CREATE TABLE `ordered_food` (
  `food` int unsigned NOT NULL,
  `order` int unsigned NOT NULL,
  `quantity` int unsigned NOT NULL DEFAULT '1',
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`id`),
  KEY `food` (`food`),
  KEY `order` (`order`),
  CONSTRAINT `ordered_food_ibfk_1` FOREIGN KEY (`food`) REFERENCES `food` (`id`),
  CONSTRAINT `ordered_food_ibfk_2` FOREIGN KEY (`order`) REFERENCES `order` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `ordered_food` */

insert  into `ordered_food`(`food`,`order`,`quantity`,`id`) values (2,4,1,1),(2,6,1,2),(2,7,1,3);

/*Table structure for table `restaurant` */

DROP TABLE IF EXISTS `restaurant`;

CREATE TABLE `restaurant` (
  `name` varchar(50) NOT NULL,
  `description` varchar(255) NOT NULL,
  `location` varchar(255) DEFAULT NULL COMMENT 'address',
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `merchant` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `merchant` (`merchant`),
  CONSTRAINT `restaurant_ibfk_1` FOREIGN KEY (`merchant`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `restaurant` */

insert  into `restaurant`(`name`,`description`,`location`,`id`,`merchant`) values ('Fruit Shop','Sell all kinds of fresh fruit','New York, USA',2,3);

/*Table structure for table `review` */

DROP TABLE IF EXISTS `review`;

CREATE TABLE `review` (
  `rating` int unsigned NOT NULL DEFAULT '10' COMMENT 'The score ranges from 1 to 10',
  `comments` varchar(500) NOT NULL,
  `restaurant` int unsigned NOT NULL,
  `time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `user` int unsigned DEFAULT NULL COMMENT 'If the user id is empty, it is an anonymous comment',
  PRIMARY KEY (`id`),
  KEY `restaurant` (`restaurant`),
  KEY `user` (`user`),
  CONSTRAINT `review_ibfk_1` FOREIGN KEY (`restaurant`) REFERENCES `restaurant` (`id`),
  CONSTRAINT `review_ibfk_2` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `review` */

insert  into `review`(`rating`,`comments`,`restaurant`,`time`,`id`,`user`) values (8,'nulla qui reprehenderit Duis',2,'2013-06-29 07:56:17',1,1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `dob` date NOT NULL COMMENT 'Day of Birth',
  `user_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '[None]',
  `password` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '123',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'The url of Icon',
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `payment_info` double NOT NULL DEFAULT '0' COMMENT 'The money user have.',
  `role` varchar(20) NOT NULL DEFAULT 'Customer' COMMENT 'Customer Merchant Administrator',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `user` */

insert  into `user`(`email`,`dob`,`user_name`,`password`,`icon`,`id`,`address`,`payment_info`,`role`) values ('1@1.com','2000-11-14','Bob','123','https://tse4-mm.cn.bing.net/th/id/OIP-C.oRG0873dtD9R0swgXDrKcwHaF7?w=196&h=180&c=7&r=0&o=5&dpr=1.6&pid=1.7',1,'BeiJing',1999.5,'Customer'),('2@2.com','2000-08-13','AAA','123','https://tse2-mm.cn.bing.net/th/id/OIP-C.dVF_0YlJOyItudWWz840DgAAAA?w=193&h=193&c=7&r=0&o=5&dpr=1.6&pid=1.7',2,'ChangSha',5000,'Administrator'),('3@3.com','1999-06-01','BB','123','https://tse3-mm.cn.bing.net/th/id/OIP-C.JP4p6VvD-8-eeKPUhEzmIwAAAA?w=194&h=194&c=7&r=0&o=5&dpr=1.6&pid=1.7',3,'ChongQing',999999,'Merchant'),('wE8PP0@nexnw','2022-11-13','ZlaCLmsqaHMIvV','123','https://tse4-mm.cn.bing.net/th/id/OIP-C.AilLq04ahoMQw9LnsJoezAAAAA?w=193&h=193&c=7&r=0&o=5&dpr=1.6&pid=1.7',4,'MIcOK5',0,'Customer'),('bBE@niu','2022-11-13','HVGKblLTeV','123','https://tse1-mm.cn.bing.net/th/id/OIP-C.PwtNfXeBNrLKHAW8IQvAUgAAAA?w=194&h=194&c=7&r=0&o=5&dpr=1.6&pid=1.7',5,'OibIo',1000,'Customer'),('fIO@tpl','2022-11-13','XOZHeF','123','https://tse1-mm.cn.bing.net/th/id/OIP-C._d1QwTRmpyITouzxR8jFHwAAAA?w=193&h=193&c=7&r=0&o=5&dpr=1.6&pid=1.7',6,'xQEi20G06',1000,'Customer'),('b.deczkvlm@qq.com','2001-01-01','312','123','https://tse4-mm.cn.bing.net/th/id/OIP-C.QS_Z0itcKet7wplcDkTGhwAAAA?w=193&h=193&c=7&r=0&o=5&dpr=1.6&pid=1.7',7,'asdf',71,'Customer'),('b.deczlm@qq.com','2001-01-01','sdf','123','https://tse3-mm.cn.bing.net/th/id/OIP-C.o0zOylFONQeGUwAqpMs8PAAAAA?w=193&h=193&c=7&r=0&o=5&dpr=1.6&pid=1.7',11,'asdf',71,'Customer'),('g.kooqvnomr@qq.com','2001-04-21','abc','123','https://tse1-mm.cn.bing.net/th/id/OIP-C.OVOc2AW9p18jOC1TiioUQQAAAA?w=193&h=193&c=7&r=0&o=5&dpr=1.6&pid=1.7',12,'bbbb',18,'Customer'),('g.kooomr@qq.com','2001-04-21','abc','123','https://tse3-mm.cn.bing.net/th/id/OIP-C.dVaJ-qOPLjsNE1zaAbmFowAAAA?w=193&h=193&c=7&r=0&o=5&dpr=1.6&pid=1.7',13,'ABC',1856745674,'Merchant'),('gmr@qq.com','2001-04-21','cba','consequat','https://tse3-mm.cn.bing.net/th/id/OIP-C.TmPN6XfgBJwDPw_HAD_umQAAAA?w=193&h=193&c=7&r=0&o=5&dpr=1.6&pid=1.7',14,'ABC',3421341,'Customer'),('123@123.com','2001-04-22','123','123','https://tse2-mm.cn.bing.net/th/id/OIP-C.gbd--YpMKzfojAYUyeaIFAAAAA?w=193&h=193&c=7&r=0&o=5&dpr=1.6&pid=1.7',15,'123',500,'Customer'),('4@4.com','2000-01-01','Four','123','https://tse1-mm.cn.bing.net/th/id/OIP-C.W5QsQ78Dvwy_cvhJ4L-HLwAAAA?w=193&h=193&c=7&r=0&o=5&dpr=1.6&pid=1.7',18,'China',500,'Customer');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
