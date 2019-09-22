/*
Navicat MySQL Data Transfer

Source Server         : cd4356
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : oa

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2019-09-22 17:33:35
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for claim_voucher
-- ----------------------------
DROP TABLE IF EXISTS `claim_voucher`;
CREATE TABLE `claim_voucher` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '报销单编号',
  `cause` varchar(100) DEFAULT NULL COMMENT '报销原由',
  `create_id` char(10) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `next_deal_id` char(10) DEFAULT NULL COMMENT '待处理人',
  `total_amount` double DEFAULT NULL COMMENT '总金额',
  `status` varchar(20) DEFAULT NULL COMMENT '处理状态',
  PRIMARY KEY (`id`),
  KEY `fk_cid` (`create_id`),
  KEY `fk_ndid` (`next_deal_id`),
  CONSTRAINT `fk_cid` FOREIGN KEY (`create_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `fk_ndid` FOREIGN KEY (`next_deal_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of claim_voucher
-- ----------------------------
INSERT INTO `claim_voucher` VALUES ('31', '出差', 'y1004', '2019-09-21 17:08:16', 'c1002', '780', '已审核');

-- ----------------------------
-- Table structure for claim_voucher_item
-- ----------------------------
DROP TABLE IF EXISTS `claim_voucher_item`;
CREATE TABLE `claim_voucher_item` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `claim_voucher_id` int(11) DEFAULT NULL COMMENT '报销单',
  `item` varchar(20) DEFAULT NULL COMMENT '费用类型',
  `amount` double DEFAULT NULL COMMENT '金额',
  `comment` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `fk_cvid` (`claim_voucher_id`),
  CONSTRAINT `fk_cvid` FOREIGN KEY (`claim_voucher_id`) REFERENCES `claim_voucher` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of claim_voucher_item
-- ----------------------------
INSERT INTO `claim_voucher_item` VALUES ('72', '31', '交通', '780', '来回高铁票');

-- ----------------------------
-- Table structure for deal_record
-- ----------------------------
DROP TABLE IF EXISTS `deal_record`;
CREATE TABLE `deal_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '报销单处理编号',
  `claim_voucher_id` int(11) DEFAULT NULL COMMENT '报销单',
  `deal_id` char(10) DEFAULT NULL COMMENT '处理人',
  `deal_time` datetime DEFAULT NULL COMMENT '处理时间',
  `deal_type` varchar(20) DEFAULT NULL COMMENT '处理类型',
  `deal_result` varchar(20) DEFAULT NULL COMMENT '处理结果',
  `comment` varchar(100) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`),
  KEY `fk_cv_id` (`claim_voucher_id`),
  KEY `fk_deal_id` (`deal_id`),
  CONSTRAINT `fk_cv_id` FOREIGN KEY (`claim_voucher_id`) REFERENCES `claim_voucher` (`id`),
  CONSTRAINT `fk_deal_id` FOREIGN KEY (`deal_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of deal_record
-- ----------------------------
INSERT INTO `deal_record` VALUES ('17', '31', 'y1004', '2019-09-21 17:08:16', '创建', '新创建', '无');
INSERT INTO `deal_record` VALUES ('18', '31', 'y1004', '2019-09-21 17:22:45', '提交', '已提交', '无');
INSERT INTO `deal_record` VALUES ('19', '31', 'y1003', '2019-09-21 17:23:39', '打回', '已打回', '');
INSERT INTO `deal_record` VALUES ('20', '31', 'y1004', '2019-09-21 17:24:00', '提交', '已提交', '无');
INSERT INTO `deal_record` VALUES ('21', '31', 'y1003', '2019-09-21 17:24:17', '通过', '已审核', '允许报销');

-- ----------------------------
-- Table structure for department
-- ----------------------------
DROP TABLE IF EXISTS `department`;
CREATE TABLE `department` (
  `id` char(10) NOT NULL COMMENT '部门编号',
  `name` varchar(20) DEFAULT NULL COMMENT '部门名称',
  `address` varchar(100) DEFAULT NULL COMMENT '部门地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of department
-- ----------------------------
INSERT INTO `department` VALUES ('10001', '总经理办公室', '梦幻大厦c1201');
INSERT INTO `department` VALUES ('10002', '财务部', '梦幻大厦a1103');
INSERT INTO `department` VALUES ('10003', '研发部', '蔡氏大夏a7001');
INSERT INTO `department` VALUES ('10004', '销售部', '永恒大夏b7005');

-- ----------------------------
-- Table structure for employee
-- ----------------------------
DROP TABLE IF EXISTS `employee`;
CREATE TABLE `employee` (
  `id` char(10) NOT NULL COMMENT '员工编号',
  `password` varchar(20) DEFAULT NULL COMMENT '密码',
  `name` varchar(20) DEFAULT NULL COMMENT '员工姓名',
  `department_id` char(10) DEFAULT NULL COMMENT '所属部门',
  `post` varchar(20) DEFAULT NULL COMMENT '职位',
  PRIMARY KEY (`id`),
  KEY `fk_did` (`department_id`),
  CONSTRAINT `fk_did` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of employee
-- ----------------------------
INSERT INTO `employee` VALUES ('c1002', '123456', '赵匡胤', '10002', '财务');
INSERT INTO `employee` VALUES ('x1005', '123456', '爱新觉罗.福临', '10004', '部门经理');
INSERT INTO `employee` VALUES ('y1003', '123456', '忽必烈', '10003', '部门经理');
INSERT INTO `employee` VALUES ('y1004', '123456', '朱元璋', '10003', '员工');
INSERT INTO `employee` VALUES ('z1001', '123456', '李世民', '10001', '总经理');

-- ----------------------------
-- Table structure for log
-- ----------------------------
DROP TABLE IF EXISTS `log`;
CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `employee_id` char(10) NOT NULL,
  `operation_time` datetime DEFAULT NULL,
  `operation` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_eid` (`employee_id`),
  CONSTRAINT `fk_eid` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of log
-- ----------------------------
INSERT INTO `log` VALUES ('1', 'y1004', '2019-09-22 16:51:13', 'login');
INSERT INTO `log` VALUES ('2', 'y1003', '2019-09-22 16:59:29', 'login');
