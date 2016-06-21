/*Table structure for table `bonus` */

DROP TABLE IF EXISTS `bonus`;

CREATE TABLE `bonus` (
  `id` varchar(36) NOT NULL,
  `staffId` varchar(12) NOT NULL,
  `staffName` varchar(255) NOT NULL,
  `wages` double(10,2) NOT NULL DEFAULT '0.00',
  `totalBonus` double(20,2) NOT NULL DEFAULT '0.00',
  `totalTax` double(20,2) DEFAULT '0.00',
  `afterTaxBonus` double(20,2) DEFAULT '0.00',
  `bonus1` double(20,2) DEFAULT '0.00' COMMENT '年终奖综合计税发放金额',
  `bonus2` double(20,2) DEFAULT '0.00' COMMENT '年终奖随第一个月发放金额',
  `bonus3` double(20,2) DEFAULT '0.00' COMMENT '年终奖随第二个月发放金额',
  `escapeTax` double(20,2) DEFAULT '0.00',
  `afterEscapeBonus` double(20,2) DEFAULT '0.00' COMMENT '年终奖税后实发总金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `staff` */

DROP TABLE IF EXISTS `staff`;

CREATE TABLE `staff` (
  `staffId` varchar(12) NOT NULL,
  `name` varchar(255) NOT NULL,
  `wages` double(10,2) NOT NULL DEFAULT '0.00',
  `afterTax` double(10,2) DEFAULT '0.00',
  PRIMARY KEY (`staffId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
