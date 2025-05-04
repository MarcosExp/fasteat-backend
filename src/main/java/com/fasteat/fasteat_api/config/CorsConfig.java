package com.fasteat.fasteat_api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/*
 * Clase que permite la configuracion de CORS en la API
 * CORS (Cross-Origin Resource Sharing) es un mecanismo que permite que recursos restringidos en una página web
 * puedan ser solicitados desde otro dominio fuera del dominio desde el que se sirvió el primer recurso.
 * En este caso, se permite el acceso a la API desde el puerto 4200 en que correra el frontend.
 *
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*");
    }
}
