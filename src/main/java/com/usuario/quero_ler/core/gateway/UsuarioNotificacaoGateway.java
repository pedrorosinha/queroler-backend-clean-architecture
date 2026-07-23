package com.usuario.quero_ler.core.gateway;

import com.usuario.quero_ler.core.entities.UsuarioNotificacao;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;

import java.time.LocalDateTime;
import java.util.List;

public interface UsuarioNotificacaoGateway {
    void enviarParaTodosUsuarios(Long notificacaoId);

    PaginatedResult<UsuarioNotificacao> buscarTodasPorUsuario(Long usuarioId, Pagination pagination);

    void marcarComoLidas(Long usuarioId);

    void deleteByNotificacaoDataDeCriacaoBefore(LocalDateTime data);

    List<UsuarioNotificacao> findByUsuarioId(Long usuarioId);

    void delete(UsuarioNotificacao usuarioNotificacao);
}
