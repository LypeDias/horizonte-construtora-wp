package com.api_horizonte.api_horizonte.Infraestructure.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Libera todos os endpoints da API
                        .allowedOrigins("*") // PERMITE QUALQUER ORIGEM (Crucial para o teste)
                        .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS") // Adicionado OPTIONS
                        .allowedHeaders("*")
                        .allowCredentials(false); // Quando usar "*", o credentials deve ser false
            }
        };
    }
}