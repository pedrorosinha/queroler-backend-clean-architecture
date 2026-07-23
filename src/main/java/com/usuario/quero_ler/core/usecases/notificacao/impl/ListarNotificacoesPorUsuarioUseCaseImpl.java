package com.usuario.quero_ler.core.usecases.notificacao.impl;

import com.usuario.quero_ler.core.entities.UsuarioNotificacao;
import com.usuario.quero_ler.core.gateway.UsuarioNotificacaoGateway;
import com.usuario.quero_ler.core.usecases.notificacao.ApagarNotificacoesAntigasUseCase;
import com.usuario.quero_ler.core.usecases.notificacao.ListarNotificacoesPorUsuarioUseCase;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;

public class ListarNotificacoesPorUsuarioUseCaseImpl implements ListarNotificacoesPorUsuarioUseCase {

    private final UsuarioNotificacaoGateway usuarioNotificacaoGateway;
    private final ApagarNotificacoesAntigasUseCase apagarNotificacoesAntigasUseCase;

    public ListarNotificacoesPorUsuarioUseCaseImpl(UsuarioNotificacaoGateway usuarioNotificacaoGateway,
                                                   ApagarNotificacoesAntigasUseCase apagarNotificacoesAntigasUseCase) {
        this.usuarioNotificacaoGateway = usuarioNotificacaoGateway;
        this.apagarNotificacoesAntigasUseCase = apagarNotificacoesAntigasUseCase;
    }

    @Override
    public PaginatedResult<UsuarioNotificacao> execute(Long usuarioId, Pagination pagination) {
        apagarNotificacoesAntigasUseCase.execute();
        return usuarioNotificacaoGateway.buscarTodasPorUsuario(usuarioId, pagination);
    }
}
