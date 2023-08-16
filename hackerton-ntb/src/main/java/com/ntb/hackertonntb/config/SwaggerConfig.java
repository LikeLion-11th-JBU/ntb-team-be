package com.ntb.hackertonntb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ntb.hackertonntb"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(info());
    }

    //정보 객체를 생성하는 메소드
    public ApiInfo info() {
        return new ApiInfoBuilder()
                .title("NTB 프로젝트")
                .description("NTB Swagger")
                .version("프로젝트 버전(3.0.0)")
                .license("NTB")
                .build();
    }
}
