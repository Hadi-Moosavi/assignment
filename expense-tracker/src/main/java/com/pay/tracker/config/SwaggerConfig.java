package com.pay.tracker.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                ).info(new Info()
                        .title("Expense Tracker API")
                        .description("Swagger of expense tracker, an assignment impl for snapp pay")
                        .version("0.0.1")
                ).addSecurityItem(
                        new SecurityRequirement()
                                .addList("bearer-jwt", Collections.emptyList())
                                .addList("bearer-key", Collections.emptyList())
                );
    }
}
