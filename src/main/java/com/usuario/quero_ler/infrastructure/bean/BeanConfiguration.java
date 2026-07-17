package com.usuario.quero_ler.infrastructure.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Configuration
public class BeanConfiguration {

    @Bean
    Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }
}
