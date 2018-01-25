#drop database orderpolling;
create database orderpolling default charset utf8;

DROP table `orderPollingRecord`;
CREATE TABLE `orderPollingRecord` (
  `id` bigint(20) NOT NULL DEFAULT 0,
  `createTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `editTime` datetime DEFAULT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '最后修改时间',
  `pollingTimes` int(11) NOT NULL DEFAULT 0 COMMENT '当前轮询通知次数',
  `pollingLimitTimes` int(11) NOT NULL DEFAULT 0 COMMENT '轮询通知总数',
  `bankTypeCode` varchar(26) NOT NULL DEFAULT '' COMMENT '银行渠道编号',
  #`url` varchar(2000) NOT NULL DEFAULT '' COMMENT '通知地址 获取订单处理状态',
  `orderTransactionNo` varchar(26) NOT NULL DEFAULT '' COMMENT '平台订单交易流水号',
  `status` varchar(17) NOT NULL COMMENT 'SUCCESS:成功 FAILED:失败',
  `pollingRule` varchar(100) DEFAULT NULL DEFAULT '' COMMENT '通知规则json',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u1` (`orderTransactionNo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单轮询表';

DROP table `order`;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL DEFAULT 0,
  `createTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT '创建时间',
  `productName` varchar(100) NOT NULL DEFAULT '' COMMENT '商品名称',
  `orderTransactionNo` varchar(26) NOT NULL DEFAULT '' COMMENT '平台订单交易流水号',
  `bankTypeCode` varchar(26) NOT NULL DEFAULT '' COMMENT '银行渠道编号',
  `status` varchar(17) NOT NULL COMMENT 'SUCCESS:成功 FAILED:失败 WAITING_PAYMENT:等待支付',
  PRIMARY KEY (`id`),
  UNIQUE KEY `u1` (`orderTransactionNo`),
  KEY `k1` (`createTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单表';

