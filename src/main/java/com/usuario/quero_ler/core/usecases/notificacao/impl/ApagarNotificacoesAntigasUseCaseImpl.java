package com.usuario.quero_ler.core.usecases.notificacao.impl;

import com.usuario.quero_ler.core.gateway.NotificacaoGateway;
import com.usuario.quero_ler.core.gateway.UsuarioNotificacaoGateway;
import com.usuario.quero_ler.core.usecases.notificacao.ApagarNotificacoesAntigasUseCase;


import java.time.LocalDateTime;

public class ApagarNotificacoesAntigasUseCaseImpl implements ApagarNotificacoesAntigasUseCase {

    private final UsuarioNotificacaoGateway usuarioNotificacaoGateway;
    private final NotificacaoGateway notificacaoGateway;

    public ApagarNotificacoesAntigasUseCaseImpl(UsuarioNotificacaoGateway usuarioNotificacaoGateway,
                                                NotificacaoGateway notificacaoGateway) {
        this.usuarioNotificacaoGateway = usuarioNotificacaoGateway;
        this.notificacaoGateway = notificacaoGateway;
    }

    @Override
    public void execute() {
        LocalDateTime dataDeCorte = LocalDateTime.now().minusDays(30);
        usuarioNotificacaoGateway.deleteByNotificacaoDataDeCriacaoBefore(dataDeCorte);
        notificacaoGateway.deleteByDataDeCriacaoBefore(dataDeCorte);
    }
}
