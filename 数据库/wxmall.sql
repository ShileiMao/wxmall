drop database wxMall;

create database wxMall;
use wxMall;

/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : wxmall

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2021-12-01 22:57:21
*/

SET FOREIGN_KEY_CHECKS=0;

DROP DATABASE IF EXISTS `wxMall`;
CREATE DATABASE `wxMall`;
use `wxMall`;

-- set global innodb_large_prefix=on;

-- set global innodb_file_per_table=on;

-- set global innodb_file_format=BARRACUDA;

-- ----------------------------
-- Table structure for cart_info
-- ----------------------------
DROP TABLE IF EXISTS `cart_info`;
CREATE TABLE `cart_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `count` int(10) NOT NULL DEFAULT '0' COMMENT '数量',
  `goodsId` bigint(10) NOT NULL DEFAULT '0' COMMENT '所属商品',
  `userId` bigint(10) NOT NULL DEFAULT '0' COMMENT '所属用户',
  `level` int(10) DEFAULT NULL COMMENT '用户等级',
  `createTime` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='购物车信息表';

-- ----------------------------
-- Records of cart_info
-- ----------------------------

-- ----------------------------
-- Table structure for comment_info
-- ----------------------------
DROP TABLE IF EXISTS `comment_info`;
CREATE TABLE `comment_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `content` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '评价内容',
  `goodsId` bigint(10) NOT NULL DEFAULT '0' COMMENT '所属商品',
  `userId` bigint(10) NOT NULL DEFAULT '0' COMMENT '评价人id',
  `level` int(10) DEFAULT NULL COMMENT '用户等级',
  `createTime` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='商品评价表';

-- ----------------------------
-- Records of comment_info
-- ----------------------------

-- ----------------------------
-- Table structure for goods_info
-- ----------------------------
DROP TABLE IF EXISTS `goods_info`;
CREATE TABLE `goods_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '商品名称',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '商品描述',
  `price` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '商品价格',
  `discount` double(10,2) DEFAULT '1.00' COMMENT '商品折扣',
  `sales` int(10) NOT NULL DEFAULT '0' COMMENT '商品销量',
  `hot` int(10) NOT NULL DEFAULT '0' COMMENT '商品点赞数',
  `recommend` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '否' COMMENT '是否是推荐',
  `count` int(10) NOT NULL DEFAULT '0' COMMENT '商品库存',
  `typeId` bigint(20) NOT NULL DEFAULT '0' COMMENT '所属类别',
  `fileIds` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '商品图片id，用英文逗号隔开',
  `userId` bigint(10) NOT NULL DEFAULT '0' COMMENT '评价人id',
  `level` int(10) DEFAULT NULL COMMENT '用户等级',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='商品详情表';

-- ----------------------------
-- Records of goods_info
-- ----------------------------
INSERT INTO `goods_info` VALUES ('1', '大豆家 方头奶奶鞋', '一脚蹬设计，方便日常的穿脱。', '100.00', '0.80', '30', '8', '是', '200', '1', '[12]', '1', null);
INSERT INTO `goods_info` VALUES ('2', '佳宝路转角水槽', '这是商品2', '300.00', '0.80', '16', '128', '是', '194', '2', '[13]', '1', null);
INSERT INTO `goods_info` VALUES ('3', 'LANCOME秋冬限量迷雾红管唇膏', '雾面质感暗红色管身，优雅而高贵。', '300.00', '0.80', '20', '2', '是', '200', '3', '[14]', '1', null);

-- ----------------------------
-- Table structure for groupbuy
-- ----------------------------
DROP TABLE IF EXISTS `groupbuy`;
CREATE TABLE `groupbuy` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `goodsId` bigint(20) DEFAULT NULL,
  `userId` bigint(20) DEFAULT NULL,
  `goodsName` varchar(255) DEFAULT NULL,
  `price` double(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of groupbuy
-- ----------------------------
INSERT INTO `groupbuy` VALUES ('5', '2', '5', '佳宝路转角水槽', '150.00');
INSERT INTO `groupbuy` VALUES ('6', '2', '7', '佳宝路转角水槽', '150.00');

-- ----------------------------
-- Table structure for nx_system_file_info
-- ----------------------------
DROP TABLE IF EXISTS `nx_system_file_info`;
CREATE TABLE `nx_system_file_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `originName` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '原始文件名',
  `fileName` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '存储文件名',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='文件信息表';

-- ----------------------------
-- Records of nx_system_file_info
-- ----------------------------
INSERT INTO `nx_system_file_info` VALUES ('12', '1606719414.jpg', '16067194141606719468634.jpg');
INSERT INTO `nx_system_file_info` VALUES ('13', '1606719435(1).jpg', '1606719435(1)1606719561267.jpg');
INSERT INTO `nx_system_file_info` VALUES ('14', '1606719449(1).jpg', '1606719449(1)1606719580696.jpg');

-- ----------------------------
-- Table structure for order_goods_rel
-- ----------------------------
DROP TABLE IF EXISTS `order_goods_rel`;
CREATE TABLE `order_goods_rel` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `orderId` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `goodsId` bigint(10) NOT NULL DEFAULT '0' COMMENT '所属商品',
  `count` int(11) DEFAULT NULL COMMENT '商品数量',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='订单商品关系映射表';

-- ----------------------------
-- Records of order_goods_rel
-- ----------------------------
INSERT INTO `order_goods_rel` VALUES ('1', '1', '1', '30');
INSERT INTO `order_goods_rel` VALUES ('2', '2', '2', '10');
INSERT INTO `order_goods_rel` VALUES ('3', '3', '3', '20');
INSERT INTO `order_goods_rel` VALUES ('4', '5', '2', '1');
INSERT INTO `order_goods_rel` VALUES ('6', '7', '2', '1');
INSERT INTO `order_goods_rel` VALUES ('7', '8', '2', '1');
INSERT INTO `order_goods_rel` VALUES ('8', '9', '2', '1');
INSERT INTO `order_goods_rel` VALUES ('9', '10', '2', '1');

-- ----------------------------
-- Table structure for order_info
-- ----------------------------
DROP TABLE IF EXISTS `order_info`;
CREATE TABLE `order_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `orderId` bigint(20) DEFAULT NULL COMMENT '订单ID',
  `totalPrice` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '总价格',
  `userId` bigint(10) NOT NULL DEFAULT '0' COMMENT '所属用户',
  `level` int(10) DEFAULT NULL COMMENT '用户等级',
  `linkAddress` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '联系地址',
  `linkPhone` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '联系电话',
  `linkMan` varchar(20) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '联系人',
  `createTime` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '创建时间',
  `status` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '未发货' COMMENT '订单状态',
  `notes` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='商品订单信息表';

-- ----------------------------
-- Records of order_info
-- ----------------------------
-- INSERT INTO `order_info` VALUES ('1', '111111', '480.00', '1', null, '安徽省合肥市高新区', '18812337865', 'admin', '2021-11-30 14:36:05', '完成');
-- INSERT INTO `order_info` VALUES ('2', '222222', '160.00', '2', null, '上海市浦东新区', '18812337865', '张三', '2021-11-30 14:36:05', '完成');
-- INSERT INTO `order_info` VALUES ('3', '333333', '320.00', '3', null, '上海市长宁区', '18812337865', '李四', '2021-11-30 14:36:05', '完成');
-- INSERT INTO `order_info` VALUES ('4', '32020120122288177', '150.00', '3', null, '上海市', '18843232356', '买家李四', '2021-12-01 22:28:19', '待付款');
-- INSERT INTO `order_info` VALUES ('5', '32020120122411293', '150.00', '3', null, '上海市', '18843232356', '买家李四', '2021-12-01 22:41:40', '已取消');
-- INSERT INTO `order_info` VALUES ('7', '32020120122498400', '150.00', '3', null, '上海市', '18843232356', '买家李四', '2021-12-01 22:49:47', '已取消');
-- INSERT INTO `order_info` VALUES ('8', '32020120122514686', '150.00', '3', null, '上海市', '18843232356', '买家李四', '2021-12-01 22:51:04', '已取消');
-- INSERT INTO `order_info` VALUES ('9', '32020120122525404', '240.00', '3', null, '上海市', '18843232356', '买家李四', '2021-12-01 22:52:50', '待付款');
-- INSERT INTO `order_info` VALUES ('10', '32020120122533526', '150.00', '3', null, '上海市', '18843232356', '买家李四', '2021-12-01 22:53:09', '待付款');

-- ----------------------------
-- Table structure for type_info
-- ----------------------------
DROP TABLE IF EXISTS `type_info`;
CREATE TABLE `type_info` (
  `id` bigint(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '类型名称',
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '类型描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='商品类别表';

-- ----------------------------
-- Records of type_info
-- ----------------------------
INSERT INTO `type_info` VALUES ('1', '穿衣好物', '穿衣好物');
INSERT INTO `type_info` VALUES ('2', '日常家用', '日常家用');
INSERT INTO `type_info` VALUES ('3', '美妆精品', '美妆精品');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '姓名',
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '密码',
  `nickName` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '昵称',
  `sex` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '性别',
  `age` int(10) DEFAULT NULL COMMENT '年龄',
  `birthday` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '生日',
  `phone` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '地址',
  `code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '编号',
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
  `cardId` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '身份证',
  `level` int(10) NOT NULL DEFAULT '1' COMMENT '权限等级',
  `account` double(10,2) DEFAULT NULL COMMENT '余额',
  `loginMethod` varchar(20) not null default 'pwd',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `uk_name` (`name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='管理员信息表';

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', '男', '22', null, '18843232356', '上海市', null, null, null, '1', '0.00', 'pwd');
INSERT INTO `user_info` VALUES ('2', '张三', 'e10adc3949ba59abbe56e057f20f883e', '卖家老张', '男', '22', null, '18843232356', '上海市', null, null, null, '2', '0.00', 'pwd');
INSERT INTO `user_info` VALUES ('3', '李四', 'e10adc3949ba59abbe56e057f20f883e', '买家李四', '男', '22', null, '18843232356', '上海市', null, null, null, '3', '10000.00', 'pwd');
INSERT INTO `user_info` VALUES ('5', '王五', 'e10adc3949ba59abbe56e057f20f883e', '买家王五', '男', '22', '', '18843232356', '上海市', '', '', '', '3', '10000.00', 'pwd');
INSERT INTO `user_info` VALUES ('7', '赵六', 'e10adc3949ba59abbe56e057f20f883e', '买家赵六', '男', '22', '', '18843232356', '上海市', '', '', '', '3', '10000.00', 'pwd');



-- -
-- post address

DROP TABLE IF EXISTS `user_addr`;
CREATE TABLE `user_addr`(
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(10) NOT NULL DEFAULT '0',
  `province` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `district` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `addr` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
);



DROP TABLE IF EXISTS `goods_address`;
CREATE TABLE `goods_address` (
 `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `goodsId` bigint(10) NOT NULL,
  `province` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `district` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `addr` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
);

insert into goods_address(goodsId, province, city, district, addr) values (1, '广东省', '佛山市', '禅城区', '广东省佛山市禅城区亲仁路21号7座首层5-7号铺(农商银行楼上2楼红棉苑内)');
insert into goods_address(goodsId, province, city, district, addr) values (2, '广东省', '佛山市', '禅城区', '广东省佛山市禅城区亲仁路21号7座首层5-7号铺(农商银行楼上2楼红棉苑内)');
insert into goods_address(goodsId, province, city, district, addr) values (2, '广东省', '佛山市', '禅城区', '广东省佛山市禅城区亲仁路21号7座首层5-7号铺(农商银行楼上2楼红棉苑内)');


DROP TABLE IF EXISTS `cart_notes`;
CREATE TABLE `cart_notes` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(10) NOT NULL,
  `notes` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE 
);


DROP TABLE IF EXISTS `store_config`;
CREATE TABLE `store_config` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
`name` varchar(30) COLLATE utf8mb4_unicode_ci COMMENT '配置名称',
`keyname` varchar(20) COLLATE utf8mb4_unicode_ci COMMENT '配置唯一字符串',
`value` varchar(255) COLLATE utf8mb4_unicode_ci COMMENT '配置属性值',
PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci ROW_FORMAT=DYNAMIC COMMENT='商店配置表';


INSERT INTO `store_config` (`name`, `keyname`, `value`) VALUES ('免邮起步价', 'FREE_DISPATCH_AMOUNT', '100');
INSERT INTO `store_config` (`name`, `keyname`, `value`) VALUES ('配送价格', 'DISPATCH_FEE', '23');
INSERT INTO `store_config` (`name`, `keyname`, `value`) VALUES ('配送距离', 'DISPATCH_DISTANCE', '100');
insert into `store_config` (`name`, `keyname`, `value`) values ('商店地址', 'STORE_ADDR', '广东省佛山市禅城区岭南大道43号');


DROP TABLE IF EXISTS `advertiser_info`;
CREATE TABLE `advertiser_info` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `content` longtext,
  `time` varchar(45) DEFAULT NULL,
  `scope` varchar(45) NOT NULL DEFAULT 'all',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;