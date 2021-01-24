/*
 Navicat Premium Data Transfer

 Source Server         : 20200501
 Source Server Type    : MySQL
 Source Server Version : 50730
 Source Host           : localhost:3306
 Source Schema         : polygon

 Target Server Type    : MySQL
 Target Server Version : 50730
 File Encoding         : 65001

 Date: 24/01/2021 22:46:53
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for poly_approve
-- ----------------------------
DROP TABLE IF EXISTS `poly_approve`;
CREATE TABLE `poly_approve`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `business_no` bigint(10) UNSIGNED NULL DEFAULT NULL COMMENT '具体业务的编号',
  `flow_node_no` bigint(10) UNSIGNED NOT NULL,
  `approve_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `approve_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `approve_info` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `approve_date` datetime(0) NULL DEFAULT NULL,
  `flow_no` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `approve_state` tinyint(1) NOT NULL COMMENT '未审批：0 同意：1 驳回：-1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for poly_archive
-- ----------------------------
DROP TABLE IF EXISTS `poly_archive`;
CREATE TABLE `poly_archive`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `file_no` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `old_file_no` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_name` varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `file_path` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `file_type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `edit_dept` varchar(8) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `edit_person` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `issue_date` datetime(0) NOT NULL,
  `status` varchar(6) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `is_new` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for poly_department
-- ----------------------------
DROP TABLE IF EXISTS `poly_department`;
CREATE TABLE `poly_department`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for poly_employee
-- ----------------------------
DROP TABLE IF EXISTS `poly_employee`;
CREATE TABLE `poly_employee`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `mail` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `department_id` bigint(10) UNSIGNED NOT NULL,
  `enabled` tinyint(1) UNSIGNED NOT NULL DEFAULT 1,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for poly_employee_role
-- ----------------------------
DROP TABLE IF EXISTS `poly_employee_role`;
CREATE TABLE `poly_employee_role`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `eid` bigint(10) UNSIGNED NOT NULL,
  `rid` bigint(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 62 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for poly_flow
-- ----------------------------
DROP TABLE IF EXISTS `poly_flow`;
CREATE TABLE `poly_flow`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `flow_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `flow_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `remark` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for poly_flow_line
-- ----------------------------
DROP TABLE IF EXISTS `poly_flow_line`;
CREATE TABLE `poly_flow_line`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `flow_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `pre_node` bigint(10) UNSIGNED NOT NULL,
  `next_node` bigint(10) UNSIGNED NULL DEFAULT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for poly_flow_node
-- ----------------------------
DROP TABLE IF EXISTS `poly_flow_node`;
CREATE TABLE `poly_flow_node`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `flow_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `flow_node_name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `approve_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `approve_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for poly_leave
-- ----------------------------
DROP TABLE IF EXISTS `poly_leave`;
CREATE TABLE `poly_leave`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `employee_no` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `employee_name` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `reason` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `start_date` datetime(0) NOT NULL,
  `end_date` datetime(0) NOT NULL,
  `flow_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `flow_state` int(1) UNSIGNED ZEROFILL NULL DEFAULT NULL COMMENT '审批中：0，审批结束：1',
  `current_node` int(10) UNSIGNED NOT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 20 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for poly_menu
-- ----------------------------
DROP TABLE IF EXISTS `poly_menu`;
CREATE TABLE `poly_menu`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `path` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `component` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `icon` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `parent_id` bigint(10) UNSIGNED ZEROFILL NULL DEFAULT NULL,
  `gmt_create` datetime(0) NOT NULL,
  `gmt_modified` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for poly_operation
-- ----------------------------
DROP TABLE IF EXISTS `poly_operation`;
CREATE TABLE `poly_operation`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `name_zh` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `url` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `gmt_create` datetime(0) NOT NULL,
  `gmt_modified` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for poly_permission
-- ----------------------------
DROP TABLE IF EXISTS `poly_permission`;
CREATE TABLE `poly_permission`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pid` bigint(10) UNSIGNED NOT NULL COMMENT '资源id',
  `type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '资源类型',
  `gmt_create` datetime(0) NOT NULL,
  `gmt_modified` datetime(0) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for poly_role
-- ----------------------------
DROP TABLE IF EXISTS `poly_role`;
CREATE TABLE `poly_role`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `name_zh` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `gmt_create` datetime(0) NULL DEFAULT NULL,
  `gmt_modified` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

-- ----------------------------
-- Table structure for poly_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `poly_role_permission`;
CREATE TABLE `poly_role_permission`  (
  `id` bigint(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `rid` bigint(10) UNSIGNED NOT NULL,
  `pid` bigint(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`id`, `rid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 51 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = COMPACT;

SET FOREIGN_KEY_CHECKS = 1;
