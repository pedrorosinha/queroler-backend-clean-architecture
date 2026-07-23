package com.usuario.quero_ler.fixtures;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import com.usuario.quero_ler.infrastructure.dto.notificacao.NotificacaoRequestDto;
import com.usuario.quero_ler.infrastructure.dto.notificacao.NotificacaoResponseDto;
import com.usuario.quero_ler.infrastructure.persistence.NotificacaoEntity;
import com.usuario.quero_ler.core.entities.Notificacao;
import java.time.LocalDateTime;

public class NotificacaoFixture {
    private static final Long ID = 3L;
    private static final String NOTIFICACAO = "Alterado o termo gerais";
    private static final LocalDateTime DATA_DE_CRIACAO = LocalDateTime.now();

    public static NotificacaoRequestDto requestDto(){
        return new NotificacaoRequestDto(NOTIFICACAO);
    }

    // Retorna a entidade de persistência da infraestrutura (JPA)
    public static NotificacaoEntity entity() {
        return NotificacaoEntity.builder()
                .id(ID)
                .dataDeCriacao(DATA_DE_CRIACAO)
                .notificacao(NOTIFICACAO)
                .lido(false)
                .build();
    }

    // Retorna o objeto de domínio do Core
    public static Notificacao domain(){
        return notificacao()
                .id(ID)
                .dataDeCriacao(DATA_DE_CRIACAO)
                .notificacao(NOTIFICACAO)
                .lido(false)
                .build();
    }

    public static NotificacaoResponseDto response(){
        return new NotificacaoResponseDto(ID, NOTIFICACAO, DATA_DE_CRIACAO, false);
    }   
}