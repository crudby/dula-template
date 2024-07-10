-- 用户信息
CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` BIGINT(20) NOT NULL COMMENT '主键',
  `open_id` VARCHAR(40) DEFAULT NULL COMMENT 'openID',
  `username` VARCHAR(40) NOT NULL COMMENT '用户姓名',
  `nickname` VARCHAR(40) DEFAULT NULL COMMENT '用户昵称',
  `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号码',
  `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
  `password` VARCHAR(100) DEFAULT NULL COMMENT '密码',
  `registered` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否注册 0：否，1：是',
  `gender` VARCHAR(2) DEFAULT NULL COMMENT '性别',
  `avatar` VARCHAR(100) DEFAULT NULL COMMENT '头像',
  `login_ip` VARCHAR(20) DEFAULT NULL COMMENT '最后登录IP',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '帐号状态（1启用 0禁用）',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `revise_time` datetime DEFAULT NULL COMMENT '修改时间',
  `reviser` bigint(20) DEFAULT NULL COMMENT '修改人',
  `tenant_id` VARCHAR(20) NOT NULL DEFAULT '0' COMMENT '租户',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户信息';

-- 组织信息
CREATE TABLE IF NOT EXISTS `sys_group` (
  `id` BIGINT(20) NOT NULL COMMENT '主键',
  `parent_id` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '父级ID',
  `group_icon` VARCHAR(40) DEFAULT NULL COMMENT '组织icon',
  `group_name` VARCHAR(40) NOT NULL COMMENT '组织名称',
  `group_code` VARCHAR(20) NOT NULL COMMENT '组织编码',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用，0：否，1：是',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `revise_time` datetime DEFAULT NULL COMMENT '修改时间',
  `reviser` bigint(20) DEFAULT NULL COMMENT '修改人',
  `tenant_id` VARCHAR(20) NOT NULL DEFAULT '0' COMMENT '租户',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='组织信息';

-- 用户组织关联表
CREATE TABLE IF NOT EXISTS `sys_user_group` (
  `id` BIGINT(20) NOT NULL COMMENT '主键',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `group_id` BIGINT(20) NOT NULL COMMENT '组织ID',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `revise_time` datetime DEFAULT NULL COMMENT '修改时间',
  `reviser` bigint(20) DEFAULT NULL COMMENT '修改人',
  `tenant_id` VARCHAR(20) NOT NULL DEFAULT '0' COMMENT '租户',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户组织关联表';

-- 角色信息
CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` BIGINT(20) NOT NULL COMMENT '主键',
  `role_name` VARCHAR(40) NOT NULL COMMENT '角色名称',
  `role_code` VARCHAR(20) NOT NULL COMMENT '角色编码',
  `role_icon` VARCHAR(40) DEFAULT NULL COMMENT '角色icon',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用，0：否，1：是',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `revise_time` datetime DEFAULT NULL COMMENT '修改时间',
  `reviser` bigint(20) DEFAULT NULL COMMENT '修改人',
  `tenant_id` VARCHAR(20) NOT NULL DEFAULT '0' COMMENT '租户',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='角色信息';

-- 用户角色关联表
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `id` BIGINT(20) NOT NULL COMMENT '主键',
  `user_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `revise_time` datetime DEFAULT NULL COMMENT '修改时间',
  `reviser` bigint(20) DEFAULT NULL COMMENT '修改人',
  `tenant_id` VARCHAR(20) NOT NULL DEFAULT '0' COMMENT '租户',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='用户角色关联表';

-- 菜单信息
CREATE TABLE IF NOT EXISTS `sys_menu` (
  `id` BIGINT(20) NOT NULL COMMENT '主键',
  `parent_id` BIGINT(20) NOT NULL DEFAULT '0' COMMENT '父级ID',
  `menu_icon` VARCHAR(40) DEFAULT NULL COMMENT '菜单icon',
  `menu_name` VARCHAR(40) NOT NULL COMMENT '菜单名称',
  `menu_code` VARCHAR(20) NOT NULL COMMENT '菜单编码',
  `menu_url` VARCHAR(80) DEFAULT NULL COMMENT '菜单路径',
  `enabled` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否启用，0：否，1：是',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `revise_time` datetime DEFAULT NULL COMMENT '修改时间',
  `reviser` bigint(20) DEFAULT NULL COMMENT '修改人',
  `tenant_id` VARCHAR(20) NOT NULL DEFAULT '0' COMMENT '租户',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='菜单信息';

-- 菜单操作信息
CREATE TABLE IF NOT EXISTS `sys_menu_operate` (
  `id` BIGINT(20) NOT NULL COMMENT '主键',
  `menu_id` BIGINT(20) DEFAULT NULL COMMENT '菜单ID',
  `operate_name` VARCHAR(40) NOT NULL COMMENT '菜单名称',
  `operate_code` VARCHAR(20) NOT NULL COMMENT '菜单编码',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `revise_time` datetime DEFAULT NULL COMMENT '修改时间',
  `reviser` bigint(20) DEFAULT NULL COMMENT '修改人',
  `tenant_id` VARCHAR(20) NOT NULL DEFAULT '0' COMMENT '租户',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='菜单操作信息';

-- 角色菜单关联表
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `id` BIGINT(20) NOT NULL COMMENT '主键',
  `role_id` BIGINT(20) NOT NULL COMMENT '角色ID',
  `menu_id` BIGINT(20) NOT NULL COMMENT '用户ID',
  `menu_operate_ids` VARCHAR(200) DEFAULT NULL COMMENT '菜单操作ID，多个以英文逗号分隔',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `revise_time` datetime DEFAULT NULL COMMENT '修改时间',
  `reviser` bigint(20) DEFAULT NULL COMMENT '修改人',
  `tenant_id` VARCHAR(20) NOT NULL DEFAULT '0' COMMENT '租户',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='角色菜单关联表';

-- 系统文件表
CREATE TABLE IF NOT EXISTS `sys_storage` (
  `id` BIGINT(20) NOT NULL COMMENT '主键',
  `filename` VARCHAR(40) NOT NULL COMMENT '文件名称',
  `filepath` VARCHAR(200) NOT NULL COMMENT '访问路径',
  `storage_type` VARCHAR(20) NOT NULL DEFAULT 'LOCAL' COMMENT '存储方式',
  `deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否删除，0：否，1：是',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `creator` bigint(20) NOT NULL COMMENT '创建人',
  `revise_time` datetime DEFAULT NULL COMMENT '修改时间',
  `reviser` bigint(20) DEFAULT NULL COMMENT '修改人',
  `tenant_id` VARCHAR(20) NOT NULL DEFAULT '0' COMMENT '租户',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC COMMENT='系统文件表';