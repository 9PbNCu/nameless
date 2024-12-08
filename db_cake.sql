
DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `admins` VALUES (1, 'admin', 'tuShOfiBrA8+br7ENrMS8A==');

DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '名称',
  `cover` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '封面',
  `image1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '细节图片1',
  `image2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '细节图片2',
  `price` int(0) NULL DEFAULT NULL COMMENT '价格',
  `intro` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `stock` int(0) NULL DEFAULT 0 COMMENT '库存',
  `type_id` int(0) NULL DEFAULT NULL COMMENT '分类',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `items`;
CREATE TABLE `items`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `price` int(0) NULL DEFAULT NULL COMMENT '购买时价格',
  `amount` int(0) NULL DEFAULT NULL COMMENT '购买数量',
  `order_id` int(0) NULL DEFAULT NULL COMMENT '订单id',
  `good_id` int(0) NULL DEFAULT NULL COMMENT '蛋糕id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `items` VALUES (1, 36, 1, 1, 12);
INSERT INTO `items` VALUES (2, 299, 1, 2, 7);

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `total` int(0) NOT NULL DEFAULT 0 COMMENT '总价',
  `amount` int(0) NOT NULL DEFAULT 0 COMMENT '商品总数',
  `status` tinyint(0) NOT NULL DEFAULT 1 COMMENT '订单状态(1未付款/2已付款/3已发货/4已完成)',
  `paytype` tinyint(0) NOT NULL DEFAULT 0 COMMENT '支付方式 (1微信/2支付宝/3货到付款)',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  `systime` timestamp(0) NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '下单时间',
  `user_id` int(0) NULL DEFAULT NULL COMMENT '下单用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `orders` VALUES (1, 36, 1, 2, 2, '123', '12345678901', '111111111', '2023-07-17 00:17:41', 1);
INSERT INTO `orders` VALUES (2, 299, 1, 2, 1, '123', '12345678901', '111111111', '2023-11-04 22:02:31', 1);

DROP TABLE IF EXISTS `tops`;
CREATE TABLE `tops`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `type` tinyint(0) NULL DEFAULT NULL COMMENT '推荐类型(1条幅/2大图/3小图)',
  `good_id` int(0) NULL DEFAULT NULL COMMENT '蛋糕id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '首页推荐商品' ROW_FORMAT = Dynamic;

INSERT INTO `tops` VALUES (1, 2, 1);
INSERT INTO `tops` VALUES (2, 2, 2);
INSERT INTO `tops` VALUES (3, 2, 5);
INSERT INTO `tops` VALUES (4, 2, 3);
INSERT INTO `tops` VALUES (5, 3, 10);
INSERT INTO `tops` VALUES (6, 3, 11);
INSERT INTO `tops` VALUES (7, 1, 7);
INSERT INTO `tops` VALUES (8, 3, 12);
INSERT INTO `tops` VALUES (9, 3, 13);
INSERT INTO `tops` VALUES (10, 2, 9);
INSERT INTO `tops` VALUES (11, 2, 6);
DROP TABLE IF EXISTS `types`;
CREATE TABLE `types`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `types` VALUES (1, '冰淇淋系列');
INSERT INTO `types` VALUES (2, '零食系列');
INSERT INTO `types` VALUES (3, '儿童系列');
INSERT INTO `types` VALUES (4, '法式系列');
INSERT INTO `types` VALUES (5, '经典系列');

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货电话',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '收货地址',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `users` VALUES (1, '123', 'yJOXpxCuYkm+zIQrRHPdpQ==', '123', '12345678901', '111111111');

SET FOREIGN_KEY_CHECKS = 1;
