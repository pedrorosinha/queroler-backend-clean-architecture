package com.usuario.quero_ler.infrastructure.gateway;

import com.usuario.quero_ler.core.entities.Leitura;
import com.usuario.quero_ler.core.entities.Livro;
import com.usuario.quero_ler.core.gateway.LeituraGateway;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;
import com.usuario.quero_ler.infrastructure.mapper.EntityMapper;
import com.usuario.quero_ler.infrastructure.mapper.LivroMapper;
import com.usuario.quero_ler.infrastructure.persistence.LeituraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LeituraRepositoryGateway implements LeituraGateway {

    private final LeituraRepository leituraRepository;
    private final EntityMapper entityMapper;
    private final LivroMapper livroMapper;

    @Override
    public Optional<Leitura> findByUsuarioIdAndLivroId(Long usuarioId, Long livroId) {
        return leituraRepository.findByUsuarioIdAndLivroId(usuarioId, livroId)
                .map(entityMapper::toDomain);
    }

    @Override
    public Leitura save(Leitura leitura) {
        var entity = entityMapper.toPersistence(leitura);
        var saved = leituraRepository.save(entity);
        return entityMapper.toDomain(saved);
    }

    @Override
    public void delete(Leitura leitura) {
        var entity = entityMapper.toPersistence(leitura);
        leituraRepository.delete(entity);
    }

    @Override
    public PaginatedResult<Leitura> findAllByUsuarioId(Long usuarioId, Pagination pagination) {
        var pageable = PageRequest.of(pagination.page(), pagination.size());
        var page = leituraRepository.findAllByUsuarioId(usuarioId, pageable)
                .map(entityMapper::toDomain);
        return new PaginatedResult<>(page.getContent(), page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
    }

    @Override
    public PaginatedResult<Livro> findLivrosByUsuarioId(Long usuarioId, Pagination pagination) {
        var pageable = PageRequest.of(pagination.page(), pagination.size());
        var page = leituraRepository.findLivrosByUsuarioId(usuarioId, pageable)
                .map(livroMapper::toDomain);
        return new PaginatedResult<>(page.getContent(), page.getTotalElements(), page.getTotalPages(), page.getNumber(), page.getSize());
    }
}
