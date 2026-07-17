package com.usuario.quero_ler.infrastructure.gateway;

import com.usuario.quero_ler.core.entities.UsuarioNotificacao;
import com.usuario.quero_ler.core.gateway.UsuarioNotificacaoGateway;
import com.usuario.quero_ler.core.utils.PaginatedResult;
import com.usuario.quero_ler.core.utils.Pagination;
import com.usuario.quero_ler.infrastructure.mapper.EntityMapper;
import com.usuario.quero_ler.infrastructure.persistence.UsuarioNotificacaoRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UsuarioNotificacaoRepositoryGateway implements UsuarioNotificacaoGateway {

    private final UsuarioNotificacaoRepository usuarioNotificacaoRepository;
    private final EntityMapper entityMapper;

    @Override
    public void enviarParaTodosUsuarios(Long notificacaoId) {
        usuarioNotificacaoRepository.enviarParaTodosUsuarios(notificacaoId);
    }

    @Override
    public void marcarComoLidas(Long usuarioId) {
        usuarioNotificacaoRepository.marcarComoLidas(usuarioId);
    }

    @Override
    public void deleteByNotificacaoDataDeCriacaoBefore(LocalDateTime data) {
        usuarioNotificacaoRepository.deleteByNotificacaoDataDeCriacaoBefore(data);
    }

    @Override
    public List<UsuarioNotificacao> findByUsuarioId(Long usuarioId) {
        return usuarioNotificacaoRepository.findByUsuarioId(usuarioId)
                .stream()
                .map(entityMapper::toDomain)
                .toList();
    }

    @Override
    public PaginatedResult<UsuarioNotificacao> buscarTodasPorUsuario(Long usuarioId, Pagination pagination) {
        var pageable = PageRequest.of(pagination.page(), pagination.size());
        var page = usuarioNotificacaoRepository.buscarTodasPorUsuario(usuarioId, pageable)
                .map(entityMapper::toDomain);
        return new PaginatedResult<>(
                page.getContent(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getNumber(),
                page.getSize());
    }

    @Override
    public void delete(UsuarioNotificacao usuarioNotificacao) {
        var entity = entityMapper.toPersistence(usuarioNotificacao);
        usuarioNotificacaoRepository.delete(entity);
    }
}
