package com.usuario.quero_ler.core.usecases.notificacao.impl;

import com.usuario.quero_ler.core.entities.Notificacao;
import com.usuario.quero_ler.core.gateway.NotificacaoGateway;
import com.usuario.quero_ler.core.gateway.UsuarioNotificacaoGateway;
import com.usuario.quero_ler.core.usecases.notificacao.CriarNotificacaoUseCase;


public class CriarNotificacaoUseCaseImpl implements CriarNotificacaoUseCase {

    private final NotificacaoGateway notificacaoGateway;
    private final UsuarioNotificacaoGateway usuarioNotificacaoGateway;

    public CriarNotificacaoUseCaseImpl(NotificacaoGateway notificacaoGateway,
                                       UsuarioNotificacaoGateway usuarioNotificacaoGateway) {
        this.notificacaoGateway = notificacaoGateway;
        this.usuarioNotificacaoGateway = usuarioNotificacaoGateway;
    }

    @Override
    public Notificacao execute(String texto) {
        Notificacao notificacao = new Notificacao(null, null, texto, null);
        notificacao = notificacaoGateway.save(notificacao);
        usuarioNotificacaoGateway.enviarParaTodosUsuarios(notificacao.id());
        return notificacao;
    }
}
