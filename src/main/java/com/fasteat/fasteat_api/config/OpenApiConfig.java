package com.fasteat.fasteat_api.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "Fasteat API", version = "v1"),
    servers = {
        @Server(url = "https://fasteat-backend-production.up.railway.app/api", description = "Producción"),
        @Server(url = "http://localhost:8080/api", description = "Local")
    }
)
@Configuration
public class OpenApiConfig {
    
}