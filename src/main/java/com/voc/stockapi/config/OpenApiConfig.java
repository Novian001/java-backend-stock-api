package com.voc.stockapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI stockApiOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("Stock Management API")
                .version("1.0")
                .description("REST API untuk manajemen stok produk (Spring Boot + PostgreSQL)"));
    }
}