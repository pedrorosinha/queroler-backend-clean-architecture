package com.usuario.quero_ler.core.gateway;

import com.usuario.quero_ler.core.entities.Notificacao;

import java.time.LocalDateTime;

public interface NotificacaoGateway {
    Notificacao save(Notificacao notificacao);

    void deleteByDataDeCriacaoBefore(LocalDateTime dataDeCorte);
}
