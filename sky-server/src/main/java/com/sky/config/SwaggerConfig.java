package com.sky.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc  // 必须添加，激活 Springfox + Knife4j 支持
public class SwaggerConfig {

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.OAS_30) // 使用 OpenAPI 3.0 规范
                .groupName("api")  // ✅ 防止与默认 Docket 冲突
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sky.controller"))  // ✅ 仅扫描你的 controller 包
                .paths(PathSelectors.any())
                .build();
    }
}
