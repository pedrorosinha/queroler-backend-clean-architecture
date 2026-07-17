package com.usuario.quero_ler.infrastructure.gateway;

import com.usuario.quero_ler.core.entities.DiarioDeLeitura;
import com.usuario.quero_ler.core.entities.Leitura;
import com.usuario.quero_ler.core.gateway.DiarioDeLeituraGateway;
import com.usuario.quero_ler.infrastructure.mapper.DiarioLeituraMapper;
import com.usuario.quero_ler.infrastructure.mapper.EntityMapper;
import com.usuario.quero_ler.infrastructure.persistence.DiarioDeLeituraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DiarioDeLeituraRepositoryGateway implements DiarioDeLeituraGateway {

    private final DiarioDeLeituraRepository diarioDeLeituraRepository;
    private final DiarioLeituraMapper diarioLeituraMapper;
    private final EntityMapper entityMapper;

    @Override
    public boolean existsByLeitura(Leitura leitura) {
        var entity = entityMapper.toPersistence(leitura);
        return diarioDeLeituraRepository.existsByLeitura(entity);
    }

    @Override
    public Optional<DiarioDeLeitura> findByUsuarioIdAndLivroId(Long usuarioId, Long livroId) {
        return diarioDeLeituraRepository.findByUsuarioIdAndLivroId(usuarioId, livroId)
                .map(diarioLeituraMapper::toDomain);
    }

    @Override
    public Optional<DiarioDeLeitura> findById(Long id) {
        return diarioDeLeituraRepository.findById(id)
                .map(diarioLeituraMapper::toDomain);
    }

    @Override
    public DiarioDeLeitura save(DiarioDeLeitura diario) {
        var entity = diarioLeituraMapper.toPersistence(diario);
        var saved = diarioDeLeituraRepository.save(entity);
        return diarioLeituraMapper.toDomain(saved);
    }
}
