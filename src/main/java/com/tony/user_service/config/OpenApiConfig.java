package com.tony.user_service.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "BearerAuth", // Nome que será usado para referenciar este esquema (ex: em @SecurityRequirement)
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Para Auditoria de Usuario con (JWT)")
                        .version("1.0")
                        .description("API REST para registro y login de usuários, utilizando autenticacion JWT (JSON Web Token) y criptografia BCrypt."));
    }
    
}




