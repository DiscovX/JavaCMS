/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50528
Source Host           : localhost:3306
Source Database       : cms

Target Server Type    : MYSQL
Target Server Version : 50528
File Encoding         : 65001

Date: 2013-12-20 19:43:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `cms_user`
-- ----------------------------
DROP TABLE IF EXISTS `cms_user`;
CREATE TABLE `cms_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `role_id` bigint(20) NOT NULL DEFAULT '2',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of cms_user
-- ----------------------------
INSERT INTO `cms_user` VALUES ('2', 'UzxMx', '50c305dcb1c32bf5952dbc1a28c5e31a', '11301050@bjtu.edu.cn', '2');

-- ----------------------------
-- Table structure for `module`
-- ----------------------------
DROP TABLE IF EXISTS `module`;
CREATE TABLE `module` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `controllerName` varchar(255) NOT NULL,
  `package` varchar(255) NOT NULL,
  `is_enabled` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module
-- ----------------------------
INSERT INTO `module` VALUES ('1', 'user', 'module.user.UserController', 'module.user', '1');
INSERT INTO `module` VALUES ('2', 'navigation', 'module.navigation.NavigationController', 'module.navigation', '1');

-- ----------------------------
-- Table structure for `module_link`
-- ----------------------------
DROP TABLE IF EXISTS `module_link`;
CREATE TABLE `module_link` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `value` varchar(50) NOT NULL,
  `mid` bigint(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of module_link
-- ----------------------------
INSERT INTO `module_link` VALUES ('1', 'login', '/user/login', '1');
INSERT INTO `module_link` VALUES ('2', 'register', '/user/register', '1');
INSERT INTO `module_link` VALUES ('3', 'logout', '/user/logout', '1');

-- ----------------------------
-- Table structure for `navigation`
-- ----------------------------
DROP TABLE IF EXISTS `navigation`;
CREATE TABLE `navigation` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `module_link_id` int(11) NOT NULL,
  `parent_id` bigint(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of navigation
-- ----------------------------
INSERT INTO `navigation` VALUES ('1', '登陆', '1', '0');
INSERT INTO `navigation` VALUES ('2', '注册', '2', '0');

-- ----------------------------
-- Table structure for `page_template`
-- ----------------------------
DROP TABLE IF EXISTS `page_template`;
CREATE TABLE `page_template` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `enabled` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of page_template
-- ----------------------------
INSERT INTO `page_template` VALUES ('1', 'classic', '1');

-- ----------------------------
-- Table structure for `region`
-- ----------------------------
DROP TABLE IF EXISTS `region`;
CREATE TABLE `region` (
  `id` int(11) NOT NULL,
  `template_id` int(11) NOT NULL,
  `isMainRegion` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of region
-- ----------------------------
INSERT INTO `region` VALUES ('1', '1', '1', 'mainRegion');
INSERT INTO `region` VALUES ('2', '1', '0', 'header');
INSERT INTO `region` VALUES ('3', '1', '0', 'left');
INSERT INTO `region` VALUES ('4', '1', '0', 'right');
INSERT INTO `region` VALUES ('5', '1', '0', 'footer');

-- ----------------------------
-- Table structure for `region_modules`
-- ----------------------------
DROP TABLE IF EXISTS `region_modules`;
CREATE TABLE `region_modules` (
  `region_id` int(11) NOT NULL,
  `module_id` int(11) NOT NULL,
  PRIMARY KEY (`region_id`,`module_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of region_modules
-- ----------------------------
INSERT INTO `region_modules` VALUES ('2', '2');
INSERT INTO `region_modules` VALUES ('3', '1');

-- ----------------------------
-- Table structure for `resource`
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) NOT NULL,
  `method` int(11) NOT NULL COMMENT '0 : GET, 1 : POST, 2 : PUT, 3 : DELETE',
  `module_id` int(11) NOT NULL,
  `is_private` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('1', '/user/login', '0', '1', '0');
INSERT INTO `resource` VALUES ('2', '/user/login', '1', '1', '0');
INSERT INTO `resource` VALUES ('3', '/user/register', '0', '1', '0');
INSERT INTO `resource` VALUES ('4', '/user/register', '1', '1', '0');
INSERT INTO `resource` VALUES ('5', '/user/logout', '0', '1', '0');
INSERT INTO `resource` VALUES ('6', '/user/{id}', '0', '1', '1');

-- ----------------------------
-- Table structure for `resources_by_roles`
-- ----------------------------
DROP TABLE IF EXISTS `resources_by_roles`;
CREATE TABLE `resources_by_roles` (
  `id` int(20) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `role` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resources_by_roles
-- ----------------------------
INSERT INTO `resources_by_roles` VALUES ('1', '1', '1');
INSERT INTO `resources_by_roles` VALUES ('2', '1', '2');
INSERT INTO `resources_by_roles` VALUES ('3', '1', '3');
INSERT INTO `resources_by_roles` VALUES ('4', '2', '1');
INSERT INTO `resources_by_roles` VALUES ('5', '2', '2');
INSERT INTO `resources_by_roles` VALUES ('6', '2', '3');
INSERT INTO `resources_by_roles` VALUES ('7', '3', '1');
INSERT INTO `resources_by_roles` VALUES ('8', '3', '2');
INSERT INTO `resources_by_roles` VALUES ('9', '3', '3');
INSERT INTO `resources_by_roles` VALUES ('10', '4', '1');
INSERT INTO `resources_by_roles` VALUES ('11', '4', '2');
INSERT INTO `resources_by_roles` VALUES ('12', '4', '3');
INSERT INTO `resources_by_roles` VALUES ('13', '5', '1');
INSERT INTO `resources_by_roles` VALUES ('14', '5', '2');
INSERT INTO `resources_by_roles` VALUES ('15', '6', '1');

-- ----------------------------
-- Table structure for `role`
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', '管理员');
INSERT INTO `role` VALUES ('2', '普通用户');
INSERT INTO `role` VALUES ('3', '匿名用户');
