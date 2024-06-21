package com.joey.Books_API.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerUIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Books API")
                        .version("1.0")
                        .description("API RestFull for Books Store")
                        .contact(new Contact()
                                .name("Joey")
                                .email("joey_qpb@hotmail.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("My GitHub")
                        .url("https://github.com/JoeyQPB"));
    }
}
