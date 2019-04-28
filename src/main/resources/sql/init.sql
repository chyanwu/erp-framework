/*
 Navicat MySQL Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80014
 Source Host           : 127.0.0.1:3306
 Source Schema         : erp-framework

 Target Server Type    : MySQL
 Target Server Version : 80014
 File Encoding         : 65001

 Date: 04/04/2019 17:15:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for erp_menu
-- ----------------------------
DROP TABLE IF EXISTS `erp_menu`;
CREATE TABLE `erp_menu`  (
  `id` char(32) NOT NULL COMMENT '菜单ID',
  `name` varchar(40) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(500) DEFAULT NULL COMMENT '菜单URL',
  `parent_id` varchar(40) DEFAULT NULL COMMENT '菜单父ID',
  `level` tinyint(2) NULL DEFAULT NULL COMMENT '菜单层级',
  `target` tinyint(1) NULL DEFAULT NULL COMMENT '打开方式',
  `sort` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '菜单类型',
  `permission` varchar(200) DEFAULT NULL COMMENT '权限标识',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(20) NOT NULL COMMENT '创建人',
  `update_by` varchar(20) NULL DEFAULT NULL COMMENT '修改人'
) COMMENT = '菜单表';

-- ----------------------------
-- Records of erp_menu
-- ----------------------------
INSERT INTO `erp_menu` VALUES ('3c7a59ad0169100000004dcfc7763697', '首页', '/home/index', '0', 1, NULL, 1, NULL, 0, '', '2019-03-02 11:37:25', '2019-03-02 11:37:25', 'chenyanwu', 'chenyanwu');
INSERT INTO `erp_menu` VALUES ('2e0734650169100000007d0aeb217952', '系统管理', '', '0', 1, NULL, 2, NULL, 0, '', '2019-02-27 16:16:58', '2019-02-27 16:16:58', 'chenyanwu', 'chenyanwu');
INSERT INTO `erp_menu` VALUES ('3c7f25590169100000004dcfc7763697', '菜单管理', '/erpmenu/index', '2e0734650169100000007d0aeb217952', 2, NULL, 3, NULL, 0, '', '2019-03-02 11:42:39', '2019-03-02 12:00:33', 'chenyanwu', 'chenyanwu');
INSERT INTO `erp_menu` VALUES ('3c7f833c0169100000004dcfc7763697', '角色管理', '/erprole/index', '2e0734650169100000007d0aeb217952', 2, NULL, 4, NULL, 0, '', '2019-03-02 11:43:03', '2019-03-02 11:44:17', 'chenyanwu', 'chenyanwu');
INSERT INTO `erp_menu` VALUES ('3c800f530169100000004dcfc7763697', '用户管理', '/erpuser/index', '2e0734650169100000007d0aeb217952', 2, NULL, 5, NULL, 0, '', '2019-03-02 11:43:39', '2019-03-02 11:43:39', 'chenyanwu', 'chenyanwu');

-- ----------------------------
-- Table structure for erp_role
-- ----------------------------
DROP TABLE IF EXISTS `erp_role`;
CREATE TABLE `erp_role`  (
  `id` char(32) NOT NULL COMMENT '角色ID',
  `name` varchar(40) DEFAULT NULL COMMENT '角色名称',
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(20) NOT NULL COMMENT '创建人',
  `update_by` varchar(20) NULL DEFAULT NULL COMMENT '修改人'
) COMMENT = '角色表';

-- ----------------------------
-- Records of erp_role
-- ----------------------------
INSERT INTO `erp_role` VALUES ('13989fd40169100000000f8447ca6bc1', '管理员', '超级管理员', '2019-02-22 13:06:03', '2019-02-28 22:31:58', 'chenyanwu', 'chenyanwu');

-- ----------------------------
-- Table structure for erp_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `erp_role_menu`;
CREATE TABLE `erp_role_menu`  (
  `role_id` char(32) DEFAULT NULL COMMENT '角色ID',
  `menu_id` char(32) DEFAULT NULL COMMENT '菜单ID'
) COMMENT = '角色菜单表';

-- ----------------------------
-- Records of erp_role_menu
-- ----------------------------
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', '3c7a59ad0169100000004dcfc7763697');
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', '2e0734650169100000007d0aeb217952');
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', '3c7f25590169100000004dcfc7763697');
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', '3c7f833c0169100000004dcfc7763697');
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', '3c800f530169100000004dcfc7763697');

-- ----------------------------
-- Table structure for erp_role_user
-- ----------------------------
DROP TABLE IF EXISTS `erp_role_user`;
CREATE TABLE `erp_role_user`  (
  `role_id` char(32) DEFAULT NULL COMMENT '角色ID',
  `user_id` char(32) DEFAULT NULL COMMENT '用户ID'
) COMMENT = '用户角色表';

-- ----------------------------
-- Records of erp_role_user
-- ----------------------------
INSERT INTO `erp_role_user` VALUES ('13989fd40169100000000f8447ca6bc1', '1');

-- ----------------------------
-- Table structure for erp_user
-- ----------------------------
DROP TABLE IF EXISTS `erp_user`;
CREATE TABLE `erp_user`  (
  `id` char(32) NOT NULL COMMENT '用户ID',
  `login_name` varchar(30) DEFAULT NULL COMMENT '登陆账号',
  `name` varchar(30) DEFAULT NULL COMMENT '用户名称',
  `password` varchar(40) DEFAULT NULL COMMENT '用户密码',
  `phone` varchar(11) DEFAULT NULL COMMENT '手机号码',
  `email` varchar(30) DEFAULT NULL COMMENT '邮箱',
  `salt` varchar(40) DEFAULT NULL COMMENT '盐值',
  `locked` tinyint(1) NULL DEFAULT NULL COMMENT '是否被锁',
  `enabled` tinyint(1) NULL DEFAULT NULL COMMENT '是否启用',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(20) NOT NULL COMMENT '创建人',
  `update_by` varchar(20) NULL DEFAULT NULL COMMENT '修改人'
) COMMENT = '用户表';

-- ----------------------------
-- Records of erp_user
-- ----------------------------
INSERT INTO `erp_user` VALUES ('1', 'admin', '超级管理员', 'f75eb17931ac512e8fc4880562e037a6c4e2858f', '13600368555', '13600368555@qq.com', '935127d3240dc63d', 0, 1, '2019-02-21 17:23:55', '2019-03-10 01:51:35', 'chenyanwu', NULL);

-- ----------------------------
-- Table structure for erp_student
-- ----------------------------
DROP TABLE IF EXISTS `erp_student`;
CREATE TABLE `erp_student`  (
  `id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_student_excel
-- ----------------------------
DROP TABLE IF EXISTS `erp_student_excel`;
CREATE TABLE `erp_student_excel`  (
  `id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT 'id',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '姓名',
  `age` int(11) NULL DEFAULT NULL COMMENT '年龄',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `reason` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '错误原因',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '创建人',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
