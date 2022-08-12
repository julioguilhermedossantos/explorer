package br.com.elo7.explorer.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI swagger() {
        return new OpenAPI()
                .info(new Info().title("Desafio elo7 - Sondas e planetas")
                        .description("API explorer")
                        .version("v1"));
    }
}

