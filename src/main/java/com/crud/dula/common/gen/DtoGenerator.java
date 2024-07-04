package com.crud.dula.common.gen;

import com.mybatisflex.codegen.config.GlobalConfig;
import com.mybatisflex.codegen.config.PackageConfig;
import com.mybatisflex.codegen.entity.Table;
import com.mybatisflex.codegen.generator.IGenerator;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author crud
 * @date 2024/5/30
 */
public class DtoGenerator implements IGenerator {

    private String templatePath;

    public DtoGenerator() {
        this.templatePath = "/gen/dto.tpl";
    }

    @Override
    public String getTemplatePath() {
        return templatePath;
    }

    @Override
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    @Override
    public void generate(Table table, GlobalConfig globalConfig) {

        PackageConfig packageConfig = globalConfig.getPackageConfig();

        String entityPackagePath = packageConfig.getBasePackage().replace(".", "/");
        File dtoJavaFile = new File(packageConfig.getSourceDir(), entityPackagePath + "/dto/" +
                table.buildEntityClassName() + "DTO.java");

        Map<String, Object> params = new HashMap<>();
        params.put("table", table);
        params.put("packageConfig", packageConfig);
        params.put("javadocConfig", globalConfig.getJavadocConfig());
        globalConfig.getTemplateConfig().getTemplate().generate(params, templatePath, dtoJavaFile);

        System.out.println("DTO ---> " + dtoJavaFile);
    }
}
