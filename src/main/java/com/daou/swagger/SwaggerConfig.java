package com.daou.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("v0.01") //버전
                .title("Daou Precapstone API") //이름
                .description("프리캡스톤"); //설명
        return new OpenAPI()
                .info(info);
    }
}