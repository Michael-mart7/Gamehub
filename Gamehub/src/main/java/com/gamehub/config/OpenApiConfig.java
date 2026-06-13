package com.gamehub.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gamehubOpenApi() {

        return new OpenAPI()

                .info(new Info()

                        .title("GAMEHUB API REST")

                        .description(
                                "API REST del sistema GAMEHUB para "
                                + "gestión de videojuegos, usuarios "
                                + "y autenticación."
                        )

                        .version("1.0")

                        .contact(new Contact()
                                .name("Equipo GAMEHUB")
                                .email("gamehub@uts.edu.co")
                        )

                        .license(new License()
                                .name("Proyecto Académico")
                        )
                )

                .externalDocs(
                        new ExternalDocumentation()

                                .description(
                                        "Documentación del proyecto"
                                )
                );
    }
}