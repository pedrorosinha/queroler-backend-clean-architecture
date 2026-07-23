package com.usuario.quero_ler.infrastructure.mapper;

import com.usuario.quero_ler.core.entities.Notificacao;
import com.usuario.quero_ler.core.entities.UsuarioNotificacao;
import com.usuario.quero_ler.infrastructure.dto.notificacao.NotificacaoResponseDto;
import com.usuario.quero_ler.infrastructure.persistence.NotificacaoEntity;
import com.usuario.quero_ler.infrastructure.persistence.UsuarioNotificacaoEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificacaoMapper {

    public Notificacao toDomain(NotificacaoEntity entity) {
        if (entity == null) return null;
        return new Notificacao(entity.getId(), entity.getDataDeCriacao(),
                entity.getNotificacao(), entity.getLido());
    }

    public UsuarioNotificacao toDomain(UsuarioNotificacaoEntity entity) {
        if (entity == null) return null;
        return new UsuarioNotificacao(entity.getId(), null,
                entity.getNotificacao() != null ? toDomain(entity.getNotificacao()) : null,
                entity.getVisualizada(), entity.getDataLeitura());
    }

    public NotificacaoEntity toPersistence(Notificacao domain) {
        if (domain == null) return null;
        return NotificacaoEntity.builder()
                .id(domain.id())
                .dataDeCriacao(domain.dataDeCriacao())
                .notificacao(domain.notificacao())
                .lido(domain.lido())
                .build();
    }

    public NotificacaoResponseDto toResponse(UsuarioNotificacao domain) {
        if (domain == null) return null;
        Notificacao notif = domain.notificacao();
        return new NotificacaoResponseDto(
                notif != null ? notif.id() : null,
                notif != null ? notif.notificacao() : null,
                notif != null ? notif.dataDeCriacao() : null,
                domain.visualizada()
        );
    }
}
