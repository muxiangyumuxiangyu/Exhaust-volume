/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50551
Source Host           : localhost:3306
Source Database       : exhaust-volume

Target Server Type    : MYSQL
Target Server Version : 50551
File Encoding         : 65001

Date: 2016-12-08 08:36:38
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `answer`
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `solution` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of answer
-- ----------------------------
INSERT INTO `answer` VALUES ('1', 'A');
INSERT INTO `answer` VALUES ('2', 'D');
INSERT INTO `answer` VALUES ('3', 'C');
INSERT INTO `answer` VALUES ('4', 'A');
INSERT INTO `answer` VALUES ('5', 'D');
INSERT INTO `answer` VALUES ('6', 'B');
INSERT INTO `answer` VALUES ('7', 'D');
INSERT INTO `answer` VALUES ('8', 'D');

-- ----------------------------
-- Table structure for `chapter`
-- ----------------------------
DROP TABLE IF EXISTS `chapter`;
CREATE TABLE `chapter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `chapterOrder` int(11) DEFAULT NULL,
  `c_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of chapter
-- ----------------------------
INSERT INTO `chapter` VALUES ('1', '计算机软件基础知识', '1', '1');
INSERT INTO `chapter` VALUES ('2', 'Turbo C2.0', '2', '1');
INSERT INTO `chapter` VALUES ('3', '程序设计基础', '3', '1');
INSERT INTO `chapter` VALUES ('4', 'C语言概述', '4', '1');
INSERT INTO `chapter` VALUES ('5', '基本数据类型和表达式', '5', '1');
INSERT INTO `chapter` VALUES ('6', '简单的C语言程序设计', '6', '1');
INSERT INTO `chapter` VALUES ('7', '分支结构', '7', '1');
INSERT INTO `chapter` VALUES ('8', '循环控制', '8', '1');
INSERT INTO `chapter` VALUES ('9', '函数与变量类型', '9', '1');
INSERT INTO `chapter` VALUES ('10', '数组', '10', '1');
INSERT INTO `chapter` VALUES ('11', '指针', '11', '1');
INSERT INTO `chapter` VALUES ('12', '结构体与共用体', '12', '1');
INSERT INTO `chapter` VALUES ('13', '文件', '13', '1');
INSERT INTO `chapter` VALUES ('14', '开始学习C++', '1', '2');
INSERT INTO `chapter` VALUES ('15', '数据类型运算符和表达式', '2', '2');
INSERT INTO `chapter` VALUES ('16', 'string、vector、数组和指针', '3', '2');
INSERT INTO `chapter` VALUES ('17', '程序流程控制', '4', '2');
INSERT INTO `chapter` VALUES ('18', '函数', '5', '2');
INSERT INTO `chapter` VALUES ('19', '类和对象', '6', '2');
INSERT INTO `chapter` VALUES ('20', '面向对象程序设计', '7', '2');
INSERT INTO `chapter` VALUES ('21', 'RTTI与异常处理', '8', '2');
INSERT INTO `chapter` VALUES ('22', 'Java概述', '1', '3');
INSERT INTO `chapter` VALUES ('23', '数组', '2', '3');
INSERT INTO `chapter` VALUES ('24', '类和对象', '3', '3');
INSERT INTO `chapter` VALUES ('25', '类的封装', '4', '3');
INSERT INTO `chapter` VALUES ('26', '类的继承', '5', '3');
INSERT INTO `chapter` VALUES ('27', '多态', '6', '3');
INSERT INTO `chapter` VALUES ('28', '包装器类', '7', '3');
INSERT INTO `chapter` VALUES ('29', '内部类', '8', '3');
INSERT INTO `chapter` VALUES ('30', '枚举', '9', '3');
INSERT INTO `chapter` VALUES ('31', '异常和断言', '10', '3');
INSERT INTO `chapter` VALUES ('32', 'WEB开发基础', '1', '4');
INSERT INTO `chapter` VALUES ('33', 'HTML基础', '2', '4');
INSERT INTO `chapter` VALUES ('34', '构建页面', '3', '4');
INSERT INTO `chapter` VALUES ('35', 'HTML语法小结', '4', '4');
INSERT INTO `chapter` VALUES ('36', 'CSS基础', '5', '4');
INSERT INTO `chapter` VALUES ('37', 'CSS基本样式修饰', '6', '4');
INSERT INTO `chapter` VALUES ('38', 'CSS盒子模型', '7', '4');
INSERT INTO `chapter` VALUES ('39', '网页布局', '8', '4');

-- ----------------------------
-- Table structure for `course`
-- ----------------------------
DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of course
-- ----------------------------
INSERT INTO `course` VALUES ('1', 'C');
INSERT INTO `course` VALUES ('2', 'C++');
INSERT INTO `course` VALUES ('3', 'Java');
INSERT INTO `course` VALUES ('4', 'WEB');
INSERT INTO `course` VALUES ('5', 'WEB2');
INSERT INTO `course` VALUES ('6', '数据库');
INSERT INTO `course` VALUES ('7', '计算机组成原理');
INSERT INTO `course` VALUES ('8', '网络原理');
INSERT INTO `course` VALUES ('9', '计算机导论');

-- ----------------------------
-- Table structure for `exam`
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `e_time` varchar(50) DEFAULT NULL,
  `t_id` int(11) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exam
-- ----------------------------
INSERT INTO `exam` VALUES ('1', '2016-12-08 08:34:41', '123456', '试卷2016-12-08 08:34:41');
INSERT INTO `exam` VALUES ('2', '2016-12-08 08:34:43', '123456', '试卷2016-12-08 08:34:43');

-- ----------------------------
-- Table structure for `menu`
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `url` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('1', '教师管理', null, null);
INSERT INTO `menu` VALUES ('2', '教师入职', '1', 'Teachermanage/toAdd');
INSERT INTO `menu` VALUES ('3', '教师离职', '1', null);
INSERT INTO `menu` VALUES ('4', '教师信息修改', '1', null);
INSERT INTO `menu` VALUES ('5', '教师信息查询', '1', 'Teachermanage/list');
INSERT INTO `menu` VALUES ('6', '课程管理', null, null);
INSERT INTO `menu` VALUES ('7', '课程修改', '6', 'course/toEdit');
INSERT INTO `menu` VALUES ('8', '删除课程', '6', 'course/toDelete');
INSERT INTO `menu` VALUES ('9', '课程查询', '6', 'course/list');
INSERT INTO `menu` VALUES ('10', '增加课程', '6', 'course/toAdd');
INSERT INTO `menu` VALUES ('11', '章节管理', null, null);
INSERT INTO `menu` VALUES ('12', '章节删除', '11', 'chapter/toDelete');
INSERT INTO `menu` VALUES ('13', '章节修改', '11', 'chapter/toEdit');
INSERT INTO `menu` VALUES ('14', '章节查询', '11', 'chapter/list');
INSERT INTO `menu` VALUES ('15', '章节录入', '11', 'chapter/toAdd');
INSERT INTO `menu` VALUES ('16', '试题记录', null, null);
INSERT INTO `menu` VALUES ('17', '查看试题记录', '16', null);
INSERT INTO `menu` VALUES ('18', '试卷规则', null, null);
INSERT INTO `menu` VALUES ('19', '设置试卷规则', '18', null);
INSERT INTO `menu` VALUES ('20', '组卷', null, null);
INSERT INTO `menu` VALUES ('21', '自动组卷', '20', 'exam/choose.jsp');
INSERT INTO `menu` VALUES ('22', '手动组卷', '20', 'exam/zujuan_kecheng_hand.jsp');
INSERT INTO `menu` VALUES ('23', '我的试卷', '20', 'exam/listExam');

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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `m_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

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
INSERT INTO `power` VALUES ('8', '删除课程', '8');
INSERT INTO `power` VALUES ('9', '课程查询', '9');
INSERT INTO `power` VALUES ('10', '增加课程', '10');
INSERT INTO `power` VALUES ('11', '章节管理', '11');
INSERT INTO `power` VALUES ('12', '章节删除', '12');
INSERT INTO `power` VALUES ('13', '章节修改', '13');
INSERT INTO `power` VALUES ('14', '章节查询', '14');
INSERT INTO `power` VALUES ('15', '章节录入', '15');
INSERT INTO `power` VALUES ('16', '试题记录', '16');
INSERT INTO `power` VALUES ('17', '查看试题记录', '17');
INSERT INTO `power` VALUES ('18', '试卷规则', '18');
INSERT INTO `power` VALUES ('19', '设置试卷规则', '19');
INSERT INTO `power` VALUES ('20', '组卷', '20');
INSERT INTO `power` VALUES ('21', '自动组卷', '21');
INSERT INTO `power` VALUES ('22', '手动组卷', '22');
INSERT INTO `power` VALUES ('23', '我的试卷', '23');

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
  `repeats` int(11) DEFAULT NULL,
  `flag` int(11) DEFAULT NULL,
  `A` text,
  `B` text,
  `C` text,
  `D` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('1', '（）是构成C语言程序的基本单位<br />', '3', '1', '1', '0', '0', 'A.函数', 'B.过程', 'C.子程序<br />', 'D.子例程<br />');
INSERT INTO `question` VALUES ('2', '下列定义变量的语句中错误的是（）<br />', '9', '1', '1', '0', '0', 'A.int_int<br />', 'B.double int_<br />', 'C.char For<br />', 'D.float US$<br />');
INSERT INTO `question` VALUES ('3', '以下不合法的用户标识符是（）<br />', '9', '1', '1', '0', '0', 'A.j2_KEY<br />', 'B.Double<br />', 'C.4d', 'D._8_');
INSERT INTO `question` VALUES ('4', '以下4组用户定义标识符中，全部合法的一组是（）<br />', '9', '1', '1', '0', '0', 'A._main enclude sin<br />', 'B.If -max turbo<br />', 'C.txt REAL 3COM<br />', 'D.int k_2_001 ???<br />');
INSERT INTO `question` VALUES ('5', '以下不能定义为用户标识符的是（）<br />', '9', '1', '1', '0', '0', 'A.scanf<br />', 'B.Void', 'C._3com_', 'D.int');
INSERT INTO `question` VALUES ('6', 'C语言中最简单的数据类型包括（）<br />', '9', '1', '1', '0', '0', 'A.整型、实型、逻辑型<br />', 'B.整型、实型、字符型<br />', 'C.整型、字符型、逻辑型<br />', 'D.整型、实型、逻辑型、字符型<br />');
INSERT INTO `question` VALUES ('7', '下列选项中，合法的C语言关键字是（）<br />', '9', '1', '1', '0', '0', 'A.VAR', 'B.cher', 'C.integer', 'D.default');
INSERT INTO `question` VALUES ('8', '下列叙述中正确的是（）<br />', '9', '1', '1', '0', '0', 'A.C语言中既有逻辑类型也有集合类型<br />', 'B.C语言中没有逻辑类型但有集合类型<br />', 'C.C语言中有逻辑类型但没有集合类型<br />', 'D.C语言中既没有逻辑类型也没有集合类型<br />');

-- ----------------------------
-- Table structure for `questionlevel`
-- ----------------------------
DROP TABLE IF EXISTS `questionlevel`;
CREATE TABLE `questionlevel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `r_id` int(11) DEFAULT NULL,
  `p_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

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
INSERT INTO `rolepoweralt` VALUES ('17', '1', '17');
INSERT INTO `rolepoweralt` VALUES ('18', '1', '18');
INSERT INTO `rolepoweralt` VALUES ('19', '1', '19');
INSERT INTO `rolepoweralt` VALUES ('20', '2', '20');
INSERT INTO `rolepoweralt` VALUES ('21', '2', '21');
INSERT INTO `rolepoweralt` VALUES ('22', '2', '22');
INSERT INTO `rolepoweralt` VALUES ('23', '2', '23');

-- ----------------------------
-- Table structure for `sort`
-- ----------------------------
DROP TABLE IF EXISTS `sort`;
CREATE TABLE `sort` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sequence` int(11) DEFAULT NULL,
  `e_id` int(11) DEFAULT NULL,
  `q_id` int(11) DEFAULT NULL,
  `t_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sort
-- ----------------------------
INSERT INTO `sort` VALUES ('3', '1', '2', '1', '1');

-- ----------------------------
-- Table structure for `teacher`
-- ----------------------------
DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `id` int(11) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `email` varchar(30) DEFAULT NULL,
  `hiredate` date DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(30) DEFAULT NULL,
  `photo` blob,
  `password` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('123456', 'admin', null, null, null, null, null, 'admin');

-- ----------------------------
-- Table structure for `teachercoursealt`
-- ----------------------------
DROP TABLE IF EXISTS `teachercoursealt`;
CREATE TABLE `teachercoursealt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `t_id` int(11) DEFAULT NULL,
  `c_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teachercoursealt
-- ----------------------------
INSERT INTO `teachercoursealt` VALUES ('1', '123456', '1');
INSERT INTO `teachercoursealt` VALUES ('2', '123456', '2');

-- ----------------------------
-- Table structure for `teacherrolealt`
-- ----------------------------
DROP TABLE IF EXISTS `teacherrolealt`;
CREATE TABLE `teacherrolealt` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `r_id` int(11) DEFAULT NULL,
  `t_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacherrolealt
-- ----------------------------
INSERT INTO `teacherrolealt` VALUES ('1', '1', '123456');
INSERT INTO `teacherrolealt` VALUES ('2', '2', '123456');
