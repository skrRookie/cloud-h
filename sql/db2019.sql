/*
Navicat MySQL Data Transfer

Source Server         : MyData
Source Server Version : 50642
Source Host           : localhost:3306
Source Database       : db2019

Target Server Type    : MYSQL
Target Server Version : 50642
File Encoding         : 65001

Date: 2020-07-19 16:24:10
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for payment
-- ----------------------------
DROP TABLE IF EXISTS `payment`;
CREATE TABLE `payment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `serial` varchar(200) DEFAULT NULL COMMENT '支付流水号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='支付表';
