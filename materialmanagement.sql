DROP TABLE IF EXISTS `materialBase`;
DROP TABLE IF EXISTS `material`;
DROP TABLE IF EXISTS `materialSku`;
DROP TABLE IF EXISTS `unit`;
DROP TABLE IF EXISTS `materialUnit`;
DROP TABLE IF EXISTS `materialCategory`;
DROP TABLE IF EXISTS `materialBaseProp`;
DROP TABLE IF EXISTS `materialBasePropVal`;
DROP TABLE IF EXISTS `materialFiles`;
DROP TABLE IF EXISTS `materialCtrlProp`;
DROP TABLE IF EXISTS `materialCtrlPropVal`;
DROP TABLE IF EXISTS `materialCtrlPropValVer`;

CREATE TABLE IF NOT EXISTS `materialBase` (
	`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`spuCode` VARCHAR(20) NOT NULL,
	`mnemonic` VARCHAR(20),
	`spuName` VARCHAR(50) NOT NULL,
	`description` VARCHAR(100),
	`type` TINYINT NOT NULL,
	`designCode` VARCHAR(10),
	`designVersion` VARCHAR(10),
	`defaultUnitId` INT UNSIGNED NOT NULL,
	`source` VARCHAR(20),
	`usage` VARCHAR(20),
	`note` VARCHAR(50),
	`materialCatId` INT UNSIGNED NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `material` (
	`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`spuCode` VARCHAR(20) NOT NULL,
	`materialCode` VARCHAR(20) NOT NULL,
	`materialName` VARCHAR(50) NOT NULL,
	`oldMaterialCode` VARCHAR(20),
	`barCode` VARCHAR(20),
	`materialBaseId` INT UNSIGNED NOT NULL,
	`purchasePrice` INT UNSIGNED,
	`sellingPrice` INT UNSIGNED
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `materialSku` (
	`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`spuCode` VARCHAR(20) NOT NULL,
	`skuCode` VARCHAR(20) NOT NULL,
	`materialId` INT UNSIGNED NOT NULL,
	`unitId` INT UNSIGNED NOT NULL,
	`purchasePrice` INT UNSIGNED,
	`sellingPrice` INT UNSIGNED,
	`description` VARCHAR(50)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `unit` (
	`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`label` VARCHAR(10) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`englishName` VARCHAR(50) NOT NULL,
	`relatedId` INT UNSIGNED NOT NULL,
	`conversionFactor` DOUBLE NOT NULL,
	`sort` INT UNSIGNED NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `materialUnit` (
	`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`spuCode` VARCHAR(20) NOT NULL,
	`unitId` INT UNSIGNED NOT NULL,
	`relatedId` INT UNSIGNED NOT NULL,
	`conversionFactor` DOUBLE NOT NULL,
	`sort` INT UNSIGNED NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `materialCategory` (
	`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`code` VARCHAR(20) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`type` INT UNSIGNED NOT NULL,
	`parentId` INT UNSIGNED NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `materialBaseProp` (
	`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`materialCatId` INT UNSIGNED NOT NULL,
	`type` TINYINT(4) NOT NULL,
	`label` VARCHAR(10) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`range` VARCHAR(50) NOT NULL,
	`sort` INT UNSIGNED NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `materialBasePropVal` (
	`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`spuCode` VARCHAR(20) NOT NULL,
	`materialCode` VARCHAR(20) NOT NULL,
	`materialBasePropId` INT UNSIGNED NOT NULL,
	`value` VARCHAR(10) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `materialFiles` (
	`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`materialBaseId` INT UNSIGNED NOT NULL,
	`fileId` INT UNSIGNED NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `materialCtrlProp` (
	`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`type` TINYINT(4) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`label` INT UNSIGNED NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `materialCtrlPropVal` (
	`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`versionId` INT UNSIGNED NOT NULL,
	`materialCtrlPropId` INT UNSIGNED NOT NULL,
	`value` VARCHAR(20) NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `materialCtrlPropValVer` (
	`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`version` VARCHAR(10) NOT NULL,
	`origanizationCode` VARCHAR(20) NOT NULL,
	`materialCatId` INT UNSIGNED NOT NULL,
	`spuCode` VARCHAR(20) NOT NULL,
	`startDate` DATETIME NOT NULL,
	`endDate` DATETIME NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- INSERT INTO materialBase VALUES (null, '10000', '12345', '毛料', '丝绸毛料', 64, '10010000', 'v1.0', 1, '工厂', '纺织', 1);
-- INSERT INTO materialBase VALUES (null, '10001', '12346', '布料', '尼龙布料', 32, '10020000', 'v1.0', 2, '工厂', '纺织', 2);
-- INSERT INTO materialBase VALUES (null, '10002', '12347', '棉布', '棉质布料', 16, '10030000', 'v2.0', 3, '工厂', '纺织', 3);
-- INSERT INTO materialCategory VALUES (null, '1', '服装布料', 0);
-- INSERT INTO materialCategory VALUES (null, '2', '日常工具', 0);
-- INSERT INTO materialCategory VALUES (null, '3', '色织棉布', 1);
-- INSERT INTO materialCategory VALUES (null, '4', '色织涤棉布', 1);
-- INSERT INTO materialCategory VALUES (null, '5', '色织中长仿毛花呢', 1);
-- INSERT INTO materialCategory VALUES (null, '6', '全毛花呢', 1);
-- INSERT INTO materialCategory VALUES (null, '7', '毛涤花呢', 1);
-- INSERT INTO materialCategory VALUES (null, '8', '毛涤粘三合一花呢', 1);
-- INSERT INTO materialCategory VALUES (null, '9', '竹节纱布', 1);
-- INSERT INTO materialCategory VALUES (null, '10', '疙瘩纱布', 1);
-- INSERT INTO materialCategory VALUES (null, '11', '螺丝', 2);
-- INSERT INTO materialCategory VALUES (null, '12', '剪刀', 2);

LOCK TABLES `materialBase` WRITE;
INSERT INTO `materialBase` VALUES (1,'110101','111','自动化机器','自动化生产线使用',11,'','',1,'采购','流水线生产','',7), \
									(2,'110102','112','人工操作机器','需雇佣工人',12,'','',1,'采购','流水线生产','',7), \
									(3,'210101','211','棉布','纺织用品',21,'001','v1.0',2,'工厂加工','服装原料','',8), \
									(4,'210102','212','丝绸','纺织用品',22,'010','v2.0',2,'工厂加工','服装原料','',8), \
									(5,'310101','311','台式电脑','管理人员使用',31,'','',3,'采购','办公','',9);
UNLOCK TABLES;

LOCK TABLES `material` WRITE;
INSERT INTO `material` VALUES (1,'110101','11101','自动机器A','','11010101',1,500000,0), \
								(2,'110101','11102','自动机器B','','11010102',1,200000,0), \
								(3,'110102','11201','人工机器C','','11010201',2,100000,0), \
								(4,'210101','21101','棉布一批','','21010101',3,10000,0), \
								(5,'210101','21102','棉布二批','','21010102',3,10000,0), \
								(6,'210102','21201','丝绸一批','','21010201',4,10000,0), \
								(7,'310101','31101','电脑1','','31010101',5,8000,0), \
								(8,'310101','31102','电脑2','','31010102',5,6000,0);
UNLOCK TABLES;

LOCK TABLES `materialSku` WRITE;
INSERT INTO `materialSku` VALUES (1,'110101','111',1,1,500000,0,'备注111'), \
								(2,'110101','112',2,1,200000,0,'备注112'), \
								(3,'110102','121',3,1,100000,0,'备注121'), \
								(4,'210101','211',4,2,10000,0,'备注211'), \
								(5,'210101','212',5,2,10000,0,'备注212'), \
								(6,'210102','221',6,2,10000,0,'备注221'), \
								(7,'310101','311',7,3,8000,0,'备注311'), \
								(8,'310101','312',8,3,6000,0,'备注312');
UNLOCK TABLES;

LOCK TABLES `unit` WRITE;
INSERT INTO `unit` VALUES (1,'单体','部','one',1,1,1), \
							(2,'长度','米','meter',2,1,2), \
							(3,'单体','台','one',3,1,3);
UNLOCK TABLES;

LOCK TABLES `materialUnit` WRITE;
INSERT INTO `materialUnit` VALUES (1,'110101',1,1,1,1), \
									(2,'110102',1,1,1,2), \
									(3,'210101',2,2,1,3), \
									(4,'210102',2,2,1,4), \
									(5,'310101',3,3,1,5);
UNLOCK TABLES;

LOCK TABLES `materialCategory` WRITE;
INSERT INTO `materialCategory` VALUES (1,'1000','A类',0,0), \
										(2,'2000','B类',0,0), \
										(3,'3000','C类',0,0), \
										(4,'1100','工厂用品',0,1), \
										(5,'2100','仓库存货',0,2), \
										(6,'3100','办公用品',0,3), \
										(7,'1101','大型机器',4,4), \
										(8,'2101','布料',1,5), \
										(9,'3101','文具类',4,6);
UNLOCK TABLES;

LOCK TABLES `materialBaseProp` WRITE;
INSERT INTO `materialBaseProp` VALUES (1,7,4,'规格1','规格1','{ \"type\": 3, \"upper\": 10, \"lower\": 0 }',1), \
										(2,7,4,'规格1','规格1','{ \"type\": 3, \"upper\": 10, \"lower\": 0 }',1), \
										(3,7,4,'规格1','规格1','{ \"type\": 3, \"upper\": 10, \"lower\": 0 }',1), \
										(4,8,4,'规格1','规格1','{ \"type\": 3, \"upper\": 10, \"lower\": 0 }',1), \
										(5,8,4,'规格1','规格1','{ \"type\": 3, \"upper\": 10, \"lower\": 0 }',1), \
										(6,8,4,'规格1','规格1','{ \"type\": 3, \"upper\": 10, \"lower\": 0 }',1), \
										(7,9,4,'规格1','规格1','{ \"type\": 3, \"upper\": 10, \"lower\": 0 }',1), \
										(8,9,4,'规格1','规格1','{ \"type\": 3, \"upper\": 10, \"lower\": 0 }',1), \
										(9,7,4,'规格1','规格1','{ \"type\": 3, \"upper\": 10, \"lower\": 0 }',1), \
										(10,8,4,'规格1','规格1','{ \"type\": 3, \"upper\": 10, \"lower\": 0 }',1), \
										(11,9,4,'规格1','规格1','{ \"type\": 3, \"upper\": 10, \"lower\": 0 }',1);
UNLOCK TABLES;

LOCK TABLES `materialBasePropVal` WRITE;
INSERT INTO `materialBasePropVal` VALUES (1,'110101','11101',1,'规格值'), \
											(2,'110101','11102',2,'规格值'), \
											(3,'110102','11201',3,'规格值'), \
											(4,'210101','21101',4,'规格值'), \
											(5,'210101','21102',5,'规格值'), \
											(6,'210102','21201',6,'规格值'), \
											(7,'310101','31101',7,'规格值'), \
											(8,'310101','31102',8,'规格值'), \
											(9,'110101','-1',9,'规格值'), \
											(10,'110102','-1',9,'规格值'), \
											(11,'210101','-1',10,'规格值'), \
											(12,'210102','-1',10,'规格值'), \
											(13,'310101','-1',11,'规格值');
UNLOCK TABLES;

LOCK TABLES `materialFiles` WRITE;
INSERT INTO `materialFiles` VALUES (1,1,111), \
									(2,2,112), \
									(3,3,211), \
									(4,4,212), \
									(5,5,311);
UNLOCK TABLES;

LOCK TABLES `materialCtrlProp` WRITE;
INSERT INTO `materialCtrlProp` VALUES (1,5,'物料制购类型',1),
									(2,5,'ABC分类',1),
									(3,5,'循环盘点编码',1),
									(4,5,'是否批次管理',1),
									(5,5,'是否单件管理',0),
									(6,5,'是否进价控制',0),
									(7,5,'默认供应商',0),
									(8,5,'默认采购部门',0),
									(9,5,'默认仓库',0),
									(10,5,'默认采购单位',0),
									(11,5,'默认库存单位',0),
									(12,5,'默认库位',0),
									(13,6,'是否独立需求',2),
									(14,6,'订货提前期（天）',3),
									(15,6,'补货政策',1),
									(16,6,'补货周期（天）',3),
									(17,6,'最大库存量',3),
									(18,6,'安全库存量',3),
									(19,6,'订货批量',3),
									(20,6,'批量政策',1),
									(21,6,'批量周期（天）',3),
									(22,6,'默认计划单位',5),
									(23,7,'销售计划价格',3),
									(24,7,'计价货币',1),
									(25,7,'是否售价控制',2),
									(26,7,'销售价格策略',1),
									(27,7,'销价下限率（%）',3),
									(28,7,'销售成本科目',4),
									(29,7,'默认客户',4),
									(30,7,'销售地',1),
									(31,7,'默认业务员',4),
									(32,7,'默认销售单位',3),
									(33,8,'检验方式',1),
									(34,8,'检验水准',1),
									(35,8,'检验程度',1),
									(36,8,'默认检验部门',4),
									(37,8,'检验工时（时）',3),
									(38,8,'存储期限（天）',3),
									(39,8,'默认检验员',4),
									(40,8,'检验标准文件',3),
									(41,9,'财务类别',1),
									(42,9,'记账本位币',1),
									(43,9,'会计科目',4),
									(44,9,'增值税代码',4),
									(45,9,'存货计价方法',1),
									(46,9,'成本计算方法',1),
									(47,9,'开票类型',1);
UNLOCK TABLES;

LOCK TABLES `materialCtrlPropVal` WRITE;
INSERT INTO `materialCtrlPropVal` VALUES (1,1,1,'制购类型1'),
										(2,1,2,'ABC分类1'),
										(3,1,3,'盘点编码1'),
										(4,1,4,'true'),
										(5,1,5,'true'),
										(6,1,6,'true'),
										(7,1,7,'默认供应商'),
										(8,1,8,'默认采购部门'),
										(9,1,9,'默认仓库'),
										(10,1,10,'默认采购单位'),
										(11,1,11,'默认库存单位'),
										(12,1,12,'默认库位'),
										(13,1,13,'true'),
										(14,1,14,'7'),
										(15,1,15,'补货政策1'),
										(16,1,16,'15'),
										(17,1,17,'一个仓库'),
										(18,1,18,'半个仓库'),
										(19,1,19,'10批一次'),
										(20,1,20,'批量政策1'),
										(21,1,21,'7'),
										(22,1,22,'默认计划单位'),
										(23,1,23,'1000'),
										(24,1,24,'计价货币1'),
										(25,1,25,'true'),
										(26,1,26,'价格策略1'),
										(27,1,27,'10'),
										(28,1,28,'成本科目1'),
										(29,1,29,'客户A'),
										(30,1,30,'销售地1'),
										(31,1,31,'业务员小青'),
										(32,1,32,'默认销售单位'),
										(33,1,33,'不检验'),
										(34,1,34,'检验水准1'),
										(35,1,35,'检验程度1'),
										(36,1,36,'默认检测部门'),
										(37,1,37,'150'),
										(38,1,38,'60'),
										(39,1,39,'默认检验员'),
										(40,1,40,'文件A'),
										(41,1,41,'财务类别1'),
										(42,1,42,'位币1'),
										(43,1,43,'默认会计科目'),
										(44,1,44,'默认增值税代码'),
										(45,1,45,'计价方法1'),
										(46,1,46,'计算方法1'),
										(47,1,47,'类型1'),
										(48,2,1,'制购类型1'),
										(49,2,2,'ABC分类1'),
										(50,2,3,'盘点编码1'),
										(51,2,4,'true'),
										(52,2,5,'true'),
										(53,2,6,'true'),
										(54,2,7,'默认供应商'),
										(55,2,8,'默认采购部门'),
										(56,2,9,'默认仓库'),
										(57,2,10,'默认采购单位'),
										(58,2,11,'默认库存单位'),
										(59,2,12,'默认库位'),
										(60,2,13,'true'),
										(61,2,14,'7'),
										(62,2,15,'补货政策1'),
										(63,2,16,'15'),
										(64,2,17,'一个仓库'),
										(65,2,18,'半个仓库'),
										(66,2,19,'10批一次'),
										(67,2,20,'批量政策1'),
										(68,2,21,'7'),
										(69,2,22,'默认计划单位'),
										(70,2,23,'1000'),
										(71,2,24,'计价货币1'),
										(72,2,25,'true'),
										(73,2,26,'价格策略1'),
										(74,2,27,'10'),
										(75,2,28,'成本科目1'),
										(76,2,29,'客户A'),
										(77,2,30,'销售地1'),
										(78,2,31,'业务员小青'),
										(79,2,32,'默认销售单位'),
										(80,2,33,'不检验'),
										(81,2,34,'检验水准1'),
										(82,2,35,'检验程度1'),
										(83,2,36,'默认检测部门'),
										(84,2,37,'150'),
										(85,2,38,'60'),
										(86,2,39,'默认检验员'),
										(87,2,40,'文件A'),
										(88,2,41,'财务类别1'),
										(89,2,42,'位币1'),
										(90,2,43,'默认会计科目'),
										(91,2,44,'默认增值税代码'),
										(92,2,45,'计价方法1'),
										(93,2,46,'计算方法1'),
										(94,2,47,'类型1'),
										(95,3,1,'制购类型1'),
										(96,3,2,'ABC分类1'),
										(97,3,3,'盘点编码1'),
										(98,3,4,'true'),
										(99,3,5,'true'),
										(100,3,6,'true'),
										(101,3,7,'默认供应商'),
										(102,3,8,'默认采购部门'),
										(103,3,9,'默认仓库'),
										(104,3,10,'默认采购单位'),
										(105,3,11,'默认库存单位'),
										(106,3,12,'默认库位'),
										(107,3,13,'true'),
										(108,3,14,'7'),
										(109,3,15,'补货政策1'),
										(110,3,16,'15'),
										(111,3,17,'一个仓库'),
										(112,3,18,'半个仓库'),
										(113,3,19,'10批一次'),
										(114,3,20,'批量政策1'),
										(115,3,21,'7'),
										(116,3,22,'默认计划单位'),
										(117,3,23,'1000'),
										(118,3,24,'计价货币1'),
										(119,3,25,'true'),
										(120,3,26,'价格策略1'),
										(121,3,27,'10'),
										(122,3,28,'成本科目1'),
										(123,3,29,'客户A'),
										(124,3,30,'销售地1'),
										(125,3,31,'业务员小青'),
										(126,3,32,'默认销售单位'),
										(127,3,33,'不检验'),
										(128,3,34,'检验水准1'),
										(129,3,35,'检验程度1'),
										(130,3,36,'默认检测部门'),
										(131,3,37,'150'),
										(132,3,38,'60'),
										(133,3,39,'默认检验员'),
										(134,3,40,'文件A'),
										(135,3,41,'财务类别1'),
										(136,3,42,'位币1'),
										(137,3,43,'默认会计科目'),
										(138,3,44,'默认增值税代码'),
										(139,3,45,'计价方法1'),
										(140,3,46,'计算方法1'),
										(141,3,47,'类型1'),
										(142,4,1,'制购类型1'),
										(143,4,2,'ABC分类1'),
										(144,4,3,'盘点编码1'),
										(145,4,4,'true'),
										(146,4,5,'true'),
										(147,4,6,'true'),
										(148,4,7,'默认供应商'),
										(149,4,8,'默认采购部门'),
										(150,4,9,'默认仓库'),
										(151,4,10,'默认采购单位'),
										(152,4,11,'默认库存单位'),
										(153,4,12,'默认库位'),
										(154,4,13,'true'),
										(155,4,14,'7'),
										(156,4,15,'补货政策1'),
										(157,4,16,'15'),
										(158,4,17,'一个仓库'),
										(159,4,18,'半个仓库'),
										(160,4,19,'10批一次'),
										(161,4,20,'批量政策1'),
										(162,4,21,'7'),
										(163,4,22,'默认计划单位'),
										(164,4,23,'1000'),
										(165,4,24,'计价货币1'),
										(166,4,25,'true'),
										(167,4,26,'价格策略1'),
										(168,4,27,'10'),
										(169,4,28,'成本科目1'),
										(170,4,29,'客户A'),
										(171,4,30,'销售地1'),
										(172,4,31,'业务员小青'),
										(173,4,32,'默认销售单位'),
										(174,4,33,'不检验'),
										(175,4,34,'检验水准1'),
										(176,4,35,'检验程度1'),
										(177,4,36,'默认检测部门'),
										(178,4,37,'150'),
										(179,4,38,'60'),
										(180,4,39,'默认检验员'),
										(181,4,40,'文件A'),
										(182,4,41,'财务类别1'),
										(183,4,42,'位币1'),
										(184,4,43,'默认会计科目'),
										(185,4,44,'默认增值税代码'),
										(186,4,45,'计价方法1'),
										(187,4,46,'计算方法1'),
										(188,4,47,'类型1'),
										(189,5,1,'制购类型1'),
										(190,5,2,'ABC分类1'),
										(191,5,3,'盘点编码1'),
										(192,5,4,'true'),
										(193,5,5,'true'),
										(194,5,6,'true'),
										(195,5,7,'默认供应商'),
										(196,5,8,'默认采购部门'),
										(197,5,9,'默认仓库'),
										(198,5,10,'默认采购单位'),
										(199,5,11,'默认库存单位'),
										(200,5,12,'默认库位'),
										(201,5,13,'true'),
										(202,5,14,'7'),
										(203,5,15,'补货政策1'),
										(204,5,16,'15'),
										(205,5,17,'一个仓库'),
										(206,5,18,'半个仓库'),
										(207,5,19,'10批一次'),
										(208,5,20,'批量政策1'),
										(209,5,21,'7'),
										(210,5,22,'默认计划单位'),
										(211,5,23,'1000'),
										(212,5,24,'计价货币1'),
										(213,5,25,'true'),
										(214,5,26,'价格策略1'),
										(215,5,27,'10'),
										(216,5,28,'成本科目1'),
										(217,5,29,'客户A'),
										(218,5,30,'销售地1'),
										(219,5,31,'业务员小青'),
										(220,5,32,'默认销售单位'),
										(221,5,33,'不检验'),
										(222,5,34,'检验水准1'),
										(223,5,35,'检验程度1'),
										(224,5,36,'默认检测部门'),
										(225,5,37,'150'),
										(226,5,38,'60'),
										(227,5,39,'默认检验员'),
										(228,5,40,'文件A'),
										(229,5,41,'财务类别1'),
										(230,5,42,'位币1'),
										(231,5,43,'默认会计科目'),
										(232,5,44,'默认增值税代码'),
										(233,5,45,'计价方法1'),
										(234,5,46,'计算方法1'),
										(235,5,47,'类型1');
UNLOCK TABLES;

LOCK TABLES `materialCtrlPropValVer` WRITE;
INSERT INTO `materialCtrlPropValVer` VALUES (1,'BB11010101',1,7,'110101','2018-01-01 00:00:00','2025-12-31 00:00:00'), \
											(2,'BB11010201',1,7,'110102','2018-01-01 00:00:00','2025-12-31 00:00:00'), \
											(3,'BB21010101',1,8,'210101','2018-01-01 00:00:00','2025-12-31 00:00:00'), \
											(4,'BB21010201',1,8,'210102','2018-01-01 00:00:00','2025-12-31 00:00:00'), \
											(5,'BB31010101',1,9,'310101','2018-01-01 00:00:00','2025-12-31 00:00:00');
UNLOCK TABLES;