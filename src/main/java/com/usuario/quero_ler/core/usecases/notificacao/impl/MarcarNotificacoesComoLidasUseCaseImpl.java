package com.usuario.quero_ler.core.usecases.notificacao.impl;

import com.usuario.quero_ler.core.gateway.UsuarioNotificacaoGateway;
import com.usuario.quero_ler.core.usecases.notificacao.ApagarNotificacoesAntigasUseCase;
import com.usuario.quero_ler.core.usecases.notificacao.MarcarNotificacoesComoLidasUseCase;


public class MarcarNotificacoesComoLidasUseCaseImpl implements MarcarNotificacoesComoLidasUseCase {

    private final UsuarioNotificacaoGateway usuarioNotificacaoGateway;
    private final ApagarNotificacoesAntigasUseCase apagarNotificacoesAntigasUseCase;

    public MarcarNotificacoesComoLidasUseCaseImpl(UsuarioNotificacaoGateway usuarioNotificacaoGateway,
                                                  ApagarNotificacoesAntigasUseCase apagarNotificacoesAntigasUseCase) {
        this.usuarioNotificacaoGateway = usuarioNotificacaoGateway;
        this.apagarNotificacoesAntigasUseCase = apagarNotificacoesAntigasUseCase;
    }

    @Override
    public void execute(Long usuarioId) {
        apagarNotificacoesAntigasUseCase.execute();
        usuarioNotificacaoGateway.marcarComoLidas(usuarioId);
    }
}
