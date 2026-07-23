package com.usuario.quero_ler.core.usecases.notificacao;

import com.usuario.quero_ler.core.entities.UsuarioNotificacao;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;

public interface ListarNotificacoesPorUsuarioUseCase {
    PaginatedResult<UsuarioNotificacao> execute(Long usuarioId, Pagination pagination);
}
