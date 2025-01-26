package com.makestar.chat.interfaces.swagger;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("메이크스타 채팅시스템 API")
                        .description("채팅시스템을 위한 API")
                        .version("v1.0"));
    }
}