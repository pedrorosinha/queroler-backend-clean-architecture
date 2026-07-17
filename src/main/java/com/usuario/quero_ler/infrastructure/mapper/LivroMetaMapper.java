package com.usuario.quero_ler.infrastructure.mapper;

import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.infrastructure.persistence.LivroMetaEntity;
import org.springframework.stereotype.Component;

@Component
public class LivroMetaMapper {

    private final LivroMapper livroMapper;

    LivroMetaMapper(LivroMapper livroMapper) {
        this.livroMapper = livroMapper;
    }

    public Livro toDomain(LivroMetaEntity entity) {
        if (entity == null) return null;
        return livroMapper.toDomain(entity.getLivro());
    }
}
