package com.usuario.quero_ler.core.usecases.documento.impl;

import com.usuario.quero_ler.core.entities.Documento;
import com.usuario.quero_ler.core.entities.Notificacao;
import com.usuario.quero_ler.core.gateway.DocumentoGateway;
import com.usuario.quero_ler.core.gateway.NotificacaoGateway;
import com.usuario.quero_ler.core.gateway.UsuarioNotificacaoGateway;
import com.usuario.quero_ler.core.usecases.documento.CriarDocumentoUseCase;


public class CriarDocumentoUseCaseImpl implements CriarDocumentoUseCase {

    private final DocumentoGateway documentoGateway;
    private final NotificacaoGateway notificacaoGateway;
    private final UsuarioNotificacaoGateway usuarioNotificacaoGateway;

    public CriarDocumentoUseCaseImpl(DocumentoGateway documentoGateway,
                                    NotificacaoGateway notificacaoGateway,
                                    UsuarioNotificacaoGateway usuarioNotificacaoGateway) {
        this.documentoGateway = documentoGateway;
        this.notificacaoGateway = notificacaoGateway;
        this.usuarioNotificacaoGateway = usuarioNotificacaoGateway;
    }

    @Override
    public Documento execute(Documento documento) {
        Documento salvo = documentoGateway.save(documento);

        Notificacao notificacao = new Notificacao(null, null, salvo.tipo().name(), null);
        notificacao = notificacaoGateway.save(notificacao);
        usuarioNotificacaoGateway.enviarParaTodosUsuarios(notificacao.id());

        return salvo;
    }
}
