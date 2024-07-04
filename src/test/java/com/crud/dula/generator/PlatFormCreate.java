package com.crud.dula.generator;

import com.crud.dula.common.base.BaseEntity;
import com.mybatisflex.codegen.Generator;
import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.constant.GenTypeConst;
import com.mybatisflex.codegen.generator.GeneratorFactory;
import com.mybatisflex.codegen.generator.impl.ServiceGenerator;
import com.mybatisflex.codegen.generator.impl.ServiceImplGenerator;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author crud
 * @date 2023/9/22 17:29
 */
public class PlatFormCreate {

    public static void main(String[] args) {
        //配置数据源
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl("jdbc:mysql://localhost:13306/dula_template");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        //创建配置内容，两种风格都可以。
        GlobalConfig globalConfig = createGlobalConfigUseStyle();
        GeneratorFactory.registerGenerator(GenTypeConst.SERVICE, new ServiceGenerator("/gen/baseService.tpl"));
        GeneratorFactory.registerGenerator(GenTypeConst.SERVICE_IMPL, new ServiceImplGenerator("/gen/baseServiceImpl.tpl"));
        //通过 datasource 和 globalConfig 创建代码生成器
        Generator generator = new Generator(dataSource, globalConfig);

        //生成代码
        generator.generate();
    }

    public static GlobalConfig createGlobalConfigUseStyle() {
        //创建配置内容
        GlobalConfig globalConfig = new GlobalConfig();

        //设置根包
        globalConfig.getPackageConfig()
                .setBasePackage("com.crud.dula.platform");

        //设置表前缀和只生成哪些表，setGenerateTable 未配置时，生成所有表
        globalConfig.getStrategyConfig()
                .setIgnoreColumns("id", "creator", "create_time", "reviser", "revise_time", "deleted", "tenant_id")
                // 配置前缀，不去前缀就注释
                //.setTablePrefix("t_")
                .setGenerateTable("sys_user", "sys_role", "sys_role_menu", "sys_menu", "sys_menu_operate", "sys_user_role", "sys_group", "sys_user_group", "sys_storage");

        //设置生成 entity 并启用 Lombok
        globalConfig.enableEntity()
                .setSuperClass(BaseEntity.class)
                .setImplInterfaces()
                .setWithLombok(true)
                .setOverwriteEnable(true);

        //设置生成 mapper
        globalConfig.enableMapper();
        globalConfig.enableService();
        globalConfig.enableServiceImpl();
        globalConfig.enableTableDef();
        globalConfig.setTableDefClassSuffix("Table");
        globalConfig.setTableDefOverwriteEnable(true);

        //可以单独配置某个列
//        ColumnConfig columnConfig = new ColumnConfig();
//        columnConfig.setColumnName("tenant_id");
//        columnConfig.setLarge(true);
//        columnConfig.setVersion(true);
//        globalConfig.getStrategyConfig()
//                .setColumnConfig("tb_account", columnConfig);

        return globalConfig;
    }
}
