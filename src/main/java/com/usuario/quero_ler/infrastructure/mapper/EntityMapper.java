package com.usuario.quero_ler.infrastructure.mapper;

import com.usuario.quero_ler.core.entities.Leitura;
import com.usuario.quero_ler.core.entities.User;
import com.usuario.quero_ler.core.entities.UsuarioNotificacao;
import com.usuario.quero_ler.infrastructure.persistence.LeituraEntity;
import com.usuario.quero_ler.infrastructure.persistence.UserEntity;
import com.usuario.quero_ler.infrastructure.persistence.UsuarioNotificacaoEntity;
import org.springframework.stereotype.Component;

@Component
public class EntityMapper {

    private final UsuarioMapper usuarioMapper;

    private final NotificacaoMapper notificacaoMapper;

    EntityMapper(UsuarioMapper usuarioMapper, NotificacaoMapper notificacaoMapper) {
        this.usuarioMapper = usuarioMapper;
        this.notificacaoMapper = notificacaoMapper;
    }

    public User toDomain(UserEntity entity) {
        if (entity == null) return null;
        return new User(entity.getId(), entity.getUser(), entity.getSenha(),
                entity.getSenhaTrocada(), entity.getProfile(),
                entity.getUsuario() != null ? usuarioMapper.toDomain(entity.getUsuario()) : null);
    }

    public UserEntity toPersistence(User domain) {
        if (domain == null) return null;
        return UserEntity.builder()
                .id(domain.id())
                .user(domain.user())
                .senha(domain.senha())
                .senhaTrocada(domain.senhaTrocada())
                .profile(domain.profile())
                .build();
    }

    public Leitura toDomain(LeituraEntity entity) {
        if (entity == null) return null;
        return new Leitura(entity.getId(), entity.getStatus(), null, null, null, entity.getLido());
    }

    public LeituraEntity toPersistence(Leitura domain) {
        if (domain == null) return null;
        return LeituraEntity.builder()
                .id(domain.id())
                .status(domain.status())
                .lido(domain.lido())
                .build();
    }

    public UsuarioNotificacao toDomain(UsuarioNotificacaoEntity entity) {
        if (entity == null) return null;
        return new UsuarioNotificacao(entity.getId(), null,
                entity.getNotificacao() != null ? notificacaoMapper.toDomain(entity.getNotificacao()) : null,
                entity.getVisualizada(), entity.getDataLeitura());
    }

    public UsuarioNotificacaoEntity toPersistence(UsuarioNotificacao domain) {
        if (domain == null) return null;
        return UsuarioNotificacaoEntity.builder()
                .id(domain.id())
                .visualizada(domain.visualizada())
                .dataLeitura(domain.dataLeitura())
                .build();
    }
}
