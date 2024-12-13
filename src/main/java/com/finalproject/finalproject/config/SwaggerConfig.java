package com.finalproject.finalproject.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Api",
                description = "API системы лояльности",
                version = "1.0.0",
                contact = @Contact(
                        name = "Diyaz Turganaliev",
                        email = "diyaz18.08@gmail.com",
                        url = "https://youtu.be/kPa7bsKwL-c?si=Wja8Isea9_JCBKrE"
                )
        )
)
public class SwaggerConfig {

}
