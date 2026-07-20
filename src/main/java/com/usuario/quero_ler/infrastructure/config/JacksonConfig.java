package com.usuario.quero_ler.infrastructure.config;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.annotation.JsonFormat;

@Configuration
public class JacksonConfig {

    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private static final String DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";

    @Bean
    JsonMapperBuilderCustomizer jsonCustomizer() {
        return builder -> {
            builder.withConfigOverride(LocalDate.class,
                    cfg -> cfg.setFormat(JsonFormat.Value.forPattern(DATE_FORMAT)));
            builder.withConfigOverride(LocalDateTime.class,
                    cfg -> cfg.setFormat(JsonFormat.Value.forPattern(DATE_TIME_FORMAT)));
        };
    }
}
