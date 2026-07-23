package com.usuario.quero_ler.core.entities;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.usuario.quero_ler.fixtures.EntityBuilders.*;
import static org.junit.jupiter.api.Assertions.*;

class NotificacaoTest {

    @Test
    @DisplayName("Deve definir lido como FALSE quando nulo")
    void lidoDefaultFalse() {
        Notificacao notificacao = notificacao().build();
        assertFalse(notificacao.lido());
    }

    @Test
    @DisplayName("Deve preservar lido como TRUE quando explicitamente definido")
    void lidoExplicito() {
        Notificacao notificacao = notificacao().lido(true).build();
        assertTrue(notificacao.lido());
    }

    @Test
    @DisplayName("Deve criar notificacao com todos os campos")
    void camposCompletos() {
        var data = java.time.LocalDateTime.of(2026, 7, 15, 10, 0);
        Notificacao notificacao = notificacao()
                .id(1L)
                .dataDeCriacao(data)
                .notificacao("Nova mensagem")
                .lido(false)
                .build();

        assertEquals(1L, notificacao.id());
        assertEquals(data, notificacao.dataDeCriacao());
        assertEquals("Nova mensagem", notificacao.notificacao());
        assertFalse(notificacao.lido());
    }
}
