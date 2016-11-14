/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50551
Source Host           : localhost:3306
Source Database       : exhaust-volume

Target Server Type    : MYSQL
Target Server Version : 50551
File Encoding         : 65001

Date: 2016-11-14 14:53:03
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of answer
-- ----------------------------
INSERT INTO `answer` VALUES ('1', 'A');
INSERT INTO `answer` VALUES ('2', 'AB');
INSERT INTO `answer` VALUES ('3', ' 编辑源程序   编译生成字节码   解释运行字节码');
INSERT INTO `answer` VALUES ('4', '正确');
INSERT INTO `answer` VALUES ('5', '异常表示程序运行过程中可能出现的非正常状态，运行时异常表示虚拟机的通常操作中可能遇到的异常，是一种常见运行错误。java编译器要求方法必须声明抛出可能发生的非运行时异常，但是并不要求必须声明抛出未被捕获的运行时异常');
INSERT INTO `answer` VALUES ('6', 'ArrayList和Vector都是使用数组方式存储数据，此数组元素数大于实际存储的数据以便增加和插入元素，它们都允许直接按序号索引元素，但是插入元素要涉及数组元素移动等内存操作，所以索引数据快而插入数据慢，Vector由于使用了synchronized方法（线程安全），通常性能上较ArrayList差，而LinkedList使用双向链表实现存储，按序号索引数据需要进行前向或后向遍历，但是插入数据时只需要记录本项的前后项即可，所以插入速度较快');
INSERT INTO `answer` VALUES ('7', 'final 用于声明属性，方法和类，分别表示属性不可变，方法不可覆盖，类不可继承。finally是异常处理语句结构的一部分，表示总是执行。finalize是Object类的一个方法，在垃圾收集器执行的时候会调用被回收对象的此方法，可以覆盖此方法提供垃圾收集时的其他资源回收，例如关闭文件等');
INSERT INTO `answer` VALUES ('8', '18');
INSERT INTO `answer` VALUES ('9', '假设有一个以上结点的huffman树中有度为1的结点A,其子结点为B,则我们可以将A.B合并为一个结点，则B以下的叶子结点的路径长度减小，树的带权路径长度减小。新树的WPL小于huffman树，这与huffman树的定义是相矛盾的。故得证');
INSERT INTO `answer` VALUES ('10', 'package test;   import java.util.ArrayList; import java.util.List;   public class ListOpreation {  public static void main(String[] args){   String s = \"ABCD\";    List<String> list = list(s,\"\");   System.out.println(list.size());   System.out.println(list);   }     public static List<String> list(String base,String buff){   List<String> result = new ArrayList<String>();   if(base.length()<=0){    result.add(buff);     System.out.println(result.toString()+\"&\");   }    for(int i=0;i<base.length();i++){    List<String> temp  =  list(new  StringBuilder(base).deleteCharAt(i).toString(),buff+base.charAt(i));              result.addAll(temp);    }    for(int i=0;i<base.length();i++){    System.out.println(new  StringBuilder(base).deleteCharAt(i).toString()+\"=\");     System.out.println(base.charAt(i)+\"*\");    System.out.println(buff+\"#\");   }    return result;      }  }');

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
-- Table structure for `examquestionsort`
-- ----------------------------
DROP TABLE IF EXISTS `examquestionsort`;
CREATE TABLE `examquestionsort` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `e_id` int(11) DEFAULT NULL,
  `q_id` int(11) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of examquestionsort
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
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of question
-- ----------------------------
INSERT INTO `question` VALUES ('1', '编译Java  Application 源程序文件将产生相应的字节码文件，这些字节码文件的扩展名为(  )', '1', '1', '1', null, null, ' A.    .java  B. .C++ C. .C  D. .CLASS');
INSERT INTO `question` VALUES ('2', '在Java  Applet程序用户自定义的Applet子类中，一般需要重载父类的(      )方法来完成一些画图操作。', '1', '2', '1', null, null, ' A.   start( ) B. end() C.do() D.open()');
INSERT INTO `question` VALUES ('3', '开发与运行Java程序需要经过的三个主要步骤为      、            \r\n 和      \r\n  。', '1', '3', '1', null, null, null);
INSERT INTO `question` VALUES ('4', '有数组定义：int   MyIntArray[ ] = { 10 , 20 , 30 , 40 , 50 , 60 , 70};   则执行以下几个语句后的输出结果是     120  \r\n有数组定义：int   MyIntArray[ ] = { 10 , 20 , 30 , 40 , 50 , 60 , 70};   则执行以下几个语句后的输出结果是     120  \r\n有数组定义：int   MyIntArray[ ] = { 10 , 20 , 30 , 40 , 50 , 60 , 70};   则执行以下几个语句后的输出结果是     120  \r\n设有数组定义：int   MyIntArray[ ] = { 10 , 20 , 30 , 40 , 50 , 60 , 70};   则执行以下几个语句后的输出结果是     120  \r\n设 x = 2 ，则表达式 ( x + + )／3 的值是     0   \r\n ', '1', '4', '1', null, null, null);
INSERT INTO `question` VALUES ('5', '运行时异常与一般异常有何异同？', '1', '5', '1', null, null, null);
INSERT INTO `question` VALUES ('6', '说出ArrayList,Vector, LinkedList的存储性能和特性', '1', '6', '1', null, null, null);
INSERT INTO `question` VALUES ('7', 'final, finally, finalize的区别。', '1', '7', '1', null, null, null);
INSERT INTO `question` VALUES ('8', '（1）-23+（-37）-（-12）\r\n+45\r\n（1）-23+（-37）-（-12）\r\n+45\r\n；', '1', '8', '1', null, null, null);
INSERT INTO `question` VALUES ('9', '证明:在结点数多于1的哈夫曼树中不存在度为1的结点', '1', '9', '1', null, null, null);
INSERT INTO `question` VALUES ('10', '写一个函数，例如：给你的 a b c 则输出 abc  acb  bac  bca  cab  cba', '1', '10', '1', null, null, null);

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
) ENGINE=InnoDB AUTO_INCREMENT=123457 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacher
-- ----------------------------
INSERT INTO `teacher` VALUES ('123456', 'admin', null, null, null, null, null, 'admin');

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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of teacherrolealt
-- ----------------------------
INSERT INTO `teacherrolealt` VALUES ('1', '1', '123456');
INSERT INTO `teacherrolealt` VALUES ('2', '2', '123456');
