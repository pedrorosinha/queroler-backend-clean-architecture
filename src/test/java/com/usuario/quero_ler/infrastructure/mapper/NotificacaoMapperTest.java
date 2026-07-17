package com.usuario.quero_ler.infrastructure.mapper;

import com.usuario.quero_ler.infrastructure.persistence.NotificacaoEntity;
import com.usuario.quero_ler.fixtures.NotificacaoFixture;
import com.usuario.quero_ler.core.entities.Notificacao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificacaoMapperTest {

    @InjectMocks
    private NotificacaoMapper mapper;

    @Test
    @DisplayName("Deve converter uma notificação entidade (infra) em notificação de domínio (core)")
    void toDomain() {
        NotificacaoEntity entity = NotificacaoFixture.entity();

        Notificacao domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(entity.getId(), domain.id());
        assertEquals(entity.getNotificacao(), domain.notificacao());
        assertEquals(entity.getDataDeCriacao(), domain.dataDeCriacao());
        assertEquals(entity.getLido(), domain.lido());
    }
}