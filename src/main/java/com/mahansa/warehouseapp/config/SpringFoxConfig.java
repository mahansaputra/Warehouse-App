package com.mahansa.warehouseapp.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Hansa",
                        email = "mahansaputra@gmail.com",
                        url = "https://github.com/mahansaputra"
                ),
                description = "OpenApi documentation for Warehouse App",
                title = "Warehouse App - OpenApi Spec - Hansa",
                version = "1.0",
                license = @License(
                        name = "License name",
                        url = "https://some-url.com"
                ),
                termsOfService = "Terms of Service"
        )
//        servers = {
//                @Server(
//                        description = "Local Environment",
//                        url = "http://localhost:8080"
//                ),
//                @Server(
//                        description = "Prod Environment",
//                        url = "https://github.com/mahansaputra"
//                )
//        }
)
public class SpringFoxConfig {
}