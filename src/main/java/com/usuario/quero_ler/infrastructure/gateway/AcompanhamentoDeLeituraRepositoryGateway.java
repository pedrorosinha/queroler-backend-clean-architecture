package com.usuario.quero_ler.infrastructure.gateway;

import com.usuario.quero_ler.core.entities.AcompanhamentoDeLeitura;
import com.usuario.quero_ler.core.gateway.AcompanhamentoDeLeituraGateway;
import com.usuario.quero_ler.infrastructure.mapper.DiarioLeituraMapper;
import com.usuario.quero_ler.infrastructure.persistence.AcompanhamentoDeLeituraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AcompanhamentoDeLeituraRepositoryGateway implements AcompanhamentoDeLeituraGateway {

    private final AcompanhamentoDeLeituraRepository acompanhamentoRepository;
    private final DiarioLeituraMapper diarioLeituraMapper;

    @Override
    public List<AcompanhamentoDeLeitura> findByLivroIdWithJoins(Long livroId) {
        return acompanhamentoRepository.findByLivroIdWithJoins(livroId)
                .stream()
                .map(diarioLeituraMapper::toDomainAcompanhamento)
                .toList();
    }

    @Override
    public List<AcompanhamentoDeLeitura> findByUsuarioIdWithJoins(Long usuarioId) {
        return acompanhamentoRepository.findByUsuarioIdWithJoins(usuarioId)
                .stream()
                .map(diarioLeituraMapper::toDomainAcompanhamento)
                .toList();
    }

    @Override
    public AcompanhamentoDeLeitura save(AcompanhamentoDeLeitura acompanhamento) {
        var entity = diarioLeituraMapper.toPersistence(acompanhamento);
        var saved = acompanhamentoRepository.save(entity);
        return diarioLeituraMapper.toDomainAcompanhamento(saved);
    }
}
