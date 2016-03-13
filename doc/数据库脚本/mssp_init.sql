/*
SQLyog 企业版 - MySQL GUI v8.14 
MySQL - 5.7.4-m14 : Database - mssp
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mssp` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `mssp`;

/*Table structure for table `comm_agent` */

DROP TABLE IF EXISTS `comm_agent`;

CREATE TABLE `comm_agent` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `p_id` int(11) DEFAULT NULL COMMENT '代理商Id',
  `score` int(11) DEFAULT NULL COMMENT '积分',
  `verify_status` int(11) DEFAULT NULL COMMENT '审核状态',
  `verify_msg` int(11) DEFAULT NULL COMMENT '审核信息',
  `verify_members` int(11) DEFAULT NULL COMMENT '审核人',
  `verify_time` datetime DEFAULT NULL COMMENT '审核时间',
  `createtime` datetime DEFAULT NULL COMMENT '创建时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后一次登陆时间',
  `close_time` datetime DEFAULT NULL COMMENT '流失时间',
  `contract_status` int(11) DEFAULT NULL COMMENT '合同状态',
  `ir_status` int(11) DEFAULT NULL COMMENT '精英网店面状态 0 未冻结 1 已冻结 2 已流失',
  `freeze_msg` varchar(1000) DEFAULT NULL COMMENT '冻结原因',
  `close_msg` varchar(1000) DEFAULT NULL COMMENT '流失原因',
  `stores_level` int(11) DEFAULT NULL COMMENT '店面级别编号',
  `image_list` varchar(1000) DEFAULT NULL,
  `apply_time` datetime DEFAULT NULL,
  `freeze_time` datetime DEFAULT NULL,
  `contract_image_list` varchar(1000) DEFAULT NULL,
  `type` int(11) DEFAULT NULL,
  `city_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`city_id`),
  KEY `fk_comm_agent_comm_city1_idx` (`city_id`),
  CONSTRAINT `fk_comm_agent_comm_city1` FOREIGN KEY (`city_id`) REFERENCES `comm_city` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户管理';

/*Data for the table `comm_agent` */

/*Table structure for table `comm_approval_log` */

DROP TABLE IF EXISTS `comm_approval_log`;

CREATE TABLE `comm_approval_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `data_type` int(11) DEFAULT NULL COMMENT '数据类型 例如：R1 R2等等',
  `date_id` int(11) DEFAULT NULL COMMENT '数据类型下的数据ID 例如：R1的第一条数据',
  `approval_user_id` int(11) NOT NULL COMMENT '审批人',
  `approval_status` int(11) DEFAULT NULL COMMENT '审批状态',
  `approval_msg` varchar(45) DEFAULT NULL COMMENT '审批信息',
  PRIMARY KEY (`id`,`approval_user_id`),
  KEY `fk_comm_approval_log_comm_user1_idx` (`approval_user_id`),
  CONSTRAINT `fk_comm_approval_log_comm_user1` FOREIGN KEY (`approval_user_id`) REFERENCES `comm_agent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='审批记录表';

/*Data for the table `comm_approval_log` */

/*Table structure for table `comm_area` */

DROP TABLE IF EXISTS `comm_area`;

CREATE TABLE `comm_area` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `area_name` varchar(45) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `is_delete` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='区域维护';

/*Data for the table `comm_area` */

/*Table structure for table `comm_city` */

DROP TABLE IF EXISTS `comm_city`;

CREATE TABLE `comm_city` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `city_name` varchar(45) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `area_id` int(11) NOT NULL,
  `is_delete` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`area_id`),
  KEY `fk_comm_city_comm_area_idx` (`area_id`),
  CONSTRAINT `fk_comm_city_comm_area` FOREIGN KEY (`area_id`) REFERENCES `comm_area` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='城市维护';

/*Data for the table `comm_city` */

/*Table structure for table `comm_pannel` */

DROP TABLE IF EXISTS `comm_pannel`;

CREATE TABLE `comm_pannel` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `is_delete` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='面板  例如：ISP  MVA等等';

/*Data for the table `comm_pannel` */

/*Table structure for table `comm_product` */

DROP TABLE IF EXISTS `comm_product`;

CREATE TABLE `comm_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `size1` varchar(45) DEFAULT NULL,
  `size2` varchar(45) DEFAULT NULL,
  `material` varchar(45) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `proudct_series_id` int(11) NOT NULL,
  `series_id` int(11) NOT NULL,
  `pannel_id` int(11) NOT NULL,
  `status` int(11) DEFAULT NULL COMMENT '状态 0,停产  1,激活',
  PRIMARY KEY (`id`,`proudct_series_id`,`series_id`,`pannel_id`),
  KEY `fk_psi_product_comm_series1_idx` (`proudct_series_id`),
  KEY `fk_comm_product_comm_series1_idx` (`series_id`),
  KEY `fk_comm_product_comm_pannel1_idx` (`pannel_id`),
  CONSTRAINT `fk_psi_product_comm_series1` FOREIGN KEY (`proudct_series_id`) REFERENCES `comm_product_series` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_comm_product_comm_series1` FOREIGN KEY (`series_id`) REFERENCES `comm_series` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_comm_product_comm_pannel1` FOREIGN KEY (`pannel_id`) REFERENCES `comm_pannel` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='型号维护';

/*Data for the table `comm_product` */

/*Table structure for table `comm_product_series` */

DROP TABLE IF EXISTS `comm_product_series`;

CREATE TABLE `comm_product_series` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `is_delete` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='零售产品系列 例如：舒适蓝 好色等等';

/*Data for the table `comm_product_series` */

/*Table structure for table `comm_role` */

DROP TABLE IF EXISTS `comm_role`;

CREATE TABLE `comm_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `is_delete` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色维护';

/*Data for the table `comm_role` */

/*Table structure for table `comm_series` */

DROP TABLE IF EXISTS `comm_series`;

CREATE TABLE `comm_series` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `is_delete` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系列 例如：E系列  C系列等等';

/*Data for the table `comm_series` */

/*Table structure for table `comm_user` */

DROP TABLE IF EXISTS `comm_user`;

CREATE TABLE `comm_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(100) DEFAULT NULL COMMENT '用户名/登录名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `sys_id` int(11) DEFAULT NULL COMMENT '系统Id',
  `comm_role_id` int(11) NOT NULL,
  `agent_id` int(11) NOT NULL COMMENT '代理商ID',
  `real_name` varchar(45) DEFAULT NULL,
  `phone` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `is_admin` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`comm_role_id`,`agent_id`),
  KEY `fk_comm_user_comm_role1_idx` (`comm_role_id`),
  KEY `fk_comm_user_comm_agent1_idx` (`agent_id`),
  CONSTRAINT `fk_comm_user_comm_role10` FOREIGN KEY (`comm_role_id`) REFERENCES `comm_role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_comm_user_comm_agent1` FOREIGN KEY (`agent_id`) REFERENCES `comm_agent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户管理';

/*Data for the table `comm_user` */

/*Table structure for table `psi_estimate_by_product` */

DROP TABLE IF EXISTS `psi_estimate_by_product`;

CREATE TABLE `psi_estimate_by_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `total_volume` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  `agent_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`product_id`,`agent_id`),
  KEY `fk_psi_total_by_month_comm_product1_idx` (`product_id`),
  KEY `fk_psi_estimate_by_product_comm_agent1_idx` (`agent_id`),
  CONSTRAINT `fk_psi_total_by_month_comm_product1` FOREIGN KEY (`product_id`) REFERENCES `comm_product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_psi_estimate_by_product_comm_agent1` FOREIGN KEY (`agent_id`) REFERENCES `comm_agent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='月度销售预估（由代理每月填写一次按每个型号的预估销量）';

/*Data for the table `psi_estimate_by_product` */

/*Table structure for table `psi_inventory` */

DROP TABLE IF EXISTS `psi_inventory`;

CREATE TABLE `psi_inventory` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inventory_volume` varchar(45) DEFAULT NULL COMMENT '库存数量',
  `createtime` datetime DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  `agent_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`product_id`,`agent_id`),
  KEY `fk_psi_inventory_comm_product1_idx` (`product_id`),
  KEY `fk_psi_inventory_comm_agent1_idx` (`agent_id`),
  CONSTRAINT `fk_psi_inventory_comm_product1` FOREIGN KEY (`product_id`) REFERENCES `comm_product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_psi_inventory_comm_agent1` FOREIGN KEY (`agent_id`) REFERENCES `comm_agent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='实时库存';

/*Data for the table `psi_inventory` */

/*Table structure for table `psi_reason` */

DROP TABLE IF EXISTS `psi_reason`;

CREATE TABLE `psi_reason` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `reason` varchar(45) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  `is_delete` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sell out修改数据理由维护';

/*Data for the table `psi_reason` */

/*Table structure for table `psi_sell_in` */

DROP TABLE IF EXISTS `psi_sell_in`;

CREATE TABLE `psi_sell_in` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL COMMENT '型号ID',
  `sell_in_volume` varchar(45) DEFAULT NULL COMMENT '销量',
  `createtime` datetime DEFAULT NULL COMMENT '物流部数据导入时间',
  `channel_type` int(11) DEFAULT NULL COMMENT '出货渠道    1，飞生   2，越海',
  `actiontime` datetime DEFAULT NULL COMMENT 'sell in数据发生时间',
  `agent_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`product_id`,`agent_id`),
  KEY `fk_psi_sell_in_comm_product1_idx` (`product_id`),
  KEY `fk_psi_sell_in_comm_agent1_idx` (`agent_id`),
  CONSTRAINT `fk_psi_sell_in_comm_product1` FOREIGN KEY (`product_id`) REFERENCES `comm_product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_psi_sell_in_comm_agent1` FOREIGN KEY (`agent_id`) REFERENCES `comm_agent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sell in数据';

/*Data for the table `psi_sell_in` */

/*Table structure for table `psi_sell_in_datasource` */

DROP TABLE IF EXISTS `psi_sell_in_datasource`;

CREATE TABLE `psi_sell_in_datasource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `delivery` varchar(100) DEFAULT NULL,
  `item` varchar(100) DEFAULT NULL,
  `material` varchar(100) DEFAULT NULL COMMENT '型号',
  `dlv_qty` bigint(20) DEFAULT NULL COMMENT '销量',
  `su` varchar(100) DEFAULT NULL COMMENT 'SU',
  `createdon` datetime DEFAULT NULL COMMENT '日期',
  `volume` varchar(100) DEFAULT NULL,
  `vun` varchar(100) DEFAULT NULL,
  `sold_to` varchar(100) DEFAULT NULL,
  `nameofsold_toparty` varchar(500) DEFAULT NULL COMMENT '付款方',
  `ship_to` varchar(100) DEFAULT NULL,
  `nameoftheship_toparty` varchar(500) DEFAULT NULL COMMENT '收货方',
  `ship_tol` varchar(500) DEFAULT NULL COMMENT '收货方城市名',
  `dlvty` varchar(100) DEFAULT NULL,
  `shpt` varchar(100) DEFAULT NULL,
  `area` varchar(100) DEFAULT NULL COMMENT '区域',
  `city` varchar(100) DEFAULT NULL COMMENT '城市',
  `province` varchar(100) DEFAULT NULL COMMENT '省份',
  `size` varchar(100) DEFAULT NULL COMMENT '村别',
  `prop` varchar(100) DEFAULT NULL COMMENT '属性',
  `channel_type` int(11) DEFAULT NULL COMMENT '出货渠道  1，飞生    2，越海',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PSI系统数据来源（每条由物流部统一导入）';

/*Data for the table `psi_sell_in_datasource` */

/*Table structure for table `psi_sell_in_estimate_by_month` */

DROP TABLE IF EXISTS `psi_sell_in_estimate_by_month`;

CREATE TABLE `psi_sell_in_estimate_by_month` (
  `id` int(11) NOT NULL,
  `sell_in_volume` int(11) DEFAULT NULL COMMENT 'sell in预估总量',
  `createtime` datetime DEFAULT NULL COMMENT '数据导入时间',
  `data_month` datetime DEFAULT NULL COMMENT '某个月',
  `agent_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`agent_id`),
  KEY `fk_psi_sell_in_estimate_by_month_comm_agent1_idx` (`agent_id`),
  CONSTRAINT `fk_psi_sell_in_estimate_by_month_comm_agent1` FOREIGN KEY (`agent_id`) REFERENCES `comm_agent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='sell in月度销量预估总量（每月由管理员导入sell in所有型号的预估总量）';

/*Data for the table `psi_sell_in_estimate_by_month` */

/*Table structure for table `psi_sell_out` */

DROP TABLE IF EXISTS `psi_sell_out`;

CREATE TABLE `psi_sell_out` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int(11) NOT NULL,
  `sell_out_volume` int(11) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL COMMENT '代理填写sell out数据时创建sell out数据的时间',
  `channel_type` int(11) DEFAULT NULL COMMENT '出货渠道    1，飞生   2，越海',
  `actiontime` datetime DEFAULT NULL COMMENT '同sell in的actiontime',
  `agent_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`product_id`,`agent_id`),
  KEY `fk_psi_sell_out_comm_product1_idx` (`product_id`),
  KEY `fk_psi_sell_out_comm_agent1_idx` (`agent_id`),
  CONSTRAINT `fk_psi_sell_out_comm_product1` FOREIGN KEY (`product_id`) REFERENCES `comm_product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_psi_sell_out_comm_agent1` FOREIGN KEY (`agent_id`) REFERENCES `comm_agent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `psi_sell_out` */

/*Table structure for table `psi_sell_out_update_log` */

DROP TABLE IF EXISTS `psi_sell_out_update_log`;

CREATE TABLE `psi_sell_out_update_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `expect_repertory` int(11) DEFAULT NULL COMMENT '预期库存',
  `apply_time` datetime DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `sell_in_volume` varchar(45) DEFAULT NULL,
  `sell_out_volume` varchar(45) DEFAULT NULL,
  `product_id` int(11) NOT NULL,
  `real_sell_out` varchar(45) DEFAULT NULL,
  `sell_out_gap` varchar(45) DEFAULT NULL,
  `apply_id` int(11) NOT NULL COMMENT '申请人',
  `applytime` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`,`product_id`,`apply_id`),
  KEY `fk_psi_sell_out_update_log_comm_product1_idx` (`product_id`),
  KEY `fk_psi_sell_out_update_log_comm_agent1_idx` (`apply_id`),
  CONSTRAINT `fk_psi_sell_out_update_log_comm_product1` FOREIGN KEY (`product_id`) REFERENCES `comm_product` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_psi_sell_out_update_log_comm_agent1` FOREIGN KEY (`apply_id`) REFERENCES `comm_agent` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='PSI Sell out修改申请记录';

/*Data for the table `psi_sell_out_update_log` */

/*Table structure for table `test` */

DROP TABLE IF EXISTS `test`;

CREATE TABLE `test` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `test` */

/*Table structure for table `test2` */

DROP TABLE IF EXISTS `test2`;

CREATE TABLE `test2` (
  `id` int(11) NOT NULL,
  `test1` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `test2` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
