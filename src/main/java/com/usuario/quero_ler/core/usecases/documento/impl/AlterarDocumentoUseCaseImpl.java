package com.usuario.quero_ler.core.usecases.documento.impl;

import com.usuario.quero_ler.core.entities.Documento;
import com.usuario.quero_ler.core.entities.Notificacao;
import com.usuario.quero_ler.core.gateway.DocumentoGateway;
import com.usuario.quero_ler.core.gateway.NotificacaoGateway;
import com.usuario.quero_ler.core.gateway.UsuarioNotificacaoGateway;
import com.usuario.quero_ler.core.usecases.documento.AlterarDocumentoUseCase;
import com.usuario.quero_ler.core.exceptions.DocumentoNaoEncontradoException;


public class AlterarDocumentoUseCaseImpl implements AlterarDocumentoUseCase {

    private final DocumentoGateway documentoGateway;
    private final NotificacaoGateway notificacaoGateway;
    private final UsuarioNotificacaoGateway usuarioNotificacaoGateway;

    public AlterarDocumentoUseCaseImpl(DocumentoGateway documentoGateway,
                                      NotificacaoGateway notificacaoGateway,
                                      UsuarioNotificacaoGateway usuarioNotificacaoGateway) {
        this.documentoGateway = documentoGateway;
        this.notificacaoGateway = notificacaoGateway;
        this.usuarioNotificacaoGateway = usuarioNotificacaoGateway;
    }

    @Override
    public void execute(Long id, Documento documentoAtualizado) {
        Documento documento = documentoGateway.findById(id)
                .orElseThrow(() -> new DocumentoNaoEncontradoException("Documento não cadastrado."));

        if (documentoAtualizado.titulo() != null) {
            documento = documento.withTitulo(documentoAtualizado.titulo());
        }
        if (documentoAtualizado.tipo() != null) {
            documento = documento.withTipo(documentoAtualizado.tipo());
        }
        if (documentoAtualizado.conteudo() != null) {
            documento = documento.withConteudo(documentoAtualizado.conteudo());
        }
        if (documentoAtualizado.ultimaAlteracao() != null) {
            documento = documento.withUltimaAlteracao(documentoAtualizado.ultimaAlteracao());
        }

        documentoGateway.save(documento);

        Notificacao notificacao = new Notificacao(null, null, documento.tipo().name(), null);
        notificacao = notificacaoGateway.save(notificacao);
        usuarioNotificacaoGateway.enviarParaTodosUsuarios(notificacao.id());
    }
}
