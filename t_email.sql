/*
 Navicat Premium Data Transfer

 Source Server         : local_3306
 Source Server Type    : MySQL
 Source Server Version : 80036 (8.0.36)
 Source Host           : localhost:3306
 Source Schema         : ymail

 Target Server Type    : MySQL
 Target Server Version : 80036 (8.0.36)
 File Encoding         : 65001

 Date: 02/03/2024 19:18:15
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_email
-- ----------------------------
DROP TABLE IF EXISTS `t_email`;
CREATE TABLE `t_email`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '邮件id',
  `master` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '邮件所有者',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '发送者昵称',
  `from` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '发送人',
  `to` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '接收人',
  `subject` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '发送主题',
  `plain_text` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'text',
  `html_text` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'html',
  `status` int NULL DEFAULT NULL COMMENT '状态:-3准备发送，-2发送失败，-1发送成功，1未读，2已读',
  `group` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '群组',
  `attachment_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '附件id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更改时间',
  `del_flag` int NULL DEFAULT NULL COMMENT '删除标识',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `from`(`from` ASC) USING BTREE,
  INDEX `to`(`to` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_email
-- ----------------------------
INSERT INTO `t_email` VALUES ('17587249239470716929', 'yexing195@163.com', 'yexing', 'yexing@pmail.slovety.top', 'yexing195@163.com', '你好', 'hello world', '<p><span style=\"color: rgb(225, 60, 57);\">hello </span><span style=\"color: rgb(225, 60, 57);\"><strong>wor</strong></span><strong>ld</strong></p>', 1, '待办邮件', NULL, '2024-02-17 13:27:56', '2024-03-02 11:59:54', 0);
INSERT INTO `t_email` VALUES ('1758724939470716929', 'yexing195@163.com', 'yexing', 'yexing@pmail.slovety.top', 'yexing195@163.com', '你好吗', 'hello world', '<p><span style=\"color: rgb(225, 60, 57);\">hello </span><span style=\"color: rgb(225, 60, 57);\"><strong>wor</strong></span><strong>ld</strong></p>', 1, '垃圾邮件', NULL, '2024-02-17 13:27:56', '2024-03-02 11:59:54', 0);
INSERT INTO `t_email` VALUES ('1758725032110309377', 'yexing195@163.com', 'yexing', 'yexing@pmail.slovety.top', 'yexing195@163.com', '你好', 'hello world\n', '<p><span style=\"color: rgb(225, 60, 57);\">hello </span><span style=\"color: rgb(225, 60, 57);\"><strong>wor</strong></span><strong>ld</strong></p><p><img src=\"http://localhost/ymail/upload/getImg?id=《仙剑问情蒙眼少女》炼丹画师 4K壁纸_彼岸图网.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>', 2, '收件箱', NULL, '2024-02-17 13:28:18', '2024-02-20 14:52:21', 1);
INSERT INTO `t_email` VALUES ('175872503212210309377', 'yexing195@163.com', 'yexing', 'yexing@pmail.slovety.top', 'yexing195@163.com', '你好', 'hello world\n', '<p><span style=\"color: rgb(225, 60, 57);\">hello </span><span style=\"color: rgb(225, 60, 57);\"><strong>wor</strong></span><strong>ld</strong></p><p><img src=\"http://localhost/ymail/upload/getImg?id=《仙剑问情蒙眼少女》炼丹画师 4K壁纸_彼岸图网.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>', 1, '收件箱', NULL, '2024-02-17 13:28:18', '2024-03-02 11:59:54', 1);
INSERT INTO `t_email` VALUES ('175873463273657577474', 'yexing195@163.com', 'yexing', 'yexing@pmail.slovety.top', 'yexing195@163.com', '你好', 'hello world\n', '<p><span style=\"color: rgb(225, 60, 57);\">hello </span><span style=\"color: rgb(225, 60, 57);\"><strong>wor</strong></span><strong>ld</strong></p><p><img src=\"http://localhost/ymail/upload/getImg?id=《仙剑问情蒙眼少女》炼丹画师 4K壁纸_彼岸图网.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>', 1, '收件箱', NULL, '2024-02-17 14:06:37', '2024-03-02 11:59:54', 0);
INSERT INTO `t_email` VALUES ('1758734673657577474', 'yexing195@163.com', 'yexing', 'yexing@pmail.slovety.top', 'yexing195@163.com', '你好', 'hello world\n', '<p><span style=\"color: rgb(225, 60, 57);\">hello </span><span style=\"color: rgb(225, 60, 57);\"><strong>wor</strong></span><strong>ld</strong></p><p><img src=\"http://localhost/ymail/upload/getImg?id=《仙剑问情蒙眼少女》炼丹画师 4K壁纸_彼岸图网.jpg\" alt=\"\" data-href=\"\" style=\"\"/></p>', 1, '收件箱', NULL, '2024-02-17 14:06:37', '2024-03-02 11:59:54', 0);
INSERT INTO `t_email` VALUES ('1758762381061918721', 'yexing195@163.com', 'yexing', 'yexing@pmail.slovety.top', 'yexing195@163.com', '你好', 'hello world', '<p><span style=\"color: rgb(225, 60, 57);\">hello </span><span style=\"color: rgb(225, 60, 57);\"><strong>wor</strong></span><strong>ld</strong></p>', 1, '收件箱', NULL, '2024-02-17 15:56:43', '2024-03-02 11:59:54', 0);
INSERT INTO `t_email` VALUES ('175876241381061918721', 'yexing195@163.com', 'yexing', 'yexing@pmail.slovety.top', 'yexing195@163.com', '你好', 'hello world', '<p><span style=\"color: rgb(225, 60, 57);\">hello </span><span style=\"color: rgb(225, 60, 57);\"><strong>wor</strong></span><strong>ld</strong></p>', 1, '收件箱', NULL, '2024-02-17 15:56:43', '2024-03-02 11:59:54', 0);
INSERT INTO `t_email` VALUES ('1759928055671132162', 'yexing195@163.com', 'yexing', 'yexing195@163.com', '2420233025@qq.com', '你好', 'hello world', '<p><span style=\"color: rgb(225, 60, 57);\">hello </span><span style=\"color: rgb(225, 60, 57);\"><strong>wor</strong></span><strong>ld</strong></p>', 2, '收件箱', NULL, '2024-02-20 21:08:41', '2024-02-20 21:08:41', 0);
INSERT INTO `t_email` VALUES ('1761726608668110849', 'yexing195@163.com', 'yexing', 'yexing195@163.com', '2420233025@qq.com', '你好', 'hello world\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n你好', '<p><span style=\"color: rgb(225, 60, 57);\">hello </span><span style=\"color: rgb(225, 60, 57);\"><strong>wor</strong></span><strong>ld</strong></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><br></p><p><strong>你好</strong></p>', -4, '收件箱', NULL, '2024-02-25 20:15:29', '2024-02-25 20:15:29', 0);

SET FOREIGN_KEY_CHECKS = 1;
