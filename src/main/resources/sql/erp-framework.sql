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

 Date: 14/05/2019 10:11:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for erp_log
-- ----------------------------
DROP TABLE IF EXISTS `erp_log`;
CREATE TABLE `erp_log`  (
  `id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '编号',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求类型',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '日志标题',
  `host` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求主机',
  `uri` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求地址',
  `header` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求头',
  `http_method` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'HTTP方法',
  `class_method` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '请求方法',
  `params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '请求参数',
  `response_value` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '返回信息',
  `error_code` tinyint(4) NULL DEFAULT NULL COMMENT '错误码',
  `error_message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '错误信息',
  `start_time` datetime(0) NULL DEFAULT NULL COMMENT '操作开始时间',
  `end_time` datetime(0) NULL DEFAULT NULL COMMENT '操作结束时间',
  `execute_time` bigint(20) NULL DEFAULT NULL COMMENT '执行时间',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作用户',
  `operating_system` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作系统',
  `brower` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '操作浏览器',
  `is_deleted` tinyint(4) NULL DEFAULT NULL COMMENT '软删除'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for erp_menu
-- ----------------------------
DROP TABLE IF EXISTS `erp_menu`;
CREATE TABLE `erp_menu`  (
  `id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '菜单ID',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `parent_id` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单父ID',
  `level` tinyint(2) NULL DEFAULT NULL COMMENT '菜单层级',
  `target` tinyint(1) NULL DEFAULT NULL COMMENT '打开方式',
  `sort` smallint(6) NULL DEFAULT NULL COMMENT '排序',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `type` tinyint(1) NULL DEFAULT NULL COMMENT '菜单类型',
  `permission` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '权限标识',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改人'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of erp_menu
-- ----------------------------
INSERT INTO `erp_menu` VALUES ('3c7a59ad0169100000004dcfc7763697', '首页', '/home/index', '0', 1, NULL, 1, NULL, 0, '', '2019-03-02 11:37:25', '2019-03-02 11:37:25', 'admin', 'admin');
INSERT INTO `erp_menu` VALUES ('2e0734650169100000007d0aeb217952', '系统管理', '', '0', 1, NULL, 2, NULL, 0, '', '2019-02-27 16:16:58', '2019-02-27 16:16:58', 'admin', 'admin');
INSERT INTO `erp_menu` VALUES ('3c7f25590169100000004dcfc7763697', '菜单管理', '/erpmenu/index', '2e0734650169100000007d0aeb217952', 2, NULL, 3, NULL, 0, '', '2019-03-02 11:42:39', '2019-03-02 12:00:33', 'admin', 'admin');
INSERT INTO `erp_menu` VALUES ('3c7f833c0169100000004dcfc7763697', '角色管理', '/erprole/index', '2e0734650169100000007d0aeb217952', 2, NULL, 4, NULL, 0, '', '2019-03-02 11:43:03', '2019-03-02 11:44:17', 'admin', 'admin');
INSERT INTO `erp_menu` VALUES ('3c800f530169100000004dcfc7763697', '用户管理', '/erpuser/index', '2e0734650169100000007d0aeb217952', 2, NULL, 5, NULL, 0, '', '2019-03-02 11:43:39', '2019-03-02 11:43:39', 'admin', 'admin');
INSERT INTO `erp_menu` VALUES ('ce053506a0ef4796a825d5e28c2645c8', '通用功能', '', '0', 1, NULL, 6, NULL, 0, '', '2019-04-26 14:28:31', '2019-04-26 14:28:31', 'admin', 'admin');
INSERT INTO `erp_menu` VALUES ('74f35ad313f34de1ad884bf8b9035df6', '学生管理', '/erpstudent/index', 'ce053506a0ef4796a825d5e28c2645c8', 2, NULL, 7, NULL, 0, '', '2019-04-26 14:29:02', '2019-04-26 14:29:02', 'admin', 'admin');
INSERT INTO `erp_menu` VALUES ('62becb3a05b04ad39bbe682a9b227493', '图片上传', '/fileupload/photolist', 'ce053506a0ef4796a825d5e28c2645c8', 2, NULL, 8, NULL, 0, '', '2019-05-09 11:18:27', '2019-05-09 11:24:30', 'admin', 'admin');
INSERT INTO `erp_menu` VALUES ('f4eb1a663649432ab036032630118699', '视频上传', '/fileupload/videolist', 'ce053506a0ef4796a825d5e28c2645c8', 2, NULL, 9, NULL, 0, '', '2019-05-09 11:19:03', '2019-05-09 11:24:33', 'admin', 'admin');
INSERT INTO `erp_menu` VALUES ('2b81f76a8a93472cb5aebba4ec1890a2', '文件上传', '/upload/upload', 'ce053506a0ef4796a825d5e28c2645c8', 2, NULL, 10, NULL, 0, '', '2019-05-10 16:59:15', '2019-05-10 16:59:15', 'admin', 'admin');

-- ----------------------------
-- Table structure for erp_role
-- ----------------------------
DROP TABLE IF EXISTS `erp_role`;
CREATE TABLE `erp_role`  (
  `id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '角色ID',
  `name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色名称',
  `comment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改人'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of erp_role
-- ----------------------------
INSERT INTO `erp_role` VALUES ('13989fd40169100000000f8447ca6bc1', '管理员', '超级管理员', '2019-02-22 13:06:03', '2019-02-28 22:31:58', 'admin', 'admin');

-- ----------------------------
-- Table structure for erp_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `erp_role_menu`;
CREATE TABLE `erp_role_menu`  (
  `role_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色ID',
  `menu_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '菜单ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '角色菜单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of erp_role_menu
-- ----------------------------
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', '3c7a59ad0169100000004dcfc7763697');
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', '2e0734650169100000007d0aeb217952');
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', '3c7f25590169100000004dcfc7763697');
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', '3c7f833c0169100000004dcfc7763697');
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', '3c800f530169100000004dcfc7763697');
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', 'ce053506a0ef4796a825d5e28c2645c8');
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', '74f35ad313f34de1ad884bf8b9035df6');
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', '62becb3a05b04ad39bbe682a9b227493');
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', 'f4eb1a663649432ab036032630118699');
INSERT INTO `erp_role_menu` VALUES ('13989fd40169100000000f8447ca6bc1', '2b81f76a8a93472cb5aebba4ec1890a2');

-- ----------------------------
-- Table structure for erp_role_user
-- ----------------------------
DROP TABLE IF EXISTS `erp_role_user`;
CREATE TABLE `erp_role_user`  (
  `role_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '角色ID',
  `user_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of erp_role_user
-- ----------------------------
INSERT INTO `erp_role_user` VALUES ('8bc912218e974b34bf8099dd79d30c1e', '08d0601059d243bf8b01129bfd4c0fa6');
INSERT INTO `erp_role_user` VALUES ('13989fd40169100000000f8447ca6bc1', '1');
INSERT INTO `erp_role_user` VALUES ('8bc912218e974b34bf8099dd79d30c1e', '1');

-- ----------------------------
-- Table structure for erp_s_family_member
-- ----------------------------
DROP TABLE IF EXISTS `erp_s_family_member`;
CREATE TABLE `erp_s_family_member`  (
  `id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '注解',
  `stud_id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '学生ID',
  `relation` tinyint(4) NULL DEFAULT NULL COMMENT '成员关系',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '成员名称',
  `job` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '成员工作'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '学生家庭成员' ROW_FORMAT = Dynamic;


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

-- ----------------------------
-- Table structure for erp_user
-- ----------------------------
DROP TABLE IF EXISTS `erp_user`;
CREATE TABLE `erp_user`  (
  `id` char(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户ID',
  `login_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登陆账号',
  `name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名称',
  `password` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
  `phone` varchar(11) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号码',
  `email` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `salt` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '盐值',
  `locked` tinyint(1) NULL DEFAULT NULL COMMENT '是否被锁',
  `enabled` tinyint(1) NULL DEFAULT NULL COMMENT '是否启用',
  `create_date` datetime(0) NOT NULL COMMENT '创建时间',
  `update_date` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `create_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '创建人',
  `update_by` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '修改人'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of erp_user
-- ----------------------------
INSERT INTO `erp_user` VALUES ('1', 'admin', '超级管理员', 'f75eb17931ac512e8fc4880562e037a6c4e2858f', '13600368555', '13600368555@qq.com', '935127d3240dc63d', 0, 1, '2019-02-21 17:23:55', '2019-04-08 09:24:49', 'admin', 'admin');

SET FOREIGN_KEY_CHECKS = 1;
