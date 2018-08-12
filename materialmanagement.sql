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
	`parentId` INT UNSIGNED NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE IF NOT EXISTS `materialBaseProp` (
	`id` INT UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT,
	`materialCatId` INT UNSIGNED NOT NULL,
	`type` TINYINT(4) NOT NULL,
	`label` VARCHAR(10) NOT NULL,
	`name` VARCHAR(50) NOT NULL,
	`range` VARCHAR(20) NOT NULL,
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
	`value` VARCHAR(10) NOT NULL
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
INSERT INTO `materialCategory` VALUES (1,'1000','A类',0), \
										(2,'2000','B类',0), \
										(3,'3000','C类',0), \
										(4,'1100','工厂用品',1), \
										(5,'2100','仓库存货',2), \
										(6,'3100','办公用品',3), \
										(7,'1101','大型机器',4), \
										(8,'2101','布料',5), \
										(9,'3101','文具类',6);
UNLOCK TABLES;

LOCK TABLES `materialBaseProp` WRITE;
INSERT INTO `materialBaseProp` VALUES (1,7,1,'可改动','wu','1-10',1), \
										(2,7,1,'可改动','liao','1-10',2), \
										(3,7,2,'不可改动','de','1-10',3), \
										(4,8,3,'不可改动','ji','1-10',4), \
										(5,8,3,'不可改动','ben','1-10',5), \
										(6,8,4,'可改动','de','1-10',6), \
										(7,9,5,'可改动','shu','1-10',7), \
										(8,9,5,'可改动','xing','1-10',8);
UNLOCK TABLES;

LOCK TABLES `materialBasePropVal` WRITE;
INSERT INTO `materialBasePropVal` VALUES (1,'110101','11101',1,'属'), \
											(2,'110101','11102',2,'性'), \
											(3,'110102','11201',3,'值'), \
											(4,'210101','21101',4,'怎'), \
											(5,'210101','21102',5,'么'), \
											(6,'210102','21201',6,'设'), \
											(7,'310101','31101',7,'置'), \
											(8,'310101','31102',8,'呢');
UNLOCK TABLES;

LOCK TABLES `materialFiles` WRITE;
INSERT INTO `materialFiles` VALUES (1,1,111), \
									(2,2,112), \
									(3,3,211), \
									(4,4,212), \
									(5,5,311);
UNLOCK TABLES;

LOCK TABLES `materialCtrlProp` WRITE;
INSERT INTO `materialCtrlProp` VALUES (1,5,'物料制购类型',1), \
										(2,5,'ABC分类',1), \
										(3,5,'循环盘点编码',1), \
										(4,5,'是否批次管理',1), \
										(5,5,'是否单件管理',0), \
										(6,5,'是否进价控制',0), \
										(7,5,'默认供应商',0), \
										(8,5,'默认采购部门',0) \
										(9,5,'默认仓库',0) \
										(10,5,'默认采购单位',0) \
										(11,5,'默认库存单位',0) \
										(12,5,'默认库位',0);
UNLOCK TABLES;

LOCK TABLES `materialCtrlPropVal` WRITE;
INSERT INTO `materialCtrlPropVal` VALUES (1,1,1,'制购类型1'), \
										(2,1,2,'ABC分类1'), \
										(3,1,3,'盘点编码1'), \
										(4,1,4,'true'), \
										(5,1,5,'true'), \
										(6,1,6,'true'), \
										(7,1,7,'默认供应商'), \
										(8,1,8,'默认采购部门') \
										(9,1,9,'默认仓库'), \
										(10,1,10,'默认采购单位'), \
										(11,1,11,'默认库存单位'), \
										(12,1,12,'默认库位'), \;
UNLOCK TABLES;

LOCK TABLES `materialCtrlPropValVer` WRITE;
INSERT INTO `materialCtrlPropValVer` VALUES (1,'BB11010101',1,7,'110101','2018-01-01 00:00:00','2025-12-31 00:00:00'), \
											(2,'BB11010201',1,7,'110102','2018-01-01 00:00:00','2025-12-31 00:00:00'), \
											(3,'BB21010101',1,8,'210101','2018-01-01 00:00:00','2025-12-31 00:00:00'), \
											(4,'BB21010201',1,8,'210102','2018-01-01 00:00:00','2025-12-31 00:00:00'), \
											(5,'BB31010101',1,9,'310101','2018-01-01 00:00:00','2025-12-31 00:00:00');
UNLOCK TABLES;