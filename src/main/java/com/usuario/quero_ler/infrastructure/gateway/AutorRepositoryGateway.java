package com.usuario.quero_ler.infrastructure.gateway;

import com.usuario.quero_ler.core.entities.Autor;
import com.usuario.quero_ler.core.gateway.AutorGateway;
import com.usuario.quero_ler.infrastructure.mapper.AutorMapper;
import com.usuario.quero_ler.infrastructure.persistence.AutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutorRepositoryGateway implements AutorGateway {

    private final AutorRepository autorRepository;
    private final AutorMapper autorMapper;

    @Override
    public Autor save(Autor autor) {
        var entity = autorMapper.toPersistence(autor);
        var saved = autorRepository.save(entity);
        return autorMapper.toDomain(saved);
    }
}
