package com.usuario.quero_ler.infrastructure.gateway;

import com.usuario.quero_ler.core.entities.Notificacao;
import com.usuario.quero_ler.core.gateway.NotificacaoGateway;
import com.usuario.quero_ler.infrastructure.mapper.NotificacaoMapper;
import com.usuario.quero_ler.infrastructure.persistence.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class NotificacaoRepositoryGateway implements NotificacaoGateway {

    private final NotificacaoRepository notificacaoRepository;
    private final NotificacaoMapper notificacaoMapper;

    @Override
    public Notificacao save(Notificacao notificacao) {
        var entity = notificacaoMapper.toPersistence(notificacao);
        var saved = notificacaoRepository.save(entity);
        return notificacaoMapper.toDomain(saved);
    }

    @Override
    public void deleteByDataDeCriacaoBefore(LocalDateTime dataDeCorte) {
        notificacaoRepository.deleteByDataDeCriacaoBefore(dataDeCorte);
    }
}
