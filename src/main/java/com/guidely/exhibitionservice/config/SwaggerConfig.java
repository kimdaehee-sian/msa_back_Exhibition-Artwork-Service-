package com.guidely.exhibitionservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Exhibition & Artwork Service API")
                        .description("전시회와 작품 관리를 위한 REST API 서비스")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Guidely Team")
                                .email("contact@guidely.com")))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("로컬 개발 서버"),
                        new Server().url("https://your-app-name.azurewebsites.net").description("Azure 프로덕션 서버")
                ));
    }
} 