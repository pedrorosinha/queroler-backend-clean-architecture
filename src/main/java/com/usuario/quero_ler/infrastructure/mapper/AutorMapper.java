package com.usuario.quero_ler.infrastructure.mapper;

import com.usuario.quero_ler.core.entities.Autor;
import com.usuario.quero_ler.infrastructure.persistence.AutorEntity;
import org.springframework.stereotype.Component;

@Component
public class AutorMapper {

    public Autor toDomain(AutorEntity entity) {
        return new Autor(entity.getId(), entity.getNome(), null);
    }

    public AutorEntity toPersistence(Autor domain) {
        return AutorEntity.builder()
                .id(domain.id())
                .nome(domain.nome())
                .build();
    }
}
