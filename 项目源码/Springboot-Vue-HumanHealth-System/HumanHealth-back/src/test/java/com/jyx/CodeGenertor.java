package com.jyx;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenertor {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/health?useUnicode=true&characterEncoding=UTF-8&useSSL=false";
        String username="root";
        String password="root";
        String moduleName = "healthsys";
        String mapperLocation = "D:\\Idea_Project\\x-admin\\src\\main\\resources\\mapper\\"+moduleName;
        String table ="j_user,j_role,j_menu,j_user_role,j_role_menu";

        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("金义雄") // 设置作者
//                            .enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\Idea_Project\\x-admin\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.jyx") // 设置父包名
                            .moduleName(moduleName) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, mapperLocation)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(table) // 设置需要生成的表名
                            .addTablePrefix("j_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
