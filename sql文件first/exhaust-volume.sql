/*
MySQL Data Transfer
Source Host: localhost
Source Database: exhaust-volume
Target Host: localhost
Target Database: exhaust-volume
Date: 2016/11/3 17:00:55
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for answer
-- ----------------------------
CREATE TABLE `answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `answer` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for chapter
-- ----------------------------
CREATE TABLE `chapter` (
  `chapter_id` int(10) NOT NULL,
  `chapter_name` varchar(20) DEFAULT NULL,
  `C_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`chapter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for course
-- ----------------------------
CREATE TABLE `course` (
  `C_id` int(10) NOT NULL AUTO_INCREMENT,
  `C_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`C_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for level
-- ----------------------------
CREATE TABLE `level` (
  `level_id` int(10) NOT NULL AUTO_INCREMENT,
  `levle_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`level_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for login
-- ----------------------------
CREATE TABLE `login` (
  `login_id` int(10) NOT NULL AUTO_INCREMENT,
  `loginName` varchar(20) NOT NULL,
  `loginPassword` varchar(20) NOT NULL,
  `role_id` int(10) DEFAULT NULL,
  `T_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
CREATE TABLE `menu` (
  `menu_id` int(10) NOT NULL DEFAULT '0',
  `menu_name` varchar(20) DEFAULT NULL,
  `parent_id` int(10) DEFAULT NULL,
  `URL` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for power
-- ----------------------------
CREATE TABLE `power` (
  `power_id` int(10) NOT NULL DEFAULT '0',
  `power_name` varchar(20) DEFAULT NULL,
  `menu_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`power_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for question
-- ----------------------------
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  `chapter_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `level_id` int(11) DEFAULT NULL,
  `answer_id` int(11) DEFAULT NULL,
  `repeat` int(11) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `option` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for record
-- ----------------------------
CREATE TABLE `record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_id` int(11) DEFAULT NULL,
  `q_id` int(11) DEFAULT NULL,
  `record` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
CREATE TABLE `role` (
  `role_id` int(10) NOT NULL DEFAULT '0',
  `role_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rolepower
-- ----------------------------
CREATE TABLE `rolepower` (
  `id` int(10) NOT NULL DEFAULT '0',
  `role_id` int(10) DEFAULT NULL,
  `power_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for teacher
-- ----------------------------
CREATE TABLE `teacher` (
  `T_id` int(10) NOT NULL,
  `T_name` varchar(20) NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  `hiredate` date DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `photo` blob,
  PRIMARY KEY (`T_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for teacher_course
-- ----------------------------
CREATE TABLE `teacher_course` (
  `id` int(10) NOT NULL DEFAULT '0',
  `T_id` int(10) DEFAULT NULL,
  `C_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for type
-- ----------------------------
CREATE TABLE `type` (
  `type_id` int(10) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records 
-- ----------------------------
