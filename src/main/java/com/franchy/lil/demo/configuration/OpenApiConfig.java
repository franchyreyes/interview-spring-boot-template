package com.franchy.lil.demo.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title("Custom API Title").description("Custom API Description").version(
                "1.0.0").contact(new Contact().name("API Support").url("http://example.com/contact").email("support" +
                "@example.com")).license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
