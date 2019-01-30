-- ----------------------------
-- Table structure for erp_user
-- ----------------------------
DROP TABLE IF EXISTS `erp_user`;
CREATE TABLE `erp_user` (
  `id` varchar(40) NOT NULL,
  `username` varchar(40) DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) DEFAULT NULL COMMENT '密码',
  `sex` char(1) DEFAULT NULL COMMENT '性别',
  `age` int(11) DEFAULT NULL COMMENT '年龄'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户表';

DROP TABLE IF EXISTS `dictionary_type`;
CREATE TABLE `dictionary_type` (
  `id` varchar(255) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `text` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `create_by` varchar(10) DEFAULT NULL,
  `update_by` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典类型表';

-- ----------------------------
-- Records of dictionary_type
-- ----------------------------
INSERT INTO `dictionary_type` VALUES ('1', 'sex', '0', '性别', '2019-01-15 09:23:40', '2019-01-15 09:23:44', 'chenyanwu', 'chenyanwu');

DROP TABLE IF EXISTS `dictionary_item`;
CREATE TABLE `dictionary_item` (
  `id` varchar(255) NOT NULL COMMENT '主键',
  `sort` int(11) DEFAULT NULL COMMENT '序号',
  `text` varchar(255) DEFAULT NULL COMMENT '字典内容',
  `value` varchar(255) DEFAULT NULL COMMENT '值',
  `type_id` varchar(255) DEFAULT NULL COMMENT '类型ID',
  `create_by` varchar(10) DEFAULT NULL,
  `update_by` varchar(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `update_date` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典类型条目表';

-- ----------------------------
-- Records of dictionary_item
-- ----------------------------
INSERT INTO `dictionary_item` VALUES ('1', '1', '男', '1', '2', 'chenyanwu', 'chenyanwu', '2019-01-15 09:25:38', '2019-01-15 09:25:38');
INSERT INTO `dictionary_item` VALUES ('2', '2', '女', '0', '2', 'hzx', '1', '2019-01-15 09:25:45', '2019-01-15 09:25:45');
