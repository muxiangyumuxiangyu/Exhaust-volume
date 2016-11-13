/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50551
Source Host           : localhost:3306
Source Database       : exhaust-volume

Target Server Type    : MYSQL
Target Server Version : 50551
File Encoding         : 65001

Date: 2016-11-13 14:44:44
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `answer`
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `key` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of answer
-- ----------------------------

-- ----------------------------
-- Table structure for `chapter`
-- ----------------------------
DROP TABLE IF EXISTS `chapter`;
CREATE TABLE `chapter` (
  `id` int(10) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `c_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter
-- ----------------------------

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------

-- ----------------------------
-- Table structure for `exam`
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `e_time` date DEFAULT NULL,
  `t_id` int(11) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam
-- ----------------------------

-- ----------------------------
-- Table structure for `examquestionalt`
-- ----------------------------
DROP TABLE IF EXISTS `examquestionalt`;
CREATE TABLE `examquestionalt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `e_id` int(11) DEFAULT NULL,
  `q_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of examquestionalt
-- ----------------------------

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `parent_id` int(10) DEFAULT NULL,
  `url` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '教师管理', null, null);
INSERT INTO `menu` VALUES ('2', '教师入职', '1', null);
INSERT INTO `menu` VALUES ('3', '教师离职', '1', null);
INSERT INTO `menu` VALUES ('4', '教师信息修改', '1', null);
INSERT INTO `menu` VALUES ('5', '教师信息查询', '1', null);
INSERT INTO `menu` VALUES ('6', '课程管理', null, null);
INSERT INTO `menu` VALUES ('7', '课程修改', '6', null);
INSERT INTO `menu` VALUES ('8', '课程取消', '6', null);
INSERT INTO `menu` VALUES ('9', '课程查询', '6', null);
INSERT INTO `menu` VALUES ('10', '章节管理', null, null);
INSERT INTO `menu` VALUES ('11', '章节录入', '10', null);
INSERT INTO `menu` VALUES ('12', '章节删除', '10', null);
INSERT INTO `menu` VALUES ('13', '章节修改', '10', null);
INSERT INTO `menu` VALUES ('14', '章节查询', '10', null);
INSERT INTO `menu` VALUES ('15', '查看试题记录', null, null);
INSERT INTO `menu` VALUES ('16', '生成试卷规则', null, null);
INSERT INTO `menu` VALUES ('17', '组卷', null, null);
INSERT INTO `menu` VALUES ('18', '自动组卷', '17', null);
INSERT INTO `menu` VALUES ('19', '手动组卷', '17', null);

-- ----------------------------
-- Table structure for `operation`
-- ----------------------------
DROP TABLE IF EXISTS `operation`;
CREATE TABLE `operation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operation
-- ----------------------------
INSERT INTO `operation` VALUES ('1', '增加');
INSERT INTO `operation` VALUES ('2', '修改');
INSERT INTO `operation` VALUES ('3', '删除');

-- ----------------------------
-- Table structure for `power`
-- ----------------------------
DROP TABLE IF EXISTS `power`;
CREATE TABLE `power` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `m_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of power
-- ----------------------------
INSERT INTO `power` VALUES ('1', '教师管理', '1');
INSERT INTO `power` VALUES ('2', '教师入职', '2');
INSERT INTO `power` VALUES ('3', '教师离职', '3');
INSERT INTO `power` VALUES ('4', '教师信息修改', '4');
INSERT INTO `power` VALUES ('5', '教师信息查询', '5');
INSERT INTO `power` VALUES ('6', '课程管理', '6');
INSERT INTO `power` VALUES ('7', '课程修改', '7');
INSERT INTO `power` VALUES ('8', '课程取消', '8');
INSERT INTO `power` VALUES ('9', '课程查询', '9');
INSERT INTO `power` VALUES ('10', '章节管理', '10');
INSERT INTO `power` VALUES ('11', '章节录入', '11');
INSERT INTO `power` VALUES ('12', '章节删除', '12');
INSERT INTO `power` VALUES ('13', '章节修改', '13');
INSERT INTO `power` VALUES ('14', '章节查询', '14');
INSERT INTO `power` VALUES ('15', '查看试题记录', '15');
INSERT INTO `power` VALUES ('16', '生成试卷规则', '16');
INSERT INTO `power` VALUES ('17', '组卷', '17');
INSERT INTO `power` VALUES ('18', '自动组卷', '18');
INSERT INTO `power` VALUES ('19', '手动组卷', '19');

-- ----------------------------
-- Table structure for `question`
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text,
  `chapter_id` int(11) DEFAULT NULL,
  `type_id` int(11) DEFAULT NULL,
  `level_id` int(11) DEFAULT NULL,
  `repeat` int(11) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `option` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------

-- ----------------------------
-- Table structure for `questionlevel`
-- ----------------------------
DROP TABLE IF EXISTS `questionlevel`;
CREATE TABLE `questionlevel` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of questionlevel
-- ----------------------------
INSERT INTO `questionlevel` VALUES ('1', '记忆');
INSERT INTO `questionlevel` VALUES ('2', '理解');
INSERT INTO `questionlevel` VALUES ('3', '简单应用');
INSERT INTO `questionlevel` VALUES ('4', '综合应用');

-- ----------------------------
-- Table structure for `questiontype`
-- ----------------------------
DROP TABLE IF EXISTS `questiontype`;
CREATE TABLE `questiontype` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of questiontype
-- ----------------------------
INSERT INTO `questiontype` VALUES ('1', '单项选择');
INSERT INTO `questiontype` VALUES ('2', '多项选择');
INSERT INTO `questiontype` VALUES ('3', '填空');
INSERT INTO `questiontype` VALUES ('4', '判断');
INSERT INTO `questiontype` VALUES ('5', '解释概念');
INSERT INTO `questiontype` VALUES ('6', '简答');
INSERT INTO `questiontype` VALUES ('7', '论述');
INSERT INTO `questiontype` VALUES ('8', '计算');
INSERT INTO `questiontype` VALUES ('9', '证明');
INSERT INTO `questiontype` VALUES ('10', '其他');

-- ----------------------------
-- Table structure for `recorder`
-- ----------------------------
DROP TABLE IF EXISTS `recorder`;
CREATE TABLE `recorder` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_id` int(11) DEFAULT NULL,
  `q_id` int(11) DEFAULT NULL,
  `description` text,
  `o_id` int(11) DEFAULT NULL,
  `time` date DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of recorder
-- ----------------------------

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '教务');
INSERT INTO `role` VALUES ('2', '教师');

-- ----------------------------
-- Table structure for `rolepoweralt`
-- ----------------------------
DROP TABLE IF EXISTS `rolepoweralt`;
CREATE TABLE `rolepoweralt` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `r_id` int(10) DEFAULT NULL,
  `p_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of rolepoweralt
-- ----------------------------
INSERT INTO `rolepoweralt` VALUES ('1', '1', '1');
INSERT INTO `rolepoweralt` VALUES ('2', '1', '2');
INSERT INTO `rolepoweralt` VALUES ('3', '1', '3');
INSERT INTO `rolepoweralt` VALUES ('4', '1', '4');
INSERT INTO `rolepoweralt` VALUES ('5', '1', '5');
INSERT INTO `rolepoweralt` VALUES ('6', '1', '6');
INSERT INTO `rolepoweralt` VALUES ('7', '1', '7');
INSERT INTO `rolepoweralt` VALUES ('8', '1', '8');
INSERT INTO `rolepoweralt` VALUES ('9', '1', '9');
INSERT INTO `rolepoweralt` VALUES ('10', '1', '10');
INSERT INTO `rolepoweralt` VALUES ('11', '1', '11');
INSERT INTO `rolepoweralt` VALUES ('12', '1', '12');
INSERT INTO `rolepoweralt` VALUES ('13', '1', '13');
INSERT INTO `rolepoweralt` VALUES ('14', '1', '14');
INSERT INTO `rolepoweralt` VALUES ('15', '1', '15');
INSERT INTO `rolepoweralt` VALUES ('16', '1', '16');
INSERT INTO `rolepoweralt` VALUES ('17', '2', '17');
INSERT INTO `rolepoweralt` VALUES ('18', '2', '18');
INSERT INTO `rolepoweralt` VALUES ('19', '2', '19');

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `email` varchar(30) DEFAULT NULL,
  `hiredate` date DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `photo` blob,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('1', 'admin', null, null, null, null, null, 'admin');

-- ----------------------------
-- Table structure for `teachercoursealt`
-- ----------------------------
DROP TABLE IF EXISTS `teachercoursealt`;
CREATE TABLE `teachercoursealt` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `t_id` int(10) DEFAULT NULL,
  `c_id` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teachercoursealt
-- ----------------------------

-- ----------------------------
-- Table structure for `teacherrolealt`
-- ----------------------------
DROP TABLE IF EXISTS `teacherrolealt`;
CREATE TABLE `teacherrolealt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `r_id` int(11) DEFAULT NULL,
  `t_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacherrolealt
-- ----------------------------
INSERT INTO `teacherrolealt` VALUES ('1', '1', '1');
