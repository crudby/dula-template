-- 用于加新表的模板
CREATE TABLE IF NOT EXISTS `table_name` (
  `id` BIGINT(20) NOT NULL COMMENT '主键',

  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `revise_time` datetime DEFAULT NULL COMMENT '修改时间',
  `reviser` bigint(20) DEFAULT NULL COMMENT '修改人',
  `tenant_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '租户',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='表信息';